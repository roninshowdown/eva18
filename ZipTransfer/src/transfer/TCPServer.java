package transfer;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class TCPServer 
{
    public final static int SOCKET_PORT = 13267;  
    public final static String FILE_TO_SEND = "C:/Public/Pictures/Sample Pictures.zip";  
    public static void main (String [] args ) throws IOException {
    FileInputStream fis;
    BufferedInputStream bis = null; 
    BufferedReader br;
    OutputStream os = null;
    ServerSocket servsock = null;
    PrintWriter pw;
    Socket sock = null;

    try {
        servsock = new ServerSocket(SOCKET_PORT);
        while (true) {
            System.out.println("Waiting...");
            try {
                sock = servsock.accept();
                System.out.println("Accepted connection : " + sock);  
                pw = new PrintWriter(sock.getOutputStream(), true);
                pw.println("SendZip");
                br = new BufferedReader(new InputStreamReader(sock.getInputStream()));
                File myFile = new File (FILE_TO_SEND);
                Integer i = (int) (long) myFile.getTotalSpace();
                String input, output;
                while ((input = br.readLine()) != null) {
                     System.out.println("in while loop");
                    if(input.equals("SENDZIP"))
                     {
                         output = "SENTZIP";
                         pw.println(output);
                         System.out.println(input+ " is the input and output is "+output);
                         byte [] mybytearray  = new byte [(int)myFile.length()];
                         fis = new FileInputStream(myFile);
                         bis = new BufferedInputStream(fis);
                         bis.read(mybytearray,0,mybytearray.length);
                         os = sock.getOutputStream();
                         System.out.println("Sending " + FILE_TO_SEND + "(" + mybytearray.length + " bytes)");
                         os.write(mybytearray,0,mybytearray.length);
                         os.flush(); 
                        }
                     }
                    pw.flush();
            System.out.println("Done.");
            }
            finally {
                if (bis != null) bis.close();
                if (os != null) os.close();
                if (sock!=null) sock.close();
            }
        }
    }
    finally {
        if (servsock != null) servsock.close();
        }
    }
 }