package com.angelofreitas.qrcode.generator.controller;

import com.angelofreitas.qrcode.generator.dto.qrcode.QRCodeRequest;
import com.angelofreitas.qrcode.generator.dto.qrcode.QRCodeResponse;
import com.angelofreitas.qrcode.generator.service.QRCodeService;
import com.google.zxing.WriterException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
/// um controler é um comando que é fortemente relacionado a uma lógica
/// abstraída em um service
///
/// Um controller marcado com requestmapping é responsável por
/// receber as ações de post do http.
///
/// Seguindo os conceitos de RESTfull, cada funcionalidade é
/// feita em camadas específicas (endpoints).
///
/// De forma resumida, o papel desse controller é ser o sistema que vai receber
/// as requisições HTTP através de objetos de requisição formatados em jsons
/// através da endpoint qrcode. Iremos retornar esse objeto em outro formato
/// através de um srvice que vai gerar o qrcode a partir do link postado pelo
/// usuário. O objeto retornado terá tanto a imagem do qrcode quanto o status
/// (seguindo as normas do REST)
@RestController
@RequestMapping("/qrcode")
public class QRCodeController {
    ///serviço que transforma  um texto em qrcode
    private final QRCodeService qrCodeService;
    ///o construtor é injetado com um service se cada classe foi
    ///marcada corretamente
    public QRCodeController(QRCodeService qrCodeService) {
        this.qrCodeService = qrCodeService;
    }

    ///A conversão em qrcode é bem sucedida se não teve nenhum
    ///problema na formatação da entrada ou na conversão do qrcode.
    ///A anotação de post informa que esse método vai retornar a requisição dessa classe.
    ///A anotação RequestBody vai exigir que os parâmetros sejam formatados pelo
    ///dto que nós criamos (os campos dos jsons precisam ser estruturados com base nesses dtos).
    @PostMapping
    public ResponseEntity<QRCodeResponse> generate(@RequestBody QRCodeRequest request){
        try {
            QRCodeResponse response =  qrCodeService.uploadQRCode(request.text());
            //código 200: tudo certo
            return  ResponseEntity.ok(response);
        } catch (WriterException | IOException ignored) {
            //erro interno: 500, pode ser causado tanto pela má formatação
            //quanto pelas permissões limitadas na política do aws
            return ResponseEntity.internalServerError().build();
        }
    }
}
