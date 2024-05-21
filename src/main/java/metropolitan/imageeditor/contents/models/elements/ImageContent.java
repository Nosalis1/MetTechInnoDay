package metropolitan.imageeditor.contents.models.elements;

import javafx.geometry.Bounds;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.transform.Rotate;
import metropolitan.imageeditor.contents.models.Content;

import java.io.File;

public class ImageContent extends Content {

    private final String imageName;
    private final Image content;

    public ImageContent(final File file) {
        super();
        this.imageName = file.getName();
        this.content = new Image(file.toURI().toString());

        super.setWidth(this.content.getWidth());
        super.setHeight(this.content.getHeight());
    }

    @Override
    public void render(GraphicsContext gc) {
        gc.save(); // save the current state

        // translate to the center of the rectangle
        gc.translate(getX() + getWidth() / 2, getY() + getHeight() / 2);
        gc.rotate(this.getAngle()); // rotate render matrix

        gc.drawImage(this.content, -getWidth() / 2, -getHeight() / 2, getWidth(), getHeight()); // draw the image

        gc.restore(); // restore the previous state
    }

    @Override
    public void highlight(GraphicsContext gc) {
        gc.setStroke(Color.HOTPINK);
        gc.setLineWidth(2); // Set the width of the stroke rectangle

        // Save the current state of the graphics context
        gc.save();

        // Translate to the center of the image
        double centerX = getX() + getWidth() / 2;
        double centerY = getY() + getHeight() / 2;
        gc.translate(centerX, centerY);

        // Rotate the graphics context by the angle
        gc.rotate(getAngle());

        // Draw the stroke rectangle around the rotated image
        Rotate rotation = new Rotate(getAngle());
        Rectangle rotatedRect = new Rectangle(getX(), getY(), getWidth(), getHeight());
        rotatedRect.setRotate(getAngle());
        rotatedRect.getTransforms().add(rotation);
        Bounds bounds = rotatedRect.getBoundsInLocal();

        gc.strokeRect(bounds.getMinX() - centerX, bounds.getMinY() - centerY, bounds.getWidth(), bounds.getHeight());

        // Restore the graphics context to its original state
        gc.restore();
    }

    @Override
    public boolean inBounds(double x, double y) {
        Rotate rotation = new Rotate(this.getAngle());
        Rectangle rotatedRect = new Rectangle(this.getX(), this.getY(), this.getWidth(), this.getHeight());
        rotatedRect.setRotate(this.getAngle());
        rotatedRect.getTransforms().add(rotation);
        return rotatedRect.getBoundsInLocal().contains(x, y);
    }

    @Override
    public String toString() {
        return this.imageName;
    }
}
