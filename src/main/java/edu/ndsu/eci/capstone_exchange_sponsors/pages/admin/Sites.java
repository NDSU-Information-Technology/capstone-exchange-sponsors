package edu.ndsu.eci.capstone_exchange_sponsors.pages.admin;

import java.util.List;

import org.apache.cayenne.ObjectContext;
import org.apache.cayenne.query.SelectQuery;
import org.apache.tapestry5.annotations.PageActivationContext;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.ioc.annotations.Inject;

import com.googlecode.tapestry5cayenne.annotations.CommitAfter;

import edu.ndsu.eci.capstone_exchange_sponsors.persist.Country;
import edu.ndsu.eci.capstone_exchange_sponsors.persist.Site;

public class Sites {

  @PageActivationContext
  @Property
  private Site site;
  
  @Inject
  private ObjectContext context;
  
  @Property
  private Site siteRow;
  
  @SuppressWarnings("unchecked")
  public List<Site> getSites() {
    return context.performQuery(new SelectQuery(Site.class));
  }
  
  @SuppressWarnings("unchecked")
  public List<Country> getCountries() {
    return context.performQuery(new SelectQuery(Country.class));
  }
  
  public void onValidateFromForm() {
    
  }
  
  @CommitAfter
  public void onSuccessFromForm() {
    
  }
}
