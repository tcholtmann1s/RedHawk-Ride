package com.example.project;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import org.junit.jupiter.api.Test;

public class StudentTest {

  @Test
  void loginFailure() throws IOException, NoSuchAlgorithmException {
    assertEquals(false, new Student("Test", "Test").login());
  }

  @Test
  void loginSuccess() throws IOException, NoSuchAlgorithmException {
    assertEquals(true, new Student("zllocke1s", "locke").login());
  }

  @Test
  void loginFail() throws IOException, NoSuchAlgorithmException {
    assertEquals(false, new Student("zllocke1s", "lock").login());
  }

  @Test
  void getUsernameSuccess() throws IOException, NoSuchAlgorithmException {
    assertEquals("zllocke1s", new Student("zllocke1s", "locke").getUsername());
  }

  @Test
  void validUserSuccess() throws IOException, NoSuchAlgorithmException {
    assertEquals(true, Student.verifyUser("S00000000"));
  }

  @Test
  void validUserFail() throws IOException, NoSuchAlgorithmException {
    assertEquals(false, new Student("zllocke1s", "locke").verifyUser("S01984483"));
  }
}
