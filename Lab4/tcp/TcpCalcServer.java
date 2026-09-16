package tcp;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.math.BigDecimal;
import java.math.MathContext;

public class TcpCalcServer {

    private static final int PORT = 5002;

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

                        System.out.println("Client: " + request);

                        if (request.equalsIgnoreCase("QUIT")) {
                            out.println("OK BYE");
                            break;
                        }

                        String response = calculate(request);

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

    // Ham xu ly phep tinh
    public static String calculate(String request) {

        // Tach lenh thanh cac thanh phan
        String[] parts = request.trim().split("\\s+");

        // Kiem tra dinh dang
        if (parts.length != 4 ||
                !parts[0].equalsIgnoreCase("CALC")) {

            return "ERR INVALID_FORMAT";
        }

        String operator = parts[1];

        // Kiem tra toan tu
        if (!operator.equals("+") &&
                !operator.equals("-") &&
                !operator.equals("*") &&
                !operator.equals("/")) {

            return "ERR UNSUPPORTED_OPERATOR";
        }

        try {

            // Chuyen chuoi thanh so
            BigDecimal a = new BigDecimal(parts[2]);
            BigDecimal b = new BigDecimal(parts[3]);

            BigDecimal result;

            switch (operator) {

                case "+":
                    result = a.add(b);
                    break;

                case "-":
                    result = a.subtract(b);
                    break;

                case "*":
                    result = a.multiply(b);
                    break;

                case "/":

                    // Kiem tra chia cho 0
                    if (b.compareTo(BigDecimal.ZERO) == 0) {
                        return "ERR DIVIDE_BY_ZERO";
                    }

                    result = a.divide(b, MathContext.DECIMAL64);
                    break;

                default:
                    return "ERR UNSUPPORTED_OPERATOR";
            }

            return "OK " +
                    result.stripTrailingZeros().toPlainString();

        } catch (NumberFormatException e) {

            return "ERR INVALID_NUMBER";
        }
    }
}