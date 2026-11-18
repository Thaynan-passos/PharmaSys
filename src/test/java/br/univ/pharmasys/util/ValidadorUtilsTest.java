package br.univ.pharmasys.util;

import br.univ.pharmasys.exceptions.ErroDePreenchimentoInvalidoException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class ValidadorUtilsTest {

    @Test
    @DisplayName("Deve validar CPF válido com sucesso")
    void testCpfValido() {
        assertDoesNotThrow(() -> ValidadorUtils.cpfValido("11122233396"));
    }

    @Test
    @DisplayName("Deve rejeitar CPF com dígitos repetidos")
    void testCpfDigitosRepetidos() {
        assertThrows(ErroDePreenchimentoInvalidoException.class, () -> ValidadorUtils.cpfValido("11111111111"));
    }

    @Test
    @DisplayName("Deve rejeitar CPF com tamanho incorreto")
    void testCpfTamanhoInvalido() {
        assertThrows(ErroDePreenchimentoInvalidoException.class, () -> ValidadorUtils.cpfValido("123456"));
    }

    @Test
    @DisplayName("Deve validar CNPJ válido com sucesso")
    void testCnpjValido() {
        // CNPJ válido de teste (Banco do Brasil)
        assertDoesNotThrow(() -> ValidadorUtils.cnpjValido("00000000000191"));
    }

    @Test
    @DisplayName("Deve rejeitar CNPJ com dígitos repetidos")
    void testCnpjDigitosRepetidos() {
        assertThrows(ErroDePreenchimentoInvalidoException.class, () -> ValidadorUtils.cnpjValido("00000000000000"));
    }

    @Test
    @DisplayName("Deve validar e-mails no formato correto")
    void testEmailValido() {
        assertDoesNotThrow(() -> ValidadorUtils.emailValido("teste@pharmasys.com"));
        assertThrows(ErroDePreenchimentoInvalidoException.class, () -> ValidadorUtils.emailValido("email_sem_arroba.com"));
    }
}
