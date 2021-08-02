package edu.pdx.cs410J.greencod;

import edu.pdx.cs410J.InvokeMainTestCase;
import edu.pdx.cs410J.UncaughtExceptionInMain;
import edu.pdx.cs410J.web.HttpRequestHelper.RestException;
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
    void test3NoAppointmentBooksThrowsAppointmentBookRestException() {
        String owner = "Dave";
        try {
            invokeMain(Project4.class, HOSTNAME, PORT, owner);
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



        MainMethodResult result = invokeMain( Project4.class, "-host", HOSTNAME, "-port", PORT, owner, description, beginDate, beginTime, beginPeriod, endDate, endTime, endPeriod);
        assertThat(result.getTextWrittenToStandardError(), result.getExitCode(), equalTo(0));

        result = invokeMain( Project4.class, "-host", HOSTNAME, "-port", PORT, owner, description, beginDate, beginTime, beginPeriod, endDate, endTime, endPeriod);
        String out = result.getTextWrittenToStandardOut();
        assertThat(out, out, containsString(owner));
        assertThat(out, out, containsString(description));
    }
}