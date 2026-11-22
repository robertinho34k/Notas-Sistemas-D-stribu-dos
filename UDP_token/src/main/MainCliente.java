
package main;

import javax.swing.SwingUtilities;

import view.ClienteGUI;

public class MainCliente {

    public static void main(String[] args) {

        SwingUtilities.invokeLater(() -> {

            ClienteGUI tela = new ClienteGUI();

            tela.setVisible(true);

        });
    }
}
