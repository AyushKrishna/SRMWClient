package com.example.ak_x64.srmclient3_v2.app.ui;

/**
 * Created by AK on 24-03-2015.
 */
public enum NotificationTypes {
    ATTENDANCE("Attendance Received","Check your attendance",1);

    private int notifID;
    private String title;
    private String content;

    NotificationTypes(String title,String content,int ID){
        this.title=title;
        this.content=content;
        notifID=ID;
    }

    public int getNotifID(){
        return notifID;
    }

    public String getNotificationTitle(){
        return title;
    }

    public String getNoficationContent(){ return content;}
}
