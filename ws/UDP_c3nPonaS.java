package ws;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.nio.charset.StandardCharsets;

public class UDP_c3nPonaS {

    public static String chuanHoa(String word){
        return word.substring(0,1).toUpperCase() +word.substring(1).toLowerCase();
    }
    public static void main(String[] args) {
        String host =  "36.50.135.242";
        int port = 2208;
        String studentCode = "B23DCCN511";
        String qCode = "c3nPonaS";

        try(DatagramSocket socket = new DatagramSocket()){
            socket.setSoTimeout(5000);
            InetAddress address = InetAddress.getByName(host);
            String request = ";" +studentCode + ";" +qCode;
            byte[] sendData = request.getBytes(StandardCharsets.UTF_8);

            DatagramPacket sendPacket = new DatagramPacket(
                    sendData,
                    sendData.length,
                    address,
                    port
            );
            socket.send(sendPacket);
            System.out.println("Đã gửi " + request);

            byte[] receiveData = new byte[4096];
            DatagramPacket receivePacket = new DatagramPacket(
                    receiveData,
                    receiveData.length
            );
            socket.receive(receivePacket);
            String data = new String(
                    receivePacket.getData(),
                    0,
                    receivePacket.getLength(),
                    StandardCharsets.UTF_8
            );
            System.out.println("Đã nhận " + data);
            String[] parts = data.split(";");
            String requestId =  parts[0];
            String[] words = parts[1].split(" ");
            for(int i = 0 ; i < words.length ; i++){

                words[i] = chuanHoa(words[i]);
                System.out.println(words[i]);
            }
            StringBuilder result = new StringBuilder(requestId + ";");
            for(int i = 0 ; i< words.length -1;i++){
               result.append(words[i] +" ");
            }
            result.append(words[words.length -1]);
            System.out.println(result);
            byte[] responseData =  result.toString().getBytes(StandardCharsets.UTF_8);
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
