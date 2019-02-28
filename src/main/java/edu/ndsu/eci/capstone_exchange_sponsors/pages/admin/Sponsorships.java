package edu.ndsu.eci.capstone_exchange_sponsors.pages.admin;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import javax.naming.NamingException;

import org.apache.cayenne.ObjectContext;
import org.apache.cayenne.query.SelectQuery;
import org.apache.log4j.Logger;
import org.apache.tapestry5.Link;
import org.apache.tapestry5.alerts.AlertManager;
import org.apache.tapestry5.annotations.Component;
import org.apache.tapestry5.annotations.InjectPage;
import org.apache.tapestry5.annotations.PageActivationContext;
import org.apache.tapestry5.annotations.PageReset;
import org.apache.tapestry5.annotations.Persist;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.corelib.components.BeanEditForm;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.apache.tapestry5.services.javascript.JavaScriptSupport;

import com.googlecode.tapestry5cayenne.annotations.CommitAfter;

import edu.ndsu.eci.capstone_exchange_sponsors.pages.account.UploadStorePhoto;
import edu.ndsu.eci.capstone_exchange_sponsors.persist.CapstoneDomainMap;
import edu.ndsu.eci.capstone_exchange_sponsors.persist.Site;
import edu.ndsu.eci.capstone_exchange_sponsors.persist.Sponsorship;
import edu.ndsu.eci.capstone_exchange_sponsors.util.RenewalConfig;
import edu.ndsu.eci.capstone_exchange_sponsors.util.enums.SponsorTier;
import edu.ndsu.eci.capstone_exchange_sponsors.util.enums.SponsorshipStatus;

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
  
  @InjectPage
  private UploadStorePhoto uploadStore;
  
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
   * Event link to create a new sponsorship.
   */
  public void onCreateSponsorship() {
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
    
    for(int i = allSites.size() - 1; i >= 0; i--) {
      if(map.performSponsorshipByStatusAndSiteQuery(context, SponsorshipStatus.PENDING, allSites.get(i)).isEmpty()) {
        allSites.remove(i);
      }
    }
    
    return allSites;
  }
  
  /**
   * Gets a list of Sites with a approved sponsorship.
   * @return List of Site objects.
   */
  public List<Site> getApprovedSites() {
    List <Site> allSites = getAllSites();
    
    for(int i = allSites.size() - 1; i >= 0; i--) {
      if(map.performSponsorshipByStatusAndSiteQuery(context, SponsorshipStatus.ACTIVE, allSites.get(i)).isEmpty()) {
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
   * @param siteRow Selection from Sites grid.
   */
  public void onSelectSite(Site siteRow) {
    site = siteRow;
    sponsorship = null;
  }
  
  /**
   * Validation for form submission.
   */
  public void onValidateFromForm() {
    //Check to make sure expiration is set after creation date
    if(sponsorship.getExpires().before(sponsorship.getCreated())) {
      form.recordError("Expiration date must be after the creation date.");
      context.rollbackChanges();
    }
    
    //Check to make sure there's not two Active sponsorships associated to a site
    List<Sponsorship> activeList = map.performSponsorshipByStatusAndSiteQuery(context, SponsorshipStatus.ACTIVE, site);
    //Check if there's a Active sponsorship we might have to worry about
    if(!activeList.isEmpty()) {
      //Check if sponsorship being edited is set as Active and if so whether
      //it's not the same sponsorship from the general search for an active sponsorship
      if(sponsorship.getStatus().equals(SponsorshipStatus.ACTIVE) && sponsorship != activeList.get(0)) {
        form.recordError("Selected site already has an Active sponsorship. Make sure to remove Active status from the other sponsorship before setting this sponsorship to Active.");
        context.rollbackChanges();
      }
    }
  }
  
  /**
   * Success for form submission.
   */
  @CommitAfter
  public void onSuccessFromForm() {
    sponsorship.setSite(site);
  }
  
  /**
   * Used to display the site logo.
   * @return Image content.
   */
  public Link getUploadedImage() {
    return uploadStore.getUploadedFile(siteRow);
  }
  
  /**
   * Gets the current row's expiration for the active sponsorship.
   * @return Active sponsorship's expiration.
   */
  public Date getExpiration() {
    Sponsorship s = map.performSponsorshipByStatusAndSiteQuery(context, SponsorshipStatus.ACTIVE, siteRow).get(0);
    return s.getExpires();
  }
  
  /**
   * Gets the current row's tier for the active sponsorship.
   * @return Active sponsorship's tier.
   */
  public SponsorTier getSiteTier() {
    Sponsorship s = map.performSponsorshipByStatusAndSiteQuery(context, SponsorshipStatus.ACTIVE, siteRow).get(0);
    return s.getTier();
  }

}