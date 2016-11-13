package edu.vanderbilt.data;

import javax.xml.parsers.*;
import javax.xml.xpath.*;
import org.w3c.dom.*;

public abstract class Rule {
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

  public static class VariableName extends Rule {
    protected String getXPath(Node node) {
      Element ele = (Element)node;
      String name = ele.getAttribute("name");
      String val = ele.getAttribute("value");
      return "/sprite/variables/variable[@name='" + name + "']/l/text() = '" + val + "'";
    }
  }

  public static class MessageKey extends Rule {
    protected String getXPath(Node node) {
      Element ele = (Element)node;
      String key = ele.getAttribute("key");
      return "/message/key/text() = '" + key + "'";
    }
  }
}
