package com.mycompany.luisferreira3infoh.controlador;

import com.mycompany.luisferreira3infoh.modelo.dao.BrinquedoDAO;
import com.mycompany.luisferreira3infoh.modelo.dao.entidade.Brinquedo;
import com.mycompany.luisferreira3infoh.servico.WebConstante;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;


@WebServlet(WebConstante.BASE_PATH + "/BrinquedoControlador")
public class BrinquedoControlador extends HttpServlet {
    private BrinquedoDAO objBrinquedoDao;
    private Brinquedo objBrinquedo;
    String codBrinquedo, nomeBrinquedo, capacidadeMaxima,capacidadeAtual,tipoBrinquedo,idadeRestrita,funcionamento;

    @Override
    public void init() {
        objBrinquedoDao = new BrinquedoDAO();
        objBrinquedo = new Brinquedo();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            String opcao = request.getParameter("opcao");
            if (opcao == null || opcao.isEmpty()) {
                opcao = "cadastrar";
            }
            
            codBrinquedo = request.getParameter("codBrinquedo");
            nomeBrinquedo = request.getParameter("nomeBrinquedo");
            tipoBrinquedo = request.getParameter("tipoBrinquedo");
            capacidadeMaxima = request.getParameter("capacidadeMaxima");
            capacidadeAtual = request.getParameter("capacidadeAtual");
            idadeRestrita = request.getParameter("idadeRestrita");
            funcionamento = request.getParameter("funcionamento");
        

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
        List<Brinquedo> listaBrinquedo = objBrinquedoDao.buscarTodosBrinquedos();
        request.setAttribute("listaBrinquedo", listaBrinquedo);
        RequestDispatcher rd = request.getRequestDispatcher("/CadastroBrinquedo.jsp");
        rd.forward(request, response);
    }

    // Método para cadastrar um novo brinquedo
    protected void cadastrar(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        objBrinquedo.setNomeBrinquedo(nomeBrinquedo);
        objBrinquedo.setCapacidadeMaxima(Integer.parseInt(capacidadeMaxima));
        objBrinquedo.setFuncionamento(Integer.parseInt(funcionamento));
        objBrinquedo.setIdadeRestrita(Integer.parseInt(idadeRestrita));
        objBrinquedo.setTipoBrinquedo(tipoBrinquedo);
        objBrinquedoDao.salvar(objBrinquedo);
        encaminharParaPagina(request, response);
    }

    // Método para editar 
    protected void editar(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setAttribute("codBrinquedo", codBrinquedo);
        request.setAttribute("nomeBrinquedo", nomeBrinquedo);
        request.setAttribute("tipoBrinquedo", tipoBrinquedo);
        request.setAttribute("idadeRestrita", idadeRestrita);
        request.setAttribute("capacidadeMaxima", capacidadeMaxima);
        request.setAttribute("funcionamento", funcionamento);
        request.setAttribute("mensagem", "Edite os dados e clique em 'Editar'");
        request.setAttribute("opcao", "confirmarEditar");
        RequestDispatcher rd = request.getRequestDispatcher("/CadastroBrinquedo.jsp");
        rd.forward(request, response);
    }

    // Método para confirmar a edição de uma categoria
    protected void confirmarEditar(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        objBrinquedo.setCodBrinquedo(Integer.valueOf(codBrinquedo));
        objBrinquedo.setNomeBrinquedo(nomeBrinquedo);
        objBrinquedo.setCapacidadeMaxima(Integer.parseInt(capacidadeMaxima));
        objBrinquedo.setFuncionamento(Integer.parseInt(funcionamento));
        objBrinquedo.setIdadeRestrita(Integer.parseInt(idadeRestrita));
        objBrinquedo.setTipoBrinquedo(tipoBrinquedo);
        objBrinquedoDao.alterar(objBrinquedo);
        encaminharParaPagina(request, response);
    }

    // Método para excluir 
    protected void excluir(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setAttribute("codBrinquedo", codBrinquedo);
        request.setAttribute("nomeBrinquedo", nomeBrinquedo);
        request.setAttribute("tipoBrinquedo", tipoBrinquedo);
        request.setAttribute("idadeRestrita", idadeRestrita);
        request.setAttribute("capacidadeMaxima", capacidadeMaxima);
        request.setAttribute("funcionamento", funcionamento);
        request.setAttribute("mensagem", "Tem certeza que deseja excluir esse brinquedo?");
        request.setAttribute("opcao", "confirmarExcluir");
        encaminharParaPagina(request, response);
        //RequestDispatcher rd = request.getRequestDispatcher("/CadastroCategoria.jsp");
        //rd.forward(request, response);
    }

    // Método para confirmar a exclusão
    protected void confirmarExcluir(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        objBrinquedo.setCodBrinquedo(Integer.valueOf(codBrinquedo));
        objBrinquedoDao.excluir(objBrinquedo);
        encaminharParaPagina(request, response);
    }
    
    protected void cancelar (HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setAttribute("codBrinquedo", "");
        request.setAttribute("nomeBrinquedo", "");
        request.setAttribute("tipoBrinquedo", "");
        request.setAttribute("idadeRestrita", "");
        request.setAttribute("capacidadeMaxima", "");
        request.setAttribute("funcionamento", "");
        request.setAttribute("opcao", "cadastrar");
        objBrinquedo.setCodBrinquedo(null);
        encaminharParaPagina(request, response);
    }
    
}


