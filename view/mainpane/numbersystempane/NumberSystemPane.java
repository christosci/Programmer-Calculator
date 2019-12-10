import javafx.geometry.VPos;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;

import java.util.ArrayList;
import java.util.Arrays;

public class NumberSystemPane extends GridPane {
    private final NumberSystemToggleGroup numberSystemToggleGroup;
    private final ArrayList<NumberSystemLabel> numberSystemLabels = new ArrayList<>();

    public NumberSystemPane() {
        setVgap(5);
        setHgap(12);

        // Set row constraints
        RowConstraints defaultRowConstraints = new RowConstraints();
        RowConstraints binaryRowConstraints = new RowConstraints(30);
        binaryRowConstraints.setValignment(VPos.TOP);
        getRowConstraints().addAll(defaultRowConstraints, defaultRowConstraints, defaultRowConstraints, binaryRowConstraints);

        // Create the radio buttons
        numberSystemToggleGroup = new NumberSystemToggleGroup();
        addColumn(0, numberSystemToggleGroup.getHexRadioButton(), numberSystemToggleGroup.getDecRadioButton(),
                numberSystemToggleGroup.getOctRadioButton(), numberSystemToggleGroup.getBinRadioButton());

        // Create the labels
        NumberSystemLabel hexLabel = new NumberSystemLabel(NumberSystem.HEX);
        NumberSystemLabel decLabel = new NumberSystemLabel(NumberSystem.DEC);
        NumberSystemLabel octLabel = new NumberSystemLabel(NumberSystem.OCT);
        NumberSystemLabel binLabel = new NumberSystemLabel(NumberSystem.BIN);
        numberSystemLabels.addAll(Arrays.asList(hexLabel, decLabel, octLabel, binLabel));

        addColumn(1, hexLabel, decLabel, octLabel, binLabel);
    }

    public NumberSystemToggleGroup getNumberSystemToggleGroup() {
        return numberSystemToggleGroup;
    }

    public ArrayList<NumberSystemLabel> getNumberSystemLabels() {
        return numberSystemLabels;
    }
}
