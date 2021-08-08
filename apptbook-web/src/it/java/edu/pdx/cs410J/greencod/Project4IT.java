package edu.pdx.cs410J.greencod;

import edu.pdx.cs410J.InvokeMainTestCase;
import edu.pdx.cs410J.UncaughtExceptionInMain;
import edu.pdx.cs410J.web.HttpRequestHelper.RestException;
import org.hamcrest.CoreMatchers;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.MethodOrderer.MethodName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

import java.io.IOException;
import java.net.HttpURLConnection;

import static edu.pdx.cs410J.greencod.Project4.sToSb;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.jupiter.api.Assertions.fail;

/**
 * An integration test for {@link Project4} that invokes its main method with
 * various arguments
 */
@TestMethodOrder(MethodName.class)
class Project4IT extends InvokeMainTestCase {
    private static final String HOSTNAME = "localhost";
    private static final String PORT = System.getProperty("http.port", "8080");

    @Test
    void testReadMe() {
        MainMethodResult result = invokeMain(Project4.class, "-README", "Cody", "Head Transplant Consultation","12/31/3000", "11:00", "am", "07/21/1992", "11:11", "pm");
        assertThat(result.getTextWrittenToStandardOut(), CoreMatchers.containsString("Readme for project 4. This project creates a REST api and uploads it to the web and stuff. Lets you add and view new appointments"));
    }
    @Test
    void test0RemoveAllMappings() throws IOException {
        AppointmentBookRestClient client = new AppointmentBookRestClient(HOSTNAME, Integer.parseInt(PORT));
        client.removeAllAppointmentBooks();
    }

    @Test
    void test1NoCommandLineArguments() {
        MainMethodResult result = invokeMain( Project4.class );
        assertThat(result.getExitCode(), equalTo(1));
        assertThat(result.getTextWrittenToStandardError(), containsString(Project4.MISSING_ARGS));
    }

    @Test
    @Disabled
    void test3NoAppointmentBooksThrowsAppointmentBookRestException() {
        String owner = "Dave";
        try {
            invokeMain(Project4.class, "-host", HOSTNAME, "-port", PORT, owner);
            fail("Expected a RestException to be thrown");

        } catch (UncaughtExceptionInMain ex) {
            RestException cause = (RestException) ex.getCause();
            assertThat(cause.getHttpStatusCode(), equalTo(HttpURLConnection.HTTP_NOT_FOUND));
        }
    }

    @Test
    void test4AddAppointment() {
        String owner = "Dave";
        String description = "Teach Java";
        String beginDate = "07/21/1992";
        String beginTime = "11:11";
        String beginPeriod = "am";
        String endDate = "07/21/1992";
        String endTime = "11:11";
        String endPeriod = "pm";



        MainMethodResult result = invokeMain( Project4.class, "-host", HOSTNAME, "-port", PORT, "-print", owner, description, beginDate, beginTime, beginPeriod, endDate, endTime, endPeriod);
        assertThat(result.getTextWrittenToStandardError(), result.getExitCode(), equalTo(0));

        result = invokeMain( Project4.class, "-host", HOSTNAME, "-port", PORT, "-print", owner, description, beginDate, beginTime, beginPeriod, endDate, endTime, endPeriod);
        String out = result.getTextWrittenToStandardOut();
        assertThat(out, out, containsString(owner));
        assertThat(out, out, containsString(description));
    }
    @Test
    void test5Search() {
        String owner = "Dave";
        String beginDate = "07/21/1992";
        String beginTime = "11:11";
        String beginPeriod = "am";
        String endDate = "07/21/1992";
        String endTime = "11:11";
        String endPeriod = "pm";



        MainMethodResult result = invokeMain( Project4.class, "-host", HOSTNAME, "-port", PORT, "-search", owner, beginDate, beginTime, beginPeriod, endDate, endTime, endPeriod);
        assertThat(result.getTextWrittenToStandardError(), result.getExitCode(), equalTo(0));

        String out = result.getTextWrittenToStandardOut();
        assertThat(out, out, containsString(owner));
    }

    @Test
    void test6PrintOwner() {
        String owner = "Dave";



        MainMethodResult result = invokeMain( Project4.class, "-host", HOSTNAME, "-port", PORT, owner);
        assertThat(result.getTextWrittenToStandardError(), result.getExitCode(), equalTo(0));

        String out = result.getTextWrittenToStandardOut();
        assertThat(out, out, containsString(owner));
    }
    @Test
    void testUnknownArgsPrintsUnknownArgs(){
        MainMethodResult result = invokeMain(Project4.class, "-host", HOSTNAME, "-port", PORT, "-print", "-Eliza", "Cody", "Head Transplant Consultation","07/21/1992", "11:00", "am", "07/21/1992", "11:11", "pm");
        assertThat(result.getTextWrittenToStandardError(), CoreMatchers.containsString(Project4.UNKNOWN_COMMAND_LINE_ARGUMENT));
        assertThat(result.getTextWrittenToStandardError(), CoreMatchers.containsString(Project4.USAGE_MESSAGE));
        assertThat(result.getExitCode(), CoreMatchers.equalTo(1));
    }
    @Test
    void testIfStartAfterEnd(){
        MainMethodResult result = invokeMain(Project4.class, "-host", HOSTNAME, "-port", PORT, "-print", "Cody", "Head Transplant Consultation","12/31/3000", "11:00", "am", "07/21/1992", "11:11", "pm");
        assertThat(result.getTextWrittenToStandardError(), CoreMatchers.containsString(Project4.BEGIN_AFTER_END));
        assertThat(result.getExitCode(), CoreMatchers.equalTo(1));
    }
    @Test
    void testBeginTimeInRange() {
        MainMethodResult result = invokeMain(Project4.class, "-host", HOSTNAME, "-port", PORT, "Cody", "Head Transplant Consultation","07/21/3000", "11:99", "am", "07/21/1992", "11:11", "pm");
        assertThat(result.getTextWrittenToStandardError(), CoreMatchers.containsString(Project4.TIME_NOT_CORRECT));
        assertThat(result.getExitCode(), CoreMatchers.equalTo(1));
    }
    /**
     * Tests that invoking the main method with an end time out of range issues error
     */
    @Test
    void testEndTimeInRange() {
        MainMethodResult result = invokeMain(Project4.class, "-host", HOSTNAME, "-port", PORT, "Cody", "Head Transplant Consultation","07/21/3000", "11:00", "am", "07/21/1992", "11:99", "pm");
        assertThat(result.getTextWrittenToStandardError(), CoreMatchers.containsString(Project4.TIME_NOT_CORRECT));
        assertThat(result.getExitCode(), CoreMatchers.equalTo(1));
    }
    /**
     * Tests that invoking the main method with an end time using characters fails
     */
    @Test
    void testTimeIsNumber() {
        MainMethodResult result = invokeMain(Project4.class, "-host", HOSTNAME, "-port", PORT, "Cody", "Head Transplant Consultation","07/21/3000", "11:xx", "am", "07/21/1992", "11:99", "pm");
        assertThat(result.getTextWrittenToStandardError(), CoreMatchers.containsString(Project4.TIME_NOT_CORRECT));
        assertThat(result.getExitCode(), CoreMatchers.equalTo(1));
    }
}