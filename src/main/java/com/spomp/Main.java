package com.spomp;

import java.io.IOException;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * JavaFX Main
 */

public class Main extends Application {
    
    String pathFxmlFile;
    
    @Override
     public void start(Stage stage) throws IOException {
        pathFxmlFile = "/view/fxml/Login.fxml";
        Parent loginRoot = FXMLLoader.load(getClass().getResource(pathFxmlFile));
        Scene loginScene = new Scene(loginRoot);
        stage.setScene(loginScene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }

}