package extractor_of_first_and_last_name;

import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.scene.effect.InnerShadow;
import javafx.scene.paint.Color;
import javafx.util.Duration;

/**
 * Questa classe estende StyledTextArea e aggiunge la funzionalità per l'effetto
 * neon.
 */
public class NeonTextArea extends StyledTextArea {

    /**
     * Costruttore di default per NeonTextArea. Imposta lo stile predefinito e
     * l'effetto neon.
     */
    public NeonTextArea() {
        super();
        initNeonEffect();
    }

    /**
     * Costruttore per NeonTextArea che accetta una stringa di testo. Imposta il
     * testo della TextArea, lo stile predefinito e l'effetto neon.
     *
     * @param text Il testo da visualizzare nella TextArea.
     */
    public NeonTextArea(String text) {
        super(text);
        initNeonEffect();
    }

    /**
     * Inizializza l'effetto neon per la TextArea.
     */
    private void initNeonEffect() {
        InnerShadow innerShadow = new InnerShadow();
        innerShadow.setColor(Color.TRANSPARENT);
        setEffect(innerShadow);
        focusedProperty().addListener((obs, oldVal, newVal) -> {
            if (newVal) {
                // Quando la TextArea ottiene il focus, avvia l'animazione neon
                startNeonAnimation(innerShadow);
            } else {
                // Quando la TextArea perde il focus, rimuovi l'effetto neon
                removeNeonEffect(innerShadow);
            }
        });
    }

    /**
     * Avvia l'animazione dell'effetto neon.
     *
     * @param innerShadow L'oggetto InnerShadow da animare.
     */
    private void startNeonAnimation(InnerShadow innerShadow) {
        Timeline timeline = new Timeline(
                new KeyFrame(Duration.ZERO, new KeyValue(innerShadow.colorProperty(), Color.TRANSPARENT)),
                new KeyFrame(Duration.seconds(0.5), new KeyValue(innerShadow.colorProperty(), Color.BLUE)));
        timeline.play();
    }

    /**
     * Rimuove l'effetto neon dalla TextArea.
     *
     * @param innerShadow L'oggetto InnerShadow da modificare.
     */
    private void removeNeonEffect(InnerShadow innerShadow) {
        Timeline timeline = new Timeline(
                new KeyFrame(Duration.ZERO, new KeyValue(innerShadow.colorProperty(), Color.BLUE)),
                new KeyFrame(Duration.seconds(0.5), new KeyValue(innerShadow.colorProperty(), Color.TRANSPARENT)));
        timeline.setOnFinished(e -> {
            setEffect(null);
            setStyle("-fx-border-color: transparent; -fx-border-width: 2px;");
        });
        timeline.play();
    }
}
