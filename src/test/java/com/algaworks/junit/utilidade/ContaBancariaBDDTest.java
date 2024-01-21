package com.algaworks.junit.utilidade;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Conta Bancária")
public class ContaBancariaBDDTest {

    @Nested
    @DisplayName("[Cenário]-Dado uma conta bancária com saldo de R$ 10,00")
    class ContaBancariaComSaldo {

        private ContaBancaria conta;

        // [A]rrange: Preparar as variáveis para o senário de teste;
        @BeforeEach
        @DisplayName("Quando")
        void beforeEach() {

            System.out.println("Antes de cada teste");
            conta = new ContaBancaria(BigDecimal.TEN);

        }

        // [A]ctive: Executando o cenário;
        @Nested
        @DisplayName("->Condições: Quando efetuar o saque com valor menor")
        class SaqueComValorMenor {

            private BigDecimal valorSaque = new BigDecimal("9.0");

            // [A]ssert: Validar se o cenário está correto;
            @Test
            @DisplayName("Então não deve lançar Exception")
            void naoDeveLancarSaqueSemException() {
                assertDoesNotThrow(()-> conta.saque(valorSaque));
            }

            // [A]ssert: Validar se o cenário está correto;
            @Test
            @DisplayName("E deve subtrair do saldo")
            void deveSubtrairDoSaldo()  {

                conta.saque(valorSaque);
                assertEquals(new BigDecimal("1.0"), conta.saldo());
            }

        }

        // [A]ctive: Executando o cenário;
        @Nested
        @DisplayName("->Condições: Quando efetuar o saque com valor maior")
        class SaqueComValorMaior {

            private BigDecimal valorSaque = new BigDecimal("20.0");

            // [A]ssert: Validar se o cenário está correto;
            @Test
            @DisplayName("Então deve lançar Exception")
            void deveFalhar() {
                assertThrows(RuntimeException.class, ()-> conta.saque(valorSaque));
            }

            // [A]ssert: Validar se o cenário está correto;
            @Test
            @DisplayName("E não deve alterar saldo")
            void naoDeveSubtrairDoSaldo()  {
                // para evitar lançar exception usar try catch, com catch vazio.
                try {
                    conta.saque(valorSaque);
                } catch (Exception e) {}

                assertEquals(BigDecimal.TEN, conta.saldo());
            }

        }

    }

    @Nested
    @DisplayName("[Cenário]-Dado uma conta bancária com saldo R$ 0,00")
    class ContaBancariaComSaldoZerado {

        private ContaBancaria conta;

        // [A]rrange: Preparar as variáveis para o senário de teste;
        @BeforeEach
        @DisplayName("Quando")
        void beforeEach() {

            System.out.println("Antes de cada teste");
            conta = new ContaBancaria(BigDecimal.ZERO);

        }

        // [A]ctive: Executando o cenário;
        @Nested
        @DisplayName("->Condições: Quando efetuar o saque com valor maior")
        class SaqueComValorMaior {

            private BigDecimal valorSaque = new BigDecimal("0.1");

            // [A]ssert: Validar se o cenário está correto;
            @Test
            @DisplayName("Então deve lançar Exception")
            void deveFalhar() {
                assertThrows(RuntimeException.class, ()-> conta.saque(valorSaque));
            }

        }

        // [A]ctive: Executando o cenário;
        @Nested
        @DisplayName("->Condições: Quando efetuar um depósito de R$ 8,00")
        class DepositoComDezReais {

            private BigDecimal valorDeposito = new BigDecimal("8.00");

            // [A]ssert: Validar se o cenário está correto;
            @Test
            @DisplayName("Então deve somar ao saldo")
            void deveSomarAoSaldo() {

                conta.deposito(valorDeposito);
                assertEquals(new BigDecimal("8.00"), conta.saldo());
            }

        }

    }


}
