package UDP;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.nio.charset.StandardCharsets;

public class UDP_K2141aZx {
    public static void main(String[] args) {
        String host = "36.50.135.242";
        int port =  2209;
        String studentCode = "B23DCCN511";
        String qCode = "K2141aZx";

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
            System.out.println("Da gui " + request);

            byte[] bufferData = new byte[4096];
            DatagramPacket receiveData = new DatagramPacket(
                    bufferData,
                    bufferData.length
            );
            socket.receive(receiveData);
            String requestId = new String(
                    receiveData.getData(),
                    0,
                    8,
                    StandardCharsets.UTF_8
            );
            ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(
                    receiveData.getData(),
                    8,
                    receiveData.getLength() -8
            );
            ObjectInputStream obs = new ObjectInputStream(byteArrayInputStream);
            Customer customer = (Customer)obs.readObject();
            System.out.println("Customer " +customer.name);

            String[] words = customer.name.trim().split(" ");
            String[] date = customer.dayOfBirth.trim().split("-");
            String nmName = words[words.length-1].toUpperCase() + ",";
            String nmUsername = "";
            String nmDate = "";
            for(int i = 0 ; i < words.length -1 ; i++){
                    nmName += " ";
                nmName += words[i].substring(0,1).toUpperCase() + words[i].substring(1).toLowerCase();
                nmUsername += words[i].substring(0,1).toLowerCase();
            }
            nmUsername += words[words.length-1].toLowerCase();

            String mm = date[0];
            String dd = date[1];
            String yyyy = date[2];
            nmDate +=dd +"/" +mm +"/"+yyyy;
            customer.name = nmName;
            customer.dayOfBirth = nmDate;
            customer.userName = nmUsername;
            System.out.println(nmName);
            System.out.println(nmUsername);
            System.out.println(nmDate);

            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            baos.write(requestId.getBytes(StandardCharsets.UTF_8));
            ObjectOutputStream oos = new ObjectOutputStream(baos);
            oos.writeObject(customer);
            oos.flush();

            byte[] responseData = baos.toByteArray();
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
