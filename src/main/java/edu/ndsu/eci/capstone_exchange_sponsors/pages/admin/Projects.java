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
import edu.ndsu.eci.capstone_exchange_sponsors.util.ProjectStatus;

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
  
  /** Request for Zone notification */
  @Inject
  private Request request;
  
  /** Renderer for Zone update */
  @Inject
  private AjaxResponseRenderer ajaxResponseRenderer;
  
  /**
   * After render to include JS files.
   */
  void afterRender() {
    javaScriptSupport.require("bootstrap/tab");
  }
  
  /**
   * Get list of Projects with pending ProjectStatus.
   * @return List of Project objects.
   */
  public List<Project> getPendingProjects() {
    return CapstoneDomainMap.getInstance().performProjectByStatusQuery(context, ProjectStatus.PENDING);
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
    }
  }
  
  /**
   * Denial eventlink for pending grid.
   * @param row The Project being updated.
   */
  @CommitAfter
  public void onDeny(Project row) {
    row.setProjectStatus(ProjectStatus.DECLINED);
    row.setLastModified(new Date());
    
    if (request.isXHR()) {
      ajaxResponseRenderer.addRender(allZone);
      ajaxResponseRenderer.addRender(pendingZone);
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
    }
  }
}
