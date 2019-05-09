// Copyright 2019 North Dakota State University
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
package edu.ndsu.eci.capstone_exchange_sponsors.util;

import edu.ndsu.eci.capstone_exchange_sponsors.persist.Country;
import edu.ndsu.eci.capstone_exchange_sponsors.util.enums.Status;

public class SrcCountry {
  /** pk from source system */
  private int countryId;
  /** iso 3166-1 alpha 2 */
  private String isoA2;
  /** country name from 3166 I think */
  private String name;
  
  /**
   * Get country id
   * @return country id
   */
  public int getCountryId() {
    return countryId;
  }
  
  /**
   * Set country id
   * @param countryId country id
   */
  public void setCountryId(int countryId) {
    this.countryId = countryId;
  }
  
  /**
   * ISO A2
   * @return ISO A2
   */
  public String getIsoA2() {
    return isoA2;
  }
  
  /**
   * ISO A2
   * @param isoA2 ISO A2
   */
  public void setIsoA2(String isoA2) {
    this.isoA2 = isoA2;
  }
  
  /**
   * Name
   * @return Name
   */
  public String getName() {
    return name;
  }
  
  /**
   * Name
   * @param name Name
   */
  public void setName(String name) {
    this.name = name;
  }

  /**
   * Push this remote information to the application version of country
   * @param country country db object to update
   */
  public void pushToCountry(Country country) {
    country.setName(getName());
    // this only deals in approved statuses
    country.setStatus(Status.APPROVED);
    country.setIsoA2(getIsoA2());
    country.setSrcPk(getCountryId());
  }
}
