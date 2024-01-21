package com.algaworks.junit.utilidade;

import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;

import static com.algaworks.junit.utilidade.SaudacaoUtil.saudar;
import static org.junit.jupiter.api.Assertions.*;

@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
class SaudacaoUtilTest {

    @Test
    public void Dado_uma_horario_matuino_Quando_saudar_Entao_deve_retornar_bom_dia() {
        // [A]rrange: Preparar as variáveis para o senário de teste;
        int horaValida = 9;

        // [A]ctive: Executando o cenário;
        String saudacao = saudar(horaValida);

        // [A]ssert: Validar se o cenário está correto;
        assertEquals("Bom dia", saudacao, "Saudação incorreta!");
    }

    @Test
    public void Dado_uma_horario_vespertino_Quando_saudar_Entao_deve_retornar_boa_tarde() {
        // [A]rrange: Preparar as variáveis para o senário de teste;
        int horaValida = 15;

        // [A]ctive: Executando o cenário;
        String saudacao = saudar(horaValida);

        // [A]ssert: Validar se o cenário está correto;
        assertEquals("Boa tarde", saudacao, "Saudação incorreta!");
    }

    @Test
    public void Dado_uma_horario_noturno_Quando_saudar_Entao_deve_retornar_boa_noite() {
        // [A]rrange: Preparar as variáveis para o senário de teste;
        int horaValida = 4;

        // [A]ctive: Executando o cenário;
        String saudacao = saudar(horaValida);

        // [A]ssert: Validar se o cenário está correto;
        assertEquals("Boa noite", saudacao, "Saudação incorreta!");
    }

    @Test
    public void Dado_uma_hora_invalida_Quando_saudar_Entao_deve_lancar_exception() {
        // [A]rrange: Preparar as variáveis para o senário de teste;
        int horaInvalida = -10;

        // [A]ctive: Executando o cenário;
        Executable chamadaInvalidaDeMetodo = ()-> saudar(horaInvalida);
        IllegalArgumentException e = assertThrows(IllegalArgumentException.class, chamadaInvalidaDeMetodo);

        // [A]ssert: Validar se o cenário está correto;
        assertEquals("Hora invalida", e.getMessage());
    }

    @Test
    public void Dado_uma_hora_valida_Quando_saudar_Entao_nao_deve_lancar_exception() {
        // [A]rrange: Preparar as variáveis para o senário de teste;
        int horaValida = 0;

        // [A]ctive: Executando o cenário;
        Executable  chamadaValidaDeMetodo = () -> saudar(horaValida);

        // [A]ssert: Validar se o cenário está correto;
        assertDoesNotThrow(chamadaValidaDeMetodo);
    }

}