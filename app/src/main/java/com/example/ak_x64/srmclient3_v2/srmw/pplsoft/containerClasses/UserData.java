package com.example.ak_x64.srmclient3_v2.srmw.pplsoft.containerClasses;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class UserData extends SRMWMessage {

    //TODO : Add data marks data also
    private String[][] attSubjectList; // set by host
    private String username; // set by client
    private String password; // set by client

    //This constructor is used only when UserData is present inside Login object, then in that case the Login object
    //already has got a copy of to,from etc... thus we only attach "subjectlist" in this object
    public UserData(String[][] attSubjectList){
        super(null,null,0,0);
        this.attSubjectList = attSubjectList;
    }

    public UserData(String to, String dest, int clientID, int task_id,String user,String pw,String[][] attSubjectList) {
        super(to, dest, clientID, task_id);
        this.attSubjectList = attSubjectList;
        username=user;
        password=pw;
        this.setType("request");
    }

    public String[][] getAttSubjectList() {
        return attSubjectList;
    }

    public void setAttSubjectList(String[][] attSubjectList) {
        this.attSubjectList = attSubjectList;
        this.setType("responce");
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }


    @Override
    public String getCustomJSONForm() {
        Gson gson = new GsonBuilder().serializeNulls().create();
        String j=gson.toJson(this);
        //System.out.println("Object2Json ; json->"+j);
        String j2="<"+this.getClass().getName()+">"+j;
        //System.out.println("Object2Json ; json final -> "+j2);
        return j2;
    }

}
