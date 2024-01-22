package com.algaworks.junit.blog.negocio;

import com.algaworks.junit.blog.armazenamento.ArmazenamentoPost;
import com.algaworks.junit.blog.modelo.Editor;
import com.algaworks.junit.blog.modelo.Ganhos;
import com.algaworks.junit.blog.modelo.Notificacao;
import com.algaworks.junit.blog.modelo.Post;
import com.algaworks.junit.blog.utilidade.ConversorSlug;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.Optional;

@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
// Esta anotação permite a utilização do Mockito nos testes, facilitando a criação de objetos falsos (mocks) para simulação.
@ExtendWith(MockitoExtension.class)
// Classe de testes para o sistema de cadastro de posts.
class CadastroPostTest {

    @Mock
    // Mock do sistema de armazenamento de posts.
    ArmazenamentoPost armazenamentoPost;

    @Mock
    // Mock da calculadora de ganhos.
    CalculadoraGanhos calculadoraGanhos;

    @Mock
    // Mock do gerenciador de notificações.
    GerenciadorNotificacao gerenciadorNotificacao;

    @InjectMocks
    // Instância da classe de cadastro de posts, com os mocks injetados.
    CadastroPost cadastroPost;

    @Captor
    // Captor de argumentos para verificar as notificações enviadas.
    ArgumentCaptor<Notificacao> notificacaoArgumentCaptor;

    @Spy
    // Espião do objeto 'Editor' para monitorar suas interações.
    Editor editor = EditorTestData.umEditorExistente().build();

    @Nested
    // Classe aninhada para testar a funcionalidade de cadastro de posts.
    public final class Cadastro {

        @Spy
        // Espião do objeto 'Post' para monitorar suas interações durante os testes de cadastro.
        Post post = CadastroPostTestData.umPostNovo().build();

        // Métodos de teste para diferentes cenários de cadastro de posts:

        @Test
        public void Dado_um_post_valido__Quanto_cadastrar__Entao_deve_salvar() {

            // Configura um comportamento simulado (mock) para o método 'salvar' de 'armazenamentoPost'.
            // 'Mockito.any(Post.class)' é usado para indicar que este mock se aplica a qualquer instância de 'Post'.
            Mockito.when(armazenamentoPost.salvar(Mockito.any(Post.class)))
                    .then(invocacao -> {

                        // Recupera o argumento passado para o método 'salvar' - neste caso, o objeto 'Post' enviado.
                        Post postEnviado = invocacao.getArgument(0, Post.class);

                        // Define o ID do 'postEnviado' como 1L. Isso simula o comportamento de um método 'salvar' real,
                        // que normalmente atribuiria um ID ao post quando salvo no banco de dados.
                        postEnviado.setId(1L);

                        // Retorna o 'postEnviado' modificado, simulando a resposta do método 'salvar'.
                        return postEnviado;

                    });

            // Chama o método 'criar' do objeto 'cadastroPost', passando o objeto 'post' como argumento.
            // Este é o método que está sendo testado.
            cadastroPost.criar(post);

            // Verifica se o método 'salvar' do mock 'armazenamentoPost' foi chamado exatamente uma vez
            // durante a execução do teste. Isso assegura que o post foi "salvo" conforme esperado.
            Mockito.verify(armazenamentoPost, Mockito.times(1)).salvar(Mockito.any(Post.class));

        }

