package edu.ndsu.eci.capstone_exchange_sponsors.pages.admin;

import java.util.List;

import org.apache.cayenne.ObjectContext;
import org.apache.cayenne.query.SelectQuery;
import org.apache.tapestry5.annotations.Component;
import org.apache.tapestry5.annotations.PageActivationContext;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.corelib.components.BeanEditForm;
import org.apache.tapestry5.ioc.annotations.Inject;

import com.googlecode.tapestry5cayenne.annotations.CommitAfter;

import edu.ndsu.eci.capstone_exchange_sponsors.persist.Sponsorship;

public class Sponsorships {
  
  @PageActivationContext
  @Property
  private Sponsorship sponsorship;
  
  @Component
  private BeanEditForm form;
  
  @Inject
  private ObjectContext context;
  
  @Property
  private Sponsorship sponsorshipRow;

  
  @SuppressWarnings("unchecked")
  public List<Sponsorship> getSponsorships() {
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