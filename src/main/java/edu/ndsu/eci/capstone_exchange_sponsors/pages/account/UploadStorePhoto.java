package edu.ndsu.eci.capstone_exchange_sponsors.pages.account;

import java.io.ByteArrayInputStream;

import org.apache.tapestry5.Link;
import org.apache.tapestry5.internal.services.LinkSource;
import org.apache.tapestry5.ioc.annotations.Inject;

import edu.ndsu.eci.capstone_exchange_sponsors.persist.Site;
import edu.ndsu.eci.capstone_exchange_sponsors.util.JPEGInline;

public class UploadStorePhoto {

  /** link creation page */
  @Inject
  private LinkSource linkSource;
  
  public Link getUploadedFile(Site site) {
    return linkSource.createPageRenderLink("account/UploadStorePhoto", false, new Object[] {site});
  }
  
  public Object onActivate(final Site site) {
    return new JPEGInline(new ByteArrayInputStream(site.getLogo()));
  }
}
