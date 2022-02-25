package Client;

import java.io.*;
import java.net.Socket;
import java.util.Scanner;

public class Client {

    private final static String ip = "localhost";
    private final static int port = 5050;

    private Socket socket;
    private BufferedWriter bw;
    private BufferedReader br;
    private boolean before;

    public Client(){
        before = false;
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        Client main = new Client();
        while(true){
            String txt = sc.nextLine();
            main.sendMessage(txt);
        }
    }

    public void sendMessage(String txt){
        try {
            socket = new Socket(ip,port);
            bw = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
            br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            bw.write(txt+"\n");
            bw.flush();
            onMessage(br.readLine());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void onMessage(String txt){
        if(txt.equals("RTT")){
            long init = System.nanoTime();
            byte[] message = new byte[1024];
            String toSend = new String(message);
            before = true;
            sendMessage(toSend);
            long end = System.nanoTime();
            System.out.println("Tiempo RTT: "+(end-init) + " nano-segundos");
        } else if(txt.equals("speed")){
            long init = System.nanoTime();
            byte[] message = new byte[8192];
            String toSend = new String(message);
            before = true;
            sendMessage(toSend);
            long end = System.nanoTime();
            System.out.println("speed: "+(8/(((end-init))*0.000000001)) + " KB/s");
        } else{
            if(!before){
                System.out.println(txt);
            }
            before = false;
        }
    }

}
