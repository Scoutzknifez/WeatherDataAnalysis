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
        setPixelPerMinuteSpinner();
        setLowTemperatureDisplayedSpinner();
        setHighTemperatureDisplayedSpinner();

        // Drawing panel
        paintPanel = new CustomPanel(this);
    }

    private void setPixelPerMinuteSpinner() {
        // pixels per minute spinner model
        pixelPerMinuteSpinner = new JSpinner(
            new SpinnerNumberModel(
                ViewModelConstants.startVal,
                ViewModelConstants.min,
                ViewModelConstants.max,
                ViewModelConstants.stepSize
            )
        );

        // pixels per minute Spinner increment/decrement listener
        pixelPerMinuteSpinner.addChangeListener(changeEvent -> {
            ((CustomPanel) paintPanel).setMinutePixelIncrementer((double) pixelPerMinuteSpinner.getValue());
            paintPanel.repaint();
        });
    }

    private void setLowTemperatureDisplayedSpinner() {
        // low temperature displayed spinner model
        lowTemperatureDisplayedSpinner = new JSpinner(
            new SpinnerNumberModel(
                ViewModelConstants.startLow,
                ViewModelConstants.lowMin,
                ViewModelConstants.lowMax,
                ViewModelConstants.temperatureStepSize
            )
        );

        lowTemperatureDisplayedSpinner.addChangeListener(changeEvent -> {
            ((CustomPanel) paintPanel).setLowestTempDisplayed((int) lowTemperatureDisplayedSpinner.getValue());
            paintPanel.repaint();
        });
    }

    private void setHighTemperatureDisplayedSpinner() {
        // high temperature displayed spinner model
        highTemperatureDisplayedSpinner = new JSpinner(
            new SpinnerNumberModel(
                ViewModelConstants.startHigh,
                ViewModelConstants.highMin,
                ViewModelConstants.highMax,
                ViewModelConstants.temperatureStepSize
            )
        );

        highTemperatureDisplayedSpinner.addChangeListener(changeEvent -> {
            ((CustomPanel) paintPanel).setHighestTempDisplayed((int) highTemperatureDisplayedSpinner.getValue());
            paintPanel.repaint();
        });
    }
}
