package metropolitan.imageeditor.controllers;

import javafx.beans.InvalidationListener;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import metropolitan.imageeditor.contents.models.Content;
import metropolitan.imageeditor.contents.models.ImageHandler;
import metropolitan.imageeditor.contents.tools.ContentHandler;
import metropolitan.imageeditor.contents.tools.Selector;
import metropolitan.imageeditor.contents.tools.Toolbar;

public class EditorController {

    private final BorderPane root;
    private final Canvas canvas;
    private final GraphicsContext graphicsContext;

    public EditorController() {
        this.root = new BorderPane();
        this.canvas = new Canvas(800, 600);
        this.graphicsContext = this.canvas.getGraphicsContext2D();

        // Setup content pane
        StackPane contentPane = new StackPane(this.canvas);
        contentPane.setStyle("""
                -fx-border-color: #f933a3;
                -fx-border-insets: 5px;
                -fx-border-width: 1.5px;
                -fx-border-style: solid;
                    """);
        contentPane.setMaxSize(800, 600);

        // Set canvas size callback to update content pane size
        this.canvas.widthProperty().addListener((e, o, n) -> contentPane.setMaxWidth(n.doubleValue()));
        this.canvas.heightProperty().addListener((e, o, n) -> contentPane.setMaxHeight(n.doubleValue()));

        this.root.setCenter(contentPane);

        // Assign event handlers
        this.canvas.setOnMousePressed(event -> Toolbar.INSTANCE.onMousePressed(event.getX(), event.getY()));
        this.canvas.setOnMouseDragged(event -> Toolbar.INSTANCE.onMouseDragged(event.getX(), event.getY()));
        this.canvas.setOnMouseReleased(event -> Toolbar.INSTANCE.onMouseReleased(event.getX(), event.getY()));

        // Setup content handler and selector listeners
        ContentHandler.INSTANCE.getContents().addListener((InvalidationListener) observable -> forceUpdate());
        Selector.INSTANCE.selectedElementProperty().addListener(observable -> forceUpdate());
    }

    public BorderPane getRoot() {
        return this.root;
    }

    public Canvas getCanvas() {
        return this.canvas;
    }

    public GraphicsContext getGraphicsContext() {
        return this.graphicsContext;
    }

    public void forceClear() {
        this.graphicsContext.clearRect(0, 0, this.canvas.getWidth(), this.canvas.getHeight());
    }

    public void forceUpdate() {
        this.cleanUpdate();
        if (Selector.INSTANCE.hasSelectedElement()) {
            Selector.INSTANCE.getSelectedElement().highlight(this.graphicsContext);
        }
    }

    public void cleanUpdate() {
        this.forceClear();
        if (ImageHandler.INSTANCE.getImage() == null) {
            return;
        }
        this.graphicsContext.drawImage(ImageHandler.INSTANCE.getImage(), 0, 0, this.canvas.getWidth(), this.canvas.getHeight());
        for (Content content : ContentHandler.INSTANCE.getContents()) {
            content.render(this.graphicsContext);
        }
    }
}