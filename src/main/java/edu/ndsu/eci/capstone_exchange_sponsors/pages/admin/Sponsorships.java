package edu.ndsu.eci.capstone_exchange_sponsors.pages.admin;

import java.util.List;

import org.apache.cayenne.ObjectContext;
import org.apache.cayenne.query.SelectQuery;
import org.apache.tapestry5.alerts.AlertManager;
import org.apache.tapestry5.annotations.Component;
import org.apache.tapestry5.annotations.PageActivationContext;
import org.apache.tapestry5.annotations.PageReset;
import org.apache.tapestry5.annotations.Persist;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.corelib.components.BeanEditForm;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.apache.tapestry5.services.javascript.JavaScriptSupport;

import com.googlecode.tapestry5cayenne.annotations.CommitAfter;

import edu.ndsu.eci.capstone_exchange_sponsors.persist.CapstoneDomainMap;
import edu.ndsu.eci.capstone_exchange_sponsors.persist.Site;
import edu.ndsu.eci.capstone_exchange_sponsors.persist.Sponsorship;
import edu.ndsu.eci.capstone_exchange_sponsors.util.Status;

/**
 * Manages Sponsorship data.
 *
 */
public class Sponsorships {
  
  /** Sponsorship selected for editing */
  @PageActivationContext
  @Property
  private Sponsorship sponsorship;
  
  /** Site selected for building list of Sponsorships */
  @Persist
  @Property
  private Site site;
  
  /** Form reference */
  @Component
  private BeanEditForm form;
  
  /** Cayenne database context */
  @Inject
  private ObjectContext context;
  
  /** Alert manager */
  @Inject
  private AlertManager alerts;
  
  /** JavaScript Support */
  @Inject
  private JavaScriptSupport javaScriptSupport;
  
  /** Row selection for Sponsorship grid */
  @Property
  private Sponsorship sponsorshipRow;
  
  /** Row selection for Site grid */
  @Property
  private Site siteRow;

  /**
   * Called when navigated to from another page. Removes persisted Site selection.
   */
  @PageReset
  public void resetSelection() {
    site = null;
  }
  
  /**
   * After render to include JS files.
   */
  public void afterRender() {
    javaScriptSupport.require("bootstrap/tab");
  }
  
  /**
   * Gets a list of Sites with a pending sponsorship;
   * @return List of Site objects.
   */
  public List<Site> getPendingSites() {
    List <Site> allSites = getAllSites();
    CapstoneDomainMap map = CapstoneDomainMap.getInstance();
    
    for(int i = allSites.size() - 1; i >= 0; i--) {
      if(map.performSponsorshipByStatusSiteQuery(context, Status.PENDING, allSites.get(i)).isEmpty()) {
        allSites.remove(i);
      }
    }
    
    return allSites;
  }
  
  /**
   * Gets a list of all Sites.
   * @return List of Site objects.
   */
  @SuppressWarnings("unchecked")
  public List<Site> getAllSites() {
    return context.performQuery(new SelectQuery(Site.class));
  }
  
  /**
   * Get a list of Sponsorships associated to selected Site.
   * @return List of Sponsorship objects.
   */
  public List<Sponsorship> getSponsorships() {
    return site.getSponsorships();
  }
  
  /**
   * Set persisted Site object.
   * @param siteRow Selection from pending Sites grid.
   */
  public void onPendingSite(Site siteRow) {
    site = siteRow;
  }
  
  /**
   * Set persisted Site object.
   * @param siteRow Selection from all Sites grid.
   */
  public void onAllSite(Site siteRow) {
    site = siteRow;
  }
  
  /**
   * Validation for form submission.
   */
  public void onValidateFromForm() {
    if(sponsorship.getExpires().before(sponsorship.getCreated())) {
      form.recordError("Expiration date must be after the creation date.");
      context.rollbackChanges();
    }
  }
  
  /**
   * Success for form submission.
   */
  @CommitAfter
  public void onSuccessFromForm() {
    
  }

}