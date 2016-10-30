package edu.vanderbilt.data;

import java.util.*;

public class Group implements java.io.Serializable {
  public String name;
  public Activity activity;
  public List<Participant> participants;

  public Group(String name, Activity activity) {
    this.name = name;
    this.activity = activity;
    participants = new ArrayList<Participant>();
  }

  public static class Participant implements java.io.Serializable {
  }
}
