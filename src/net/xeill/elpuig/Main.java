package net.xeill.elpuig;

// Scanner, Locale i Arrays
import java.util.Scanner;
import java.util.Locale;
import java.util.Arrays;

// Llibreries de fitxers
import java.io.File;
import java.io.FileWriter;
import java.io.FileNotFoundException;
import java.io.IOException;

// Llibreries temps
import java.time.ZonedDateTime;

public class Main {
    static Controlador control = new Controlador();
    static Scanner scanner = new Scanner(System.in);
    // static File laMevaCarpeta;

    static File elMeuFile;

    // Defineixo la meva carpeta
    static File carpeta = new File("data");

    static Scanner myReader;

    static String informacioACarregar;

    // Les relacions del diagrama de classes són també atributs a Java).
    public static void main(String[] args) throws IOException {
        invocarArxiu();
        generarMenu();
    }

    private static String sistemaOperatiu() {
        String os = nomSistemaOperatiu();
        if (os.equals("Windows 10")) {
            return "\\";
        } else if (os.equals("Linux")) {
            return "/";
        }
        return "\\";
    }

    // Retorna el nom del sistema operatiu
    private static String nomSistemaOperatiu() {
        return System.getProperty("os.name");
    }

    // Invoca l'arxiu de data+/+carpeta mes recent +/+carregararxiu
    private static void invocarArxiu() throws IOException {
        informacioACarregar = carpeta + sistemaOperatiu() + carpetaDataMesRecent() + sistemaOperatiu()
                + fitxerRecentDeCarpetaRecent();
        carregarArxiu();
    }

    // Truca a informacioAcarregar
    private static void carregarArxiu() throws IOException {

        elMeuFile = new File(informacioACarregar);

        // s'aconsegueix
        try {
            myReader = new Scanner(elMeuFile);
            myReader.useLocale(Locale.ENGLISH);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }

        lecturaFitxer();
    }

    private static void generarMenu() throws IOException {
        int opcio = menu();

        while (opcio != -1) {
            if (opcio == 1) {
                lectura();
            } else if (opcio == 2) {
                lecturaAutomatica();
            } else if (opcio == 3) {
                mostrarUna();
            } else if (opcio == 4) {
                mostrarTotes();
            } else if (opcio == 5) {
                carregarInformacioConcreta();
            } else if (opcio == 6) {
                esborrarHoraConcreta();
            } else if (opcio == 7) {
                esborrarDiaConcret();
            } else if (opcio == 8) {
                comprovarCarpeta();
                escripturaFitxer();
                break;
            } else {
                System.out.println("Opció invalida");
            }
            opcio = menu();
        }
    }

    private static int menu() {

        // 1. Mostrar les opcions del menú
        System.out.println(" 1. Afegir una comunitat autònoma de forma manual.");
        System.out.println(" 2. Afegir una comunitat autònoma de forma automàtica.");
        System.out.println(" 3. Mostrar les dades d'una sola comunitat autònoma.");
        System.out.println(" 4. Mostrar les dades de totes les comunitats autònomes.");
        System.out.println(" 5. Carregar informació concreta.");
        System.out.println(" 6. Esborrar una hora concreta.");
        System.out.println(" 7. Esborrar un dia concret.");
        System.out.println(" 8. Sortir.");

        // 2. Llegir l'opció escollida per l'usuari
        System.out.print("Quina opció selecciones? ");

        // 3. Retornar aquesta opció
        return scanner.nextInt();
    }

    // Cada opció del menú invocarà un mètode.
    private static void lectura() {
        // 1. Preguntar a l'usuari per les dades de l'ítem i llegir-les
        System.out.print("Escriu el nom de la comunitat autònoma: ");
        scanner.nextLine(); // Per agafar el salt de línia
        String nomTemporal = scanner.nextLine();

        System.out.print("Escriu la quantitat d'habitants que hi ha a " + nomTemporal + ": ");
        int habitantsTemporal = scanner.nextInt();

        System.out.print("Afegeix el valor densitat/habitant de " + nomTemporal + ": ");
        float densitatHabitantTemporal = scanner.nextFloat();

        scanner.nextLine(); // Aquest nextLine és per consumir el salt de línia, així no es posa a la
        // capital
        System.out.print("Afegeix la capital de " + nomTemporal + ": ");
        String capitalTemporal = scanner.nextLine();

        // 2. Enviar les dades recollides al controlador

        control.add(nomTemporal, habitantsTemporal, densitatHabitantTemporal, capitalTemporal);
        // System.out.println(control.get(idTemporal));
        System.out.println("La informació s'ha afegit correctament");
    }

