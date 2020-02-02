package com.scoutzknifez.weatherdataanalysis.graphics;

import lombok.Getter;

import javax.swing.*;

@Getter
public class OverallView {
    private JPanel parentContainer;
    private JPanel paintPanel;
    private JSpinner ppmSpinner;

    private void createUIComponents() {
        // Drawing panel
        paintPanel = new CustomPanel();

        // Spinner Model
        double startVal = .4;
        double min = .05;
        double max = 1;
        double stepSize = .05;
        SpinnerNumberModel model = new SpinnerNumberModel(startVal, min, max, stepSize);
        ppmSpinner = new JSpinner(model);

        // Spinner increment/decrement listener
        ppmSpinner.addChangeListener(changeEvent -> {
            ((CustomPanel) paintPanel).setMinutePixelIncrementor((Double) ppmSpinner.getValue());
            paintPanel.repaint();
        });
    }
}
