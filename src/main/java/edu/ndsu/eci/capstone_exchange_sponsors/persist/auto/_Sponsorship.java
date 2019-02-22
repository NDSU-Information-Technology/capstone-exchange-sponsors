package edu.ndsu.eci.capstone_exchange_sponsors.persist.auto;

import java.util.Date;

import org.apache.cayenne.CayenneDataObject;

import edu.ndsu.eci.capstone_exchange_sponsors.persist.Site;
import edu.ndsu.eci.capstone_exchange_sponsors.util.enums.Payment;
import edu.ndsu.eci.capstone_exchange_sponsors.util.enums.SponsorTier;
import edu.ndsu.eci.capstone_exchange_sponsors.util.enums.SponsorshipStatus;

/**
 * Class _Sponsorship was generated by Cayenne.
 * It is probably a good idea to avoid changing this class manually,
 * since it may be overwritten next time code is regenerated.
 * If you need to make any customizations, please use subclass.
 */
public abstract class _Sponsorship extends CayenneDataObject {

    public static final String CREATED_PROPERTY = "created";
    public static final String EXPIRES_PROPERTY = "expires";
    public static final String PAYMENT_PROPERTY = "payment";
    public static final String STATUS_PROPERTY = "status";
    public static final String TIER_PROPERTY = "tier";
    public static final String SITE_PROPERTY = "site";

    public static final String PK_PK_COLUMN = "pk";

    public void setCreated(Date created) {
        writeProperty("created", created);
    }
    public Date getCreated() {
        return (Date)readProperty("created");
    }

    public void setExpires(Date expires) {
        writeProperty("expires", expires);
    }
    public Date getExpires() {
        return (Date)readProperty("expires");
    }

    public void setPayment(Payment payment) {
        writeProperty("payment", payment);
    }
    public Payment getPayment() {
        return (Payment)readProperty("payment");
    }

    public void setStatus(SponsorshipStatus status) {
        writeProperty("status", status);
    }
    public SponsorshipStatus getStatus() {
        return (SponsorshipStatus)readProperty("status");
    }

    public void setTier(SponsorTier tier) {
        writeProperty("tier", tier);
    }
    public SponsorTier getTier() {
        return (SponsorTier)readProperty("tier");
    }

    public void setSite(Site site) {
        setToOneTarget("site", site, true);
    }

    public Site getSite() {
        return (Site)readProperty("site");
    }


}
