package main;

import javax.swing.SwingUtilities;

import view.ClienteGUI;
import view.ServidorGUI;

public class Main {

	public static void main(String[] args) {
		// Inicia o servidor 
        new Thread(() -> {
            SwingUtilities.invokeLater(() -> {
                new ServidorGUI().setVisible(true);
            });
        }).start();


        // Cliente 
        SwingUtilities.invokeLater(() -> {
            new ClienteGUI().setVisible(true);
        });
	}

}
