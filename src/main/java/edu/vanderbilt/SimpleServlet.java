package edu.vanderbilt;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.*;
import java.util.*;

import edu.vanderbilt.data.Group;
import edu.vanderbilt.data.Message;
import edu.vanderbilt.data.Message.PingMessage;
import edu.vanderbilt.data.Message.SpriteMessage;
import edu.vanderbilt.data.Rule;
import edu.vanderbilt.data.Rule.VariableName;

import org.apache.commons.fileupload.*;
import org.apache.commons.fileupload.servlet.*;
import org.apache.commons.fileupload.disk.*;
import org.apache.commons.io.*;

import javax.xml.parsers.*;
import javax.xml.xpath.*;
import org.w3c.dom.*;

public class SimpleServlet {
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

  private static Group determineGroup(HttpServletRequest request) {
    String[] spliturl = request.getRequestURI().split("/",5);
    String activityName = spliturl[3];
    String groupName = spliturl[4];

    Group retn = SimpleDatabase.getGroup(groupName, SimpleDatabase.getActivity(activityName));

    if(retn == null) {
      SimpleDatabase.addGroup(groupName, SimpleDatabase.getActivity(activityName));
      return SimpleDatabase.getGroup(groupName, SimpleDatabase.getActivity(activityName));
    } else {
      return retn;
    }
  }

  public static class Ping extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
      Group group = determineGroup(request);
      group.offer(new PingMessage());
      System.out.println("Ping Successful, size of " + group.name + " / " + group.activity.name + " queue is: " + group.size());
      response.setContentType("text/html");
      response.setStatus(HttpServletResponse.SC_OK);
    }
  }

  public static class Poll extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
      Group group = determineGroup(request);
      System.out.println("Poll Successful, size of " + group.name + " / " + group.activity.name + " queue is: " + group.size());
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
          msg = group.getMessageFollowingRule(rule);
        } else {
          msg = group.poll();
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
      Group group = determineGroup(request);
      group.offer(new SpriteMessage(db, str.toString()));
      System.out.println(str.toString());
      System.out.println("Sprite Post Successful, size of " + group.name + " / " + group.activity.name + " queue is: " + group.size());
      response.setContentType("text/html");
      response.setStatus(HttpServletResponse.SC_OK);
    }
  }


  // This should be rewritten to use servlet api 3.0+ when moved to a real container
  public static class AddRole extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
      String activityName = null;
      String roleName = null;
      String snapFile = null;
      try {
        List<FileItem> items = new ServletFileUpload(new DiskFileItemFactory()).parseRequest(request);
        for (FileItem item : items) {
          if (item.isFormField()) {
            if(item.getFieldName().equals("rolename")) { roleName = item.getString(); }
            if(item.getFieldName().equals("activityname")) { activityName = item.getString(); }
          } else {
            // Assumptions abound
            snapFile = IOUtils.toString(item.getInputStream(), "UTF-8");
          }
        }
      } catch (FileUploadException e) {
        e.printStackTrace();
      }

      SimpleDatabase.getActivity(activityName).addRole(roleName, snapFile);

      response.sendRedirect("adminactivity.jsp?activityname=" + activityName);
    }
  }

  public static class LoadActivity extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
      String[] spliturl = request.getRequestURI().split("/",4);
      String activityName = spliturl[2];
      String groupName = spliturl[3];
      String roleName = request.getParameter("role");

      SimpleDatabase.addGroup(groupName, SimpleDatabase.getActivity(activityName));

      request.setAttribute("activityName", activityName);
      request.setAttribute("groupName", groupName);
      request.setAttribute("roleName", roleName);

      request.getRequestDispatcher("/snap.jsp").forward(request, response);
    }
  }

  public static class DownloadRole extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
      String[] spliturl = request.getRequestURI().split("/",4);
      String activityName = spliturl[2];
      String roleNameXml = spliturl[3];
      String roleName = roleNameXml.substring(0, roleNameXml.length() - 4);

      response.setHeader("Content-Disposition", "attachment; filename=\"" + roleNameXml + "\"");
      response.getOutputStream().write(SimpleDatabase.getActivity(activityName).getRole(roleName).snapFile.getBytes());
      response.getOutputStream().flush();
      response.getOutputStream().close();
    }
  }

  public static class DeleteRole extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
      String activityName = request.getParameter("activityname");
      String roleName = request.getParameter("rolename");

      SimpleDatabase.getActivity(activityName).removeRole(roleName);

      response.sendRedirect("adminactivity.jsp?activityname=" + activityName);
    }
  }

  public static class JoinGroup extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
      String groupName = request.getParameter("groupname");
      String activityName = request.getParameter("activityname");
      String roleName = request.getParameter("rolename");

      response.sendRedirect("/activities/" + activityName + "/" + groupName + "?role=" + roleName);
    }
  }
}
