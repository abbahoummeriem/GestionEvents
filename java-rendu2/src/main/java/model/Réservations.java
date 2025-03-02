package model;

import java.util.Date;

public class Réservations {
   int id_user;
   int id_event;
   int id_salle ;
   int id_terrain;
   Date reservatiion;
   public Réservations(int l,int e,int k,int m,Date p){
    id_user=l;
    id_event=e;
    id_salle=k;
    id_terrain=m;
     reservatiion=p;
   }
public Réservations(){}
    public int getId_event() {
        return id_event;
    }

    public int getId_salle() {
        return id_salle;
    }

    public int getId_terrain() {
        return id_terrain;
    }

    public int getId_user() {
        return id_user;
    }

    public Date getReservatiion() {
        return reservatiion;
    }
  public void setId_event(int id_event) {
      this.id_event = id_event;
  }
  public void setId_salle(int id_salle) {
      this.id_salle = id_salle;
  }
  public void setId_terrain(int id_terrain) {
      this.id_terrain = id_terrain;
  }
  public void setId_user(int id_user) {
      this.id_user = id_user;
  }
  public void setReservatiion(Date reservatiion) {
      this.reservatiion = reservatiion;
  }
}
