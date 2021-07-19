package edu.pdx.cs410J.greencod;

import edu.pdx.cs410J.InvokeMainTestCase;
import org.junit.jupiter.api.Test;

import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

/**
 * Integration tests for the {@link Project3} main class.
 */
class Project3IT extends InvokeMainTestCase {


  /**
   * Invokes the main method of {@link Project3} with the given arguments.
   */
  private MainMethodResult invokeMain(String... args) {
    return invokeMain( Project3.class, args );
  }


  /**
   * Tests that invoking the main method with no arguments issues an error
   */
  @Test
  void testNoCommandLineArguments() {
    MainMethodResult result = invokeMain();
    assertThat(result.getExitCode(), equalTo(1));
    assertThat(result.getTextWrittenToStandardError(), containsString(Project3.MISSING_COMMAND_LINE_ARGUMENTS));
  }
  /**
   * Tests that invoking the main method with no arguments issues an error and shows usage message
   */
  @Test
  void testNoCommandArgsPrintsMissingArgs(){
    MainMethodResult result = invokeMain();
    assertThat(result.getTextWrittenToStandardError(), containsString(Project3.MISSING_COMMAND_LINE_ARGUMENTS));
    assertThat(result.getTextWrittenToStandardError(), containsString(Project3.USAGE_MESSAGE));
    assertThat(result.getExitCode(), equalTo(1));
  }
  /**
   * Tests that invoking the main method with no description issues error
   */
  @Test
  void testMissingDescription() {
    MainMethodResult result = invokeMain(Project3.class, "Cody");
    assertThat(result.getTextWrittenToStandardError(), containsString(Project3.MISSING_DESCRIPTION));
    assertThat(result.getExitCode(), equalTo(1));
  }
  /**
   * Tests that invoking the main method with no begin date issues error
   */
  @Test
  void testMissingBeginDate() {
    MainMethodResult result = invokeMain(Project3.class, "Cody", "Head Transplant Consultation");
    assertThat(result.getTextWrittenToStandardError(), containsString(Project3.MISSING_BEGIN_DATE));
    assertThat(result.getExitCode(), equalTo(1));
  }
  /**
   * Tests that invoking the main method with no begin time issues error
   */
  @Test
  void testMissingBeginTime() {
    MainMethodResult result = invokeMain(Project3.class, "Cody", "Head Transplant Consultation", "07/21/3000");
    assertThat(result.getTextWrittenToStandardError(), containsString(Project3.MISSING_BEGIN_TIME));
    assertThat(result.getExitCode(), equalTo(1));
  }
  /**
   * Tests that invoking the main method with no begin time period issues error
   */
  @Test
  void testMissingBeginTimePeriod() {
    MainMethodResult result = invokeMain(Project3.class, "Cody", "Head Transplant Consultation", "07/21/3000", "11:11");
    assertThat(result.getTextWrittenToStandardError(), containsString(Project3.MISSING_BEGIN_TIME));
    assertThat(result.getExitCode(), equalTo(1));
  }
  /**
   * Tests that invoking the main method with no end date issues error
   */
  @Test
  void testMissingEndDate() {
    MainMethodResult result = invokeMain(Project3.class, "Cody", "Head Transplant Consultation","07/21/3000", "11:11", "pm");
    assertThat(result.getTextWrittenToStandardError(), containsString(Project3.MISSING_END_DATE));
    assertThat(result.getExitCode(), equalTo(1));
  }
  /**
   * Tests that invoking the main method with no end time issues error
   */
  @Test
  void testMissingEndTime() {
    MainMethodResult result = invokeMain(Project3.class, "Cody", "Head Transplant Consultation","07/21/3000", "11:11", "am", "07/21/1992");
    assertThat(result.getTextWrittenToStandardError(), containsString(Project3.MISSING_END_TIME));
    assertThat(result.getExitCode(), equalTo(1));
  }
  /**
   * Tests that invoking the main method with no end time issues error
   */
  @Test
  void testMissingEndTimePeriod() {
    MainMethodResult result = invokeMain(Project3.class, "Cody", "Head Transplant Consultation","07/21/3000", "11:11", "pm", "07/21/1992", "11:12");
    assertThat(result.getTextWrittenToStandardError(), containsString(Project3.MISSING_END_TIME));
    assertThat(result.getExitCode(), equalTo(1));
  }
  /**
   * Tests that invoking the main method with a begin time out of range issues error
   */
  @Test
  void testBeginTimeInRange() {
    MainMethodResult result = invokeMain(Project3.class, "Cody", "Head Transplant Consultation","07/21/3000", "11:99", "am", "07/21/1992", "11:11", "pm");
    assertThat(result.getTextWrittenToStandardError(), containsString(Project3.TIME_NOT_CORRECT));
    assertThat(result.getExitCode(), equalTo(1));
  }
  /**
   * Tests that invoking the main method with an end time out of range issues error
   */
  @Test
  void testEndTimeInRange() {
    MainMethodResult result = invokeMain(Project3.class, "Cody", "Head Transplant Consultation","07/21/3000", "11:00", "am", "07/21/1992", "11:99", "pm");
    assertThat(result.getTextWrittenToStandardError(), containsString(Project3.TIME_NOT_CORRECT));
    assertThat(result.getExitCode(), equalTo(1));
  }
  /**
   * Tests that invoking the main method with an end time using characters fails
   */
  @Test
  void testTimeIsNumber() {
    MainMethodResult result = invokeMain(Project3.class, "Cody", "Head Transplant Consultation","07/21/3000", "11:xx", "am", "07/21/1992", "11:99", "pm");
    assertThat(result.getTextWrittenToStandardError(), containsString(Project3.TIME_NOT_CORRECT));
    assertThat(result.getExitCode(), equalTo(1));
  }
  /**
   * Tests that invoking the main method with a month out of range issues error
   */
  @Test
  void testDateMonthInRange() {
    MainMethodResult result = invokeMain(Project3.class, "Cody", "Head Transplant Consultation","15/30/3000", "11:00", "am", "07/21/1992", "11:11", "pm");
    assertThat(result.getTextWrittenToStandardError(), containsString(Project3.DATE_NOT_CORRECT));
    assertThat(result.getExitCode(), equalTo(1));
  }
  /**
   * Tests that invoking the main method with a day out of range issues error
   */
  @Test
  void testDateDayInRange() {
    MainMethodResult result = invokeMain(Project3.class, "Cody", "Head Transplant Consultation","12/32/3000", "11:00", "am", "07/21/1992", "11:11", "pm");
    assertThat(result.getTextWrittenToStandardError(), containsString(Project3.DATE_NOT_CORRECT));
    assertThat(result.getExitCode(), equalTo(1));
  }
  /**
   * Tests that invoking the main method with a year out of range issues error
   */
  @Test
  void testDateYearInRange() {
    MainMethodResult result = invokeMain(Project3.class, "Cody", "Head Transplant Consultation","12/31/300", "11:00", "am", "07/21/1992", "11:11", "pm");
    assertThat(result.getTextWrittenToStandardError(), containsString(Project3.DATE_NOT_CORRECT));
    assertThat(result.getExitCode(), equalTo(1));
  }
  /**
   * Tests that invoking the main method with a character issues error
   */
  @Test
  void testDateIsNums() {
    MainMethodResult result = invokeMain(Project3.class, "Cody", "Head Transplant Consultation", "12/31/CODY", "11:00", "am", "07/21/1992", "11:11", "pm");
    assertThat(result.getTextWrittenToStandardError(), containsString(Project3.DATE_NOT_CORRECT));
    assertThat(result.getExitCode(), equalTo(1));
  }
  /**
   * Tests that invoking the main method with too many args issues error
   */
  @Test
  void testTooManyArgs() {
    MainMethodResult result = invokeMain(Project3.class, "Cody", "Head Transplant Consultation","12/31/1992", "11:00", "am", "07/21/1992", "11:11", "pm", "arg");
    assertThat(result.getTextWrittenToStandardError(), containsString(Project3.TOO_MANY_ARGUMENTS));
    assertThat(result.getExitCode(), equalTo(1));
  }
  /**
   * Tests that -print works
   */
  @Test
  void testPrint() {
    MainMethodResult result = invokeMain(Project3.class, "-print", "Cody", "Head Transplant Consultation","12/31/3000", "11:00", "am", "07/21/1992", "11:11", "pm");
    assertThat(result.getTextWrittenToStandardOut(), containsString(("Head Transplant Consultation from 12/31/00, 11:00 AM until 7/21/92, 11:11 PM")));
  }
  @Test
  void testReadMe() {
    MainMethodResult result = invokeMain(Project3.class, "-README", "Cody", "Head Transplant Consultation","12/31/3000", "11:00", "am", "07/21/1992", "11:11", "pm");
    assertThat(result.getTextWrittenToStandardOut(), containsString("Readme for project 2. This project pulls in an appointment, and either creates or adds to an existing appointment book to the file you specify."));
  }
  /**
   * Tests that invoking the main method with unknown arguments issues an error and shows usage message
   */
  @Test
  void testUnknownArgsPrintsUnknownArgs(){
    MainMethodResult result = invokeMain(Project3.class, "-print", "-Eliza", "Cody", "Head Transplant Consultation","12/31/3000", "11:00", "am", "07/21/1992", "11:11", "pm");
    assertThat(result.getTextWrittenToStandardError(), containsString(Project3.UNKNOWN_COMMAND_LINE_ARGUMENT));
    assertThat(result.getTextWrittenToStandardError(), containsString(Project3.USAGE_MESSAGE));
    assertThat(result.getExitCode(), equalTo(1));
  }
  /**
   * Tests that -textFile works when given not real file name **FIRST OF THREE TESTS DONE IN ORDER**
   */
  @Test
  void testFakeFileWriter() {
    MainMethodResult result = invokeMain(Project3.class, "-print", "-textFile", "Cody", "Cody", "Head Transplant Consultation","12/31/3000", "11:00", "am", "07/21/1992", "11:11", "pm");
    assertThat(result.getTextWrittenToStandardOut(), containsString(("Head Transplant Consultation from 12/31/00, 11:00 AM until 7/21/92, 11:11 PM")));
  }
  /**
   * Tests that -textFile works when given not real file name **SECOND OF THREE TESTS DONE IN ORDER**
   */
  @Test
  void testFakeFileWriterAgain() {
    MainMethodResult result = invokeMain(Project3.class, "-print", "-textFile", "Cody", "Cody", "Head Transplant Consultation","12/31/3000", "11:00", "am", "07/21/1992", "11:11", "pm");
    assertThat(result.getTextWrittenToStandardOut(), containsString("Head Transplant Consultation from 12/31/00, 11:00 AM until 7/21/92, 11:11 PM"));
  }
  /**
   * Tests that -textFile works when given not real file name **THIRD OF THREE TESTS DONE IN ORDER**
   */
  @Test
  void testFakeFileWriterWrongOwner() {
    MainMethodResult result = invokeMain(Project3.class, "-print", "-textFile", "Cody", "Not Cody", "Head Transplant Consultation","12/31/3000", "11:00", "am", "07/21/1992", "11:11", "pm");
    assertThat(result.getTextWrittenToStandardError(), containsString(Project3.OWNERS_DONT_MATCH));
  }


}