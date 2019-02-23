package edu.ndsu.eci.capstone_exchange_sponsors.pages.account;

import java.util.Date;
import java.util.List;

import org.apache.cayenne.ObjectContext;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.tapestry5.alerts.AlertManager;
import org.apache.tapestry5.annotations.Component;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.corelib.components.Zone;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.apache.tapestry5.services.Request;
import org.apache.tapestry5.services.ajax.AjaxResponseRenderer;
import org.apache.tapestry5.services.javascript.JavaScriptSupport;

import edu.ndsu.eci.capstone_exchange_sponsors.auth.ILACRealm;
import edu.ndsu.eci.capstone_exchange_sponsors.persist.CapstoneDomainMap;
import edu.ndsu.eci.capstone_exchange_sponsors.persist.Project;
import edu.ndsu.eci.capstone_exchange_sponsors.persist.ProjectNote;
import edu.ndsu.eci.capstone_exchange_sponsors.persist.User;
import edu.ndsu.eci.capstone_exchange_sponsors.services.UserInfo;

/**
 * Page to view and create project notes.
 */
public class ProjectNotes {

  /** User info service */
  @Inject
  private UserInfo userInfo;
  
  /** Cayenne database context */
  @Inject
  private ObjectContext context;
  
  /** JavaScript Support */
  @Inject
  private JavaScriptSupport javaScriptSupport;
  
  /** Renderer for Zone update */
  @Inject
  private AjaxResponseRenderer ajaxResponseRenderer;
  
  /** Request for Zone notification */
  @Inject
  private Request request;
  
  /** Alert manager */
  @Inject
  private AlertManager alerts;
  
  /** New note zone reference */
  @Component
  private Zone newNoteZone;
  
  /** Project reference */
  @Property
  private Project project;
  
  /** Grid row for notes */
  @Property
  private ProjectNote noteRow;
  
  /** Boolean for display check */
  @Property
  private boolean makeNewNote;
  
  /** Form data object */
  @Property
  private ProjectNote newNote;
  
  /** Map reference for queries */
  private CapstoneDomainMap map = CapstoneDomainMap.getInstance();
  
  /**
   * Activate for passing in project.
   * @param project The project of interest.
   */
  @RequiresPermissions(ILACRealm.PROJECT_EDIT_INSTANCE)
  public void onActivate(Project project) {
    this.project = project;
  }
  
  /**
   * Passivate for passing in project.
   * @return The project of interest.
   */
  public Object onPassivate() {
    return project;
  }
  
  /**
   * Eventlink to display note creation beanform.
   */
  public void onCreateNote() {
    makeNewNote = true;
    if (request.isXHR()) {
      ajaxResponseRenderer.addRender(newNoteZone);
    }
  }
  
  /**
   * Form validation.
   */
  public void onValidateFromForm() {
    if(StringUtils.isBlank(newNote.getNote())) {
      alerts.warn("Note must contain a message.");
      context.rollbackChanges();
    }
  }
  
  /**
   * Form successful submission.
   */
  public void onSuccessFromForm() {
    newNote.setCreated(new Date());
    User user = (User) context.localObject(userInfo.getUser().getObjectId(), null);
    newNote.setUser(user);
    newNote.setProject(project);
    context.registerNewObject(newNote);
    context.commitChanges();
  }
  
  /**
   * Getter for notes associate to the project in descending order.
   * @return List of project notes.
   */
  public List<ProjectNote> getNotes() {
    return map.performProjectNotesQuery(context, project);
  }
}
