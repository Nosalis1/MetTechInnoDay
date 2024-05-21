package metropolitan.imageeditor.contents.models;

import javafx.beans.InvalidationListener;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.scene.canvas.GraphicsContext;
import metropolitan.imageeditor.contents.interfaces.Displayable;

public abstract class Content implements Displayable {
    private final DoubleProperty x;
    private final DoubleProperty y;
    private final DoubleProperty width;
    private final DoubleProperty height;
    private final DoubleProperty angle;

    public Content() {
        this(0, 0, 100, 100, 0);
    }

    public Content(double x, double y, double width, double height, double angle) {
        this.x = new SimpleDoubleProperty(x);
        this.y = new SimpleDoubleProperty(y);
        this.width = new SimpleDoubleProperty(width);
        this.height = new SimpleDoubleProperty(height);
        this.angle = new SimpleDoubleProperty(angle);
    }

    public DoubleProperty xProperty() {
        return this.x;
    }

    public DoubleProperty yProperty() {
        return this.y;
    }

    public DoubleProperty widthProperty() {
        return this.width;
    }

    public DoubleProperty heightProperty() {
        return this.height;
    }

    public DoubleProperty angleProperty() {
        return this.angle;
    }

    public double getX() {
        return this.x.get();
    }

    public double getY() {
        return this.y.get();
    }

    public double getWidth() {
        return this.width.get();
    }

    public double getHeight() {
        return this.height.get();
    }

    public double getAngle() {
        return this.angle.get();
    }

    public void setX(double x) {
        this.x.set(x);
    }

    public void setY(double y) {
        this.y.set(y);
    }

    public void setWidth(double width) {
        this.width.set(width);
    }

    public void setHeight(double height) {
        this.height.set(height);
    }

    public void setAngle(double angle) {
        this.angle.set(angle);
    }

    public void move(double deltaX, double deltaY) {
        this.setX(this.getX() + deltaX);
        this.setY(this.getY() + deltaY);
    }

    public void resize(double deltaX, double deltaY) {
        this.setWidth(this.getWidth() + deltaX);
        this.setHeight(this.getHeight() + deltaY);
    }

    public void rotate(double angle) {
        this.setAngle(this.getAngle() + angle);
    }

    public void setListener(InvalidationListener listener) {
        this.x.addListener(listener);
        this.y.addListener(listener);
        this.width.addListener(listener);
        this.height.addListener(listener);
        this.angle.addListener(listener);
    }

    @Override
    public abstract void render(final GraphicsContext gc);

    @Override
    public abstract void highlight(final GraphicsContext gc);

    @Override
    public abstract boolean inBounds(double x, double y);
}