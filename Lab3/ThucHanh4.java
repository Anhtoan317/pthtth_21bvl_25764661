import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class ThucHanh4 {

    public static void main(String[] args) {

        Path input = Path.of("data", "products.csv");
        Path report = Path.of("data", "report.txt");

        List<Product> products = new ArrayList<>();

        // Đọc file CSV
        try (BufferedReader reader = Files.newBufferedReader(
                input, StandardCharsets.UTF_8)) {

            // Bỏ qua dòng tiêu đề
            reader.readLine();

            String line;
            int lineNumber = 1;

            while ((line = reader.readLine()) != null) {

                lineNumber++;

                // Bỏ qua dòng trống
                if (line.isBlank()) {
                    continue;
                }

                // Tách dữ liệu bằng dấu phẩy
                String[] parts = line.split(",", -1);

                // Kiểm tra đủ 4 cột
                if (parts.length != 4) {
                    System.err.println(
                            "Bỏ qua dòng " + lineNumber);
                    continue;
                }

                try {
                    String code = parts[0].trim();
                    String name = parts[1].trim();
                    double unitPrice = Double.parseDouble(parts[2].trim());
                    int quantity = Integer.parseInt(parts[3].trim());

                    products.add(new Product(
                            code,
                            name,
                            unitPrice,
                            quantity));

                } catch (IllegalArgumentException e) {

                    System.err.println(
                            "Dòng " + lineNumber
                                    + " không hợp lệ: "
                                    + e.getMessage());
                }
            }

        } catch (IOException e) {

            System.err.println(
                    "Không đọc được CSV: "
                            + e.getMessage());

            return;
        }

        // Hiển thị danh sách và tính tổng
        double total = 0;

        for (Product product : products) {

            System.out.println(product);

            total += product.inventoryValue();
        }

        // Ghi kết quả vào report.txt
        try (BufferedWriter writer = Files.newBufferedWriter(
                report, StandardCharsets.UTF_8)) {

            writer.write(
                    "Số sản phẩm: " + products.size());
            writer.newLine();

            writer.write(
                    "Tổng giá trị tồn kho: "
                            + "%,.0f VND".formatted(total));
            writer.newLine();

            System.out.println();
            System.out.println(
                    "Đã ghi báo cáo vào: " + report);

        } catch (IOException e) {

            System.err.println(
                    "Không ghi được báo cáo: "
                            + e.getMessage());
        }
    }

    // =========================
    // LỚP PRODUCT
    // =========================

    static class Product {

        private final String code;
        private final String name;
        private final double unitPrice;
        private final int quantity;

        public Product(String code, String name,
                double unitPrice, int quantity) {

            if (code == null || code.isBlank()) {
                throw new IllegalArgumentException(
                        "Mã không được rỗng");
            }

            if (name == null || name.isBlank()) {
                throw new IllegalArgumentException(
                        "Tên không được rỗng");
            }

            if (unitPrice <= 0 || quantity < 0) {
                throw new IllegalArgumentException(
                        "Giá hoặc số lượng không hợp lệ");
            }

            this.code = code;
            this.name = name;
            this.unitPrice = unitPrice;
            this.quantity = quantity;
        }

        public double inventoryValue() {
            return unitPrice * quantity;
        }

        @Override
        public String toString() {
            return "%s - %s: %,.0f VND".formatted(
                    code,
                    name,
                    inventoryValue());
        }
    }
}