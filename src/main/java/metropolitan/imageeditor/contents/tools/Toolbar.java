package metropolitan.imageeditor.contents.tools;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import metropolitan.imageeditor.contents.models.Content;

public class Toolbar {
    public static final Toolbar INSTANCE = new Toolbar(); // Singleton

    private final ObjectProperty<Mode> mode;
    private double lastMouseX, lastMouseY;

    private Toolbar() {
        this.mode = new SimpleObjectProperty<>(Mode.SELECTION);
        this.lastMouseX = this.lastMouseY = 0.0;
    } // Prevent instantiation

    public ObjectProperty<Mode> modeProperty() {
        return this.mode;
    }

    public Mode getMode() {
        return this.mode.get();
    }

    public void setMode(Mode mode) {
        this.mode.set(mode);
    }

    public double getLastMouseX() {
        return this.lastMouseX;
    }

    public double getLastMouseY() {
        return this.lastMouseY;
    }

    public void onMousePressed(double x, double y) {
        if (this.getMode() == Mode.SELECTION) {
            Content temp = null;
            for (Content content : ContentHandler.INSTANCE.getContents()) {
                if (content.inBounds(x, y)) {
                    temp = content;
                }
            }
            Selector.INSTANCE.setSelectedElement(temp);
        }

        this.lastMouseX = x;
        this.lastMouseY = y;
    }

    public void onMouseDragged(double x, double y) {
        if (Selector.INSTANCE.hasSelectedElement()) {
            double deltaX = x - lastMouseX; // Calculate delta X
            double deltaY = y - lastMouseY; // Calculate delta Y

            switch (this.getMode()) {
                case MOVE -> Selector.INSTANCE.getSelectedElement().move(deltaX, deltaY);
                case RESIZE -> Selector.INSTANCE.getSelectedElement().resize(deltaX, deltaY);
                case ROTATE -> {
                    double angle = Math.atan2(deltaY, deltaX); // Calculate angle
                    Selector.INSTANCE.getSelectedElement().rotate(angle);
                }
            }
        }

        this.lastMouseX = x;
        this.lastMouseY = y;
    }

    public void onMouseReleased(double x, double y) {
        this.lastMouseX = x;
        this.lastMouseY = y;
    }
}
