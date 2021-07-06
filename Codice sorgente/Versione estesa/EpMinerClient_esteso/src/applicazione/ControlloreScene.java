package applicazione;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * Classe che permette di spostarsi fra le due finestre presenti nell'interfaccia grafica.
 */
class ControlloreScene {
    private Stage stage;
    private Scene scene;
    private Parent root;

    /**
     * Metodo che permette di visualizzare la prima schermata dell'interfaccia.
     * @param event click del bottone "Torna indietro".
     * @throws IOException
     */
    void scenaUno(ActionEvent event) throws IOException{
        root = FXMLLoader.load(getClass().getResource("Scena1.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        scene.getStylesheets().add(getClass().getResource("Tema.css").toExternalForm());
        stage.setScene(scene);
        stage.show();
    }

    /**
     * Metodo che permette di visualizzare la seconda schermata dell'interfaccia.
     * @param event click del bottone "Inizia".
     * @throws IOException
     */
    void scenaDue(ActionEvent event) throws IOException {
        root = FXMLLoader.load(getClass().getResource("Scena2.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        scene.getStylesheets().add(getClass().getResource("Tema.css").toExternalForm());
        stage.setScene(scene);
        stage.show();
    }
}
