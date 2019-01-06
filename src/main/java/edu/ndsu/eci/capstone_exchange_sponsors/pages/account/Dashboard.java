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


import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

import org.apache.cayenne.ObjectContext;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.apache.tapestry5.services.javascript.JavaScriptSupport;

import edu.ndsu.eci.capstone_exchange_sponsors.auth.ILACRealm;
import edu.ndsu.eci.capstone_exchange_sponsors.persist.CapstoneDomainMap;
import edu.ndsu.eci.capstone_exchange_sponsors.persist.Project;
import edu.ndsu.eci.capstone_exchange_sponsors.persist.Sponsorship;
import edu.ndsu.eci.capstone_exchange_sponsors.persist.Subject;
import edu.ndsu.eci.capstone_exchange_sponsors.persist.User;
import edu.ndsu.eci.capstone_exchange_sponsors.services.UserInfo;
import edu.ndsu.eci.capstone_exchange_sponsors.util.Status;

/**
 * User's dashboard to direct them to after login.
 *
 */
public class Dashboard {

  /** user info service */
  @Inject
  private UserInfo userInfo;

  /** logged in user */
  @Property
  private User user;
  
  /** cayenne context */
  @Inject
  private ObjectContext context;
  
  @Inject
  private JavaScriptSupport javaScriptSupport;
  
  /** tml row for subjects */
  @Property
  private Subject subjectRow;
  
  @Property
  private Sponsorship sponsorshipRow;
  
  @Property
  private List<Sponsorship> sponsorships;
  
  /** tml row for proposals */
  @Property
  private Project projectRow;
  
  @Property
  private List<Project> projects;
  
  
  /**
   * Setup render, get logged in user
   */
  public void setupRender() {
    user = userInfo.getUser();
    sponsorships = user.getSite().getSponsorships();
    projects = user.getProjects();
  }
  
  void afterRender() {
    javaScriptSupport.require("bootstrap/tab");
  }
  
  /**
   * List of approved subjects to display their meaning
   * @return list of subjects
   */
  public List<Subject> getSubjects() {
    return CapstoneDomainMap.getInstance().performSubjectsByStatus(context, Status.APPROVED);
  }

  
  @RequiresPermissions(ILACRealm.PROJECT_EDIT_INSTANCE)
  public void onDelete(Project project) {
    if (!project.isDeletable()) {
      return;
    }
    context.deleteObject(project);
    context.commitChanges();
  }
  
  public boolean onPending(Sponsorship sponsorship) {
    if(Status.PENDING.equals(sponsorship.getStatus())) {
      return true;
    }
    return false;
  }
  
  public void onCancelSponsorship(Sponsorship sponsorship) {
    //Change sponsorship status and send notifications
  }
  
  /**
   * Attempts to navigate to a given external URL string.
   * @param urlValue URL value.
   * @return External URL.
   */
  public URL onNavigate(String urlValue) {
    try {
      String url;
      if( !(urlValue.contains("http://") || urlValue.contains("https://")) ) {
        url = "http://" + urlValue;
      } else {
        url = urlValue;
      }
      return new URL(url);
    } catch (MalformedURLException e) {
      return null;
    }
  }
  
}
