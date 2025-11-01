<%@ page contentType="text/html; charset=Latin1" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
    <head>
        <meta charset="Latin1">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>Cadastro de Cargos</title>
        <link rel="stylesheet" href="${pageContext.request.contextPath}/estilo/cadastro.css">
    </head>
    <body>
        <header>
            <%@ include file="menuParque.jsp" %>
        </header>
        <main>
            <h2>Cadastro de Cargo</h2>
            <form action="${pageContext.request.contextPath}${URL_BASE}/CargoControlador"
                  method="get">
                <!-- Campo oculto para código do cargo e operação (cadastrar, editar, etc.) -->
                <input type="hidden" name="codCargo" value="${codCargo}">
                <input type="hidden" name="opcao" value="${opcao != null ? opcao : 'cadastrar'}">

                <!-- Nome do Cargo -->
                <label for="nomeCargo">Nome do Cargo:</label><br>
                <input type="text" id="nomeCargo" name="nomeCargo" value="${nomeCargo}" size="40" required><br><br>

                <!-- Salário Inicial -->
                <label for="salarioInicial">Salário Inicial:</label><br>
                <input type="text" id="salarioInicial" name="salarioInicial" 
                       value="${salarioInicial}" 
                       oninput="this.value = this.value.replace(/[^0-9.]/g, '')" required><br><br>

                <!-- Comissão -->
                <label for="comissao">Comissão (%):</label><br>
                <input type="text" id="comissao" name="comissao" 
                       value="${comissao}" 
                       oninput="this.value = this.value.replace(/[^0-9.]/g, '')"><br><br>

                <!-- Mensagem de feedback (caso exista) -->
                <h3 style="color: #0066cc;">${mensagem}</h3>

                <!-- Botão de ação (Cadastrar ou Atualizar) -->
                <button type="submit" name="opcao" value="${opcao == 'confirmarEditar' ? 'confirmarEditar' : (opcao == 'confirmarExcluir' ? 'confirmarExcluir' : 'cadastrar')}">
                    ${opcao == 'confirmarEditar' ? 'Editar' : (opcao == 'confirmarExcluir' ? 'Excluir' : 'Cadastrar')}
                </button>

                <!-- Botão de Cancelar -->
                <a href="${pageContext.request.contextPath}${URL_BASE}/CargoControlador?opcao=cancelar">
                    <button id="btnCancelar" class="botao-cancelar" type="button">Cancelar</button>
                </a>

                <a href="${pageContext.request.contextPath}${URL_BASE}/CargoControlador?opcao=listar">
                    <button class="botao-listar" type="button">Listar Tudo</button>
                </a>
            </form>



            <hr>

            <h2>Lista de Cargos</h2>
            
            <div class="aviso-mobile-tabela">
                <p>A listagem detalhada está disponível apenas em telas maiores (tablets ou desktops) para garantir a visualização completa de todos os dados.</p>
                <p style="margin-top: 10px;">Por favor, gire seu dispositivo ou use um monitor maior.</p>
            </div>
            <table class="tabela-categorias">
                <tr>
                    <th>Código</th>
                    <th>Nome</th>
                    <th>Salário Inicial</th>
                    <th>Salário Final</th>
                    <th>Comissão</th>
                    <th>Ações</th>
                </tr>
                <c:forEach var="cargo" items="${listaCargo}">
                    <tr>
                        <td>${cargo.codCargo}</td>
                        <td>${cargo.nomeCargo}</td>
                        <td>R$${cargo.salarioInicial}</td>
                        <td>R$${cargo.salarioFinal}</td>
                        <td>${cargo.comissao}%</td>
                        <td class="td-acoes">
                            <!-- Botão para editar cargo -->
                            <form style="margin:0;" action="${pageContext.request.contextPath}${URL_BASE}/CargoControlador" method="get">
                                <input type="hidden" name="opcao" value="editar">
                                <input type="hidden" name="codCargo" value="${cargo.codCargo}">
                                <input type="hidden" name="nomeCargo" value="${cargo.nomeCargo}">
                                <input type="hidden" name="salarioInicial" value="${cargo.salarioInicial}">
                                <input type="hidden" name="comissao" value="${cargo.comissao}">
                                <button class="botao-editar" type="submit">Editar</button>
                            </form>

                            <!-- Botão para excluir cargo -->
                            <form style="margin:0;" action="${pageContext.request.contextPath}${URL_BASE}/CargoControlador" method="get">
                                <input type="hidden" name="opcao" value="excluir">
                                <input type="hidden" name="codCargo" value="${cargo.codCargo}">
                                <input type="hidden" name="nomeCargo" value="${cargo.nomeCargo}">
                                <input type="hidden" name="salarioInicial" value="${cargo.salarioInicial}">
                                <input type="hidden" name="comissao" value="${cargo.comissao}">
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
