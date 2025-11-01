<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page contentType="text/html; charset=Latin1" %>

<!DOCTYPE html>
<html>
    <head>
        <meta charset="Latin1" />
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>Minhas Compras</title>
        <style>
            main {
                max-width: 1000px;
                margin: 40px auto;
                padding: 20px;
                background-color: #f9f9f9;
            }
            h1 {
                text-align: center;
                color: #0d6efd;
                margin-bottom: 30px;
            }
            h3 {
                margin-bottom: 30px;
            }
            .compra {
                background-color: #fff;
                border-left: 5px solid #0d6efd;
                padding: 20px;
                margin-top: 30px;
                margin-bottom: 30px;
                border-radius: 10px;
                box-shadow: 0 0 8px rgba(0,0,0,0.1);
            }
            .compra h3 {
                color: #0d6efd;
                margin-bottom: 10px;
            }
            .compra p {
                margin: 5px 0;
                font-size: 1rem;
            }
            .compra h4 {
                margin-top: 20px;
                color: #333;
                font-weight: bold;
            }
            ul {
                padding-left: 20px;
                margin-top: 10px;
            }
            ul li {
                margin-bottom: 8px;
                font-size: 0.95rem;
            }
            form {
                display: inline;
            }
            form button {
                background-color: #dc3545;
                border: none;
                color: white;
                padding: 5px 10px;
                font-size: 0.9rem;
                border-radius: 5px;
                cursor: pointer;
                margin: 10px;
            }
            form button:hover {
                background-color: #c82333;
            }
            .additem {
                background-color: #198754 !important;
                border: none;
                color: white;
                padding: 5px 10px;
                font-size: 0.9rem;
                border-radius: 5px;
                cursor: pointer;
                margin: 10px;
                text-decoration: none;
            }
            .additem:hover {
                background-color: #146c43 !important;
            }
            .botao-ver-compras {
                display: block;
                width: fit-content;
                margin: 30px auto;
                padding: 12px 30px;
                background-color: #198754;
                color: white;
                font-weight: bold;
                text-decoration: none;
                border-radius: 5px;
                transition: background-color 0.3s ease;
            }
            .botao-ver-compras:hover {
                background-color: #146c43;
            }
            footer {
                text-align: center;
                padding: 20px;
                background-color: #eee;
                margin-top: 40px;
                font-size: 0.9rem;
                color: #555;
            }
            /* --- FILTROS DE DESKTOP (Linha Única) --- */
            .filtro-data {
                text-align: center;
                margin-bottom: 30px;
            }

            .filtro-data form {
                display: inline-flex; /* Mantém os filtros lado a lado */
                align-items: center;
                gap: 10px;
            }

            .filtro-data .filtro-campo {
                display: inline-block;
            }

            .filtro-data .filtro-campo label {
                margin-right: 5px;
                font-weight: bold;
            }

            .filtro-data input[type="date"] {
                padding: 6px 10px;
                border: 1px solid #ccc;
                border-radius: 6px;
                font-size: 0.95rem;
                width: 130px; /* Largura fixa para desktop */
            }
            .filtro-data input[type="date"]:focus {
                border-color: #0d6efd;
                box-shadow: 0 0 5px rgba(13, 110, 253, 0.3);
                outline: none;
            }

            .filtro-data button {
                background-color: #0d6efd;
                border: none;
                color: white;
                padding: 6px 15px;
                border-radius: 5px;
                cursor: pointer;
                font-size: 0.95rem;
            }

            .filtro-data button:hover {
                background-color: #0b5ed7;
            }

            
            .botoes-exportar {
                margin-top: 40px;
                text-align: center;
            }
            .botoes-exportar button {
                margin: 5px;
                padding: 10px 20px;
                border: none;
                border-radius: 8px;
                font-weight: bold;
                cursor: pointer;
            }
            .botoes-exportar .excel {
                background-color: #28a745;
                color: white;
            }
            .botoes-exportar .pdf {
                background-color: #dc3545;
                color: white;
            }
            .botoes-exportar .excel:hover {
                background-color: #208537;
                color: white;
            }
            .botoes-exportar .pdf:hover {
                background-color: #b02a37;
                color: white;
            }
            

            /* ---------------------------------------------------- */
            /* --- Media Query para Responsividade (Mobile) --- */
            /* ---------------------------------------------------- */
            @media (max-width: 600px) {

                main {
                    margin: 20px auto;
                    padding: 10px;
                }

                h3 {
                    margin: 10px 20px 20px;
                }
               

                /* Container Principal do Filtro */
                .filtro-data {
                    display: flex;
                    flex-direction: column; /* Empilha o formulário principal e o "Listar Tudo" */
                    align-items: center;
                    margin-bottom: 20px;
                    width: 100%;
                }

                /* Formulário principal (Data Início, Data Fim, Filtrar) */
                .filtro-data form {
                    display: flex;
                    flex-direction: column;
                    width: 90%; /* Ocupa mais espaço horizontal */
                    gap: 10px; /* Espaçamento entre as linhas */
                }

                /* Linhas do Filtro: "De:" e "Até:" */
                .filtro-data .filtro-campo {
                    display: flex; /* Alinha o label e o input lado a lado */
                    align-items: center;
                    justify-content: space-between;
                    width: 100%;
                    margin: 0;
                }

                .filtro-data .filtro-campo label {
                    flex-shrink: 0; /* Impede que o label diminua */
                    margin-right: 15px;
                }

                .filtro-data input[type="date"] {
                    width: 70%; /* Input preenche a maior parte do espaço restante */
                    max-width: 200px; /* Limita a largura máxima */
                }

                /* Botões (Linha 3) */
                .filtro-botoes {
                    display: flex;
                    justify-content: center;
                    width: 100%;
                }

                /* Exibe o botão Filtrar específico do mobile */
                .filtro-data .botao-filtrar-mobile {
                    display: block;
                    width: 100%; /* Ocupa 100% da largura do filtro-botoes */
                    margin: 0; /* Zera margem do desktop */
                }

                /* Formulário Listar Tudo */
                .form-listar-tudo-mobile {
                    display: block !important;
                    width: 90%;
                    margin-top: 10px;
                }

                .form-listar-tudo-mobile button {
                    width: 100%; /* Faz o botão Listar Tudo ocupar a linha toda */
                    margin: 0 !important;
                }

                /* Botões de Exportar: Empilha no mobile */
                .botoes-exportar {
                    display: flex;
                    flex-direction: column;
                    align-items: center;
                    width: 90%;
                    margin: 20px auto;
                }
                .botoes-exportar form {
                    width: 100%;
                    margin: 5px 0;
                }
                .botoes-exportar button {
                    width: 100%;
                    margin: 0;
                }

                /* Ajuste para botões de ação na lista (Excluir/Adicionar Item) */
                .compra form,
                .compra .additem {
                    display: block !important;
                    width: 100%;
                    text-align: center;
                    margin: 5px 0 5px 0 !important;
                }

                .compra form button,
                .compra .additem {
                    width: 100%;
                    margin: 5px 0 !important;
                }

                /* Ajuste para o botão excluir item na lista (o menor) */
                ul li form {
                    margin-left: auto; /* Empurra o botão de excluir para o final da linha */
                }

            }
        </style>
    </head>
    <body>
        <header>
            <%@include file="menuParque.jsp"%>
        </header>

        <main>
            <h3>Bem Vindo, ${visitante.nomeVisitante}!</h3>
            <h1>Minhas Compras</h1>

            <!-- Filtro de Data -->
            <div class="filtro-data">
                <form action="CompraControlador" method="get">
                    <input type="hidden" name="opcao" value="filtrarMinhasCompras" />

                    <div class="filtro-campo">
                        <label>De:</label>
                        <input type="date" name="dataInicio"
                               value="${filtroDataInicio != null ? filtroDataInicio : ''}" required />
                    </div>

                    <div class="filtro-campo">
                        <label>Até:</label>
                        <input type="date" name="dataFim"
                               value="${filtroDataFim != null ? filtroDataFim : ''}" required />
                    </div>

                    <div class="filtro-botoes">
                        <button type="submit" class="botao-filtrar-mobile">Filtrar</button>
                    </div >
                </form>

                <form action="CompraControlador" method="get" class="form-listar-tudo-mobile">
                    <input type="hidden" name="opcao" value="minhasCompras" />
                    <button type="submit">Listar Tudo</button>
                </form>
            </div>

            <c:choose>
                <c:when test="${not empty listaCompras}">
                    <c:forEach var="compra" items="${listaCompras}">
                        <div class="compra">
                            <h3>Compra #${compra.codCompra}</h3>
                            <p><strong>Data:</strong> ${compra.dataCompraFormatada}</p>
                            <p><strong>Pagamento:</strong> ${compra.tipoPagamento}</p>
                            <p><strong>Funcionário:</strong> ${compra.funcionario.nomeFuncionario}</p>

                            <!-- Excluir compra -->
                            <form method="get" action="CompraControlador">
                                <input type="hidden" name="opcao" value="excluirCompraCompleta">
                                <input type="hidden" name="codCompra" value="${compra.codCompra}">
                                <button type="submit">Excluir Compra</button>
                            </form>

                            <!-- Adicionar item -->
                            <a href="CompraControlador?opcao=adicionarItemForm&codCompra=${compra.codCompra}" class="additem">
                                Adicionar Item
                            </a>

                            <!-- Lanches -->
                            <c:if test="${not empty compra.listaLanches}">
                                <h4>Lanches:</h4>
                                <ul>
                                    <c:forEach var="cl" items="${compra.listaLanches}">
                                        <li>
                                            ${cl.lanche.nomeLanche} - Qtd: ${cl.quantidadeLanche}
                                            - Preço un.: R$
                                            <fmt:formatNumber value="${cl.lanche.precoLanche}" type="number" minFractionDigits="2" maxFractionDigits="2" />
                                            - Total: R$
                                            <fmt:formatNumber value="${cl.lanche.precoLanche * cl.quantidadeLanche}" type="number" minFractionDigits="2" maxFractionDigits="2" />
                                            <form method="get" action="CompraControlador">
                                                <input type="hidden" name="opcao" value="excluirLanche">
                                                <input type="hidden" name="codCompraLanche" value="${cl.codCompraLanche}">
                                                <button type="submit">Excluir</button>
                                            </form>
                                        </li>
                                    </c:forEach>
                                </ul>
                            </c:if>

                            <!-- Ingressos Brinquedos -->
                            <c:if test="${not empty compra.listaCIB}">
                                <h4>Ingressos de Brinquedos:</h4>
                                <ul>
                                    <c:forEach var="cib" items="${compra.listaCIB}">
                                        <li>
                                            ${cib.ib.brinquedo.nomeBrinquedo} - Qtd: ${cib.quantIngBrinq}
                                            - Preço un.: R$
                                            <fmt:formatNumber value="${cib.ib.valor}" type="number" minFractionDigits="2" maxFractionDigits="2" />
                                            - Total: R$
                                            <fmt:formatNumber value="${cib.ib.valor * cib.quantIngBrinq}" type="number" minFractionDigits="2" maxFractionDigits="2" />
                                            <form method="get" action="CompraControlador">
                                                <input type="hidden" name="opcao" value="excluirBrinquedo">
                                                <input type="hidden" name="codCompraBrinquedos" value="${cib.codCompraBrinquedos}">
                                                <button type="submit">Excluir</button>
                                            </form>
                                        </li>
                                    </c:forEach>
                                </ul>
                            </c:if>

                            <!-- Ingressos Eventos -->
                            <c:if test="${not empty compra.listaCIE}">
                                <h4>Ingressos de Eventos:</h4>
                                <ul>
                                    <c:forEach var="cie" items="${compra.listaCIE}">
                                        <li>
                                            ${cie.ie.evento.nomeEvento} - Qtd: ${cie.quantIngEvent}
                                            - Preço un.: R$
                                            <fmt:formatNumber value="${cie.ie.valor}" type="number" minFractionDigits="2" maxFractionDigits="2" />
                                            - Total: R$
                                            <fmt:formatNumber value="${cie.ie.valor * cie.quantIngEvent}" type="number" minFractionDigits="2" maxFractionDigits="2" />
                                            <form method="get" action="CompraControlador">
                                                <input type="hidden" name="opcao" value="excluirEvento">
                                                <input type="hidden" name="codCompraEventos" value="${cie.codCompraEventos}">
                                                <button type="submit">Excluir</button>
                                            </form>
                                        </li>
                                    </c:forEach>
                                </ul>
                            </c:if>

                            <!-- Total da compra -->
                            <p><strong>Total da Compra:</strong>
                                R$
                                <fmt:formatNumber value="${compra.valorTotalCompra}" type="number" minFractionDigits="2" maxFractionDigits="2" />
                            </p>
                        </div>
                    </c:forEach>

                    <!-- ? Botões Exportar -->
                    <div class="botoes-exportar">
                        <form action="${pageContext.request.contextPath}/ExportarRelatorioControlador" method="get">
                            <input type="hidden" name="opcao" value="exportarExcel">
                            <button type="submit" class="excel">Exportar Excel</button>
                        </form>
                        <form action="${pageContext.request.contextPath}/ExportarRelatorioControlador" method="get">
                            <input type="hidden" name="opcao" value="exportarPdf">
                            <button type="submit" class="pdf">Exportar PDF</button>
                        </form>
                    </div>
                </c:when>
                <c:otherwise>
                    <p>Você ainda não realizou nenhuma compra.</p>
                </c:otherwise>
            </c:choose>

            <a class="botao-ver-compras" href="CompraControlador?opcao=listar">Voltar ao Catálogo</a>
        </main>

        <footer>
            <p>&copy; 2025 Parque de Diversões. Todos os direitos reservados.</p>
        </footer>
    </body>
</html>
