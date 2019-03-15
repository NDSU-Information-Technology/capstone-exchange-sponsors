package edu.ndsu.eci.capstone_exchange_sponsors.pages;

import java.util.ArrayList;
import java.util.List;

import org.apache.cayenne.ObjectContext;
import org.apache.tapestry5.Link;
import org.apache.tapestry5.annotations.InjectPage;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.ioc.annotations.Inject;

import edu.ndsu.eci.capstone_exchange_sponsors.persist.CapstoneDomainMap;
import edu.ndsu.eci.capstone_exchange_sponsors.persist.Site;
import edu.ndsu.eci.capstone_exchange_sponsors.persist.Sponsorship;
import edu.ndsu.eci.capstone_exchange_sponsors.util.enums.SponsorTier;
import edu.ndsu.eci.capstone_exchange_sponsors.util.enums.SponsorshipStatus;

/**
 * Page to publicly view Sponsors.
 */
public class ViewSponsors {

  /** Cayenne database context */
  @Inject
  private ObjectContext context;
  
  /** Grid row **/
  @Property
  private Site row;
  
  /** Holds Strategic Partner list **/
  @Property
  private List<Site> strategicSponsors;
  
  /** Holds General Partner list **/
  @Property
  private List<Site> generalSponsors;
  
  /** Page used to display logo **/
  @InjectPage
  private UploadStorePhoto uploadStore;
  
  /** Cayenne data map for querying */
  private CapstoneDomainMap map = CapstoneDomainMap.getInstance();
  
  /**
   * Setup to compile list of sponsors.
   */
  public void setupRender() {
    strategicSponsors = new ArrayList<>();
    generalSponsors = new ArrayList<>();
    List<Sponsorship> sponsorships = map.performSponsorshipByStatusQuery(context, SponsorshipStatus.ACTIVE);
    
    for(Sponsorship s : sponsorships) {
      if(s.getTier().equals(SponsorTier.STRATEGIC_PARTNER)) {
        strategicSponsors.add(s.getSite());
      }
      
      if(s.getTier().equals(SponsorTier.GENERAL)) {
        generalSponsors.add(s.getSite());
      }
    } 
  }

  /**
   * Used to display the site logo.
   * @return Image content.
   */
  public Link getUploadedImage() {
    return uploadStore.getUploadedFile(row);
  }
  
}
