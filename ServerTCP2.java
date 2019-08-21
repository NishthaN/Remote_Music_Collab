import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

class ServerTCP2 {
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
            String songName = songChosen + ".mp3";
            String FiletoSend = System.getProperty("user.dir") + "/" + songName;

            File myFile = new File (FiletoSend);
            DataOutputStream dos = new DataOutputStream(socket.getOutputStream());
            FileInputStream fis = new FileInputStream(myFile);
            BufferedInputStream bis = new BufferedInputStream(fis);
            byte[] buffer = new byte[(int)myFile.length()];
            bis = new BufferedInputStream(fis);
            bis.read(buffer,0,buffer.length);
            while (fis.read(buffer) > 0) {
                dos.write(buffer);
            }
            System.out.println("Sending "  + "(" + buffer.length + " bytes)");
            dos.write(buffer,0,buffer.length);
            dos.flush();

            fis.close();
            dos.close();
            socket.close();




        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}