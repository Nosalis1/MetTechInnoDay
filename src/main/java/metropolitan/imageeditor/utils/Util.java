package metropolitan.imageeditor.utils;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;

import java.util.Objects;

public class Util {
    public static class Button {
        public static javafx.scene.control.Button button(String text, Runnable action) {
            javafx.scene.control.Button button = new javafx.scene.control.Button(text);
            button.setOnAction(e -> action.run());
            return button;
        }

        public static javafx.scene.control.Button imageButton(String imagePath, Runnable action) {
            javafx.scene.control.Button button = new javafx.scene.control.Button();
            button.setGraphic(new javafx.scene.image.ImageView(new javafx.scene.image.Image(imagePath)));
            button.setOnAction(e -> action.run());
            return button;
        }

        public static javafx.scene.control.Button iconButton(String iconName, Runnable action) {
            return iconButton(iconName, action, 24);
        }

        public static javafx.scene.control.Button iconButton(String iconName, Runnable action, double scale) {
            Image image = new Image(Objects.requireNonNull(Util.class.getResourceAsStream("/images/" + iconName)));
            javafx.scene.control.Button button = new javafx.scene.control.Button();
            ImageView imageView = new ImageView(image);
            imageView.setFitWidth(scale);
            imageView.setFitHeight(scale);
            button.setGraphic(imageView);
            button.setOnAction(event -> action.run());
            return button;
        }
    }

    public static class File {
        public static java.io.File openFile() {
            FileChooser fileChooser = new FileChooser();
            return fileChooser.showOpenDialog(null);
        }

        public static java.io.File openImage() {
            FileChooser fileChooser = new FileChooser();
            fileChooser.setSelectedExtensionFilter(new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg", "*.jpeg"));

            return fileChooser.showOpenDialog(null);
        }

        public static java.io.File saveImage(String imagePath) {
            FileChooser fileChooser = new FileChooser();
            fileChooser.setSelectedExtensionFilter(new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg", "*.jpeg"));
            fileChooser.setInitialFileName(imagePath);
            fileChooser.setInitialDirectory(new java.io.File(imagePath));

            return fileChooser.showSaveDialog(null);
        }
    }
}