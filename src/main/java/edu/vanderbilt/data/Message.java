package edu.vanderbilt.data;

import javax.xml.parsers.*;
import javax.xml.xpath.*;
import org.w3c.dom.*;
import java.io.*;

public abstract class Message implements java.io.Serializable {
  public abstract String getText();
  public abstract boolean ruleApplies(Rule rule);

  public static class PingMessage extends Message {
    public String getText() { return "<ping />"; }
    public boolean ruleApplies(Rule rule) { return true; }
  }

  public static class SpriteMessage extends Message {
    String spriteXML;
    Document spriteDocument;

    public SpriteMessage(String spriteXML, Document spriteDocument) {
      this.spriteXML = spriteXML;
      this.spriteDocument = spriteDocument;
    }
    public String getText() { return spriteXML; }
    public boolean ruleApplies(Rule rule) { return rule.valid(spriteDocument); }
  }

  public static Message NULL = new Message() {
    public String getText() { return "<null />"; }
    public boolean ruleApplies(Rule rule) { return true; }
  };
}
