package TCP;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.nio.charset.StandardCharsets;

public class TCP_3QAYzGIT {
    public static void main(String[] args) {

        String host = "36.50.135.242";
        int port = 2206;

        String studentCode = "B23DCCN511";
        String qCode = "3QAYzGIT";

        try (Socket socket = new Socket(host, port)) {

            socket.setSoTimeout(5000);

            InputStream in = socket.getInputStream();
            OutputStream out = socket.getOutputStream();

            String request = studentCode + ";" + qCode;

            out.write(request.getBytes(StandardCharsets.UTF_8));
            out.flush();

            System.out.println("Đã gửi: " + request);

            byte[] buffer = new byte[4096];

            int length = in.read(buffer);

            if (length == -1) {
                System.out.println("Server đã đóng kết nối.");
                return;
            }

            String data = new String(
                    buffer,
                    0,
                    length,
                    StandardCharsets.UTF_8
            ).trim();

            System.out.println("Server gửi: " + data);

            String[] parts = data.split(",");

            int[] numbers = new int[parts.length];

            for (int i = 0; i < parts.length; i++) {
                numbers[i] = Integer.parseInt(parts[i].trim());
            }


            int max1 = Integer.MIN_VALUE;
            int max2 = Integer.MIN_VALUE;

            for (int number : numbers) {

                if (number > max1) {
                    max2 = max1;
                    max1 = number;

                } else if (number > max2 && number < max1) {
                    max2 = number;
                }
            }


            int position = -1;

            for (int i = 0; i < numbers.length; i++) {

                if (numbers[i] == max2) {
                    position = i;
                    break;
                }
            }

            String result = max2 + "," + position;

            System.out.println("Kết quả: " + result);

            out.write(result.getBytes(StandardCharsets.UTF_8));
            out.flush();

            System.out.println("Đã gửi kết quả.");


        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
