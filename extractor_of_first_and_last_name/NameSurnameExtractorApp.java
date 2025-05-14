package extractor_of_first_and_last_name;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import javafx.animation.FadeTransition;
import javafx.animation.Interpolator;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.ParallelTransition;
import javafx.animation.RotateTransition;
import javafx.animation.ScaleTransition;
import javafx.animation.Timeline;
//Importa TranslateTransition
import javafx.animation.TranslateTransition;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.CheckBox; // Importa CheckBox
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.SplitPane;
import javafx.scene.control.ToggleGroup;
import javafx.scene.effect.InnerShadow;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;
import javafx.util.Duration;

/**
 * Questa classe rappresenta l'interfaccia utente per l'applicazione di
 * estrazione di nomi e cognomi.
 */
public class NameSurnameExtractorApp extends Application {

    private StyledTextArea inputTextArea;
    private NeonTextArea outputTextArea;
    private RadioButton nomiRadioButton;
    private RadioButton cognomiRadioButton;
    private Stage primaryStage; // Memorizza lo Stage principale
    private Label inputLabel;
    private Label outputLabel;
    private DemoDataPopulator demoDataPopulator;
    private InnerShadow originalShadow; // Memorizza l'ombra originale
    private StyledButton processButton;
    private CheckBox effettiSpecialiCheckBox; // Dichiarazione della CheckBox
    private final Duration animationDuration = Duration.millis(200);
    private final double scaleFactor = 1.1;

    @Override
    public void start(Stage primaryStage) {
        this.primaryStage = primaryStage;
        primaryStage.setTitle("Estrattore Nomi/Cognomi Dinamico");

        BorderPane root = new BorderPane();
        root.setPadding(new Insets(10));

        // Inizializza le Label
        inputLabel = new Label("Testo da analizzare:");
        outputLabel = new Label("Risultato:");

        // Usa StyledTextArea per l'input e NeonTextArea per l'output
        inputTextArea = new StyledTextArea();
        inputTextArea.setPromptText("Inserisci un elenco di nomi e cognomi, uno per riga...");
        inputTextArea.setOpacity(0);
        inputTextArea.getStyleClass().add("text-area");
        outputTextArea = new NeonTextArea();
        outputTextArea.setPromptText("I risultati appariranno qui...");
        outputTextArea.setEditable(false);
        outputTextArea.getStyleClass().add("text-area");
        outputTextArea.setOpacity(0);

        // Memorizza l'ombra originale di outputTextArea
        originalShadow = (InnerShadow) outputTextArea.getEffect();
        if (originalShadow == null) {
            originalShadow = new InnerShadow();
        }
        outputTextArea.setEffect(null); // Imposta l'effetto a null inizialmente

        // Imposta la priorità di ridimensionamento per le TextAreas
        VBox.setVgrow(inputTextArea, Priority.ALWAYS);
        VBox.setVgrow(outputTextArea, Priority.ALWAYS);

        // Crea un VBox per contenere la label e la textarea di input
        VBox inputVBox = new VBox(5); // Spazio di 5 pixel tra gli elementi
        inputVBox.getChildren().addAll(inputLabel, inputTextArea);

        // Crea un VBox per contenere la label e la textarea di output
        VBox outputVBox = new VBox(5);
        outputVBox.getChildren().addAll(outputLabel, outputTextArea);

        SplitPane splitPane = new SplitPane();
        splitPane.getItems().addAll(inputVBox, outputVBox);
        splitPane.setDividerPositions(0.5);
        // Imposta la priorità di ridimensionamento per lo SplitPane
        SplitPane.setResizableWithParent(inputVBox, true);
        SplitPane.setResizableWithParent(outputVBox, true);

        root.setCenter(splitPane);

        VBox controlsBox = new VBox(10);
        controlsBox.setAlignment(Pos.CENTER);
        controlsBox.setPadding(new Insets(10, 0, 0, 0));

        Label choiceLabel = new Label("Scegli cosa restituire:");
        nomiRadioButton = new RadioButton("Restituisci nomi");
        cognomiRadioButton = new RadioButton("Restituisci cognomi");
        cognomiRadioButton.setSelected(true);

        ToggleGroup choiceGroup = new ToggleGroup();
        nomiRadioButton.setToggleGroup(choiceGroup);
        cognomiRadioButton.setToggleGroup(choiceGroup);

        HBox radioBox = new HBox(10, nomiRadioButton, cognomiRadioButton);
        radioBox.setAlignment(Pos.CENTER);

        // Crea la CheckBox per gli effetti speciali
        effettiSpecialiCheckBox = new CheckBox("Attiva effetti speciali");
        effettiSpecialiCheckBox.setSelected(false); // Di default, la checkbox è disattivata

        // Usa la nuova classe StyledButton
        processButton = new StyledButton("Elabora");
        processButton.getStyleClass().add("button"); // Aggiungi la classe CSS
        processButton.setOnAction(e -> processInput());

        controlsBox.getChildren().addAll(choiceLabel, radioBox, effettiSpecialiCheckBox, processButton); // Aggiungi la
        // checkbox
        root.setBottom(controlsBox);

        Scene scene = new Scene(root, 750, 500);
        scene.getStylesheets().add(getClass().getResource("styles.css").toExternalForm());
        primaryStage.setScene(scene);
        primaryStage.show();

        // Inizializza il popolatore di dati demo
        demoDataPopulator = new DemoDataPopulator(inputTextArea);

        // Avvia l'animazione di comparsa delle textarea
        animateTextAreas();
        demoDataPopulator.populateSampleInput();

        primaryStage.setOnCloseRequest(event -> {
            event.consume();
            startCloseAnimation();
        });

        // Applica gli effetti di ingrandimento alle checkbox e radio button
        applyHoverEffect(nomiRadioButton);
        applyHoverEffect(cognomiRadioButton);
        applyHoverEffect(effettiSpecialiCheckBox);
    }

