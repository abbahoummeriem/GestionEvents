package model;

import java.util.Date;


public class evenements {
    private int id_event;
    private String nom_event;
    private Date date_evnt;
    private String description;
    private int id_user;
    public evenements(){
  }
  public int getId_event() {
      return id_event;
  }
  public void setId_event(int k){
    id_event=k;
  }
    public evenements(String n,Date dt,String des,int id) {
        nom_event=n;
        date_evnt=dt;
        description=des;
        id_user=id;

    }


    
    public String getDescription() {
        return description;
    }

    public Date getDate_evnt() {
        return date_evnt;
    }

    public int getId_user() {
        return id_user;
    }

    public String getNom_event() {
        return nom_event;
    }
    public void setDescription(String description) {
        this.description = description;
    }
    public void setDate_evnt(Date event) {
        this.date_evnt = event;
    }
    public void setId_user(int id_user) {
        this.id_user = id_user;
    }
    public void setNom_event(String nom_event) {
        this.nom_event = nom_event;
    }
}
