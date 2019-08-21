import java.io.*;
import java.net.*;
import java.net.MalformedURLException;
import java.net.URL;
//import javazoom.jlgui.basicplayer.BasicPlayer;
//import javazoom.jlgui.basicplayer.BasicPlayerException;


public class ServerTCP {
    private static Socket socket;


    public static void main(String[] args) {
        try {

            ServerSocket serverSocket = new ServerSocket(8000);
            System.out.println("Server Started");
            socket = serverSocket.accept();



            DataInputStream is = new DataInputStream(socket.getInputStream());
           InputStreamReader isr = new InputStreamReader(is);
            BufferedReader br = new BufferedReader(isr);
            String songChosen = br.readLine();
            System.out.println("Message received from client is "+songChosen);
            String songName = songChosen + ".mp3\n";

            DataOutputStream os = new DataOutputStream(socket.getOutputStream());
            OutputStreamWriter osw = new OutputStreamWriter(os);
            BufferedWriter bw = new BufferedWriter(osw);
            bw.write(songName);
            System.out.println(songName);
            bw.flush();




        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

}