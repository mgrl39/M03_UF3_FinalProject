package net.xeill.elpuig;

public class ComunitatAutonoma {

    // Creem una variable privada String de tipus enter
    private String nom;

    // Creem una variable privada ID de tipus enter.
    private int id;

    // Creem una variable privada habitants de tipus enter.
    private int habitants;

    // Creem una variable privada densitat (habitant/km²) tipus float
    private float densitatHabitant;
    // Aquesta variable representa la quantitat d'habitants per quilòmetre quadrat
    // Creem una variable privada capital de tipus String.
    private String capital;

    // Creem un constructor, el constructor te el mateix nom que la classe
    public ComunitatAutonoma(String nom, int id, int habitants, float densitatHabitant, String capital) {
        // Donem valors a les nostres variables amb el constructor
        // El this es per diferenciar
        this.nom = nom;
        this.id = id;
        this.habitants = habitants;
        this.densitatHabitant = densitatHabitant;
        this.capital = capital;

    }

    // Com hem fet privat les variables no les podem llegir al Main, hem de fer
    // un getter per cada variable privada.
    // Getters per obtenir el valor d'atributs privats:
    public String getNom() {
        return nom;
    }

    public int getId() {
        return id;
    }

    public int getHabitants() {
        return habitants;
    }

    public float getDensitatHabitant() {
        return densitatHabitant;
    }

    public String getCapital() {
        return capital;
    }

    public String toString() {

        // Retornar un string que conté tota la informació de l'ítem (ítem: valor\n).
        return "\nNom: " + nom + "\nID: " + id + "\nHabitants: " + habitants + "\nHabitants per km²: "
                + densitatHabitant + "\nCapital: " + capital + "\n";
    }
}
