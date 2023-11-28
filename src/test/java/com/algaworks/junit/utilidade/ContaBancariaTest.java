package com.algaworks.junit.utilidade;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class ContaBancariaTest {

    @Test
    void saque() {
        // [A]rrange: Preparar as variáveis para o senário de teste;
        ContaBancaria conta = new ContaBancaria(new BigDecimal("70.00"));
        // [A]ctive: Executando o cenário;
        conta.saque(new BigDecimal("50.01"));
        // [A]ssert: Validar se o cenário está correto;
        assertEquals(new BigDecimal("19.99"), conta.saldo());
    }

    @Test
    void saqueComValorZeradoFalha() {
        // [A]rrange: Preparar as variáveis para o senário de teste;
        ContaBancaria conta = new ContaBancaria(BigDecimal.TEN);
        // [A]ctive: Executando o cenário;
        // [A]ssert: Validar se o cenário está correto;
        assertThrows(RuntimeException.class, ()-> conta.saque(BigDecimal.ZERO));
    }

    @Test
    void saqueComValorNegativoFalha() {
        // [A]rrange: Preparar as variáveis para o senário de teste;
        ContaBancaria conta = new ContaBancaria(BigDecimal.TEN);
        // [A]ctive: Executando o cenário;
        // [A]ssert: Validar se o cenário está correto;
        assertThrows(RuntimeException.class, ()-> conta.saque(new BigDecimal("-10.0")));
    }

    @Test
    void saqueComValorMaiorFalha() {
        // [A]rrange: Preparar as variáveis para o senário de teste;
        ContaBancaria conta = new ContaBancaria(BigDecimal.TEN);
        // [A]ctive: Executando o cenário;
        // [A]ssert: Validar se o cenário está correto;
        assertThrows(RuntimeException.class, ()-> conta.saque(new BigDecimal("20.0")));
    }

    @Test
    void saqueComValorIgualNaoFalha() {
        // [A]rrange: Preparar as variáveis para o senário de teste;
        ContaBancaria conta = new ContaBancaria(BigDecimal.TEN);
        // [A]ctive: Executando o cenário;
        conta.saque(new BigDecimal("10.00"));
        // [A]ssert: Validar se o cenário está correto;
        assertEquals(new BigDecimal("0.00"), conta.saldo());
    }

    @Test
    void saqueComValorNullFalha() {
        // [A]rrange: Preparar as variáveis para o senário de teste;
        ContaBancaria conta = new ContaBancaria(BigDecimal.TEN);
        // [A]ctive: Executando o cenário;
        // [A]ssert: Validar se o cenário está correto;
        assertThrows(IllegalArgumentException.class, ()-> conta.saque(null));
    }

    @Test
    void deposito() {
        // [A]rrange: Preparar as variáveis para o senário de teste;
        ContaBancaria conta = new ContaBancaria(BigDecimal.TEN);
        // [A]ctive: Executando o cenário;
        conta.deposito(BigDecimal.TEN);
        // [A]ssert: Validar se o cenário está correto;
        assertEquals(new BigDecimal("20"), conta.saldo());
    }

    @Test
    void depositoComValorZeradoFalha() {
        // [A]rrange: Preparar as variáveis para o senário de teste;
        ContaBancaria conta = new ContaBancaria(BigDecimal.TEN);
        // [A]ctive: Executando o cenário;
        // [A]ssert: Validar se o cenário está correto;
        assertThrows(RuntimeException.class, ()-> conta.deposito(BigDecimal.ZERO));
    }

    @Test
    void depositoComValorNegativoFalha() {
        // [A]rrange: Preparar as variáveis para o senário de teste;
        ContaBancaria conta = new ContaBancaria(BigDecimal.TEN);
        // [A]ctive: Executando o cenário;
        // [A]ssert: Validar se o cenário está correto;
        assertThrows(RuntimeException.class, ()-> conta.deposito(new BigDecimal("-10.0")));
    }

    @Test
    void depositoComValorNullFalha() {
        // [A]rrange: Preparar as variáveis para o senário de teste;
        ContaBancaria conta = new ContaBancaria(BigDecimal.TEN);
        // [A]ctive: Executando o cenário;
        // [A]ssert: Validar se o cenário está correto;
        assertThrows(IllegalArgumentException.class, ()-> conta.deposito(null));
    }

    @Test
    void saqueAposDeposito() {
        // [A]rrange: Preparar as variáveis para o senário de teste;
        ContaBancaria conta = new ContaBancaria(BigDecimal.TEN);
        // [A]ctive: Executando o cenário;
        conta.deposito(BigDecimal.TEN);
        conta.saque(new BigDecimal("5"));
        // [A]ssert: Validar se o cenário está correto;
        assertEquals(new BigDecimal("15"), conta.saldo());
    }

    @Test
    void saldo() {
        // [A]rrange: Preparar as variáveis para o senário de teste;
        ContaBancaria conta = new ContaBancaria(new BigDecimal("29.90"));
        // [A]ctive: Executando o cenário;
        // [A]ssert: Validar se o cenário está correto;
        assertEquals(new BigDecimal("29.90"), conta.saldo());
    }

    @Test
    void criarContaComSaldoNullFalha() {
        // [A]rrange: Preparar as variáveis para o senário de teste;
        // [A]ctive: Executando o cenário;
        // [A]ssert: Validar se o cenário está correto;
        assertThrows(IllegalArgumentException.class, ()-> new ContaBancaria(null));
    }

}