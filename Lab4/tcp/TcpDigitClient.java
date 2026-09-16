package tcp;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;
import java.nio.charset.StandardCharsets;

public class TcpDigitClient {

    public static void main(String[] args) {

        try (
                Socket socket = new Socket("localhost", 5001);

                BufferedReader console = new BufferedReader(
                        new InputStreamReader(
                                System.in,
                                StandardCharsets.UTF_8));

                BufferedReader in = new BufferedReader(
                        new InputStreamReader(
                                socket.getInputStream(),
                                StandardCharsets.UTF_8));

                PrintWriter out = new PrintWriter(
                        new OutputStreamWriter(
                                socket.getOutputStream(),
                                StandardCharsets.UTF_8),
                        true)) {

            System.out.println("Da ket noi den Server!");
            System.out.println("Nhap chu so 0-9 hoac QUIT de thoat:");

            String request;

            while ((request = console.readLine()) != null) {

                // Gui du lieu den Server
                out.println(request);

                // Nhan ket qua tu Server
                String response = in.readLine();

                if (response == null) {
                    System.out.println("Server da dong ket noi!");
                    break;
                }

                System.out.println("Server: " + response);

                if (request.equalsIgnoreCase("QUIT")) {
                    break;
                }
            }

        } catch (IOException e) {
            System.out.println("Loi ket noi: " + e.getMessage());
        }
    }
}