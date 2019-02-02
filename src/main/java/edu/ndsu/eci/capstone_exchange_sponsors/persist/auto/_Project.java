package edu.ndsu.eci.capstone_exchange_sponsors.persist.auto;

import java.util.Date;
import java.util.List;

import org.apache.cayenne.CayenneDataObject;

import edu.ndsu.eci.capstone_exchange_sponsors.persist.Site;
import edu.ndsu.eci.capstone_exchange_sponsors.persist.Subject;
import edu.ndsu.eci.capstone_exchange_sponsors.persist.User;
import edu.ndsu.eci.capstone_exchange_sponsors.util.ProjectStatus;

/**
 * Class _Project was generated by Cayenne.
 * It is probably a good idea to avoid changing this class manually,
 * since it may be overwritten next time code is regenerated.
 * If you need to make any customizations, please use subclass.
 */
public abstract class _Project extends CayenneDataObject {

    public static final String BUDGET_PROPERTY = "budget";
    public static final String CREATED_PROPERTY = "created";
    public static final String DESCRIPTION_PROPERTY = "description";
    public static final String DURATION_IN_WEEKS_PROPERTY = "durationInWeeks";
    public static final String LAST_MODIFIED_PROPERTY = "lastModified";
    public static final String NAME_PROPERTY = "name";
    public static final String POTENTIAL_START_PROPERTY = "potentialStart";
    public static final String PROJECT_STATUS_PROPERTY = "projectStatus";
    public static final String SITE_PROPERTY = "site";
    public static final String SUBJECTS_PROPERTY = "subjects";
    public static final String USER_PROPERTY = "user";

    public static final String PK_PK_COLUMN = "pk";

    public void setBudget(Double budget) {
        writeProperty("budget", budget);
    }
    public Double getBudget() {
        return (Double)readProperty("budget");
    }

    public void setCreated(Date created) {
        writeProperty("created", created);
    }
    public Date getCreated() {
        return (Date)readProperty("created");
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

    public void setLastModified(Date lastModified) {
        writeProperty("lastModified", lastModified);
    }
    public Date getLastModified() {
        return (Date)readProperty("lastModified");
    }

    public void setName(String name) {
        writeProperty("name", name);
    }
    public String getName() {
        return (String)readProperty("name");
    }

    public void setPotentialStart(Date potentialStart) {
        writeProperty("potentialStart", potentialStart);
    }
    public Date getPotentialStart() {
        return (Date)readProperty("potentialStart");
    }

    public void setProjectStatus(ProjectStatus projectStatus) {
        writeProperty("projectStatus", projectStatus);
    }
    public ProjectStatus getProjectStatus() {
        return (ProjectStatus)readProperty("projectStatus");
    }

    public void setSite(Site site) {
        setToOneTarget("site", site, true);
    }

    public Site getSite() {
        return (Site)readProperty("site");
    }


    public void addToSubjects(Subject obj) {
        addToManyTarget("subjects", obj, true);
    }
    public void removeFromSubjects(Subject obj) {
        removeToManyTarget("subjects", obj, true);
    }
    @SuppressWarnings("unchecked")
    public List<Subject> getSubjects() {
        return (List<Subject>)readProperty("subjects");
    }


    public void setUser(User user) {
        setToOneTarget("user", user, true);
    }

    public User getUser() {
        return (User)readProperty("user");
    }


}
