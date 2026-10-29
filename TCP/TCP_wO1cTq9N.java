package TCP;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.Socket;

public class TCP_wO1cTq9N {
    public static void main(String[] args) {

        String host = "36.50.135.242";
        int port = 2207;

        String studentCode = "B23DCCN511";
        String qCode = "wO1cTq9N";

        try (Socket socket = new Socket(host, port)) {

            socket.setSoTimeout(5000);

            DataInputStream in = new DataInputStream(
                    socket.getInputStream()
            );

            DataOutputStream out = new DataOutputStream(
                    socket.getOutputStream()
            );

            String request = studentCode + ";" + qCode;

            out.writeUTF(request);
            out.flush();

            System.out.println("Đã gửi: " + request);

            int a = in.readInt();
            int b = in.readInt();

            System.out.println("a = " + a);
            System.out.println("b = " + b);



            int sum = a + b;
            int product = a * b;

            System.out.println("Tổng = " + sum);
            System.out.println("Tích = " + product);

            out.writeInt(sum);
            out.flush();

            out.writeInt(product);
            out.flush();

            System.out.println("Đã gửi kết quả.");


        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
