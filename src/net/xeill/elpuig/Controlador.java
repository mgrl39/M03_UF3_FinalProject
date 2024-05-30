package net.xeill.elpuig;

// importem l'ArrayList, ja que la classe tindrà un ArrayList privat per a emmagatzemar els ítems de la nostra pràctica.
import java.util.ArrayList;

public class Controlador {
    /*
     * Aquí els atributs necessaris
     * Les relacions del diagrama de classes són també atributs a Java).
     */

    // Creem un arrayList anomenat arrayItems (l'ArrayList de comunitats autonomes)
    private ArrayList<ComunitatAutonoma> arrayItems;

    /*
     * Afegir la classe <ComunitatAutonoma> vol dir que l'ArrayList només acceptarà
     * dades de tipus ComunitatAutonoma.
     * És el nostre tipus de dades.
     */

    // Creem un constructor:

    /*
     * El constructor és un métode especial amb el mateix nom que la classe,
     * serveix per inicialitzar el nostre array.
     * Sense aixó no estaria inicialitzat
     */

    // Incluïm un constructor vuit (sense paràmetres) que s'encarrega d'inicialitzar
    // l'ArrayList
    /*
     * El constructor vuit (sense paràmetres) s'encarrega d'inicialitzar l'ArrayList
     * (anomenat arrayItems),
     * és necessari que l'ArrayList estigui inicialitzat abans d'utilitzar-lo.
     * Si no s'inicialitza, no es reserva memoria i es produixirá l'error:
     * NullPointerException.
     */

    // Creem la VARIABLE PRIVADA identificador de tipus enter.
    private int identificador = 0;

    public Controlador() {
        // Hem de deixar el mètode Controlador públic per poder accedir a ell des del
        // Main
        // Amb la paraula especial NEW, reservem memoria per l'ArrayList
        this.arrayItems = new ArrayList<ComunitatAutonoma>();
    }

    // Si el constructor és públic, podem crear instàncies de la classe Controlador
    // i accedir a l'ArrayList de ComunitatAutonoma.

    // Al mètode add afegim les dades de la comunitat Autonoma
    public void add(String nom, int habitants, float densitatHabitant, String capital /* dades de l'ítem */) {
        // Creem una nova instància i la guardem dins de l'ArrayList
        // 1. Crear un nou ítem amb les dades rebudes.
        // identificador ++ primer incrementa i després li dona valor, és a dir:
        // en comptes de començar per 0, començará per 1
        ComunitatAutonoma comunitat = new ComunitatAutonoma(nom, ++identificador, habitants, densitatHabitant, capital);
        // 2. Afegir la comunitat que hem inicialitzat a l'ArrayList del Controlador.
        arrayItems.add(comunitat);
        /*
         * Es important tenir en compte que és el "Controlador" el que estableix el
         * valor de l'atribut ID.
         * i que no hi poden haver dos "items" amb el mateix "id"
         */

    }

    // El seguent metode es per obtenir un item a partir del seu ID
    // El mètode "get" rep l'identificador (id)
    public ComunitatAutonoma get(int id) {

        // 1. Cercar l'ítem amb aquest ID dins l'ArrayList.
        // aquest mètode rep l’identificador (id) d’un ítem i retorna l’ítem en questió.
        // arrayItems.size() és un INT, és la quantitat de posicions que tenim
        for (int i = 0; i < arrayItems.size(); i++) {
            // ArrayItems conté ComunitatAutonoma
            // En Array dinamic arrayItems posar el metode .get(posicio) es com fer
            // array[posicio]
            if (id == arrayItems.get(i).getId()) {
                // Podem posar tants return com volguem, quan utilitzem el return s'acaba
                // l'execució del metode
                return arrayItems.get(i);
            }
        }
        // 2. Retornar l'ítem
        // Si ha arribat aquí és que no ha trobat cap ID, és a dir:
        // No s'ha executat el return que està dins de la condició
        return null;
    }

    /*
     * Si volem accedir al Arraylist de ComunitatAutonoma des d'altre clase (que no
     * sigui controlador),
     * haurá falta crear un "getter" (mètode)
     */

    // Ha de ser públic per poder accedir desde altre classe, anomena getAll

    public String getAll() {
        String paraula = "";
        for (int i = 0; i < arrayItems.size(); i++) {
            paraula += arrayItems.get(i).toString();
        }
        return paraula;
    }

    public String getTot() {
        String text = "";
        for (int i = 0 ; i < arrayItems.size() ; i++) {
            text += arrayItems.get(i).getNom() + "," + arrayItems.get(i).getHabitants() + "," + arrayItems.get(i).getDensitatHabitant() + "," + arrayItems.get(i).getCapital();
            if (i != arrayItems.size()-1) {
                text += "\n";
            }
        }
        return text;
    }

    public void esborrarInfoArrayList() {
        arrayItems.clear();
        identificador = 0;
    }

}
