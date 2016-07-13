package edu.vanderbilt;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.*;

import javax.xml.parsers.*;
import javax.xml.xpath.*;
import org.w3c.dom.*;

import java.util.concurrent.ConcurrentLinkedQueue;
 
public class SimpleServlet {
  static ConcurrentLinkedQueue<Message> queue = new ConcurrentLinkedQueue<Message>();
  static DocumentBuilderFactory dbf;
  static DocumentBuilder db;
  static {{
    try {
      dbf = DocumentBuilderFactory.newInstance();
      db = dbf.newDocumentBuilder();
    } catch(Exception e) {
      e.printStackTrace();
    }
  } }

  public static class Ping extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
      queue.offer(new PingMessage());
      System.out.println("Ping Successful, size of queue is: " + queue.size());
      response.setContentType("text/html");
      response.setStatus(HttpServletResponse.SC_OK);
    }
  }

  public static class Poll extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
      System.out.println("Poll Successful, size of queue is: " + queue.size());
      StringBuffer str = new StringBuffer();
      String line = null;
      try {
        BufferedReader reader = request.getReader();
        while((line = reader.readLine()) != null)
          str.append(line);
      } catch(Exception e) {
        e.printStackTrace();
      }
      Message msg = null;
      try {
        Document doc = db.parse(new ByteArrayInputStream(str.toString().getBytes()));
        if(doc.getDocumentElement().getFirstChild() != null) {
          Rule rule = getRule(doc.getDocumentElement().getFirstChild());
          msg = getMessageFollowingRule(rule);
        } else {
          msg = queue.poll();
        }
      } catch(Exception e) {
        e.printStackTrace();
        // Prototypes mean no error handling, yay!
      }

      if(msg != null) {
        response.setContentType(msg.getContentType());
        response.setStatus(HttpServletResponse.SC_OK);
        response.getWriter().print(msg.getText());
      } else {
        response.setContentType("text/html");
        response.setStatus(HttpServletResponse.SC_OK);
        response.getWriter().print("false");
      }
    }
  }

  public static Message getMessageFollowingRule(Rule rule) {
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

  private interface Rule {
    public void load(Node node);
    public boolean valid(Document sprite);
  }

  private static class VariableName implements Rule {
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

  private static Rule getRule(Node node) {
    Rule rule = null;
    if(node.getNodeName().equals("variable-equal")) {
      rule = new VariableName();
    } else {
      throw new RuntimeException("Don't know node type: " + node.getNodeName());
    }
    rule.load(node);
    return rule;
  }

  public static class PostSprite extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
      StringBuffer str = new StringBuffer();
      String line = null;
      try {
        BufferedReader reader = request.getReader();
        while((line = reader.readLine()) != null)
          str.append(line);
      } catch(Exception e) {
        e.printStackTrace();
      }
      queue.offer(new SpriteMessage(str.toString()));
      System.out.println(str.toString());
      System.out.println("Sprite Post Successful, size of queue is: " + queue.size());
      response.setContentType("text/html");
      response.setStatus(HttpServletResponse.SC_OK);
    }
  }

  public interface Message {
    public abstract String getText();
    public String getContentType();
    public boolean ruleApplies(Rule rule);
  }

  public static class PingMessage implements Message {
    public String getText() { return "ping"; }
    public String getContentType() { return "text/html"; }
    public boolean ruleApplies(Rule rule) { return true; }
  }

  public static class SpriteMessage implements Message {
    String spriteXML;
    Document spriteDocument;
    SpriteMessage(String spriteXML) {
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
