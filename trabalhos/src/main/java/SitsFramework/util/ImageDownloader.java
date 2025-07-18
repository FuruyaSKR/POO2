package SitsFramework.util;

import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.URL;

public class ImageDownloader {
    public static void download(String fileUrl, String destino) throws Exception {
        try (InputStream in = new URL(fileUrl).openStream();
                FileOutputStream out = new FileOutputStream(destino)) {
            byte[] buffer = new byte[4096];
            int bytesRead;
            while ((bytesRead = in.read(buffer)) != -1) {
                out.write(buffer, 0, bytesRead);
            }
        }
    }
}
