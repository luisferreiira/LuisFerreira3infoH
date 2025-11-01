/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.luisferreira3infoh.controlador;

import com.mycompany.luisferreira3infoh.modelo.dao.BrinquedoDAO;
import com.mycompany.luisferreira3infoh.modelo.dao.IngressoBrinquedosDAO;
import com.mycompany.luisferreira3infoh.modelo.dao.entidade.Brinquedo;
import com.mycompany.luisferreira3infoh.modelo.dao.entidade.IngressoBrinquedos;
import com.mycompany.luisferreira3infoh.servico.WebConstante;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 *
 * @author
 */
@WebServlet(WebConstante.BASE_PATH + "/IngressoBrinquedosControlador")
public class IngressoBrinquedosControlador extends HttpServlet {

    private IngressoBrinquedosDAO objIBDao;
    private BrinquedoDAO objBrinquedoDao;
    private IngressoBrinquedos objIB;
    private Brinquedo brinquedo;
    String codIngresso, tipoIngresso, valor, lucroIB, codBrinquedo;

    @Override
    public void init() {
        objIBDao = new IngressoBrinquedosDAO();
        objBrinquedoDao = new BrinquedoDAO();
        objIB= new IngressoBrinquedos();
        brinquedo = new Brinquedo();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            String opcao = request.getParameter("opcao");
            if (opcao == null || opcao.isEmpty()) {
                opcao = "cadastrar";
            }

            codIngresso = request.getParameter("codIngresso");
            tipoIngresso = request.getParameter("tipoIngresso");
            valor = request.getParameter("valor");
            lucroIB = request.getParameter("lucroIB");
            codBrinquedo = request.getParameter("codBrinquedo");

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
        List<IngressoBrinquedos> listaIB = objIBDao.buscarTodosIngressoBrinquedos();
        request.setAttribute("listaIB", listaIB);

        List<Brinquedo> listaBrinquedo = objBrinquedoDao.buscarTodosBrinquedos();
        request.setAttribute("listaBrinquedo", listaBrinquedo);

        RequestDispatcher rd = request.getRequestDispatcher("/CadastroIngressoBrinquedos.jsp");
        rd.forward(request, response);
    }

    protected void cadastrar(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        objIB.setTipoIngresso(tipoIngresso);
        objIB.setValor(Double.valueOf(valor));
        objIB.setLucroIB(Integer.valueOf(lucroIB));
        brinquedo.setCodBrinquedo(Integer.valueOf(codBrinquedo));
        objIB.setBrinquedo(brinquedo);
        objIBDao.salvar(objIB);
        encaminharParaPagina(request, response);
    }

    protected void editar(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        request.setAttribute("codIngresso", codIngresso);
        request.setAttribute("tipoIngresso", tipoIngresso);
        request.setAttribute("valor", valor);
        request.setAttribute("lucroIB", lucroIB);
        request.setAttribute("codBrinquedo", codBrinquedo);
        request.setAttribute("mensagem", "Edite os dados e clique em 'Editar'");
        request.setAttribute("opcao", "confirmarEditar");

        encaminharParaPagina(request, response);
    }

    protected void confirmarEditar(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        objIB.setCodIngresso(Integer.valueOf(codIngresso));
        objIB.setTipoIngresso(tipoIngresso);
        objIB.setValor(Double.valueOf(valor));
        objIB.setLucroIB(Integer.valueOf(lucroIB));
        brinquedo.setCodBrinquedo(Integer.valueOf(codBrinquedo));
        objIB.setBrinquedo(brinquedo);
        objIBDao.alterar(objIB);
        encaminharParaPagina(request, response);
    }

    protected void excluir(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        request.setAttribute("codIngresso", codIngresso);
        request.setAttribute("tipoIngresso", tipoIngresso);
        request.setAttribute("valor", valor);
        request.setAttribute("lucroIB", lucroIB);
        request.setAttribute("codBrinquedo", codBrinquedo);
        request.setAttribute("mensagem", "Tem certeza que deseja excluir esse ingresso?");
        request.setAttribute("opcao", "confirmarExcluir");

        encaminharParaPagina(request, response);
    }

    protected void confirmarExcluir(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        objIB.setCodIngresso(Integer.valueOf(codIngresso));
        objIBDao.excluir(objIB);
        encaminharParaPagina(request, response);
    }

    protected void cancelar(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setAttribute("codIngresso", "");
        request.setAttribute("tipoIngresso", "");
        request.setAttribute("valor", "");
        request.setAttribute("lucroIB", "");
        request.setAttribute("codBrinquedo", "");
        request.setAttribute("opcao", "cadastrar");

        encaminharParaPagina(request, response);
    }
}
