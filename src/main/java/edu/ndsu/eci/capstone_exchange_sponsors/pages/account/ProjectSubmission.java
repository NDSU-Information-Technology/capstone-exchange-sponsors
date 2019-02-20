// Copyright 2018 North Dakota State University
//
// Licensed under the Apache License, Version 2.0 (the "License");
// you may not use this file except in compliance with the License.
// You may obtain a copy of the License at
//
//     http://www.apache.org/licenses/LICENSE-2.0
//
// Unless required by applicable law or agreed to in writing, software
// distributed under the License is distributed on an "AS IS" BASIS,
// WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
// See the License for the specific language governing permissions and
// limitations under the License.
package edu.ndsu.eci.capstone_exchange_sponsors.pages.account;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.cayenne.ObjectContext;
import org.apache.cayenne.Persistent;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.tapestry5.SelectModel;
import org.apache.tapestry5.ValueEncoder;
import org.apache.tapestry5.alerts.AlertManager;
import org.apache.tapestry5.annotations.Component;
import org.apache.tapestry5.annotations.InjectPage;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.corelib.components.BeanEditForm;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.exception.ParseErrorException;
import org.apache.velocity.exception.ResourceNotFoundException;

import com.google.common.collect.Sets;
import com.google.common.collect.Sets.SetView;
import com.googlecode.tapestry5cayenne.PersistentEntitySelectModel;
import com.googlecode.tapestry5cayenne.annotations.Cayenne;

import edu.ndsu.eci.capstone_exchange_sponsors.auth.ILACRealm;
import edu.ndsu.eci.capstone_exchange_sponsors.persist.CapstoneDomainMap;
import edu.ndsu.eci.capstone_exchange_sponsors.persist.Project;
import edu.ndsu.eci.capstone_exchange_sponsors.persist.Site;
import edu.ndsu.eci.capstone_exchange_sponsors.persist.Sponsorship;
import edu.ndsu.eci.capstone_exchange_sponsors.persist.Subject;
import edu.ndsu.eci.capstone_exchange_sponsors.persist.User;
import edu.ndsu.eci.capstone_exchange_sponsors.services.HtmlCleaner;
import edu.ndsu.eci.capstone_exchange_sponsors.services.UserInfo;
import edu.ndsu.eci.capstone_exchange_sponsors.services.VelocityEmailService;
import edu.ndsu.eci.capstone_exchange_sponsors.util.ProjectStatus;
import edu.ndsu.eci.capstone_exchange_sponsors.util.SponsorTier;
import edu.ndsu.eci.capstone_exchange_sponsors.util.Status;

public class ProjectSubmission {

  /** user info service */
  @Inject
  private UserInfo userInfo;
  
  /** cayenne context */
  @Inject
  private ObjectContext context;

  /** page to go back to */
  @InjectPage
  private Dashboard dashboard;

  /** alerts */
  @Inject
  private AlertManager alerts;

  /** form object */
  @Property
  private Project project;

  /** form */
  @Component
  private BeanEditForm form;

  /** selected subjects from palette */
  @Property
  private List<Subject> selectedSubjects;
  
  /** Selected subject row */
  @Property
  private Subject subjectRow;

  /** encoder for palette */
  @Inject
  @Cayenne
  @Property
  private ValueEncoder<Persistent> encoder;

  /** html cleaner */
  @Inject
  private HtmlCleaner cleaner;
  
  /** Email service for notification */
  @Inject
  private VelocityEmailService emailService;
  
  /** Cayenne data map for querying */
  private CapstoneDomainMap map = CapstoneDomainMap.getInstance();

  
  /**
   * Setup the form if it is a new submission
   */
  public void onActivate() {
    if (project != null) {
      return;
    }
    project = new Project();
    // required for autoboxing
    project.setBudget(0.0);
  }

  /**
   * Standard on passivate
   * @return proposal
   */
  public Object onPassivate() {
    return project;
  }

  /**
   * On activate for editing a project's details.
   * @param project The project being edited.
   */
  @RequiresPermissions(ILACRealm.PROJECT_EDIT_INSTANCE)
  public void onActivate(Project project) {
    this.project = project;
    if (selectedSubjects == null && project.getSubjects() != null) {
      selectedSubjects = new ArrayList<>(project.getSubjects());
    }
  }
  
  /**
   * Validates if user's site has an active Sponsorship of appropriate tier.
   * @return The dashboard page if invalid, null otherwise.
   */
  public Object afterRender() {
    Sponsorship sponsorship;
    List<Sponsorship> sponsorshipList = map.performSponsorshipByStatusAndSiteQuery(context, Status.APPROVED, userInfo.getUser().getSite());
    
    if(sponsorshipList.isEmpty()) {
      alerts.warn("Site associated to this account does not have an approved sponsorship.");
      return dashboard;
    } else {
      sponsorship = sponsorshipList.get(0);
    }
    
    // Sponsorship is of strategic partner tier
    if(sponsorship.getTier().equals(SponsorTier.STRATEGIC_PARTNER)) {
      return null;
    } else {
      alerts.warn("Site associated to this account does not have an active sponsorship of Strategic Partner tier.");
      return dashboard;
    }
  }

  /**
   * Validate the form
   */
  public void onValidateFromForm() {
    if (StringUtils.isBlank(project.getDescription())) {
      form.recordError("Must provide a description");
      context.rollbackChanges();
    }
  }
  

  /**
   * On form success submission
   * @return return page
   * @throws Exception 
   * @throws ParseErrorException 
   * @throws ResourceNotFoundException 
   */
  public Object onSuccessFromForm() throws ResourceNotFoundException, ParseErrorException, Exception {
    if (project.getCreated() == null) {
      project.setCreated(new Date());
    }
    project.setLastModified(new Date());
    project.setProjectStatus(ProjectStatus.PENDING);
    project.setDescription(cleaner.cleanCapstone(project.getDescription()));
    
    User user = (User) context.localObject(userInfo.getUser().getObjectId(), null);
    project.setUser(user);
    Site site = (Site) context.localObject(userInfo.getUser().getSite().getObjectId(), null);
    project.setSite(site);
    
    fixupSubjects();
    
    context.commitChanges();
    alerts.success("Project Submitted");
    notifyAdmins();
    
    return dashboard;
  }
  
  /**
   * Emails admin upon project submission.
   * @throws ResourceNotFoundException
   * @throws ParseErrorException
   * @throws Exception
   */
  private void notifyAdmins() throws ResourceNotFoundException, ParseErrorException, Exception {
    VelocityContext velContext = new VelocityContext();
    velContext.put("project", project);
    emailService.sendAdminEmail(velContext, "project-submitted.vm", "Project Submission");
  }
  
  private void fixupSubjects() {
    Set<Subject> existing = new HashSet<>(project.getSubjects());
    Set<Subject> newSubjects = new HashSet<>(selectedSubjects);
    
    SetView<Subject> newView = Sets.difference(newSubjects, existing);
    
    for (Subject subject : newView) {
      project.addToSubjects(subject);
    }

    SetView<Subject> oldView = Sets.difference(existing, newSubjects);
    
    for (Subject subject : oldView) {
      project.removeFromSubjects(subject);
    }
  }

  /**
   * Subject checklist model
   * @return checklist model
   */
  public SelectModel getSubjectsModel() {
    return new PersistentEntitySelectModel<>(Subject.class, getSubjects());
  }
 
  /**
   * Possible subjects
   * @return sort approved subjects
   */
  public List<Subject> getSubjects() {
    return CapstoneDomainMap.getInstance().performSubjectsByStatus(context, Status.APPROVED);
  } 

}
