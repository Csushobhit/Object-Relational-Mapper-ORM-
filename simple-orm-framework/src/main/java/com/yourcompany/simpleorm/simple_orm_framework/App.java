package com.yourcompany.simpleorm.simple_orm_framework;

import javax.swing.SwingUtilities;

public class App {

    public static void main(String[] args) {

        SwingUtilities.invokeLater(() -> {

            StudentDriver ui = new StudentDriver();
            ui.setVisible(true);

        });

    }
}