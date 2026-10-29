package TCP;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.util.LinkedHashMap;
import java.util.Map;

public class TCP_YWngFFMe {
    public static void main(String[] args) {

        String host = "36.50.135.242";
        int port = 2208;

        String studentCode = "B23DCCN511";
        String qCode = "YWngFFMe";

        try (Socket socket = new Socket(host, port)) {

            socket.setSoTimeout(5000);

            BufferedReader reader = new BufferedReader(
                    new InputStreamReader(socket.getInputStream())
            );

            BufferedWriter writer = new BufferedWriter(
                    new OutputStreamWriter(socket.getOutputStream())
            );

            String request = studentCode + ";" + qCode;

            writer.write(request);
            writer.newLine();
            writer.flush();

            System.out.println("Đã gửi: " + request);

            String data = reader.readLine();

            System.out.println("Input: " + data);

            Map<Character, Integer> count = new LinkedHashMap<>();

            for (char c : data.toCharArray()) {

                if (Character.isLetterOrDigit(c)) {
                    count.put(c, count.getOrDefault(c, 0) + 1);
                }
            }


            StringBuilder result = new StringBuilder();

            for (Map.Entry<Character, Integer> entry : count.entrySet()) {

                if (entry.getValue() > 1) {

                    result.append(entry.getKey())
                            .append(":")
                            .append(entry.getValue())
                            .append(",");
                }
            }

            System.out.println("Submitted: " + result);

            writer.write(result.toString());
            writer.newLine();
            writer.flush();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
