package metropolitan.imageeditor.contents.interfaces;

import javafx.scene.canvas.GraphicsContext;

public interface Displayable {
    boolean inBounds(double x, double y);

    void render(final GraphicsContext gc);

    void highlight(final GraphicsContext gc);
}
