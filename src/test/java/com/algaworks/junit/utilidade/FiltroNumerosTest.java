package com.algaworks.junit.utilidade;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
class FiltroNumerosTest {

    @Test
    public void deve_retornar_rumeros_pares() {

        // [A]rrange: Preparar as variáveis para o senário de teste;
        List<Integer> numeros = Arrays.asList(1, 2, 3, 4);
        List<Integer> numerosParesEsperados = Arrays.asList(2, 4);

        // [A]ctive: Executando o cenário;
        List<Integer> resultadoFiltro = FiltroNumeros.numerosPares(numeros);

        // [A]ssert: Validar se o cenário está correto, com o resultado;
        //verificar tanto do conteúdo quanto da ordem
        Assertions.assertArrayEquals(numerosParesEsperados.toArray(new Object[]{}), resultadoFiltro.toArray(new Object[]{}));

    }

}