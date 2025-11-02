package com.mycompany.luisferreira3infoh.controlador;

import com.mycompany.luisferreira3infoh.modelo.dao.entidade.*;
import com.mycompany.luisferreira3infoh.modelo.dao.*;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.time.ZoneId;
import java.util.List;

@WebServlet("/CompraControlador")
public class CompraControlador extends HttpServlet {

    private CompraDAO compraDAO;
    private FuncionarioDAO funcionarioDAO;
    private VisitanteDAO visitanteDAO;
    private LancheDAO lancheDAO;
    private IngressoEventosDAO ingressoEventosDAO;
    private IngressoBrinquedosDAO ingressoBrinquedosDAO;
    private CompraLancheDAO compraLancheDAO;
    private CompraIngressoBrinquedosDAO compraBrinquedoDAO;
    private CompraIngressoEventosDAO compraEventoDAO;

    @Override
    public void init() {
        compraDAO = new CompraDAO();
        funcionarioDAO = new FuncionarioDAO();
        visitanteDAO = new VisitanteDAO();
        lancheDAO = new LancheDAO();
        ingressoEventosDAO = new IngressoEventosDAO();
        ingressoBrinquedosDAO = new IngressoBrinquedosDAO();
        compraLancheDAO = new CompraLancheDAO();
        compraBrinquedoDAO = new CompraIngressoBrinquedosDAO();
        compraEventoDAO = new CompraIngressoEventosDAO();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            String opcao = request.getParameter("opcao");
            if (opcao == null || opcao.isEmpty()) {
                opcao = "listar";
            }

            switch (opcao) {
                case "minhasCompras":
                    mostrarComprasVisitante(request, response);
                    break;
                case "filtrarMinhasCompras":
                    filtrarComprasVisitantePorPeriodo(request, response);
                    break;

                case "listar":
                    encaminharParaPagina(request, response);
                    break;
                case "cadastrar":
                    cadastrar(request, response);
                    break;
                case "excluirCompraCompleta":
                    excluirCompraCompleta(request, response);
                    break;
                case "excluirLanche":
                    excluirLanche(request, response);
                    break;
                case "excluirBrinquedo":
                    excluirBrinquedo(request, response);
                    break;
                case "excluirEvento":
                    excluirEvento(request, response);
                    break;
                case "cancelar":
                    cancelar(request, response);
                    break;
                case "adicionarItemForm":
                    exibirFormularioAdicionarItem(request, response);
                    break;
                case "confirmarAdicionarItem":
                    confirmarAdicionarItem(request, response);
                    break;
                default:
                    throw new IllegalArgumentException("Opção inválida: " + opcao);
            }

        } catch (Exception e) {
            e.printStackTrace();
            response.getWriter().println("Erro: " + e.getMessage());
        }
    }

    private void encaminharParaPagina(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setAttribute("listaCompra", compraDAO.buscarTodasCompras());
        request.setAttribute("listaLanches", lancheDAO.buscarTodosLanches());
        request.setAttribute("listaIngressosBrinquedo", ingressoBrinquedosDAO.buscarTodosIngressoBrinquedos());
        request.setAttribute("listaIngressosEvento", ingressoEventosDAO.buscarTodosIngressoEventos());
        request.getRequestDispatcher("/CadastroCompras.jsp").forward(request, response);
    }

    private void cadastrar(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            String tipoPagamento = request.getParameter("tipoPagamento");
            String[] tipos = request.getParameterValues("tipo");
            String[] ids = request.getParameterValues("id");
            String[] quantidades = request.getParameterValues("quantidade");

            // Recupera o usuário logado da sessão
            Object usuarioLogado = request.getSession().getAttribute("usuarioLogado");
            Visitante visitante = null;
            if (usuarioLogado instanceof Visitante) {
                visitante = (Visitante) usuarioLogado;
            } else {
                // Se não estiver logado como visitante, redireciona para login
                response.sendRedirect(request.getContextPath() + "/loginVisitante.jsp");
                return;
            }

            Funcionario funcionario = funcionarioDAO.buscarFuncionarioAleatorio();

            //ZonedDateTime agoraBrasil = ZonedDateTime.now(ZoneId.of("America/Sao_Paulo"));
            Compra novaCompra = new Compra();
            novaCompra.setDataCompra(LocalDateTime.now(ZoneId.of("America/Sao_Paulo")));
            novaCompra.setTipoPagamento(tipoPagamento);
            novaCompra.setVisitante(visitante);
            novaCompra.setFuncionario(funcionario);

            int codCompraGerado = compraDAO.salvarRetornandoID(novaCompra);
            novaCompra.setCodCompra(codCompraGerado);

            for (int i = 0; i < tipos.length; i++) {
                String tipo = tipos[i];
                int id = Integer.parseInt(ids[i]);
                int qtd = (quantidades[i] != null && !quantidades[i].isEmpty()) ? Integer.parseInt(quantidades[i]) : 0;

                if (qtd > 0) {
                    switch (tipo) {
                        case "lanche": {
                            Lanche lanche = new Lanche();
                            lanche.setCodLanche(id);
                            CompraLanche cl = new CompraLanche();
                            cl.setCompra(novaCompra);
                            cl.setLanche(lanche);
                            cl.setQuantidadeLanche(qtd);
                            compraLancheDAO.salvar(cl);
                        }
                        break;
                        case "brinquedo": {
                            IngressoBrinquedos ib = new IngressoBrinquedos();
                            ib.setCodIngresso(id);
                            CompraIngressoBrinquedos cib = new CompraIngressoBrinquedos();
                            cib.setCompra(novaCompra);
                            cib.setIb(ib);
                            cib.setQuantIngBrinq(qtd);
                            compraBrinquedoDAO.salvar(cib);
                        }
                        break;
                        case "evento": {
                            IngressoEventos ie = new IngressoEventos();
                            ie.setCodIngresso(id);
                            CompraIngressoEventos cie = new CompraIngressoEventos();
                            cie.setCompra(novaCompra);
                            cie.setIe(ie);
                            cie.setQuantIngEvent(qtd);
                            compraEventoDAO.salvar(cie);
                        }
                        break;
                    }
                }
            }

            mostrarComprasVisitante(request, response);

        } catch (Exception e) {
            e.printStackTrace();
            response.getWriter().println("Erro ao cadastrar compra: " + e.getMessage());
        }
    }

    private void excluirCompraCompleta(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int codCompra = Integer.parseInt(request.getParameter("codCompra"));
        Compra compra = new Compra();
        compra.setCodCompra(codCompra);
        compraDAO.excluir(compra);
        mostrarComprasVisitante(request, response);
    }

    private void excluirLanche(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int id = Integer.parseInt(request.getParameter("codCompraLanche"));
        CompraLanche cl = new CompraLanche();
        cl.setCodCompraLanche(id);
        compraLancheDAO.excluir(cl);
        mostrarComprasVisitante(request, response);
    }

    private void excluirBrinquedo(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int id = Integer.parseInt(request.getParameter("codCompraBrinquedos"));
        CompraIngressoBrinquedos cib = new CompraIngressoBrinquedos();
        cib.setCodCompraBrinquedos(id);
        compraBrinquedoDAO.excluir(cib);
        mostrarComprasVisitante(request, response);
    }

    private void excluirEvento(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int id = Integer.parseInt(request.getParameter("codCompraEventos"));
        CompraIngressoEventos cie = new CompraIngressoEventos();
        cie.setCodCompraEventos(id);
        compraEventoDAO.excluir(cie);
        mostrarComprasVisitante(request, response);
    }

    private void cancelar(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setAttribute("codCompra", "");
        request.setAttribute("tipoPagamento", "");
        request.setAttribute("codVisitante", "");
        request.setAttribute("codFuncionario", "");
        request.setAttribute("opcao", "cadastrar");
        encaminharParaPagina(request, response);
    }

    private void mostrarComprasVisitante(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Recupera o visitante logado
        Object usuarioLogado = request.getSession().getAttribute("usuarioLogado");
        Visitante visitante = null;
        if (usuarioLogado instanceof Visitante) {
            visitante = (Visitante) usuarioLogado;
        } else {
            // Se não estiver logado como visitante, redireciona para login
            response.sendRedirect(request.getContextPath() + "/loginVisitante.jsp");
            return;
        }

        List<Compra> listaCompras = compraDAO.listarPorVisitanteComItens(visitante.getCodVisitante());
        request.setAttribute("listaCompras", listaCompras);
        request.getSession().setAttribute("listaCompras", listaCompras);
        request.getRequestDispatcher("/MinhasCompras.jsp").forward(request, response);
    }

    private void filtrarComprasVisitantePorPeriodo(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Object usuarioLogado = request.getSession().getAttribute("usuarioLogado");
        if (!(usuarioLogado instanceof Visitante)) {
            response.sendRedirect(request.getContextPath() + "/loginVisitante.jsp");
            return;
        }
        Visitante visitante = (Visitante) usuarioLogado;

        try {
            String inicioStr = request.getParameter("dataInicio");
            String fimStr = request.getParameter("dataFim");

            LocalDateTime inicio = LocalDateTime.parse(inicioStr + "T00:00:00");
            LocalDateTime fim = LocalDateTime.parse(fimStr + "T23:59:59");

            List<Compra> listaCompras = compraDAO.listarPorVisitanteComItensPorPeriodo(visitante.getCodVisitante(), inicio, fim);
            request.getSession().setAttribute("listaCompras", listaCompras);
            request.setAttribute("listaCompras", listaCompras);
            request.setAttribute("filtroDataInicio", inicioStr);
            request.setAttribute("filtroDataFim", fimStr);

            request.getRequestDispatcher("/MinhasCompras.jsp").forward(request, response);

        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("erro", "Erro ao filtrar compras: " + e.getMessage());
            mostrarComprasVisitante(request, response);
        }
    }

    private void exibirFormularioAdicionarItem(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int codCompra = Integer.parseInt(request.getParameter("codCompra"));
        Compra compra = compraDAO.buscarCompraPorId(codCompra);
        request.setAttribute("compra", compra);
        request.setAttribute("lanches", lancheDAO.buscarTodosLanches());
        request.setAttribute("ingressosBrinquedos", ingressoBrinquedosDAO.buscarTodosIngressoBrinquedos());
        request.setAttribute("ingressosEventos", ingressoEventosDAO.buscarTodosIngressoEventos());
        request.getRequestDispatcher("/formAdicionarItem.jsp").forward(request, response);
    }

    private void confirmarAdicionarItem(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int codCompra = Integer.parseInt(request.getParameter("codCompra"));
        String[] tipos = request.getParameterValues("tipo");
        String[] produtos = request.getParameterValues("codProduto");
        String[] quantidades = request.getParameterValues("quantidade");

        for (int i = 0; i < tipos.length; i++) {
            String tipo = tipos[i];
            int codProduto = Integer.parseInt(produtos[i]);
            int quantidade = Integer.parseInt(quantidades[i]);

            if (quantidade > 0) {
                switch (tipo) {
                    case "lanche":
                        CompraLanche cl = new CompraLanche();
                        cl.setCompra(new Compra());
                        cl.getCompra().setCodCompra(codCompra);
                        cl.setQuantidadeLanche(quantidade);
                        cl.setLanche(new Lanche());
                        cl.getLanche().setCodLanche(codProduto);
                        compraLancheDAO.salvar(cl);
                        break;

                    case "brinquedo":
                        CompraIngressoBrinquedos cib = new CompraIngressoBrinquedos();
                        cib.setCompra(new Compra());
                        cib.getCompra().setCodCompra(codCompra);
                        cib.setQuantIngBrinq(quantidade);
                        cib.setIb(new IngressoBrinquedos());
                        cib.getIb().setCodIngresso(codProduto);
                        compraBrinquedoDAO.salvar(cib);
                        break;

                    case "evento":
                        CompraIngressoEventos cie = new CompraIngressoEventos();
                        cie.setCompra(new Compra());
                        cie.getCompra().setCodCompra(codCompra);
                        cie.setQuantIngEvent(quantidade);
                        cie.setIe(new IngressoEventos());
                        cie.getIe().setCodIngresso(codProduto);
                        compraEventoDAO.salvar(cie);
                        break;
                }
            }
        }

        mostrarComprasVisitante(request, response);
    }
}
