/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.luisferreira3infoh.controlador;

import com.mycompany.luisferreira3infoh.modelo.dao.CategoriaDAO;
import com.mycompany.luisferreira3infoh.modelo.dao.LancheDAO;
import com.mycompany.luisferreira3infoh.modelo.dao.entidade.Categoria;
import com.mycompany.luisferreira3infoh.modelo.dao.entidade.Lanche;
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
@WebServlet(WebConstante.BASE_PATH + "/LancheControlador")
public class LancheControlador extends HttpServlet {

    private LancheDAO objLancheDao;
    private CategoriaDAO objCategoriaDao;
    private Lanche objLanche;
    private Categoria categoria;
    String codLanche, nomeLanche, precoLanche, descricao, lucroLanches, codCategoria;

    @Override
    public void init() {
        objLancheDao = new LancheDAO();
        objCategoriaDao = new CategoriaDAO();
        objLanche = new Lanche();
        categoria = new Categoria();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            String opcao = request.getParameter("opcao");
            if (opcao == null || opcao.isEmpty()) {
                opcao = "cadastrar";
            }

            codLanche = request.getParameter("codLanche");
            nomeLanche = request.getParameter("nomeLanche");
            precoLanche = request.getParameter("precoLanche");
            descricao = request.getParameter("descricao");
            lucroLanches = request.getParameter("lucroLanches");
            codCategoria = request.getParameter("codCategoria");

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
        List<Lanche> listaLanche = objLancheDao.buscarTodosLanches();
        request.setAttribute("listaLanche", listaLanche);

        List<Categoria> listaCategoria = objCategoriaDao.buscarTodasCategorias();
        request.setAttribute("listaCategoria", listaCategoria);

        RequestDispatcher rd = request.getRequestDispatcher("/CadastroLanche.jsp");
        rd.forward(request, response);
    }

    protected void cadastrar(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        objLanche.setNomeLanche(nomeLanche);
        objLanche.setPrecoLanche(Double.valueOf(precoLanche));
        objLanche.setDescricao(descricao);
        objLanche.setLucroLanches(Integer.valueOf(lucroLanches));
        categoria.setCodCategoria(Integer.valueOf(codCategoria));
        objLanche.setCategoria(categoria);
        objLancheDao.salvar(objLanche);
        encaminharParaPagina(request, response);
    }

    protected void editar(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        request.setAttribute("codLanche", codLanche);
        request.setAttribute("nomeLanche", nomeLanche);
        request.setAttribute("precoLanche", precoLanche);
        request.setAttribute("descricao", descricao);
        request.setAttribute("lucroLanches", lucroLanches);
        request.setAttribute("codCategoria", codCategoria);
        request.setAttribute("mensagem", "Edite os dados e clique em 'Editar'");
        request.setAttribute("opcao", "confirmarEditar");

        encaminharParaPagina(request, response);
    }

    protected void confirmarEditar(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        objLanche.setCodLanche(Integer.valueOf(codLanche));
        objLanche.setNomeLanche(nomeLanche);
        objLanche.setPrecoLanche(Double.valueOf(precoLanche));
        objLanche.setDescricao(descricao);
        objLanche.setLucroLanches(Integer.valueOf(lucroLanches));
        
        categoria.setCodCategoria(Integer.valueOf(codCategoria));
        objLanche.setCategoria(categoria);
        objLancheDao.alterar(objLanche);
        encaminharParaPagina(request, response);
    }

    protected void excluir(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        request.setAttribute("codLanche", codLanche);
        request.setAttribute("nomeLanche", nomeLanche);
        request.setAttribute("precoLanche", precoLanche);
        request.setAttribute("descricao", descricao);
        request.setAttribute("lucroLanches", lucroLanches);
        request.setAttribute("codCategoria", codCategoria);
        request.setAttribute("mensagem", "Tem certeza que deseja excluir esse lanche?");
        request.setAttribute("opcao", "confirmarExcluir");

        encaminharParaPagina(request, response);
    }

    protected void confirmarExcluir(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        objLanche.setCodLanche(Integer.valueOf(codLanche));
        objLancheDao.excluir(objLanche);
        encaminharParaPagina(request, response);
    }

    protected void cancelar(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setAttribute("codLanche", "");
        request.setAttribute("nomeLanche", "");
        request.setAttribute("precoLanche", "");
        request.setAttribute("descricao", "");
        request.setAttribute("lucroLanches", "");
        request.setAttribute("codCategoria", "");
        request.setAttribute("opcao", "cadastrar");

        encaminharParaPagina(request, response);
    }
}