    private static void lecturaAutomatica() {
        // Afegim Comunitat autònomes automàtiques
        control.add("Extremadura", 1054245, 25.32f, "Mérida");
        control.add("Principado de Asturias", 1004499, 94.73f, "Oviedo");
        control.add("Comunidad Foral de Navarra", 663612, 63.86f, "Pamplona");
        control.add("Cantabria", 585222, 110.40f, "Santander");
        control.add("La Rioja", 319485, 63.33f, "Logroño");
        System.out.println("Creant informació de demo... OK!");
    }

    private static void mostrarUna() {
        System.out.print("Escriu l'ID de la comunitat autònoma que vols mostrar: ");
        int seleccionarID = scanner.nextInt();
        if (control.get(seleccionarID) != null) {
            System.out.println(control.get(seleccionarID).toString());
        } else {
            System.out.println("No l'he trobada :(");
        }

    }

    private static void mostrarTotes() {
        System.out.println(control.getAll());
    }

    private static void lecturaFitxer() {
        // El problema del codi es que si escollim l'opcio 6, l'info carregada
        // anteriorment,
        // el que passa es que aquesta informacio quedara en memoria i al guardar
        // l'arxiu, la informacio del ultim arxiu
        // es quedará, al guardar el nou arxiu es guardará l'informació importada i la
        // informacio que es va obrir al principi
        control.esborrarInfoArrayList();
        while (myReader.hasNextLine()) {
            String frase = myReader.nextLine();
            String[] words = frase.split(",");
            control.add(words[0], Integer.parseInt(words[1]), Float.parseFloat(words[2]), words[3]);
        }

    }

    private static void escripturaFitxer() throws IOException {
        FileWriter escriptor = new FileWriter(carpeta + sistemaOperatiu() + carpetaDataMesRecent() + sistemaOperatiu()
                + fitxerRecentDeCarpetaRecent());
        escriptor.write(control.getTot());
        escriptor.close();
    }

    private static String carpetaDataMesRecent() throws IOException {
        existeixCarpeta();
        String recent;
        String[] carpetes = carpeta.list();

        Arrays.sort(carpetes); // Si l'array es [2023-05-01, 2023-05-02, 2023-05-03] --> 2023-05-03
        recent = carpetes[carpetes.length - 1];
        return recent;

    }

    /*
     * Aquest metode s'executa abans del carpetaDataMesRecent, si el programa fosi
     * nou i no hagues cap carpeta
     * amb cap data el programa petaria, ja que en el metode carpetaDataMesRecent
     * estem veient el carpetes.length-1
     */
    /* Ens assegurem que no peti amb aquest condicional */
    private static void existeixCarpeta() throws IOException {
        String[] carpetes = carpeta.list();
        if (carpetes.length == 0) {
            creaCarpetaDiaActual();
        }
    }

    private static String fitxerRecentDeCarpetaRecent() throws IOException {
        String recent = "";
        File dinsCarpeta = new File("data" + sistemaOperatiu() + carpetaDataMesRecent() + sistemaOperatiu());
        String[] files = dinsCarpeta.list();
        // Hem d'ordenar l'array
        Arrays.sort(files);
        // No hauria d'haver problema amb un files -1, ja que si existeix una carpeta,
        // existeix un arxiu
        if (files.length != 0) {
            recent = files[files.length - 1];
        } else {
            autoGenerarArxiu();
        }

        return recent;

    }

    private static String horaActual() {
        return horaActualSO();
    }

    private static String horaActualSO() {
        int horaActual = ZonedDateTime.now().getHour();
        int minutActual = ZonedDateTime.now().getMinute();
        int segonActual = ZonedDateTime.now().getSecond();

        String stringHoraActual;
        String stringMinutActual;
        String stringSegonActual;
        String resultat;

        if (horaActual < 10) {
            stringHoraActual = "0" + horaActual;
        } else {
            stringHoraActual = String.valueOf(horaActual);
        }

        if (minutActual < 10) {
            stringMinutActual = "0" + minutActual;
        } else {
            stringMinutActual = String.valueOf(minutActual);
        }

        if (segonActual < 10) {
            stringSegonActual = "0" + segonActual;
        } else {
            stringSegonActual = String.valueOf(segonActual);
        }

        resultat = stringHoraActual + "_" + stringMinutActual + "_" + stringSegonActual;

        return resultat;
    }

