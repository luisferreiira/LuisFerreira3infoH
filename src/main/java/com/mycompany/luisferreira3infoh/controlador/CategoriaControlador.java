package com.mycompany.luisferreira3infoh.controlador;

import com.mycompany.luisferreira3infoh.modelo.dao.CategoriaDAO;
import com.mycompany.luisferreira3infoh.modelo.dao.entidade.Categoria;
import com.mycompany.luisferreira3infoh.servico.WebConstante;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import javax.swing.JOptionPane;


@WebServlet(WebConstante.BASE_PATH + "/CategoriaControlador")
public class CategoriaControlador extends HttpServlet {
    private CategoriaDAO objCategoriaDao;
    private Categoria objCategoria;
    String nomeCategoria, descricao, codigoCategoria;

    @Override
    public void init() {
        objCategoriaDao = new CategoriaDAO();
        objCategoria = new Categoria();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            String opcao = request.getParameter("opcao");
            if (opcao == null || opcao.isEmpty()) {
                opcao = "cadastrar";
            }

            nomeCategoria = request.getParameter("nomeCategoria");
            descricao = request.getParameter("descricao");
            codigoCategoria = request.getParameter("codigoCategoria");

            switch (opcao) {
                case "listar":
                    encaminharParaPagina(request, response);
                    break;
                case "cadastrar":
                    cadastrarCategoria(request, response);
                    break;
                case "editar":
                    editarCategoria(request, response);
                    break;
                case "confirmarEditar":
                    confirmarEditarCategoria(request, response);
                    break;
                case "excluir":
                    excluirCategoria(request, response);
                    break;
                case "confirmarExcluir":
                    confirmarExcluirCategoria(request, response);
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

    // Método para listar categorias
    protected void encaminharParaPagina(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<Categoria> listaCategoria = objCategoriaDao.buscarTodasCategorias();
        request.setAttribute("listaCategoria", listaCategoria);
        RequestDispatcher rd = request.getRequestDispatcher("/CadastroCategoria.jsp");
        rd.forward(request, response);
    }

    // Método para cadastrar uma nova categoria
    protected void cadastrarCategoria(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        objCategoria.setNomeCategoria(nomeCategoria);
        objCategoria.setDescricao(descricao);
        objCategoriaDao.salvar(objCategoria);
        encaminharParaPagina(request, response);
    }

    // Método para editar uma categoria
    protected void editarCategoria(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setAttribute("codigoCategoria", codigoCategoria);
        request.setAttribute("nomeCategoria", nomeCategoria);
        request.setAttribute("descricao", descricao);
        request.setAttribute("mensagem", "Edite os dados e clique em 'Editar'");
        request.setAttribute("opcao", "confirmarEditar");
        RequestDispatcher rd = request.getRequestDispatcher("/CadastroCategoria.jsp");
        rd.forward(request, response);
    }

    // Método para confirmar a edição de uma categoria
    protected void confirmarEditarCategoria(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        objCategoria.setCodCategoria(Integer.valueOf(codigoCategoria));
        objCategoria.setNomeCategoria(nomeCategoria);
        objCategoria.setDescricao(descricao);
        objCategoriaDao.alterar(objCategoria);
        encaminharParaPagina(request, response);
    }

    // Método para excluir uma categoria
    protected void excluirCategoria(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setAttribute("codigoCategoria", codigoCategoria);
        request.setAttribute("nomeCategoria", nomeCategoria);
        request.setAttribute("descricao", descricao);
        request.setAttribute("mensagem", "Tem certeza que deseja excluir essa categoria?");
        request.setAttribute("opcao", "confirmarExcluir");
        encaminharParaPagina(request, response);
        //RequestDispatcher rd = request.getRequestDispatcher("/CadastroCategoria.jsp");
        //rd.forward(request, response);
    }

    // Método para confirmar a exclusão de uma categoria
    protected void confirmarExcluirCategoria(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        objCategoria.setCodCategoria(Integer.valueOf(codigoCategoria));
        objCategoriaDao.excluir(objCategoria);
        encaminharParaPagina(request, response);
    }
    
    protected void cancelar (HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setAttribute("codCargo", "");
        request.setAttribute("nomeCargo", "");
        request.setAttribute("salarioInicial", "");
        request.setAttribute("comissao", "");
        request.setAttribute("opcao", "cadastrar");
        objCategoria.setCodCategoria(null);
        encaminharParaPagina(request, response);
    }
    
}


