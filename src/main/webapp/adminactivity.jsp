<%
  String name = request.getParameter("activityname");
  if(name != null) {
    edu.vanderbilt.SimpleDatabase.addActivity(name);
  }

  edu.vanderbilt.data.Activity activity = edu.vanderbilt.SimpleDatabase.getActivity(name);
/*  System.out.println(name);
/
/
/  if(request.getParameter("rolename") != null) {
/    out.println(request.getParameter("rolename"));
/    out.println(request.getParameter("snapfile"));
/  }*/
%>

<html>
  <body>
    <h1>Administer <%=name%></h1>
    <h2>Roles</h2>

    <% java.util.List<edu.vanderbilt.data.Activity.Role> roles = activity.getRoles(); %>
    <table>
      <% for (edu.vanderbilt.data.Activity.Role role : roles) { %>
        <tr>
          <td><%=role.name%></td>
          <td><a href="downloadrole/<%=name%>/<%=role.name%>.xml">Snap File</a></td>
          <td><a href="deleterole?activityname=<%=name%>&rolename=<%=role.name%>">Delete</a></td>
        </tr>
      <% } %>
    </table>

    <h3>Add Role</h3>
    <form action="addrole" method="post" enctype="multipart/form-data">
      <input type="hidden" name="activityname" value="<%=name%>">
      <input name="rolename">
      <input type="file" name="snapfile">
      <input type="submit" value="Add">
    </form>

  </body>
</html>
