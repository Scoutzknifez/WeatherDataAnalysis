package com.scoutzknifez.weatherdataanalysis.graphics;

import lombok.Getter;

import javax.swing.*;

@Getter
public class OverallView {
    private JPanel parentContainer;
    private JPanel paintPanel;
    private JSpinner pixelPerMinuteSpinner;
    private JSpinner lowTemperatureDisplayedSpinner;
    private JSpinner highTemperatureDisplayedSpinner;

    private void createUIComponents() {
        // pixels per minute spinner model
        SpinnerNumberModel model = new SpinnerNumberModel(
                ViewModelConstants.startVal,
                ViewModelConstants.min,
                ViewModelConstants.max,
                ViewModelConstants.stepSize
        );
        pixelPerMinuteSpinner = new JSpinner(model);

        // low temperature displayed spinner model
        model = new SpinnerNumberModel(
                ViewModelConstants.startLow,
                ViewModelConstants.lowMin,
                ViewModelConstants.lowMax,
                ViewModelConstants.temperatureStepSize
        );
        lowTemperatureDisplayedSpinner = new JSpinner(model);

        // high temperature displayed spinner model
        model = new SpinnerNumberModel(
                ViewModelConstants.startHigh,
                ViewModelConstants.highMin,
                ViewModelConstants.highMax,
                ViewModelConstants.temperatureStepSize
        );
        highTemperatureDisplayedSpinner = new JSpinner(model);

        // Spinner increment/decrement listener
        pixelPerMinuteSpinner.addChangeListener(changeEvent -> {
            ((CustomPanel) paintPanel).setMinutePixelIncrementer((double) pixelPerMinuteSpinner.getValue());
            paintPanel.repaint();
        });

        // Drawing panel
        paintPanel = new CustomPanel(this);
    }
}
