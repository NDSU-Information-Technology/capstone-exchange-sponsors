package edu.ndsu.eci.capstone_exchange_sponsors.util.enums;

import org.apache.cayenne.ExtendedEnumeration;

/**
 * Status of a Sponsorship.
 */
public enum SponsorshipStatus implements ExtendedEnumeration {

  /** Sponsorship is awaiting invoicing */
  PENDING,
  /** Sponsorship has been declined */
  DECLINED,
  /** An invoice has been sent to the site */
  INVOICED,
  /** Sponsorship has been approved and payed for */
  ACTIVE,
  /** Sponsorship has expired */
  EXPIRED;
  
  @Override
  public Object getDatabaseValue() {
    return this.toString();
  }
}
