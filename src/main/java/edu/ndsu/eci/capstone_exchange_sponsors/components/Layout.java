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
package edu.ndsu.eci.capstone_exchange_sponsors.components;

import java.util.ArrayList;
import java.util.List;

import org.apache.tapestry5.BindingConstants;
import org.apache.tapestry5.ComponentResources;
import org.apache.tapestry5.annotations.Import;
import org.apache.tapestry5.annotations.Parameter;
import org.apache.tapestry5.annotations.Persist;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.tynamo.security.services.SecurityService;

import edu.ndsu.eci.capstone_exchange_sponsors.auth.FederatedAccountsRealm;
import edu.ndsu.eci.capstone_exchange_sponsors.services.UserInfo;

/**
 * Layout component for pages of application test-project.
 */
@Import(module="bootstrap/collapse")
public class Layout {
  @Inject
  private ComponentResources resources;

  /**
   * The page title, for the <title> element and the <h1> element.
   */
  @Property
  @Parameter(required = true, defaultPrefix = BindingConstants.LITERAL)
  private String title;

  /** current page name for loop */
  @Property
  private String pageName;
  
  /** PageName index in loop */
  @Property
  private int pageIndex;

  /** user info */
  @Inject
  private UserInfo userInfo;
  
  @Inject
  private SecurityService securityService;
  
  /** List of page names */
  @Persist
  private List<String> pageNames;
  
  /** List of page name's alternative labels */
  @Persist
  private List<String> pseudoNames;

  /**
   * Get the styling CSS class for specified page
   * @return css styling class
   */
  public String getClassForPageName() {
    return resources.getPageName().equalsIgnoreCase(pageName)
        ? "active"
        : null;
  }
  
  /**
   * Boolean whether user is logged in.
   * @return True if logged in, false otherwise.
   */
  public boolean loggedIn() {
    return userInfo.isLoggedIn();
  }

  /**
   * List of page names to be shown in the menu
   * @return list of page names
   */
  public String[] getPageNames() {
    if (!loggedIn()) {
      return new String[]{"Index","Contact","Privacy","Login"};
    }
    
    if(pageNames == null) {
      pageNames = new ArrayList<>();
      pageNames.add("Index");
      pageNames.add("Contact");
      pageNames.add("Privacy");
      
      if (securityService.hasRole(FederatedAccountsRealm.APPROVED_USER_ROLE)) {
        pageNames.add("Account/Dashboard");
      }
      
      if (securityService.hasRole(FederatedAccountsRealm.APPROVED_USER_ROLE)) {
        pageNames.add("Account/Sponsorship");
      }
      
      if (userInfo.isAdmin()) {
        pageNames.add("Admin/Admin");
      }
      
      pageNames.add("Logout");
    }
    
    return pageNames.toArray(new String[pageNames.size()]);
  }
  
  /**
   * Get alternative name of a pageName.
   * @return Alternative name for a page.
   */
  public String getPseudoName() {
    if(pseudoNames == null) {
      pseudoNames = new ArrayList<>();
      pseudoNames.add("Index");
      pseudoNames.add("Contact");
      pseudoNames.add("Privacy");
      pseudoNames.add("Dashboard");
      pseudoNames.add("Sponsorship");
      pseudoNames.add("Admin");
      pseudoNames.add("Logout");
    }
    
    return pseudoNames.get(pageIndex);
  }

}
