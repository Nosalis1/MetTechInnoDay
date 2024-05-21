package metropolitan.imageeditor.contents.models;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.Alert;
import javafx.scene.image.Image;
import javafx.scene.image.PixelReader;
import javafx.scene.image.WritableImage;
import metropolitan.imageeditor.utils.Util;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.awt.image.WritableRaster;
import java.io.File;

public class ImageHandler {
    public static final ImageHandler INSTANCE = new ImageHandler();

    private final ObjectProperty<Image> image;
    private String imagePath;

    private ImageHandler() {
        this.image = new SimpleObjectProperty<>(null);
        this.imagePath = "";
    }

    public ObjectProperty<Image> imageProperty() {
        return this.image;
    }

    public Image getImage() {
        return this.image.get();
    }

    public void setImage(Image image) {
        this.image.set(image);
    }

    public String getImagePath() {
        return this.imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    public void open() {
        File file = Util.File.openFile();
        if (file == null) return;
        this.image.set(new Image(file.toURI().toString()));
        this.imagePath = file.getAbsolutePath();
    }

    public void save() {
        if (getImage() == null)
            return; // TODO : Cast Error

        Editor.INSTANCE.getController().cleanUpdate(); // Render Image without debugs!

        final Canvas canvas = Editor.INSTANCE.getController().getCanvas();
        try {
            int width = (int) canvas.getWidth(); // Get the width of the canvas
            int height = (int) canvas.getHeight(); // Get the height of the canvas

            // Create a WritableImage with the given width and height
            WritableImage writableImage = new WritableImage(width, height);

            // Take a snapshot of the canvas and store it in the writable image
            canvas.snapshot(null, writableImage);

            // Create a file to save the image as fileName
            File file = new File(imagePath);

            // Create a BufferedImage with the same width and height
            BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);

            // Get the pixel reader from the writable image
            PixelReader pixelReader = writableImage.getPixelReader();
            // Get the raster of the image
            WritableRaster raster = image.getRaster();

            int[] pixelBuffer = new int[4]; // Buffer to hold pixel data
            // Loop through each pixel in the image
            for (int y = 0; y < height; y++) {
                for (int x = 0; x < width; x++) {
                    javafx.scene.paint.Color color = pixelReader.getColor(x, y); // Get color of pixel
                    // Store color components in the pixel buffer
                    pixelBuffer[0] = (int) (color.getRed() * 255);
                    pixelBuffer[1] = (int) (color.getGreen() * 255);
                    pixelBuffer[2] = (int) (color.getBlue() * 255);
                    pixelBuffer[3] = (int) (color.getOpacity() * 255);
                    // Set the pixel data in the raster
                    raster.setPixel(x, y, pixelBuffer);
                }
            }

            // Write the image to a file in PNG format
            ImageIO.write(image, "png", file);
        } catch (Exception e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Error");
            alert.setContentText(e.getMessage());
            alert.showAndWait();
            return;
        }
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Success");
        alert.setHeaderText("Success");
        alert.setContentText("Image saved successfully!");
        alert.showAndWait();
    }

    public void saveAs() {
        if (getImage() == null)
            return; // TODO : Cast Error

        Editor.INSTANCE.getController().cleanUpdate(); // Render Image without debugs!

        final Canvas canvas = Editor.INSTANCE.getController().getCanvas();
        try {
            int width = (int) canvas.getWidth(); // Get the width of the canvas
            int height = (int) canvas.getHeight(); // Get the height of the canvas

            // Create a WritableImage with the given width and height
            WritableImage writableImage = new WritableImage(width, height);

            // Take a snapshot of the canvas and store it in the writable image
            canvas.snapshot(null, writableImage);

            // Create a file to save the image as fileName
            File file = Util.File.saveImage(this.imagePath);

            // Create a BufferedImage with the same width and height
            BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);

            // Get the pixel reader from the writable image
            PixelReader pixelReader = writableImage.getPixelReader();
            // Get the raster of the image
            WritableRaster raster = image.getRaster();

            int[] pixelBuffer = new int[4]; // Buffer to hold pixel data
            // Loop through each pixel in the image
            for (int y = 0; y < height; y++) {
                for (int x = 0; x < width; x++) {
                    javafx.scene.paint.Color color = pixelReader.getColor(x, y); // Get color of pixel
                    // Store color components in the pixel buffer
                    pixelBuffer[0] = (int) (color.getRed() * 255);
                    pixelBuffer[1] = (int) (color.getGreen() * 255);
                    pixelBuffer[2] = (int) (color.getBlue() * 255);
                    pixelBuffer[3] = (int) (color.getOpacity() * 255);
                    // Set the pixel data in the raster
                    raster.setPixel(x, y, pixelBuffer);
                }
            }

            // Write the image to a file in PNG format
            ImageIO.write(image, "png", file);
        } catch (Exception e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Error");
            alert.setContentText(e.getMessage());
            alert.showAndWait();
            return;
        }
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Success");
        alert.setHeaderText("Success");
        alert.setContentText("Image saved successfully!");
        alert.showAndWait();
    }
}