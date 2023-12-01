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
    public void Dado_uma_lista_de_numeros_Quando_filtrar_por_pares_Entao_deve_retornar_apenas_numeros_pares() {

        // [A]rrange: Preparar as variáveis para o senário de teste;
        List<Integer> numeros = Arrays.asList(1, 2, 3, 4);
        List<Integer> numerosParesEsperados = Arrays.asList(2, 4);

        // [A]ctive: Executando o cenário;
        List<Integer> resultadoFiltro = FiltroNumeros.numerosPares(numeros);

        // [A]ssert: Validar se o cenário está correto, com o resultado;
        //verificar tanto do conteúdo quanto da ordem
        Assertions.assertArrayEquals(numerosParesEsperados.toArray(new Object[]{}), resultadoFiltro.toArray(new Object[]{}));

    }

    @Test
    public void Dado_uma_lista_de_numeros_Quando_filtrar_por_impares_Entao_deve_retornar_apenas_numeros_impares() {

        // [A]rrange: Preparar as variáveis para o senário de teste;
        List<Integer> numeros = Arrays.asList(1, 2, 3, 4);
        List<Integer> numerosImparesEsperados = Arrays.asList(1, 3);

        // [A]ctive: Executando o cenário;
        List<Integer> resultadoFiltro = FiltroNumeros.numerosImpares(numeros);

        // [A]ssert: Validar se o cenário está correto, com o resultado;
        //verificar tanto do conteúdo quanto da ordem
        Assertions.assertArrayEquals(numerosImparesEsperados.toArray(new Object[]{}), resultadoFiltro.toArray(new Object[]{}));

    }

}