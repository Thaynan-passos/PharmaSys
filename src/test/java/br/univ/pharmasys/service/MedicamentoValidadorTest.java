package br.univ.pharmasys.service;

import br.univ.pharmasys.exceptions.CustoUnitarioInvalidoException;
import br.univ.pharmasys.exceptions.ErroDePreenchimentoInvalidoException;
import br.univ.pharmasys.exceptions.EstoqueInvalidoException;
import br.univ.pharmasys.exceptions.NomeInvalidoException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

public class MedicamentoValidadorTest {

    @Test
    @DisplayName("Deve permitir nomes de medicamentos comerciais contendo dosagens numéricas e hífens")
    void testNomeComercialValido() {
        assertDoesNotThrow(() -> MedicamentoValidador.nomeValidador("Dipirona 500mg"));
        assertDoesNotThrow(() -> MedicamentoValidador.nomeValidador("Vitamina C-500"));
        assertDoesNotThrow(() -> MedicamentoValidador.nomeValidador("Dorflex 36"));
    }

    @Test
    @DisplayName("Deve rejeitar nomes vazios")
    void testNomeComercialVazio() {
        assertThrows(NomeInvalidoException.class, () -> MedicamentoValidador.nomeValidador(""));
        assertThrows(NomeInvalidoException.class, () -> MedicamentoValidador.nomeValidador("   "));
    }

    @Test
    @DisplayName("Deve validar formato de SKU")
    void testSkuValidador() {
        assertDoesNotThrow(() -> MedicamentoValidador.skuValidador("MED-001"));
        assertThrows(ErroDePreenchimentoInvalidoException.class, () -> MedicamentoValidador.skuValidador("med_minusculo"));
    }

    @Test
    @DisplayName("Deve validar regras de estoque mínimo e máximo")
    void testEstoqueRegras() {
        assertDoesNotThrow(() -> MedicamentoValidador.estoqueMinValidador(10, 100));
        assertThrows(EstoqueInvalidoException.class, () -> MedicamentoValidador.estoqueMinValidador(150, 100));
        assertThrows(EstoqueInvalidoException.class, () -> MedicamentoValidador.estoqueMinValidador(-5, 100));
    }

    @Test
    @DisplayName("Deve rejeitar preços nulos ou negativos")
    void testPrecoValidador() {
        assertDoesNotThrow(() -> MedicamentoValidador.precoValidador(new BigDecimal("19.90")));
        assertThrows(CustoUnitarioInvalidoException.class, () -> MedicamentoValidador.precoValidador(BigDecimal.ZERO));
        assertThrows(CustoUnitarioInvalidoException.class, () -> MedicamentoValidador.precoValidador(new BigDecimal("-1.00")));
    }
}
