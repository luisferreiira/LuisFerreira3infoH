<%@ page contentType="text/html; charset=Latin1" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
    <head>
        <meta charset="Latin1">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>Relatórios de Funcionários</title>
        <link rel="stylesheet" href="${pageContext.request.contextPath}/estilo/cadastro.css">
        <script>
            function atualizarFiltros() {
                const tipo = document.getElementById('tipoRelatorioFunc').value;
                document.querySelectorAll('.filtroFunc').forEach(div => div.style.display = 'none');

                switch (tipo) {
                    case 'maiorTempoContrato':
                        document.getElementById('filtrosMaiorTempo').style.display = 'block';
                        break;
                    case 'salarioVendas':
                        document.getElementById('filtrosSalarioVendas').style.display = 'block';
                        break;
                }
            }
        </script>
    </head>
    <body>
        <header>
            <%@ include file="menuParque.jsp" %>
        </header>
        <main>
            <h2>Relatórios de Funcionários</h2>

            <form action="${pageContext.request.contextPath}/RelatorioFuncionariosControlador" method="get">
                <label for="tipoRelatorioFunc">Selecione o Relatório:</label>
                <select id="tipoRelatorioFunc" name="tipoRelatorio" onchange="atualizarFiltros()" required>
                    <option value="">Selecione</option>
                    <option value="maiorTempoContrato">Funcionários com Maior Tempo de Contrato</option>
                    <option value="salarioVendas">Comparação Salário x Vendas Mensal</option>
                    <option value="funcionariosCargo">Quantidade de Funcionários por Cargo</option>
                </select>

                <div id="filtrosSalarioVendas" class="filtroFunc" style="display:none; margin-top:10px;">
                    <label>Mês/Ano:</label><input type="month" name="mesAno">
                </div>

                <button type="submit">Gerar Relatório</button>
            </form>

            <hr>

            <!-- Mensagens de erro -->
            <c:if test="${not empty mensagemErro}">
                <p style="color:red;">${mensagemErro}</p>
            </c:if>

            <!-- Resultados -->
            <c:choose>
                <c:when test="${empty listaRelatorio}">
                    <p class="paragrafo-recuperacao"><strong>Nenhum resultado encontrado para os filtros selecionados.</strong></p>
                </c:when>
                <c:otherwise>
                    <h2>Resultados</h2>
                    
                    <p class="paragrafo-recuperacao"><em>${descricaoRelatorio}</em></p>
                    
                    <div class="aviso-mobile-tabela">
                        <p>O relatório detalhado está disponível apenas em telas maiores (tablets ou desktops) para garantir a visualização completa de todos os dados.</p>
                        <p style="margin-top: 10px;">Por favor, gire seu dispositivo ou use um monitor maior.</p>
                        <p style="margin-top: 10px;">A exportação está disponível para todos os modelos, basta selecionar o tipo de relatório e clicar em "Gerar Relatório"</p>
                    </div>

                    <table class="tabela-categorias">
                        <thead>
                            <tr>
                                <c:forEach var="col" items="${colunas}">
                                    <th>${col}</th>
                                    </c:forEach>
                            </tr>
                        </thead>
                        <tbody>
                            <c:forEach var="linha" items="${listaRelatorio}">
                                <tr>
                                    <c:forEach var="valor" items="${linha}">
                                        <td>${valor}</td>
                                    </c:forEach>
                                </tr>
                            </c:forEach>
                        </tbody>
                    </table>

                    <div class="botoes-exportar" style="margin-bottom: 30px;">
                        <form action="${pageContext.request.contextPath}/ExportarRelatorioControlador" method="get" style="display:inline;">
                            <input type="hidden" name="tipoRelatorio" value="${tipoRelatorio}">
                            <input type="hidden" name="descricaoRelatorio" value="${descricaoRelatorio}">
                            <input type="hidden" name="exportFormato" value="excel">
                            <button class="botao-exportar-excel" type="submit">Exportar Excel</button>
                        </form>
                        <form action="${pageContext.request.contextPath}/ExportarRelatorioControlador" method="get" style="display:inline;">
                            <input type="hidden" name="tipoRelatorio" value="${tipoRelatorio}">
                            <input type="hidden" name="descricaoRelatorio" value="${descricaoRelatorio}">
                            <input type="hidden" name="exportFormato" value="pdf">
                            <button class="botao-exportar-pdf" type="submit">Exportar PDF</button>
                        </form>

                    </div>
                </c:otherwise>
            </c:choose>
        </main>
        <footer>
            <p>&copy; 2025 Parque de Diversões. Todos os direitos reservados.</p>
        </footer>
    </body>
</html>
