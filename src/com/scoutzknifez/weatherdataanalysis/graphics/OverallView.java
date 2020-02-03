package com.scoutzknifez.weatherdataanalysis.graphics;

import lombok.Getter;

import javax.swing.*;

@Getter
public class OverallView {
    private JPanel parentContainer;
    private JPanel paintPanel;
    private JSpinner ppmSpinner;

    private void createUIComponents() {
        // Spinner Model
        SpinnerNumberModel model = new SpinnerNumberModel(
                ViewModelConstants.startVal, ViewModelConstants.min, ViewModelConstants.max, ViewModelConstants.stepSize
        );
        ppmSpinner = new JSpinner(model);

        // Spinner increment/decrement listener
        ppmSpinner.addChangeListener(changeEvent -> {
            ((CustomPanel) paintPanel).setMinutePixelIncrementer((Double) ppmSpinner.getValue());
            paintPanel.repaint();
        });

        // Drawing panel
        paintPanel = new CustomPanel(this);
    }
}
