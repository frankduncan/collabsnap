package edu.vanderbilt.data;

import javax.xml.parsers.*;
import javax.xml.xpath.*;
import org.w3c.dom.*;
import java.io.*;

public abstract class Message implements java.io.Serializable {
  public abstract String getText();
  public abstract String getContentType();
  public abstract boolean ruleApplies(Rule rule);

  public static class PingMessage extends Message {
    public String getText() { return "ping"; }
    public String getContentType() { return "text/html"; }
    public boolean ruleApplies(Rule rule) { return true; }
  }

  public static class SpriteMessage extends Message {
    String spriteXML;
    Document spriteDocument;

    public SpriteMessage(DocumentBuilder db, String spriteXML) {
      this.spriteXML = spriteXML;
      try {
        this.spriteDocument = db.parse(new ByteArrayInputStream(spriteXML.getBytes()));
      } catch(Exception e) {
        e.printStackTrace();
      }
    }
    public String getText() { return spriteXML; }
    public String getContentType() { return "application/xml"; }
    public boolean ruleApplies(Rule rule) { return rule.valid(spriteDocument); }
  }
}
