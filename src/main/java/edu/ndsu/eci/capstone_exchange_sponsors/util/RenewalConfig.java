package edu.ndsu.eci.capstone_exchange_sponsors.util;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

/**
 * Configuration for time interval for created Sponsorships to expire.
 *
 */
public class RenewalConfig {

  /** Number of years before expiration */
  private int years;

  /** Number of months before expiration */
  private int months;

  /** Number of days before expiration */
  private int days;

  /**
   * Constructor.
   */
  public RenewalConfig() {
    
  }
  
  /**
   * Factory creator method.
   * @return The config settings assigned.
   * @throws NamingException Parsing error from configuration.
   */
  public static RenewalConfig getInstance() throws NamingException {
    Context initCtx = new InitialContext();
    Context envCtx = (Context) initCtx.lookup("java:comp/env");
    return (RenewalConfig) envCtx.lookup("bean/renewalconf");
  }
  
  /**
   * Getter for number of years.
   * @return Number of years before expiration.
   */
  public int getYears() {
    return years;
  }

  /**
   * Setter for number of years.
   * @param years Number of years before expiration.
   */
  public void setYears(int years) {
    this.years = years;
  }

  /**
   * Getter for number of months.
   * @return Number of months before expiration.
   */
  public int getMonths() {
    return months;
  }

  /**
   * Setter for number of months.
   * @param months Number of months before expiration.
   */
  public void setMonths(int months) {
    this.months = months;
  }

  /**
   * Getter for number of days.
   * @return Number of days before expiration.
   */
  public int getDays() {
    return days;
  }

  /**
   * Setter for number of days.
   * @param days Number of days before expiration.
   */
  public void setDays(int days) {
    this.days = days;
  }

}
