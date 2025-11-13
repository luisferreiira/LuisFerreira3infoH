<%@ page contentType="text/html; charset=Latin1" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
    <head>
        <meta charset="Latin1">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>Cadastro de Brinquedos</title>
        <link rel="stylesheet" href="${pageContext.request.contextPath}/estilo/cadastro.css">
    </head>
    <body>
        <header>
            <%@ include file="menuParque.jsp" %>
        </header>
        <main>
            <h2>Cadastro de Brinquedos</h2>
            <form action="${pageContext.request.contextPath}${URL_BASE}/BrinquedoControlador"
                  method="get">
                <!-- Campo oculto para código do brinquedo e operação (cadastrar, editar, etc.) -->
                <input type="hidden" name="codBrinquedo" value="${codBrinquedo}">
                <input type="hidden" name="opcao" value="${opcao != null ? opcao : 'cadastrar'}">

                <!-- Nome do brinquedo -->
                <label for="nomeBrinquedo">Nome do Brinquedo:</label><br>
                <input type="text" id="nomeBrinquedo" name="nomeBrinquedo" value="${nomeBrinquedo}" size="40" required><br><br>

                <!-- Tipo do brinquedo -->
                <label for="tipoBrinquedo">Tipo do Brinquedo:</label><br>
                <input type="text" id="tipoBrinquedo" name="tipoBrinquedo" value="${tipoBrinquedo}" size="40" required><br><br>

                <!-- Capacidade Máxima do brinquedo -->
                <label for="capacidadeMaxima">Capacidade Máxima:</label><br>
                <input type="text" id="capacidadeMaxima" name="capacidadeMaxima" value="${capacidadeMaxima}"
                       oninput="this.value = this.value.replace(/[^0-9.]/g, '')" required><br><br>

                <!-- Restrição de Idade do brinquedo -->
                <label for="idadeRestrita">Restrição de Idade:</label><br>
                <input type="text" id="idadeRestrita" name="idadeRestrita" value="${idadeRestrita}"
                       oninput="this.value = this.value.replace(/[^0-9.]/g, '')" required><br><br>

                <!-- Funcionamento do Brinquendo -->
                <label for="funcionamento">Funcionamento do Brinquedo:</label><br>
                <select name="funcionamento" id="funcionamento" onchange="atualizaValor()" required>
                    <option value="">-- Selecione --</option>
                    <option value="0" ${funcionamento == '0' ? 'selected' : ''}>Desligado</option>
                    <option value="1" ${funcionamento == '1' ? 'selected' : ''}>Ligado</option>
                    <option value="2" ${funcionamento == '2' ? 'selected' : ''}>Em Manutenção</option>
                </select>

                <!-- Campo Oculto para Armazenar Valor Numérico -->
                <input type="hidden" id="funcionamentoValor" name="funcionamentoValor" value="${funcionamento}"><br><br>


                <!-- Mensagem de feedback (caso exista) -->
                <p style="color: #0066cc;">${mensagem}</p>

                <!-- Botão de ação (Cadastrar ou Atualizar) -->
                <button type="submit" name="opcao" value="${opcao == 'confirmarEditar' ? 'confirmarEditar' : (opcao == 'confirmarExcluir' ? 'confirmarExcluir' : 'cadastrar')}">
                    ${opcao == 'confirmarEditar' ? 'Editar' : (opcao == 'confirmarExcluir' ? 'Excluir' : 'Cadastrar')}
                </button>

                <!-- Botão de Cancelar -->
                <a href="${pageContext.request.contextPath}${URL_BASE}/BrinquedoControlador?opcao=cancelar">
                    <button id="btnCancelar" class="botao-cancelar" type="button">Cancelar</button>
                </a>

                <a href="${pageContext.request.contextPath}${URL_BASE}/BrinquedoControlador?opcao=listar">
                    <button class="botao-listar" type="button">Listar Tudo</button>
                </a>
            </form>

            <hr>

            <h2>Lista de Brinquedos</h2>

            <div class="aviso-mobile-tabela">
                <p>A listagem detalhada está disponível apenas em telas maiores (tablets ou desktops) para garantir a visualização completa de todos os dados.</p>
                <p style="margin-top: 10px;">Por favor, gire seu dispositivo ou use um monitor maior.</p>
            </div>

            <table class="tabela-categorias">
                <tr>
                    <th>Código</th>
                    <th>Brinquedo</th>
                    <th>Tipo</th>
                    <th>Restrição de Idade</th>
                    <th>Capacidade Máxima</th>
                    <th>Capacidade Atual</th>
                    <th>Funcionamento</th>
                    <th>Ações</th>
                </tr>
                <c:forEach var="b" items="${listaBrinquedo}">
                    <tr>
                        <td data-label="Código">${b.codBrinquedo}</td>
                        <td data-label="Brinquedo">${b.nomeBrinquedo}</td>
                        <td data-label="Tipo">${b.tipoBrinquedo}</td>
                        <td data-label="Restrição de Idade">${b.idadeRestrita} anos</td>
                        <td data-label="Capacidade Máxima">${b.capacidadeMaxima} pessoas</td>
                        <td data-label="Capacidade Atual">${b.capacidadeAtual} pessoas</td>
                        <td data-label="Funcionamento">
                            ${b.funcionamento == 0 ? 'Desligado' : b.funcionamento == 1 ? 'Ligado' : b.funcionamento == 2 ? 'Em Manutenção' : 'Indefinido'}
                        </td>
                        <td data-label="Ações" class="td-acoes">
                            <!-- Botão para editar  -->
                            <form style="margin:0;" action="${pageContext.request.contextPath}${URL_BASE}/BrinquedoControlador" method="get">
                                <input type="hidden" name="opcao" value="editar">
                                <input type="hidden" name="codBrinquedo" value="${b.codBrinquedo}">
                                <input type="hidden" name="nomeBrinquedo" value="${b.nomeBrinquedo}">
                                <input type="hidden" name="tipoBrinquedo" value="${b.tipoBrinquedo}">
                                <input type="hidden" name="idadeRestrita" value="${b.idadeRestrita}">
                                <input type="hidden" name="capacidadeMaxima" value="${b.capacidadeMaxima}">
                                <input type="hidden" name="funcionamento" value="${b.funcionamento}">
                                <button class="botao-editar" type="submit">Editar</button>
                            </form>

                            <!-- Botão para excluir  -->
                            <form style="margin:0;" action="${pageContext.request.contextPath}${URL_BASE}/BrinquedoControlador" method="get">
                                <input type="hidden" name="opcao" value="excluir">
                                <input type="hidden" name="codBrinquedo" value="${b.codBrinquedo}">
                                <input type="hidden" name="nomeBrinquedo" value="${b.nomeBrinquedo}">
                                <input type="hidden" name="tipoBrinquedo" value="${b.tipoBrinquedo}">
                                <input type="hidden" name="idadeRestrita" value="${b.idadeRestrita}">
                                <input type="hidden" name="capacidadeMaxima" value="${b.capacidadeMaxima}">
                                <input type="hidden" name="funcionamento" value="${b.funcionamento}">
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
        <script>
            // Função para atualizar o valor numérico do campo oculto
            function atualizaValor() {
                var funcionamento = document.getElementById("funcionamento").value;
                document.getElementById("funcionamentoValor").value = funcionamento;
            }
        </script>
    </body>
</html>
