<%@ page contentType="text/html; charset=Latin1" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
    <head>
        <meta charset="Latin1">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>Cadastro de Ingressos de Evento</title>
        <link rel="stylesheet" href="${pageContext.request.contextPath}/estilo/cadastro.css">
    </head>
    <body>
        <header>
            <%@ include file="menuParque.jsp" %>
        </header>
        <main>
            <h2>Cadastro de Ingressos de Evento</h2>
            <form action="${pageContext.request.contextPath}${URL_BASE}/IngressoEventosControlador" method="get">
                <input type="hidden" name="codIngresso" value="${codIngresso}">
                <input type="hidden" name="opcao" value="${opcao != null ? opcao : 'cadastrar'}">

                <!-- Eventos -->
                <label for="codEventos">Evento:</label><br>
                <select id="codEventos" name="codEventos" required>
                    <option value="">-- Selecione o Evento --</option>
                    <c:forEach var="e" items="${listaEventos}">
                        <option value="${e.codEventos}" <c:if test="${codEventos == e.codEventos.toString()}">selected</c:if>>${e.nomeEvento}</option>
                    </c:forEach>
                </select>
                <br><br>

                <!-- Tipo -->
                <!-- Tipo -->
                <label for="tipoIngresso">Tipo de Ingresso:</label><br>
                <select name="tipoIngresso" id="tipoIngresso" required>
                    <option value="">-- Selecione --</option>
                    <option value="VIP" <c:if test="${tipoIngresso == 'VIP'}">selected</c:if>>VIP</option>
                    <option value="Comum" <c:if test="${tipoIngresso == 'Comum'}">selected</c:if>>Comum</option>
                    </select><br><br>


                    <!-- Valor do Ingresso -->
                    <label for="valor">Valor do Ingresso:</label><br>
                    <input type="number" id="valor" name="valor" value="${valor}" size="20" required><br><br>

                <!-- Lucro do Ingresso -->
                <label for="lucroIE">Lucro do Ingresso (%):</label><br>
                <input type="number" id="lucroIE" name="lucroIE" value="${lucroIE}"><br><br>




                <!-- Mensagem de feedback -->
                <h3 style="color: #0066cc;">${mensagem}</h3>

                <!-- Botão de Ação -->
                <button type="submit" name="opcao"
                        value="${opcao == 'confirmarEditar' ? 'confirmarEditar' : (opcao == 'confirmarExcluir' ? 'confirmarExcluir' : 'cadastrar')}">
                    ${opcao == 'confirmarEditar' ? 'Editar' : (opcao == 'confirmarExcluir' ? 'Excluir' : 'Cadastrar')}
                </button>

                <!-- Botões Extras -->
                <a href="${pageContext.request.contextPath}${URL_BASE}/IngressoEventosControlador?opcao=cancelar">
                    <button id="btnCancelar" class="botao-cancelar" type="button">Cancelar</button>
                </a>

                <a href="${pageContext.request.contextPath}${URL_BASE}/IngressoEventosControlador?opcao=listar">
                    <button class="botao-listar" type="button">Listar Tudo</button>
                </a>
            </form>

            <hr>

            <h2>Lista de Ingressos de Evento</h2>
            
            <div class="aviso-mobile-tabela">
                <p>A listagem detalhada está disponível apenas em telas maiores (tablets ou desktops) para garantir a visualização completa de todos os dados.</p>
                <p style="margin-top: 10px;">Por favor, gire seu dispositivo ou use um monitor maior.</p>
            </div>
            
            <table class="tabela-categorias">
                <tr>
                    <th>Código</th>
                    <th>Evento</th>
                    <th>Tipo do Ingresso</th>
                    <th>Valor</th>
                    <th>Lucro do Ingresso (%)</th>                    
                    <th>Ações</th>
                </tr>
                <c:forEach var="ie" items="${listaIE}">
                    <tr>
                        <td>${ie.codIngresso}</td>
                        <td>
                            <c:choose>
                                <c:when test="${not empty ie.evento}">
                                    ${ie.evento.nomeEvento}
                                </c:when>
                                <c:otherwise>
                                    Sem evento
                                </c:otherwise>
                            </c:choose>
                        </td>
                        <td>${ie.tipoIngresso}</td>
                        <td>R$${ie.valor}</td>
                        <td>${ie.lucroIE}%</td>

                        <td class="td-acoes">
                            <!-- Editar -->
                            <form style="margin:0;" action="${pageContext.request.contextPath}${URL_BASE}/IngressoEventosControlador" method="get">
                                <input type="hidden" name="opcao" value="editar">
                                <input type="hidden" name="codIngresso" value="${ie.codIngresso}">
                                <input type="hidden" name="tipoIngresso" value="${ie.tipoIngresso}">
                                <input type="hidden" name="valor" value="${ie.valor}">
                                <input type="hidden" name="lucroIE" value="${ie.lucroIE}">
                                <input type="hidden" name="codEventos" value="${ie.evento.codEventos}">
                                <button class="botao-editar" type="submit">Editar</button>
                            </form>

                            <!-- Excluir -->
                            <form style="margin:0;" action="${pageContext.request.contextPath}${URL_BASE}/IngressoEventosControlador" method="get">
                                <input type="hidden" name="opcao" value="excluir">
                                <input type="hidden" name="codIngresso" value="${ie.codIngresso}">
                                <input type="hidden" name="tipoIngresso" value="${ie.tipoIngresso}">
                                <input type="hidden" name="valor" value="${ie.valor}">
                                <input type="hidden" name="lucroIE" value="${ie.lucroIE}">
                                <input type="hidden" name="codEventos" value="${ie.evento.codEventos}">
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
