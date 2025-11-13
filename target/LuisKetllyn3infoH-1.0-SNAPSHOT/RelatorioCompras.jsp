<%@ page contentType="text/html; charset=Latin1" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
    <head>
        <meta charset="Latin1">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>Relatórios de Compras</title>
        <link rel="stylesheet" href="${pageContext.request.contextPath}/estilo/cadastro.css">
        <script>
            function atualizarFiltros() {
                const tipo = document.getElementById('tipoRelatorio').value;
                document.querySelectorAll('.filtro').forEach(div => div.style.display = 'none');

                switch (tipo) {
                    case 'comprasLanches':
                        document.getElementById('filtrosVendasLanches').style.display = 'block';
                        break;
                    case 'comprasIngressosBrinquedos':
                        document.getElementById('filtrosIngressosBrinquedos').style.display = 'block';
                        break;
                    case 'comprasIngressosEventos':
                        document.getElementById('filtrosIngressosEventos').style.display = 'block';
                        break;
                    case 'receitaDiaria':
                        document.getElementById('filtrosFinanceiroDiario').style.display = 'block';
                        break;
                    case 'receitaMensal':
                        document.getElementById('filtrosReceitaMensal').style.display = 'block';
                        break;
                    case 'receitaAnual':
                        document.getElementById('filtrosReceitaAnual').style.display = 'block';
                        break;
                    case 'lanchesMaisVendidos':
                        document.getElementById('filtrosTopLanches').style.display = 'block';
                        break;
                    case 'ibMaisVendidos':
                        document.getElementById('filtrosTopIB').style.display = 'block';
                        break;
                    case 'ieMaisVendidos':
                        document.getElementById('filtrosTopIE').style.display = 'block';
                        break;
                    case 'comprasFuncionario':
                        document.getElementById('filtrosComprasFuncionario').style.display = 'block';
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
            <h2>Relatórios de Compras</h2>

            <form action="${pageContext.request.contextPath}/RelatorioComprasControlador" method="get">
                <label for="tipoRelatorio">Selecione o Relatório:</label>
                <select id="tipoRelatorio" name="tipoRelatorio" onchange="atualizarFiltros()" required>
                    <option value="">Selecione</option>
                    <option value="comprasLanches">Compras de Lanches por Período</option>
                    <option value="comprasIngressosBrinquedos">Compras de Ingressos de Brinquedo por Período</option>
                    <option value="comprasIngressosEventos">Compras de Ingressos de Evento por Período</option>
                    <option value="receitaDiaria">Receita Diária por Tipo de Produto</option>
                    <option value="receitaMensal">Receita Mensal por Tipo de Produto</option>
                    <option value="receitaAnual">Receita Anual por Tipo de Produto</option>
                    <option value="lanchesMaisVendidos">Lanches Mais Vendidos por Período</option>
                    <option value="ibMaisVendidos">Ingressos de Brinquedos Mais Vendidos por Período</option>
                    <option value="ieMaisVendidos">Ingressos de Eventos Mais Vendidos por Período</option>
                </select>

                <!-- Filtros -->
                <div id="filtrosVendasLanches" class="filtro" style="display:none; margin-top:10px;">
                    <label>Data Início:</label><input type="date" name="dataInicio">
                    <label>Data Fim:</label><input type="date" name="dataFim">
                </div>

                <div id="filtrosIngressosBrinquedos" class="filtro" style="display:none; margin-top:10px;">
                    <label>Data Início:</label><input type="date" name="dataInicioBrinquedo">
                    <label>Data Fim:</label><input type="date" name="dataFimBrinquedo">
                </div>

                <div id="filtrosIngressosEventos" class="filtro" style="display:none; margin-top:10px;">
                    <label>Data Início:</label><input type="date" name="dataInicioEvento">
                    <label>Data Fim:</label><input type="date" name="dataFimEvento">
                </div>

                <div id="filtrosFinanceiroDiario" class="filtro" style="display:none; margin-top:10px;">
                    <label>Data:</label><input type="date" name="dataFinanceiro">
                </div>

                <div id="filtrosReceitaMensal" class="filtro" style="display:none; margin-top:10px;">
                    <label>Mês/Ano:</label><input type="month" name="mesAno">
                </div>

                <div id="filtrosReceitaAnual" class="filtro" style="display:none; margin-top:10px;">
                    <label>Ano:</label><input type="number" name="ano" oninput="this.value = this.value.slice(0, 4)">

                </div>

                <div id="filtrosTopLanches" class="filtro" style="display:none; margin-top:10px;">
                    <label>Data Início:</label><input type="date" name="dataInicioLanche">
                    <label>Data Fim:</label><input type="date" name="dataFimLanche">
                </div>

                <div id="filtrosTopIB" class="filtro" style="display:none; margin-top:10px;">
                    <label>Data Início:</label><input type="date" name="dataInicioIB">
                    <label>Data Fim:</label><input type="date" name="dataFimIB">
                </div>

                <div id="filtrosTopIE" class="filtro" style="display:none; margin-top:10px;">
                    <label>Data Início:</label><input type="date" name="dataInicioIE">
                    <label>Data Fim:</label><input type="date" name="dataFimIE">
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
