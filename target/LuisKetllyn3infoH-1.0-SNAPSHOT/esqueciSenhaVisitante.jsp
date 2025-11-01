<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>Esqueci minha senha</title>
        <link rel="stylesheet" href="estilo/cadastro.css">
    </head>
    <body>

        <header>
            <%@include file="menuParque.jsp" %>
        </header>

        <main>
            <h2>Recuperação de Senha</h2>

            <p class="paragrafo-recuperacao">Digite o e-mail cadastrado para receber o link de redefinição de senha:</p>

            <form action="${pageContext.request.contextPath}/LoginControlador" method="post">
                <input type="hidden" name="tipoUsuario" value="visitante">
                <input type="hidden" name="opcao" value="esqueciSenha">

                <label for="email">E-mail:</label>
                <input type="email" name="email" id="email" required>

                <button type="submit">Enviar link de redefinição</button>
            </form>

            <c:if test="${not empty mensagem}">
                <p style="color: green; text-align: center; margin-top: 10px;">${mensagem}</p>
            </c:if>

            <c:if test="${not empty erro}">
                <p style="color: red; text-align: center; margin-top: 10px;">${erro}</p>
            </c:if>
        </main>

        <footer>
            <p>&copy; 2025 Parque de Diversões. Todos os direitos reservados.</p>
        </footer>

    </body>
</html>
