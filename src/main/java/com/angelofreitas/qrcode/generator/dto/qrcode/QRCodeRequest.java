package com.angelofreitas.qrcode.generator.dto.qrcode;
/// classe simples de transferência de dados. Aqui, a formatação do json
/// é limitada pelo parâmetro text.
///
///
/// <pre>
/// <code>
/// .json
/// {
///  "text": "texto a ser injetado na instância desse record"
/// }
/// </code></pre>
///
/// a lógica é idêntica para o outro record, mas por enquanto esse dto é
/// tratado dentro desse sistema, então a formatação dele em json pouco importa pra gente.
public record QRCodeRequest(String text) {
}
