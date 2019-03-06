package edu.ndsu.eci.capstone_exchange_sponsors.pages.admin;

import org.apache.tapestry5.annotations.Property;

import edu.ndsu.eci.capstone_exchange_sponsors.persist.Project;

/**
 * View given Project's details in full.
 *
 */
public class ProjectDetails {

  /** Project of interest */
  @Property
  private Project project;
  
  /**
   * Activate to get Project.
   * @param project Project of interest.
   */
  public void onActivate(Project project) {
    this.project = project;
  }
  
  /**
   * Passivate to set Project.
   * @return Project of interest.
   */
  public Object onPassivate() {
    return project;
  }
  
}
