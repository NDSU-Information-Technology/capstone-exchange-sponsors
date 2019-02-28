package edu.ndsu.eci.capstone_exchange_sponsors.pages.admin;

import java.util.List;

import org.apache.cayenne.ObjectContext;
import org.apache.cayenne.query.SelectQuery;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.validator.routines.EmailValidator;
import org.apache.log4j.Logger;
import org.apache.tapestry5.alerts.AlertManager;
import org.apache.tapestry5.annotations.Component;
import org.apache.tapestry5.annotations.InjectComponent;
import org.apache.tapestry5.annotations.PageActivationContext;
import org.apache.tapestry5.annotations.Persist;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.corelib.components.BeanEditForm;
import org.apache.tapestry5.corelib.components.Zone;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.apache.tapestry5.services.Request;
import org.apache.tapestry5.services.ajax.AjaxResponseRenderer;

import com.google.i18n.phonenumbers.NumberParseException;
import com.google.i18n.phonenumbers.PhoneNumberUtil;
import com.google.i18n.phonenumbers.PhoneNumberUtil.PhoneNumberFormat;
import com.google.i18n.phonenumbers.Phonenumber.PhoneNumber;
import com.googlecode.tapestry5cayenne.annotations.CommitAfter;

import edu.ndsu.eci.capstone_exchange_sponsors.persist.Country;
import edu.ndsu.eci.capstone_exchange_sponsors.persist.Site;
import edu.ndsu.eci.capstone_exchange_sponsors.util.CodeGenerator;

/**
 * Manages site creation and updating.
 *
 */
public class Sites {

  /** Site being created/updated */
  @PageActivationContext
  @Property
  private Site site;
  
  /** Cayenne database context */
  @Inject
  private ObjectContext context;
  
  /** Alert manager */
  @Inject
  private AlertManager alerts;
  
  /** AJAX updating */
  @Inject
  private AjaxResponseRenderer ajaxResponseRenderer;
  
  /** Request for AJAX updating */
  @Inject
  private Request request;
  
  /** Zone surrounding code generator to display generation */
  @InjectComponent
  private Zone codeZone;
  
  /** Value of a generated code */
  @Persist
  @Property
  private String newCode;
  
  /** Site row in grid */
  @Property
  private Site siteRow;
  
  /** Form */
  @Component
  private BeanEditForm form;
  
  /** Error logger for page */
  private static final Logger LOGGER = Logger.getLogger(Sites.class);
  
  /**
   * Page load event.
   */
  public void setupRender() {
    newCode = null;
  }
  
  /**
   * Getter for entire Site list.
   * @return List of all Sites.
   */
  @SuppressWarnings("unchecked")
  public List<Site> getSites() {
    return context.performQuery(new SelectQuery(Site.class));
  }
  
  /**
   * Getter for entire Country list.
   * @return List of all Countries.
   */
  @SuppressWarnings("unchecked")
  public List<Country> getCountries() {
    return context.performQuery(new SelectQuery(Country.class));
  }
  
  /**
   * Validation for form.
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
    
    if(site.getCode() == null && newCode == null) {
      form.recordError("Please generate a code for the site.");
    }
  }
  
  /**
   * Create/Update the Site.
   */
  @CommitAfter
  public void onSuccessFromForm() {
    if(newCode != null) {
      site.setCode(newCode);
    }
    alerts.success("Site Created/Updated");
  }
  
  /**
   * Clear all fields by nulling the site object and refreshing page.
   */
  public void onClear() {
    site = null;
    context.rollbackChangesLocally();
  }
  
  /**
   * Eventlink to generate a new site code.
   */
  public void onNewCode() {
    CodeGenerator generator = new CodeGenerator();
    newCode = generator.getCode(context);
    if(request.isXHR()) {
      ajaxResponseRenderer.addRender(codeZone);
    }
  }
  
}
