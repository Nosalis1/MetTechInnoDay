package metropolitan.imageeditor.controllers;

import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.util.Callback;
import metropolitan.imageeditor.contents.models.Content;
import metropolitan.imageeditor.contents.tools.ContentHandler;
import metropolitan.imageeditor.contents.tools.Selector;
import metropolitan.imageeditor.utils.Util;

public class ContentController {
    private final VBox root;
    private final ListView<Content> listView;

    public ContentController(final PropertyController propertyController) {
        this.root = new VBox();
        this.listView = new ListView<>(ContentHandler.INSTANCE.getContents());

        this.listView.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> Selector.INSTANCE.setSelectedElement(newValue));

        this.initializeListView();

        HBox buttons = new HBox();
        buttons.setAlignment(javafx.geometry.Pos.CENTER);
        buttons.setSpacing(10);

        buttons.getChildren().addAll(
                Util.Button.iconButton("add_image.png", ContentHandler.INSTANCE::addImageContent),
                Util.Button.iconButton("add_text.png", ContentHandler.INSTANCE::addTextContent),
                Util.Button.iconButton("delete.png", this::deleteSelected)
        );

        this.root.setAlignment(javafx.geometry.Pos.CENTER);
        this.root.setSpacing(10);
        this.root.setPadding(new Insets(10));

        this.root.getChildren().addAll(
                buttons, this.listView, propertyController.getRoot()
        );
    }

    public VBox getRoot() {
        return this.root;
    }

    public ListView<Content> getListView() {
        return this.listView;
    }

    private void deleteSelected() {
        Content selected = this.listView.getSelectionModel().getSelectedItem();
        if (selected == null) return;
        ContentHandler.INSTANCE.removeContent(selected);
    }

    public void initializeListView() {
        this.listView.setCellFactory(new Callback<>() {
            @Override
            public ListCell<Content> call(ListView<Content> listView) {
                return new ListCell<>() {
                    private final HBox content;
                    private final Button upButton;
                    private final Button downButton;

                    {
                        this.content = new HBox();
                        this.content.setSpacing(5);
                        this.upButton = Util.Button.iconButton("up-arrow.png", this::moveUp, 12);
                        this.downButton = Util.Button.iconButton("down-arrow.png", this::moveDown, 12);
                        this.content.getChildren().addAll(this.upButton, this.downButton);
                    }

                    private void moveUp() {
                        int currentIndex = getIndex();
                        if (currentIndex > 0) {
                            Content item = listView.getItems().remove(currentIndex);
                            listView.getItems().add(currentIndex - 1, item);
                            listView.getSelectionModel().select(currentIndex - 1);
                        }
                    }

                    private void moveDown() {
                        int currentIndex = getIndex();
                        if (currentIndex < listView.getItems().size() - 1) {
                            Content item = listView.getItems().remove(currentIndex);
                            listView.getItems().add(currentIndex + 1, item);
                            listView.getSelectionModel().select(currentIndex + 1);
                        }
                    }

                    @Override
                    protected void updateItem(Content content, boolean empty) {
                        super.updateItem(content, empty);
                        if (empty) {
                            setGraphic(null);
                            setText(null);
                        } else {
                            setGraphic(this.content);
                            setText(content.toString());
                        }
                    }
                };
            }
        });
    }
}
