package vn.onepay.billing.service;

import java.util.List;
import vn.onepay.billing.model.ProviderProfile;

public abstract interface ProviderProfileManager
{
  public static final String BEAN_NAME = "providerProfileManager";
  public static final String DEFAULT_PROVIDER_PROFILE = "default_provider_profile";
  public static final int PROVIDER_PROFILE_ENABLE_STATUS = 1;
  public static final int PROVIDER_PROFILE_DISABLE_STATUS = 0;

  public abstract ProviderProfile getDefaultProviderActivedProfile();

  public abstract ProviderProfile getProviderProfile(String paramString);

  public abstract ProviderProfile getProviderProfile(String paramString, int paramInt);

  public abstract double getOrginalCharingRateOfProviderProfile(ProviderProfile paramProviderProfile, double paramDouble, String paramString1, String paramString2);

  public abstract double getCharingRate(ProviderProfile paramProviderProfile, double paramDouble, String paramString1, String paramString2, boolean paramBoolean);

  public abstract List<ProviderProfile> getAllProviderProfiles();

  public abstract List<ProviderProfile> findActivedProviderProfiles();

  public abstract List<ProviderProfile> findProviderProfiles(int paramInt);

  public abstract List<ProviderProfile> findProviderProfiles(String paramString);

  public abstract List<ProviderProfile> findProviderProfiles(String[] paramArrayOfString);
}