import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;

public class ThucHanh3 {

    private static final int BUFFER_SIZE = 8192;

    public static void main(String[] args) {

        // Kiểm tra tham số dòng lệnh
        if (args.length != 2) {
            System.out.println(
                    "Cách dùng: java BinaryFileCopy <nguồn> <đích>");
            return;
        }

        // Lấy đường dẫn file nguồn và file đích
        Path source = Path.of(args[0]);
        Path target = Path.of(args[1]);

        long totalBytes = 0;

        try (
                InputStream input = new BufferedInputStream(
                        Files.newInputStream(source));

                OutputStream output = new BufferedOutputStream(
                        Files.newOutputStream(target))) {

            // Tạo bộ đệm 8192 byte
            byte[] buffer = new byte[BUFFER_SIZE];

            int bytesRead;

            // Đọc dữ liệu từ file nguồn
            while ((bytesRead = input.read(buffer)) != -1) {

                // Chỉ ghi số byte thực sự vừa đọc
                output.write(buffer, 0, bytesRead);

                totalBytes += bytesRead;
            }

            System.out.println(
                    "Đã sao chép " + totalBytes + " byte.");

        } catch (IOException e) {

            System.err.println(
                    "Sao chép thất bại: " + e.getMessage());
        }
    }
}