package edu.ndsu.eci.capstone_exchange_sponsors.services.impl;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import javax.naming.NamingException;

import org.apache.cayenne.ObjectContext;
import org.apache.cayenne.access.DataContext;
import org.apache.log4j.Logger;

import edu.ndsu.eci.capstone_exchange_sponsors.persist.CapstoneDomainMap;
import edu.ndsu.eci.capstone_exchange_sponsors.persist.Sponsorship;
import edu.ndsu.eci.capstone_exchange_sponsors.services.UpdateSponsorships;
import edu.ndsu.eci.capstone_exchange_sponsors.util.RenewalConfig;
import edu.ndsu.eci.capstone_exchange_sponsors.util.enums.SponsorshipStatus;
import edu.ndsu.eci.capstone_exchange_sponsors.util.enums.Status;

/**
 * Implementation of UpdateSponsorships.
 *
 */
public class UpdateSponsorshipsImpl implements UpdateSponsorships {

  /** Error logger */
  private static final Logger LOGGER = Logger.getLogger(UpdateSponsorships.class);
  
  @Override
  public void update() {
    //Create new database context
    ObjectContext context = DataContext.createDataContext();
    CapstoneDomainMap map = CapstoneDomainMap.getInstance();
    
    //Get all Sponsorships with Approved status
    List<Sponsorship> sponsorships = map.performSponsorshipByStatusQuery(context, SponsorshipStatus.ACTIVE);
    
    
    Date today = new Date();
    for(int i = 0; i < sponsorships.size(); i++) {
      Sponsorship sponsorship = sponsorships.get(i);
      if(sponsorship.getExpires().before(today)) {
        //Decommission expired sponsorship
        sponsorship.setStatus(SponsorshipStatus.EXPIRED);
        
        Sponsorship renew = new Sponsorship();
        renew.setCreated(today);
        renew.setPayment(sponsorship.getPayment());
        renew.setSite(sponsorship.getSite());
        renew.setTier(sponsorship.getTier());
        renew.setStatus(SponsorshipStatus.ACTIVE);
        
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
          LOGGER.warn("Renewal Configurations could not be properly parsed. Please manually fix the expiration for: " + sponsorship.getSite().getName(), e);
        }
        renew.setExpires(expiration.getTime());
        
        context.registerNewObject(renew);
        
        //FIXME add in site and admin notification
      }
    }
    
    context.commitChanges();
    
  }

  
}
