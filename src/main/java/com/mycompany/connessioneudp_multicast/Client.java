
package com.mycompany.connessioneudp_multicast;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.MulticastSocket;
import java.net.SocketException;
import java.net.UnknownHostException;

/**
 *
 * @author asuf507zu4lp054w
 */
public class Client {
       
    int porta;
    int portaGroup;
    byte[] bufferIN = new byte[1024];
    byte[] bufferOUT = new byte[1024];
    byte[] bufferING = new byte[1024];
    DatagramPacket sendPacket;
    DatagramPacket receivePacket = null;
    DatagramSocket clientSocket = null;
    MulticastSocket multiSocket = null;
    String received;
    String dato;
    InetAddress IPServer;
     
    
    public Client(int porta, int portaGroup) {
    this.porta = porta;
    this.portaGroup = portaGroup;
    this.dato = "Richiesta connessione!";
    bufferOUT = dato.getBytes();
    }
      
    public void avvio() throws Exception {
        try {
        invia();
        ricevi();
        gruppo();
            
        } catch (UnknownHostException ex) {                    
            System.err.println("Errore di risoluzione");
        } catch (SocketException e) {
            System.out.println("errore nella risposta al server");
            e.printStackTrace();
        } catch (IOException e) {
            System.out.println("errore generico nell'invio della risposta");
        } finally{
          if (clientSocket != null)
              clientSocket.close();
          if (multiSocket != null)
              multiSocket.close();
        }
    }
    
    
    public void invia() throws Exception {
       IPServer = InetAddress.getByName("localhost");
       clientSocket = new DatagramSocket();  
                   
       sendPacket =  new DatagramPacket(bufferOUT, bufferOUT.length, IPServer, porta);
       System.out.println("Server trovato!!");
       clientSocket.send(sendPacket);
                    
       System.out.println("Richiesta inviata al Server!!");
    }
    
    public void ricevi() throws Exception {
        System.out.println("Preparazione per ricevere la risposta dal server.");

        receivePacket =  new DatagramPacket(bufferIN, bufferIN.length);
        clientSocket.receive(receivePacket);
        received = new String(receivePacket.getData());
        
        int nCaratteri = receivePacket.getLength();
        received = received.substring(0, nCaratteri);
        System.out.println("Messaggio ricevuto dal server " + IPServer + ":" + porta + "\n\t" + received);
    } 
    
    public void gruppo() throws Exception {
        multiSocket = new MulticastSocket(portaGroup);
        InetAddress group = InetAddress.getByName("239.255.255.250");
        multiSocket.joinGroup(group);
            
        receivePacket = new DatagramPacket(bufferING, bufferING.length);
        clientSocket.receive(receivePacket); 
            
        received = new String(receivePacket.getData(),0, receivePacket.getLength());
            
        System.out.println("Lettura dei dati ricevuti dai partecipanti al gruppo");
        System.out.println("Messaggio ricevuto dal gruppo " + group + ":" + portaGroup + "\n\t" + received);
    }            
}