        @Test
        // Testa se, após o cadastro de um post válido, um ID válido é retornado.
        public void Dado_um_post_valido__Quanto_cadastrar__Entao_deve_retornar_id_valido() {

            Mockito.when(armazenamentoPost.salvar(Mockito.any(Post.class)))
                    .then(invocacao -> {
                        // Configura o comportamento do mock 'armazenamentoPost' para o método 'salvar'.
                        // Aqui, ele é configurado para responder a qualquer objeto 'Post' passado.

                        Post postEnviado = invocacao.getArgument(0, Post.class);
                        // Obtém o objeto 'Post' que foi passado para o método 'salvar'.

                        postEnviado.setId(1L);
                        // Define um ID para o post. Neste caso, estamos simulando que o post recebeu o ID 1L
                        // após ser salvo, o que normalmente aconteceria em um banco de dados real.

                        return postEnviado;
                        // Retorna o post com o ID definido, simulando a resposta de um método 'salvar' real.
                    });

            Post postSalvo = cadastroPost.criar(post);
            // Executa o método 'criar' da classe CadastroPost, passando o objeto 'post' como argumento,
            // e armazena o resultado em 'postSalvo'. Este é o objeto 'Post' que supostamente foi salvo.

            Assertions.assertEquals(1L, postSalvo.getId());
            // Verifica se o ID do 'postSalvo' é igual a 1L, conforme esperado. Isso confirma que
            // o método 'criar' está retornando um objeto 'Post' com o ID correto.
        }


        @Test
        //Ao cadastrar um post válido, os ganhos são calculados e retornados corretamente.
        public void Dado_um_post_valido__Quanto_cadastrar__Entao_deve_retornar_post_com_ganhos() {

            Mockito.when(armazenamentoPost.salvar(Mockito.any(Post.class)))
                    .then(invocacao -> {
                        // Configura o comportamento simulado do método 'salvar' do 'armazenamentoPost'.
                        // Este comportamento será aplicado para qualquer 'Post' passado como argumento.

                        Post postEnviado = invocacao.getArgument(0, Post.class);
                        // Obtém o 'Post' passado para o método 'salvar'.

                        postEnviado.setId(1L);
                        // Define um ID para o post, simulando o que aconteceria em um cenário real onde
                        // o post é salvo em um banco de dados e recebe um ID.

                        return postEnviado;
                        // Retorna o post modificado, simulando o comportamento de um repositório real.
                    });

            Mockito.when(calculadoraGanhos.calcular(post))
                    .thenReturn(new Ganhos(BigDecimal.TEN, 4, BigDecimal.valueOf(40)));
            // Configura o comportamento simulado do método 'calcular' da 'calculadoraGanhos'.
            // Quando chamado com o objeto 'post', retorna um objeto 'Ganhos' simulado.

            Post postSalvo = cadastroPost.criar(post);
            // Chama o método 'criar' da classe 'CadastroPost', passando o 'post' como argumento.
            // 'postSalvo' é o objeto 'Post' retornado por esse método, supostamente após ser salvo e ter seus ganhos calculados.

            Mockito.verify(post, Mockito.times(1)).setGanhos(Mockito.any(Ganhos.class));
            // Verifica se o método 'setGanhos' foi chamado exatamente uma vez no objeto 'post'.
            // Isso assegura que os ganhos foram efetivamente calculados e atribuídos ao post.

            Assertions.assertNotNull(postSalvo.getGanhos());
            // Verifica se o 'postSalvo' tem um objeto 'Ganhos' não nulo, confirmando que os ganhos foram de fato calculados e atribuídos.
        }


