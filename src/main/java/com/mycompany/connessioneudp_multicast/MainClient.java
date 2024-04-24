package com.mycompany.connessioneudp_multicast;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.MulticastSocket;
import java.net.SocketException;
import java.net.UnknownHostException;

public class MainClient {

    public static void main(String[] args) throws Exception {
     
       Client client = new Client(4002, 4001);
       client.avvio();
    }              
}
