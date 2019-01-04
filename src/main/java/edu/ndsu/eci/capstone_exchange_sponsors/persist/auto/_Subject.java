package edu.ndsu.eci.capstone_exchange_sponsors.persist.auto;

import java.util.Date;
import java.util.List;

import org.apache.cayenne.CayenneDataObject;

import edu.ndsu.eci.capstone_exchange_sponsors.persist.Project;
import edu.ndsu.eci.capstone_exchange_sponsors.util.Status;

/**
 * Class _Subject was generated by Cayenne.
 * It is probably a good idea to avoid changing this class manually,
 * since it may be overwritten next time code is regenerated.
 * If you need to make any customizations, please use subclass.
 */
public abstract class _Subject extends CayenneDataObject {

    public static final String CREATED_PROPERTY = "created";
    public static final String DESCRIPTION_PROPERTY = "description";
    public static final String NAME_PROPERTY = "name";
    public static final String STATUS_PROPERTY = "status";
    public static final String PROPOSALS_PROPERTY = "proposals";

    public static final String PK_PK_COLUMN = "pk";

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

    public void setName(String name) {
        writeProperty("name", name);
    }
    public String getName() {
        return (String)readProperty("name");
    }

    public void setStatus(Status status) {
        writeProperty("status", status);
    }
    public Status getStatus() {
        return (Status)readProperty("status");
    }

    public void addToProposals(Project obj) {
        addToManyTarget("proposals", obj, true);
    }
    public void removeFromProposals(Project obj) {
        removeToManyTarget("proposals", obj, true);
    }
    @SuppressWarnings("unchecked")
    public List<Project> getProposals() {
        return (List<Project>)readProperty("proposals");
    }


}
