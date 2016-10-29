package edu.vanderbilt;

import edu.vanderbilt.data.Activity;

import java.util.*;

public class SimpleDatabase {
  private static SimpleDatabase instance = null;
  private static final String FILE_NAME = "collabsnapdump.out";

  private List<Activity> activities;

  public static List<Activity> getActivities() {
    return instance().activities;
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

  private SimpleDatabase() {
    if(!new java.io.File(FILE_NAME).exists()) {
      activities = new ArrayList<Activity>();
    } else {
      try {
        java.io.ObjectInputStream ois = new java.io.ObjectInputStream(new java.io.FileInputStream(FILE_NAME));
        activities = (List<Activity>)ois.readObject();
        ois.close();
      } catch(Exception e) {
        e.printStackTrace();
        // In the case of failure here, we just reset the server to brand new.
        // That means if deserialization fails, we don't really care, and count on
        // other means to be backing up the data.
        activities = new ArrayList<Activity>();
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
        oos.writeObject(instance.activities);
        oos.close();
      } catch(Exception e) {
        System.err.println("While saving, something very bad happened");
        e.printStackTrace();
      }
    }
  }
}
