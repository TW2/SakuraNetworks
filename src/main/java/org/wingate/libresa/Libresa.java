package org.wingate.libresa;

import com.formdev.flatlaf.FlatLightLaf;
import java.awt.EventQueue;

/**
 *
 * @author util2
 */
public class Libresa {

    public static void main(String[] args) {
        System.out.println("Welcome to Libre Sakura Alexia (Sakura Networks)");
        EventQueue.invokeLater(() -> {
            FlatLightLaf.setup();
            MainFrame mf = new MainFrame();
            mf.setTitle("Sakura Networks");
            mf.setSize(1900, 1000);
            mf.setLocationRelativeTo(null);
            mf.setVisible(true);
        });
    }
}
