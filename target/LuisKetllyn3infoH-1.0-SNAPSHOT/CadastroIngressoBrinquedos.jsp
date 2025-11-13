<%@ page contentType="text/html; charset=Latin1" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
    <head>
        <meta charset="Latin1">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>Cadastro de Ingressos de Brinquedo</title>
        <link rel="stylesheet" href="${pageContext.request.contextPath}/estilo/cadastro.css">
    </head>
    <body>
        <header>
            <%@ include file="menuParque.jsp" %>
        </header>
        <main>
            <h2>Cadastro de Ingressos de Brinquedo</h2>
            <form action="${pageContext.request.contextPath}${URL_BASE}/IngressoBrinquedosControlador" method="get">
                <input type="hidden" name="codIngresso" value="${codIngresso}">
                <input type="hidden" name="opcao" value="${opcao != null ? opcao : 'cadastrar'}">

                <!-- Brinquedos -->
                <label for="codBrinquedo">Brinquedo:</label><br>
                <select id="codBrinquedo" name="codBrinquedo" required>
                    <option value="">-- Selecione o Brinquedo --</option>
                    <c:forEach var="b" items="${listaBrinquedo}">
                        <option value="${b.codBrinquedo}"
                                <c:if test="${codBrinquedo == b.codBrinquedo || codBrinquedo == b.codBrinquedo.toString()}">selected</c:if>>
                            ${b.nomeBrinquedo}
                        </option>
                    </c:forEach>
                </select>
                <br><br>


                <!-- Tipo -->
                <label for="tipoIngresso">Tipo de Ingresso:</label><br>
                <select name="tipoIngresso" id="tipoIngresso" required>
                    <option value="">-- Selecione --</option>
                    <option value="Meia-Entrada" <c:if test="${tipoIngresso == 'Meia-Entrada'}">selected</c:if>>Meia-Entrada</option>
                    <option value="Inteira" <c:if test="${tipoIngresso == 'Inteira'}">selected</c:if>>Inteira</option>
                    </select><br><br>

                    <!-- Valor do Ingresso -->
                    <label for="valor">Valor do Ingresso:</label><br>
                    <input type="number" id="valor" name="valor" value="${valor}" size="20" required><br><br>

                <!-- Lucro do Ingresso -->
                <label for="lucroIB">Lucro do Ingresso (%):</label><br>
                <input type="number" id="lucroIB" name="lucroIB" value="${lucroIB}"><br><br>



                <!-- Mensagem de feedback -->
                <p style="color: #0066cc;">${mensagem}</p>

                <!-- Botão de Ação -->
                <button type="submit" name="opcao"
                        value="${opcao == 'confirmarEditar' ? 'confirmarEditar' : (opcao == 'confirmarExcluir' ? 'confirmarExcluir' : 'cadastrar')}">
                    ${opcao == 'confirmarEditar' ? 'Editar' : (opcao == 'confirmarExcluir' ? 'Excluir' : 'Cadastrar')}
                </button>

                <!-- Botões Extras -->
                <a href="${pageContext.request.contextPath}${URL_BASE}/IngressoBrinquedosControlador?opcao=cancelar">
                    <button id="btnCancelar" class="botao-cancelar" type="button">Cancelar</button>
                </a>

                <a href="${pageContext.request.contextPath}${URL_BASE}/IngressoBrinquedosControlador?opcao=listar">
                    <button class="botao-listar" type="button">Listar Tudo</button>
                </a>
            </form>

            <hr>

            <h2>Lista de Ingressos de Brinquedo</h2>
            
            <div class="aviso-mobile-tabela">
                <p>A listagem detalhada está disponível apenas em telas maiores (tablets ou desktops) para garantir a visualização completa de todos os dados.</p>
                <p style="margin-top: 10px;">Por favor, gire seu dispositivo ou use um monitor maior.</p>
            </div>
            
            <table class="tabela-categorias">
                <tr>
                    <th>Código</th>
                    <th>Brinquedo</th>
                    <th>Tipo do Ingresso</th>
                    <th>Valor</th>
                    <th>Lucro do Ingresso (%)</th>                    
                    <th>Ações</th>
                </tr>
                <c:forEach var="ib" items="${listaIB}">
                    <tr>
                        <td>${ib.codIngresso}</td>
                        <td>
                            <c:choose>
                                <c:when test="${not empty ib.brinquedo}">
                                    ${ib.brinquedo.nomeBrinquedo}
                                </c:when>
                                <c:otherwise>
                                    Sem brinquedo
                                </c:otherwise>
                            </c:choose>
                        </td>
                        <td>${ib.tipoIngresso}</td>
                        <td>R$${ib.valor}</td>
                        <td>${ib.lucroIB}%</td>

                        <td class="td-acoes">
                            <!-- Editar -->
                            <form style="margin:0;" action="${pageContext.request.contextPath}${URL_BASE}/IngressoBrinquedosControlador" method="get">
                                <input type="hidden" name="opcao" value="editar">
                                <input type="hidden" name="codIngresso" value="${ib.codIngresso}">
                                <input type="hidden" name="tipoIngresso" value="${ib.tipoIngresso}">
                                <input type="hidden" name="valor" value="${ib.valor}">
                                <input type="hidden" name="lucroIB" value="${ib.lucroIB}">
                                <input type="hidden" name="codBrinquedo" value="${ib.brinquedo.codBrinquedo}">
                                <button class="botao-editar" type="submit">Editar</button>
                            </form>

                            <!-- Excluir -->
                            <form style="margin:0;" action="${pageContext.request.contextPath}${URL_BASE}/IngressoBrinquedosControlador" method="get">
                                <input type="hidden" name="opcao" value="excluir">
                                <input type="hidden" name="codIngresso" value="${ib.codIngresso}">
                                <input type="hidden" name="tipoIngresso" value="${ib.tipoIngresso}">
                                <input type="hidden" name="valor" value="${ib.valor}">
                                <input type="hidden" name="lucroIB" value="${ib.lucroIB}">
                                <input type="hidden" name="codBrinquedo" value="${ib.brinquedo.codBrinquedo}">
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
