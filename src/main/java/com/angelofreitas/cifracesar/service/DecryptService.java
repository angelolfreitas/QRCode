package com.angelofreitas.cifracesar.service;

import com.angelofreitas.cifracesar.dto.encrypt.EncryptResponse;
import org.springframework.stereotype.Service;

@Service
public class DecryptService {
    public EncryptResponse decrypt(String cipherText) throws StringIndexOutOfBoundsException {
        StringBuilder binaryString = new StringBuilder();
        for (int i = 0; i < cipherText.length()-1; i+=8) {
            String byteBinario = cipherText.substring(i, i+8);
            int decimal  = Integer.parseInt(byteBinario, 2);
            binaryString.append((char)decimal);
        }
        return new EncryptResponse(binaryString.toString());
    }
}
