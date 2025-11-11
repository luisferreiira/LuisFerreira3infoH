<%@ page contentType="text/html; charset=Latin1" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
    <head>
        <meta charset="Latin1">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>Cadastro de Visitantes</title>
        <link rel="stylesheet" href="${pageContext.request.contextPath}/estilo/cadastro.css">
    </head>
    <body>
        <header>
            <%@ include file="menuParque.jsp" %>
        </header>
        <main>
            <h2>Cadastro de Visitantes</h2>
            <form action="${pageContext.request.contextPath}${URL_BASE}/VisitanteControlador"
                  method="get">
                <!-- Campo oculto para c�digo do evento e opera��o (cadastrar, editar, etc.) -->
                <input type="hidden" name="codVisitante" value="${codVisitante}">
                <input type="hidden" name="opcao" value="${opcao != null ? opcao : 'cadastrar'}">

                <!-- Nome do Visitante -->
                <label for="nomeVisitante">Nome do Visitante:</label><br>
                <input type="text" id="nomeVisitante" name="nomeVisitante" value="${nomeVisitante}" size="40" required><br><br>

                <!-- Data de Nascimento do Visitante -->
                <label for="dataNascimento">Data de Nascimento:</label><br>
                <input type="date" id="dataNascimento" name="dataNascimento" value="${dataNascimento}" required><br><br>

                <!-- Email do Visitante -->
                <label for="email">Email:</label><br>
                <input type="email" id="email" name="email" value="${email}" size="60" required><br><br>

                <!-- Senha do Visitante -->
                <label for="senha">Senha:</label><br>
                <input type="password" id="senha" name="senha" value="${senha}" size="60" required><br><br>

                <!-- Mensagem de feedback (caso exista) -->
                <h3 style="color: #0066cc;">${mensagem}</h3>

                <!-- Bot�o de a��o (Cadastrar ou Atualizar) -->
                <button type="submit" name="opcao" value="${opcao == 'confirmarEditar' ? 'confirmarEditar' : (opcao == 'confirmarExcluir' ? 'confirmarExcluir' : 'cadastrar')}">
                    ${opcao == 'confirmarEditar' ? 'Editar' : (opcao == 'confirmarExcluir' ? 'Excluir' : 'Cadastrar')}
                </button>

                <!-- Bot�o de Cancelar -->
                <a href="${pageContext.request.contextPath}${URL_BASE}/VisitanteControlador?opcao=cancelar">
                    <button id="btnCancelar" class="botao-cancelar" type="button">Cancelar</button>
                </a>

                <a href="${pageContext.request.contextPath}${URL_BASE}/VisitanteControlador?opcao=listar">
                    <button class="botao-listar" type="button">Listar Tudo</button>
                </a>
            </form>

            <c:if test="${not empty mensagemErro}">
                <h2 style="color: red;">${mensagemErro}</h2>
            </c:if>

            <c:if test="${not empty mensagem}">
                <h2 style="color: green;">${mensagem}</h2>
            </c:if>

            <hr>

            <h2>Lista de Visitantes</h2>

            <div class="aviso-mobile-tabela">
                <p>A listagem detalhada est� dispon�vel apenas em telas maiores (tablets ou desktops) para garantir a visualiza��o completa de todos os dados.</p>
                <p style="margin-top: 10px;">Por favor, gire seu dispositivo ou use um monitor maior.</p>
            </div>

            <table class="tabela-categorias">
                <tr>
                    <th>C�digo</th>
                    <th>Visitante</th>
                    <th>Data de Nascimento</th>
                    <th>Email</th>
                    <th>Senha</th>
                    <th>A��es</th>
                </tr>
                <c:forEach var="v" items="${listaVisitante}">
                    <tr>
                        <td>${v.codVisitante}</td>
                        <td>${v.nomeVisitante}</td>
                        <td>${v.dataNascimentoFormatada}</td>
                        <td>${v.email}</td>
                        <td>${v.senha}</td>
                        <td class="td-acoes">
                            <!-- Bot�o para editar -->
                            <form style="margin:0;" action="${pageContext.request.contextPath}${URL_BASE}/VisitanteControlador" method="get">
                                <input type="hidden" name="opcao" value="editar">
                                <input type="hidden" name="codVisitante" value="${v.codVisitante}">
                                <input type="hidden" name="nomeVisitante" value="${v.nomeVisitante}">
                                <input type="hidden" name="dataNascimento" value="${v.dataNascimento}">
                                <input type="hidden" name="email" value="${v.email}">
                                <input type="hidden" name="senha" value="${v.senha}">
                                <button class="botao-editar" type="submit">Editar</button>
                            </form>

                            <!-- Bot�o para excluir -->
                            <form style="margin:0;" action="${pageContext.request.contextPath}${URL_BASE}/VisitanteControlador" method="get">
                                <input type="hidden" name="opcao" value="excluir">
                                <input type="hidden" name="codVisitante" value="${v.codVisitante}">
                                <input type="hidden" name="nomeVisitante" value="${v.nomeVisitante}">
                                <input type="hidden" name="dataNascimento" value="${v.dataNascimento}">
                                <input type="hidden" name="email" value="${v.email}">
                                <input type="hidden" name="senha" value="${v.senha}">
                                <button class="botao-excluir" type="submit">Excluir</button>
                            </form>
                        </td>
                    </tr>
                </c:forEach>
            </table>
        </main>
        <footer>
            <p>&copy; 2025 Parque de Divers�es. Todos os direitos reservados.</p>
        </footer>
    </body>
</html>
