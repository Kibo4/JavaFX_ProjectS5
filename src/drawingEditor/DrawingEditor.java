package drawingEditor;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import static javafx.application.Application.launch;

/**
 * Created by arialan on 22/12/17.
 */
public class DrawingEditor extends Application{
    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("view/docfxml.fxml"));
        primaryStage.setTitle("Projet JavaFX");
        primaryStage.setScene(new Scene(root, 1200, 400));
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }

}
