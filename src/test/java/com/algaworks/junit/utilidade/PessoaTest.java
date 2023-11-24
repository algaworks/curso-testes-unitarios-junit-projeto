package com.algaworks.junit.utilidade;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PessoaTest {

    @Test
    public void assercaoAgrupada() {

        Pessoa pessoa = new Pessoa("Alex", "Carvalho");

        assertAll(
                "Asserções de pessoa",
                ()-> assertEquals("Alex", pessoa.getNome()),
                ()-> assertEquals("Silva", pessoa.getSobrenome()));

        // assertAll faz a mesma coisa que o AssertEquals, porém a maior vantagem neste caso que ele executa todos
        // para depois apresentar o erro de ambos, caso ambos estajam com erro, não parando na primeira asserção.

        //assertEquals("Alex", pessoa.getNome());
        //assertEquals("Silva", pessoa.getSobrenome());
    }

}