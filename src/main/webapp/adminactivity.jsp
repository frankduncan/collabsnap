<%
  String name = request.getParameter("activityname");
  if(name != null) {
    edu.vanderbilt.SimpleDatabase.addActivity(name);
  }

  edu.vanderbilt.data.Activity activity = edu.vanderbilt.SimpleDatabase.getActivity(name);
%>

<html>
  <body>
    <h1>Administer <%=name%></h1>
    <h2>Roles</h2>

    <% java.util.List<edu.vanderbilt.data.Activity.Role> roles = activity.getRoles(); %>
    <ul>
      <% for (edu.vanderbilt.data.Activity.Role role : roles) { %>
        <li>
        <%=role.name%>
        (<% if(role.snapFile != null) { %><a href="downloadrole/<%=name%>/<%=role.name%>.xml">Download Snap File</a><% } else {%>No File<% } %>):
        <form action="addrole" method="post" enctype="multipart/form-data" style="display:inline">
          <input type="hidden" name="activityname" value="<%=name%>">
          <input type="hidden" name="rolename" value="<%=role.name%>">
          <input type="file" name="snapfile">
          <input type="submit" value="Overwrite Snap File">
        </form>
        |
        <a href="deleterole?activityname=<%=name%>&rolename=<%=role.name%>">Delete</a>
      <% } %>
    </ul>

    <h3>Add Role</h3>
    <form action="addrole" method="post" enctype="multipart/form-data">
      <input type="hidden" name="activityname" value="<%=name%>">
      <input name="rolename">
      <input type="file" name="snapfile">
      <input type="submit" value="Add">
    </form>

  </body>
</html>
