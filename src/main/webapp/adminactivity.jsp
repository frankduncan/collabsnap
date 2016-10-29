<%
  String name = request.getParameter("activityname");
  if(name != null) {
    edu.vanderbilt.SimpleDatabase.addActivity(name);
  }
%>

<html>
  <body>
    <h1>Administer <%=name%></h1>
  </body>
</html>
