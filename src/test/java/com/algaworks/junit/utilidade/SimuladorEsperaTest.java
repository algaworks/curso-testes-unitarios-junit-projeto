package com.algaworks.junit.utilidade;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.time.Duration;

import static org.junit.jupiter.api.Assertions.*;

class SimuladorEsperaTest {

    @Test
    public void deveEsperarENaoDarTimeout() {

        // Assegura que este método deveria ser executado em 1 segundo
        // problema desse método é que fica bloqueado por 10 segundos.
        Assertions.assertTimeout(Duration.ofSeconds(1), ()-> SimuladorEspera.esperar(Duration.ofSeconds(10)));
    }

}