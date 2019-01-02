package edu.ndsu.eci.capstone_exchange_sponsors.pages.account;

import java.util.Date;

import org.apache.cayenne.ObjectContext;
import org.apache.tapestry5.alerts.AlertManager;
import org.apache.tapestry5.annotations.InjectComponent;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.corelib.components.BeanEditForm;
import org.apache.tapestry5.ioc.annotations.Inject;

import com.googlecode.tapestry5cayenne.annotations.CommitAfter;

import edu.ndsu.eci.capstone_exchange_sponsors.persist.User;
import edu.ndsu.eci.capstone_exchange_sponsors.services.UserInfo;

public class Sponsorship {

  /** user info service */
  @Inject
  private UserInfo userInfo;
  
  /** logged in user */
  @Property
  private User user;
  
  @Inject
  private ObjectContext context;
  
  /** alerts */
  @Inject
  private AlertManager alerts;
  
  @Property
  private edu.ndsu.eci.capstone_exchange_sponsors.persist.Sponsorship sponsorship;
  
  @InjectComponent("sponsorshipForm")
  private BeanEditForm sponsorshipForm;
  
  
  /**
   * Setup render, get logged in user
   */
  public void setupRender() {
    user = userInfo.getUser();
  }
  
  @CommitAfter
  public void onSuccess() {
    sponsorship.setUser((User) context.localObject(userInfo.getUser().getObjectId(), null));
    sponsorship.setCreated(new Date());
    context.registerNewObject(sponsorship);
    alerts.success("New Sponsorship Successfully Created");
  }
  
}