        @Test
        // Teste para verificar se um slug é gerado e atribuído corretamente ao cadastrar um post válido.
        // Em testes unitários, o foco está em garantir que o slug, que é um identificador único derivado do título do post,
        // é criado e associado corretamente ao objeto 'Post' durante o processo de cadastro.
        public void Dado_um_post_valido__Quanto_cadastrar__Entao_deve_retornar_post_com_slug() {


            Mockito.when(armazenamentoPost.salvar(Mockito.any(Post.class)))
                    .then(invocacao -> {
                        // Configura o comportamento do mock 'armazenamentoPost' para o método 'salvar'.
                        // Este mock vai responder a qualquer instância de 'Post' passada como argumento.

                        Post postEnviado = invocacao.getArgument(0, Post.class);
                        // Extrai o objeto 'Post' enviado para o método 'salvar'.

                        postEnviado.setId(1L);
                        // Define um ID para o post, simulando o comportamento de atribuição de ID após salvar no banco de dados.

                        return postEnviado;
                        // Retorna o post modificado, simulando a resposta do método 'salvar' em um repositório real.
                    });

            Post postSalvo = cadastroPost.criar(post);
            // Executa o método 'criar' da classe 'CadastroPost', passando o objeto 'post' como argumento.
            // 'postSalvo' é o objeto 'Post' retornado, que teoricamente foi salvo e teve um slug gerado.

            Mockito.verify(post, Mockito.times(1)).setSlug(Mockito.anyString());
            // Verifica se o método 'setSlug' foi chamado exatamente uma vez no objeto 'post'.
            // Isso assegura que o slug foi gerado e atribuído ao post durante o processo de cadastro.

            Assertions.assertNotNull(postSalvo.getSlug());
            // Confirma se o 'postSalvo' possui um slug não nulo, indicando que o slug foi de fato gerado e associado ao post.
        }

        @Test
        // Nome do método seguindo a convenção BDD, indicando que o teste verifica o comportamento
        // do sistema quando um post nulo é passado para o método de cadastro.
        public void Dado_um_post_null__Quanto_cadastrar__Entao_deve_lancar_exception_e_nao_deve_savar() {

            Assertions.assertThrows(NullPointerException.class, ()-> cadastroPost.criar(null));
            // Esta linha de teste verifica se a tentativa de cadastrar um post nulo resulta na
            // geração de uma NullPointerException. Isso é importante para validar que o método 'criar'
            // está lidando corretamente com entradas inválidas, mantendo a robustez do sistema.

            Mockito.verify(armazenamentoPost, Mockito.never()).salvar(Mockito.any(Post.class));
            // Verifica se o método 'salvar' do 'armazenamentoPost' nunca é chamado quando um post nulo é fornecido.
            // Isso assegura que o sistema não está tentando salvar um post nulo no banco de dados,
            // evitando assim erros de execução e mantendo a integridade dos dados.
        }

        @Test
        // Teste para verificar se os ganhos de um post são calculados antes de o post ser salvo.
        // Esse teste garante que a lógica de negócios está sendo seguida corretamente.
        public void Dado_um_post_valido__Quanto_cadastrar__Entao_deve_calcular_ganhos_antes_de_salvar() {

            Mockito.when(armazenamentoPost.salvar(Mockito.any(Post.class)))
                    .then(invocacao -> {
                        // Configura um comportamento simulado para o método 'salvar' do objeto 'armazenamentoPost'.
                        // Responde a qualquer 'Post' passado como argumento.

                        Post postEnviado = invocacao.getArgument(0, Post.class);
                        // Captura o 'Post' que foi passado para o método 'salvar'.

                        postEnviado.setId(1L);
                        // Atribui um ID ao 'postEnviado', simulando o que aconteceria em um banco de dados real.

                        return postEnviado;
                        // Retorna o 'postEnviado' modificado, simulando a ação de salvamento.
                    });

            cadastroPost.criar(post);
            // Chama o método 'criar' do objeto 'cadastroPost', passando 'post' como argumento.
            // Este é o método que está sendo testado.

            InOrder inOrder = Mockito.inOrder(calculadoraGanhos, armazenamentoPost);
            // Cria um objeto 'InOrder' do Mockito para verificar a ordem das chamadas de método nos mocks.

            inOrder.verify(calculadoraGanhos, Mockito.times(1)).calcular(post);
            // Verifica se o método 'calcular' da 'calculadoraGanhos' foi chamado exatamente uma vez.
            // Isso confirma que os ganhos do post foram calculados.

            inOrder.verify(armazenamentoPost, Mockito.times(1)).salvar(post);
            // Verifica se o método 'salvar' do 'armazenamentoPost' foi chamado exatamente uma vez.
            // Isso confirma que o post foi salvo após o cálculo dos ganhos.
        }


