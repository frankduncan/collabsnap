package edu.vanderbilt;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.*;

import java.util.concurrent.ConcurrentLinkedQueue;
 
public class SimpleServlet {
  static ConcurrentLinkedQueue<Message> queue = new ConcurrentLinkedQueue<Message>();

  public static class Ping extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
      queue.offer(new PingMessage());
      System.out.println("Ping Successful, size of queue is: " + queue.size());
      response.setContentType("text/html");
      response.setStatus(HttpServletResponse.SC_OK);
    }
  }

  public static class Poll extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
      System.out.println("Poll Successful, size of queue is: " + queue.size());
      response.setContentType("text/html");
      response.setStatus(HttpServletResponse.SC_OK);
      Message msg = queue.poll();
      if(msg != null) {
        response.getWriter().print(msg.getText());
      } else {
        response.getWriter().print("false");
      }
    }
  }

  public static class PostSprite extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
      StringBuffer str = new StringBuffer();
      String line = null;
      try {
        BufferedReader reader = request.getReader();
        while ((line = reader.readLine()) != null)
          str.append(line);
      } catch (Exception e) {
        e.printStackTrace();
      }
      queue.offer(new SpriteMessage(str.toString()));
      System.out.println("Sprite Post Successful, size of queue is: " + queue.size());
      response.setContentType("text/html");
      response.setStatus(HttpServletResponse.SC_OK);
    }
  }

  public interface Message {
    public abstract String getText();
  }

  public static class PingMessage implements Message {
    public String getText() { return "ping"; }
  }

  public static class SpriteMessage implements Message {
    String spriteXML;
    SpriteMessage(String spriteXML) { this.spriteXML = spriteXML; }
    public String getText() { return spriteXML; }
  }
}
