import javax.xml.crypto.Data;
import java.io.*;
import java.net.Socket;
import java.net.ServerSocket;

/*
 * A chat server that delivers public and private messages.
 */
public class ServerTCPmulti2 {

    // The server socket.
    private static ServerSocket serverSocket = null;
    // The client socket.
    private static Socket clientSocket = null;

    // This chat server can accept up to maxClientsCount clients' connections.
    private static final int maxClientsCount = 10;
    private static final clientThread[] threads = new clientThread[maxClientsCount];

    public static void main(String args[]) {

        // The default port number.
        int portNumber = 2222;
        if (args.length < 1) {
            System.out
                    .println("Now using port number=" + portNumber);
        } else {
            portNumber = Integer.valueOf(args[0]).intValue();
        }

        try {
            serverSocket = new ServerSocket(portNumber);
        } catch (IOException e) {
            System.out.println(e);
        }

        while (true) {
            try {
                clientSocket = serverSocket.accept();
                int i = 0;
                for (i = 0; i < maxClientsCount; i++) {
                    if (threads[i] == null) {
                        (threads[i] = new clientThread(clientSocket, threads)).start();
                        break;
                    }
                }
                if (i == maxClientsCount) {
                    PrintStream os = new PrintStream(clientSocket.getOutputStream());
                    os.println("Server too busy. Try later.");
                    os.close();
                    clientSocket.close();
                }
            } catch (IOException e) {
                System.out.println(e);
            }
        }
    }
}

class clientThread extends Thread {

    private DataInputStream is = null;
    private PrintStream os = null;
    private Socket clientSocket = null;
    private final clientThread[] threads;
    private int maxClientsCount;
    private DataOutputStream dos = null;
    FileInputStream fis = null;
    BufferedInputStream bis = null;

    public clientThread(Socket clientSocket, clientThread[] threads) {
        this.clientSocket = clientSocket;
        this.threads = threads;
        maxClientsCount = threads.length;
    }

    public void run() {
        int maxClientsCount = this.maxClientsCount;
        clientThread[] threads = this.threads;

        try {
            /*
             * Create input and output streams for this client.
             */
            //is = new DataInputStream(clientSocket.getInputStream());
            os = new PrintStream(clientSocket.getOutputStream());
            String a = System.getProperty("user.dir");
            System.out.println(a);
            os.println("Enter your choice.");

            /*String name = is.readLine().trim();
            String songName = name + ".mp3";
            System.out.println(songName);
            String FiletoSend = System.getProperty("user.dir") + "/" + songName;
            */
            String songName = "Jambi.mp3";
            System.out.println(songName);
            String FiletoSend = System.getProperty("user.dir") + "/" + songName;
            File myFile = new File (FiletoSend);

            System.out.println("aa");
            FileInputStream fis = new FileInputStream(myFile);
            BufferedInputStream bis;
            System.out.println("aa");

            DataOutputStream dos = new DataOutputStream(clientSocket.getOutputStream());

            System.out.println("aa");


            System.out.println("aaa");
            byte[] buffer = new byte[(int)myFile.length()];

            for (int i = 0; i < maxClientsCount; i++) {
                System.out.println("aaaa");
                System.out.println(threads[i]);

                if (threads[i] != null && threads[i] != this) {
                    System.out.println("ab");

                    bis = new BufferedInputStream(fis);
                    bis.read(buffer,0,buffer.length);

                    while (fis.read(buffer) > 0) {
                        threads[i].dos.write(buffer);
                        System.out.println("yes");

                    }

                    System.out.println("Sending "  + "(" + buffer.length + " bytes)");
                    dos.write(buffer,0,buffer.length);
                    dos.flush();


                }
            }
            /*while (true) {
                /*String line = is.readLine();
                if (line.startsWith("/quit")) {
                  break;
            }
                for (int i = 0; i < maxClientsCount; i++) {
                    if (threads[i] != null) {
                        //threads[i].os.println("Next song: " + line);
                    }
                }

            }
            */


            /*
             * Clean up. Set the current thread variable to null so that a new client
             * could be accepted by the server.
             */
            for (int i = 0; i < maxClientsCount; i++) {
                if (threads[i] == this) {
                    threads[i] = null;
                }
            }

            /*
             * Close the output stream, close the input stream, close the socket.
             */
            //is.close();
            os.close();
            clientSocket.close();
        } catch (IOException e) {
        }
    }
}