        @Test
        // Teste para verificar se o slug do post é gerado antes do processo de salvamento.
        // Este teste garante que o slug, que é um identificador único baseado no título do post,
        // é criado corretamente antes de salvar o post no banco de dados.
        public void Dado_um_post_valido__Quanto_cadastrar__Entao_deve_gerar_slug_salvar() {


            Mockito.when(armazenamentoPost.salvar(Mockito.any(Post.class)))
                    .then(invocacao -> {
                        // Configura o comportamento do mock 'armazenamentoPost' para o método 'salvar'.
                        // Este mock vai responder a qualquer instância de 'Post' passada como argumento.

                        Post postEnviado = invocacao.getArgument(0, Post.class);
                        // Extrai o objeto 'Post' que foi passado para o método 'salvar'.

                        postEnviado.setId(1L);
                        // Define um ID para o post, simulando o que aconteceria em um cenário real.

                        return postEnviado;
                        // Retorna o post modificado, simulando a resposta do método 'salvar' de um repositório real.
                    });

            try (MockedStatic<ConversorSlug> conversorSlug = Mockito.mockStatic(ConversorSlug.class)) {
                // Cria um mock estático para a classe 'ConversorSlug', que é responsável por gerar o slug.

                cadastroPost.criar(post);
                // Chama o método 'criar' da classe 'CadastroPost', passando o objeto 'post' como argumento.

                InOrder inOrder = Mockito.inOrder(armazenamentoPost, ConversorSlug.class);
                // Cria um objeto 'InOrder' do Mockito para verificar a ordem das chamadas de método nos mocks.

                inOrder.verify(conversorSlug, () -> ConversorSlug.converterJuntoComCodigo(post.getTitulo()), Mockito.times(1));
                // Verifica se o método estático 'converterJuntoComCodigo' da classe 'ConversorSlug' foi chamado exatamente uma vez.
                // Isso confirma que o slug foi gerado antes de salvar o post.

                inOrder.verify(armazenamentoPost, Mockito.times(1)).salvar(post);
                // Verifica se o método 'salvar' do 'armazenamentoPost' foi chamado exatamente uma vez,
                // indicando que o post foi salvo após a geração do slug.
            }
        }

        @Test
        // Teste para verificar se uma notificação é enviada após um post válido ser salvo.
        // Isso garante que o sistema de notificações está funcionando como esperado no fluxo de cadastro de posts.
        public void Dado_um_post_valido__Quanto_cadastrar__Entao_deve_enviar_notificacao_apos_salvar() {

            Mockito.when(armazenamentoPost.salvar(Mockito.any(Post.class)))
                    .then(invocacao -> {
                        // Configura um comportamento simulado para o método 'salvar' do objeto 'armazenamentoPost'.
                        // Este comportamento será aplicado a qualquer 'Post' passado como argumento.

                        Post postEnviado = invocacao.getArgument(0, Post.class);
                        // Extrai o objeto 'Post' que foi passado para o método 'salvar'.

                        postEnviado.setId(1L);
                        // Atribui um ID ao 'postEnviado', simulando o que aconteceria em um banco de dados real.

                        return postEnviado;
                        // Retorna o post modificado, simulando a resposta do método 'salvar'.
                    });

            cadastroPost.criar(post);
            // Chama o método 'criar' da classe 'CadastroPost', passando o objeto 'post' como argumento.
            // Este é o passo principal que está sendo testado.

            InOrder inOrder = Mockito.inOrder(gerenciadorNotificacao, armazenamentoPost);
            // Cria um objeto 'InOrder' do Mockito para verificar a ordem das chamadas de método nos mocks.

            inOrder.verify(armazenamentoPost, Mockito.times(1)).salvar(post);
            // Verifica se o método 'salvar' do 'armazenamentoPost' foi chamado exatamente uma vez.

            inOrder.verify(gerenciadorNotificacao, Mockito.times(1)).enviar(Mockito.any(Notificacao.class));
            // Verifica se o método 'enviar' do 'gerenciadorNotificacao' foi chamado exatamente uma vez após salvar o post.
            // Isso confirma que uma notificação é enviada como parte do fluxo de cadastro do post.
        }

