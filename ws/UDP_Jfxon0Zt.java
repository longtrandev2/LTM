package ws;
import TH1.D;

import javax.xml.crypto.Data;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

public class UDP_Jfxon0Zt {

    public static void main(String[] args) {
        String host = "36.50.135.242";
        int port = 2207;

        String studentCode = "B23DCCN511";
        String qCode = "Jfxon0Zt";

        try(DatagramSocket socket = new DatagramSocket()){
            socket.setSoTimeout(5000);

            InetAddress address = InetAddress.getByName(host);

            String reqeust = ";" +studentCode +";" +qCode;

            byte[] sendData = reqeust.getBytes(StandardCharsets.UTF_8);

            DatagramPacket sendPacket = new DatagramPacket(
                    sendData,
                    sendData.length,
                    address,
                    port
            );

            socket.send(sendPacket);

            System.out.println("Đã gửi " + reqeust);

            byte[] receiveBuffer = new byte[4096];

            DatagramPacket receivePacket = new DatagramPacket(
                    receiveBuffer,
                    receiveBuffer.length
            );
            socket.receive(receivePacket);
            String data = new String(
                    receivePacket.getData(),
                    0,
                    receivePacket.getLength(),
                    StandardCharsets.UTF_8
            ).trim();
            System.out.println("Sever gửi" + data);

            String[] parts =  data.split(";");
            String requestId =parts[0];
            int n = Integer.parseInt(parts[1]);
            boolean[] exits = new boolean[n + 1];
            String[] numbers = parts[2].split(",");
            for (String number: numbers){
                int value = Integer.parseInt(number);
                exits[value] = true;

            }
            ArrayList<Integer> missing = new ArrayList<>();
            for(int i = 1; i <= n; i++ )
                if(!exits[i])
                    missing.add(i);
            StringBuilder res = new StringBuilder();

            res.append(requestId).append(";");
            for(int i = 0 ; i < missing.size(); i++){
                if(i > 0){
                    res.append(",");
                }
                res.append(missing.get(i));
            }
            String response = res.toString();
            System.out.println("ket qua" + response);

            byte[] responseData = response.getBytes(StandardCharsets.UTF_8);

            DatagramPacket responsePacket = new DatagramPacket(
                    responseData,
                    responseData.length,
                    address,
                    port
            );
            socket.send(responsePacket);
        } catch (Exception e){
            e.printStackTrace();
        }
    }
}