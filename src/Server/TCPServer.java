package Server;

import Interfaces.MessageReceiver;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class TCPServer extends Thread{

    public final static int port = 5050;

    private Socket socket;
    private BufferedReader br;
    private BufferedWriter bw;
    private MessageReceiver receiver;

    @Override
    public void run() {
        try {
            ServerSocket svSocket = new ServerSocket(port);
            while(true){
                socket = svSocket.accept();
                br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                bw = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));

                String txt = br.readLine();
                bw.write(receiver.onMessage(txt)+"\n");
                bw.flush();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void setReceiver(MessageReceiver receiver){
        this.receiver = receiver;
    }
}
