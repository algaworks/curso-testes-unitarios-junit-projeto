package com.algaworks.junit.utilidade;

import java.math.BigDecimal;

import static java.util.Objects.isNull;

public class ContaBancaria {

    private BigDecimal saldo;

    public ContaBancaria(BigDecimal saldoInicial) {

        //TODO 1 - validar saldo: não pode ser nulo, caso seja, deve lançar uma IllegalArgumentException
        if (saldoInicial == null) {
            throw new IllegalArgumentException("Saldo não pode ser null");
        }

        //TODO 2 - pode ser zero ou negativo
        this.saldo = saldoInicial;
    }

    public void saque(BigDecimal valor) {

        //TODO 1 - validar valor: não pode ser nulo, zero ou menor que zero, caso seja, deve lançar uma IllegalArgumentException
        if (valor == null || valor.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("Valor não pode ser nulo, zero ou menor que zero");
        }

        //TODO 2 - Deve subtrair o valor do saldo
        if (this.saldo.compareTo(valor) < 0) {
            throw new RuntimeException("Saldo insuficiente para saque");
        }

        //TODO 3 - Se o saldo for insuficiente deve lançar uma RuntimeException
        this.saldo = this.saldo.subtract(valor);
    }

    public void deposito(BigDecimal valor) {

        //TODO 1 - validar valor: não pode ser nulo, zero ou menor que zero, caso seja, deve lançar uma IllegalArgumentException
        if (valor == null || valor.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("Valor não pode ser nulo, zero ou menor que zero");
        }

        //TODO 2 - Deve adicionar o valor ao saldo
        this.saldo = this.saldo.add(valor);
    }

    public BigDecimal saldo() {
        //TODO 1 - retornar saldo
        return this.saldo;
    }
}
