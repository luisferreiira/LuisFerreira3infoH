package com.mycompany.luisferreira3infoh.controlador;

import com.mycompany.luisferreira3infoh.modelo.dao.*;
import com.mycompany.luisferreira3infoh.modelo.dao.entidade.*;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.text.NumberFormat;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.*;

@WebServlet("/RelatorioFuncionariosControlador")
public class RelatorioFuncionariosControlador extends HttpServlet {

    private String formatarMoeda(Double valor) {
        if (valor == null) {
            return "";
        }
        NumberFormat nf = NumberFormat.getCurrencyInstance(new Locale("pt", "BR"));
        return nf.format(valor);
    }

    private String formatarData(LocalDate data) {
        if (data == null) {
            return "";
        }
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        return data.format(formatter);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String tipo = request.getParameter("tipoRelatorio");
        List<List<Object>> tabela = new ArrayList<>();
        List<String> colunas = new ArrayList<>();
        String descricaoRelatorio = "";

        try {
            if (tipo == null) {
                throw new IllegalArgumentException("tipoRelatorio não informado");
            }

            FuncionarioDAO fDAO = new FuncionarioDAO();
            CompraDAO cDAO = new CompraDAO();
            RelatorioCargoDAO rcDAO = new RelatorioCargoDAO();

            switch (tipo) {
                case "maiorTempoContrato": {
                    List<Funcionario> lista = fDAO.listarPorTempoContratoRelatorio();
                    for (Funcionario f : lista) {
                        Integer id = f.getCodFuncionario();
                        String nome = f.getNomeFuncionario();
                        String nomeCargo = f.getCargo() != null ? f.getCargo().getNomeCargo() : null;
                        String dataAdm = f.getDataAdmissao() != null ? formatarData(f.getDataAdmissao()) : "";
                        Integer anos = f.getAnosContrato();

                        tabela.add(Arrays.asList(id, nome, nomeCargo, dataAdm, anos));
                    }
                    colunas = Arrays.asList("ID", "Nome", "Cargo", "Data Admissão", "Tempo Empresa (anos)");
                    descricaoRelatorio = "Funcionários com maior tempo de contrato";
                    break;
                }
                case "funcionariosCargo": {
                    List<RelatorioCargoDTO> lista = rcDAO.listarFuncionariosPorCargo();
                    for (RelatorioCargoDTO rc : lista) {
                        
                        String nomeCargo = rc.getNomeCargo();
                        Integer totalFuncionarios = rc.getTotalFuncionarios();
                        Double porcentagem = rc.getPorcentagem();

                        tabela.add(Arrays.asList(nomeCargo, totalFuncionarios, porcentagem));
                    }
                    colunas = Arrays.asList("Cargo", "Total de Funcionários", "Porcentagem (%)");
                    descricaoRelatorio = "Quantidade de Funcionários por Cargo";
                    break;
                }

                case "salarioVendas": {
                    String mesAno = request.getParameter("mesAno"); // yyyy-MM
                    String[] parts = mesAno.split("-"); // separa o mês do ano
                    int ano = Integer.parseInt(parts[0]);
                    int mes = Integer.parseInt(parts[1]);

                    Map<String, Double> resultado = cDAO.compararSalarioVendas(mes, ano); // tabela chave-valor
                    // define as colunas chave do relatório
                    List<String> keys = Arrays.asList("Total Salarios", "Total Vendas", "Lucro/Prejuizo");
                    // define os dados valor que são resgastados do DAO
                    List<Object> values = new ArrayList<>();
                    for (String k : keys) {
                        values.add(formatarMoeda(resultado.getOrDefault(k, 0.0))); // formata o valor para R$, se não encontra define 0,00
                    }
                    tabela.add(values);
                    colunas = keys;
                    descricaoRelatorio = "Comparação de salários e vendas no mês " + mesAno;
                    break;
                }

                default:
                    request.setAttribute("mensagemErro", "Tipo de relatório inválido!");
            }
        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("mensagemErro", "Erro ao gerar relatório: " + e.getMessage());
        }

        /*request.setAttribute("listaRelatorio", tabela);
        request.setAttribute("colunas", colunas);
        request.setAttribute("tipoRelatorio", tipo);
        request.setAttribute("descricaoRelatorio", descricaoRelatorio);*/
        request.getSession().setAttribute("listaRelatorio", tabela);
        request.getSession().setAttribute("colunas", colunas);
        request.getSession().setAttribute("tipoRelatorio", tipo);
        request.getSession().setAttribute("descricaoRelatorio", descricaoRelatorio);
        request.getRequestDispatcher("/RelatorioFuncionarios.jsp").forward(request, response);
    }
}
