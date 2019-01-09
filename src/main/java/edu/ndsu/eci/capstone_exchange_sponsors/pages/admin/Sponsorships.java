package edu.ndsu.eci.capstone_exchange_sponsors.pages.admin;

import java.util.List;

import org.apache.cayenne.ObjectContext;
import org.apache.cayenne.query.SelectQuery;
import org.apache.tapestry5.annotations.Component;
import org.apache.tapestry5.annotations.PageActivationContext;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.corelib.components.BeanEditForm;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.apache.tapestry5.services.javascript.JavaScriptSupport;

import com.googlecode.tapestry5cayenne.annotations.CommitAfter;

import edu.ndsu.eci.capstone_exchange_sponsors.persist.Sponsorship;
import edu.ndsu.eci.capstone_exchange_sponsors.util.Status;

public class Sponsorships {
  
  @PageActivationContext
  @Property
  private Sponsorship sponsorship;
  
  @Component
  private BeanEditForm form;
  
  @Inject
  private ObjectContext context;
  
  /** JavaScript Support */
  @Inject
  private JavaScriptSupport javaScriptSupport;
  
  @Property
  private Sponsorship sponsorshipRow;

  
  /**
   * After render to include JS files.
   */
  void afterRender() {
    javaScriptSupport.require("bootstrap/tab");
  }
  
  public List<Sponsorship> getPendingSponsorships() {
    List <Sponsorship> allSponsorships = getAllSponsorships();
    
    //FIXME Replace with query
    for(int i = 0; i < allSponsorships.size(); i++) {
      if(!allSponsorships.get(i).getStatus().equals(Status.PENDING)) {
        allSponsorships.remove(i);
      }
    }
    
    return allSponsorships;
  }
  
  @SuppressWarnings("unchecked")
  public List<Sponsorship> getAllSponsorships() {
    return context.performQuery(new SelectQuery(Sponsorship.class));
  }
  
  public void onValidateFromForm() {
    if(sponsorship.getExpires().before(sponsorship.getCreated())) {
      form.recordError("Expiration date must be after the creation date.");
      context.rollbackChanges();
    }
  }
  
  @CommitAfter
  public void onSuccessFromForm() {
    
  }

}