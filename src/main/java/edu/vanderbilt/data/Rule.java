package edu.vanderbilt.data;

import javax.xml.parsers.*;
import javax.xml.xpath.*;
import org.w3c.dom.*;
import java.util.*;

public interface Rule<T> {
  public boolean valid(T t);
  public void load(Node node);

  public abstract class DocumentRule implements Rule<Document> {
    private XPathExpression xpath;
    public void load(Node node) {
      Element ele = (Element)node;
      String name = ele.getAttribute("key");
      try {
        this.xpath = XPathFactory.newInstance().newXPath().compile(getXPath(node));
      } catch(Exception e) {
        e.printStackTrace();
      }
    }

    public boolean valid(Document sprite) {
      try {
        return this.xpath.evaluate(sprite).equals("true");
      } catch(Exception e) {
        e.printStackTrace();
      }
      return false;
    }

    protected abstract String getXPath(Node node);
  }

  public static class VariableName extends DocumentRule {
    protected String getXPath(Node node) {
      Element ele = (Element)node;
      String name = ele.getAttribute("name");
      String val = ele.getAttribute("value");
      return "/sprite/variables/variable[@name='" + name + "']/l/text() = '" + val + "'";
    }
  }

  public static class MessageKey extends DocumentRule {
    protected String getXPath(Node node) {
      Element ele = (Element)node;
      String key = ele.getAttribute("key");
      return "/message/key/text() = '" + key + "'";
    }
  }

  // This is kind of shoe horned into it all, but... prototype
  public interface VariableRule extends Rule<Map<String, Integer>> {
    public Integer variableValue();
  }

  public static class GetVariable implements VariableRule {
    String name;
    Integer val;
    public boolean valid(Map<String, Integer> map) {
      if(map.containsKey(name)) {
        val = map.get(name);
        return true;
      }
      return false;
    }
    public Integer variableValue() { return val; }
    public void load(Node node) {
      name = ((Element)node).getAttribute("name");
    }
  }
}
