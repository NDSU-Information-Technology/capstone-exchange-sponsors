package edu.ndsu.eci.capstone_exchange_sponsors.util;

import org.apache.cayenne.ExtendedEnumeration;

/**
 * Used as a selector for payment method in Sponsorship menu.
 *
 */
public enum Payment implements ExtendedEnumeration {

  /** Indicates they paid with credit */
  CREDIT,
  /** Indicates they paid with a check */
  CHECK;
  
  @Override
  public Object getDatabaseValue() {
    return this.toString();
  }
}
