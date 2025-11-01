<%@ page contentType="text/html; charset=Latin1" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
    <head>
        <meta charset="Latin1">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>Cadastro de Categoria</title>
        <link rel="stylesheet" href="${pageContext.request.contextPath}/estilo/cadastro.css">
    </head>
    <body>
        <header>
            <%@ include file="menuParque.jsp" %>
        </header>
        <main>
            <h2>Cadastro de Categoria</h2>
            <form action="${pageContext.request.contextPath}${URL_BASE}/CategoriaControlador"
                  method="get">
                <!-- Campo oculto para código da categoria e operação (cadastrar, editar, etc.) -->
                <input type="hidden" name="codigoCategoria" value="${codigoCategoria}">
                <input type="hidden" name="opcao" value="${opcao != null ? opcao : 'cadastrar'}">

                <!-- Nome da Categoria -->
                <label for="nomeCategoria">Nome da Categoria:</label><br>
                <input type="text" id="nomeCategoria" name="nomeCategoria" value="${nomeCategoria}" size="40" required><br><br>

                <!-- Descrição da Categoria -->
                <label for="descricao">Descrição:</label><br>
                <input type="text" id="descricao" name="descricao" value="${descricao}" size="60"><br><br>

                <!-- Mensagem de feedback (caso exista) -->
                <h3 style="color: #0066cc;">${mensagem}</h3>

                <!-- Botão de ação (Cadastrar ou Atualizar) -->
                <button type="submit">
                    ${opcao == 'confirmarEditar' ? 'Editar' : (opcao == 'confirmarExcluir' ? 'Excluir' : 'Cadastrar')}
                </button>

                <!-- Botão de Cancelar -->
                <a href="${pageContext.request.contextPath}${URL_BASE}/CategoriaControlador?opcao=cancelar">
                    <button id="btnCancelar" class="botao-cancelar" type="button">Cancelar</button>
                </a>

                <a href="${pageContext.request.contextPath}${URL_BASE}/CategoriaControlador?opcao=listar">
                    <button class="botao-listar" type="button">Listar Tudo</button>
                </a>
            </form>

            <hr>

            <h2>Lista de Categorias</h2>
            <div class="aviso-mobile-tabela">
                <p>A listagem detalhada está disponível apenas em telas maiores (tablets ou desktops) para garantir a visualização completa de todos os dados.</p>
                <p style="margin-top: 10px;">Por favor, gire seu dispositivo ou use um monitor maior.</p>
            </div>
            
            <table class="tabela-categorias">
                <tr>
                    <th>Código</th>
                    <th>Nome</th>
                    <th>Descrição</th>
                    <th>Ações</th>
                </tr>
                <c:forEach var="cat" items="${listaCategoria}">
                    <tr>
                        <td>${cat.codCategoria}</td>
                        <td>${cat.nomeCategoria}</td>
                        <td>${cat.descricao}</td>
                        <td class="td-acoes">
                            <!-- Botão para editar categoria -->
                            <form style="margin:0;" action="${pageContext.request.contextPath}${URL_BASE}/CategoriaControlador" method="get">
                                <input type="hidden" name="opcao" value="editar">
                                <input type="hidden" name="codigoCategoria" value="${cat.codCategoria}">
                                <input type="hidden" name="nomeCategoria" value="${cat.nomeCategoria}">
                                <input type="hidden" name="descricao" value="${cat.descricao}">
                                <button class="botao-editar" type="submit">Editar</button>
                            </form>

                            <!-- Botão para excluir categoria -->
                            <form style="margin:0;" action="${pageContext.request.contextPath}${URL_BASE}/CategoriaControlador" method="get">
                                <input type="hidden" name="opcao" value="excluir">
                                <input type="hidden" name="codigoCategoria" value="${cat.codCategoria}">
                                <input type="hidden" name="nomeCategoria" value="${cat.nomeCategoria}">
                                <input type="hidden" name="descricao" value="${cat.descricao}">
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
