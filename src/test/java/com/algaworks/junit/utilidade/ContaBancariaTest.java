package com.algaworks.junit.utilidade;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class ContaBancariaTest {

    @Test
    public void deveLancarException() {

        IllegalArgumentException illegalArgumentException = assertThrows(IllegalArgumentException.class,
                ()-> new ContaBancaria(null));

        assertEquals("Saldo não pode ser null", illegalArgumentException.getMessage());
    }

    @Test
    public void contaBancariaPodeSerZero() {
        BigDecimal valor = BigDecimal.valueOf(0);
        assertEquals(BigDecimal.ZERO, valor);
    }

    @Test
    public void saqueNaoPodeSerNuloDeveLancarExceptionIllegalArgumentException() {

        ContaBancaria contaBancaria = new ContaBancaria(BigDecimal.ZERO);

        IllegalArgumentException illegalArgumentException = assertThrows(IllegalArgumentException.class,
                ()-> contaBancaria.saque(null));
        assertEquals("Valor não pode ser nulo, zero ou menor que zero", illegalArgumentException.getMessage());
    }

    @Test
    public void saqueNaoPodeSerZero() {
        ContaBancaria contaBancaria = new ContaBancaria(BigDecimal.TEN);

        // O saque com valor zero deve lançar uma exceção
        Exception excecao = assertThrows(IllegalArgumentException.class, () -> {
            contaBancaria.saque(BigDecimal.ZERO);
        });

        String mensagemEsperada = "Valor não pode ser nulo, zero ou menor que zero";
        assertEquals(mensagemEsperada, excecao.getMessage());
    }

    @Test
    public void saqueNaoPodeSerNegativo() {
        ContaBancaria contaBancaria = new ContaBancaria(BigDecimal.TEN);

        // O saque com valor zero deve lançar uma exceção
        Exception excecao = assertThrows(IllegalArgumentException.class, () -> {
            contaBancaria.saque(BigDecimal.valueOf(-1));
        });

        String mensagemEsperada = "Valor não pode ser nulo, zero ou menor que zero";
        assertEquals(mensagemEsperada, excecao.getMessage());
    }

}