package edu.vanderbilt.data;

import java.util.*;

import java.util.concurrent.ConcurrentLinkedQueue;
 
public class Group implements java.io.Serializable {
  public String name;
  public Activity activity;
  public List<Participant> participants;
  private Date lastActiveTime;
  private ConcurrentLinkedQueue<Message> queue;

  public Group(String name, Activity activity) {
    this.name = name;
    this.activity = activity;
    lastActiveTime = new Date();
    queue = new ConcurrentLinkedQueue<Message>();
    participants = new ArrayList<Participant>();
  }

  public void offer(Message msg) {
    lastActiveTime = new Date();
    queue.offer(msg);
  }

  public Message poll() {
    lastActiveTime = new Date();
    return queue.poll();
  }

  public int size() {
    lastActiveTime = new Date();
    return queue.size();
  }

  public boolean stillActive() {
    return new Date().getTime() < (lastActiveTime.getTime() + 24L * 60L * 60L * 1000L);
  }

  public Message getMessageFollowingRule(Rule rule) {
    lastActiveTime = new Date();
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
      return Message.NULL;
    }
  }

  public static class Participant implements java.io.Serializable {
  }
}
