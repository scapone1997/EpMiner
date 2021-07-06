package applicazione;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

import java.io.IOException;

/**
 * Classe rappresentante le azioni della seconda schermata dell'applicazione.
 */
public class ScenaDue {
    @FXML
    private Button indietro;

    @FXML
    private Button elabora;

    @FXML
    private Button cancella;

    @FXML
    private Button esci;

    @FXML
    private TextField tabellaTarget;

    @FXML
    private TextField tabellaBackground;

    @FXML
    private TextField minSup;

    @FXML
    private TextField minGrow;

    @FXML
    private TextArea risultati;

    @FXML
    private CheckBox nuovo;

    @FXML
    private CheckBox archivio;

    /**
     * Metodo che permette di inviare una richiesta al Server con tutte le informazioni
     * utili per la ricerca dei pattern frequenti ed emergenti. Stampa all'interno della TextArea il risultato della richiesta.
     * @param actionEvent click del bottone "Elabora".
     */
    public void elaboraRichiesta(ActionEvent actionEvent) {
        String target = this.tabellaTarget.getText();
        String background = this.tabellaBackground.getText();
        String supporto = this.minSup.getText();
        String growrate = this.minGrow.getText();

        try {
            if (target.equals("") || background.equals("") || supporto.equals("") || growrate.equals("")) {
                this.risultati.setText("Inserire tutti i dati nel form.");
            } else if (!this.archivio.isSelected() && !this.nuovo.isSelected()) {
                this.risultati.setText("Scegliere opzione fra Nuovo Pattern o Risultati in archivio");
            } else {
                String file = this.tabellaTarget.getText() + "_" + this.tabellaTarget.getText();
                String esito = new String();
                Float f_sup = Float.valueOf(Float.parseFloat(supporto));
                Float f_grow = Float.valueOf(Float.parseFloat(growrate));
                if (f_sup.floatValue() <= 0.0F || f_sup.floatValue() > 1.0F || f_grow.floatValue() <= 0.0F) {
                    this.risultati.setText("Valori di minimo supporto o growrate non validi. " +
                            "Si ricorda che per\npoter elaborare una richiesta bisogna rispettare i seguenti vincoli:\n" +
                            "il minimo supporto deve essere compreso fra 0 e 1 (estremo sinistro escluso);\n"+
                            "il minimo growrate deve essere maggiore di 0.");
                } else {
                    Richiesta r = new Richiesta();
                    try{
                        Richiesta.connetti();
                        esito = r.chiediServizio(target, background, scelta(), f_sup.floatValue(), f_grow.floatValue(), file);
                        this.risultati.setText(esito);
                    }catch(IOException e){
                        this.risultati.setText("Connessione al Server non avvenuta.");
                    }
                }
            }
        } catch (NumberFormatException e) {
            this.risultati.setText("Impossibile elaborare la richiesta. Formato minimo supporto o \nminimo growrate non valido. Inserire valore reale.");
        }catch(Exception e){
            this.risultati.setText("Impossibile elaborare la richiesta.");
        }
    }

    /**
     * Metodo per pulire le TextField e la TextArea presenti nell'interfaccia.
     * @param actionEvent click del bottone "Cancella".
     */
    public void cancellaForm(ActionEvent actionEvent) {
        this.tabellaTarget.setText("");
        this.tabellaBackground.setText("");
        this.minGrow.setText("");
        this.minSup.setText("");
        this.risultati.setText("");
    }

    /**
     * Metodo che permette di visualizzare la schermata introduttiva.
     * @param actionEvent click del bottone "Torna indietro".
     */
    public void tornaIndietro(ActionEvent actionEvent) {
        ControlloreScene cs = new ControlloreScene();
        try {
            cs.scenaUno(actionEvent);
            Richiesta.disconnetti();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Metodo per chiudere l'applicazione.
     * @param actionEvent click del bottone "Esci".
     */
    public void chiudiProgramma(ActionEvent actionEvent) {
        try {
            Richiesta.disconnetti();
        } catch (Exception exception) {}
        Platform.exit();
    }

    /**
     * Metodo che permette di selezionare il servizio "Ricerca in archivio" e di
     * cancellare la spunta dall'opzione "Nuovo Pattern".
     * @param actionEvent spunta dell' opzione "Ricerca in archivio" nella Check-Box.
     */
    public void risultatiInArchivio(ActionEvent actionEvent) {
        this.nuovo.setSelected(false);
    }

    /**
     * Metodo che permette di selezionare il servizio "Nuovo Pattern" e di
     * cancellare la spunta dall'opzione "Ricerca in archivio".
     * @param actionEvent spunta dell' opzione "Nuovo Pattern" nella Check-Box.
     */
    public void nuovoPattern(ActionEvent actionEvent) {
        this.archivio.setSelected(false);
    }

    /**
     * Metodo che permette di tradurre il risultato dell'opzione presente nella check-box in un intero.
     * @return intero rappresentante la tipologia di servizio da chiedere al Server.
     */
    private int scelta() {
        if (this.nuovo.isSelected())
            return 1;
        return 2;
    }
}