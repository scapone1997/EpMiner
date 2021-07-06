package applicazione;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;

import java.io.IOException;

/**
 * Classe rappresentante le azioni della prima schermata dell'applicazione.
 */
public class ScenaUno {
    @FXML
    private Button inizia;
    @FXML
    private Button esci;
    @FXML
    private TextArea introduzione;

    /**
     * Metodo che permette di chiudere l'applicazione.
     * @param actionEvent click del bottone "Esci".
     */
    public void chiudiProgramma(ActionEvent actionEvent) {
        Platform.exit();
    }

    /**
     * Metodo che permette di spostarsi nella seconda schermata dell'applicazione.
     * @param actionEvent click del bottone "Inizia".
     */
    public void iniziaScoperta(ActionEvent actionEvent) {
        ControlloreScene cs = new ControlloreScene();
        try{
            cs.scenaDue(actionEvent);
        }catch(IOException e){};
    }
}