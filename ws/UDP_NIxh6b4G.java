package ws;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class UDP_NIxh6b4G {

    public static void main(String[] args) {
        String host = "36.50.135.242";
        int port = 2208;

        String studentCode = "B23DCCN511";
        String qCode = "NIxh6b4G";

        try(DatagramSocket socket = new DatagramSocket()){
            socket.setSoTimeout(5000);

            InetAddress address =  InetAddress.getByName(host);

            String request =";" + studentCode + ";" + qCode;

            byte[] sendData = request.getBytes(StandardCharsets.UTF_8);
            DatagramPacket sendPacket = new DatagramPacket(
                    sendData,
                    sendData.length,
                    address,
                    port
            );

            socket.send(sendPacket);

            System.out.println("Đã gửi: " + request);

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

            System.out.println("Server gửi: " + data);


            String[] parts = data.split(";");
            String reqId = parts[0];
            String text = parts[1];
            HashMap<Character, Integer> countMap = new HashMap<>();
            int maxCount = 0;
            char maxChar = ' ';
            for(char x : text.toCharArray()){
                int currentCount = countMap.getOrDefault(x, 0) + 1;
                countMap.put(x, currentCount);

                if(currentCount > maxCount){
                    maxCount = currentCount;
                    maxChar = x;
                }

            }
            List<Integer> position =  new ArrayList<>();

            for(int i = 0 ; i < text.length(); i++){
                if (text.charAt(i) == maxChar)
                    position.add(i + 1);
            }
            String response = reqId + ";" +maxChar+":";
            for(Integer x : position)
                response+= x + ",";

            System.out.println(response);

            byte[] responseData =  response.getBytes(StandardCharsets.UTF_8);

            DatagramPacket datagramPacket = new DatagramPacket(
                    responseData,
                    responseData.length,
                    receivePacket.getAddress(),
                    receivePacket.getPort()
            );
            socket.send(datagramPacket);

        } catch (Exception e){
            e.printStackTrace();
        }
    }
}
