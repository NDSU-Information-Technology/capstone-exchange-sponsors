package edu.ndsu.eci.capstone_exchange_sponsors.pages.admin;

import org.apache.tapestry5.annotations.Property;

import edu.ndsu.eci.capstone_exchange_sponsors.persist.Project;

public class ProjectDetails {

  @Property
  private Project project;
  
  
  public void onActivate(Project project) {
    this.project = project;
  }
  
  public Object onPassivate() {
    return project;
  }
  
}
