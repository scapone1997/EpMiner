package applicazione;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * Classe che permette di avviare la prima scena dell'applicazione.
 */
public class App extends Application {

    /**
     * Metodo per l'avvio della prima finestra dell'applicazione.
     * @param primaryStage stage iniziale dell'applicazione.
     * @throws Exception
     */
    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("Scena1.fxml"));
        primaryStage.setTitle("EpMiner");
        Scene scene = new Scene(root);
        scene.getStylesheets().add(getClass().getResource("Tema.css").toExternalForm());
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    /**
     * Metodo main per l'avvio dell'applicazione.
     */
    public static void main() {
        launch();
    }
}