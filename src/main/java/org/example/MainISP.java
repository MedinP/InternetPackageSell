package org.example;

/*
 * JavaFX - Internet provider MHS Co.
 */

import java.net.URL;
import java.util.Collections;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

/**
    *
    * @author Pivalic Medin / 2021
    */

public class MainISP extends Application{


    public static void main(String[] args) {
        launch(args);
    }
    @Override
    public void start(Stage primaryStage) throws Exception {

        URL fxmlUrl = getClass().getClassLoader().getResource("SellView.fxml");
        BorderPane root = FXMLLoader.<BorderPane>load(fxmlUrl);
        root.getStylesheets().add("style.css");
        root.setBackground(new Background(
                Collections.singletonList(new BackgroundFill(
                        Color.WHITE,
                        new CornerRadii(0),
                        new Insets(0))),
                Collections.singletonList(new BackgroundImage(
                        new Image("file:pic1.jpg", 100, 100, false, true),
                        BackgroundRepeat.NO_REPEAT,
                        BackgroundRepeat.NO_REPEAT,
                        BackgroundPosition.DEFAULT,
                        new BackgroundSize(1.0, 1.0, true, true, false, false)
                ))));

        root.setStyle("-fx-border-color: white");

        primaryStage.getIcons().add(new Image(("file:logo.png")));

        Scene scene = new Scene(root);
        primaryStage.initStyle(StageStyle.DECORATED);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Internet provider MHS Co. |  Welcome  |  Version: 1.0.23");
        primaryStage.centerOnScreen();
        primaryStage.setResizable(false);
        primaryStage.show();

    }

}