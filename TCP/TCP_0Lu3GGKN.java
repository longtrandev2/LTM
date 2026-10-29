package TCP;
import java.io.*;
import java.net.InetSocketAddress;
import java.net.Socket;

public class TCP_0Lu3GGKN {
    public static void main(String[] args) {
        String host = "36.50.135.242";
        int port = 2208;

        String studentCode = "B23DCCN511";
        String qCode = "0Lu3GGKN";

        try (Socket socket = new Socket()){
            socket.connect(new InetSocketAddress(host,port), 5000);
            socket.setSoTimeout(5000);
            BufferedReader reader = new BufferedReader(
                    new InputStreamReader(socket.getInputStream()));
            BufferedWriter writer = new BufferedWriter(
                    new OutputStreamWriter(socket.getOutputStream()));
            String request = studentCode + ";" + qCode;
            writer.write(request);
            writer.newLine();
            writer.flush();

            String data = reader.readLine();
            System.out.println(data);

            StringBuilder result = new StringBuilder();

            String[] domains = data.split(",");

            for (String domain : domains) {
                domain = domain.trim();

                if (domain.endsWith(".edu")) {
                    if (result.length() > 0) {
                        result.append(", ");
                    }
                    result.append(domain);
                }
            }

            writer.write(result.toString());
            writer.newLine();
            writer.flush();

            System.out.println(result);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}