package com.angelofreitas.cifracesar.port;

public interface Port {
    String uploadFile(byte[] data, String key, String contentType);
}
