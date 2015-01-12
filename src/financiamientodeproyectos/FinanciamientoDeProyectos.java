/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package financiamientodeproyectos;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPaneBuilder;
import javafx.stage.Stage;

/**
 *
 * @author Freddy
 */
public class FinanciamientoDeProyectos extends Application {
    
    @Override
    public void start(Stage primaryStage) {
       Parent root;
        try {
            root = FXMLLoader.load(getClass().getResource("GUI.fxml"));
        } catch (Exception e) {
            root = AnchorPaneBuilder.create().id("mainWindow").prefWidth(800).prefHeight(600).build();
        }
        Scene scene = new Scene(root);

        primaryStage.setTitle("PROGRAMA PARA EVALUACIÓN DE PROYECTOS DE CENTRO DE CÓMPUTO");
        primaryStage.setScene(scene);
        primaryStage.centerOnScreen();
        primaryStage.show();
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
    
}
