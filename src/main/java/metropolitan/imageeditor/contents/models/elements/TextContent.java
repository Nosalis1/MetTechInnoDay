package metropolitan.imageeditor.contents.models.elements;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.scene.transform.Rotate;
import metropolitan.imageeditor.contents.models.Content;

public class TextContent extends Content {

    private final StringProperty content;
    private final ObjectProperty<Color> color;

    public TextContent(final String text) {
        super(0, 0, text.length() * 20, 20, 0);
        this.content = new SimpleStringProperty(text);
        this.color = new SimpleObjectProperty<>(Color.WHITE);
    }

    public StringProperty contentProperty() {
        return this.content;
    }

    public ObjectProperty<Color> colorProperty() {
        return this.color;
    }

    public String getContent() {
        return this.content.get();
    }

    public Color getColor() {
        return this.color.get();
    }

    public void setContent(String content) {
        this.content.set(content);
    }

    public void setColor(Color color) {
        this.color.set(color);
    }

    @Override
    public void render(GraphicsContext gc) {
        gc.save(); // save the current state

        // translate to the center of the rectangle
        gc.translate(getX() + getWidth() / 2, getY());
        gc.rotate(getAngle()); // rotate render matrix

        gc.setFont(Font.font("Arial", FontWeight.NORMAL, FontPosture.REGULAR, getHeight()));
        gc.setFill(getColor());

        gc.fillText(getContent(), -getWidth() / 2, getHeight(), getWidth());// draw the text

        gc.restore(); // restore the previous state
    }

    @Override
    public void highlight(GraphicsContext gc) {
        gc.setStroke(Color.HOTPINK);
        gc.setLineWidth(2); // Set the width of the stroke rectangle

        // Calculate the corners of the rectangle in the local coordinate system
        double halfWidth = getWidth() / 2;
        double halfHeight = getHeight() / 2;

        double[] xPoints = {
                -halfWidth, halfWidth, halfWidth, -halfWidth
        };
        double[] yPoints = {
                -halfHeight, -halfHeight, halfHeight, halfHeight
        };

        // Apply rotation to the points
        double angleRad = Math.toRadians(getAngle());
        double cos = Math.cos(angleRad);
        double sin = Math.sin(angleRad);

        for (int i = 0; i < xPoints.length; i++) {
            double x = xPoints[i];
            double y = yPoints[i];
            xPoints[i] = cos * x - sin * y;
            yPoints[i] = sin * x + cos * y;
        }

        // Find min and max points
        double minX = xPoints[0], maxX = xPoints[0];
        double minY = yPoints[0], maxY = yPoints[0];
        for (int i = 1; i < xPoints.length; i++) {
            if (xPoints[i] < minX) minX = xPoints[i];
            if (xPoints[i] > maxX) maxX = xPoints[i];
            if (yPoints[i] < minY) minY = yPoints[i];
            if (yPoints[i] > maxY) maxY = yPoints[i];
        }

        // Save the current state of the graphics context
        gc.save();

        // Translate to the center of the text
        double centerX = getX() + getWidth() / 2;
        double centerY = getY() + getHeight() / 2;
        gc.translate(centerX, centerY);

        // Draw the stroke rectangle using min and max points
        gc.strokeRect(minX, minY, maxX - minX, maxY - minY);

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
}
