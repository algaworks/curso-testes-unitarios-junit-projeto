package com.algaworks.junit.blog.negocio;

import com.algaworks.junit.blog.modelo.Editor;
import com.algaworks.junit.blog.modelo.Ganhos;
import com.algaworks.junit.blog.modelo.Post;
import com.algaworks.junit.blog.utilidade.ProcessadorTextoSimples;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;

class CalculadoraGanhosTest {

    static CalculadoraGanhos calculadora;
    Editor autor;
    Post post;

    @BeforeAll
    static void beforeachAll() {

        System.out.println("Antes de todos os testes");
        calculadora = new CalculadoraGanhos(new ProcessadorTextoSimples(), BigDecimal.TEN);
    }

    @BeforeEach
    void beforEach() {

        System.out.println("Antes de cada teste");
        // Editor
        autor = new Editor(1L, "Alex", "alex@gmail", new BigDecimal(5), true);
        // Publicação
        post = new Post(1L,"Ecossistema Java","O Ecossistema do Java é muito maduro",
                autor, "ecossistema-java-abc123", null, false, false);
    }

//    @AfterEach
//    void afterEach() {
//        System.out.println("Depois de cada teste");
//    }
//
//    @AfterAll
//    static void afterAll() {
//        System.out.println("Depois de todos os testes");
//    }

    @Test
    public void deveCalcularGanhos() {

        Ganhos ganhos = calculadora.calcular(post);

        assertEquals(new BigDecimal("45"), ganhos.getTotalGanho());
        assertEquals(7, ganhos.getQuantidadePalavras());
        assertEquals(autor.getValorPagoPorPalavra(), ganhos.getValorPagoPorPalavra());
    }

    @Test
    public void deveCalcularGanhosSemPremium() {

        autor.setPremium(false);

        Ganhos ganhos = calculadora.calcular(post);

        assertEquals(new BigDecimal("35"), ganhos.getTotalGanho());
        assertEquals(7, ganhos.getQuantidadePalavras());
        assertEquals(autor.getValorPagoPorPalavra(), ganhos.getValorPagoPorPalavra());
    }

}