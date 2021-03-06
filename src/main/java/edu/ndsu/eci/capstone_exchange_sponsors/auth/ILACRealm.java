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
package edu.ndsu.eci.capstone_exchange_sponsors.auth;

import org.apache.cayenne.PersistenceState;
import org.apache.commons.codec.binary.StringUtils;
import org.apache.tapestry5.plastic.MethodInvocation;
import org.apache.tapestry5.services.Environment;

import edu.ndsu.eci.capstone_exchange_sponsors.persist.Project;
import edu.ndsu.eci.capstone_exchange_sponsors.persist.Site;
import edu.ndsu.eci.capstone_exchange_sponsors.persist.Sponsorship;
import edu.ndsu.eci.capstone_exchange_sponsors.services.UserInfo;
import edu.ndsu.eci.capstone_exchange_sponsors.util.enums.SponsorTier;
import edu.ndsu.eci.capstone_exchange_sponsors.util.enums.SponsorshipStatus;
import edu.ndsu.eci.capstone_exchange_sponsors.util.enums.UserRole;

public class ILACRealm extends BaseILACRealm {

  /** ability to edit a proposal */
  public static final String PROJECT_EDIT_INSTANCE = "project_edit:instance";

  public static final String PROJECT_CREATE = "project:create";
  
  private final UserInfo userInfo;

  public ILACRealm(Environment environment, UserInfo userInfo) {
    super(environment);
    this.userInfo = userInfo;
  }

  @InstanceAccessMethod(PROJECT_EDIT_INSTANCE) 
  public boolean isProjectEditMember() {
    MethodInvocation invocation = getInvocation();

    if (invocation == null) {
      return false;
    }

    Project project = (Project) invocation.getParameter(0);

    if (project.getPersistenceState() == PersistenceState.TRANSIENT) {
      return true;
    }
    
    if(userInfo.getUser().getRoles().contains(UserRole.ADMIN)) {
      return true;
    }

    return StringUtils.equals(project.getSite().getCode(), userInfo.getUser().getSite().getCode());

  }

  @InstanceAccessMethod(PROJECT_CREATE)
  public boolean isProjectCreateAllowed() {
    Site site = userInfo.getUser().getSite();
    boolean spons = false;
    for (Sponsorship sponsor : site.getSponsorships()) {
      if (sponsor.getStatus() == SponsorshipStatus.ACTIVE && sponsor.getTier() == SponsorTier.STRATEGIC_PARTNER) {
        spons = true;
      }
    }
    
    if (!spons) {
      return false;
    }

    // FIXME this only works for the first year
    return site.getProjects().isEmpty();
  }
}
