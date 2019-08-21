import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.Scanner;
import java.net.SocketException;


public class ServerUDP {


    public static void main(String[] args) {

        try {
            DatagramSocket ds = null;
            int i=0;

            ds = new DatagramSocket(4445);


            byte[] receiveData = new byte[256];
            byte[] sendData = new byte[256];
            System.out.println("Server Started");

            DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);
            System.out.println(receiveData.length);
            ds.receive(receivePacket);
            String sentence = new String(receivePacket.getData());

            String choice = "";

            while(sentence.charAt(i) != 0){
                choice = choice + sentence.charAt(i);
                i++;
            }

            System.out.println(choice);
            InetAddress IPAddress = receivePacket.getAddress();

            int port = receivePacket.getPort();

            sendData = choice.getBytes();

            DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, IPAddress, port);
            ds.send(sendPacket);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
