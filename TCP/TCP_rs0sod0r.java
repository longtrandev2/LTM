package TCP;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class TCP_rs0sod0r {
    public static void main(String[] args) {
        String host = "36.50.135.242";
        int port = 2209;

        String studentCode = "B23DCCN511";
        String qCode = "rs0sod0r";

        try (Socket socket = new Socket(host, port)) {
            socket.setSoTimeout(5000);

            ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
            ObjectInputStream in = new ObjectInputStream(socket.getInputStream());

            String request = studentCode + ";" + qCode;
            out.writeObject(request);
            out.flush();
            System.out.println("Đã gửi request: " + request);

            Laptop laptop = (Laptop) in.readObject();
            System.out.println("Dữ liệu nhận được:");
            System.out.println(" - Name: " + laptop.getName());
            System.out.println(" - Quantity: " + laptop.getQuantity());

            String[] words = laptop.getName().split("\\s+");
            if (words.length > 1) {
                String temp = words[0];
                words[0] = words[words.length - 1];
                words[words.length - 1] = temp;

                laptop.setName(String.join(" ", words));
            }

            String qtyStr = String.valueOf(laptop.getQuantity());
            String reversedQtyStr = new StringBuilder(qtyStr).reverse().toString();
            laptop.setQuantity(Integer.parseInt(reversedQtyStr));

            System.out.println("Dữ liệu sau khi sửa:");
            System.out.println(" - Name: " + laptop.getName());
            System.out.println(" - Quantity: " + laptop.getQuantity());

            out.writeObject(laptop);
            out.flush();
            System.out.println("Đã gửi kết quả thành công!");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}