package metropolitan.imageeditor.contents.models;

import metropolitan.imageeditor.controllers.*;

public class Editor {
    public static final Editor INSTANCE = new Editor();

    private final EditorController controller;
    private final MenuController menuController;
    private final PropertyController propertyController;
    private final ContentController contentController;
    private final ToolbarController toolbarController;

    private Editor() {
        this.controller = new EditorController();
        this.menuController = new MenuController();
        this.propertyController = new PropertyController();
        this.contentController = new ContentController(this.propertyController);
        this.toolbarController = new ToolbarController();

        this.controller.getRoot().setTop(this.menuController.getRoot());
        this.controller.getRoot().setRight(this.contentController.getRoot());
        this.controller.getRoot().setLeft(this.toolbarController.getRoot());

        ImageHandler.INSTANCE.imageProperty().addListener((e, o, n) -> {
            if (n == null) return;
            this.controller.getCanvas().setWidth(n.getWidth());
            this.controller.getCanvas().setHeight(n.getHeight());
            this.controller.forceUpdate();
        });

        this.menuController.onOpen = ImageHandler.INSTANCE::open;
        this.menuController.onSave = ImageHandler.INSTANCE::save;
        this.menuController.onSaveAs = ImageHandler.INSTANCE::saveAs;
    }

    public EditorController getController() {
        return this.controller;
    }

    public MenuController getMenuController() {
        return this.menuController;
    }

    public PropertyController getPropertyController() {
        return this.propertyController;
    }

    public ContentController getContentController() {
        return this.contentController;
    }

    public ToolbarController getToolbarController() {
        return this.toolbarController;
    }
}