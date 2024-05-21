package metropolitan.imageeditor.controllers;

import javafx.scene.control.ColorPicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.util.converter.NumberStringConverter;
import metropolitan.imageeditor.contents.models.elements.TextContent;
import metropolitan.imageeditor.contents.tools.Selector;

public class PropertyController {
    private final GridPane root;
    private final TextField[] textFields;
    private final TextField contentField;
    private final ColorPicker colorPicker;

    public PropertyController() {
        this.root = new GridPane();
        this.textFields = new TextField[]{
                new TextField("X: "),
                new TextField("Y: "),
                new TextField("Width: "),
                new TextField("Height: "),
                new TextField("Angle: ")
        };
        this.contentField = new TextField("");
        this.contentField.setVisible(false);

        this.colorPicker = new ColorPicker();
        this.colorPicker.setVisible(false);

        Label contentLabel = new Label("Content: ");
        contentLabel.setVisible(false);
        Label colorLabel = new Label("Color: ");
        colorLabel.setVisible(false);

        this.root.setVgap(10);
        this.root.setHgap(5);

        NumberStringConverter nsc = new NumberStringConverter();
        Selector.INSTANCE.selectedElementProperty().addListener((e, o, n) -> {
            if (o != null) {
                this.textFields[0].textProperty().unbindBidirectional(o.xProperty());
                this.textFields[1].textProperty().unbindBidirectional(o.yProperty());
                this.textFields[2].textProperty().unbindBidirectional(o.widthProperty());
                this.textFields[3].textProperty().unbindBidirectional(o.heightProperty());
                this.textFields[4].textProperty().unbindBidirectional(o.angleProperty());

                TextContent oText = o instanceof TextContent ? (TextContent) o : null;
                if (oText != null) {
                    this.contentField.textProperty().unbindBidirectional(oText.contentProperty());
                    this.colorPicker.valueProperty().unbindBidirectional(oText.colorProperty());
                }
            }
            if (n != null) {
                this.textFields[0].textProperty().bindBidirectional(n.xProperty(), nsc);
                this.textFields[1].textProperty().bindBidirectional(n.yProperty(), nsc);
                this.textFields[2].textProperty().bindBidirectional(n.widthProperty(), nsc);
                this.textFields[3].textProperty().bindBidirectional(n.heightProperty(), nsc);
                this.textFields[4].textProperty().bindBidirectional(n.angleProperty(), nsc);

                TextContent nText = n instanceof TextContent ? (TextContent) n : null;
                if (nText != null) {
                    this.contentField.textProperty().bindBidirectional(nText.contentProperty());
                    this.colorPicker.valueProperty().bindBidirectional(nText.colorProperty());
                }
            }
            boolean vis = n instanceof TextContent;
            contentLabel.setVisible(vis);
            this.contentField.setVisible(vis);
            colorLabel.setVisible(vis);
            this.colorPicker.setVisible(vis);

            this.root.setVisible(n != null);
        });

        for (int i = 0; i < this.textFields.length; i++) {
            this.root.add(new Label(this.textFields[i].getText()), 0, i);
            this.textFields[i].clear();
            this.root.add(this.textFields[i], 1, i);
        }
        this.root.add(contentLabel, 0, this.textFields.length);
        this.root.add(this.contentField, 1, this.textFields.length);

        this.root.add(colorLabel, 0, this.textFields.length + 1);
        this.root.add(this.colorPicker, 1, this.textFields.length + 1);

        this.root.setVisible(false);
    }

    public GridPane getRoot() {
        return this.root;
    }
}