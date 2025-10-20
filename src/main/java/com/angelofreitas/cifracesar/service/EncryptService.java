package com.angelofreitas.cifracesar.service;

import com.angelofreitas.cifracesar.dto.encrypt.EncryptResponse;
import com.angelofreitas.cifracesar.port.Port;
import org.springframework.stereotype.Service;

@Service
public class EncryptService {
    public EncryptResponse response(String txt){
        StringBuilder binaryString = new StringBuilder();

        for(char c : txt.toCharArray()){
            String binaryChar = Integer.toBinaryString(c);
            String paddedBinaryChar = String.format("%8s", binaryChar).replace(' ', '0');
            binaryString.append(paddedBinaryChar);
        }

        return new EncryptResponse(binaryString.toString());
    }
}
