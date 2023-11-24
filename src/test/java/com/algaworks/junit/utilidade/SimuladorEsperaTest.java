package com.algaworks.junit.utilidade;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.time.Duration;

import static org.junit.jupiter.api.Assertions.*;

class SimuladorEsperaTest {

    @Test
    public void deveEsperarENaoDarTimeout() {

        // Assegura que este método deveria ser executado em 1 segundo
        // Esse é o melhor método para validar o tempo de executação, porque não deixa bloqueado por 10 segundos
        // chegou no 1 segundo conforme foi configurado já lança o erro de teste
        Assertions.assertTimeoutPreemptively(Duration.ofSeconds(1), ()-> SimuladorEspera.esperar(Duration.ofMillis(10)));
    }

}