package com.mycompany.connessioneudp_multicast;

import java.net.DatagramPacket;
import java.net.DatagramSocket;


public class Server {
    
    int porta = 4002;
    int groupPort = 4001;
    byte[] bufferIN = new byte[1024];
    DatagramPacket sendPacket;
    DatagramPacket receivePacket = null;
    DatagramSocket serverSocket = null;
    String received;
    String send;
}
