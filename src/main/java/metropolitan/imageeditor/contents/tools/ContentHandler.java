package metropolitan.imageeditor.contents.tools;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import metropolitan.imageeditor.contents.models.Content;
import metropolitan.imageeditor.contents.models.Editor;
import metropolitan.imageeditor.contents.models.elements.ImageContent;
import metropolitan.imageeditor.contents.models.elements.TextContent;
import metropolitan.imageeditor.utils.Util;

import java.io.File;

public class ContentHandler {
    public static final ContentHandler INSTANCE = new ContentHandler();

    private final ObservableList<Content> contents;

    private ContentHandler() {
        this.contents = FXCollections.observableArrayList();
    } // Prevent instantiation

    public ObservableList<Content> getContents() {
        return this.contents;
    }

    public void addContent(Content content) {
        content.setListener(observable -> Editor.INSTANCE.getController().forceUpdate());
        this.contents.add(content);
    }

    public void addImageContent() {
        File file = Util.File.openFile();
        if (file == null) return;
        this.addContent(new ImageContent(file));
    }

    public void addTextContent() {
        TextContent content = new TextContent("New Text");
        content.contentProperty().addListener((e, o, n) -> Editor.INSTANCE.getController().forceUpdate());
        content.colorProperty().addListener((e, o, n) -> Editor.INSTANCE.getController().forceUpdate());
        this.addContent(content);
    }

    public void removeContent(Content content) {
        this.contents.remove(content);
    }

    public void clearContents() {
        this.contents.clear();
    }
}