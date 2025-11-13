<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>Senha Alterada</title>
        <link rel="stylesheet" href="estilo/cadastro.css">
    </head>
    <body>

        <header>
            <%@include file="menuParque.jsp" %>
        </header>

        <main>


            <div class="container">
                <h2>${mensagem}</h2>
                <form action="${pageContext.request.contextPath}/inicio.jsp" method="get">
                    <button type="submit">Voltar ao início</button>
                </form>
            </div>

        </main>

        <footer>
            <p>&copy; 2025 Parque de Diversões. Todos os direitos reservados.</p>
        </footer>

    </body>
</html>
