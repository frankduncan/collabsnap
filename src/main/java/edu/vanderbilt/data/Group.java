package edu.vanderbilt.data;

import java.util.*;

import java.util.concurrent.ConcurrentLinkedQueue;
 
public class Group implements java.io.Serializable {
  public String name;
  public Activity activity;
  public List<Participant> participants;
  public ConcurrentLinkedQueue<Message> queue;

  public Group(String name, Activity activity) {
    this.name = name;
    this.activity = activity;
    queue = new ConcurrentLinkedQueue<Message>();
    participants = new ArrayList<Participant>();
  }

  public Message getMessageFollowingRule(Rule rule) {
    Message potential = null;
    for(Message msg : queue) {
      if(msg.ruleApplies(rule)) {
        potential = msg;
      }
    }
    if(potential != null) {
      boolean successfullyRemoved = queue.remove(potential);
      return successfullyRemoved ? potential : getMessageFollowingRule(rule);
    } else {
      return null;
    }
  }

  public static class Participant implements java.io.Serializable {
  }
}
