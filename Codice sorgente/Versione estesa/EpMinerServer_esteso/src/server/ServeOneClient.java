package server;

import data.Data;
import data.EmptySetException;
import database.DatabaseConnectionException;
import database.NoValueException;
import mining.EmergingPatternMiner;
import mining.FrequentPatternMiner;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.SocketException;
import java.sql.SQLException;

/**
 * Classe che modella il comportamento del server dopo la connessione di un client.
 */
public class ServeOneClient extends Thread {
    private Socket socket;
    private ObjectInputStream in;
    private ObjectOutputStream out;

    /**
     * Costruttore di classe che inizializza il socket, gli stream di Input e Output e avvia il thread.
     * @param s Socket.
     * @throws IOException Eccezione per problemi I/O.
     */
    public ServeOneClient(Socket s) throws IOException {
        this.socket = s;
        in = new ObjectInputStream(s.getInputStream());
        out = new ObjectOutputStream(s.getOutputStream());
        start();
    }

    /**
     * Metodo che esegue le richieste del client e salva su file dat il risultato.
     * Il nome del file è generato automaticamente usando gli estremi del metodo di ricerca preceduti da "ARCHIVE__".
     */
    @Override
    public void run() {
        System.out.println("Nuovo client connesso.");
        //La cartella archivio la metto sul Desktop
        String archivePath = System.getProperty("user.home") + "/Desktop/EpMiner_Archive/";
        boolean continua = true; //per fare più richieste nella stessa sessione
        while (continua) {
            boolean successo = true; //per capire se la richiesta è andata a buon fine
            try {
                //ricevo richiesta dal client
                int opzione = (int) in.readObject();
                float minsup = (float) in.readObject();
                float minGr = (float) in.readObject();
                String targetName = (String) in.readObject();
                String backgroundName = (String) in.readObject();
                String nameFile = (String) in.readObject();
                System.out.println("Elaborazione nuova richiesta...");

                //elabora dati...
                FrequentPatternMiner fpMiner = null;
                EmergingPatternMiner epMiner = null;
                if (opzione == 1) {
                    try {
                        Data dataTarget = new Data(targetName);
                        Data dataBackground = new Data(backgroundName);

                        try {
                            fpMiner = new FrequentPatternMiner(dataTarget, minsup);
                            try {
                                fpMiner.salva(archivePath + "ARCHIVE__" + nameFile
                                        + "_FP_playtennis_minSup" + minsup + ".dat");
                            } catch (IOException e1) {
                                e1.printStackTrace();
                            }
                        } catch (EmptySetException e) {
                            //System.out.println(e);
                        }
                        try {
                            epMiner = new EmergingPatternMiner(dataBackground, fpMiner, minGr);
                            try {
                                epMiner.salva(archivePath + "ARCHIVE__" + nameFile
                                        + "_EP_playtennis_minSup" + minsup + "_minGr" + minGr + ".dat");
                            } catch (IOException e1) {
                                e1.printStackTrace();
                            }
                        } catch (EmptySetException e) {
                            //System.out.println(e);
                        }

                    } catch (SQLException e) {
                        System.out.println("Tabella non trovata.");
                        try {
                            out.writeObject("Impossibile elaborare la richiesta.\n");
                            out.writeObject("Non è stata trovata una tabella avente il nome da lei indicato.");
                        } catch (IOException ioException) {
                            ioException.printStackTrace();
                        }
                        successo = false;
                    } catch (NoValueException e) {
                        System.out.println("Dati non trovati.");
                        try {
                            out.writeObject("Impossibile elaborare la richiesta.\n");
                            out.writeObject("Non è stata trovata corrispondenza con i dati da lei inseriti.");
                        } catch (IOException ioException) {
                            ioException.printStackTrace();
                        }
                        successo = false;
                    }
                } else {
                    try {
                        fpMiner = FrequentPatternMiner.carica(archivePath + "ARCHIVE__" + nameFile
                                + "_FP_playtennis_minSup" + minsup + ".dat");
                        epMiner = EmergingPatternMiner.carica(archivePath + "ARCHIVE__" + nameFile
                                + "_EP_playtennis_minSup" + minsup + "_minGr" + minGr + ".dat");
                    } catch (FileNotFoundException e) {
                        System.out.println("File non trovato.");
                        out.writeObject("Impossibile elaborare la richiesta.\n");
                        out.writeObject("Non è stata trovata nessuna tabella in archivio con i dati da lei inseriti.");
                        successo = false;
                    }
                }

                //...e manda in output al client
                if (successo) {
                    assert fpMiner != null;
                    out.writeObject(fpMiner.toString() + "\n");
                    assert epMiner != null;
                    out.writeObject(epMiner.toString());
                    System.out.println("Richiesta completata con successo.");
                }
            } catch (SocketException e) {
                //se non riesco più ad accedere al socket, vuol dire che il client si è disconnesso
                try {
                    socket.close();
                    System.out.println("Closing...");
                } catch (IOException ioException) {
                    ioException.printStackTrace();
                    System.out.println("Errore, il Socket non si chiude!");
                }
                continua = false;
            } catch (ClassNotFoundException | IOException | DatabaseConnectionException e) {
               // se c'è un errore che impedisce di leggere o scrivere dati, forzo la chiusura
                //System.err.println("Exception Error");
                //e.printStackTrace();
                try {
                    socket.close();
                    System.out.println("Closing...");
                } catch (IOException ioException) {
                    ioException.printStackTrace();
                    System.err.println("Errore, il Socket non si chiude!");
                }
                continua = false;
            }
        }
    }
}
