package edu.ndsu.eci.capstone_exchange_sponsors.pages.account;

import java.io.IOException;

import org.apache.cayenne.ObjectContext;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.validator.routines.EmailValidator;
import org.apache.log4j.Logger;
import org.apache.tapestry5.alerts.AlertManager;
import org.apache.tapestry5.annotations.Component;
import org.apache.tapestry5.annotations.InjectPage;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.corelib.components.BeanEditForm;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.apache.tapestry5.upload.services.UploadedFile;

import com.google.i18n.phonenumbers.NumberParseException;
import com.google.i18n.phonenumbers.PhoneNumberUtil;
import com.google.i18n.phonenumbers.PhoneNumberUtil.PhoneNumberFormat;
import com.google.i18n.phonenumbers.Phonenumber.PhoneNumber;

import edu.ndsu.eci.capstone_exchange_sponsors.persist.Site;
import edu.ndsu.eci.capstone_exchange_sponsors.persist.User;
import edu.ndsu.eci.capstone_exchange_sponsors.services.UserInfo;

public class UpdateSite {

  /** logger */
  private static final Logger LOGGER = Logger.getLogger(UpdateSite.class);
  
  /** user info service */
  @Inject
  private UserInfo userInfo;

  /** logged in user */
  @Property
  private User user;
  
  @Property
  private Site site;
  
  @Property
  private UploadedFile file;
  
  /** cayenne context */
  @Inject
  private ObjectContext context;
  
  /** form */
  @Component
  private BeanEditForm form;
  
  /** alerts */
  @Inject
  private AlertManager alerts;
  
  @InjectPage
  private Dashboard index;
  
  /**
   * Setup render, get logged in user
   */
  public void setupRender() {
    user = userInfo.getUser();
    site = user.getSite();
  }
  
  /**
   * Validate form submission
   */
  public void onValidateFromForm() {
    String phone = site.getPhone();
    if (!StringUtils.startsWith(phone, "+")) {
      phone = "+" + phone;
    }
    
    PhoneNumberUtil phoneUtil = PhoneNumberUtil.getInstance();
    PhoneNumber number;
    try {
      number = phoneUtil.parse(phone, null);
      if (!phoneUtil.isValidNumber(number)) {
        form.recordError("Phone number format isn't valid, be sure to include country code. For most of North America, this is a 1");
      } else {
        site.setPhone(phoneUtil.format(number, PhoneNumberFormat.INTERNATIONAL));
      }
    } catch (NumberParseException e) {
      form.recordError("Phone number format isn't valid, be sure to include country code. For most of North America, this is a 1");
      LOGGER.info("Failed to parse number", e);
    }
    
    
    if (!EmailValidator.getInstance().isValid(site.getEmail())) {
      form.recordError("Email address is not valid");
    }
    
  }
  
  /** 
   * Success from form
   * @return dashboard page
   */
  public Object onSuccessFromForm() {
    Site siteEdit = userInfo.getUser().getSite();
    siteEdit.setName(site.getName());
    siteEdit.setStreet(site.getStreet());
    siteEdit.setCity(site.getCity());
    siteEdit.setZipPostal(site.getZipPostal());
    siteEdit.setPhone(site.getPhone());
    siteEdit.setEmail(site.getEmail());
    siteEdit.setUrl(site.getUrl());
    siteEdit.getObjectContext().commitChanges();
    alerts.success("Updated Site Info");
    return index;
  }
  
  public Object onSuccessFromPhoto() {
    Site siteEdit = userInfo.getUser().getSite();
    
    try {
      byte[] bytes = IOUtils.toByteArray(file.getStream());
      siteEdit.setLogo(bytes);
    } catch (IOException e) {
      e.printStackTrace();
    }
    
    
    siteEdit.getObjectContext().commitChanges();
    alerts.success("Updated Site Logo");
    return index;
  }
}
