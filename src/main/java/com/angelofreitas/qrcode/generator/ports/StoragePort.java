package com.angelofreitas.qrcode.generator.ports;
/// Interface que abstrai as implementações de portas necesárias
/// para as interações com o http.
/// Isso é ótimo para a escalabilidade do projeto, pois podemos ter
/// múltiplas implementações de portas coexistentes sem atrapalhar
/// os contextos que as demandem.
/// Para mudar a implementação, basta trocar a anotação Component
public interface StoragePort {
    String uploadFile(byte[] file, String fileName, String contentType);
}
