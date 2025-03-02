package model;
public class salle {
    private int id_salle;
    private  String nom_salle;
    private  String capacite;
    public salle(){}
    public String getNom_salle(){
        return nom_salle;
    }
    public int  getId_salle(){
       return id_salle;
    }
    public void  setId_salle(int l){
        id_salle=l;
     }
    public String getCapacite(){
        return capacite;
    }
    public void setCapacite(String capacite) {
        this.capacite = capacite;
    }
    public void setNom_salle(String nom_salle) {
        this.nom_salle = nom_salle;
    }
    @Override
    public String toString() {
        return "salle{" +
                "nom='" + nom_salle+ '\'' +
                ", capacité'" + capacite + '\'' +
                '}';
    }
}
