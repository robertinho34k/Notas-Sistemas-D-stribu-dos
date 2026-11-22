package main;

import comunicador.ServidorUDP;

public class MainServidor {

    public static void main(String[] args) {

        ServidorUDP servidor = new ServidorUDP();

        servidor.iniciarServidor();
    }
}