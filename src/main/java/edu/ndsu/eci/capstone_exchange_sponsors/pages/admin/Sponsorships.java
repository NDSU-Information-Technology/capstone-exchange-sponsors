package edu.ndsu.eci.capstone_exchange_sponsors.pages.admin;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import javax.naming.NamingException;

import org.apache.cayenne.ObjectContext;
import org.apache.cayenne.query.SelectQuery;
import org.apache.log4j.Logger;
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
import edu.ndsu.eci.capstone_exchange_sponsors.util.RenewalConfig;
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
  
  /** Cayenne data map for querying */
  private CapstoneDomainMap map = CapstoneDomainMap.getInstance();
  
  /** Error logger */
  private static final Logger LOGGER = Logger.getLogger(Sponsorships.class);

  /**
   * On page load, create a new Sponsorship.
   */
  public void setupRender() {
    if(sponsorship == null) {
      sponsorship = new Sponsorship();
      Date today = new Date();
      sponsorship.setCreated(today);
      Calendar expiration = new GregorianCalendar();
      expiration.setTime(today);
      expiration.set(Calendar.MILLISECOND, 0);
      expiration.set(Calendar.SECOND, 0);
      expiration.set(Calendar.MINUTE, 0);
      expiration.set(Calendar.HOUR_OF_DAY, 0);
      //Try to set expiration date with configuration settings in jetty-env.xml file.
      try {
        RenewalConfig config = RenewalConfig.getInstance();
        expiration.add(Calendar.YEAR, config.getYears());
        expiration.add(Calendar.MONTH, config.getMonths());
        expiration.add(Calendar.DAY_OF_MONTH, config.getDays());
      } catch (NamingException e) {
        //Some reason config parsing failed, so default to 1 month and throw error
        expiration.add(Calendar.MONTH, 1);
        alerts.warn("Renewal Configurations could not be properly parsed for newly created sponsorship. Please set expiration date manually.");
        LOGGER.warn("Renewal Configurations could not be properly parsed for newly created sponsorship.", e);
      }
      sponsorship.setExpires(expiration.getTime());
    }
  }
  
  /**
   * Event link to create a new sponsorship.
   */
  public void onCreateSponsorship() {
    sponsorship = null;
  }
  
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
      if(map.performSponsorshipByStatusAndSiteQuery(context, Status.PENDING, allSites.get(i)).isEmpty()) {
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
    sponsorship = null;
  }
  
  /**
   * Set persisted Site object.
   * @param siteRow Selection from all Sites grid.
   */
  public void onAllSite(Site siteRow) {
    site = siteRow;
    sponsorship = null;
  }
  
  /**
   * Validation for form submission.
   */
  public void onValidateFromForm() {
    if(sponsorship.getExpires().before(sponsorship.getCreated())) {
      form.recordError("Expiration date must be after the creation date.");
      context.rollbackChanges();
    }
    
    if(sponsorship.getStatus().equals(Status.APPROVED) && !map.performSponsorshipByStatusAndSiteQuery(context, Status.APPROVED, site).isEmpty()) {
      form.recordError("Selected site already has an Approved sponsorship. Make sure to set the other sponsorships to Decommissioned before creating/updating this sponsorship.");
      context.rollbackChanges();
    }
  }
  
  /**
   * Success for form submission.
   */
  @CommitAfter
  public void onSuccessFromForm() {
    sponsorship.setSite(site);
  }

}