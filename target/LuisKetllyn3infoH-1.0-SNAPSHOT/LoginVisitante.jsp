<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>Login do Visitante</title>
        <link rel="stylesheet" href="estilo/cadastro.css">
    </head>
    <body>

        <header>
            <%@include file="menuParque.jsp" %>
        </header>

        <main>
            <h2>Entre para acessar suas Compras</h2>

            <form action="${pageContext.request.contextPath}/LoginControlador" method="post">
                <input type="hidden" name="opcao" value="loginVisitante">

                <label for="email">E-mail:</label>
                <input type="email" name="email" id="email" required>

                <label for="senha">Senha:</label>
                <input type="password" name="senha" id="senha" required>

                <button type="submit">Entrar</button>

                <!-- Link para a página de esqueci a senha -->
                <a  href="${pageContext.request.contextPath}/esqueciSenhaVisitante.jsp">
                    <button class="botao-cancelar" type="button">Esqueci minha senha</button>
                </a>

                <c:if test="${not empty erro}">
                    <p style="color: red; text-align: center; margin-top: 10px;">${erro}</p>
                </c:if>
            </form>
        </main>

        <footer>
            <p>&copy; 2025 Parque de Diversões. Todos os direitos reservados.</p>
        </footer>

    </body>
</html>
