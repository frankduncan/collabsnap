package edu.vanderbilt.data;

import javax.xml.parsers.*;
import javax.xml.xpath.*;
import org.w3c.dom.*;
import java.io.*;
import java.util.*;

public interface Message<T> extends java.io.Serializable {
  public String getText();
  public boolean ruleApplies(T rule);

  public static class PingMessage implements Message<Rule.DocumentRule> {
    public String getText() { return "<ping />"; }
    public boolean ruleApplies(Rule.DocumentRule rule) { return true; }
  }

  public static class SpriteMessage implements Message<Rule.DocumentRule> {
    String spriteXML;
    Document spriteDocument;

    public SpriteMessage(String spriteXML, Document spriteDocument) {
      this.spriteXML = spriteXML;
      this.spriteDocument = spriteDocument;
    }
    public String getText() { return spriteXML; }
    public boolean ruleApplies(Rule.DocumentRule rule) { return rule.valid(spriteDocument); }
  }

  public static class VariableMessage implements Message<Rule.VariableRule> {
    private Integer value;
    private Map<String, Integer> variables;

    public VariableMessage(Map<String, Integer> variables) { this.variables = variables; }

    public String getText() {
      return "<variable value='" + value + "' />";
    }

    public boolean ruleApplies(Rule.VariableRule rule) {
      if(rule.valid(variables)) {
        value = rule.variableValue();
        return true;
      }
      return false;
    }
  }

  public static Message NULL = new Message() {
    public String getText() { return "<null />"; }
    public boolean ruleApplies(Object rule) { return true; }
  };
}
