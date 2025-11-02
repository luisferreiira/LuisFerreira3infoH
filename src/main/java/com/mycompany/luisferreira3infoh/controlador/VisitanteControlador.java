package com.mycompany.luisferreira3infoh.controlador;

import com.mycompany.luisferreira3infoh.modelo.dao.VisitanteDAO;
import com.mycompany.luisferreira3infoh.modelo.dao.entidade.Visitante;
import com.mycompany.luisferreira3infoh.servico.WebConstante;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;

@WebServlet(WebConstante.BASE_PATH + "/VisitanteControlador")
public class VisitanteControlador extends HttpServlet {
    private VisitanteDAO objVisitanteDao;

    @Override
    public void init() {
        objVisitanteDao = new VisitanteDAO();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            String opcao = request.getParameter("opcao");
            if (opcao == null || opcao.isEmpty()) {
                opcao = "cadastrar";
            }

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

    // Método para listar
    protected void encaminharParaPagina(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<Visitante> listaVisitante = objVisitanteDao.buscarTodosVisitantes();
        request.setAttribute("listaVisitante", listaVisitante);
        RequestDispatcher rd = request.getRequestDispatcher("/CadastroVisitante.jsp");
        rd.forward(request, response);
    }

    // Método para cadastrar
    protected void cadastrar(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Visitante objVisitante = new Visitante();

        String nomeVisitante = request.getParameter("nomeVisitante");
        String dataNascimentoStr = request.getParameter("dataNascimento");
        String email = request.getParameter("email");
        String senha = request.getParameter("senha");

        objVisitante.setNomeVisitante(nomeVisitante);
        objVisitante.setEmail(email);
        objVisitante.setSenha(senha);

        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            LocalDate dataNascimento = LocalDate.parse(dataNascimentoStr, formatter);
            objVisitante.setDataNascimento(dataNascimento);
        } catch (DateTimeParseException e) {
            request.setAttribute("erro", "Formato de data de nascimento inválido.");
            request.getRequestDispatcher("/erro.jsp").forward(request, response);
            return;
        }

        objVisitanteDao.salvar(objVisitante);
        encaminharParaPagina(request, response);
    }

    // Método para editar
    protected void editar(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String codVisitante = request.getParameter("codVisitante");
        String nomeVisitante = request.getParameter("nomeVisitante");
        String dataNascimentoStr = request.getParameter("dataNascimento");
        String email = request.getParameter("email");
        String senha = request.getParameter("senha");

        request.setAttribute("codVisitante", codVisitante);
        request.setAttribute("nomeVisitante", nomeVisitante);
        request.setAttribute("dataNascimento", dataNascimentoStr);
        request.setAttribute("email", email);
        request.setAttribute("senha", senha);
        request.setAttribute("mensagem", "Edite os dados e clique em 'Editar'");
        request.setAttribute("opcao", "confirmarEditar");

        encaminharParaPagina(request, response);
    }

    // Método para confirmar a edição
    protected void confirmarEditar(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Visitante objVisitante = new Visitante();

        String codVisitante = request.getParameter("codVisitante");
        String nomeVisitante = request.getParameter("nomeVisitante");
        String dataNascimentoStr = request.getParameter("dataNascimento");
        String email = request.getParameter("email");
        String senha = request.getParameter("senha");

        objVisitante.setCodVisitante(Integer.valueOf(codVisitante));
        objVisitante.setNomeVisitante(nomeVisitante);
        objVisitante.setEmail(email);
        objVisitante.setSenha(senha);

        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            LocalDate dataNascimento = LocalDate.parse(dataNascimentoStr, formatter);
            objVisitante.setDataNascimento(dataNascimento);
        } catch (DateTimeParseException e) {
            request.setAttribute("erro", "Formato de data de nascimento inválido.");
            request.getRequestDispatcher("/erro.jsp").forward(request, response);
            return;
        }

        objVisitanteDao.alterar(objVisitante);
        encaminharParaPagina(request, response);
    }

    // Método para excluir
    protected void excluir(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String codVisitante = request.getParameter("codVisitante");
        String nomeVisitante = request.getParameter("nomeVisitante");
        String dataNascimento = request.getParameter("dataNascimento");
        String email = request.getParameter("email");
        String senha = request.getParameter("senha");

        request.setAttribute("codVisitante", codVisitante);
        request.setAttribute("nomeVisitante", nomeVisitante);
        request.setAttribute("dataNascimento", dataNascimento);
        request.setAttribute("email", email);
        request.setAttribute("senha", senha);
        request.setAttribute("mensagem", "Tem certeza que deseja excluir esse visitante?");
        request.setAttribute("opcao", "confirmarExcluir");

        encaminharParaPagina(request, response);
    }

    // Método para confirmar a exclusão
    protected void confirmarExcluir(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String codVisitante = request.getParameter("codVisitante");
        Visitante objVisitante = new Visitante();
        objVisitante.setCodVisitante(Integer.valueOf(codVisitante));
        objVisitanteDao.excluir(objVisitante);
        encaminharParaPagina(request, response);
    }

    // Método para cancelar
    protected void cancelar(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setAttribute("codVisitante", "");
        request.setAttribute("nomeVisitante", "");
        request.setAttribute("dataNascimento", "");
        request.setAttribute("email", "");
        request.setAttribute("senha", "");
        request.setAttribute("opcao", "cadastrar");

        encaminharParaPagina(request, response);
    }
}
