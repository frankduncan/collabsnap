<html>
  <head>
    <script src="index_helper.js"></script>
    <script>
      var activity_role_hash = [];
      <% for(edu.vanderbilt.data.Activity activity : edu.vanderbilt.SimpleDatabase.getActivities()) { %>
        activity_role_hash["<%= activity.name %>"] = [
          <% for(edu.vanderbilt.data.Activity.Role role : activity.getRoles()) { %>
            ,"<%= role.name %>"
          <% } %>
            ];
      <% } %>
    </script>
  </head>
  <body>
    <h1>Welcome to collabspap</h1>

    <h2>Join a running activity</h2>
    <% if(edu.vanderbilt.SimpleDatabase.getActivities().size() > 0) { %>
      <% if(edu.vanderbilt.SimpleDatabase.getGroups().size() > 0) { %>
        <form action="joingroup" method="get">
          Group:
          <select id="runninggroup" name="groupname" onchange='loadRunningGroupRoleSelect()'>
            <% for(edu.vanderbilt.data.Group group : edu.vanderbilt.SimpleDatabase.getGroups()) { %>
            <option activity="<%= group.activity.name %>" value="<%= group.name %>"><%= group.name %> (Activity: <%= group.activity.name %>)</option>
            <% } %>
          </select>
          <input type="hidden" name="activityname" id="runninggroupactivity">
          Role:
          <select id="runninggrouprole" name="rolename">
          </select>
          <input type="submit" value="Join">
        </form>
      <% } else { %>
        <p>At this time, there are no running activities on server to join.  Please be the first to start one!</p>
      <% } %>
    <% } else { %>
      <p>At this time, there are no activities on server to start.  Please be the first to add one!</p>
    <% } %>

    <h2>Start an activity</h2>
    <% if(edu.vanderbilt.SimpleDatabase.getActivities().size() > 0) { %>
      <form action="joingroup" method="get">
        Group: <input name="groupname">
        Activity:
        <select id="newgroupactivityname" name="activityname" onchange='loadNewGroupRoleSelect()'>
          <% for(edu.vanderbilt.data.Activity activity : edu.vanderbilt.SimpleDatabase.getActivities()) { %>
          <option value="<%= activity.name %>"><%= activity.name %></option>
          <% } %>
        </select>
        Role:
        <select id="newgrouprole" name="rolename">
        </select>
        <input type="submit" value="Start">
      </form>
    <% } else { %>
      <p>At this time, there are no activities on server to start.  Please be the first to add one!</p>
    <% } %>

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
