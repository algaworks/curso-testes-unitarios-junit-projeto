package com.algaworks.junit.blog.negocio;

import com.algaworks.junit.blog.armazenamento.ArmazenamentoEditor;
import com.algaworks.junit.blog.modelo.Editor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
@ExtendWith(MockitoExtension.class)
public class CadastroEditorComMockTest {

    Editor editor;

    @Mock // Instanciando de uma forma mockada o objeto
    ArmazenamentoEditor armazenamentoEditor;

    @Mock // Instanciando de uma forma mockada o objeto
    GerenciadorEnvioEmail gerenciadorEnvioEmail;

    // Usando a annotation @InjectMocks, vai ser a dicionado os dois
    // mocks acima para dentro da classe CadastroEditor
    @InjectMocks
    CadastroEditor cadastroEditor;

    @BeforeEach
    void init() {

        editor = new Editor(null, "Alex", "alex@email.com", BigDecimal.TEN, true);

        // Definindo comportamento do Mock
        // Quando você chamar o editor armazenamentoEditor.salvar
        // Então deve retornar
        Mockito.when(armazenamentoEditor.salvar(editor))
            .thenReturn(new Editor(1L, "Alex", "alex@email.com", BigDecimal.TEN, true));
    }

    @Test
    void Dado_um_editor_valido_Quando_criar_Entao_deve_retornar_um_id_de_cadastro() {

        Editor editorSalvo = cadastroEditor.criar(editor);
        assertEquals(1L, editorSalvo.getId());
    }

}
