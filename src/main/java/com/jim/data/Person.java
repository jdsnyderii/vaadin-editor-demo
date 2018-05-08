package com.jim.data;

public class Person {

  private String name;
  private String dob;

  public String getName() {
    return name;
  }

  public Person setName(String name) {
    this.name = name;
    return this;
  }

  public String getDob() {
    return dob;
  }

  public Person setDob(String dob) {
    this.dob = dob;
    return this;
  }

}
