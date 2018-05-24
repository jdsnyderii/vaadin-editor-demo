package com.jim.data;

import com.vaadin.server.ExternalResource;
import com.vaadin.ui.Link;

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

  public Link getLink() {
    return new Link("Linked In", new ExternalResource("https://media.bizj.us/view/archive/austin/user_media/Jim-Snyder-935781*220.jpg"));
  }

  public String getPicture() {
    return "https://picsum.photos/40";
  }

  public String getDob() {
    return dob;
  }

  public Person setDob(String dob) {
    this.dob = dob;
    return this;
  }


}
