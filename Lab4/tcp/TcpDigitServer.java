package tcp;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.charset.StandardCharsets;

public class TcpDigitServer {

    private static final int PORT = 5001;

    public static void main(String[] args) {

        try (ServerSocket server = new ServerSocket(PORT)) {

            System.out.println("Server dang chay tai port " + PORT);

            while (true) {

                try (Socket socket = server.accept()) {

                    System.out.println("Client da ket noi!");

                    BufferedReader in = new BufferedReader(
                            new InputStreamReader(
                                    socket.getInputStream(),
                                    StandardCharsets.UTF_8));

                    PrintWriter out = new PrintWriter(
                            new OutputStreamWriter(
                                    socket.getOutputStream(),
                                    StandardCharsets.UTF_8),
                            true);

                    String request;

                    while ((request = in.readLine()) != null) {

                        System.out.println("Client gui: " + request);

                        if (request.equalsIgnoreCase("QUIT")) {
                            out.println("OK BYE");
                            break;
                        }

                        String response = convertDigit(request);

                        out.println(response);
                    }

                    System.out.println("Client da ngat ket noi!");

                } catch (IOException e) {
                    System.out.println("Loi client: " + e.getMessage());
                }
            }

        } catch (IOException e) {
            System.out.println("Loi server: " + e.getMessage());
        }
    }

    // Chuyen chu so thanh chu tieng Viet
    public static String convertDigit(String s) {

        // Kiem tra phai dung 1 chu so tu 0 den 9
        if (s.length() != 1 ||
                s.charAt(0) < '0' ||
                s.charAt(0) > '9') {

            return "ERR INVALID_DIGIT";
        }

        String[] words = {
                "không",
                "một",
                "hai",
                "ba",
                "bốn",
                "năm",
                "sáu",
                "bảy",
                "tám",
                "chín"
        };

        int digit = s.charAt(0) - '0';

        return "OK " + words[digit];
    }
}