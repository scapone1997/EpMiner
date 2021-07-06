package server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Classe che gestisce il comportamento di un server in attesa di richiesta di connessione da parte dei client.
 */
public class MultiServer {
    private static final int PORT = 8080;

    /**
     * Costruttore del MultiServer che avvia il tread. Il numero di port predefinito � 8080.
     */
    private MultiServer() {
        try {
            run();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Metodo che rappresenta il punto di avvio del thread.
     * @throws IOException Eccezione per problemi I/O.
     */
    private void run() throws IOException {
        ServerSocket s = new ServerSocket(PORT);
        System.out.println("Started: " + s);
        try {
            while (true) {
                // Si blocca finch� non si verifica una connessione:
                Socket socket = s.accept();
                try {
                    new ServeOneClient(socket);
                } catch (IOException e) {
                // Se fallisce chiude il socket,
                // altrimenti il thread la chiuder�:
                    socket.close();
                }
            }
        } finally {
            s.close();
        }
    }

    /**
     * Metodo che avvia il MultiServer.
     */
    public static void main(String[] args) {
        new MultiServer();
    }

}
