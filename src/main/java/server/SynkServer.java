package server;

import java.io.DataInputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class SynkServer {
    public static final int PORT = 5555;

    private ServerSocket socket;

    public SynkServer(){
        Runnable r = new Runnable() {
            @Override
            public void run() {
                runWork();
            }
        };
        Thread t = new Thread(r);
        t.setName("SynkServer");
        t.start();
    }
    private void runWork(){
        try {
            socket = new ServerSocket(PORT);
            System.out.println("SynkServer Running");
        }catch (IOException io){
            io.printStackTrace();
        }
        while (true){
            try {
                Socket connectionSocket = socket.accept();
                DataInputStream inFromClient = new DataInputStream(connectionSocket.getInputStream());
                String sentence = inFromClient.readUTF();
                System.out.println("received " + sentence);
                if(sentence.equals("-shutdown")){
                    System.out.println("Shutting down");
                    return;
                }
            }catch (IOException io){
                io.printStackTrace();
            }
        }

    }
}
