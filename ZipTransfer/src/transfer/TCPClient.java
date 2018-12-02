package transfer;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.zip.ZipFile;

public class TCPClient {
    public final static int SOCKET_PORT = 13267;      
    public final static String SERVER = "00.200.00.00";  
    public final static String
    FILE_TO_RECEIVED = "D:/Projects/Transferred.zip";
    public final static int FILE_SIZE = 5830740;
    public static void main(String args[]) {
        int bytesRead;
        int current = 0;
        PrintWriter pwr=null;
        FileOutputStream fos = null;
        BufferedOutputStream bos = null;
        BufferedReader br = null;
        Socket sock = null;
        String filesize= null, s=null;

        try {
            sock = connectToServer(SERVER, SOCKET_PORT);
            System.out.println("Connecting...");
            br= new BufferedReader(new InputStreamReader(sock.getInputStream()));
            pwr = new PrintWriter(sock.getOutputStream());
            String input=null, output = "SENDZIP";
            while((input = br.readLine()) != null){
                System.out.println("INPUT is "+input);
                if (input.contains("SENTZIP")){
                    byte [] mybytearray  = new byte [Integer.parseInt(s)];
                    InputStream is = sock.getInputStream();
                    fos = new FileOutputStream(FILE_TO_RECEIVED);
                    bos = new BufferedOutputStream(fos);
                    bytesRead = is.read(mybytearray,0,mybytearray.length);
                    current = bytesRead;
                    do {
                        bytesRead = is.read(mybytearray, current, (mybytearray.length-current));
                        if(bytesRead >= 0) current += bytesRead;
                    } while(current < FILE_SIZE);
                    bos.write(mybytearray, 0 , current);
                    bos.flush();
                    ZipFile file = new ZipFile(FILE_TO_RECEIVED) ; 
                    System.out.println(file.size()+ " zip files are received in  the client"); 
                    output = "Received"+file.size();
                    System.out.println(input+" when output is "+output);
                    pwr.println(output);
                }

                pwr.flush();
            }

        // receive file
        @SuppressWarnings("resource")
        ZipFile file = new ZipFile(FILE_TO_RECEIVED) ; 
        System.out.println(file.size()+ "in client"); 
        System.out.println("File " + FILE_TO_RECEIVED  +" has no of files "+file.size() + " downloaded (" + current + " bytes read)");
    } catch (IOException e) {
        System.out.println("Exception in Input Stream in Client");
        e.printStackTrace();
    }
    finally {
        if (fos != null){
            try {
                fos.close();
                if (bos != null) bos.close();
                if (sock != null) sock.close();
            } catch (IOException e) {
                System.out.println("Exception in closing FileOutputStream or BufferedReader or Socket");
                e.printStackTrace();
            }
        }
    }
}
public static Socket connectToServer(String server2, int socketPort) {
    Socket sock = null;
    try {
        sock = new Socket(server2, socketPort);
    } catch (IOException e) {
        System.out.println("Exception in establishing connection with server");
        e.printStackTrace();
    }
    return sock;
    }
}