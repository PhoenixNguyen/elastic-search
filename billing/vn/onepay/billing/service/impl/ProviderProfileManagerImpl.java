package vn.onepay.billing.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import vn.onepay.billing.model.ProviderProfile;
import vn.onepay.billing.service.ProviderProfileManager;
import vn.onepay.utils.Utils;

public class ProviderProfileManagerImpl
  implements ProviderProfileManager
{
	
  private static List<ProviderProfile> profiles;

 private String hasVat;
  
  public String getHasVat() {
	return hasVat;
}

public void setHasVat(String hasVat) {
	this.hasVat = hasVat;
}

public void setProfiles(List<ProviderProfile> _profiles)
  {
    profiles = _profiles;
  }

  public ProviderProfile getDefaultProviderActivedProfile()
  {
    for (ProviderProfile profile : profiles) {
      if (("default_provider_profile".equalsIgnoreCase(profile.getProfileCode())) && (1 == profile.getStatus()))
        return profile;
    }
    return null;
  }

  public ProviderProfile getProviderProfile(String profileCode)
  {
    for (ProviderProfile profile : profiles) {
      if (profileCode.equalsIgnoreCase(profile.getProfileCode()))
        return profile;
    }
    return null;
  }

  public ProviderProfile getProviderProfile(String providerCode, int status)
  {
    for (ProviderProfile profile : profiles) {
      if ((providerCode.equalsIgnoreCase(profile.getProviderCode())) && (status == profile.getStatus()))
        return profile;
    }
    return null;
  }

  public double getOrginalCharingRateOfProviderProfile(ProviderProfile providerProfile, double currentAmount, String chargingService, String vendor)
  {
    Map<String, Map<String, Map<Long, Double>>> chargingRates = providerProfile.getChargingRates();
    Map<String, Map<Long, Double>> chargingServiceRates = null;
    if (chargingRates.containsKey(chargingService)) {
      chargingServiceRates = (Map<String, Map<Long, Double>>)chargingRates.get(chargingService);
    }
    if (chargingServiceRates != null) {
      Map<Long, Double> vendorRates = new TreeMap<Long, Double>(chargingServiceRates.containsKey(vendor) ? (Map<Long, Double>)chargingServiceRates.get(vendor) : (Map<Long, Double>)chargingServiceRates.get("DEFAULT"));
      double rate = 0.0D;
      for (Map.Entry<Long, Double> entry : vendorRates.entrySet()) {
        if (Utils.doubleToLong(currentAmount) >= ((Long)entry.getKey()).longValue())
          rate = ((Double)entry.getValue()).doubleValue();
      }
      return rate;
    }
    return 0.0D;
  }

  public double getCharingRate(ProviderProfile providerProfile, double currentAmount, String chargingService, String vendor, boolean usedDefault)
  {
    Map<String, Map<String, Map<Long, Double>>>  chargingRates = providerProfile.getChargingRates();
    if (chargingRates.containsKey(chargingService)) {
      Map<String, Map<Long, Double>> chargingServiceRates = (Map<String, Map<Long, Double>>)chargingRates.get(chargingService);
      if ((!chargingServiceRates.containsKey(vendor)) && (!usedDefault)) {
        return 0.0D;
      }
      Map<Long, Double> vendorRates = new TreeMap<Long, Double>(chargingServiceRates.containsKey(vendor) ? (Map<Long, Double>)chargingServiceRates.get(vendor) : (Map<Long, Double>)chargingServiceRates.get("DEFAULT"));
      double rate = 0.0D;
      
      for (Map.Entry<Long, Double> entry : vendorRates.entrySet()) {
        if (Utils.doubleToLong(currentAmount) >= ((Long)entry.getKey()).longValue())
          rate = ((Double)entry.getValue()).doubleValue();
      }
      return rate / 100.0D;
    }
    return 0.0D;
  }

  public List<ProviderProfile> getAllProviderProfiles()
  {
    List rs = new ArrayList();
    int status = 1;
    for (ProviderProfile profile : profiles) {
      if (1 == profile.getStatus())
        rs.add(profile);
    }
    return rs;
  }

  public List<ProviderProfile> findActivedProviderProfiles()
  {
    return null;
  }

  public List<ProviderProfile> findProviderProfiles(int status)
  {
    List rs = new ArrayList();
    for (ProviderProfile profile : profiles) {
      if (status == profile.getStatus())
        rs.add(profile);
    }
    return rs;
  }

  public List<ProviderProfile> findProviderProfiles(String chargingService)
  {
    List rs = new ArrayList();
    for (ProviderProfile profile : profiles) {
      Map chargingRates = profile.getChargingRates();
      if ((chargingRates.containsKey(chargingService)) && (profile.getStatus() == 1)) {
        rs.add(profile);
      }
    }
    return rs;
  }

  public List<ProviderProfile> findProviderProfiles(String[] chargingServices)
  {
    List rs = new ArrayList();
    for (ProviderProfile profile : profiles) {
      Map chargingRates = profile.getChargingRates();
      for (String chargingService : chargingServices) {
        if ((chargingRates.containsKey(chargingService)) && (profile.getStatus() == 1)) {
          rs.add(profile);
        }
      }
    }
    return rs;
  }

  public static void main(String[] args)
  {
  }
}