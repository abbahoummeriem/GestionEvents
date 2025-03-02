package model;
public class Terrains {
  private int id_terrain;
    private String  nom_terrain;
    private String type;
    public int getId_terrain(){
      return id_terrain;
    }
    public void setId_terrain(int p){
      id_terrain=p;
    }
  public String getNom(){
    return  nom_terrain;
  }
  public String getType(){
    return type;
  }
 public void setNom(String nom){
    this.nom_terrain=nom;
 }
 public void setType(String type){
    this.type=type;
 }
@Override
 public String toString() {
  return "Terrains{" +
          "nom='" + nom_terrain + '\'' +
          ", tupe'" +type + '\'' +
          '}';
}


}
