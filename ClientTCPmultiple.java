//import javazoom.jlgui.basicplayer.BasicPlayer;
//import javazoom.jlgui.basicplayer.BasicPlayerException;

import java.io.*;
import java.net.MalformedURLException;
import java.net.Socket;
import java.net.URL;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Scanner;

class ClientTCPmultiple

{
    public static void main(String[] args) {
        ArrayList<String> songList = new ArrayList<String>();


        try{
            int i;
            Socket client =  new Socket("192.168.43.126", 8000);
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

            InputStream is = client.getInputStream();
            InputStreamReader isr = new InputStreamReader(is);
            BufferedReader br = new BufferedReader(isr);
            String message = br.readLine();
            System.out.println("wut");
            System.out.println(message);

            br.close();

            String pathToMp3 = System.getProperty("user.dir") +"/"+ message;
            System.out.println(pathToMp3);
            BasicPlayer player = new BasicPlayer();
            try {
                player.open(new URL("file:///" + pathToMp3));
                player.play();
            } catch (BasicPlayerException | MalformedURLException e) {
                e.printStackTrace();
            }
            client.close();


        } catch (UnknownHostException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}