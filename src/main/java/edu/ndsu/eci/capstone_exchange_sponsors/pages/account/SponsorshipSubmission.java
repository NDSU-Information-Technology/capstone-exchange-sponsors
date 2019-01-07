package edu.ndsu.eci.capstone_exchange_sponsors.pages.account;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import org.apache.cayenne.ObjectContext;
import org.apache.tapestry5.alerts.AlertManager;
import org.apache.tapestry5.annotations.Component;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.corelib.components.BeanEditForm;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.exception.ParseErrorException;
import org.apache.velocity.exception.ResourceNotFoundException;

import com.googlecode.tapestry5cayenne.annotations.CommitAfter;

import edu.ndsu.eci.capstone_exchange_sponsors.persist.CapstoneDomainMap;
import edu.ndsu.eci.capstone_exchange_sponsors.persist.Site;
import edu.ndsu.eci.capstone_exchange_sponsors.persist.Sponsorship;
import edu.ndsu.eci.capstone_exchange_sponsors.services.UserInfo;
import edu.ndsu.eci.capstone_exchange_sponsors.services.VelocityEmailService;
import edu.ndsu.eci.capstone_exchange_sponsors.util.Status;

/**
 * Create new sponsorship associated to the logged in user.
 *
 */
public class SponsorshipSubmission {

  /** user info service */
  @Inject
  private UserInfo userInfo;
  
  /** Cayenne database context */
  @Inject
  private ObjectContext context;
  
  /** alerts */
  @Inject
  private AlertManager alerts;
  
  /** Email service for notification */
  @Inject
  private VelocityEmailService emailService;
  
  /** Form reference */
  @Component
  private BeanEditForm sponsorshipForm;
  
  /** New database sponsorship object */
  @Property
  private Sponsorship sponsorship;
  
  /** Cayenne data map for querying */
  private CapstoneDomainMap map = CapstoneDomainMap.getInstance();
  
  /** Number of months a sponsorship lasts */
  private static final int sponsorshipLength = 1;
  
  /**
   * Validation for form.
   */
  public void onValidateFromSponsorshipForm() {
    Site site = userInfo.getUser().getSite();
    
    if(!map.performSponsorshipByStatusSiteQuery(context, Status.PENDING, site).isEmpty()) {
      sponsorshipForm.recordError("Your site already has a pending sponsorship. Please contact a system admin if you want your current sponsorship details to be adjusted.");
      context.rollbackChanges();
    }
    if(!map.performSponsorshipByStatusSiteQuery(context, Status.APPROVED, site).isEmpty()) {
      sponsorshipForm.recordError("Your site already has an active sponsorship. Please contact a system admin if you want your current sponsorship details to be adjusted.");
      context.rollbackChanges();
    }
  }
  
  /**
   * Create new sponsorship.
   * @throws Exception 
   * @throws ParseErrorException 
   * @throws ResourceNotFoundException 
   */
  @CommitAfter
  public void onSuccess() throws ResourceNotFoundException, ParseErrorException, Exception {
    sponsorship.setSite((Site) context.localObject(userInfo.getUser().getSite().getObjectId(), null));
    sponsorship.setStatus(Status.PENDING);
    
    Date today = new Date();
    Calendar expiration = new GregorianCalendar();
    expiration.setTime(today);
    expiration.set(Calendar.MILLISECOND, 0);
    expiration.set(Calendar.SECOND, 0);
    expiration.set(Calendar.MINUTE, 0);
    expiration.set(Calendar.HOUR_OF_DAY, 0);
    expiration.add(Calendar.MONTH, sponsorshipLength);
    
    sponsorship.setCreated(today);
    sponsorship.setExpires(expiration.getTime());
    
    context.registerNewObject(sponsorship);
    alerts.success("New Sponsorship Successfully Created");
    
    notifyAdminsSubmit();
  }
  
  /**
   * Emails admin upon sponsorship submission.
   * @throws ResourceNotFoundException
   * @throws ParseErrorException
   * @throws Exception
   */
  private void notifyAdminsSubmit() throws ResourceNotFoundException, ParseErrorException, Exception {
    VelocityContext velContext = new VelocityContext();
    velContext.put("sponsorship", sponsorship);
    velContext.put("user", userInfo.getUser());
    emailService.sendAdminEmail(velContext, "sponsorship-submitted.vm", "Sponsorship Submission");
  }
  
}
