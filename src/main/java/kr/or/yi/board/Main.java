package kr.or.yi.board;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
//import org.w3c.dom.events.MouseEvent;
import javafx.scene.input.MouseEvent;

import java.io.IOException;

public class Main extends Application {
    private double x = 0;
    private double y = 0;
    @Override
    public void start(Stage stage) throws IOException {
        try {
//            Parent root = FXMLLoader.load(getClass().getResource("Main.fxml"));
//            Scene scene = new Scene(root, 900, 600);
            Parent root = FXMLLoader.load(getClass().getResource("Login.fxml"));
            Scene scene = new Scene(root, 600, 400);

            stage.setScene(scene);
            stage.setResizable(false);
            root.setOnMousePressed((MouseEvent event)->{
                x = event.getSceneX();
                y = event.getSceneY();
            });
            root.setOnMouseDragged((MouseEvent event)->{
                stage.setX(event.getScreenX() - x);
                stage.setY(event.getScreenY() - y);
            });

            root.setOnMouseReleased((MouseEvent event) -> {
                stage.setOpacity(1);
            });

            stage.initStyle(StageStyle.TRANSPARENT);
            stage.show();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        launch();
    }
}