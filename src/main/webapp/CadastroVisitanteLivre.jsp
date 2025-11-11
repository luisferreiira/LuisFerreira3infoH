<%@ page contentType="text/html; charset=Latin1" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
    <head>
        <meta charset="Latin1">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>Cadastro de Visitantes (Livre)</title>
        <link rel="stylesheet" href="${pageContext.request.contextPath}/estilo/cadastro.css">
    </head>
    <body>
        <header>
            <%@ include file="menuParque.jsp" %>
        </header>
        <main>
            <h2>Cadastro de Visitantes (Livre)</h2>

            <form action="${pageContext.request.contextPath}${URL_BASE}/VisitanteControlador"
                  method="get">

                <!-- Campos ocultos -->
                <input type="hidden" name="codVisitante" value="${codVisitante}">
                <input type="hidden" name="opcao" value="${opcao != null ? opcao : 'cadastrarLivre'}">

                <!-- Nome -->
                <label for="nomeVisitante">Nome do Visitante:</label><br>
                <input type="text" id="nomeVisitante" name="nomeVisitante"
                       value="${nomeVisitante}" size="40" required><br><br>

                <!-- Data de Nascimento -->
                <label for="dataNascimento">Data de Nascimento:</label><br>
                <input type="date" id="dataNascimento" name="dataNascimento"
                       value="${dataNascimento}" required><br><br>

                <!-- Email -->
                <label for="email">Email:</label><br>
                <input type="email" id="email" name="email"
                       value="${email}" size="60" required><br><br>

                <!-- Senha -->
                <label for="senha">Senha:</label><br>
                <input type="password" id="senha" name="senha"
                       value="${senha}" size="60" required><br><br>

                <!-- Mensagem de feedback -->
                <h3 style="color: #0066cc;">${mensagem}</h3>

                <!-- Botão principal -->
                <button type="submit" name="opcao"
                        value="${opcao == 'confirmarEditarLivre' ? 'confirmarEditarLivre' :
                               (opcao == 'confirmarExcluirLivre' ? 'confirmarExcluirLivre' : 'cadastrarLivre')}">
                    ${opcao == 'confirmarEditarLivre' ? 'Editar' :
                      (opcao == 'confirmarExcluirLivre' ? 'Excluir' : 'Cadastrar')}
                </button>

                <!-- Botão Cancelar -->
                <a href="${pageContext.request.contextPath}${URL_BASE}/VisitanteControlador?opcao=cancelarLivre">
                    <button id="btnCancelar" class="botao-cancelar" type="button">Cancelar</button>
                </a>

            </form>

            <c:if test="${not empty mensagemErro}">
                <h2 style="color: red;">${mensagemErro}</h2>
            </c:if>

            <c:if test="${not empty mensagem}">
                <h2 style="color: green;">${mensagem}</h2>
            </c:if>

            <hr>

        </main>
        <footer>
            <p>&copy; 2025 Parque de Diversões. Todos os direitos reservados.</p>
        </footer>
    </body>
</html>
