module metropolitan.imageeditor {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.desktop;


    opens metropolitan.imageeditor to javafx.fxml;
    exports metropolitan.imageeditor;
}