    private void animateTextAreas() {
        // Animazione di Fade per la comparsa delle TextAreas
        FadeTransition fadeInInput = new FadeTransition(Duration.seconds(1), inputTextArea);
        fadeInInput.setFromValue(0);
        fadeInInput.setToValue(1);

        FadeTransition fadeInOutput = new FadeTransition(Duration.seconds(1), outputTextArea);
        fadeInOutput.setFromValue(0);
        fadeInOutput.setToValue(1);

        ScaleTransition scaleOutput = new ScaleTransition(Duration.seconds(1), outputTextArea);
        scaleOutput.setFromX(1);
        scaleOutput.setFromY(1);
        scaleOutput.setToX(1);
        scaleOutput.setToY(1);

        // Animazione di rotazione (leggera)
        RotateTransition rotateInput = new RotateTransition(Duration.seconds(1), inputTextArea);
        rotateInput.setFromAngle(0);
        rotateInput.setToAngle(0);
        rotateInput.setInterpolator(Interpolator.LINEAR);

        // Esegui le animazioni in parallelo
        ParallelTransition parallelTransition = new ParallelTransition(fadeInInput, fadeInOutput, scaleOutput,
                rotateInput);
        parallelTransition.play();
    }

    private void startCloseAnimation() {
        // Animazione di rotazione
        RotateTransition rotateTransition = new RotateTransition(Duration.seconds(1.5),
                primaryStage.getScene().getRoot());
        rotateTransition.setByAngle(360);
        rotateTransition.setInterpolator(Interpolator.EASE_BOTH);

        // Animazione di allontanamento (riduzione della scala)
        ScaleTransition scaleTransition = new ScaleTransition(Duration.seconds(1.5), primaryStage.getScene().getRoot());
        scaleTransition.setToX(0.1);
        scaleTransition.setToY(0.1);
        scaleTransition.setInterpolator(Interpolator.EASE_BOTH);

        // Animazione di dissolvenza verso il bianco
        FadeTransition fadeTransition = new FadeTransition(Duration.seconds(1.5), primaryStage.getScene().getRoot());
        fadeTransition.setToValue(0.0);
        fadeTransition.setInterpolator(Interpolator.EASE_BOTH);

        // Animazione del colore di sfondo verso il bianco
        Timeline backgroundColorTimeline = new Timeline(
                new KeyFrame(Duration.ZERO,
                        new KeyValue(primaryStage.getScene().getRoot().styleProperty(),
                                "-fx-background-color: white;")),
                new KeyFrame(Duration.seconds(1.5), new KeyValue(primaryStage.getScene().getRoot().styleProperty(),
                        "-fx-background-color: white;")));

        // Esegui tutte le animazioni in parallelo
        ParallelTransition parallelTransition = new ParallelTransition(rotateTransition, scaleTransition,
                fadeTransition, backgroundColorTimeline);
        parallelTransition.setOnFinished(event -> {
            primaryStage.close();
        });
        parallelTransition.play();
    }

