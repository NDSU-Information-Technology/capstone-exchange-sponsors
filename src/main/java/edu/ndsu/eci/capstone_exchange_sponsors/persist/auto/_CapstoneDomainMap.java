package edu.ndsu.eci.capstone_exchange_sponsors.persist.auto;

import java.util.List;

import org.apache.cayenne.ObjectContext;
import org.apache.cayenne.query.NamedQuery;

import edu.ndsu.eci.capstone_exchange_sponsors.persist.Country;
import edu.ndsu.eci.capstone_exchange_sponsors.persist.Project;
import edu.ndsu.eci.capstone_exchange_sponsors.persist.ProjectNote;
import edu.ndsu.eci.capstone_exchange_sponsors.persist.Role;
import edu.ndsu.eci.capstone_exchange_sponsors.persist.Site;
import edu.ndsu.eci.capstone_exchange_sponsors.persist.Sponsorship;
import edu.ndsu.eci.capstone_exchange_sponsors.persist.Subject;
import edu.ndsu.eci.capstone_exchange_sponsors.persist.User;
import edu.ndsu.eci.capstone_exchange_sponsors.util.enums.ProjectStatus;
import edu.ndsu.eci.capstone_exchange_sponsors.util.enums.SponsorshipStatus;
import edu.ndsu.eci.capstone_exchange_sponsors.util.enums.Status;
import edu.ndsu.eci.capstone_exchange_sponsors.util.enums.UserRole;

/**
 * This class was generated by Cayenne.
 * It is probably a good idea to avoid changing this class manually,
 * since it may be overwritten next time code is regenerated.
 * If you need to make any customizations, please use subclass.
 */
public class _CapstoneDomainMap {

    public static final String COUNTRIES_QUERYNAME = "Countries";

    public static final String PROJECT_BY_STATUS_QUERY_QUERYNAME = "ProjectByStatusQuery";

    public static final String PROJECT_NOTES_QUERY_QUERYNAME = "ProjectNotesQuery";

    public static final String ROLE_BY_NAME_QUERY_QUERYNAME = "RoleByNameQuery";

    public static final String SITE_BY_CODE_QUERY_QUERYNAME = "SiteByCodeQuery";

    public static final String SPONSORSHIP_BY_STATUS_AND_SITE_QUERY_QUERYNAME = "SponsorshipByStatusAndSiteQuery";

    public static final String SPONSORSHIP_BY_STATUS_QUERY_QUERYNAME = "SponsorshipByStatusQuery";

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

    public List<Project> performProjectByStatusQuery(ObjectContext context , ProjectStatus projectStatus) {
        String[] parameters = {
            "projectStatus",
        };

        Object[] values = {
            projectStatus,
        };

        return context.performQuery(new NamedQuery("ProjectByStatusQuery", parameters, values));
    }

    public List<ProjectNote> performProjectNotesQuery(ObjectContext context , Project project) {
        String[] parameters = {
            "project",
        };

        Object[] values = {
            project,
        };

        return context.performQuery(new NamedQuery("ProjectNotesQuery", parameters, values));
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

    public List<Site> performSiteByCodeQuery(ObjectContext context , String code) {
        String[] parameters = {
            "code",
        };

        Object[] values = {
            code,
        };

        return context.performQuery(new NamedQuery("SiteByCodeQuery", parameters, values));
    }

    public List<Sponsorship> performSponsorshipByStatusAndSiteQuery(ObjectContext context , SponsorshipStatus status, Site site) {
        String[] parameters = {
            "status",
            "site",
        };

        Object[] values = {
            status,
            site,
        };

        return context.performQuery(new NamedQuery("SponsorshipByStatusAndSiteQuery", parameters, values));
    }

    public List<Sponsorship> performSponsorshipByStatusQuery(ObjectContext context , SponsorshipStatus status) {
        String[] parameters = {
            "status",
        };

        Object[] values = {
            status,
        };

        return context.performQuery(new NamedQuery("SponsorshipByStatusQuery", parameters, values));
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