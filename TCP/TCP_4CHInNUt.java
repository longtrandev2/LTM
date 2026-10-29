package TCP;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.Socket;
public class TCP_4CHInNUt {
    public static void main(String[] args) {
        // Thay đổi IP theo đề bài (nếu cần)
        String host = "36.50.135.242";
        int port = 2207;

        String studentCode = "B23DCCN511";
        String qCode = "4CHInNUt";

        try (Socket socket = new Socket(host, port)) {
            socket.setSoTimeout(5000);

            DataInputStream in = new DataInputStream(socket.getInputStream());
            DataOutputStream out = new DataOutputStream(socket.getOutputStream());

            String request = studentCode + ";" + qCode;
            out.writeUTF(request);
            out.flush();
            System.out.println("Đã gửi: " + request);

            String encryptedStr = in.readUTF();
            int s = in.readInt();
            System.out.println("Chuỗi nhận được: " + encryptedStr);
            System.out.println("Độ dịch (s): " + s);

            // c. Thực hiện giải mã
            StringBuilder decryptedStr = new StringBuilder();

            for (char c : encryptedStr.toCharArray()) {
                if (Character.isUpperCase(c)) {
                    char shifted = (char) (((c - 'A' - s) % 26 + 26) % 26 + 'A');
                    decryptedStr.append(shifted);
                } else if (Character.isLowerCase(c)) {
                    char shifted = (char) (((c - 'a' - s) % 26 + 26) % 26 + 'a');
                    decryptedStr.append(shifted);
                } else {
                    decryptedStr.append(c);
                }
            }
            System.out.println("Chuỗi sau khi giải mã: " + decryptedStr.toString());

            out.writeUTF(decryptedStr.toString());
            out.flush();
            System.out.println("Đã gửi kết quả giải mã thành công!");


        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