        @Test
        // Teste para verificar se a notificação gerada após o cadastro de um post contém o título do post.
        // Isso é importante para garantir que as notificações sejam informativas e relevantes.
        public void Dado_um_post_valido__Quanto_cadastrar__Entao_deve_gerar_notificacao_com_titulo_do_post() {


            Mockito.when(armazenamentoPost.salvar(Mockito.any(Post.class)))
                    .then(invocacao -> {
                        // Configura um comportamento simulado para o método 'salvar' do objeto 'armazenamentoPost'.
                        // Este comportamento será aplicado a qualquer 'Post' passado como argumento.

                        Post postEnviado = invocacao.getArgument(0, Post.class);
                        // Extrai o objeto 'Post' que foi passado para o método 'salvar'.

                        postEnviado.setId(1L);
                        // Atribui um ID ao 'postEnviado', simulando o que aconteceria em um banco de dados real.

                        return postEnviado;
                        // Retorna o post modificado, simulando a resposta do método 'salvar'.
                    });

            cadastroPost.criar(post);
            // Chama o método 'criar' da classe 'CadastroPost', passando o objeto 'post' como argumento.
            // Este é o passo principal que está sendo testado.

            Mockito.verify(gerenciadorNotificacao)
                    .enviar(notificacaoArgumentCaptor.capture());
            // Verifica se o método 'enviar' do 'gerenciadorNotificacao' foi chamado e captura o argumento passado.

            Notificacao notificacao = notificacaoArgumentCaptor.getValue();
            // Recupera a notificação que foi capturada pelo 'notificacaoArgumentCaptor'.

            Assertions.assertEquals("Novo post criado -> " + post.getTitulo(), notificacao.getConteudo());
            // Verifica se o conteúdo da notificação capturada corresponde ao esperado, ou seja,
            // se contém o título do post, confirmando que a notificação é sobre o post recém-criado.
        }

    }

    @Nested
    // Classe aninhada para testar a funcionalidade de edição de posts.
    public final class Edicao {

        @Spy
        // Espião do objeto 'Post' para monitorar suas interações durante os testes de edição.
        Post post = CadastroPostTestData.umPostExistente().build();

        // Métodos de teste para diferentes cenários de edição de posts:

        @Test
        // Teste para verificar se um post válido é salvo corretamente ao editar.
        // Este teste assegura que o método de edição está funcionando conforme esperado,
        // salvando efetivamente as alterações feitas no post.
        public void Dado_um_post_valido__Quando_editar__Entao_deve_salvar() {

            Mockito.when(armazenamentoPost.salvar(Mockito.any(Post.class)))
                    .then(invocacao -> invocacao.getArgument(0, Post.class));
            // Configura um comportamento simulado para o método 'salvar' do 'armazenamentoPost'.
            // Este comportamento devolve o objeto 'Post' passado para o método, sem modificações adicionais.

            Mockito.when(armazenamentoPost.encontrarPorId(1L)).thenReturn(Optional.ofNullable(post));
            // Configura um comportamento simulado para o método 'encontrarPorId' do 'armazenamentoPost'.
            // Quando chamado com o ID 1L, retorna um 'Optional' contendo o 'post'.

            cadastroPost.editar(post);
            // Chama o método 'editar' da classe 'CadastroPost', passando o objeto 'post' como argumento.
            // Este é o passo principal que está sendo testado.

            Mockito.verify(armazenamentoPost, Mockito.times(1)).salvar(Mockito.any(Post.class));
            // Verifica se o método 'salvar' do 'armazenamentoPost' foi chamado exatamente uma vez.
            // Isso confirma que o post foi salvo após a edição.
        }

