package TCP;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
public class TCP_YpK8HqKm {
    public static void main(String[] args) {

        String host = "36.50.135.242";
        int port = 2206;

        String studentCode = "B23DCCN511";
        String qCode = "YpK8HqKm";

        try (Socket socket = new Socket(host, port)) {

            socket.setSoTimeout(5000);

            InputStream in = socket.getInputStream();
            OutputStream out = socket.getOutputStream();

            String request = studentCode + ";" + qCode;
            out.write(request.getBytes(StandardCharsets.UTF_8));
            out.flush();

            byte[] buffer = new byte[4096];
            int length = in.read(buffer);

            if (length == -1) {
                return;
            }

            String data = new String(
                    buffer, 0, length, StandardCharsets.UTF_8
            ).trim();

            System.out.println("Input: " + data);

            String[] parts = data.split(",");

            int[] numbers = new int[parts.length];

            for (int i = 0; i < parts.length; i++) {
                numbers[i] = Integer.parseInt(parts[i].trim());
            }

            int minDistance = Integer.MAX_VALUE;
            int number1 = 0;
            int number2 = 0;

            for (int i = 0; i < numbers.length - 1; i++) {

                for (int j = i + 1; j < numbers.length; j++) {

                    int distance = Math.abs(numbers[i] - numbers[j]);

                    if (distance < minDistance) {
                        minDistance = distance;

                        number1 = numbers[i];
                        number2 = numbers[j];
                    }
                }
            }

            String result = minDistance + "," + number1 + "," + number2;

            System.out.println("Submitted: " + result);

            out.write(result.getBytes(StandardCharsets.UTF_8));
            out.flush();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
