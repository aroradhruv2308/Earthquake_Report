package com.example.android.quakereport;

public class quake_data {
    private String mStateName;
    private String mAmplitude;
    private String mDate;
    private String mTime;
    private String mRefKms;
    private String mUrl;
    //constructor
    public quake_data(String StateName,String refKms,String Amplitude,String Date,String Time,String url){
        mStateName = StateName;
        mAmplitude = Amplitude;
        mDate = Date;
        mRefKms = refKms;
        mTime = Time;
        mUrl = url;

    }
    //methods getters and setters
    public String getDate(){return mDate;}
    public String getAmplitude(){return mAmplitude;}
    public String getStateName(){return mStateName;}
    public String getRefKms() {return mRefKms;}
    public String getTime() {return mTime;}
    public String getUrl(){return mUrl;}
}
