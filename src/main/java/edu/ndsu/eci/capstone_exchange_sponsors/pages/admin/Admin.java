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

import org.apache.cayenne.ObjectContext;
import org.apache.tapestry5.ioc.annotations.Inject;

import edu.ndsu.eci.capstone_exchange_sponsors.persist.CapstoneDomainMap;
import edu.ndsu.eci.capstone_exchange_sponsors.util.ProjectStatus;
import edu.ndsu.eci.capstone_exchange_sponsors.util.Status;

public class Admin {
 
  /** Cayenne database context */
  @Inject
  private ObjectContext context;
  
  /** Cayenne data map for querying */
  private CapstoneDomainMap map = CapstoneDomainMap.getInstance();
  
  /**
   * Get number of pending Projects.
   * @return Number of pending items.
   */
  public int getPendingProjects() {
    return map.performProjectByStatusQuery(context, ProjectStatus.PENDING).size();
  }
  
  /**
   * Get number of pending Sponsorships.
   * @return Number of pending items.
   */
  public int getPendingSponsorships() {
    return map.performSponsorshipByStatusQuery(context, Status.PENDING).size();
  }
  
  /**
   * Get number of pending Users.
   * @return Number of pending items.
   */
  public int getPendingUsers() {
    return map.performUsersByStatus(context, Status.PENDING).size();
  }
}
