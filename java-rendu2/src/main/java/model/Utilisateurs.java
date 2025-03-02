package model;

public class Utilisateurs {
    private String nom;
    private String prenom;
    private String email;
    private String type;
    private String password;

    // Constructeur
    public Utilisateurs(String nom, String prenom, String email, String type, String password) {
        this.nom = nom;
        this.prenom = prenom;
        this.email = email;
        this.type = type;
        this.password = password;
    }

    public Utilisateurs() {}

    // Getters et Setters
    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getPrenom() {
        return prenom;
    }
    public String getFullname() {
        return prenom+" "+nom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }
    

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return "Utilisateurs{" +
                "nom='" + nom + '\'' +
                ", prenom='" + prenom + '\'' +
                ", email='" + email + '\'' +
                ", type='" + type + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}
