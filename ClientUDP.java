import javazoom.jlgui.basicplayer.BasicPlayer;
import javazoom.jlgui.basicplayer.BasicPlayerException;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.*;
import java.util.ArrayList;
import java.util.Scanner;

public class ClientUDP{

    public static void main(String[] args) {

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
            for(i=0; i<songList.size(); i++){
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

            DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);
            socket.receive(receivePacket);
            System.out.println(receivePacket);
            String sentence = new String(receivePacket.getData());
            String songToplay = "";
            int j=0;

            while(sentence.charAt(j) != 0){
                songToplay = songToplay + sentence.charAt(j);
                j++;
                System.out.println(sentence.charAt(j));

            }
            System.out.println(sentence);
            System.out.println(songToplay);
            socket.close();

            /*BasicPlayer player = new BasicPlayer();
            String songName = songToplay + ".mp3";

            String pathToMp3 = System.getProperty("user.dir") + "/" + songName;
            try {
                player.open(new URL("file:///" + pathToMp3));
                player.play();
            } catch (BasicPlayerException | MalformedURLException e) {
                e.printStackTrace();

            }
            */

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (SocketException e) {
            e.printStackTrace();
        } catch (UnknownHostException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}

