<!DOCTYPE html>
<html>
    <link rel = "stylesheet" href ="${pageContext.request.contextPath}/estilo/inicio.css">
    <body>
        <nav class="navbar">
            <div class="logo">
                <a href="${pageContext.request.contextPath}/inicio.jsp">Parque de Diversões</a>
            </div>

            <div class="menu-toggle" id="mobile-menu">
                <span class="bar"></span>
                <span class="bar"></span>
                <span class="bar"></span>
            </div>

            <ul class="nav-links" id="nav-menu">
                <li>
                    <a href="${pageContext.request.contextPath}/CadastroVisitanteLivre.jsp">Cadastre-se</a>
                </li>
                <li class="dropdown">
                    <a href="#">Registros e Cadastros</a>
                    <ul class="submenu">
                        <li><a href="${pageContext.request.contextPath}/CadastroVisitante.jsp">Visitante</a></li>
                        <li><a href="${pageContext.request.contextPath}/CadastroCargo.jsp">Cargo</a></li>
                        <li><a href="${pageContext.request.contextPath}/CadastroFuncionario.jsp">Funcionário</a></li>
                        <li><a href="${pageContext.request.contextPath}/CadastroBrinquedo.jsp">Brinquedo</a></li>
                        <li><a href="${pageContext.request.contextPath}/CadastroIngressoBrinquedos.jsp">Ingresso de Brinquedo</a></li>
                        <li><a href="${pageContext.request.contextPath}/CadastroEventos.jsp">Evento</a></li>
                        <li><a href="${pageContext.request.contextPath}/CadastroIngressoEventos.jsp">Ingresso de Evento</a></li>
                        <li><a href="${pageContext.request.contextPath}/CadastroLanche.jsp">Lanche</a></li>
                        <li><a href="${pageContext.request.contextPath}/CadastroCategoria.jsp">Categoria</a></li>
                    </ul>
                </li>
                <li class="dropdown">
                    <a href="#">Relatórios</a>
                    <ul class="submenu">
                        <li><a href="${pageContext.request.contextPath}/RelatorioCompras.jsp">Compras</a></li>
                        <li><a href="${pageContext.request.contextPath}/RelatorioFuncionarios.jsp">Funcionários</a></li>
                        <li><a href="${pageContext.request.contextPath}/MinhasCompras.jsp">Visitantes</a></li>
                    </ul>
                </li>
                <li>
                    <a href="${pageContext.request.contextPath}/LoginVisitante.jsp">Comprar</a>
                </li>
                <li>
                    <a href="${pageContext.request.contextPath}/LoginControlador?opcao=logout">Sair</a>
                </li>

            </ul>
        </nav>

        <script>
            document.getElementById('mobile-menu').addEventListener('click', function () {
                var navMenu = document.getElementById('nav-menu');
                navMenu.classList.toggle('active');
            });
        </script>

    </body>
</html>