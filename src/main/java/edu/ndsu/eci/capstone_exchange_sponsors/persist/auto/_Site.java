package edu.ndsu.eci.capstone_exchange_sponsors.persist.auto;

import java.util.List;

import org.apache.cayenne.CayenneDataObject;

import edu.ndsu.eci.capstone_exchange_sponsors.persist.Country;
import edu.ndsu.eci.capstone_exchange_sponsors.persist.Project;
import edu.ndsu.eci.capstone_exchange_sponsors.persist.Sponsorship;
import edu.ndsu.eci.capstone_exchange_sponsors.persist.User;

/**
 * Class _Site was generated by Cayenne.
 * It is probably a good idea to avoid changing this class manually,
 * since it may be overwritten next time code is regenerated.
 * If you need to make any customizations, please use subclass.
 */
public abstract class _Site extends CayenneDataObject {

    public static final String CITY_PROPERTY = "city";
    public static final String CODE_PROPERTY = "code";
    public static final String DESCRIPTION_PROPERTY = "description";
    public static final String EMAIL_PROPERTY = "email";
    public static final String LOGO_PROPERTY = "logo";
    public static final String NAME_PROPERTY = "name";
    public static final String PHONE_PROPERTY = "phone";
    public static final String STREET_PROPERTY = "street";
    public static final String URL_PROPERTY = "url";
    public static final String ZIP_POSTAL_PROPERTY = "zipPostal";
    public static final String COUNTRY_PROPERTY = "country";
    public static final String PROJECTS_PROPERTY = "projects";
    public static final String SPONSORSHIPS_PROPERTY = "sponsorships";
    public static final String USERS_PROPERTY = "users";

    public static final String PK_PK_COLUMN = "pk";

    public void setCity(String city) {
        writeProperty("city", city);
    }
    public String getCity() {
        return (String)readProperty("city");
    }

    public void setCode(String code) {
        writeProperty("code", code);
    }
    public String getCode() {
        return (String)readProperty("code");
    }

    public void setDescription(String description) {
        writeProperty("description", description);
    }
    public String getDescription() {
        return (String)readProperty("description");
    }

    public void setEmail(String email) {
        writeProperty("email", email);
    }
    public String getEmail() {
        return (String)readProperty("email");
    }

    public void setLogo(byte[] logo) {
        writeProperty("logo", logo);
    }
    public byte[] getLogo() {
        return (byte[])readProperty("logo");
    }

    public void setName(String name) {
        writeProperty("name", name);
    }
    public String getName() {
        return (String)readProperty("name");
    }

    public void setPhone(String phone) {
        writeProperty("phone", phone);
    }
    public String getPhone() {
        return (String)readProperty("phone");
    }

    public void setStreet(String street) {
        writeProperty("street", street);
    }
    public String getStreet() {
        return (String)readProperty("street");
    }

    public void setUrl(String url) {
        writeProperty("url", url);
    }
    public String getUrl() {
        return (String)readProperty("url");
    }

    public void setZipPostal(String zipPostal) {
        writeProperty("zipPostal", zipPostal);
    }
    public String getZipPostal() {
        return (String)readProperty("zipPostal");
    }

    public void setCountry(Country country) {
        setToOneTarget("country", country, true);
    }

    public Country getCountry() {
        return (Country)readProperty("country");
    }


    public void addToProjects(Project obj) {
        addToManyTarget("projects", obj, true);
    }
    public void removeFromProjects(Project obj) {
        removeToManyTarget("projects", obj, true);
    }
    @SuppressWarnings("unchecked")
    public List<Project> getProjects() {
        return (List<Project>)readProperty("projects");
    }


    public void addToSponsorships(Sponsorship obj) {
        addToManyTarget("sponsorships", obj, true);
    }
    public void removeFromSponsorships(Sponsorship obj) {
        removeToManyTarget("sponsorships", obj, true);
    }
    @SuppressWarnings("unchecked")
    public List<Sponsorship> getSponsorships() {
        return (List<Sponsorship>)readProperty("sponsorships");
    }


    public void addToUsers(User obj) {
        addToManyTarget("users", obj, true);
    }
    public void removeFromUsers(User obj) {
        removeToManyTarget("users", obj, true);
    }
    @SuppressWarnings("unchecked")
    public List<User> getUsers() {
        return (List<User>)readProperty("users");
    }


}
