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
package edu.ndsu.eci.capstone_exchange_sponsors.util;

/**
 * Email configuration class
 *
 */
public class EmailConfig {

  /** from address for the application */
  private String fromAddress;

  /**
   * Get from address
   * @return from address
   */
  public String getFromAddress() {
    return fromAddress;
  }

  /**
   * Set from address
   * @param fromAddress from address
   */
  public void setFromAddress(String fromAddress) {
    this.fromAddress = fromAddress;
  }
  
  
}
