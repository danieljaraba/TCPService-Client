package Server;

import Interfaces.MessageReceiver;

import java.net.InterfaceAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Enumeration;
import java.util.List;

public class MessageAnalyzer implements MessageReceiver {

    private static MessageAnalyzer instance;
    private String status;

    private MessageAnalyzer(){
        status = "";
    }

    public static MessageAnalyzer getInstance(){
        if(instance == null){
            instance = new MessageAnalyzer();
        }
        return instance;
    }


    @Override
    public synchronized String onMessage(String txt) {
        System.out.println(txt);
        String message = "";
        if(txt.equals("RTT")){
            status = txt;
            message = txt;
        }else if(status.equals("RTT")){
            message = txt;
            status = "";
        }else if(txt.equals("speed")){
            status = txt;
            message = txt;
        }else if(status.equals("speed")){
            message = txt;
            status = "";
        }else if(txt.equals("interface")){
            try {
                Enumeration<NetworkInterface> interfaces = NetworkInterface.getNetworkInterfaces();
                while(interfaces.hasMoreElements()){
                    NetworkInterface netN = interfaces.nextElement();
                    if(netN.isUp()){
                        if(netN.getHardwareAddress() != null){
                            message = "Interface: "+netN.getName();
                        }
                    }
                }
            } catch (SocketException e) {
                e.printStackTrace();
            }
        } else if(txt.equals("remoteIpconfig")){
            try {
                Enumeration<NetworkInterface> interfaces = NetworkInterface.getNetworkInterfaces();
                while(interfaces.hasMoreElements()){
                    NetworkInterface netN = interfaces.nextElement();
                    if(netN.isUp()){
                        if(netN.getHardwareAddress() != null){
                            List<InterfaceAddress> list = netN.getInterfaceAddresses();
                            message = list.get(0).toString();
                        }
                    }
                }
            } catch (SocketException e) {
                e.printStackTrace();
            }
        } else if(txt.equals("whatTimeIsIt")){
            DateFormat format = new SimpleDateFormat("HH:mm:ss");
            Date date = new Date();
            message = format.format(date);
        }
        return message;
    }
}
