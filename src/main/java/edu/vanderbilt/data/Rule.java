package edu.vanderbilt.data;

import javax.xml.parsers.*;
import javax.xml.xpath.*;
import org.w3c.dom.*;

public interface Rule {
  public void load(Node node);
  public boolean valid(Document sprite);

  public static class VariableName implements Rule {
    private XPathExpression xpath;

    public void load(Node node) {
      Element ele = (Element)node;
      String name = ele.getAttribute("name");
      String val = ele.getAttribute("value");
      try {
        this.xpath = XPathFactory.newInstance().newXPath().compile("/sprite/variables/variable[@name='" + name + "']/l/text() = '" + val + "'");
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
  }
}
