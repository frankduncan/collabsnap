function loadNewGroupRoleSelect() {
  var activitySelect = document.getElementById("newgroupactivityname");
  var activityName = activitySelect.options[activitySelect.selectedIndex].value;

  loadRoleSelect(activityName, "newgrouprole")
}

function loadRunningGroupRoleSelect() {
  var groupSelect = document.getElementById("runninggroup");
  var activityName = groupSelect.options[groupSelect.selectedIndex].getAttribute("activity");

  document.getElementById("runninggroupactivity").value = activityName;

  loadRoleSelect(activityName, "runninggrouprole");
}

function loadRoleSelect(activityName, roleSelectName) {
  var roleSelect = document.getElementById(roleSelectName);

  var roles = (activity_role_hash[activityName]);

  while(roleSelect.childNodes.length > 0) {
    roleSelect.removeChild(roleSelect.childNodes[0]);
  }

  for(var idx = 1 ; idx < roles.length ; idx++) {
    var option = document.createElement("option");
    option.value = roles[idx];
    option.appendChild(document.createTextNode(roles[idx]));
    roleSelect.appendChild(option);
  }

}

window.onload = function() {
  loadNewGroupRoleSelect();
  loadRunningGroupRoleSelect();
}
