package com.axibase.xmpp.core;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Base64;

public class ImageHelper {
    public static String loadAsDataUrl(String srcPath) throws IOException {
        String mimeType = Files.probeContentType(Paths.get(srcPath));
        byte[] data = Files.readAllBytes(Paths.get(srcPath));
        String dataEncoded = Base64.getEncoder().encodeToString(data);
        return String.format("data:%s;base64,%s", mimeType, dataEncoded);
    }
}
