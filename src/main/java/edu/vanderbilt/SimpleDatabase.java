package edu.vanderbilt;

import edu.vanderbilt.data.*;

import java.util.*;

public class SimpleDatabase {
  private static SimpleDatabase instance = null;
  private static final String FILE_NAME = "collabsnapdump.out";

  private State state;

  public static List<Activity> getActivities() {
    return instance().state.activities;
  }

  public static List<Group> getGroups() {
    return instance().state.groups;
  }

  // This is so a race condition
  public static void addActivity(String name) {
    if(getActivity(name) == null) {
      Activity activity = new Activity();
      activity.name = name;
      getActivities().add(activity);
    }
  }

  public static Activity getActivity(String name) {
    for(Activity activity : getActivities()) {
      if(activity.name.equals(name)) { return activity; }
    }
    return null;
  }

  public static Group getGroup(String name, Activity activity) {
    for(Group group : getGroups()) {
      if(group.name.equals(name) && group.activity.name.equals(activity.name)) { return group; }
    }
    return null;
  }

  public static void addGroup(String name, Activity activity) {
    if(getGroup(name, activity) == null) {
      Group group = new Group(name, activity);
      getGroups().add(group);
    }
  }

  private SimpleDatabase() {
    if(!new java.io.File(FILE_NAME).exists()) {
      state = new State();
    } else {
      try {
        java.io.ObjectInputStream ois = new java.io.ObjectInputStream(new java.io.FileInputStream(FILE_NAME));
        state = (State)ois.readObject();
        ois.close();
      } catch(Exception e) {
        e.printStackTrace();
        // In the case of failure here, we just reset the server to brand new.
        // That means if deserialization fails, we don't really care, and count on
        // other means to be backing up the data.
        state = new State();
      }
    }
  }

  private static void createInstance() {
    instance = new SimpleDatabase();
    new Thread(new Save()).start();
  }

  private static SimpleDatabase instance() {
    if(instance == null) {
      synchronized(SimpleDatabase.class) {
        if(instance == null) {
          createInstance();
        }
      }
    }
    return instance;
  }

  private static class Save implements Runnable {
    public void run() {
      try {
        Thread.sleep(5 * 60 * 1000);
        java.io.ObjectOutputStream oos = new java.io.ObjectOutputStream(new java.io.FileOutputStream(FILE_NAME));
        oos.writeObject(instance().state);
        oos.close();
      } catch(Exception e) {
        System.err.println("While saving, something very bad happened");
        e.printStackTrace();
      }
    }
  }

  private static class State implements java.io.Serializable {
    private List<Activity> activities;
    private List<Group> groups;

    private State() {
      activities = new ArrayList<Activity>();
      groups = new ArrayList<Group>();
    }
  }
}
