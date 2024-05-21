package metropolitan.imageeditor.controllers;

import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import metropolitan.imageeditor.contents.tools.Mode;
import metropolitan.imageeditor.contents.tools.Toolbar;
import metropolitan.imageeditor.utils.Util;

public class ToolbarController {
    private final VBox root;
    private final Button[] buttons;

    public ToolbarController() {
        this.root = new VBox();
        this.buttons = new Button[Mode.values().length];

        for (int i = 0; i < buttons.length; i++) {
            final Mode mode = Mode.values()[i];
            buttons[i] = Util.Button.iconButton(mode.getImagePath(), () -> Toolbar.INSTANCE.setMode(mode), 30);
            buttons[i].setStyle("-fx-background-color: white;");
            this.root.getChildren().add(buttons[i]);
        }

        Toolbar.INSTANCE.modeProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue == null) return;
            this.buttons[newValue.ordinal()].setStyle("-fx-background-color: #00ff00;");
            if (oldValue == null) return;
            this.buttons[oldValue.ordinal()].setStyle("-fx-background-color: white;");
        });

        this.root.setAlignment(javafx.geometry.Pos.CENTER);
        this.root.setStyle("""
                 -fx-padding: 10px;
                 -fx-spacing: 10px;
                """);
    }

    public VBox getRoot() {
        return this.root;
    }

    public Button[] getButtons() {
        return this.buttons;
    }
}
