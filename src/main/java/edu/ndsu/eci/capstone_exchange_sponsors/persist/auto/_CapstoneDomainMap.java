package edu.ndsu.eci.capstone_exchange_sponsors.persist.auto;

import java.util.List;

import org.apache.cayenne.ObjectContext;
import org.apache.cayenne.query.NamedQuery;

import edu.ndsu.eci.capstone_exchange_sponsors.persist.Country;
import edu.ndsu.eci.capstone_exchange_sponsors.persist.Proposal;
import edu.ndsu.eci.capstone_exchange_sponsors.persist.Role;
import edu.ndsu.eci.capstone_exchange_sponsors.persist.Subject;
import edu.ndsu.eci.capstone_exchange_sponsors.persist.User;
import edu.ndsu.eci.capstone_exchange_sponsors.util.ProposalStatus;
import edu.ndsu.eci.capstone_exchange_sponsors.util.Status;
import edu.ndsu.eci.capstone_exchange_sponsors.util.UserRole;

/**
 * This class was generated by Cayenne.
 * It is probably a good idea to avoid changing this class manually,
 * since it may be overwritten next time code is regenerated.
 * If you need to make any customizations, please use subclass.
 */
public class _CapstoneDomainMap {

    public static final String COUNTRIES_QUERYNAME = "Countries";

    public static final String PROPOSALS_BY_STATUS_QUERYNAME = "ProposalsByStatus";

    public static final String ROLE_BY_NAME_QUERY_QUERYNAME = "RoleByNameQuery";

    public static final String SUBJECTS_BY_STATUS_QUERYNAME = "SubjectsByStatus";

    public static final String USER_BY_SOURCE_ID_QUERY_QUERYNAME = "UserBySourceIdQuery";

    public static final String USERS_BY_ROLE_QUERY_QUERYNAME = "UsersByRoleQuery";

    public static final String USERS_BY_STATUS_QUERYNAME = "UsersByStatus";

    public List<Country> performCountries(ObjectContext context , Status status) {
        String[] parameters = {
            "status",
        };

        Object[] values = {
            status,
        };

        return context.performQuery(new NamedQuery("Countries", parameters, values));
    }

    public List<Proposal> performProposalsByStatus(ObjectContext context , ProposalStatus status) {
        String[] parameters = {
            "status",
        };

        Object[] values = {
            status,
        };

        return context.performQuery(new NamedQuery("ProposalsByStatus", parameters, values));
    }

    public List<Role> performRoleByNameQuery(ObjectContext context , UserRole name) {
        String[] parameters = {
            "name",
        };

        Object[] values = {
            name,
        };

        return context.performQuery(new NamedQuery("RoleByNameQuery", parameters, values));
    }

    public List<Subject> performSubjectsByStatus(ObjectContext context , Status status) {
        String[] parameters = {
            "status",
        };

        Object[] values = {
            status,
        };

        return context.performQuery(new NamedQuery("SubjectsByStatus", parameters, values));
    }

    public List<User> performUserBySourceIdQuery(ObjectContext context , String source, String id) {
        String[] parameters = {
            "source",
            "id",
        };

        Object[] values = {
            source,
            id,
        };

        return context.performQuery(new NamedQuery("UserBySourceIdQuery", parameters, values));
    }

    public List<User> performUsersByRoleQuery(ObjectContext context , UserRole role) {
        String[] parameters = {
            "role",
        };

        Object[] values = {
            role,
        };

        return context.performQuery(new NamedQuery("UsersByRoleQuery", parameters, values));
    }

    public List<User> performUsersByStatus(ObjectContext context , Status status) {
        String[] parameters = {
            "status",
        };

        Object[] values = {
            status,
        };

        return context.performQuery(new NamedQuery("UsersByStatus", parameters, values));
    }
}