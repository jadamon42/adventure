package com.github.jadamon42.adventure.builder;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class BuilderUiEngine {
    private BuilderUiController controller;

    public void initialize(Stage stage) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/buildui.fxml"));
        Parent root = loader.load();
        controller = loader.getController();

        stage.setScene(new Scene(root));
        stage.setTitle("Build");
        stage.show();
    }
}
