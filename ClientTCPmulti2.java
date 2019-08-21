import java.io.*;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Scanner;

public class ClientTCPmulti2 implements Runnable {

    // The client socket
    private static Socket clientSocket = null;
    // The output stream
    private static PrintStream os = null;
    // The input stream
    private static DataInputStream is = null;

    private static BufferedReader inputLine = null;
    private static boolean closed = false;

    public final static String
            FILE_TO_RECEIVED = "/Users/nishthanayar/Library/Preferences/IdeaIC2018.2/scratches/songqq.mp3";
    int FILE_SIZE = Integer.MAX_VALUE;

    public static void main(String[] args) {

        // The default port.
        int portNumber = 2222;
        // The default host.
        String host = "localhost";

        if (args.length < 2) {
            System.out
                    .println("Now using host=" + host + ", portNumber=" + portNumber);
        } else {
            host = args[0];
            portNumber = Integer.valueOf(args[1]).intValue();
        }

        /*
         * Open a socket on a given host and port. Open input and output streams.
         */
        try {
            clientSocket = new Socket(host, portNumber);
            inputLine = new BufferedReader(new InputStreamReader(System.in));
            //os = new PrintStream(clientSocket.getOutputStream());
            is = new DataInputStream(clientSocket.getInputStream());
        } catch (UnknownHostException e) {
            System.err.println("Don't know about host " + host);
        } catch (IOException e) {
            System.err.println("Couldn't get I/O for the connection to the host "
                    + host);
        }

        /*
         * If everything has been initialized then we want to write some data to the
         * socket we have opened a connection to on the port portNumber.
         */
        if (clientSocket != null && os != null && is != null) {
            try {
                ArrayList<String> songList = new ArrayList<String>();
                String filename = "/Users/nishthanayar/Library/Preferences/IdeaIC2018.2/scratches/SongList.txt";
                File list = new File(filename);
                Scanner scan = new Scanner(list);
                /* while (scan.hasNextLine()) {
                    songList.add(scan.nextLine());
                }
                System.out.println("Song list is:");
                for(int i=0; i<songList.size(); i++){
                    System.out.println(songList.get(i));
                }
*/
                /* Create a thread to read from the server. */
                new Thread(new ClientTCPmulti2()).start();



                /*System.out.println("a");
                Scanner in = new Scanner(System.in);
                String choice = in.nextLine();
                OutputStream os = clientSocket.getOutputStream();
                OutputStreamWriter osw = new OutputStreamWriter(os);
                BufferedWriter bw = new BufferedWriter(osw);
                String sendMessage = choice + "\n";

                while(!closed) {
                    bw.write(sendMessage);
                }
                */

                DataInputStream dis = new DataInputStream(clientSocket.getInputStream());
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
                 * Close the output stream, close the input stream, close the socket.
                 */
                os.close();
                is.close();
                clientSocket.close();
            } catch (IOException e) {
                System.err.println("IOException:  " + e);
            }
        }
    }

    /*
     * Create a thread to read from the server. (non-Javadoc)
     *
     * @see java.lang.Runnable#run()
     */
    public void run() {
        /*
         * Keep on reading from the socket till we receive "Bye" from the
         * server. Once we received that then we want to break.
         */
        String responseLine;

        DataInputStream dis = null;
        try {
            dis = new DataInputStream(clientSocket.getInputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream(FILE_TO_RECEIVED);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        byte[] buffer = new byte[100000000];
        System.out.println("hey");

        try {
            while ((responseLine = is.readLine()) != null) {
                System.out.println(responseLine);
                if (responseLine.indexOf("*** Bye") != -1)
                    break;
            }

        } catch (IOException e) {
            System.err.println("IOException:  " + e);
        }

    }
}