package com.algaworks.junit.blog.negocio;

import com.algaworks.junit.blog.armazenamento.ArmazenamentoEditor;
import com.algaworks.junit.blog.exception.EditorNaoEncontradoException;
import com.algaworks.junit.blog.exception.RegraNegocioException;
import com.algaworks.junit.blog.modelo.Editor;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
@ExtendWith(MockitoExtension.class)
public class CadastroEditorComMockTest {

    @Captor
    ArgumentCaptor<Mensagem> mensagemArgumentCaptor;

    @Mock // Instanciando de uma forma mockada o objeto
    ArmazenamentoEditor armazenamentoEditor;

    @Mock // Instanciando de uma forma mockada o objeto
    GerenciadorEnvioEmail gerenciadorEnvioEmail;

    // Usando a annotation @InjectMocks, vai ser a dicionado os dois
    // mocks acima para dentro da classe CadastroEditor
    @InjectMocks
    CadastroEditor cadastroEditor;

    @Nested
    class CadastroComEditorValido {

        @Spy
        Editor editor = EditorTestData.umEditorNovo().build();

        @BeforeEach
        void init() {

            // Definindo comportamento do Mock
            // Quando você chamar o editor armazenamentoEditor.salvar
            // Então deve retornar
            Mockito.when(armazenamentoEditor.salvar(Mockito.any(Editor.class)))
                    .thenAnswer(invocacao -> {
                        // Adicionando comportamento dinâmico
                        Editor editorPassado = invocacao.getArgument(0, Editor.class);
                        editorPassado.setId(1L);
                        return editorPassado;

                    });
        }

        @Test
        void Dado_um_editor_valido_Quando_criar_Entao_deve_retornar_um_id_de_cadastro() {

            Editor editorSalvo = cadastroEditor.criar(editor);
            assertEquals(1L, editorSalvo.getId());
        }

        // Verificando chamada de métodos com mock usando Mockito verify
        @Test
        void Dado_um_editor_valido_Quando_criar_Entao_deve_chamar_metodo_salvar_do_armazenamento() {

            cadastroEditor.criar(editor);
            Mockito.verify(armazenamentoEditor, times(1))
                    .salvar(Mockito.eq(editor));
        }

        @Test
        void Dado_um_editor_valido_Quando_criar_e_lancar_exception_ao_salvar_nao_deve_enviar_email() {

            Mockito.when(armazenamentoEditor.salvar(editor))
                    .thenThrow(new RuntimeException());

            assertAll("Não deve enviar e-mail, qunado lançar exception do armazenamento",
                    () -> assertThrows(RuntimeException.class, ()-> cadastroEditor.criar(editor)),
                    () -> Mockito.verify(gerenciadorEnvioEmail, Mockito.never()).enviarEmail(Mockito.any()));
        }

        @Test
        void Dado_um_editor_valido_Quando_cadastrar_Entao_deve_enviar_email_com_destino_ao_editor() {

            Editor editorSalvo = cadastroEditor.criar(editor);
            Mockito.verify(gerenciadorEnvioEmail).enviarEmail(mensagemArgumentCaptor.capture());
            Mensagem mensagem = mensagemArgumentCaptor.getValue();

            assertEquals(editorSalvo.getEmail(), mensagem.getDestinatario());
        }


        @Test
        void Dado_um_editor_valido_Quando_cadastrar_Entao_deve_verificar_o_email() {

            cadastroEditor.criar(editor);
            Mockito.verify(editor, Mockito.atLeast(1)).getEmail();

        }

        @Test
        void Dado_um_editor_com_email_existente_Quando_cadastrar_Entao_deve_lancar_exception() {

            Mockito.when(armazenamentoEditor.encontrarPorEmail("alex@email.com"))
                    .thenReturn(Optional.empty())
                    .thenReturn(Optional.of(editor));

            Editor editorComEmailExistente = EditorTestData.umEditorNovo().build();

            cadastroEditor.criar(editor);
            assertThrows(RegraNegocioException.class, ()-> cadastroEditor.criar(editorComEmailExistente));
        }

        @Test
        void Dado_um_editor_com_email_valido_Quando_cadastrar_Entao_deve_enviar_email_apos_salvar() {

            cadastroEditor.criar(editor);

            InOrder inOrder = Mockito.inOrder(armazenamentoEditor, gerenciadorEnvioEmail);
            inOrder.verify(armazenamentoEditor, times(1)).salvar(editor);
            inOrder.verify(gerenciadorEnvioEmail, times(1)).enviarEmail(Mockito.any(Mensagem.class));
        }
    }


    @Nested
    // Esta anotação indica que a classe interna a seguir é uma classe de teste aninhada.
    // Classes aninhadas em testes JUnit são usadas para organizar testes relacionados em grupos lógicos.
    class CadastroComEditorNull {

