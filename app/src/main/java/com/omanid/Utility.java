package com.omanid;

import android.app.Activity;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

public class Utility {
    private static final Utility ourInstance = new Utility();
    public String documentKey = "log";
    public String filePath = "/PassportReadNFC_";
    private String passportNumber;
    private String dateOfBirth;
    private String expiryDate;
    //    private String logoUrl="https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcRbk0YDyfWSiZJNMehf3KvJrbLPvVyUTyM2nt0TGwCGjJB06-WDNg";
//    private String logoUrl = "";
//    private int backgroundColorCode = 0;
//    private int buttonColorCode = 0;
//    private int textColorCode = 0;
    //    private int textColorCode=0xffffffff;
    private String fullName;
    private String gender;
    private String nationality;
    private String agencyCode = "Demo101";

    public int getThemeId() {
        return themeId;
    }

    public void setThemeId(int themeId) {
        this.themeId = themeId;
    }

    private int themeId  = 1;

    public byte[] scannedImage;
    public byte[] liveImage;

    private Utility() {
    }

    public static Utility getInstance() {
        return ourInstance;
    }

//    public String getAgencyCode() {
//        return agencyCode;
//    }
//
//    public void setAgencyCode(String agencyCode) {
//        this.agencyCode = agencyCode;
//    }

//    public String getLogoUrl() {
//        return logoUrl;
//    }
//
//    public void setLogoUrl(String logoUrl) {
//        this.logoUrl = logoUrl;
//    }

//    public int getBackgroundColorCode() {
//        return backgroundColorCode;
//    }
//
//    public void setBackgroundColorCode(int backgroundColorCode) {
//        this.backgroundColorCode = backgroundColorCode;
//    }
//
//    public int getButtonColorCode() {
//        return buttonColorCode;
//    }
//
//    public void setButtonColorCode(int buttonColorCode) {
//        this.buttonColorCode = buttonColorCode;
//    }
//
//    public int getTextColorCode() {
//        return textColorCode;
//    }
//
//    public void setTextColorCode(int textColorCode) {
//        this.textColorCode = textColorCode;
//    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getNationality() {
        return nationality;
    }

    public void setNationality(String nationality) {
        this.nationality = nationality;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public String getPassportNumber() {
        return passportNumber;
    }

    public void setPassportNumber(String passportNumber) {
        this.passportNumber = passportNumber;
    }

    public String getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(String dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public String getExpiryDate() {
        return expiryDate;
    }

    public void setExpiryDate(String expiryDate) {
        this.expiryDate = expiryDate;
    }


    public void setLogo(ImageView imageView, Activity activity) {
        if (!AgencyData.getInstance().getAgencyLogo().equals("")) {
            Glide.with(activity)
                    .load(AgencyData.getInstance().getAgencyLogo())
                    .fitCenter()
                    .placeholder(R.drawable.logo)
                    .into(imageView);
        }
    }

    public Drawable getDrawableWithRadius() {

        GradientDrawable gradientDrawable = new GradientDrawable();
        gradientDrawable.setCornerRadii(new float[]{20, 20, 20, 20, 20, 20, 20, 20});
//        gradientDrawable.setColor(getBackgroundColorCode());
        return gradientDrawable;
    }
}
