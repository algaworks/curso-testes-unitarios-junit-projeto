package com.algaworks.junit.blog.negocio;

import com.algaworks.junit.blog.modelo.Editor;
import com.algaworks.junit.blog.modelo.Ganhos;
import com.algaworks.junit.blog.modelo.Post;
import com.algaworks.junit.blog.utilidade.ProcessadorTextoSimples;
import org.junit.jupiter.api.*;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;
@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
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
    public void Dado_um_post_e_autor_premium_Quando_calcular_ganhos_Entao_retorna_valor_com_bonus() {

        Ganhos ganhos = calculadora.calcular(post);
        assertEquals(new BigDecimal("45"), ganhos.getTotalGanho());
    }

    @Test
    public void Dado_um_post_e_autor_Quando_calcular_ganhos_Entao_retorna_quantidade_de_palavras() {

        Ganhos ganhos = calculadora.calcular(post);
        assertEquals(7, ganhos.getQuantidadePalavras());
    }

    @Test
    public void Dado_um_post_e_autor_Quando_calcular_ganhos_Entao_deve_retorna_valor_pago_por_palavra_do_autor() {

        Ganhos ganhos = calculadora.calcular(post);
        assertEquals(autor.getValorPagoPorPalavra(), ganhos.getValorPagoPorPalavra());
    }

    @Test
    public void Dado_um_post_e_autor_que_nao_premium_Quando_calcular_ganhos_Entao_deve_retornar_valor_sem_bonus() {

        autor.setPremium(false);

        Ganhos ganhos = calculadora.calcular(post);
        assertEquals(new BigDecimal("35"), ganhos.getTotalGanho());
    }

}