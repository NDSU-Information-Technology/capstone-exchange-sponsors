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
package edu.ndsu.eci.capstone_exchange_sponsors.pages.admin;

import java.util.Date;
import java.util.List;

import org.apache.cayenne.ObjectContext;
import org.apache.cayenne.query.SelectQuery;
import org.apache.tapestry5.alerts.AlertManager;
import org.apache.tapestry5.annotations.Component;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.corelib.components.Zone;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.apache.tapestry5.services.Request;
import org.apache.tapestry5.services.ajax.AjaxResponseRenderer;
import org.apache.tapestry5.services.javascript.JavaScriptSupport;

import com.googlecode.tapestry5cayenne.annotations.CommitAfter;

import edu.ndsu.eci.capstone_exchange_sponsors.persist.CapstoneDomainMap;
import edu.ndsu.eci.capstone_exchange_sponsors.persist.Project;
import edu.ndsu.eci.capstone_exchange_sponsors.util.enums.ProjectStatus;
import edu.ndsu.eci.capstone_exchange_sponsors.util.enums.SponsorshipStatus;
import edu.ndsu.eci.capstone_exchange_sponsors.util.enums.Status;

/**
 * Manages Projects status.
 *
 */
public class Projects {

  /** Cayenne database context */
  @Inject
  private ObjectContext context;
  
  /** JavaScript Support */
  @Inject
  private JavaScriptSupport javaScriptSupport;
  
  /** Alert manager */
  @Inject
  private AlertManager alerts;
  
  /** Project row selection for either grid */
  @Property
  private Project row;
  
  /** Holds ProjectStatus value for updating a Project */
  @Property
  private ProjectStatus status;
  
  /** Zone surrounding all projects grid */
  @Component
  private Zone allZone;
  
  /** Zone surrounding pending projects grid */
  @Component
  private Zone pendingZone;
  
  /** Zone surrounding approved projects grid */
  @Component
  private Zone approvedZone;
  
  /** Zone surrounding active projects grid */
  @Component
  private Zone activeZone;
  
  /** Zone surrounding awarded projects grid */
  @Component
  private Zone awardedZone;
  
  /** Request for Zone notification */
  @Inject
  private Request request;
  
  /** Renderer for Zone update */
  @Inject
  private AjaxResponseRenderer ajaxResponseRenderer;
  
  /** Map reference for queries */
  private CapstoneDomainMap map = CapstoneDomainMap.getInstance();
  
  /**
   * After render to include JS files.
   */
  void afterRender() {
    javaScriptSupport.require("bootstrap/tab");
  }
  
  public List<Project> projectsByStatus(String status) {
    return map.performProjectByStatusQuery(context, ProjectStatus.valueOf(status.toUpperCase()));
  }
  
  /**
   * Get list of Projects with pending ProjectStatus.
   * @return List of Project objects.
   */
  public List<Project> getPendingProjects() {
    return map.performProjectByStatusQuery(context, ProjectStatus.PENDING);
  }
  
  /**
   * Get list of all Projects.
   * @return List of Project objects.
   */
  @SuppressWarnings("unchecked")
  public List<Project> getAllProjects() {
    return context.performQuery(new SelectQuery(Project.class));
  }
  
  /**
   * Approval eventlink for pending grid.
   * @param row The Project being updated.
   */
  @CommitAfter
  public void onApprove(Project row) {
    row.setProjectStatus(ProjectStatus.APPROVED);
    row.setLastModified(new Date());
    
    if (request.isXHR()) {
      ajaxResponseRenderer.addRender(allZone);
      ajaxResponseRenderer.addRender(pendingZone);
      ajaxResponseRenderer.addRender(approvedZone);
    }
  }
  
  @CommitAfter
  public void onActive(Project row) {
    if(map.performSponsorshipByStatusAndSiteQuery(context, SponsorshipStatus.ACTIVE, row.getSite()).isEmpty()) {
      alerts.warn("Project does not belong to a site with an active sponsorship.");
      return;
    }
    
    row.setProjectStatus(ProjectStatus.ACTIVE);
    row.setLastModified(new Date());
    
    if (request.isXHR()) {
      ajaxResponseRenderer.addRender(allZone);
      ajaxResponseRenderer.addRender(approvedZone);
      ajaxResponseRenderer.addRender(activeZone);
    }
  }
  
  @CommitAfter
  public void onAwarded(Project row) {
    row.setProjectStatus(ProjectStatus.AWARDED);
    row.setLastModified(new Date());
    
    if (request.isXHR()) {
      ajaxResponseRenderer.addRender(allZone);
      ajaxResponseRenderer.addRender(activeZone);
      ajaxResponseRenderer.addRender(awardedZone);
    }
  }
  
  
  /**
   * General ProjectStatus updating for all grid.
   * @param row The Project being updated.
   */
  @CommitAfter
  public void onSuccessFromStatusForm(Project row) {
    row.setProjectStatus(status);
    row.setLastModified(new Date());
    
    if (request.isXHR()) {
      ajaxResponseRenderer.addRender(allZone);
      ajaxResponseRenderer.addRender(pendingZone);
      ajaxResponseRenderer.addRender(approvedZone);
      ajaxResponseRenderer.addRender(activeZone);
      ajaxResponseRenderer.addRender(awardedZone);
    }
  }
}
