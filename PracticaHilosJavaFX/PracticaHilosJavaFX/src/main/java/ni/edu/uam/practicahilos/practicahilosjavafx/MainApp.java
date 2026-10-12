package ni.edu.uam.practicahilos.practicahilosjavafx;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class MainApp extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(MainApp.class.getResource("procesador-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        ProcesadorController controller = fxmlLoader.getController();

        stage.setTitle("Práctica - Hilos en JavaFX");
        stage.setScene(scene);
        stage.setOnCloseRequest(event -> controller.detenerAlCerrar());
        stage.show();
    }
}
