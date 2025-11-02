package com.mycompany.luisferreira3infoh.controlador;

import com.mycompany.luisferreira3infoh.modelo.dao.*;
import com.mycompany.luisferreira3infoh.modelo.dao.entidade.*;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.text.NumberFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;

@WebServlet("/RelatorioComprasControlador")
public class RelatorioComprasControlador extends HttpServlet {

    //formatador de moeda R$
    private String formatarMoeda(Double valor) {
        if (valor == null) {
            return "";
        }
        NumberFormat nf = NumberFormat.getCurrencyInstance(new Locale("pt", "BR"));
        return nf.format(valor);
    }

    // formatador de data BR
    public String getDataFormatada(LocalDate data) {
        if (data != null) {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
            return data.format(formatter);
        }
        return "erro";
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // cria as variaveis onde serão armazenados e resgatados dinamicamente:
        String tipo = request.getParameter("tipoRelatorio"); // resgata o tipo relatório que usuário escolheu
        List<List<Object>> tabela = new ArrayList<>(); // cria uma lista de lista de objetos para formar a tabela
        List<String> colunas = new ArrayList<>(); // cria uma lista string onde serão informadas as colunas do relatório
        String descricaoRelatorio = "";

        try {
            if (tipo == null) {
                throw new IllegalArgumentException("tipoRelatorio não informado");
            }

            switch (tipo) {
                case "comprasLanches": {
                    // resgata o filtro do usuario
                    LocalDate inicio = LocalDate.parse(request.getParameter("dataInicio"));
                    LocalDate fim = LocalDate.parse(request.getParameter("dataFim"));
                    //gera a lista dos resultados desde o inicio do dia início, até o final do dia fim
                    List<CompraLanche> resultados = new CompraLancheDAO().buscarPorPeriodo(inicio.atStartOfDay(), fim.atTime(23, 59, 59));

                    // percorre a lista para armazenar os dados 
                    for (CompraLanche cl : resultados) {
                        Integer idCompra = cl.getCompra() != null ? cl.getCompra().getCodCompra() : null;
                        String visitante = (cl.getCompra() != null && cl.getCompra().getVisitante() != null) ? cl.getCompra().getVisitante().getNomeVisitante() : null;
                        String dataCompra = (cl.getCompra() != null) ? cl.getCompra().getDataCompraFormatada2() : null;
                        String nomeLanche = cl.getLanche() != null ? cl.getLanche().getNomeLanche() : null;
                        Integer qtd = cl.getQuantidadeLanche();
                        String precoTotal = (cl.getLanche() != null && qtd != null) ? formatarMoeda(qtd * cl.getLanche().getPrecoLanche()) : null;

                        // adiciona os dados na tabela
                        tabela.add(Arrays.asList(idCompra, visitante, dataCompra, nomeLanche, qtd, precoTotal));
                    }
                    //define as colunas desse tipo de relatório e sua descrição
                    colunas = Arrays.asList("ID Compra", "Visitante", "Data", "Lanche", "Quantidade", "Preço Total");
                    descricaoRelatorio = "Compras de Lanches entre " + getDataFormatada(inicio) + " e " + getDataFormatada(fim);
                    break;
                }

                case "comprasIngressosBrinquedos": {
                    LocalDate inicio = LocalDate.parse(request.getParameter("dataInicioBrinquedo"));
                    LocalDate fim = LocalDate.parse(request.getParameter("dataFimBrinquedo"));
                    List<CompraIngressoBrinquedos> resultados = new CompraIngressoBrinquedosDAO().buscarPorPeriodo(inicio.atStartOfDay(), fim.atTime(23, 59, 59));

                    for (CompraIngressoBrinquedos cib : resultados) {
                        Integer idCompra = cib.getCompra() != null ? cib.getCompra().getCodCompra() : null;
                        String visitante = (cib.getCompra() != null && cib.getCompra().getVisitante() != null) ? cib.getCompra().getVisitante().getNomeVisitante() : null;
                        String dataCompra = (cib.getCompra() != null) ? cib.getCompra().getDataCompraFormatada2() : null;
                        String nomeBrinquedo = (cib.getIb() != null && cib.getIb().getBrinquedo() != null) ? cib.getIb().getBrinquedo().getNomeBrinquedo() : null;
                        Integer qtd = cib.getQuantIngBrinq();
                        String precoTotal = (cib.getIb() != null && qtd != null) ? formatarMoeda(qtd * cib.getIb().getValor()) : null;

                        tabela.add(Arrays.asList(idCompra, visitante, dataCompra, nomeBrinquedo, qtd, precoTotal));
                    }
                    colunas = Arrays.asList("ID Compra", "Visitante", "Data", "Brinquedo", "Qtd Ingressos", "Preço Total");
                    descricaoRelatorio = "Compras de Ingressos de Brinquedos entre " + getDataFormatada(inicio) + " e " + getDataFormatada(fim);
                    break;
                }

                case "comprasIngressosEventos": {
                    LocalDate inicio = LocalDate.parse(request.getParameter("dataInicioEvento"));
                    LocalDate fim = LocalDate.parse(request.getParameter("dataFimEvento"));
                    List<CompraIngressoEventos> resultados = new CompraIngressoEventosDAO().buscarPorPeriodo(inicio.atStartOfDay(), fim.atTime(23, 59, 59));

                    for (CompraIngressoEventos cie : resultados) {
                        Integer idCompra = cie.getCompra() != null ? cie.getCompra().getCodCompra() : null;
                        String visitante = (cie.getCompra() != null && cie.getCompra().getVisitante() != null) ? cie.getCompra().getVisitante().getNomeVisitante() : null;
                        String dataCompra = (cie.getCompra() != null) ? cie.getCompra().getDataCompraFormatada2() : null;
                        String nomeEvento = (cie.getIe() != null && cie.getIe().getEvento() != null) ? cie.getIe().getEvento().getNomeEvento() : null;
                        Integer qtd = cie.getQuantIngEvent();
                        String precoTotal = (cie.getIe() != null && qtd != null) ? formatarMoeda(qtd * cie.getIe().getValor()) : null;

                        tabela.add(Arrays.asList(idCompra, visitante, dataCompra, nomeEvento, qtd, precoTotal));
                    }
                    colunas = Arrays.asList("ID Compra", "Visitante", "Data", "Evento", "Qtd Ingressos", "Preço Total");
                    descricaoRelatorio = "Compras de Ingressos de Eventos entre " + getDataFormatada(inicio) + " e " + getDataFormatada(fim);
                    break;
                }

                case "receitaDiaria": {
                    LocalDate data = LocalDate.parse(request.getParameter("dataFinanceiro"));
                    double[] totaisDia = new CompraDAO().totalPorDia(data.atStartOfDay());

                    tabela.add(Arrays.asList("Lanches", totaisDia[0]));
                    tabela.add(Arrays.asList("Brinquedos", totaisDia[1]));
                    tabela.add(Arrays.asList("Eventos", totaisDia[2]));

                    colunas = Arrays.asList("Tipo Produto", "Total (R$)");
                    descricaoRelatorio = "Receita diária de " + getDataFormatada(data);
                    break;
                }

                case "receitaMensal": {
                    String mesAno = request.getParameter("mesAno"); // yyyy-MM
                    String[] parts = mesAno.split("-");
                    int ano = Integer.parseInt(parts[0]);
                    int mes = Integer.parseInt(parts[1]);
                    double[] receita = new CompraDAO().receitaMensalPorCategoria(mes, ano);

                    tabela.add(Arrays.asList("Lanches", receita[0]));
                    tabela.add(Arrays.asList("Brinquedos", receita[1]));
                    tabela.add(Arrays.asList("Eventos", receita[2]));

                    colunas = Arrays.asList("Tipo Produto", "Receita");
                    descricaoRelatorio = "Receita Mensal (" + mesAno + ")";
                    break;
                }

                case "receitaAnual": {
                    String anoParam = request.getParameter("ano"); // yyyy
                    int ano = Integer.parseInt(anoParam);

                    double[] receita = new CompraDAO().receitaAnualPorCategoria(ano);

                    tabela.add(Arrays.asList("Lanches", receita[0]));
                    tabela.add(Arrays.asList("Brinquedos", receita[1]));
                    tabela.add(Arrays.asList("Eventos", receita[2]));

                    colunas = Arrays.asList("Tipo Produto", "Receita");
                    descricaoRelatorio = "Receita Anual (" + ano + ")";
                    break;
                }

                case "lanchesMaisVendidos": {
                    LocalDate inicio = LocalDate.parse(request.getParameter("dataInicioLanche"));
                    LocalDate fim = LocalDate.parse(request.getParameter("dataFimLanche"));
                    List<CompraLanche> resultados = new CompraLancheDAO().maisVendidos(inicio.atStartOfDay(), fim.atTime(23, 59, 59));
                    for (CompraLanche cl : resultados) {
                        String nome = cl.getLanche() != null ? cl.getLanche().getNomeLanche() : null;
                        Integer total = cl.getQuantidadeLanche();
                        tabela.add(Arrays.asList(nome, total));
                    }
                    colunas = Arrays.asList("Lanche", "Quantidade Vendida");
                    descricaoRelatorio = "Lanches mais vendidos entre " + getDataFormatada(inicio) + " e " + getDataFormatada(fim);
                    break;
                }

                case "ibMaisVendidos": {
                    LocalDate inicio = LocalDate.parse(request.getParameter("dataInicioIB"));
                    LocalDate fim = LocalDate.parse(request.getParameter("dataFimIB"));

                    List<CompraIngressoBrinquedos> resultados
                            = new CompraIngressoBrinquedosDAO().maisVendidos(inicio.atStartOfDay(), fim.atTime(23, 59, 59));

                    for (CompraIngressoBrinquedos cib : resultados) {
                        String nomeBrinquedo = (cib.getIb() != null && cib.getIb().getBrinquedo() != null)
                                ? cib.getIb().getBrinquedo().getNomeBrinquedo()
                                : null;
                        String tipoIngresso = (cib.getIb() != null) ? cib.getIb().getTipoIngresso() : null;
                        Integer total = cib.getQuantIngBrinq();

                        tabela.add(Arrays.asList(nomeBrinquedo, tipoIngresso, total));
                    }

                    colunas = Arrays.asList("Brinquedo", "Tipo Ingresso", "Quantidade Vendida");
                    descricaoRelatorio = "Ingressos de Brinquedos mais vendidos entre "
                            + getDataFormatada(inicio) + " e " + getDataFormatada(fim);
                    break;
                }

                case "ieMaisVendidos": {
                    LocalDate inicio = LocalDate.parse(request.getParameter("dataInicioIE"));
                    LocalDate fim = LocalDate.parse(request.getParameter("dataFimIE"));

                    List<CompraIngressoEventos> resultados
                            = new CompraIngressoEventosDAO().maisVendidos(inicio.atStartOfDay(), fim.atTime(23, 59, 59));

                    for (CompraIngressoEventos cie : resultados) {
                        String nomeEvento = (cie.getIe() != null && cie.getIe().getEvento() != null)
                                ? cie.getIe().getEvento().getNomeEvento()
                                : null;
                        String tipoIngresso = (cie.getIe() != null) ? cie.getIe().getTipoIngresso() : null;
                        Integer total = cie.getQuantIngEvent();

                        tabela.add(Arrays.asList(nomeEvento, tipoIngresso, total));
                    }

                    colunas = Arrays.asList("Evento", "Tipo Ingresso", "Quantidade Vendida");
                    descricaoRelatorio = "Ingressos de Eventos mais vendidos entre "
                            + getDataFormatada(inicio) + " e " + getDataFormatada(fim);
                    break;
                }

                default:
                    request.setAttribute("mensagemErro", "Tipo de relatório inválido!");
            }
        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("mensagemErro", "Erro ao gerar relatório: " + e.getMessage());
        }

        // salva na sessão tudo o que foi gerado pelo usuário para possivel exportação depois
        request.getSession().setAttribute("listaRelatorio", tabela);
        request.getSession().setAttribute("colunas", colunas);
        request.getSession().setAttribute("tipoRelatorio", tipo);
        request.getSession().setAttribute("descricaoRelatorio", descricaoRelatorio);
        request.getRequestDispatcher("/RelatorioCompras.jsp").forward(request, response);

    }
}
