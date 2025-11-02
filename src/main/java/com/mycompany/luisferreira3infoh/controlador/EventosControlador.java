package com.mycompany.luisferreira3infoh.controlador;

import com.mycompany.luisferreira3infoh.modelo.dao.EventosDAO;
import com.mycompany.luisferreira3infoh.modelo.dao.entidade.Eventos;
import com.mycompany.luisferreira3infoh.servico.WebConstante;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;


@WebServlet(WebConstante.BASE_PATH + "/EventosControlador")
public class EventosControlador extends HttpServlet {
    private EventosDAO objEventosDao;
    private Eventos objEventos;
    String nomeEvento,tipoEvento, localEvento, dataInicioStr, dataFimStr, descricao, codEventos;

    @Override
    public void init() {
        objEventosDao = new EventosDAO();
        objEventos = new Eventos();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            String opcao = request.getParameter("opcao");
            if (opcao == null || opcao.isEmpty()) {
                opcao = "cadastrar";
            }

            nomeEvento = request.getParameter("nomeEvento");
            tipoEvento = request.getParameter("tipoEvento");
            localEvento = request.getParameter("localEvento");
            descricao = request.getParameter("descricao");
            codEventos = request.getParameter("codEventos");
            dataInicioStr = request.getParameter("dataInicio");
            dataFimStr = request.getParameter("dataFim");
            descricao = request.getParameter("descricao");
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

    // Método para listar eventos
    protected void encaminharParaPagina(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<Eventos> listaEventos = objEventosDao.buscarTodosEventos();
        request.setAttribute("listaEventos", listaEventos);
        RequestDispatcher rd = request.getRequestDispatcher("/CadastroEventos.jsp");
        rd.forward(request, response);
    }

    // Método para cadastrar um novo evento
    protected void cadastrar(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        objEventos.setNomeEvento(nomeEvento);
        objEventos.setTipoEvento(tipoEvento);
        objEventos.setLocalEvento(localEvento);
        objEventos.setDescricao(descricao);
        
        try {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm");
        LocalDateTime dataInicio = LocalDateTime.parse(dataInicioStr, formatter);
        LocalDateTime dataFim = LocalDateTime.parse(dataFimStr, formatter);

        objEventos.setDataInicio(dataInicio);
        objEventos.setDataFim(dataFim);

    } catch (DateTimeParseException e) {
        e.printStackTrace();
        request.setAttribute("erro", "Formato de data inválido");
        request.getRequestDispatcher("/erro.jsp").forward(request, response);
    }
        objEventosDao.salvar(objEventos);
        encaminharParaPagina(request, response);
    }

    // Método para editar uma categoria
    protected void editar(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setAttribute("codEventos", codEventos);
        request.setAttribute("nomeEvento", nomeEvento);
        request.setAttribute("tipoEvento", tipoEvento);
        request.setAttribute("localEvento", localEvento);
        request.setAttribute("dataInicio", dataInicioStr);
        request.setAttribute("dataFim", dataFimStr);
        request.setAttribute("descricao", descricao);
        request.setAttribute("mensagem", "Edite os dados e clique em 'Editar'");
        request.setAttribute("opcao", "confirmarEditar");
        encaminharParaPagina(request, response);
    }

    // Método para confirmar a edição de um cargo
protected void confirmarEditar(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    /*String nomeEvento = request.getParameter("nomeEvento");
    String tipoEvento = request.getParameter("tipoEvento");
    String localEvento = request.getParameter("localEvento");
    String descricao = request.getParameter("descricao");
    String dataInicioStr = request.getParameter("dataInicio");
    String dataFimStr = request.getParameter("dataFim");
    String codEventos = request.getParameter("codEventos"); // vem como String*/

    try {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm");
        LocalDateTime dataInicio = LocalDateTime.parse(dataInicioStr, formatter);
        LocalDateTime dataFim = LocalDateTime.parse(dataFimStr, formatter);

        Eventos objEvento = new Eventos();
        objEvento.setCodEventos(Integer.valueOf(codEventos));
        objEvento.setNomeEvento(nomeEvento);
        objEvento.setTipoEvento(tipoEvento);
        objEvento.setLocalEvento(localEvento);
        objEvento.setDescricao(descricao);
        objEvento.setDataInicio(dataInicio);
        objEvento.setDataFim(dataFim);

        objEventosDao.alterar(objEvento); // chama o DAO que faz o update
        encaminharParaPagina(request, response);

    } catch (DateTimeParseException e) {
        e.printStackTrace();
        request.setAttribute("erro", "Erro ao converter a data");
        request.getRequestDispatcher("/erro.jsp").forward(request, response);
    }
}


    // Método para excluir um evento
    protected void excluir(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setAttribute("codEventos", codEventos);
        request.setAttribute("nomeEvento", nomeEvento);
        request.setAttribute("tipoEvento", tipoEvento);
        request.setAttribute("localEvento", localEvento);
        request.setAttribute("dataInicio", dataInicioStr);
        request.setAttribute("dataFim", dataFimStr);
        request.setAttribute("descricao", descricao);
        request.setAttribute("mensagem", "Tem certeza que deseja excluir esse evento?");
        request.setAttribute("opcao", "confirmarExcluir");
        encaminharParaPagina(request, response);
    }

    // Método para confirmar a exclusão de uma categoria
    protected void confirmarExcluir(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        objEventos.setCodEventos(Integer.valueOf(codEventos));
        objEventosDao.excluir(objEventos);
        encaminharParaPagina(request, response);
    }
    
    protected void cancelar (HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setAttribute("codEventos", "");
        request.setAttribute("nomeEvento", "");
        request.setAttribute("tipoEvento", "");
        request.setAttribute("localEvento", "");
        request.setAttribute("dataInicio", "");
        request.setAttribute("dataFim", "");
        request.setAttribute("descricao", "");
        request.setAttribute("opcao", "cadastrar");
        objEventos.setCodEventos(null);
        encaminharParaPagina(request, response);
    }
}


