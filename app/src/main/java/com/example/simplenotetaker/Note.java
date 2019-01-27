package com.example.simplenotetaker;

import android.content.Context;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;


public class Note implements Serializable {

    private long mDateTime;
    private String mTitle;
    private String mContent;

    //Constructor
    public Note(long DateTime, String Title, String Content) {
        mDateTime = DateTime;
        mTitle = Title;
        mContent = Content;
    }

    //Setter
    public void setmDateTime(long mDateTime) {
        this.mDateTime = mDateTime;
    }

    public void setmTitle(String mTitle) {
        this.mTitle = mTitle;
    }

    public void setmContent(String mContent) {
        this.mContent = mContent;
    }

    //Getter
    public long getmDateTime() {
        return mDateTime;
    }

    public String getmTitle() {
        return mTitle;
    }

    public String getmContent() {
        return mContent;
    }

    //Return DateTime to String but not just a milli-second format
    public String getDateTimeFormatted(Context context){
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy hh:mm:ss",
                context.getResources().getConfiguration().locale);
        sdf.setTimeZone(TimeZone.getDefault());
        return sdf.format(new Date(mDateTime));
    }

}