        @Test
        // Teste para garantir que o ID de um post válido permanece o mesmo após a edição.
        // Este teste é crucial para assegurar a consistência dos dados, pois o ID de um post
        // não deve mudar após ser editado.
        public void Dado_um_post_valido__Quando_editar__Entao_deve_retornar_mesmo_id() {

            Mockito.when(armazenamentoPost.salvar(Mockito.any(Post.class)))
                    .then(invocacao -> invocacao.getArgument(0, Post.class));
            // Configura um comportamento simulado para o método 'salvar' do 'armazenamentoPost'.
            // Quando o método 'salvar' é chamado, simplesmente retorna o objeto 'Post' fornecido.

            Mockito.when(armazenamentoPost.encontrarPorId(1L)).thenReturn(Optional.ofNullable(post));
            // Configura um comportamento simulado para o método 'encontrarPorId' do 'armazenamentoPost'.
            // Quando chamado com o ID 1L, retorna um 'Optional' contendo o 'post' existente.

            Post postSalvo = cadastroPost.editar(post);
            // Chama o método 'editar' da classe 'CadastroPost', passando o objeto 'post' como argumento.
            // 'postSalvo' é o objeto 'Post' que é retornado após a edição.

            Assertions.assertEquals(1L, postSalvo.getId());
            // Verifica se o ID do 'postSalvo' é igual ao ID original (1L).
            // Isso confirma que o ID permaneceu inalterado após a edição.
        }

        @Test
        // Teste para verificar se, ao editar um post que já é pago, os ganhos previamente calculados não são recalculados.
        // Isso garante que a lógica de negócios para posts pagos está sendo seguida corretamente.
        public void Dado_um_post_pago__Quando_editar__Entao_deve_retornar_post_com_os_mesmos_ganhos_sem_recalcular() {

            post.setConteudo("Conteúdo editado");
            // Atualiza o conteúdo do 'post' para simular uma edição.

            Mockito.when(armazenamentoPost.salvar(Mockito.any(Post.class)))
                    .then(invocacao -> invocacao.getArgument(0, Post.class));
            // Configura um comportamento simulado para o método 'salvar' do 'armazenamentoPost'.
            // Retorna o 'Post' passado como argumento.

            Mockito.when(armazenamentoPost.encontrarPorId(1L)).thenReturn(Optional.ofNullable(post));
            // Configura um comportamento simulado para o método 'encontrarPorId' do 'armazenamentoPost'.
            // Retorna um 'Optional' contendo o 'post' quando chamado com o ID 1L.

            Post postSalvo = cadastroPost.editar(post);
            // Chama o método 'editar' da classe 'CadastroPost', passando o objeto 'post' como argumento.
            // 'postSalvo' é o objeto 'Post' que é retornado após a edição.

            Mockito.verify(post, Mockito.never()).setGanhos(Mockito.any(Ganhos.class));
            // Verifica se o método 'setGanhos' nunca foi chamado no objeto 'post'.
            // Isso confirma que os ganhos não foram recalculados durante a edição.

            Mockito.verify(post, Mockito.times(1)).isPago();
            // Verifica se o método 'isPago' foi chamado exatamente uma vez no objeto 'post'.
            // Isso é usado para confirmar que a lógica de verificação do status de pagamento foi executada.

            Assertions.assertNotNull(postSalvo.getGanhos());
            // Confirma que o 'postSalvo' ainda possui um objeto 'Ganhos' após a edição,
            // garantindo que os ganhos originais permaneceram inalterados.
        }