    private void processInput() {
        String inputText = inputTextArea.getText();
        if (inputText == null || inputText.trim().isEmpty()) {
            outputTextArea.setText("Nessun input fornito.");
            return;
        }

        List<String> lines = Arrays.asList(inputText.split("\\r?\\n"));
        StringBuilder resultBuilder = new StringBuilder();
        boolean returnNomiSelected = nomiRadioButton.isSelected();

        // Se la checkbox è selezionata, esegui l'animazione sul pulsante E SULLA LABEL
        if (effettiSpecialiCheckBox.isSelected()) {
            // 1. Rotazione del pulsante "Elabora"
            RotateTransition rotateButton = new RotateTransition(Duration.seconds(2), processButton);
            rotateButton.setByAngle(720);
            rotateButton.setInterpolator(Interpolator.EASE_BOTH);
            rotateButton.play();

            // 2. Animazione della label di destra (outputLabel)
            final Duration animationDurationLabel = Duration.seconds(2);
            final Font initialFont = Font.getDefault();
            final Color initialColor = Color.BLACK;

            Timeline labelTimeline = new Timeline(
                    new KeyFrame(Duration.ZERO, new KeyValue(outputLabel.textFillProperty(), initialColor),
                            new KeyValue(outputLabel.scaleXProperty(), 0), // Inizia da scala 0
                            new KeyValue(outputLabel.scaleYProperty(), 0),
                            new KeyValue(outputLabel.translateXProperty(), 100), // Inizia da 100 a destra
                            new KeyValue(outputLabel.fontProperty(),
                                    Font.font(initialFont.getFamily(), FontWeight.BOLD, initialFont.getSize())),
                            new KeyValue(outputLabel.opacityProperty(), 0)),
                    new KeyFrame(Duration.seconds(0.5), new KeyValue(outputLabel.opacityProperty(), 1),
                            new KeyValue(outputLabel.textFillProperty(), Color.DARKGRAY),
                            new KeyValue(outputLabel.scaleXProperty(), 1.2),
                            new KeyValue(outputLabel.scaleYProperty(), 1.2),
                            new KeyValue(outputLabel.translateXProperty(), 0)), // Si muove a 0
                    new KeyFrame(Duration.seconds(1.0), new KeyValue(outputLabel.opacityProperty(), 0)),
                    new KeyFrame(Duration.seconds(1.5), new KeyValue(outputLabel.opacityProperty(), 0)),
                    new KeyFrame(animationDurationLabel, new KeyValue(outputLabel.textFillProperty(), initialColor),
                            new KeyValue(outputLabel.fontProperty(), initialFont),
                            new KeyValue(outputLabel.scaleXProperty(), 1),
                            new KeyValue(outputLabel.scaleYProperty(), 1),
                            new KeyValue(outputLabel.translateXProperty(), 0),
                            new KeyValue(outputLabel.opacityProperty(), 1)));
            labelTimeline.play();

            // Animazione combinata per outputTextArea: Fade, Scala e Traslazione
            FadeTransition fadeInOutput = new FadeTransition(Duration.seconds(1), outputTextArea);
            fadeInOutput.setFromValue(0);
            fadeInOutput.setToValue(1);

            ScaleTransition scaleOutput = new ScaleTransition(Duration.seconds(1), outputTextArea);
            scaleOutput.setFromX(0.8); // Inizia da una scala ridotta
            scaleOutput.setFromY(0.8);
            scaleOutput.setToX(1);
            scaleOutput.setToY(1);

            TranslateTransition translateOutput = new TranslateTransition(Duration.seconds(1), outputTextArea);
            translateOutput.setFromX(50); // Inizia da 50 pixel a destra
            translateOutput.setToX(0);

            ParallelTransition parallelTransition = new ParallelTransition(fadeInOutput, scaleOutput, translateOutput);
            parallelTransition.play();
        }

        // Usa uno stream per processare le righe, mantenendo le righe vuote
        String result = lines.stream().map(line -> NameSurnameExtractor.processLine(line, returnNomiSelected)) // Usa la
                // classe
                // NameSurnameExtractor
                .collect(Collectors.joining("\n"));

        outputTextArea.setText(result);

        // trigger della neon animation
        outputTextArea.requestFocus();
        if (!effettiSpecialiCheckBox.isSelected()) {
            outputTextArea.setEffect(originalShadow); // Riattiva l'effetto neon SOLO se effetti speciali non attivi
            Timeline timeline = new Timeline(
                    new KeyFrame(Duration.ZERO,
                            new KeyValue(originalShadow.colorProperty(), javafx.scene.paint.Color.BLUE)),
                    new KeyFrame(Duration.seconds(0.5),
                            new KeyValue(originalShadow.colorProperty(), javafx.scene.paint.Color.TRANSPARENT)));
            timeline.setOnFinished(e -> {
                outputTextArea.setEffect(null);
                outputTextArea.setStyle("-fx-border-color: transparent; -fx-border-width: 2px;");
            });
            timeline.play();
        } else {
            outputTextArea.setEffect(null);
            outputTextArea.setStyle("-fx-border-color: transparent; -fx-border-width: 2px;");
        }
    }

