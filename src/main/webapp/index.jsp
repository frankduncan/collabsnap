<html>
  <body>
    <h1>Welcome to collabspap</h1>

    <h2>Administer an activity</h2>
    <% if(edu.vanderbilt.SimpleDatabase.getActivities().size() > 0) { %>
      <form action="adminactivity.jsp" method="get">
        Activity:
        <select name="activityname">
          <% for(edu.vanderbilt.data.Activity activity : edu.vanderbilt.SimpleDatabase.getActivities()) { %>
          <option name="<%= activity.name %>"><%= activity.name %></option>
          <% } %>
        </select>
        <input type="submit" value="Administer">
      </form>
    <% } else { %>
      <p>At this time, there are no activities on server.  Please be the first to add one!</p>
    <% } %>

    <h2>Add an activity</h2>
    <form action="adminactivity.jsp" method="post">
      Activity Name: <input name="activityname">
      <input type="submit" value="Add">
    </form>
  </body>
</html>
