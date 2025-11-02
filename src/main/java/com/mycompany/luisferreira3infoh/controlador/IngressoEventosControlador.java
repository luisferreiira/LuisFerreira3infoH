/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.luisferreira3infoh.controlador;

import com.mycompany.luisferreira3infoh.modelo.dao.EventosDAO;
import com.mycompany.luisferreira3infoh.modelo.dao.IngressoEventosDAO;
import com.mycompany.luisferreira3infoh.modelo.dao.entidade.Eventos;
import com.mycompany.luisferreira3infoh.modelo.dao.entidade.IngressoEventos;
import com.mycompany.luisferreira3infoh.servico.WebConstante;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 *
 * @author
 */
@WebServlet(WebConstante.BASE_PATH + "/IngressoEventosControlador")
public class IngressoEventosControlador extends HttpServlet {

    private IngressoEventosDAO objIEDao;
    private EventosDAO objEventosDao;
    private IngressoEventos objIE;
    private Eventos evento;
    String codIngresso, tipoIngresso, valor, lucroIE, codEventos;

    @Override
    public void init() {
        objIEDao = new IngressoEventosDAO();
        objEventosDao = new EventosDAO();
        objIE = new IngressoEventos();
        evento = new Eventos();
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
            lucroIE = request.getParameter("lucroIE");
            codEventos = request.getParameter("codEventos");

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
        List<IngressoEventos> listaIE = objIEDao.buscarTodosIngressoEventos();
        request.setAttribute("listaIE", listaIE);

        List<Eventos> listaEventos = objEventosDao.buscarTodosEventos();
        request.setAttribute("listaEventos", listaEventos);

        RequestDispatcher rd = request.getRequestDispatcher("/CadastroIngressoEventos.jsp");
        rd.forward(request, response);
    }

    protected void cadastrar(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        objIE.setTipoIngresso(tipoIngresso);
        objIE.setValor(Double.valueOf(valor));
        objIE.setLucroIE(Integer.valueOf(lucroIE));
        evento.setCodEventos(Integer.valueOf(codEventos));
        objIE.setEvento(evento);
        objIEDao.salvar(objIE);
        encaminharParaPagina(request, response);
    }

    protected void editar(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        request.setAttribute("codIngresso", codIngresso);
        request.setAttribute("tipoIngresso", tipoIngresso);
        request.setAttribute("valor", valor);
        request.setAttribute("lucroIE", lucroIE);
        request.setAttribute("codEventos", codEventos);
        request.setAttribute("mensagem", "Edite os dados e clique em 'Editar'");
        request.setAttribute("opcao", "confirmarEditar");

        encaminharParaPagina(request, response);
    }

    protected void confirmarEditar(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        objIE.setCodIngresso(Integer.valueOf(codIngresso));
        objIE.setTipoIngresso(tipoIngresso);
        objIE.setValor(Double.valueOf(valor));
        objIE.setLucroIE(Integer.valueOf(lucroIE));
        evento.setCodEventos(Integer.valueOf(codEventos));
        objIE.setEvento(evento);
        objIEDao.alterar(objIE);
        encaminharParaPagina(request, response);
    }

    protected void excluir(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        request.setAttribute("codIngresso", codIngresso);
        request.setAttribute("tipoIngresso", tipoIngresso);
        request.setAttribute("valor", valor);
        request.setAttribute("lucroIE", lucroIE);
        request.setAttribute("codEventos", codEventos);
        request.setAttribute("mensagem", "Tem certeza que deseja excluir esse ingresso?");
        request.setAttribute("opcao", "confirmarExcluir");

        encaminharParaPagina(request, response);
    }

    protected void confirmarExcluir(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        objIE.setCodIngresso(Integer.valueOf(codIngresso));
        objIEDao.excluir(objIE);
        encaminharParaPagina(request, response);
    }

    protected void cancelar(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setAttribute("codIngresso", "");
        request.setAttribute("tipoIngresso", "");
        request.setAttribute("valor", "");
        request.setAttribute("lucroIE", "");
        request.setAttribute("codEventos", "");
        request.setAttribute("opcao", "cadastrar");

        encaminharParaPagina(request, response);
    }
}
