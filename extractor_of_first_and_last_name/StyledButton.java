package extractor_of_first_and_last_name;

import javafx.animation.ParallelTransition;
import javafx.animation.RotateTransition;
import javafx.animation.ScaleTransition;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.util.Duration;

/**
 * Questa classe estende la classe Button di JavaFX e fornisce uno stile
 * predefinito e animazioni per i pulsanti dell'applicazione.
 */
public class StyledButton extends Button {

    /**
     * Costruttore di default per StyledButton. Imposta lo stile predefinito del
     * pulsante.
     */
    public StyledButton() {
        initStyle();
    }

    /**
     * Costruttore per StyledButton che accetta una stringa di testo. Imposta il
     * testo del pulsante e lo stile predefinito.
     *
     * @param text Il testo da visualizzare sul pulsante.
     */
    public StyledButton(String text) {
        super(text);
        initStyle();
    }

    /**
     * Costruttore per StyledButton che accetta un nodo e una stringa di testo.
     * Imposta il testo del pulsante e lo stile predefinito.
     *
     * @param text    Il testo da visualizzare sul pulsante.
     * @param graphic il nodo da visualizzare nel bottone
     */
    public StyledButton(String text, Node graphic) {
        super(text, graphic);
        initStyle();
    }

    /**
     * Inizializza lo stile predefinito del pulsante e imposta i gestori di eventi
     * per le animazioni.
     */
    private void initStyle() {
        getStyleClass().add("styled-button"); // Aggiungi una classe CSS per lo stile di base
        setStyle(
                "-fx-background-color: #4CAF50; -fx-text-fill: white; -fx-background-radius: 5px; -fx-padding: 10 20; -fx-font-size: 14px; -fx-font-weight: normal;");
        // Imposta i gestori di eventi per le animazioni
        setOnMouseEntered(createMouseEnterHandler());
        setOnMouseExited(createMouseExitHandler());
        setOnMousePressed(createMousePressHandler());
        setOnMouseReleased(createMouseReleaseHandler());
    }

    /**
     * Crea e restituisce un gestore di eventi per l'evento MouseEnter. Questo
     * gestore applica un effetto di ombra, aumenta la dimensione del pulsante e lo
     * ruota leggermente.
     *
     * @return Un EventHandler per l'evento MouseEnter.
     */
    private EventHandler<MouseEvent> createMouseEnterHandler() {
        return e -> {
            // Imposta lo stile di base e l'effetto ombra
            setStyle(
                    "-fx-background-color: #45a049; -fx-effect: dropshadow(three-pass-box, rgba(0, 0, 0, 0.2), 10, 0, 0, 5); -fx-cursor: hand; -fx-font-size: 16px; -fx-font-weight: bold;"); // Increased
                                                                                                                                                                                              // font
                                                                                                                                                                                              // size
                                                                                                                                                                                              // and
                                                                                                                                                                                              // bold
            // Crea l'animazione di scala
            ScaleTransition scale = new ScaleTransition(Duration.millis(200), this);
            scale.setToX(1.1);
            scale.setToY(1.1);
            // Crea l'animazione di rotazione
            RotateTransition rotate = new RotateTransition(Duration.millis(200), this);
            rotate.setToAngle(5);
            // Esegui le animazioni in parallelo
            ParallelTransition parallelTransition = new ParallelTransition(scale, rotate);
            parallelTransition.play();
        };
    }

    /**
     * Crea e restituisce un gestore di eventi per l'evento MouseExit. Questo
     * gestore ripristina lo stile originale del pulsante e annulla le animazioni.
     *
     * @return Un EventHandler per l'evento MouseExit.
     */
    private EventHandler<MouseEvent> createMouseExitHandler() {
        return e -> {
            // Ripristina lo stile originale del pulsante
            setStyle(
                    "-fx-background-color: #4CAF50; -fx-text-fill: white; -fx-background-radius: 5px; -fx-padding: 10 20; -fx-font-size: 14px; -fx-font-weight: normal;"); // Restored
                                                                                                                                                                           // font
                                                                                                                                                                           // size
                                                                                                                                                                           // and
                                                                                                                                                                           // normal
            ScaleTransition scale = new ScaleTransition(Duration.millis(200), this);
            scale.setToX(1.0);
            scale.setToY(1.0);
            RotateTransition rotate = new RotateTransition(Duration.millis(200), this);
            rotate.setToAngle(0);
            ParallelTransition parallelTransition = new ParallelTransition(scale, rotate);
            parallelTransition.play();
        };
    }

    /**
     * Crea e restituisce un gestore di eventi per l'evento MousePress. Questo
     * gestore riduce la dimensione del pulsante e lo ruota leggermente.
     *
     * @return Un EventHandler per l'evento MousePress.
     */
    private EventHandler<MouseEvent> createMousePressHandler() {
        return e -> {
            setStyle(
                    "-fx-background-color: #388E3C; -fx-text-fill: white; -fx-background-radius: 5px; -fx-padding: 10 20; -fx-font-size: 14px;"); // Keep
                                                                                                                                                  // original
                                                                                                                                                  // font
                                                                                                                                                  // size
                                                                                                                                                  // on
                                                                                                                                                  // pressed
            ScaleTransition scale = new ScaleTransition(Duration.millis(100), this);
            scale.setToX(0.9);
            scale.setToY(0.9);
            RotateTransition rotate = new RotateTransition(Duration.millis(100), this);
            rotate.setToAngle(5);
            ParallelTransition parallelTransition = new ParallelTransition(scale, rotate);
            parallelTransition.play();
        };
    }

    /**
     * Crea e restituisce un gestore di eventi per l'evento MouseRelease. Questo
     * gestore ripristina lo stile originale del pulsante e annulla le animazioni.
     *
     * @return Un EventHandler per l'evento MouseRelease.
     */
    private EventHandler<MouseEvent> createMouseReleaseHandler() {
        return e -> {
            setStyle(
                    "-fx-background-color: #4CAF50; -fx-text-fill: white; -fx-background-radius: 5px; -fx-padding: 10 20; -fx-font-size: 14px;"); // Restored
                                                                                                                                                  // font
                                                                                                                                                  // size
                                                                                                                                                  // on
                                                                                                                                                  // released
            ScaleTransition scale = new ScaleTransition(Duration.millis(100), this);
            scale.setToX(1.0);
            scale.setToY(1.0);
            RotateTransition rotate = new RotateTransition(Duration.millis(100), this);
            rotate.setToAngle(0);
            ParallelTransition parallelTransition = new ParallelTransition(scale, rotate);
            parallelTransition.play();
        };
    }
}
