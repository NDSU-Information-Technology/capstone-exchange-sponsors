package edu.ndsu.eci.capstone_exchange_sponsors.services.impl;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import org.apache.cayenne.ObjectContext;
import org.apache.cayenne.access.DataContext;
import org.apache.cayenne.query.SelectQuery;

import edu.ndsu.eci.capstone_exchange_sponsors.pages.account.SponsorshipSubmission;
import edu.ndsu.eci.capstone_exchange_sponsors.persist.Sponsorship;
import edu.ndsu.eci.capstone_exchange_sponsors.services.UpdateSponsorships;
import edu.ndsu.eci.capstone_exchange_sponsors.util.Status;

/**
 * Implementation of UpdateSponsorships.
 *
 */
public class UpdateSponsorshipsImpl implements UpdateSponsorships {

  @Override
  public void update() {
    //Create new database context
    ObjectContext context = DataContext.createDataContext();
    
    //Get all Sponsorship data
    @SuppressWarnings("unchecked")
    List<Sponsorship> sponsorships = context.performQuery(new SelectQuery(Sponsorship.class));
    
    
    Date today = new Date();
    for(int i = 0; i < sponsorships.size(); i++) {
      Sponsorship sponsorship = sponsorships.get(i);
      if(sponsorship.getStatus().equals(Status.APPROVED) && sponsorship.getExpires().before(today)) {
        //Decommission expired sponsorship
        sponsorship.setStatus(Status.DECOMMISSIONED);
        
        Sponsorship renew = new Sponsorship();
        renew.setCreated(today);
        renew.setPayment(sponsorship.getPayment());
        renew.setSite(sponsorship.getSite());
        renew.setTier(sponsorship.getTier());
        renew.setStatus(Status.APPROVED);
        
        Calendar expiration = new GregorianCalendar();
        expiration.setTime(today);
        expiration.set(Calendar.MILLISECOND, 0);
        expiration.set(Calendar.SECOND, 0);
        expiration.set(Calendar.MINUTE, 0);
        expiration.set(Calendar.HOUR_OF_DAY, 0);
        //FIXME replace sponsorshipLength with a configuration setting
        expiration.add(Calendar.MONTH, SponsorshipSubmission.sponsorshipLength);
        
        renew.setExpires(expiration.getTime());
        
        context.registerNewObject(renew);
        
        //FIXME add in site and admin notification
      }
    }
    
    context.commitChanges();
    
  }

  
}
