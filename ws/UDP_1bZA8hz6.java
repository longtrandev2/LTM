package ws;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Collections;

public class UDP_1bZA8hz6 {
    public static void main(String[] args) {
        String host = "36.50.135.242";
        int port = 2207;
        String studentCode = "B23DCCN511";
        String qCode =  "1bZA8hz6";

        try(DatagramSocket socket = new DatagramSocket()){
            socket.setSoTimeout(5000);
            InetAddress address = InetAddress.getByName(host);
            String request = ";" + studentCode + ";" +qCode;

            byte[] sendData = request.getBytes(StandardCharsets.UTF_8);

            DatagramPacket sendPacket = new DatagramPacket(
                    sendData,
                    sendData.length,
                    address,
                    port
            );

            socket.send(sendPacket);

            System.out.println("Đã gửi " + request);

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
            System.out.println(data);
            String[] parts = data.split(";");
            String requestId = parts[0];
            String[] numbers = parts[1].split(",");
            ArrayList<Integer> numbersArray = new ArrayList<>();
            for(String number:numbers)
                numbersArray.add(Integer.parseInt(number));
            String res = requestId +";";
            res+= Collections.max(numbersArray) + "," +Collections.min(numbersArray);
            System.out.println(res);
            byte[] responseData = res.getBytes(StandardCharsets.UTF_8);
            DatagramPacket responsePacket = new DatagramPacket(
                    responseData,
                    responseData.length,
                    address,
                    port
            );
            socket.send(responsePacket);
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
