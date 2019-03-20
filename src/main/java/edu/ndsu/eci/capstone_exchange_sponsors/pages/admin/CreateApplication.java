package edu.ndsu.eci.capstone_exchange_sponsors.pages.admin;

import java.util.List;

import org.apache.cayenne.ObjectContext;
import org.apache.cayenne.query.SelectQuery;
import org.apache.tapestry5.annotations.Component;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.corelib.components.BeanEditForm;
import org.apache.tapestry5.ioc.annotations.Inject;

import edu.ndsu.eci.capstone_exchange_sponsors.persist.Application;
import edu.ndsu.eci.capstone_exchange_sponsors.persist.ApplicationDate;
import edu.ndsu.eci.capstone_exchange_sponsors.persist.ApplicationNote;

/**
 * Incomplete side page under Admin privileges, just meant to be used for easy creation of application objects
 * for testing purposes. Assumption was that these objects would eventually be automated to pull from the
 * sibling database tied to the other Capstone web application.
 */
public class CreateApplication {

  @Property
  private Application application;
  
  @Property
  private ApplicationNote applicationNote;
  
  @Property
  private ApplicationDate applicationDate;
  
  /** Form reference */
  @Component
  private BeanEditForm appForm;
  
  /** Form reference */
  @Component
  private BeanEditForm appNoteForm;
  
  /** Form reference */
  @Component
  private BeanEditForm appDateForm;
  
  @Property
  private Application appRow;
  
  @Property
  private ApplicationNote appNoteRow;
  
  @Property
  private ApplicationDate appDateRow;
  
  /** Cayenne database context */
  @Inject
  private ObjectContext context;
  
  
  public void onSuccessFromAppForm() {
    
  }
  
  public List<Application> getApplications() {
    return context.performQuery(new SelectQuery(Application.class));
  }
  
  public void onSuccessFromAppNoteForm() {
    
  }
  
  public List<ApplicationNote> getApplicationNotes() {
    return context.performQuery(new SelectQuery(ApplicationNote.class));
  }
  
  public void onSuccessFromAppDateForm() {
    
  }
  
  public List<ApplicationDate> getApplicationDates() {
    return context.performQuery(new SelectQuery(ApplicationDate.class));
  }
  
}
