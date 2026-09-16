package tcp;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;
import java.nio.charset.StandardCharsets;

public class TcpCalcClient {

    public static void main(String[] args) {

        try (
                Socket socket = new Socket("localhost", 5002);

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
            System.out.println("Nhap: CALC toan_tu so1 so2");
            System.out.println("Vi du: CALC + 100 200");
            System.out.println("Nhap QUIT de thoat.");

            String request;

            while ((request = console.readLine()) != null) {

                // Gui yeu cau den Server
                out.println(request);

                // Nhan phan hoi
                String response = in.readLine();

                if (response == null) {
                    System.out.println("Server da dong ket noi!");
                    break;
                }

                System.out.println("Server: " + response);

                // Ket thuc khi nhap QUIT
                if (request.equalsIgnoreCase("QUIT")) {
                    break;
                }
            }

        } catch (IOException e) {
            System.out.println("Loi ket noi: " + e.getMessage());
        }
    }
}