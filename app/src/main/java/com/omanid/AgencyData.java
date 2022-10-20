package com.omanid;

public class AgencyData {
    private String WebsiteAddress;
    private int ThemeId=1;

    public int getAgencyId() {
        return AgencyId;
    }

    public void setAgencyId(int agencyId) {
        AgencyId = agencyId;
    }

    private int AgencyId=1;
    private boolean IsFaceComparisionNeeded;

    public String getWebsiteAddress() {
        return WebsiteAddress;
    }

    public void setWebsiteAddress(String websiteAddress) {
        WebsiteAddress = websiteAddress;
    }

    public int getThemeId() {
        return ThemeId;
    }

    public void setThemeId(int themeId) {
        ThemeId = themeId;
    }

    public boolean isFaceComparisionNeeded() {
        return IsFaceComparisionNeeded;
    }

    public void setFaceComparisionNeeded(boolean faceComparisionNeeded) {
        IsFaceComparisionNeeded = faceComparisionNeeded;
    }

    public int getAgencyCreditBalance() {
        return AgencyCreditBalance;
    }

    public void setAgencyCreditBalance(int agencyCreditBalance) {
        AgencyCreditBalance = agencyCreditBalance;
    }

    public String getIsCreditAvailable() {
        return IsCreditAvailable;
    }

    public void setIsCreditAvailable(String isCreditAvailable) {
        IsCreditAvailable = isCreditAvailable;
    }

    public String getAgencyLogo() {
        return AgencyLogo;
    }

    public void setAgencyLogo(String agencyLogo) {
        AgencyLogo = agencyLogo;
    }

    public String getApiEndPoint() {
        return ApiEndPoint;
    }

    public void setApiEndPoint(String apiEndPoint) {
        ApiEndPoint = apiEndPoint;
    }

    private int AgencyCreditBalance;
    private String  IsCreditAvailable;
    private String  AgencyLogo="";
    private String  ApiEndPoint;

    private static final AgencyData ourInstance = new AgencyData();

    public static AgencyData getInstance() {
        return ourInstance;
    }

    private AgencyData() {
    }
}
