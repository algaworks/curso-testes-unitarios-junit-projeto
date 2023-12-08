package com.algaworks.junit.blog.negocio;

import com.algaworks.junit.blog.modelo.Editor;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;

@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
class CadastroEditorTest {

    static CadastroEditor cadastroEditor;
    Editor editor;

    //Executa antes de todos os testes
    @BeforeAll
    static void beforeAll() {

        System.out.println("@BeforeAll: Antes de todos os testes");

        cadastroEditor = new CadastroEditor(
            new ArmazenamentoEditorFixoEmMemoria(),
            new GerenciadorEnvioEmail() {

                @Override
                void enviarEmail(Mensagem mensagem) {
                    System.out.println("Enviando mensagem para: "+mensagem.getDestinatario());
                }
            }
        );
    }

    // Preparar as variáveis para o senário de teste;
    @BeforeEach
    void beforeEach() {

        // [A]rrange: Preparar as variáveis para o senário de teste;
        System.out.println("@BeforeEach: Executa antes de cada teste");

      editor = new Editor();
    }

}