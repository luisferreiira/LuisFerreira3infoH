<%@ page contentType="text/html; charset=Latin1" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
    <head>
        <meta charset="Latin1">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>Cadastro de Eventos</title>
        <link rel="stylesheet" href="${pageContext.request.contextPath}/estilo/cadastro.css">
    </head>
    <body>
        <header>
            <%@ include file="menuParque.jsp" %>
        </header>
        <main>
            <h2>Cadastro de Eventos</h2>
            <form action="${pageContext.request.contextPath}${URL_BASE}/EventosControlador"
                  method="get">
                <!-- Campo oculto para código do evento e operação (cadastrar, editar, etc.) -->
                <input type="hidden" name="codEventos" value="${codEventos}">
                <input type="hidden" name="opcao" value="${opcao != null ? opcao : 'cadastrar'}">

                <!-- Nome do Evento -->
                <label for="nomeEvento">Nome do Evento:</label><br>
                <input type="text" id="nomeEvento" name="nomeEvento" value="${nomeEvento}" size="40" required><br><br>

                <!-- Tipo do Evento -->
                <label for="tipoEvento">Tipo do Evento:</label><br>
                <input type="text" id="tipoEvento" name="tipoEvento" value="${tipoEvento}" size="40" required><br><br>

                <!-- Local do Evento -->
                <label for="localEvento">Local do Evento:</label><br>
                <input type="text" id="localEvento" name="localEvento" value="${localEvento}" size="40"><br><br>

                <!-- Data de Início do Evento -->
                <label for="dataInicio">Data Início do Evento:</label><br>
                <input type="datetime-local" id="dataInicio" name="dataInicio" value="${dataInicio}" size="40" required><br><br>

                <!-- Data Final do Evento -->
                <label for="dataFim">Data Fim do Evento:</label><br>
                <input type="datetime-local" id="dataFim" name="dataFim" value="${dataFim}" size="40"required><br><br>

                <!-- Descrição do Evento -->
                <label for="descricao">Descrição:</label><br>
                <input type="text" id="descricao" name="descricao" value="${descricao}" size="60"><br><br>

                <!-- Mensagem de feedback (caso exista) -->
                <p style="color: #0066cc;">${mensagem}</p>

                <!-- Botão de ação (Cadastrar ou Atualizar) -->
                <button type="submit" name="opcao" value="${opcao == 'confirmarEditar' ? 'confirmarEditar' : (opcao == 'confirmarExcluir' ? 'confirmarExcluir' : 'cadastrar')}">
                    ${opcao == 'confirmarEditar' ? 'Editar' : (opcao == 'confirmarExcluir' ? 'Excluir' : 'Cadastrar')}
                </button>

                <!-- Botão de Cancelar -->
                <a href="${pageContext.request.contextPath}${URL_BASE}/EventosControlador?opcao=cancelar">
                    <button id="btnCancelar" class="botao-cancelar" type="button">Cancelar</button>
                </a>

                <a href="${pageContext.request.contextPath}${URL_BASE}/EventosControlador?opcao=listar">
                    <button class="botao-listar" type="button">Listar Tudo</button>
                </a>
            </form>

            <hr>

            <h2>Lista de Eventos</h2>
            
            <div class="aviso-mobile-tabela">
                <p>A listagem detalhada está disponível apenas em telas maiores (tablets ou desktops) para garantir a visualização completa de todos os dados.</p>
                <p style="margin-top: 10px;">Por favor, gire seu dispositivo ou use um monitor maior.</p>
            </div>
            
            <table class="tabela-categorias">
                <tr>
                    <th>Código</th>
                    <th>Evento</th>
                    <th>Tipo</th>
                    <th>Local</th>
                    <th>Início</th>
                    <th>Fim</th>
                    <th>Descrição</th>
                    <th>Ações</th>
                </tr>
                <c:forEach var="eventos" items="${listaEventos}">
                    <tr>
                        <td>${eventos.codEventos}</td>
                        <td>${eventos.nomeEvento}</td>
                        <td>${eventos.tipoEvento}</td>
                        <td>${eventos.localEvento}</td>
                        <td>${eventos.dataInicioFormatada}</td>
                        <td>${eventos.dataFimFormatada}</td>
                        <td>${eventos.descricao}</td>
                        <td class="td-acoes">
                            <!-- Botão para editar -->
                            <form style="margin:0;" action="${pageContext.request.contextPath}${URL_BASE}/EventosControlador" method="get">
                                <input type="hidden" name="opcao" value="editar">
                                <input type="hidden" name="codEventos" value="${eventos.codEventos}">
                                <input type="hidden" name="nomeEvento" value="${eventos.nomeEvento}">
                                <input type="hidden" name="tipoEvento" value="${eventos.tipoEvento}">
                                <input type="hidden" name="localEvento" value="${eventos.localEvento}">
                                <input type="hidden" name="dataInicio" value="${eventos.dataInicio}">
                                <input type="hidden" name="dataFim" value="${eventos.dataFim}">
                                <input type="hidden" name="descricao" value="${eventos.descricao}">
                                <button class="botao-editar" type="submit">Editar</button>
                            </form>

                            <!-- Botão para excluir -->
                            <form style="margin:0;" action="${pageContext.request.contextPath}${URL_BASE}/EventosControlador" method="get">
                                <input type="hidden" name="opcao" value="excluir">
                                <input type="hidden" name="codEventos" value="${eventos.codEventos}">
                                <input type="hidden" name="nomeEvento" value="${eventos.nomeEvento}">
                                <input type="hidden" name="tipoEvento" value="${eventos.tipoEvento}">
                                <input type="hidden" name="localEvento" value="${eventos.localEvento}">
                                <input type="hidden" name="dataInicio" value="${eventos.dataInicio}">
                                <input type="hidden" name="dataFim" value="${eventos.dataFim}">
                                <input type="hidden" name="descricao" value="${eventos.descricao}">
                                <button class="botao-excluir" type="submit">Excluir</button>
                            </form>
                        </td>
                    </tr>
                </c:forEach>
            </table>
        </main>
        <footer>
            <p>&copy; 2025 Parque de Diversões. Todos os direitos reservados.</p>
        </footer>
    </body>
</html>
