package com.mycompany.luisferreira3infoh.controlador;

import com.mycompany.luisferreira3infoh.modelo.dao.CargoDAO;
import com.mycompany.luisferreira3infoh.modelo.dao.entidade.Cargo;
import com.mycompany.luisferreira3infoh.servico.WebConstante;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;


@WebServlet(WebConstante.BASE_PATH + "/CargoControlador")
public class CargoControlador extends HttpServlet {
    private CargoDAO objCargoDao;
    private Cargo objCargo;
    String nomeCargo, salarioInicial,salarioFinal, comissao, codCargo;

    @Override
    public void init() {
        objCargoDao = new CargoDAO();
        objCargo = new Cargo();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            String opcao = request.getParameter("opcao");
            if (opcao == null || opcao.isEmpty()) {
                opcao = "cadastrar";
            }

            nomeCargo = request.getParameter("nomeCargo");
            salarioInicial = request.getParameter("salarioInicial");
            //salarioFinal = request.getParameter("salarioFinal");
            codCargo = request.getParameter("codCargo");
            comissao = request.getParameter("comissao");

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

    // Método para listar categorias
    protected void encaminharParaPagina(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<Cargo> listaCargo = objCargoDao.buscarTodosCargos();
        request.setAttribute("listaCargo", listaCargo);
        RequestDispatcher rd = request.getRequestDispatcher("/CadastroCargo.jsp");
        rd.forward(request, response);
    }

    // Método para cadastrar uma nova categoria
    protected void cadastrar(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        objCargo.setNomeCargo(nomeCargo);

        try {
            objCargo.setSalarioInicial(Double.parseDouble(salarioInicial.trim()));
        } catch (NumberFormatException e) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Salário inválido");
            return;
        }

        if (comissao != null && !comissao.trim().isEmpty()) {
            try {
                objCargo.setComissao(Integer.parseInt(comissao.trim()));
            } catch (NumberFormatException e) {
                response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Comissão inválida");
                return;
            }
        } else {
            objCargo.setComissao(0);
        }

        objCargoDao.salvar(objCargo);
        encaminharParaPagina(request, response);
    }

    // Método para editar uma categoria
    protected void editar(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setAttribute("codCargo", codCargo);
        request.setAttribute("nomeCargo", nomeCargo);
        request.setAttribute("salarioInicial", salarioInicial);
        request.setAttribute("comissao", comissao);
        request.setAttribute("mensagem", "Edite os dados e clique em 'Editar'");
        request.setAttribute("opcao", "confirmarEditar");
        encaminharParaPagina(request, response);
    }

    // Método para confirmar a edição de um cargo
    protected void confirmarEditar(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String nomeCargo = request.getParameter("nomeCargo");
        String salarioInicial = request.getParameter("salarioInicial");
        String comissao = request.getParameter("comissao");
        String codCargo = request.getParameter("codCargo");

        Cargo objCargo = new Cargo();
        objCargo.setCodCargo(Integer.valueOf(codCargo));
        objCargo.setNomeCargo(nomeCargo);

        try {
            objCargo.setSalarioInicial(Double.parseDouble(salarioInicial.trim()));
        } catch (NumberFormatException e) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Dados numéricos inválidos");
            return;
        }

        if (comissao != null && !comissao.trim().isEmpty()) {
            try {
                objCargo.setComissao(Integer.parseInt(comissao.trim()));
            } catch (NumberFormatException e) {
                response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Comissão inválida");
                return;
            }
        } else {
            objCargo.setComissao(0);
        }

        objCargoDao.alterar(objCargo);
        encaminharParaPagina(request, response);
    }

    // Método para excluir uma categoria
    protected void excluir(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setAttribute("codCargo", codCargo);
        request.setAttribute("nomeCargo", nomeCargo);
        request.setAttribute("salarioInicial", salarioInicial);
        request.setAttribute("comissao", comissao);
        request.setAttribute("mensagem", "Tem certeza que deseja excluir esse cargo?");
        request.setAttribute("opcao", "confirmarExcluir");
        encaminharParaPagina(request, response);
    }

    // Método para confirmar a exclusão de uma categoria
    protected void confirmarExcluir(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        objCargo.setCodCargo(Integer.valueOf(codCargo));
        objCargoDao.excluir(objCargo);
        encaminharParaPagina(request, response);
    }
    
    protected void cancelar (HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setAttribute("codCargo", "");
        request.setAttribute("nomeCargo", "");
        request.setAttribute("salarioInicial", "");
        request.setAttribute("comissao", "");
        request.setAttribute("opcao", "cadastrar");
        objCargo.setCodCargo(null);
        encaminharParaPagina(request, response);
    }
}