    private static String diaActual() throws IOException {
        int diaActual = ZonedDateTime.now().getDayOfMonth();
        int mesActual = ZonedDateTime.now().getMonthValue();
        int anyActual = ZonedDateTime.now().getYear();

        String stringDiaActual;
        String stringMesActual;
        String resultat;

        if (diaActual < 10) {
            stringDiaActual = "0" + diaActual;
        } else {
            stringDiaActual = String.valueOf(diaActual);
        }

        if (mesActual < 10) {
            stringMesActual = "0" + mesActual;
        } else {
            stringMesActual = String.valueOf(mesActual);
        }

        resultat = anyActual + "-" + stringMesActual + "-" + stringDiaActual;
        return resultat;
    }

    private static void comprovarCarpeta() throws IOException {
        if (carpetaDataMesRecent().equals(diaActual())) {
            autoGenerarArxiu();

        } else {
            creaCarpetaDiaActual();

        }
    }

    private static void creaCarpetaDiaActual() throws IOException {
        // Creo que no funciona
        File carpetaNova = new File(carpeta + sistemaOperatiu() + diaActual());

        if (!carpetaNova.exists()) {
            carpetaNova.mkdirs();
        }
        autoGenerarArxiu();
        comprovarCarpeta();

    }

    private static void autoGenerarArxiu() throws IOException {
        File arxiu = new File(carpeta + sistemaOperatiu() + diaActual() + sistemaOperatiu() + horaActual());
        FileWriter a = new FileWriter(arxiu);
        a.close();
    }

    private static void carregarInformacioConcreta() throws IOException {
        String[] carpetaData = carpeta.list();
        Arrays.sort(carpetaData);
        System.out.println("CARPETES DISPONIBLES:");
        // System.out.println("Format: yyyy-mm-dd (posa el valor numeric 1,2,3,4...)");
        for (int i = 0; i < carpetaData.length; i++) {
            System.out.println(i + 1 + ") " + carpetaData[i]);
        }
        System.out.println((carpetaData.length + 1) + ") Tornar al menú");
        System.out.print("Selecciona una data: ");
        int opcio = scanner.nextInt();
        String[] array = carpeta.list();
        if (opcio == carpetaData.length + 1) {
            System.out.println("Tornant al menu...");
            System.out.println();
            return;
        }
        Arrays.sort(array);

        for (int i = 0; i < array.length; i++) {
            if (opcio == i + 1) {
                File carpetaSeleccionada = new File(carpeta + sistemaOperatiu() + array[i]);
                String[] FileCarpetaSeleccionada = carpetaSeleccionada.list();
                if (FileCarpetaSeleccionada.length <= 0) {
                    System.out.println("ERROR: Aquesta carpeta esta buida.");
                    System.out.println();
                    return;
                }
                Arrays.sort(FileCarpetaSeleccionada);
                for (int j = 0; j < FileCarpetaSeleccionada.length; j++) {
                    System.out.println((j + 1) + ") " + FileCarpetaSeleccionada[j]);
                }
                System.out.println((FileCarpetaSeleccionada.length + 1) + ") Tornar al menú");
                // System.out.println("Data seleccionada: " + carpetaSeleccionada.getName());
                System.out.print("Selecciona una hora: ");

                int seleccionada = scanner.nextInt();
                if (seleccionada == FileCarpetaSeleccionada.length + 1) {
                    System.out.println("Tornant al menu...");
                    System.out.println();
                    return;
                }
                String arxiuSeleccionat = FileCarpetaSeleccionada[seleccionada - 1];
                informacioACarregar = carpeta + sistemaOperatiu() + array[i] + sistemaOperatiu() + arxiuSeleccionat;
                System.out.println("Informació carregada: " + informacioACarregar);
                carregarArxiu();

            }

        }

    }

