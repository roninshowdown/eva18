package transfer;

import java.io.*;
import java.net.*;

class TCPServer {

    private final static String fileToSend = "/home/donald/git/eva18/ZipTransfer/AntragGEZ.pdf";

    public static void main(String args[]) {

        while (true) {
            ServerSocket welcomeSocket = null;
            Socket connectionSocket = null;
            BufferedOutputStream outToClient = null;

            try {
                welcomeSocket = new ServerSocket(3248);
                connectionSocket = welcomeSocket.accept();
                outToClient = new BufferedOutputStream(connectionSocket.getOutputStream());
            } catch (IOException ex) {
            	ex.printStackTrace();
            }

            if (outToClient != null) {
                File myFile = new File( fileToSend );
                byte[] mybytearray = new byte[(int) myFile.length()];
                System.out.println("Filelength: "+myFile.length());
                FileInputStream fis = null;

                try {
                    fis = new FileInputStream(myFile);
                } catch (FileNotFoundException ex) {
                	ex.printStackTrace();
                }
                BufferedInputStream bis = new BufferedInputStream(fis);

                try {
                    bis.read(mybytearray, 0, mybytearray.length);
                    outToClient.write(mybytearray, 0, mybytearray.length);
                    outToClient.flush();
                    outToClient.close();
                    connectionSocket.close();

                    // File sent, exit the main method
                    return;
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
        }
    }
}