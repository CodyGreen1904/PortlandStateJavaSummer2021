package edu.pdx.cs410J.greencod;

import edu.pdx.cs410J.InvokeMainTestCase;
import org.junit.jupiter.api.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.hamcrest.core.StringContains.containsString;

/**
 * Integration tests for the <code>Student</code> class's main method.
 * These tests extend <code>InvokeMainTestCase</code> which allows them
 * to easily invoke the <code>main</code> method of <code>Student</code>.
 */
class StudentIT extends InvokeMainTestCase {



  @Test
  void invokingMainWithNoArgumentsHasExitCodeOf1() {
    InvokeMainTestCase.MainMethodResult result = invokeMain(Student.class);
    assertThat(result.getExitCode(), equalTo(1));
  }

  @Test
  void invokingMainWithNoArgumentsPrintsMissingArgumentsToStandardError() {
    InvokeMainTestCase.MainMethodResult result = invokeMain(Student.class);
    assertThat(result.getTextWrittenToStandardError(), containsString(Student.MISSING_COMMAND_LINE_ARGUMENTS));
    assertThat(result.getTextWrittenToStandardError(), containsString(Student.USAGE_MESSAGE));
  }

  @Test
  void missingGender() {
    InvokeMainTestCase.MainMethodResult result = invokeMain(Student.class, "Dave");
    assertThat(result.getTextWrittenToStandardError(), containsString(Student.MISSING_GENDER));
    assertThat(result.getExitCode(), equalTo(1));
  }

  @Test
  void unrecognizeGender() {
    MainMethodResult result = invokeMain(Student.class, "Dave", "3.45");
    assertThat(result.getTextWrittenToStandardError(), containsString("Unrecognized Gender: \"3.45\""));
    assertThat(result.getExitCode(), equalTo(1));
  }

}
