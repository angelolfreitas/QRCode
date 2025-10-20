package com.angelofreitas.cifracesar.controller;

import com.angelofreitas.cifracesar.dto.encrypt.EncryptRequest;
import com.angelofreitas.cifracesar.dto.encrypt.EncryptResponse;
import com.angelofreitas.cifracesar.dto.encrypt.Type;
import com.angelofreitas.cifracesar.service.DecryptService;
import com.angelofreitas.cifracesar.service.EncryptService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/encrypt")
public class EncryptController {
    private final DecryptService decryptService;
    private final EncryptService encryptService;

    public EncryptController(DecryptService decryptService, EncryptService encryptService) {
        this.decryptService = decryptService;
        this.encryptService = encryptService;
    }

    @PostMapping
    public ResponseEntity<EncryptResponse> encript(@RequestBody EncryptRequest encryptRequest) {
        EncryptResponse response;
        Type type = encryptRequest.type();
        String text = encryptRequest.text();
        try{
            if(type.equals(Type.BINARY))
                response = decryptService.decrypt(text);
            else
                response = encryptService.response(text);

            return ResponseEntity.ok(response);
        }catch(StringIndexOutOfBoundsException e){
            return ResponseEntity.internalServerError().build();
        }
    }
}
