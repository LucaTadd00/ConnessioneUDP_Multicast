package com.mycompany.connessioneudp_multicast;

import java.io.IOException;
import java.net.BindException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;

public class MainServer {

    public static void main(String[] args) {

    
    int porta = 4002;
    byte[] bufferIN = new byte[1024];
    DatagramPacket sendPacket;
    DatagramPacket receivePacket = null;
    DatagramSocket serverSocket = null;
    String received;
    String send;

    
        try { 
            System.out.println("SERVER UDP");
            InetAddress IPServer = InetAddress.getByName("localhost");
            serverSocket = new DatagramSocket(porta); 
            System.out.println("Server avviato");
            
            while(true) {
                   
                receivePacket = new DatagramPacket(bufferIN, bufferIN.length);
                //si attende il pacchetto dal client
                serverSocket.receive(receivePacket);
                    
                InetAddress IPClient = receivePacket.getAddress();
                int clientPorta = receivePacket.getPort();
                
                received = new String(receivePacket.getData());
                int nCaratteri = receivePacket.getLength();
                received = received.substring(0, nCaratteri);
                System.out.println("Messaggio ricevuto dal Client " + IPClient + ":" + clientPorta + "\n\t" + received);
                
                send = "Ricevuta richiesta!";
                sendPacket = new DatagramPacket(send.getBytes(), send.length(), IPClient, clientPorta);
            
               
                serverSocket.send(sendPacket);
                System.out.println("Spedito messaggio al client: " + send);
                
                InetAddress groupAddress = InetAddress.getByName("239.255.255.250");
                int groupPort = 4001;
                
                send = "Benvenuti a tutti! C'è un nuovo ingresso!";
                sendPacket = new DatagramPacket(send.getBytes(), send.length(), groupAddress, groupPort);
            
                serverSocket.send(sendPacket);
                System.out.println("Spedito messaggio al gruppo: " + send);
            
            }
        } catch (BindException ex) {
            System.err.println("Porta già in uso");
        } catch (UnknownHostException ex) {                    
            System.err.println("Errore di risoluzione");
        } catch (SocketException e) {
            System.out.println("errore nella risposta al server");
            e.printStackTrace();
        } catch (IOException e) {
            System.out.println("errore generico nell'invio della risposta");
        } finally{
          if (serverSocket != null)
              serverSocket.close();
                }
        
    
    }              
}