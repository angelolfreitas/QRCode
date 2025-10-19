package com.angelofreitas.qrcode.generator.dto.qrcode;
/// classe simples de transferência de dados. Aqui, vamos retornar a
/// localização desse qrcode dentro do nosso servidor aws (por isso precisamos configurar ele).
public record QRCodeResponse(String url) {
}
