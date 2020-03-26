package Cerego;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.util.Optional;

public class Main extends Application {
    Stage window;
    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("Main_GUI.fxml"));
        window = primaryStage;
        primaryStage.setTitle("Cerego Dictionary Web Scrapper");
        primaryStage.getIcons().add(new Image("logo.png"));
        primaryStage.setScene(new Scene(root, 1250, 720));
        primaryStage.setOnCloseRequest(e -> {
            e.consume();
            closeProgram();
        });

        primaryStage.show();
    }

    private void closeProgram() {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Exiting Confirmation");
        alert.setHeaderText("You are about to exit!");
        alert.setContentText("Are you sure with this?");

        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK){
            System.out.println("Enter here");
            window.close();
        }
    }



    public static void main(String[] args) {
        launch(args);
    }
}
