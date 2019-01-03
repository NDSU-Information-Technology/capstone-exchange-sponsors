package edu.ndsu.eci.capstone_exchange_sponsors.pages.account;

import java.util.Date;

import org.apache.cayenne.ObjectContext;
import org.apache.tapestry5.alerts.AlertManager;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.ioc.annotations.Inject;

import com.googlecode.tapestry5cayenne.annotations.CommitAfter;

import edu.ndsu.eci.capstone_exchange_sponsors.persist.User;
import edu.ndsu.eci.capstone_exchange_sponsors.services.UserInfo;

/**
 * Create new sponsorship associated to the logged in user.
 *
 */
public class Sponsorship {

  /** user info service */
  @Inject
  private UserInfo userInfo;
  
  /** logged in user */
  @Property
  private User user;
  
  /** Cayenne database context */
  @Inject
  private ObjectContext context;
  
  /** alerts */
  @Inject
  private AlertManager alerts;
  
  /** New database sponsorship object */
  @Property
  private edu.ndsu.eci.capstone_exchange_sponsors.persist.Sponsorship sponsorship;
  
  
  /**
   * Setup render, get logged in user
   */
  public void setupRender() {
    user = userInfo.getUser();
  }
  
  /**
   * Create new sponsorship.
   */
  @CommitAfter
  public void onSuccess() {
    sponsorship.setUser((User) context.localObject(userInfo.getUser().getObjectId(), null));
    sponsorship.setCreated(new Date());
    context.registerNewObject(sponsorship);
    alerts.success("New Sponsorship Successfully Created");
  }
  
}
