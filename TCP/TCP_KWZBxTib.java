package TCP;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class TCP_KWZBxTib {
    public static void main(String[] args) {
        String host = "36.50.135.242";
        int port = 2209;

        String studentCode = "B23DCCN511";
        String qCode = "KWZBxTib";

        try (Socket socket = new Socket(host, port)) {
            socket.setSoTimeout(5000);

            ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
            ObjectInputStream in = new ObjectInputStream(socket.getInputStream());

            String request = studentCode + ";" + qCode;
            out.writeObject(request);
            out.flush();
            System.out.println("Đã gửi request: " + request);

            Customer customer = (Customer) in.readObject();
            System.out.println("--- Dữ liệu gốc ---");
            System.out.println("Name: " + customer.getName());
            System.out.println("DOB: " + customer.getDayOfBirth());

            String originalName = customer.getName().trim().toLowerCase();
            String originalDob = customer.getDayOfBirth().trim();

            String[] nameWords = originalName.split("\\s+");

            StringBuilder formattedNameParts = new StringBuilder();
            StringBuilder usernameBuilder = new StringBuilder();

            String lastWord = nameWords[nameWords.length - 1];

            for (int i = 0; i < nameWords.length - 1; i++) {
                String word = nameWords[i];
                usernameBuilder.append(word.charAt(0));

                String capitalizedWord = Character.toUpperCase(word.charAt(0)) + word.substring(1);
                formattedNameParts.append(capitalizedWord).append(" ");
            }

            String newUserName = usernameBuilder.toString() + lastWord;

            String newName = lastWord.toUpperCase() + ", " + formattedNameParts.toString().trim();

            String[] dobParts = originalDob.split("-");
            String newDob = dobParts[1] + "/" + dobParts[0] + "/" + dobParts[2];

            customer.setName(newName);
            customer.setUserName(newUserName);
            customer.setDayOfBirth(newDob);

            System.out.println("\n--- Dữ liệu sau khi chuẩn hóa ---");
            System.out.println("Name: " + customer.getName());
            System.out.println("Username: " + customer.getUserName());
            System.out.println("DOB: " + customer.getDayOfBirth());

            out.writeObject(customer);
            out.flush();
            System.out.println("\nĐã gửi đối tượng thành công!");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}