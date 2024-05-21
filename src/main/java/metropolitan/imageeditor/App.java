package metropolitan.imageeditor;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import metropolitan.imageeditor.contents.models.Editor;

import java.io.IOException;

public class App extends Application {

    public static final int WINDOW_WIDTH = 1280;
    public static final int WINDOW_HEIGHT = 720;

    @Override
    public void start(Stage stage) throws IOException {
        Scene scene = new Scene(Editor.INSTANCE.getController().getRoot(), WINDOW_WIDTH, WINDOW_HEIGHT);
        stage.setTitle("Image Editor");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}