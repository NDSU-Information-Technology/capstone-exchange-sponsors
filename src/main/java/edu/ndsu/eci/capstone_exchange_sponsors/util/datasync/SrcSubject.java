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
package edu.ndsu.eci.capstone_exchange_sponsors.util.datasync;

import edu.ndsu.eci.capstone_exchange_sponsors.persist.Subject;
import edu.ndsu.eci.capstone_exchange_sponsors.util.enums.Status;

public class SrcSubject {
  /** src pk */
  private int subjectId;
  
  /** name */
  private String name;
  
  /** html description */
  private String description;
  
  /**
   * src pk
   * @return src pk
   */
  public int getSubjectId() {
    return subjectId;
  }
  
  /**
   * src pk
   * @param subjectId src pk
   */
  public void setSubjectId(int subjectId) {
    this.subjectId = subjectId;
  }
  
  /**
   * Subject name
   * @return Subject name
   */
  public String getName() {
    return name;
  }
  
  /**
   * Subject name
   * @param name Subject name
   */
  public void setName(String name) {
    this.name = name;
  }
  
  /**
   * HTML description
   * @return HTML description
   */
  public String getDescription() {
    return description;
  }
  
  /**
   * HTML description
   * @param description HTML description
   */
  public void setDescription(String description) {
    this.description = description;
  }
  
  /**
   * Set the db subject object with the info from this one
   * @param subject db subject object
   */
  public void pushToSubject(Subject subject) {
    subject.setDescription(getDescription());
    subject.setName(getName());
    subject.setSrcPk(getSubjectId());
    // only approved subjects come back
    subject.setStatus(Status.APPROVED);
  }
}
