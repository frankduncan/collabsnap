package edu.vanderbilt.data;

import java.util.*;

public class Activity implements java.io.Serializable {
  public String name;

  private List<Role> roles;
  public List<Role> getRoles() { return roles; }

  // No thread safety here
  public void addRole(String roleName, String snapFile) {
    Role role = getRole(roleName);
    if(role == null) {
      role = new Role();
      role.name = roleName;
      roles.add(role);
    }
    role.snapFile = snapFile;
  }

  public void removeRole(String roleName) {
    roles.remove(getRole(roleName));
  }

  public Role getRole(String roleName) {
    for(Role role : roles) {
      if(role.name.equals(roleName)) { return role; }
    }
    return null;
  }

  public Activity() {
    roles = new ArrayList<Role>();
  }

  public static class Role implements java.io.Serializable {
    public String name;
    public String snapFile;
  }
}