        @Test
        // Teste para verificar se, ao editar um post não pago, os ganhos são recalculados antes de salvar.
        // Isso assegura que a lógica de negócio para posts não pagos está sendo seguida corretamente,
        // recalculando os ganhos com base nas novas informações do post.
        public void Dado_um_post_nao_pago__Quando_editar__Entao_deve_retornar_recalcular_ganhos_antes_de_salvar() {

            post.setConteudo("Conteúdo editado");
            // Atualiza o conteúdo do 'post' para simular uma edição real.

            post.setPago(false);
            // Define o post como não pago, o que deve acionar o recálculo de ganhos.

            Ganhos novoGanho = new Ganhos(BigDecimal.TEN, 2, BigDecimal.valueOf(20));
            // Cria um novo objeto 'Ganhos' para simular o resultado esperado do recálculo.

            Mockito.when(armazenamentoPost.salvar(Mockito.any(Post.class))).then(invocacao -> invocacao.getArgument(0, Post.class));
            // Configura um comportamento simulado para o método 'salvar' do 'armazenamentoPost'.
            // Retorna o objeto 'Post' passado como argumento.

            Mockito.when(armazenamentoPost.encontrarPorId(1L)).thenReturn(Optional.ofNullable(post));
            // Configura um comportamento simulado para o método 'encontrarPorId' do 'armazenamentoPost'.
            // Retorna um 'Optional' contendo o 'post' quando chamado com o ID 1L.

            Mockito.when(calculadoraGanhos.calcular(post)).thenReturn(novoGanho);
            // Configura um comportamento simulado para o método 'calcular' da 'calculadoraGanhos'.
            // Retorna 'novoGanho' quando chamado com o 'post'.

            Post postSalvo = cadastroPost.editar(post);
            // Chama o método 'editar' da classe 'CadastroPost', passando o objeto 'post' como argumento.
            // 'postSalvo' é o objeto 'Post' que é retornado após a edição.

            Mockito.verify(post, Mockito.times(1)).setGanhos(novoGanho);
            // Verifica se o método 'setGanhos' foi chamado exatamente uma vez no objeto 'post'
            // com o 'novoGanho', confirmando que os ganhos foram recalculados e atualizados.

            Assertions.assertNotNull(postSalvo.getGanhos());
            // Confirma que o 'postSalvo' tem um objeto 'Ganhos' não nulo após a edição.

            Assertions.assertEquals(novoGanho, postSalvo.getGanhos());
            // Confirma que os ganhos do 'postSalvo' são iguais ao 'novoGanho',
            // indicando que os ganhos foram corretamente recalculados e atualizados.

            InOrder inOrder = Mockito.inOrder(calculadoraGanhos, armazenamentoPost);
            // Cria um objeto 'InOrder' do Mockito para verificar a ordem das chamadas de método nos mocks.

            inOrder.verify(calculadoraGanhos, Mockito.times(1)).calcular(post);
            // Verifica se o método 'calcular' da 'calculadoraGanhos' foi chamado exatamente uma vez,
            // indicando que os ganhos foram recalculados.

            inOrder.verify(armazenamentoPost, Mockito.times(1)).salvar(post);
            // Verifica se o método 'salvar' do 'armazenamentoPost' foi chamado após o recálculo dos ganhos,
            // garantindo que o post foi salvo com os ganhos atualizados.
        }

        @Test
        // Teste para assegurar que o slug de um post não é alterado mesmo se o título do post for modificado.
        // Isso é importante para manter a consistência dos URLs e para SEO, já que mudanças frequentes no slug podem prejudicar a rastreabilidade do post.
        public void Dado_um_post_com_titulo_alterado__Quando_editar__Entao_deve_retornar_post_com_a_mesma_slug_sem_alterar() {

            //post.setTitulo("Ola Teste");
            // Altera o título do 'post' para simular uma edição real.

            Mockito.when(armazenamentoPost.salvar(Mockito.any(Post.class))).then(invocacao -> invocacao.getArgument(0, Post.class));
            // Configura um comportamento simulado para o método 'salvar' do 'armazenamentoPost'.
            // Retorna o objeto 'Post' passado como argumento.

            Mockito.when(armazenamentoPost.encontrarPorId(1L)).thenReturn(Optional.ofNullable(post));
            // Configura um comportamento simulado para o método 'encontrarPorId' do 'armazenamentoPost'.
            // Retorna um 'Optional' contendo o 'post' quando chamado com o ID 1L.

            Post postSalvo = cadastroPost.editar(post);
            // Chama o método 'editar' da classe 'CadastroPost', passando o objeto 'post' como argumento.
            // 'postSalvo' é o objeto 'Post' que é retornado após a edição.

            Mockito.verify(post, Mockito.never()).setSlug(Mockito.anyString());
            // Verifica se o método 'setSlug' nunca foi chamado no objeto 'post'.
            // Isso confirma que o slug não foi alterado durante o processo de edição.

            Assertions.assertEquals("ola-mundo-java", postSalvo.getSlug());
            // Confirma que o slug do 'postSalvo' permaneceu como "ola-mundo-java",
            // indicando que o slug não mudou apesar da alteração do título.
        }