    /**
     * Classe utility per popolare la textarea di input con dati demo.
     */
    private static class DemoDataPopulator {

        private final StyledTextArea inputTextArea;

        public DemoDataPopulator(StyledTextArea inputTextArea) {
            this.inputTextArea = inputTextArea;
        }

        public void populateSampleInput() {
            String sampleInput = "Ottavia Rossi\nMaria Anna Bianchi\nAndrea De Gennaro\nEnrico Maria Di Martino\nGiovanni D'Assisi\nLucia Degli Sforza\nMarco Catanzaro\nGiulia Giudicato Catania\nGiuseppe Dall'Arco\nStefano De Luca\nMartina Esposito\nAlessandro Della Rovere\nAntonio Del Re\nFederica Meloni\nArturo D'Amico\nFranco Delvecchio\nMario Di Bella\nPaolo D Annunzio\nPaolo D' Annunzio\nPaolo D'Annunzio\nLuigi Senza Preposizione CognomeComposto\nGiovanna Alliata Bronner";
            inputTextArea.setText(sampleInput);
        }
    }

    private void applyHoverEffect(Node node) {
        node.setOnMouseEntered(e -> {
            // Interrompe qualsiasi animazione precedente per evitare interferenze
            node.getProperties().remove("scaleTransition");
            ScaleTransition scaleTransition = new ScaleTransition(animationDuration, node);
            scaleTransition.setToX(scaleFactor);
            scaleTransition.setToY(scaleFactor);
            scaleTransition.play();
            // Memorizza l'animazione per poterla interrompere in seguito
            node.getProperties().put("scaleTransition", scaleTransition);
        });

        node.setOnMouseExited(e -> {
            // Interrompe qualsiasi animazione precedente per evitare interferenze
            ScaleTransition scaleTransition = (ScaleTransition) node.getProperties().get("scaleTransition");
            if (scaleTransition != null) {
                scaleTransition.stop();
            }
            ScaleTransition reverseScaleTransition = new ScaleTransition(animationDuration, node);
            reverseScaleTransition.setToX(1);
            reverseScaleTransition.setToY(1);
            reverseScaleTransition.play();
            node.getProperties().remove("scaleTransition");
        });
    }

    public static void main(String[] args) {
        launch(args);
    }
}
