package com.github.jadamon42.adventure.builder.element;

import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Paint;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class ExpandableTextInput extends HBox {
    private String text = "";
    private final TextField textField;

    public ExpandableTextInput(String promptText) {
        getStyleClass().add("node-text-input-container");
        StackPane stackPane = new StackPane();

        textField = new TextField();
        textField.getStyleClass().add("node-text-input");
        textField.setPromptText(promptText);
        textField.setFocusTraversable(false);
        textField.setOnKeyTyped(event -> text = textField.getText());
        textField.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ENTER || event.getCode() == KeyCode.ESCAPE) {
                getScene().getRoot().requestFocus();
            }
        });

        FontAwesomeIconView expandIcon = new FontAwesomeIconView(FontAwesomeIcon.EXPAND);
        expandIcon.getStyleClass().add("clickable");
        expandIcon.setFill(Paint.valueOf("lightgrey"));
        expandIcon.setOnMouseClicked(event -> openDialog(textField));

        HBox expandTextInputIcon = new HBox(expandIcon);
        expandTextInputIcon.getStyleClass().add("expand-text-input-icon");
        expandTextInputIcon.setMinWidth(HBox.USE_PREF_SIZE);
        expandTextInputIcon.setMinHeight(HBox.USE_PREF_SIZE);
        expandTextInputIcon.setMaxWidth(HBox.USE_PREF_SIZE);
        expandTextInputIcon.setMaxHeight(HBox.USE_PREF_SIZE);

        stackPane.getChildren().addAll(textField, expandTextInputIcon);
        StackPane.setAlignment(expandTextInputIcon, Pos.CENTER_RIGHT);
        getChildren().add(stackPane);
    }

    public void setText(String text) {
        textField.setText(text);
    }

    public String getText() {
        return text;
    }

    public void setPromptText(String promptText) {
        textField.setPromptText(promptText);
    }

    public String getPromptText() {
        return textField.getPromptText();
    }

    private void openDialog(TextField textField) {
        if (text.isEmpty()) {
            text = textField.getText();
        }

        Stage dialog = new Stage();
        dialog.initModality(Modality.APPLICATION_MODAL);
        dialog.setTitle("Expand Input");

        TextArea textArea = new TextArea(text);
        textArea.setWrapText(true);
        textArea.setPrefSize(300, 200);
        textArea.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ESCAPE) {
                dialog.close();
            }
        });

        Button acceptButton = new Button("Accept");
        acceptButton.setOnAction(event -> {
            text = textArea.getText();
            String[] lines = text.split("\n");
            String firstLine = lines[0];
            if (lines.length > 1) {
                firstLine += "...";
            }
            textField.setText(firstLine);
            dialog.close();
        });

        Button cancelButton = new Button("Cancel");
        cancelButton.setOnAction(event -> dialog.close());

        HBox buttonBox = new HBox(10, acceptButton, cancelButton);
        buttonBox.setAlignment(Pos.CENTER);

        VBox dialogVbox = new VBox(10, textArea, buttonBox);
        dialogVbox.setAlignment(Pos.CENTER);
        Scene dialogScene = new Scene(dialogVbox, 300, 250);
        dialog.setScene(dialogScene);
        dialog.setResizable(false);
        dialog.centerOnScreen();
        dialog.show();
    }
}