        // Este é um método de teste seguindo a convenção de nomenclatura BDD (Behavior-Driven Development).
        // O nome descreve o comportamento esperado: "Dado um editor null, Quando cadastrar, Então deve lançar exception".
        @Test
        void Dado_um_editor_null_Quando_cadastrar_Entao_deve_lancar_exception() {

            // Esta linha verifica se uma NullPointerException é lançada ao tentar criar um 'editor' com um valor null.
            // É um método de asserção do JUnit que espera uma exceção específica.
            Assertions.assertThrows(NullPointerException.class, () -> cadastroEditor.criar(null));

            // Aqui, usamos o Mockito para verificar se o método 'salvar' nunca é chamado no 'armazenamentoEditor'.
            // 'Mockito.never()' assegura que o método não foi invocado nenhuma vez.
            Mockito.verify(armazenamentoEditor, Mockito.never()).salvar(Mockito.any());

            // Similarmente, esta linha verifica se o método 'enviarEmail' nunca é chamado no 'gerenciadorEnvioEmail'.
            // Isso garante que nenhuma ação é tomada (como enviar um email) após uma tentativa falha de criar um 'editor'.
            Mockito.verify(gerenciadorEnvioEmail, Mockito.never()).enviarEmail(Mockito.any());

        }
    }

    @Nested
    class EdicaoComEditorValido {

        @Spy
        // A anotação '@Spy' é usada para criar um objeto 'spy' do Mockito.
        // Isso permite que você monitore as chamadas aos métodos reais do objeto enquanto mantém o comportamento original.
        // Esta é a instância do objeto 'Editor' que será usada nos testes.
        Editor editor = EditorTestData.umEditorExistente().build();

        @BeforeEach
        // A anotação '@BeforeEach' indica que o método a seguir será executado antes de cada teste.
        // Este método é chamado antes de cada teste para configurar condições comuns.
        void init() {

            // Configura um comportamento mock para o método 'salvar' de 'armazenamentoEditor'.
            // Quando 'salvar' é chamado com 'editor', ele retorna o próprio 'editor'.
            Mockito.when(armazenamentoEditor.salvar(editor)).thenAnswer(invocacao -> invocacao.getArgument(0, Editor.class));

            // Configura um comportamento mock para o método 'encontrarPorId'.
            // Quando chamado com o ID 1, ele retorna um Optional contendo o 'editor'.
            Mockito.when(armazenamentoEditor.encontrarPorId(1L)).thenReturn(Optional.of(editor));

        }

        @Test
        void Dado_um_editor_valido_Quando_editar_Entao_deve_alterar_editor_salvo() {

            // Cria uma nova instância de 'Editor' para simular a edição de um editor existente.
            Editor editorAtualizado = EditorTestData.umEditorExistente()
                    .comNome("Alex Silva")
                    .comEmail("alex.silva@email.com")
                    .comValorPagoPorPalavra(BigDecimal.ZERO)
                    .comPremium(false)
                    .build();

            // Chama o método 'editar' do objeto 'cadastroEditor' com o 'editorAtualizado'.
            cadastroEditor.editar(editorAtualizado);

            // Verifica se o método 'atualizarComDados' foi chamado exatamente uma vez no objeto 'editor'.
            Mockito.verify(editor, times(1)).atualizarComDados(editorAtualizado);

            // Cria um objeto 'InOrder' para verificar a ordem das chamadas nos mocks.
            InOrder inOrder = Mockito.inOrder(editor, armazenamentoEditor);

            // Verifica se 'atualizarComDados' foi chamado no 'editor'.
            inOrder.verify(editor).atualizarComDados(editorAtualizado);

            // Verifica se 'salvar' foi chamado em 'armazenamentoEditor' com o objeto 'editor'.
            inOrder.verify(armazenamentoEditor).salvar(editor);

        }
    }


    @Nested
    class EdicaoComEditorInexistente {

        // Cria uma instância de 'Editor' que será usada nos testes.
        // O 'Editor' tem um ID que presumivelmente não existe no sistema (99L).
        Editor editor = EditorTestData.umEditorComIdInexistente().build();

        @BeforeEach
        // Anotação que indica que este método será executado antes de cada teste.
        void init() {

            // Configura um comportamento mock para o método 'encontrarPorId' de 'armazenamentoEditor'.
            // Quando chamado com ID 99L, ele retornará um 'Optional' vazio, simulando que o editor não existe.
            Mockito.when(armazenamentoEditor.encontrarPorId(99L)).thenReturn(Optional.empty());
        }

        @Test
        void Dado_um_editor_que_nao_exista_Quando_editar_Entao_deve_lancar_exception() {

            // Verifica se uma 'EditorNaoEncontradoException' é lançada ao tentar editar um 'editor' que não existe.
            assertThrows(EditorNaoEncontradoException.class, ()-> cadastroEditor.editar(editor));

            // Usa o Mockito para verificar que o método 'salvar' nunca é chamado em 'armazenamentoEditor'.
            // Isso assegura que nenhuma ação de salvar ocorre quando um editor inexistente é editado.
            verify(armazenamentoEditor, never()).salvar(Mockito.any(Editor.class));

        }
    }

}