        @Test
        // Teste para garantir que uma exceção é lançada se um post nulo for passado para o método de edição.
        // Isso é importante para assegurar que o sistema trata adequadamente casos de entrada inválida,
        // evitando erros de execução e mantendo a integridade dos dados.
        public void Dado_um_post_null__Quando_editar__Entao_deve_lancar_exception_e_nao_deve_savar() {

            Assertions.assertThrows(NullPointerException.class, ()-> cadastroPost.editar(null));
            // Verifica se a tentativa de editar um post nulo resulta na geração de uma NullPointerException.
            // Este é um método padrão do JUnit para testar se uma exceção específica é lançada.

            Mockito.verify(armazenamentoPost, Mockito.never()).salvar(Mockito.any(Post.class));
            // Verifica se o método 'salvar' do 'armazenamentoPost' nunca é chamado quando um post nulo é fornecido.
            // Isso garante que o sistema não está tentando salvar um post nulo no banco de dados,
            // o que poderia levar a erros de execução ou problemas de integridade dos dados.
        }

        @Test
        // Teste para verificar se um post válido é atualizado corretamente no sistema ao ser editado.
        // Este teste assegura que as alterações feitas em um post são refletidas corretamente após a edição.
        public void Dado_um_post_valido__Quando_editar__Entao_deve_deve_alterar_post_salvo() {

            Post postAlterado = CadastroPostTestData.umPostExistente()
                    .comTitulo("Olá Java")
                    .comConteudo("Olá Java")
                    .build();
            // Cria uma nova instância de 'Post' para simular os dados alterados de um post existente.

            Mockito.when(armazenamentoPost.salvar(Mockito.any(Post.class)))
                    .then(invocacao -> invocacao.getArgument(0, Post.class));
            // Configura um comportamento simulado para o método 'salvar' do 'armazenamentoPost'.
            // Retorna o objeto 'Post' passado como argumento.

            Mockito.when(armazenamentoPost.encontrarPorId(1L)).thenReturn(Optional.ofNullable(post));
            // Configura um comportamento simulado para o método 'encontrarPorId' do 'armazenamentoPost'.
            // Retorna um 'Optional' contendo o 'post' original quando chamado com o ID 1L.

            cadastroPost.editar(postAlterado);
            // Chama o método 'editar' da classe 'CadastroPost', passando o objeto 'postAlterado' como argumento.

            Mockito.verify(post).atualizarComDados(postAlterado);
            // Verifica se o método 'atualizarComDados' foi chamado no objeto 'post' original.
            // Isso confirma que os dados do post original foram atualizados com os dados do 'postAlterado'.

            InOrder inOrder = Mockito.inOrder(armazenamentoPost, post);
            // Cria um objeto 'InOrder' do Mockito para verificar a ordem das chamadas de método nos mocks.

            inOrder.verify(post).atualizarComDados(postAlterado);
            // Verifica se o método 'atualizarComDados' foi chamado primeiro no objeto 'post'.

            inOrder.verify(armazenamentoPost).salvar(post);
            // Verifica se o método 'salvar' do 'armazenamentoPost' foi chamado após a atualização dos dados,
            // garantindo que o post atualizado foi salvo no sistema.
        }

    }

}