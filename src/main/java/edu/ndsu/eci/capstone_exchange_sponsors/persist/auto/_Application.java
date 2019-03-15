package edu.ndsu.eci.capstone_exchange_sponsors.persist.auto;

import org.apache.cayenne.CayenneDataObject;

/**
 * Class _Application was generated by Cayenne.
 * It is probably a good idea to avoid changing this class manually,
 * since it may be overwritten next time code is regenerated.
 * If you need to make any customizations, please use subclass.
 */
public abstract class _Application extends CayenneDataObject {

    public static final String COST_PROPERTY = "cost";
    public static final String DESCRIPTION_PROPERTY = "description";
    public static final String DURATION_IN_WEEKS_PROPERTY = "durationInWeeks";
    public static final String NAME_PROPERTY = "name";
    public static final String SITE_PK_PROPERTY = "sitePk";
    public static final String TEAM_SIZE_PROPERTY = "teamSize";

    public static final String PK_PK_COLUMN = "pk";

    public void setCost(Double cost) {
        writeProperty("cost", cost);
    }
    public Double getCost() {
        return (Double)readProperty("cost");
    }

    public void setDescription(String description) {
        writeProperty("description", description);
    }
    public String getDescription() {
        return (String)readProperty("description");
    }

    public void setDurationInWeeks(Integer durationInWeeks) {
        writeProperty("durationInWeeks", durationInWeeks);
    }
    public Integer getDurationInWeeks() {
        return (Integer)readProperty("durationInWeeks");
    }

    public void setName(String name) {
        writeProperty("name", name);
    }
    public String getName() {
        return (String)readProperty("name");
    }

    public void setSitePk(Integer sitePk) {
        writeProperty("sitePk", sitePk);
    }
    public Integer getSitePk() {
        return (Integer)readProperty("sitePk");
    }

    public void setTeamSize(Integer teamSize) {
        writeProperty("teamSize", teamSize);
    }
    public Integer getTeamSize() {
        return (Integer)readProperty("teamSize");
    }

}
