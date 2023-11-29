package com.algaworks.junit.ecommerce;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Carrinho de Compra")
class CarrinhoCompraAlexTest {

    @Nested
    @DisplayName("[Cenário]-Dado um carrinho de compra com 2 tipos de itens e quantidade 3")
    class CarrinhoCompraComItens {

        private CarrinhoCompra carrinhoCompra;
        private Cliente cliente;
        private List<ItemCarrinhoCompra> itens = new ArrayList<>();

        // [A]rrange: Preparar as variáveis para o senário de teste;
        @BeforeEach
        @DisplayName("Quando")
        void beforeEach() {

            System.out.println("Antes de cada teste, CarrinhoCompraComItens.");

            cliente = new Cliente(1L, "Alex");

            ItemCarrinhoCompra item1 = new ItemCarrinhoCompra(
                    new Produto(1L, "Pão integral","Pão Integral", new BigDecimal("10.00")),1);
            ItemCarrinhoCompra item2 = new ItemCarrinhoCompra(
                    new Produto(2L, "Suco de Laranja","Suco de Laranja 1,5L", new BigDecimal("10.00")),2);

            itens.add(item1);
            itens.add(item2);

            carrinhoCompra = new CarrinhoCompra(cliente, itens);

        }

        // [A]ctive: Executando o cenário;
        @Nested
        @DisplayName("->Condições: Quando adicionar produto com quantidade 1")
        class AdicionarProduto {

            private Produto produto = new Produto(
                    3L,
                    "Leite Integral",
                    "Leite Integral",
                    new BigDecimal("7.00")
            );

            // [A]ssert: Validar se o cenário está correto;
            @Test
            @DisplayName("Então não deve lançar Exception")
            void naoDeveLancarSaqueSemException() {
                assertDoesNotThrow(()-> carrinhoCompra.adicionarProduto(produto, 1));
            }

            // [A]ssert: Validar se o cenário está correto;
            @Test
            @DisplayName("Então quantidade do produto deve ser maior que zero")
            void quantidadeItemDoProdutoDeveSerMaiorQueZero()  {

                int indice = 2;
                carrinhoCompra.adicionarProduto(produto, 1);
                assertEquals(1, carrinhoCompra.getItens().get(indice).getQuantidade());
            }

            @Test
            @DisplayName("Então deve incrementar a quantidade total dos itens")
            void deveIncrementarQuantidadeTotalDosProdutos()  {

                carrinhoCompra.adicionarProduto(produto, 1);
                assertEquals(4, carrinhoCompra.getQuantidadeTotalDeProdutos());
            }

            @Test
            @DisplayName("Então deve incrementar a quantidade no item caso o produto já exista")
            void deveIncrementarQuantidadeDoItemQuandoJaExiste()  {

                int indice = 1;

                Produto produto = new Produto(2L,
                        "Suco de Laranja",
                        "Suco de Laranja 1,5L",
                        new BigDecimal("10.00")
                );

                carrinhoCompra.adicionarProduto(produto, 5);
                assertEquals(7, carrinhoCompra.getItens().get(indice).getQuantidade());
            }
        }

    }

    @Nested
    @DisplayName("[Cenário]-Dado um carrinho de compra com 0 itens")
    class CarrinhoCompraSemItens {

        private CarrinhoCompra carrinhoCompra;
        private Cliente cliente;
        private List<ItemCarrinhoCompra> itens = new ArrayList<>();

        // [A]rrange: Preparar as variáveis para o senário de teste;
        @BeforeEach
        @DisplayName("Quando")
        void beforeEach() {

            System.out.println("Antes de cada teste CarrinhoCompraSemItens");

            cliente = new Cliente(1L, "Alex");
            carrinhoCompra = new CarrinhoCompra(cliente, itens);

        }

        // [A]ctive: Executando o cenário;
        @Nested
        @DisplayName("->Condições: Quando adicionar produto com quantidade 1")
        class AdicionarProduto {

            // [A]ssert: Validar se o cenário está correto;
            @Test
            @DisplayName("Então deve lançar Exception")
            void naoDeveLancarSaqueSemException() {

                assertThrows(RuntimeException.class,
                        ()-> carrinhoCompra.adicionarProduto(null, 0));
            }

        }

    }

}