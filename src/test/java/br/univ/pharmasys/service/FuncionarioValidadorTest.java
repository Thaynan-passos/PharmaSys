package br.univ.pharmasys.service;

import br.univ.pharmasys.exceptions.DataInvalidoException;
import br.univ.pharmasys.exceptions.ErroDePreenchimentoInvalidoException;
import br.univ.pharmasys.exceptions.NomeInvalidoException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

public class FuncionarioValidadorTest {

    @Test
    @DisplayName("Deve validar senhas alfanuméricas com 8 a 14 caracteres")
    void testSenhaValida() {
        assertDoesNotThrow(() -> FuncionarioValidador.senhaValidar("admin12345"));
        assertDoesNotThrow(() -> FuncionarioValidador.senhaValidar("senhaSegura1"));
    }

    @Test
    @DisplayName("Deve rejeitar senhas com menos de 8 caracteres")
    void testSenhaCurta() {
        assertThrows(ErroDePreenchimentoInvalidoException.class, () -> FuncionarioValidador.senhaValidar("1234567"));
    }

    @Test
    @DisplayName("Deve rejeitar senhas com mais de 14 caracteres")
    void testSenhaLonga() {
        assertThrows(ErroDePreenchimentoInvalidoException.class, () -> FuncionarioValidador.senhaValidar("senhaMuitoLonga12345"));
    }

    @Test
    @DisplayName("Deve validar nome de funcionário contendo apenas letras e espaços")
    void testNomeFuncionario() {
        assertDoesNotThrow(() -> FuncionarioValidador.nomeValidar("Maria da Silva"));
        assertThrows(NomeInvalidoException.class, () -> FuncionarioValidador.nomeValidar("Carlos 123"));
    }

    @Test
    @DisplayName("Deve rejeitar data de nascimento futura")
    void testDataNascimentoFutura() {
        assertThrows(DataInvalidoException.class, () -> FuncionarioValidador.dataNascimentoValidar(LocalDate.now().plusDays(1)));
    }
}
