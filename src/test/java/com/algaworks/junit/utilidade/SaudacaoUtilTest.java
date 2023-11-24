package com.algaworks.junit.utilidade;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class SaudacaoUtilTest {

    @Test
    public void saudarDeveMostrarBomDia() {

        String saudacao = SaudacaoUtil.saudar(9);

        assertEquals("Bom dia", saudacao, "Saudação incorreta!");
    }

    @Test
    public void saudarDeveMostrarBoaTarde() {

        String saudacao = SaudacaoUtil.saudar(15);

        assertEquals("Boa tarde", saudacao, "Saudação incorreta!");
    }

    @Test
    public void saudarDeveMostrarBoaNoite() {

        String saudacao = SaudacaoUtil.saudar(20);

        assertEquals("Boa noite", saudacao, "Saudação incorreta!");
    }

    @Test
    public void deveLancarException() {

        IllegalArgumentException illegalArgumentException = assertThrows(IllegalArgumentException.class,
                ()-> SaudacaoUtil.saudar(-10));

        assertEquals("Hora invalida", illegalArgumentException.getMessage());
    }

    @Test
    public void naoDeveLancarException() {
        assertDoesNotThrow(() -> SaudacaoUtil.saudar(0));
    }

}