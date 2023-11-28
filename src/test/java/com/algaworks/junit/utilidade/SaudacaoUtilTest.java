package com.algaworks.junit.utilidade;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;

import static com.algaworks.junit.utilidade.SaudacaoUtil.saudar;
import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Testes no utilitário de saudação")
class SaudacaoUtilTest {

    @Test
    @DisplayName("Deve saudar com bom dia")
    public void saudarComBomDia() {
        // [A]rrange: Preparar as variáveis para o senário de teste;
        int horaValida = 9;

        // [A]ctive: Executando o cenário;
        String saudacao = saudar(horaValida);

        // [A]ssert: Validar se o cenário está correto;
        assertEquals("Bom dia", saudacao, "Saudação incorreta!");
    }

    @Test
    @DisplayName("Deve saudar com bom dia às 5 horas")
    public void saudarComBomDiaApartir5h() {
        // [A]rrange: Preparar as variáveis para o senário de teste;
        int horaValida = 5;

        // [A]ctive: Executando o cenário;
        String saudacao = saudar(horaValida);

        // [A]ssert: Validar se o cenário está correto;
        assertEquals("Bom dia", saudacao, "Saudação incorreta!");
    }

    @Test
    @DisplayName("Deve saudar com boa tarde")
    public void saudarComBoaTarde() {
        // [A]rrange: Preparar as variáveis para o senário de teste;
        int horaValida = 15;

        // [A]ctive: Executando o cenário;
        String saudacao = saudar(horaValida);

        // [A]ssert: Validar se o cenário está correto;
        assertEquals("Boa tarde", saudacao, "Saudação incorreta!");
    }

    @Test
    @DisplayName("Deve saudar com bom noite")
    public void saudarComBoaNoite() {
        // [A]rrange: Preparar as variáveis para o senário de teste;
        int horaValida = 22;

        // [A]ctive: Executando o cenário;
        String saudacao = saudar(horaValida);

        // [A]ssert: Validar se o cenário está correto;
        assertEquals("Boa noite", saudacao, "Saudação incorreta!");
    }
    @Test
    @DisplayName("Deve saudar com boa noite às 4 horas")
    public void saudarComBoaNoiteAs4h() {
        // [A]rrange: Preparar as variáveis para o senário de teste;
        int horaValida = 4;

        // [A]ctive: Executando o cenário;
        String saudacao = saudar(horaValida);

        // [A]ssert: Validar se o cenário está correto;
        assertEquals("Boa noite", saudacao, "Saudação incorreta!");
    }

    @Test
    @DisplayName("Deve lançar uma exception quando for hora negativa")
    public void deveLancarException() {
        // [A]rrange: Preparar as variáveis para o senário de teste;
        int horaInvalida = -10;

        // [A]ctive: Executando o cenário;
        Executable chamadaInvalidaDeMetodo = ()-> saudar(horaInvalida);
        IllegalArgumentException e = assertThrows(IllegalArgumentException.class, chamadaInvalidaDeMetodo);

        // [A]ssert: Validar se o cenário está correto;
        assertEquals("Hora invalida", e.getMessage());
    }

    @Test
    @DisplayName("Não lançar uma exception quando for hora for igual a zero")
    public void naoDeveLancarException() {
        // [A]rrange: Preparar as variáveis para o senário de teste;
        int horaValida = 0;

        // [A]ctive: Executando o cenário;
        Executable  chamadaValidaDeMetodo = () -> saudar(horaValida);

        // [A]ssert: Validar se o cenário está correto;
        assertDoesNotThrow(chamadaValidaDeMetodo);
    }

}