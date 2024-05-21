package metropolitan.imageeditor.contents.tools;

public enum Mode {
    SELECTION("selection_tool.png"),
    MOVE("move_tool.png"),
    RESIZE("resize_tool.png"),
    ROTATE("rotate_tool.png");

    private final String imagePath;

    Mode(String imagePath) {
        this.imagePath = imagePath;
    }

    public String getImagePath() {
        return this.imagePath;
    }
}