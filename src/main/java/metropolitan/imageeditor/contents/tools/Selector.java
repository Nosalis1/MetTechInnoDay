package metropolitan.imageeditor.contents.tools;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import metropolitan.imageeditor.contents.models.Content;

public class Selector {
    public static final Selector INSTANCE = new Selector(); // Singleton

    private final ObjectProperty<Content> selectedElement;

    private Selector() {
        this.selectedElement = new SimpleObjectProperty<>(null);
    } // Prevent instantiation

    public ObjectProperty<Content> selectedElementProperty() {
        return this.selectedElement;
    }

    public Content getSelectedElement() {
        return this.selectedElement.get();
    }

    public void setSelectedElement(Content selectedElement) {
        this.selectedElement.set(selectedElement);
    }

    public void clearSelectedElement() {
        this.selectedElement.set(null);
    }

    public boolean hasSelectedElement() {
        return this.selectedElement.get() != null;
    }
}
