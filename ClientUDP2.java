//import com.sun.xml.internal.ws.addressing.W3CAddressingConstants;
//import javazoom.jlgui.basicplayer.BasicPlayer;
//import javazoom.jlgui.basicplayer.BasicPlayerException;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.*;
import java.util.ArrayList;
import java.util.Scanner;
//import javazoom.jl.player.*;
import java.io.FileInputStream;

class ClientUDP2 {
    public static void main(String[] args) {
         String FILE_TO_RECEIVED = "/Users/nishthanayar/Library/Preferences/IdeaIC2018.2/scratches/song.mp3";
        ArrayList<String> songList = new ArrayList<String>();

        try {


            String filename = "SongList.txt";
            File list = new File(filename);
            Scanner scan = new Scanner(list);
            while (scan.hasNextLine()) {
                songList.add(scan.nextLine());
            }


            DatagramSocket socket = new DatagramSocket();
            InetAddress IPAddress = InetAddress.getByName("localhost");

            byte[] sendData = new byte[256];
            byte[] receiveData = new byte[256];


            System.out.println("Song list is:");
            int i;
            for (i = 0; i < songList.size(); i++) {
                System.out.println(songList.get(i));
            }

            System.out.println("Choose a song to play:");

            Scanner in = new Scanner(System.in);
            String s = in.nextLine();
            sendData = s.getBytes();
            System.out.println(sendData);
            DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, IPAddress,
                    4445);
            socket.send(sendPacket);

            byte b[]=new byte[1000000];
            FileOutputStream f=new FileOutputStream(FILE_TO_RECEIVED);


                DatagramPacket dp=new DatagramPacket(b,b.length);

        socket.receive(dp);
        f.write(b);


            /*BasicPlayer player = new BasicPlayer();
            player.open(new File(FILE_TO_RECEIVED));
            player.play();
            */




        } catch (UnknownHostException e) {
            e.printStackTrace();
        } catch (SocketException e) {
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } /* catch (BasicPlayerException e) {
            e.printStackTrace();
        } */
    }
}