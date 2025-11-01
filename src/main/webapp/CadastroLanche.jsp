<%@ page contentType="text/html; charset=Latin1" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
    <head>
        <meta charset="Latin1">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>Cadastro de Lanches</title>
        <link rel="stylesheet" href="${pageContext.request.contextPath}/estilo/cadastro.css">
    </head>
    <body>
        <header>
            <%@ include file="menuParque.jsp" %>
        </header>
        <main>
            <h2>Cadastro de Lanches</h2>
            <form action="${pageContext.request.contextPath}${URL_BASE}/LancheControlador" method="get">
                <input type="hidden" name="codLanche" value="${codLanche}">
                <input type="hidden" name="opcao" value="${opcao != null ? opcao : 'cadastrar'}">

                <!-- Nome -->
                <label for="nomeLanche">Nome do Lanche</label><br>
                <input type="text" id="nomeLanche" name="nomeLanche" value="${nomeLanche}" size="40" required><br><br>

                <!-- Preço do Lanche -->
                <label for="precoLanche">Preço do Lanche:</label><br>
                <input type="number" id="precoLanche" name="precoLanche" value="${precoLanche}" size="20" required><br><br>

                <!-- Descrição -->
                <label for="descricao">Descrição:</label><br>
                <input type="text" id="descricao" name="descricao" value="${descricao}"><br><br>

                <!-- Lucro do Lanche -->
                <label for="lucroLanches">Lucro do Lanche (%):</label><br>
                <input type="number" id="lucroLanches" name="lucroLanches" value="${lucroLanches}"><br><br>

                <!-- Categoria -->
                <label for="codCategoria">Categoria:</label><br>
                <select id="codCategoria" name="codCategoria" required>
                    <option value="">-- Selecione a Categoria --</option>
                    <c:forEach var="c" items="${listaCategoria}">
                        <option value="${c.codCategoria}" <c:if test="${codCategoria == c.codCategoria.toString()}">selected</c:if>>${c.nomeCategoria}</option>
                    </c:forEach>
                </select>
                <br><br>


                <!-- Mensagem de feedback -->
                <h3 style="color: #0066cc;">${mensagem}</h3>

                <!-- Botão de Ação -->
                <button type="submit" name="opcao"
                        value="${opcao == 'confirmarEditar' ? 'confirmarEditar' : (opcao == 'confirmarExcluir' ? 'confirmarExcluir' : 'cadastrar')}">
                    ${opcao == 'confirmarEditar' ? 'Editar' : (opcao == 'confirmarExcluir' ? 'Excluir' : 'Cadastrar')}
                </button>

                <!-- Botões Extras -->
                <a href="${pageContext.request.contextPath}${URL_BASE}/LancheControlador?opcao=cancelar">
                    <button id="btnCancelar" class="botao-cancelar" type="button">Cancelar</button>
                </a>

                <a href="${pageContext.request.contextPath}${URL_BASE}/LancheControlador?opcao=listar">
                    <button class="botao-listar" type="button">Listar Tudo</button>
                </a>
            </form>

            <hr>

            <h2>Lista de Lanches</h2>
            
            <div class="aviso-mobile-tabela">
                <p>A listagem detalhada está disponível apenas em telas maiores (tablets ou desktops) para garantir a visualização completa de todos os dados.</p>
                <p style="margin-top: 10px;">Por favor, gire seu dispositivo ou use um monitor maior.</p>
            </div>
            
            <table class="tabela-categorias">
                <tr>
                    <th>Código</th>
                    <th>Lanche</th>
                    <th>Preço</th>
                    <th>Descrição</th>
                    <th>Lucro do Lanche (%)</th>
                    <th>Categoria</th>
                    <th>Ações</th>
                </tr>
                <c:forEach var="l" items="${listaLanche}">
                    <tr>
                        <td>${l.codLanche}</td>
                        <td>${l.nomeLanche}</td>
                        <td>R$${l.precoLanche}</td>
                        <td>${l.descricao}</td>
                        <td>${l.lucroLanches}%</td>
                        <td>
                            <c:choose>
                                <c:when test="${not empty l.categoria}">
                                    ${l.categoria.nomeCategoria}
                                </c:when>
                                <c:otherwise>
                                    Sem categoria
                                </c:otherwise>
                            </c:choose>
                        </td>

                        <td class="td-acoes">
                            <!-- Editar -->
                            <form style="margin:0;" action="${pageContext.request.contextPath}${URL_BASE}/LancheControlador" method="get">
                                <input type="hidden" name="opcao" value="editar">
                                <input type="hidden" name="codLanche" value="${l.codLanche}">
                                <input type="hidden" name="nomeLanche" value="${l.nomeLanche}">
                                <input type="hidden" name="precoLanche" value="${l.precoLanche}">
                                <input type="hidden" name="descricao" value="${l.descricao}">
                                <input type="hidden" name="lucroLanches" value="${l.lucroLanches}">
                                <input type="hidden" name="codCategoria" value="${l.categoria.codCategoria}">
                                <button class="botao-editar" type="submit">Editar</button>
                            </form>

                            <!-- Excluir -->
                            <form style="margin:0;" action="${pageContext.request.contextPath}${URL_BASE}/LancheControlador" method="get">
                                <input type="hidden" name="opcao" value="excluir">
                                <input type="hidden" name="codLanche" value="${l.codLanche}">
                                <input type="hidden" name="nomeLanche" value="${l.nomeLanche}">
                                <input type="hidden" name="precoLanche" value="${l.precoLanche}">
                                <input type="hidden" name="descricao" value="${l.descricao}">
                                <input type="hidden" name="lucroLanches" value="${l.lucroLanches}">
                                <input type="hidden" name="codCategoria" value="${l.categoria.codCategoria}">
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
