package edu.ndsu.eci.capstone_exchange_sponsors.util;

import org.apache.cayenne.ExtendedEnumeration;

public enum SponsorTier implements ExtendedEnumeration {
  /** General sponsorship support */
  GENERAL,
  /** Strategic Partner sponsorship which allows posting of projects */
  STRATEGIC_PARTNER;

  @Override
  public Object getDatabaseValue() {
    return this.toString();
  }

}
