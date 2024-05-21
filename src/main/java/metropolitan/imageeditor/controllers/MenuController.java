package metropolitan.imageeditor.controllers;

import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import metropolitan.imageeditor.contents.tools.Mode;
import metropolitan.imageeditor.contents.tools.Toolbar;

public class MenuController {
    private final MenuBar root;
    public Runnable onOpen, onSave, onSaveAs;

    public MenuController() {
        this.root = new MenuBar();

        // File menu
        Menu fileMenu = new Menu("File");
        MenuItem openItem = new MenuItem("Open");
        openItem.setOnAction(e -> onOpen.run());
        MenuItem saveItem = new MenuItem("Save");
        saveItem.setOnAction(e -> onSave.run());
        MenuItem saveAsItem = new MenuItem("Save As");
        saveAsItem.setOnAction(e -> onSaveAs.run());
        fileMenu.getItems().addAll(openItem, saveItem, saveAsItem);

        // Tools menu
        Menu toolMenu = new Menu("Tools");
        MenuItem selectorItem = new MenuItem("Selection");
        selectorItem.setOnAction(e -> Toolbar.INSTANCE.setMode(Mode.SELECTION));
        MenuItem moveItem = new MenuItem("Move");
        moveItem.setOnAction(e -> Toolbar.INSTANCE.setMode(Mode.MOVE));
        MenuItem resizeItem = new MenuItem("Resize");
        resizeItem.setOnAction(e -> Toolbar.INSTANCE.setMode(Mode.RESIZE));
        MenuItem rotateItem = new MenuItem("Rotate");
        rotateItem.setOnAction(e -> Toolbar.INSTANCE.setMode(Mode.ROTATE));
        toolMenu.getItems().addAll(selectorItem, moveItem, resizeItem, rotateItem);

        // Help menu
        Menu helpMenu = new Menu("Help");
        MenuItem aboutItem = new MenuItem("About");
        helpMenu.getItems().add(aboutItem);

        this.root.getMenus().addAll(fileMenu, toolMenu, helpMenu);
    }

    public MenuBar getRoot() {
        return this.root;
    }
}