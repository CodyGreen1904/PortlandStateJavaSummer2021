package edu.pdx.cs410J.greencod;

import edu.pdx.cs410J.InvokeMainTestCase;
import org.junit.jupiter.api.Test;

import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

/**
 * Integration tests for the {@link Project1} main class.
 */
class Project1IT extends InvokeMainTestCase {


  /**
   * Invokes the main method of {@link Project1} with the given arguments.
   */
  private MainMethodResult invokeMain(String... args) {
    return invokeMain( Project1.class, args );
  }


  /**
   * Tests that invoking the main method with no arguments issues an error
   */
  @Test
  void testNoCommandLineArguments() {
    MainMethodResult result = invokeMain();
    assertThat(result.getExitCode(), equalTo(1));
    assertThat(result.getTextWrittenToStandardError(), containsString(Project1.MISSING_COMMAND_LINE_ARGUMENTS));
  }
  @Test
  void testNoCommandArgsPrintsMissingArgs(){
    MainMethodResult result = invokeMain();
    assertThat(result.getTextWrittenToStandardError(), containsString(Project1.MISSING_COMMAND_LINE_ARGUMENTS));
    assertThat(result.getTextWrittenToStandardError(), containsString(Project1.USAGE_MESSAGE));
    assertThat(result.getExitCode(), equalTo(1));
  }

  @Test
  void testMissingDescription() {
    MainMethodResult result = invokeMain(Project1.class, "Cody");
    assertThat(result.getTextWrittenToStandardError(), containsString(Project1.MISSING_DESCRIPTION));
    assertThat(result.getExitCode(), equalTo(1));
  }

  @Test
  void testMissingBeginDate() {
    MainMethodResult result = invokeMain(Project1.class, "Cody", "Head Transplant Consultation");
    assertThat(result.getTextWrittenToStandardError(), containsString(Project1.MISSING_BEGIN_DATE));
    assertThat(result.getExitCode(), equalTo(1));
  }

  @Test
  void testMissingBeginTime() {
    MainMethodResult result = invokeMain(Project1.class, "Cody", "Head Transplant Consultation", "07/21/3000");
    assertThat(result.getTextWrittenToStandardError(), containsString(Project1.MISSING_BEGIN_TIME));
    assertThat(result.getExitCode(), equalTo(1));
  }

  @Test
  void testMissingEndDate() {
    MainMethodResult result = invokeMain(Project1.class, "Cody", "Head Transplant Consultation","07/21/3000", "11:11");
    assertThat(result.getTextWrittenToStandardError(), containsString(Project1.MISSING_END_DATE));
    assertThat(result.getExitCode(), equalTo(1));
  }

  @Test
  void testMissingEndTime() {
    MainMethodResult result = invokeMain(Project1.class, "Cody", "Head Transplant Consultation","07/21/3000", "11:11", "07/21/1992");
    assertThat(result.getTextWrittenToStandardError(), containsString(Project1.MISSING_END_TIME));
    assertThat(result.getExitCode(), equalTo(1));
  }


}