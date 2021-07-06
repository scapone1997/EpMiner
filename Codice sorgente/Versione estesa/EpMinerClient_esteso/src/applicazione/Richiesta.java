package applicazione;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;

/**
 * Classe che permette la comunicazione fra Client e Server.
 */

class Richiesta {
    private static Socket socket = null;
    private static int PORT = 8080;
    private static int CONNESSO = 0;
    private static InetAddress addr;
    private static ObjectInputStream in;
    private static ObjectOutputStream out;
    private String risultato = new String();

    /**
     * Costruttore della classe Richiesta.
     */
    Richiesta() {
        try {
            addr = InetAddress.getByName("127.0.0.1");
        } catch (UnknownHostException exception) { };
    }

    /**
     * Metodo che costruisce una nuova Socket per comunicare con il Server.
     * @throws IOException
     */
    static void connetti() throws IOException {
        if (CONNESSO == 0) {
            socket = new Socket(addr, PORT);
            CONNESSO = 1;
            out = new ObjectOutputStream(socket.getOutputStream());
            in = new ObjectInputStream(socket.getInputStream());
        }
    }

    /**
     * Metodo che chiude la Socket nel caso sia aperta.
     * @throws IOException
     */
    static void disconnetti() throws IOException {
        if (CONNESSO != 0) {
            socket.close();
            CONNESSO = 0;
        }
    }

    /**
     * Metodo che comunica con il Server al fine di ottenere i pattern frequenti e i pattern emergenti.
     * @param target Nome della tabella Target.
     * @param background Nome della tabella Background.
     * @param servizio Valore che indica la tipologia di servizio: 1 ricerca nuovi pattern, 2 ricerca in archivio.
     * @param supporto Valore numerico rappresentante il minimo supporto.
     * @param growrate Valore numerico rappresentante il minimo growrate.
     * @param file Nome del file da utilizzare per leggere/scrivere i risultati.
     * @return stringa rappresentante i pattern frequenti ed emergenti o un messaggio che indica l'impossibilità di elaborare la richiesta.
     */
    String chiediServizio(String target, String background, int servizio, float supporto, float growrate, String file) {
        try {
            out.writeObject(Integer.valueOf(servizio));
            out.writeObject(Float.valueOf(supporto));
            out.writeObject(Float.valueOf(growrate));
            out.writeObject(target);
            out.writeObject(background);
            out.writeObject(file);
            String fpMiner = (String)in.readObject();
            String epMiner = (String)in.readObject();
            StringBuilder s = new StringBuilder();
            s.append(fpMiner);
            s.append(epMiner);
            this.risultato = s.toString();
        } catch (IOException | ClassNotFoundException e) {
            this.risultato = "Impossibile elaborare la richiesta.\nAssicurarsi che il server sia acceso e che i campi del form siano tutti riempiti.";
        }
        return this.risultato;
    }
}
