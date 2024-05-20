module metropolitan.imageeditor {
    requires javafx.controls;
    requires javafx.fxml;


    opens metropolitan.imageeditor to javafx.fxml;
    exports metropolitan.imageeditor;
}