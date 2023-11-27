package com.algaworks.junit.utilidade;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class SaudacaoUtilTest {

    @Test
    public void saudarComBomDia() {

        String saudacao = SaudacaoUtil.saudar(9);
        assertEquals("Bom dia", saudacao, "Saudação incorreta!");
    }

    @Test
    public void saudarComBomDiaApartir5h() {

        String saudacao = SaudacaoUtil.saudar(5);
        assertEquals("Bom dia", saudacao, "Saudação incorreta!");
    }

    @Test
    public void saudarComBoaTarde() {

        String saudacao = SaudacaoUtil.saudar(15);
        assertEquals("Boa tarde", saudacao, "Saudação incorreta!");
    }

    @Test
    public void saudarComBoaNoite() {

        String saudacao = SaudacaoUtil.saudar(22);
        assertEquals("Boa noite", saudacao, "Saudação incorreta!");
    }
    @Test
    public void saudarComBoaNoiteAs4h() {

        String saudacao = SaudacaoUtil.saudar(4);
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