package edu.vanderbilt;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.util.concurrent.ConcurrentLinkedQueue;
 
public class SimpleServlet {
  static ConcurrentLinkedQueue<Message> queue = new ConcurrentLinkedQueue<Message>();

  public static class Ping extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
      queue.offer(new Message());
      System.out.println("Ping Successful, size of queue is: " + queue.size());
      response.setContentType("text/html");
      response.setStatus(HttpServletResponse.SC_OK);
    }
  }

  public static class Poll extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
      queue.poll();
      System.out.println("Poll Successful, size of queue is: " + queue.size());
      response.setContentType("text/html");
      response.setStatus(HttpServletResponse.SC_OK);
    }
  }

  public static class Message {
  }
}
