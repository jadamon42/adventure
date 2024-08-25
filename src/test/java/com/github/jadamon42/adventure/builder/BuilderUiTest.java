package com.github.jadamon42.adventure.builder;

import javafx.application.Application;
import javafx.stage.Stage;

public class BuilderUiTest extends Application {
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {
        BuilderUiEngine engine = new BuilderUiEngine();
        engine.initialize(stage);
    }
}