    private static void esborrarHoraConcreta() {
        String[] carpetaData = carpeta.list();
        Arrays.sort(carpetaData);
        System.out.println("CARPETES DISPONIBLES:");
        // System.out.println("Format: yyyy-mm-dd (posa el valor numeric 1,2,3,4...)");
        for (int i = 0; i < carpetaData.length; i++) {
            System.out.println(i + 1 + ") " + carpetaData[i]);
        }
        System.out.println((carpetaData.length + 1) + ") Tornar al menú");
        System.out.print("Selecciona una carpeta: ");
        int opcio = scanner.nextInt();
        if (opcio == carpetaData.length + 1) {
            System.out.println("Tornant al menu...");
            System.out.println();
            return;
        }
        String[] array = carpeta.list();
        Arrays.sort(array);
        for (int i = 0; i < array.length; i++) {
            if (opcio == i + 1) {
                File carpetaSeleccionada = new File(carpeta + sistemaOperatiu() + array[i]);
                String[] FileCarpetaSeleccionada = carpetaSeleccionada.list();
                Arrays.sort(FileCarpetaSeleccionada);
                for (int j = 0; j < FileCarpetaSeleccionada.length; j++) {
                    System.out.println((j + 1) + ") " + FileCarpetaSeleccionada[j]);
                }
                if (FileCarpetaSeleccionada.length <= 0) {
                    System.out.println("ERROR: Aquesta carpeta esta buida.");
                    System.out.println();
                    return;
                }
                System.out.println((FileCarpetaSeleccionada.length + 1) + ") Tornar al menú");
                System.out.print("Selecciona un fitxer: ");
                int seleccionada = scanner.nextInt();
                if (seleccionada == FileCarpetaSeleccionada.length + 1) {
                    System.out.println("Tornant al menu...");
                    System.out.println();
                    return;
                }
                String arxiuSeleccionat = FileCarpetaSeleccionada[seleccionada - 1];
                File eliminar = new File(carpeta + sistemaOperatiu() + array[i] + sistemaOperatiu() + arxiuSeleccionat);
                if (eliminar.isFile()) {
                    eliminar.delete();
                    if (eliminar.getAbsolutePath().equals(elMeuFile.getAbsolutePath())) {
                        myReader.close();
                        eliminar.delete();
                    }
                    if (!eliminar.exists()) {
                        System.out.println("L'arxiu " + arxiuSeleccionat + " s'ha esborrat correctament :)");
                    } else {
                        System.out.println("No s'ha pogut esborrar.");
                    }
                    System.out.println();
                } else {
                    System.out.println("No es un arxiu.");
                }
                // System.out.print(ruta);

            }
        }
    }

    private static void esborrarDiaConcret() {
        String[] carpetaData = carpeta.list();
        Arrays.sort(carpetaData);

        System.out.println("CARPETES DISPONIBLES:");
        // System.out.println("Format: yyyy-mm-dd (posa el valor numeric 1,2,3,4...)");
        for (int i = 0; i < carpetaData.length; i++) {
            System.out.println(i + 1 + ") " + carpetaData[i]);
        }
        System.out.println((carpetaData.length + 1) + ") Tornar al menú");
        System.out.print("Selecciona una carpeta: ");
        int opcio = scanner.nextInt();
        String[] array = carpeta.list();
        if (opcio == carpetaData.length + 1) {
            System.out.println("Tornant al menu...");
            System.out.println();
            return;
        }
        Arrays.sort(array);
        for (int i = 0; i < array.length; i++) {
            if (opcio == i + 1) {
                File carpetaSeleccionada = new File(carpeta + sistemaOperatiu() + array[i]);
                String[] FileCarpetaSeleccionada = carpetaSeleccionada.list();
                String[] arxiusEliminar = carpetaSeleccionada.list();
                Arrays.sort(FileCarpetaSeleccionada);
                for (int j = 0; j < arxiusEliminar.length; j++) {
                    File arxiu = new File(carpetaSeleccionada + sistemaOperatiu() + arxiusEliminar[j]);

                    if (arxiu.isFile()) {
                        arxiu.delete();
                    }
                    if (!arxiu.exists()) {
                        System.out.println("Esborrat correctament: " + arxiu);
                    } else {
                        if (arxiu.getAbsolutePath().equals(elMeuFile.getAbsolutePath())) {
                            myReader.close();
                            arxiu.delete();
                            if (!arxiu.exists()) {
                                System.out.println("Esborrat correctament: " + arxiu);
                            }
                        }
                    }
                }
                FileCarpetaSeleccionada = carpetaSeleccionada.list();
                if (FileCarpetaSeleccionada.length == 0) {
                    String nom = carpetaSeleccionada.getName();
                    carpetaSeleccionada.delete();
                    System.out.println("S'ha esborrat correctament la data: " + nom);
                    System.out.println();
                } else {
                    System.out.println("No s'ha pogut esborrar el directori.");
                }

            }
        }

    }

}
