package edu.ndsu.eci.capstone_exchange_sponsors.persist;

import com.googlecode.tapestry5cayenne.annotations.Label;

import edu.ndsu.eci.capstone_exchange_sponsors.persist.auto._Project;
import edu.ndsu.eci.capstone_exchange_sponsors.util.ProjectStatus;

public class Project extends _Project {

  @Label
  @Override
  public String getName() {
    return super.getName();
  }
  
  /**
   * If the proposal is editable or eligible for deletion
   * @return if the proposal can be changed by the author
   */
  public boolean isEditable() {
    return getProjectStatus() == ProjectStatus.PENDING;
  }
  
  /**
   * If it is in a state where it can be deleted
   * @return true if it can be deleted, false otherwise
   */
  public boolean isDeletable() {
    return getProjectStatus() == ProjectStatus.PENDING;
  }
  
  @Override
  public void setUser(User user) {
    super.setUser(user);
  }
}
