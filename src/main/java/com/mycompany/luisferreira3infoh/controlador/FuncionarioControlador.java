/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.luisferreira3infoh.controlador;

import com.mycompany.luisferreira3infoh.modelo.dao.CargoDAO;
import com.mycompany.luisferreira3infoh.modelo.dao.FuncionarioDAO;
import com.mycompany.luisferreira3infoh.modelo.dao.entidade.Cargo;
import com.mycompany.luisferreira3infoh.modelo.dao.entidade.Funcionario;
import com.mycompany.luisferreira3infoh.servico.WebConstante;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;

/**
 *
 * @author
 */
@WebServlet(WebConstante.BASE_PATH + "/FuncionarioControlador")
public class FuncionarioControlador extends HttpServlet {

    private FuncionarioDAO objFuncionarioDao;
    private CargoDAO objCargoDao;
    private Funcionario objFuncionario;
    private Cargo cargo;
    String codFuncionario, nomeFuncionario, cpf, carteiraTrabalho, dataAdmissaoStr, dataDemissaoStr,email, senha, codCargo;

    @Override
    public void init() {
        objFuncionarioDao = new FuncionarioDAO();
        objCargoDao = new CargoDAO();
        objFuncionario = new Funcionario();
        cargo = new Cargo();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            String opcao = request.getParameter("opcao");
            if (opcao == null || opcao.isEmpty()) {
                opcao = "cadastrar";
            }

            codFuncionario = request.getParameter("codFuncionario");
            nomeFuncionario = request.getParameter("nomeFuncionario");
            cpf = request.getParameter("cpf");
            carteiraTrabalho = request.getParameter("carteiraTrabalho");
            dataAdmissaoStr = request.getParameter("dataAdmissao");
            dataDemissaoStr = request.getParameter("dataDemissao");
            email = request.getParameter("email");
            senha = request.getParameter("senha");
            codCargo = request.getParameter("codCargo");

            switch (opcao) {
                case "listar":
                    encaminharParaPagina(request, response);
                    break;
                case "cadastrar":
                    cadastrar(request, response);
                    break;
                case "editar":
                    editar(request, response);
                    break;
                case "confirmarEditar":
                    confirmarEditar(request, response);
                    break;
                case "excluir":
                    excluir(request, response);
                    break;
                case "confirmarExcluir":
                    confirmarExcluir(request, response);
                    break;
                case "cancelar":
                    cancelar(request, response);
                    break;
                default:
                    throw new IllegalArgumentException("Opção inválida: " + opcao);
            }
        } catch (Exception e) {
            response.getWriter().println("Erro: " + e.getMessage());
        }
    }

    protected void encaminharParaPagina(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<Funcionario> listaFuncionario = objFuncionarioDao.buscarTodosFuncionarios();
        request.setAttribute("listaFuncionario", listaFuncionario);
        List<Cargo> listaCargo = objCargoDao.buscarTodosCargos();
        request.setAttribute("listaCargo", listaCargo);
        RequestDispatcher rd = request.getRequestDispatcher("/CadastroFuncionario.jsp");
        rd.forward(request, response);
    }

    protected void cadastrar(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        objFuncionario.setNomeFuncionario(nomeFuncionario);
        objFuncionario.setCpf(cpf);
        objFuncionario.setCarteiraTrabalho(carteiraTrabalho);
        objFuncionario.setEmail(email);
        objFuncionario.setSenha(senha);

        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            objFuncionario.setDataAdmissao(LocalDate.parse(dataAdmissaoStr, formatter));

            if (dataDemissaoStr != null && !dataDemissaoStr.trim().isEmpty()) {
                objFuncionario.setDataDemissao(LocalDate.parse(dataDemissaoStr, formatter));
            } else {
                objFuncionario.setDataDemissao(null);
            }

        } catch (DateTimeParseException e) {
            request.setAttribute("erro", "Formato de data inválido.");
            request.getRequestDispatcher("/erro.jsp").forward(request, response);
            return;
        }

        cargo.setCodCargo(Integer.valueOf(codCargo));
        objFuncionario.setCargo(cargo);

        objFuncionarioDao.salvar(objFuncionario);
        encaminharParaPagina(request, response);
    }

    protected void editar(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        request.setAttribute("codFuncionario", codFuncionario);
        request.setAttribute("nomeFuncionario", nomeFuncionario);
        request.setAttribute("cpf", cpf);
        request.setAttribute("carteiraTrabalho", carteiraTrabalho);
        request.setAttribute("dataAdmissao", dataAdmissaoStr);
        request.setAttribute("dataDemissao", dataDemissaoStr);
        request.setAttribute("email", email);
        request.setAttribute("senha", senha);
        request.setAttribute("codCargo", codCargo);
        request.setAttribute("mensagem", "Edite os dados e clique em 'Editar'");
        request.setAttribute("opcao", "confirmarEditar");

        encaminharParaPagina(request, response);
    }

    protected void confirmarEditar(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        objFuncionario.setCodFuncionario(Integer.valueOf(codFuncionario));
        objFuncionario.setNomeFuncionario(nomeFuncionario);
        objFuncionario.setCpf(cpf);
        objFuncionario.setCarteiraTrabalho(carteiraTrabalho);
        objFuncionario.setEmail(email);
        objFuncionario.setSenha(senha);

        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            objFuncionario.setDataAdmissao(LocalDate.parse(dataAdmissaoStr, formatter));

            if (dataDemissaoStr != null && !dataDemissaoStr.trim().isEmpty()) {
                objFuncionario.setDataDemissao(LocalDate.parse(dataDemissaoStr, formatter));
            } else {
                objFuncionario.setDataDemissao(null);
            }

        } catch (DateTimeParseException e) {
            request.setAttribute("erro", "Formato de data inválido.");
            request.getRequestDispatcher("/erro.jsp").forward(request, response);
            return;
        }

        cargo.setCodCargo(Integer.valueOf(codCargo));
        objFuncionario.setCargo(cargo);

        objFuncionarioDao.alterar(objFuncionario);
        encaminharParaPagina(request, response);
    }

    protected void excluir(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        request.setAttribute("codFuncionario", codFuncionario);
        request.setAttribute("nomeFuncionario", nomeFuncionario);
        request.setAttribute("cpf", cpf);
        request.setAttribute("carteiraTrabalho", carteiraTrabalho);
        request.setAttribute("dataAdmissao", dataAdmissaoStr);
        request.setAttribute("dataDemissao", dataDemissaoStr);
        request.setAttribute("email", email);
        request.setAttribute("senha", senha);
        request.setAttribute("codCargo", codCargo);
        request.setAttribute("mensagem", "Tem certeza que deseja excluir esse funcionário?");
        request.setAttribute("opcao", "confirmarExcluir");

        encaminharParaPagina(request, response);
    }

    protected void confirmarExcluir(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        objFuncionario.setCodFuncionario(Integer.valueOf(codFuncionario));
        objFuncionarioDao.excluir(objFuncionario);
        encaminharParaPagina(request, response);
    }

    protected void cancelar(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setAttribute("codFuncionario", "");
        request.setAttribute("nomeFuncionario", "");
        request.setAttribute("cpf", "");
        request.setAttribute("carteiraTrabalho", "");
        request.setAttribute("dataAdmissao", "");
        request.setAttribute("dataDemissao", "");
        request.setAttribute("email", "");
        request.setAttribute("senha", "");
        request.setAttribute("codCargo", "");
        request.setAttribute("opcao", "cadastrar");

        encaminharParaPagina(request, response);
    }
}
