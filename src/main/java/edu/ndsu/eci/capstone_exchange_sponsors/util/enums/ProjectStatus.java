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
package edu.ndsu.eci.capstone_exchange_sponsors.util.enums;

import org.apache.cayenne.ExtendedEnumeration;

/**
 * Status of a Project.
 */
public enum ProjectStatus implements ExtendedEnumeration {

  /** Project is awaiting approval */
  PENDING,
  /** Project has been approved and needs confirmation of site's sponsorship tier */
  APPROVED,
  /** Project is currently taking proposals */
  ACTIVE,
  /** Project has been allocated to a proposal */
  AWARDED,
  /** Project has been withdrawn */
  WITHDRAWN;

  @Override
  public Object getDatabaseValue() {
    return this.toString();
  }
}
