<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %> 
<%@ page contentType="text/html; charset=Latin1" %>
<!DOCTYPE html>
<html>
    <head>
        <meta charset="Latin1" />
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>Adicionar Itens à Compra</title>
        <link rel="stylesheet" href="estilo/inicio.css">
        <link rel="stylesheet" href="estilo/comprar.css">

        <style>
            .card-item input[type="number"] {
                width: 60px;
                margin-bottom: 1em;
                border-radius: 5px;
                border: 2px solid #0066cc;
            }

            .card-item input[type=number]::-webkit-outer-spin-button,
            .card-item input[type=number]::-webkit-inner-spin-button {
                -webkit-appearance: none;
                margin: 0;
            }

            .botao-voltar {
                display: inline-block;
                margin-top: 20px;
                padding: 10px 20px;
                background-color: #0d6efd;
                color: white;
                font-weight: bold;
                text-decoration: none;
                border-radius: 5px;
            }

            .botao-voltar:hover {
                background-color: #0b5ed7;
            }

            .card-item img {
                width: 100%;
                height: 170px;
                object-fit: cover;
                border-bottom: 1px solid #ccc;
            }

            #additem{
                background-color: #198754 !important;

            }
            #additem:hover{
                background-color: #146c43 !important;
            }
        </style>
    </head>
    <body>

        <header>
            <%@ include file="menuParque.jsp" %>
        </header>

        <main>
            <form action="${pageContext.request.contextPath}/CompraControlador" method="get">
                <h3>Olá, ${visitante.nomeVisitante}!</h3>
                <h1>Adicionar Itens à Compra #${compra.codCompra}</h1>

                <input type="hidden" name="opcao" value="confirmarAdicionarItem" />
                <input type="hidden" name="codCompra" value="${compra.codCompra}" />

                <!-- Abas -->
                <div class="tabs">
                    <button type="button" class="tab-button active" onclick="openTab('brinquedos')">Brinquedos</button>
                    <button type="button" class="tab-button" onclick="openTab('eventos')">Eventos</button>
                    <button type="button" class="tab-button" onclick="openTab('lanches')">Lanches</button>
                </div>

                <!-- Conteúdo das abas -->

                <div class="tab-content active" id="brinquedos">
                    <div class="card-container">
                        <c:forEach var="ib" items="${ingressosBrinquedos}">
                            <div class="card-item">
                                <img src="imagens/brinquedo_padrao.png" alt="Imagem do brinquedo">
                                <div class="info">
                                    <h3>${ib.brinquedo.nomeBrinquedo}</h3>
                                    <p>Tipo: ${ib.tipoIngresso}</p>
                                    <p class="preco">R$ ${ib.valor}</p>
                                    <input type="hidden" name="tipo" value="brinquedo">
                                    <input type="hidden" name="codProduto" value="${ib.codIngresso}">
                                    <label>Quantidade:</label>
                                    <input type="number" name="quantidade" min="0" value="0">
                                </div>
                            </div>
                        </c:forEach>
                    </div>
                </div>

                <div class="tab-content" id="eventos">
                    <div class="card-container">
                        <c:forEach var="ie" items="${ingressosEventos}">
                            <div class="card-item">
                                <img src="imagens/eventos_padrao.png" alt="Imagem do evento">
                                <div class="info">
                                    <h3>${ie.evento.nomeEvento}</h3>
                                    <p>Tipo: ${ie.tipoIngresso}</p>
                                    <p class="preco">R$ ${ie.valor}</p>
                                    <input type="hidden" name="tipo" value="evento">
                                    <input type="hidden" name="codProduto" value="${ie.codIngresso}">
                                    <label>Quantidade:</label>
                                    <input type="number" name="quantidade" min="0" value="0">
                                </div>
                            </div>
                        </c:forEach>
                    </div>
                </div>

                <div class="tab-content" id="lanches">
                    <div class="card-container">
                        <c:forEach var="lanche" items="${lanches}">
                            <div class="card-item">
                                <img src="imagens/lanche_padrao.png" alt="Imagem do lanche">
                                <div class="info">
                                    <h3>${lanche.nomeLanche}</h3>
                                    <p class="preco">R$ ${lanche.precoLanche}</p>
                                    <input type="hidden" name="tipo" value="lanche">
                                    <input type="hidden" name="codProduto" value="${lanche.codLanche}">
                                    <label>Quantidade:</label>
                                    <input type="number" name="quantidade" min="0" value="0">
                                </div>
                            </div>
                        </c:forEach>
                    </div>
                </div>

                <div  class="pagamento-final">
                    <input id="additem" type="submit" value="Adicionar Itens à Compra">
                </div>

                <div class="pagamento-final">
                    <a href="${pageContext.request.contextPath}/CompraControlador?opcao=minhasCompras" class="botao-voltar">Voltar para Minhas Compras</a>
                </div>

            </form>
        </main>

        <footer>
            <p>&copy; 2025 Parque de Diversões. Todos os direitos reservados.</p>
        </footer>

        <script>
            function openTab(tabId) {
                const tabs = document.querySelectorAll('.tab-content');
                const buttons = document.querySelectorAll('.tab-button');
                tabs.forEach((t) => t.classList.remove('active'));
                buttons.forEach((b) => b.classList.remove('active'));

                document.getElementById(tabId).classList.add('active');
                event.currentTarget.classList.add('active');
            }
        </script>

    </body>
</html>
