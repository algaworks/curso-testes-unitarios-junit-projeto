package com.algaworks.junit.utilidade;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;

import static com.algaworks.junit.utilidade.SaudacaoUtil.saudar;
import static org.junit.jupiter.api.Assertions.*;

class SaudacaoUtilTest {

    @Test
    public void saudarComBomDia() {
        // [A]rrange: Preparar as variáveis para o senário de teste;
        int horaValida = 9;

        // [A]ctive: Executando o cenário;
        String saudacao = saudar(horaValida);

        // [A]ssert: Validar se o cenário está correto;
        assertEquals("Bom dia", saudacao, "Saudação incorreta!");
    }

    @Test
    public void saudarComBomDiaApartir5h() {
        // [A]rrange: Preparar as variáveis para o senário de teste;
        int horaValida = 5;

        // [A]ctive: Executando o cenário;
        String saudacao = saudar(horaValida);

        // [A]ssert: Validar se o cenário está correto;
        assertEquals("Bom dia", saudacao, "Saudação incorreta!");
    }

    @Test
    public void saudarComBoaTarde() {
        // [A]rrange: Preparar as variáveis para o senário de teste;
        int horaValida = 15;

        // [A]ctive: Executando o cenário;
        String saudacao = saudar(horaValida);

        // [A]ssert: Validar se o cenário está correto;
        assertEquals("Boa tarde", saudacao, "Saudação incorreta!");
    }

    @Test
    public void saudarComBoaNoite() {
        // [A]rrange: Preparar as variáveis para o senário de teste;
        int horaValida = 22;

        // [A]ctive: Executando o cenário;
        String saudacao = saudar(horaValida);

        // [A]ssert: Validar se o cenário está correto;
        assertEquals("Boa noite", saudacao, "Saudação incorreta!");
    }
    @Test
    public void saudarComBoaNoiteAs4h() {
        // [A]rrange: Preparar as variáveis para o senário de teste;
        int horaValida = 4;

        // [A]ctive: Executando o cenário;
        String saudacao = saudar(horaValida);

        // [A]ssert: Validar se o cenário está correto;
        assertEquals("Boa noite", saudacao, "Saudação incorreta!");
    }

    @Test
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
    public void naoDeveLancarException() {
        // [A]rrange: Preparar as variáveis para o senário de teste;
        int horaValida = 0;

        // [A]ctive: Executando o cenário;
        Executable  chamadaValidaDeMetodo = () -> saudar(horaValida);

        // [A]ssert: Validar se o cenário está correto;
        assertDoesNotThrow(chamadaValidaDeMetodo);
    }

}