<%@ page contentType="text/html; charset=Latin1" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
    <head>
        <meta charset="Latin1">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>Cadastro de Funcionários</title>
        <link rel="stylesheet" href="${pageContext.request.contextPath}/estilo/cadastro.css">
    </head>
    <body>
        <header>
            <%@ include file="menuParque.jsp" %>
        </header>
        <main>
            <h2>Cadastro de Funcionários</h2>
            <form action="${pageContext.request.contextPath}${URL_BASE}/FuncionarioControlador" method="get">
                <input type="hidden" name="codFuncionario" value="${codFuncionario}">
                <input type="hidden" name="opcao" value="${opcao != null ? opcao : 'cadastrar'}">

                <!-- Nome -->
                <label for="nomeFuncionario">Nome do Funcionário:</label><br>
                <input type="text" id="nomeFuncionario" name="nomeFuncionario" value="${nomeFuncionario}" size="40" required><br><br>

                <!-- CPF -->
                <label for="cpf">CPF:</label><br>
                <input type="text" id="cpf" name="cpf" value="${cpf}" size="20" required><br><br>

                <!-- Carteira de Trabalho -->
                <label for="carteiraTrabalho">Carteira de Trabalho:</label><br>
                <input type="text" id="carteiraTrabalho" name="carteiraTrabalho" value="${carteiraTrabalho}" required><br><br>

                <!-- Data de Admissão -->
                <label for="dataAdmissao">Data de Admissão:</label><br>
                <input type="date" id="dataAdmissao" name="dataAdmissao" value="${dataAdmissao}" required><br><br>

                <!-- Data de Demissão -->
                <label for="dataDemissao">Data de Demissão:</label><br>
                <input type="date" id="dataDemissao" name="dataDemissao" value="${dataDemissao}"><br><br>

                <!-- Email -->
                <label for="email">E-mail:</label><br>
                <input type="email" id="email" name="email" value="${email}" required><br><br>

                <!-- Senha -->
                <label for="senha">Senha:</label><br>
                <input type="password" id="senha" name="senha" value="${senha}" required><br><br>

                <!-- Cargo -->
                <label for="codCargo">Cargo:</label><br>
                <select id="codCargo" name="codCargo" required>
                    <option value="">-- Selecione o Cargo --</option>
                    <c:forEach var="c" items="${listaCargo}">
                        <option value="${c.codCargo}" ${codCargo == c.codCargo ? 'selected' : ''}>${c.nomeCargo}</option>
                    </c:forEach>
                </select>
                <br><br>


                <!-- Mensagem de feedback -->
                <p style="color: #0066cc;">${mensagem}</p>

                <!-- Botão de Ação -->
                <button type="submit" name="opcao"
                        value="${opcao == 'confirmarEditar' ? 'confirmarEditar' : (opcao == 'confirmarExcluir' ? 'confirmarExcluir' : 'cadastrar')}">
                    ${opcao == 'confirmarEditar' ? 'Editar' : (opcao == 'confirmarExcluir' ? 'Excluir' : 'Cadastrar')}
                </button>

                <!-- Botões Extras -->
                <a href="${pageContext.request.contextPath}${URL_BASE}/FuncionarioControlador?opcao=cancelar">
                    <button id="btnCancelar" class="botao-cancelar" type="button">Cancelar</button>
                </a>

                <a href="${pageContext.request.contextPath}${URL_BASE}/FuncionarioControlador?opcao=listar">
                    <button class="botao-listar" type="button">Listar Tudo</button>
                </a>
            </form>

            <hr>

            <h2>Lista de Funcionários</h2>

            <div class="aviso-mobile-tabela">
                <p>A listagem detalhada está disponível apenas em telas maiores (tablets ou desktops) para garantir a visualização completa de todos os dados.</p>
                <p style="margin-top: 10px;">Por favor, gire seu dispositivo ou use um monitor maior.</p>
            </div>

            <table class="tabela-categorias">
                <tr>
                    <th>Código</th>
                    <th>Nome</th>
                    <th>CPF</th>
                    <th>Carteira de Trabalho</th>
                    <th>Data de Admissão</th>
                    <th>Data de Demissão</th>
                    <th>E-mail</th>
                    <th>Senha</th>
                    <th>Cargo</th>
                    <th>Ações</th>
                </tr>
                <c:forEach var="f" items="${listaFuncionario}">
                    <tr>
                        <td>${f.codFuncionario}</td>
                        <td>${f.nomeFuncionario}</td>
                        <td>${f.cpf}</td>
                        <td>${f.carteiraTrabalho}</td>
                        <td>${f.dataAdmissaoFormatada}</td>
                        <td>${f.dataDemissaoFormatada}</td>
                        <td>${f.email}</td>
                        <td>${f.senha}</td>
                        <td>
                            <c:choose>
                                <c:when test="${not empty f.cargo}">
                                    ${f.cargo.nomeCargo}
                                </c:when>
                                <c:otherwise>
                                    Sem cargo
                                </c:otherwise>
                            </c:choose>
                        </td>

                        <td class="td-acoes">
                            <!-- Editar -->
                            <form style="margin:0;" action="${pageContext.request.contextPath}${URL_BASE}/FuncionarioControlador" method="get">
                                <input type="hidden" name="opcao" value="editar">
                                <input type="hidden" name="codFuncionario" value="${f.codFuncionario}">
                                <input type="hidden" name="nomeFuncionario" value="${f.nomeFuncionario}">
                                <input type="hidden" name="cpf" value="${f.cpf}">
                                <input type="hidden" name="carteiraTrabalho" value="${f.carteiraTrabalho}">
                                <input type="hidden" name="dataAdmissao" value="${f.dataAdmissao}">
                                <input type="hidden" name="dataDemissao" value="${f.dataDemissao}">
                                <input type="hidden" name="email" value="${f.email}">
                                <input type="hidden" name="senha" value="${f.senha}">
                                <input type="hidden" name="codCargo" value="${f.cargo.codCargo}">
                                <button class="botao-editar" type="submit">Editar</button>
                            </form>

                            <!-- Excluir -->
                            <form style="margin:0;" action="${pageContext.request.contextPath}${URL_BASE}/FuncionarioControlador" method="get">
                                <input type="hidden" name="opcao" value="excluir">
                                <input type="hidden" name="codFuncionario" value="${f.codFuncionario}">
                                <input type="hidden" name="nomeFuncionario" value="${f.nomeFuncionario}">
                                <input type="hidden" name="cpf" value="${f.cpf}">
                                <input type="hidden" name="carteiraTrabalho" value="${f.carteiraTrabalho}">
                                <input type="hidden" name="dataAdmissao" value="${f.dataAdmissao}">
                                <input type="hidden" name="dataDemissao" value="${f.dataDemissao}">
                                <input type="hidden" name="email" value="${f.email}">
                                <input type="hidden" name="senha" value="${f.senha}">
                                <input type="hidden" name="codCargo" value="${f.cargo.codCargo}">
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
