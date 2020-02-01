package com.scoutzknifez.weatherdataanalysis.graphics;

import lombok.Getter;

import javax.swing.*;

@Getter
public class OverallView {
    private JPanel parentContainer;
    private JLabel frameLabel;
    private JPanel paintPanel;

    private void createUIComponents() {
        paintPanel = new CustomPanel();
    }
}
