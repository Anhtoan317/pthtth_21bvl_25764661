package network;

import java.net.InetAddress;
import java.net.Inet4Address;
import java.net.Inet6Address;
import java.net.UnknownHostException;
import java.net.URI;
import java.net.URISyntaxException;

public class HostURIInspector {

    public static void main(String[] args) {

        // 1. Kiểm tra số lượng tham số
        if (args.length != 2) {
            System.out.println("Loi: Can nhap hostname va URI");
            System.out.println(
                    "Usage: java network.HostURIInspector <hostname> <URI>");
            return;
        }

        String hostname = args[0];
        String uriText = args[1];

        // 2. Kiểm tra hostname rỗng
        if (hostname.trim().isEmpty()) {
            System.out.println("Loi: Hostname khong duoc rong");
            return;
        }

        try {

            // 3. Phân tích URI
            URI uri = new URI(uriText);

            // Kiểm tra URI có scheme và host
            if (uri.getScheme() == null || uri.getHost() == null) {
                System.out.println("Loi: URI khong hop le");
                return;
            }

            // 4. Phân giải hostname thành các địa chỉ IP
            InetAddress[] addresses = InetAddress.getAllByName(hostname);

            System.out.println("===== HOST INSPECTOR =====");

            System.out.println("Hostname: " + hostname);

            for (InetAddress address : addresses) {

                System.out.println("----------------------");

                System.out.println(
                        "IP: " + address.getHostAddress());

                // Xác định IPv4 hoặc IPv6
                if (address instanceof Inet4Address) {
                    System.out.println("Loai IP: IPv4");

                } else if (address instanceof Inet6Address) {
                    System.out.println("Loai IP: IPv6");

                } else {
                    System.out.println("Loai IP: Khong xac dinh");
                }

                System.out.println(
                        "Loopback: " + address.isLoopbackAddress());

                System.out.println(
                        "Site Local: " + address.isSiteLocalAddress());
            }

            // 5. In các thành phần của URI
            System.out.println();
            System.out.println("===== URI INSPECTOR =====");

            System.out.println("URI: " + uri);

            System.out.println("Scheme: " + uri.getScheme());

            System.out.println("Host: " + uri.getHost());

            System.out.println("Port: " + uri.getPort());

            System.out.println("Path: " + uri.getPath());

            System.out.println("Query: " + uri.getQuery());

            System.out.println("Fragment: " + uri.getFragment());

        } catch (URISyntaxException e) {

            System.out.println(
                    "Loi: URI sai cu phap - " + e.getMessage());

        } catch (UnknownHostException e) {

            System.out.println(
                    "Loi: Khong phan giai duoc hostname: " + hostname);
        }
    }
}