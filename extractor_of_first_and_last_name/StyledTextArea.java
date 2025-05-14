package extractor_of_first_and_last_name;

import javafx.scene.control.TextArea;

/**
 * Questa classe estende la classe TextArea di JavaFX e fornisce uno stile
 * predefinito per le TextAreas dell'applicazione.
 */
public class StyledTextArea extends TextArea {

    /**
     * Costruttore di default per StyledTextArea. Imposta lo stile predefinito della
     * TextArea.
     */
    public StyledTextArea() {
        initStyle();
    }

    /**
     * Costruttore per StyledTextArea che accetta una stringa di testo. Imposta il
     * testo della TextArea e lo stile predefinito.
     *
     * @param text Il testo da visualizzare nella TextArea.
     */
    public StyledTextArea(String text) {
        super(text);
        initStyle();
    }

    /**
     * Inizializza lo stile predefinito della TextArea.
     */
    private void initStyle() {
        getStyleClass().add("text-area"); // Aggiunge la classe CSS per lo stile di base
        setStyle("-fx-border-color: transparent; -fx-border-width: 2px;");
    }
}
