import javafx.geometry.Pos;
import javafx.scene.control.Label;

public class InputHistoryLabel extends Label {
    public InputHistoryLabel() {
        super("");
        setId("input-history-label");
        setMaxWidth(Double.MAX_VALUE);
        setAlignment(Pos.CENTER_RIGHT);
    }

    public void clear() {
        setText("");
    }
}
