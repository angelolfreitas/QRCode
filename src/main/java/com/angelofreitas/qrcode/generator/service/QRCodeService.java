package com.angelofreitas.qrcode.generator.service;

import com.angelofreitas.qrcode.generator.dto.qrcode.QRCodeResponse;
import com.angelofreitas.qrcode.generator.ports.StoragePort;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.UUID;
/// um sérvice é uma lógica mais complexa encapsulada que deve ser usada em
/// um constroller.
/// é semelhante a um susbsistema no padrão command-based
@Service
public class QRCodeService {
    private final StoragePort storagePort;

    public QRCodeService(StoragePort storagePort) {
        this.storagePort = storagePort;
    }
    //texto a ser transformado em qrcode
    public QRCodeResponse uploadQRCode(String txt) throws WriterException, IOException {
        QRCodeWriter qrCodeWriter = new QRCodeWriter();
        //transforma um texto numa matriz de bits
        BitMatrix bitMatrix = qrCodeWriter.encode(txt, BarcodeFormat.QR_CODE, 200, 200);

        ByteArrayOutputStream pngOutputStream = new ByteArrayOutputStream();

        //writer que transforma matriz de bits em imagem
        MatrixToImageWriter.writeToStream(bitMatrix, "PNG", pngOutputStream);

        //conversão em array de bits (serialização), para lançar para o aws
        byte[] pngData = pngOutputStream.toByteArray();

        String url = storagePort.uploadFile(pngData, UUID.randomUUID().toString(), "image/png");

        return new QRCodeResponse(url);
    }
}
