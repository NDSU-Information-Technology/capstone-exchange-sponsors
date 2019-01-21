package edu.ndsu.eci.capstone_exchange_sponsors.services;

/**
 * Service for handling expiration and renewal of Sponsorships.
 *
 */
public interface UpdateSponsorships {
  
  /**
   * Checks all Sponsorships for whether they expired. If they expired and were previously in an approved status,
   * then it reissues a sponsorship under the assumption of renewal and sends notifications to the list site email
   * and to admins. Function is intended to be used as part of a CRON job for automation.
   */
  public void update();

}
