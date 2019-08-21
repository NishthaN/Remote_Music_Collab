import java.io.*;
import java.net.*;

class ServerUDP2 {
    private static Socket socket;

    public static void main(String[] args) {
        try {
            DatagramSocket ds = null;
            int i = 0;

            ds = new DatagramSocket(4445);


            byte[] receiveData = new byte[256];
            byte[] sendData = new byte[256];
            System.out.println("Server Started");

            DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);
            System.out.println(receiveData.length);
            ds.receive(receivePacket);
            String sentence = new String(receivePacket.getData());

            String songChosen = "";

            while (sentence.charAt(i) != 0) {
                songChosen = songChosen + sentence.charAt(i);
                i++;
            }

            System.out.println("Message received from client is " + songChosen);
            String songName = songChosen + ".mp3";
            String FiletoSend = System.getProperty("user.dir") + "/" + songName;

            byte b[]=new byte[1000000];
            InetAddress IPAddress = receivePacket.getAddress();

            int port = receivePacket.getPort();

            FileInputStream f=new FileInputStream(FiletoSend);
            DatagramSocket dsoc=new DatagramSocket();
            int k=0;
            while(f.available()!=0)
            {
                b[k]=(byte)f.read();
                k++;
            }
            f.close();
            dsoc.send(new DatagramPacket(b,k,IPAddress,port));


        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}