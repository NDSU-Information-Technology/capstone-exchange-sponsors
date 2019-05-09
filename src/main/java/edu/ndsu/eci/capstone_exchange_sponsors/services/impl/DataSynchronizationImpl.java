package edu.ndsu.eci.capstone_exchange_sponsors.services.impl;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.cayenne.ObjectContext;
import org.apache.cayenne.access.DataContext;
import org.apache.cayenne.query.SelectQuery;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.util.EntityUtils;
import org.apache.log4j.Logger;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import edu.ndsu.eci.capstone_exchange_sponsors.persist.Country;
import edu.ndsu.eci.capstone_exchange_sponsors.services.DataSynchronization;
import edu.ndsu.eci.capstone_exchange_sponsors.util.SrcCountry;
import edu.ndsu.eci.capstone_exchange_sponsors.util.enums.Status;

public class DataSynchronizationImpl implements DataSynchronization {

  /** gson */
  private static final Gson gson = new Gson();
  
  /** logger */
  private static final Logger LOGGER = Logger.getLogger(DataSynchronizationImpl.class);

  /** the base path of the remote system to communicate with.
   * FIXME get from JNDI
   */
  private final String remoteBasePath = "https://wings.it.ndsu.edu/cap/";

  /** http client that is thread safe */
  private final CloseableHttpClient httpclient;

  /**
   * Constructor
   */
  public DataSynchronizationImpl() {
    PoolingHttpClientConnectionManager cm = new PoolingHttpClientConnectionManager();
    cm.setMaxTotal(100);

    httpclient = HttpClients.custom().setConnectionManager(cm).build();
  }

  @Override
  public void synchronizeCountries() {
    try {
      HttpGet get = new HttpGet(remoteBasePath + "/rest/countries");
      CloseableHttpResponse resp = httpclient.execute(get);
      HttpEntity entity = resp.getEntity();
      // FIXME this should be UTF8
      String json = EntityUtils.toString(entity);
      ObjectContext context = DataContext.createDataContext();
      
      List<Country> countries = context.performQuery(new SelectQuery(Country.class));
      Map<Integer, Country> map = new HashMap<>();
      
      for (Country country : countries) {
        map.put(country.getSrcPk(), country);
      }
      
      List<SrcCountry> srcCountries = gson.fromJson(json, new TypeToken<List<SrcCountry>>() {}.getType());
      
      for (SrcCountry src : srcCountries) {
        if (map.containsKey(src.getCountryId())) {
          src.pushToCountry(map.get(src.getCountryId()));
          map.remove(src.getCountryId());
        } else {
          Country country = context.newObject(Country.class);
          country.setCreated(new Date());
          src.pushToCountry(country);
        }
      }
      
      for (Country country : map.values()) {
        country.setStatus(Status.DECOMMISSIONED);
      }
      
      context.commitChanges();
    } catch (IOException e) {
      LOGGER.error("Failed to update countries", e);
    }
  }

}