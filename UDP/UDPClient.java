package UDP;

import UDP.Student; // Import class Student từ package UDP

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.nio.charset.StandardCharsets;

public class UDPClient {
    public static void main(String[] args) {
        String host = "36.50.135.242";
        int port = 2209;
        String studentCode = "B23DCCN511";
        String qCode = "ZWxbS8fa";

        try (DatagramSocket socket = new DatagramSocket()) {
            socket.setSoTimeout(5000);
            InetAddress address = InetAddress.getByName(host);

            String request = ";" + studentCode + ";" + qCode;
            byte[] sendData = request.getBytes(StandardCharsets.UTF_8);

            DatagramPacket sendPacket = new DatagramPacket(
                    sendData, sendData.length, address, port
            );
            socket.send(sendPacket);
            System.out.println("Đã gửi request: " + request);

            byte[] receiveBuffer = new byte[4096];
            DatagramPacket receivePacket = new DatagramPacket(receiveBuffer, receiveBuffer.length);
            socket.receive(receivePacket);

            String requestId = new String(receivePacket.getData(), 0, 8, StandardCharsets.UTF_8);

            ByteArrayInputStream bais = new ByteArrayInputStream(
                    receivePacket.getData(),
                    8,
                    receivePacket.getLength() - 8
            );
            ObjectInputStream ois = new ObjectInputStream(bais);
            Student student = (Student) ois.readObject();

            System.out.println("Nhận được sinh viên: " + student.name);

            String[] words = student.name.trim().toLowerCase().split("\\s+");
            StringBuilder normalizedName = new StringBuilder();

            String mainName = words[words.length - 1];
            StringBuilder emailPrefix = new StringBuilder(mainName);

            for (int i = 0; i < words.length; i++) {
                String word = words[i];
                normalizedName.append(word.substring(0, 1).toUpperCase())
                        .append(word.substring(1));

                if (i < words.length - 1) {
                    normalizedName.append(" ");
                    emailPrefix.append(word.charAt(0));
                }
            }

            // Gán lại dữ liệu cho đối tượng
            student.name = normalizedName.toString();
            student.email = emailPrefix.toString() + "@ptit.edu.vn";

            System.out.println("Tên sau chuẩn hóa: " + student.name);
            System.out.println("Email tạo ra: " + student.email);

            ByteArrayOutputStream baos = new ByteArrayOutputStream();

            baos.write(requestId.getBytes(StandardCharsets.UTF_8));

            ObjectOutputStream oos = new ObjectOutputStream(baos);
            oos.writeObject(student);
            oos.flush();

            byte[] responseData = baos.toByteArray();
            DatagramPacket responsePacket = new DatagramPacket(
                    responseData,
                    responseData.length,
                    receivePacket.getAddress(),
                    receivePacket.getPort()
            );
            socket.send(responsePacket);

            System.out.println("Đã gửi kết quả thành công.");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}