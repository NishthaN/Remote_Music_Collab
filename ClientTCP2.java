//import javazoom.jlgui.basicplayer.BasicPlayer;
//import javazoom.jlgui.basicplayer.BasicPlayerException;

import java.io.*;
import java.net.MalformedURLException;
import java.net.Socket;
import java.net.URL;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Scanner;

class ClientTCP2 {

    public final static String
            FILE_TO_RECEIVED = "/Users/nishthanayar/Library/Preferences/IdeaIC2018.2/scratches/song.mp3";
    int FILE_SIZE = Integer.MAX_VALUE;
    public static void main(String[] args) throws BasicPlayerException, MalformedURLException {
        ArrayList<String> songList = new ArrayList<String>();
        int FILE_SIZE = 6022386;

        try{
            int i;
            Socket client =  new Socket("localhost", 8000);
            String filename = "SongList.txt";
            File list = new File(filename);
            Scanner scan = new Scanner(list);
            while (scan.hasNextLine()) {
                songList.add(scan.nextLine());
            }

            System.out.println("Song list is:");
            for(i=0; i<songList.size(); i++){
                System.out.println(songList.get(i));
            }

            System.out.println("Choose a song to play:");

            Scanner in = new Scanner(System.in);
            String choice = in.nextLine();


            OutputStream os = client.getOutputStream();
            OutputStreamWriter osw = new OutputStreamWriter(os);
            BufferedWriter bw = new BufferedWriter(osw);
            String sendMessage = choice + "\n";
            bw.write(sendMessage);
            bw.flush();


            DataInputStream dis = new DataInputStream(client.getInputStream());
            FileOutputStream fos = new FileOutputStream(FILE_TO_RECEIVED);
            byte[] buffer = new byte[100000000];


            int bytesRead = dis.read(buffer,0,buffer.length);
            int current = bytesRead;

            do {
                bytesRead =
                        dis.read(buffer, current, (buffer.length-current));
                if(bytesRead >= 0) current += bytesRead;
            } while(bytesRead > -1);

            fos.write(buffer, 0 , current);
            fos.flush();


            System.out.println("File " + " downloaded (" + current + " bytes read)");

            /*

            BasicPlayer player = new BasicPlayer();
            player.open(new File(FILE_TO_RECEIVED));
            player.play();
            client.close();
*/


        } catch (UnknownHostException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}