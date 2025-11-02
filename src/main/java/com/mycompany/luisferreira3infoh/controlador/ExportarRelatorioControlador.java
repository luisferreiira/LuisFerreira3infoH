package com.mycompany.luisferreira3infoh.controlador;

import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.geom.PageSize;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;
import com.itextpdf.layout.element.Cell;
import com.itextpdf.layout.properties.UnitValue;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFDrawing;
import org.apache.poi.xssf.usermodel.XSSFChart;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xddf.usermodel.chart.*;
import org.apache.poi.xddf.usermodel.chart.XDDFDataSource;
import org.apache.poi.xddf.usermodel.chart.XDDFNumericalDataSource;
import org.apache.poi.xddf.usermodel.chart.XDDFDataSourcesFactory;

import java.io.IOException;
import java.io.OutputStream;
import java.text.NumberFormat;
import java.time.format.DateTimeFormatter;
import java.util.*;

@WebServlet("/ExportarRelatorioControlador")
public class ExportarRelatorioControlador extends HttpServlet {

    // LISTA DE RELATÓRIOS QUE GERAM GRÁFICOS DE PIZZA
    private static final Set<String> RELATORIOS_COM_GRAFICO = Set.of(
            "lanchesMaisVendidos",
            "ibMaisVendidos",
            "ieMaisVendidos",
            "funcionariosCargo",
            "receitaDiaria",
            "receitaMensal",
            "receitaAnual"
    );

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String opcao = request.getParameter("opcao");
        String exportFormato = request.getParameter("exportFormato");
        String tipoRelatorio = request.getParameter("tipoRelatorio");
        String descricaoRelatorio = request.getParameter("descricaoRelatorio");
        HttpSession session = request.getSession(false);

        // Exportação de "Minhas Compras" do visitante
        if (opcao != null) {
            if (session == null || session.getAttribute("listaCompras") == null) {
                response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Nenhuma compra encontrada para exportação.");
                return;
            }

            List<?> listaCompras = (List<?>) session.getAttribute("listaCompras");
            if (listaCompras == null || listaCompras.isEmpty()) {
                response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Nenhuma compra encontrada para exportação.");
                return;
            }

            if ("exportarPdf".equals(opcao)) {
                exportarComprasPdf(listaCompras, response);
            } else if ("exportarExcel".equals(opcao)) {
                exportarComprasExcel(listaCompras, response);
            } else {
                response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Opção inválida.");
            }

        } // Relatórios para compras e funcionários
        else if (exportFormato != null && tipoRelatorio != null) {

            List<String> colunas = (List<String>) session.getAttribute("colunas");
            List<List<Object>> listaRelatorio = (List<List<Object>>) session.getAttribute("listaRelatorio");

            if (listaRelatorio == null || listaRelatorio.isEmpty() || colunas == null || colunas.isEmpty()) {
                response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Nenhum dado disponível para exportação.");
                return;
            }

            if ("pdf".equalsIgnoreCase(exportFormato)) {
                exportarRelatorioPdf(descricaoRelatorio, colunas, listaRelatorio, response);
            } else if ("excel".equalsIgnoreCase(exportFormato)) {
                // Se o relatório está na lista de gráficos, usa o método com gráfico de pizza
                if (RELATORIOS_COM_GRAFICO.contains(tipoRelatorio)) {
                    exportarRelatorioExcelComGrafico(descricaoRelatorio, colunas, listaRelatorio, response, tipoRelatorio);
                } else {
                    exportarRelatorioExcel(descricaoRelatorio, colunas, listaRelatorio, response);
                }
            } else {
                response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Formato inválido.");
            }

        } else {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Parâmetros inválidos.");
        }
    }

    // MÉTODO DE EXPORTAÇÃO PDF (TABELA)
    private void exportarRelatorioPdf(String titulo, List<String> colunas, List<List<Object>> dados, HttpServletResponse response)
            throws IOException {
        response.setContentType("application/pdf");
        response.setHeader("Content-Disposition", "attachment; filename=" + titulo + ".pdf");

        try (OutputStream out = response.getOutputStream()) {
            PdfWriter writer = new PdfWriter(out);
            PdfDocument pdf = new PdfDocument(writer);
            Document document = new Document(pdf, PageSize.A4.rotate());

            document.add(new Paragraph("Relatório: " + titulo).setBold().setFontSize(14));
            document.add(new Paragraph(" "));

            float[] widths = new float[colunas.size()];
            Arrays.fill(widths, 3f);
            widths[0] = 2;
            widths[widths.length - 1] = 4;

            Table table = new Table(widths);
            table.setWidth(UnitValue.createPercentValue(100));

            for (String col : colunas) {
                table.addHeaderCell(new Cell().add(new Paragraph(col)));
            }

            for (List<Object> linha : dados) {
                for (Object valor : linha) {
                    table.addCell(new Paragraph(valor != null ? valor.toString() : "-"));
                }
            }

            document.add(table);
            document.close();
        }
    }

    // MÉTODO DE EXPORTAÇÃO EXCEL (TABELA)
    private void exportarRelatorioExcel(String titulo, List<String> colunas, List<List<Object>> dados, HttpServletResponse response)
            throws IOException {
        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
        response.setHeader("Content-Disposition", "attachment; filename=" + titulo + ".xlsx");

        try (Workbook workbook = new XSSFWorkbook(); OutputStream out = response.getOutputStream()) {
            Sheet sheet = workbook.createSheet("Relatório");

            Row headerRow = sheet.createRow(0);
            for (int i = 0; i < colunas.size(); i++) {
                headerRow.createCell(i).setCellValue(colunas.get(i));
            }

            int rowIdx = 1;
            for (List<Object> linha : dados) {
                Row row = sheet.createRow(rowIdx++);
                for (int i = 0; i < linha.size(); i++) {
                    row.createCell(i).setCellValue(linha.get(i) != null ? linha.get(i).toString() : "-");
                }
            }

            for (int i = 0; i < colunas.size(); i++) {
                sheet.autoSizeColumn(i);
            }
            workbook.write(out);
        }
    }

    //EXPORTAÇÃO EXCEL COM GRÁFICO DE PIZZA
    private void exportarRelatorioExcelComGrafico(String titulo, List<String> colunas, List<List<Object>> dados,
            HttpServletResponse response, String tipoRelatorio) throws IOException {

        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
        response.setHeader("Content-Disposition", "attachment; filename=" + titulo + ".xlsx");

        try (XSSFWorkbook workbook = new XSSFWorkbook(); OutputStream out = response.getOutputStream()) {
            XSSFSheet sheet = workbook.createSheet("Relatório");

            // Preenche tabela
            Row headerRow = sheet.createRow(0);
            for (int i = 0; i < colunas.size(); i++) {
                headerRow.createCell(i).setCellValue(colunas.get(i));
            }

            int rowIdx = 1;
            for (List<Object> linha : dados) {
                Row row = sheet.createRow(rowIdx++);
                for (int i = 0; i < linha.size(); i++) {
                    row.createCell(i).setCellValue(linha.get(i) != null ? linha.get(i).toString() : "-");
                }
            }
            for (int i = 0; i < colunas.size(); i++) {
                sheet.autoSizeColumn(i);
            }

            // Prepara dados para gráfico de pizza
            List<String> nomes = new ArrayList<>();
            List<Double> valores = new ArrayList<>();
            for (List<Object> linha : dados) {
                String nome;
                if ("Ingressos de Brinquedos".equals(tipoRelatorio) || "Ingressos de Eventos".equals(tipoRelatorio)) {
                    nome = linha.get(1) + " " + linha.get(0);
                } else if (tipoRelatorio.startsWith("Receita")) {
                    nome = linha.get(0).toString();
                } else {
                    nome = linha.get(0).toString();
                }
                nomes.add(nome);
                try {
                    valores.add(Double.parseDouble(linha.get(linha.size() - 1).toString()));
                } catch (NumberFormatException e) {
                    valores.add(0.0);
                }
            }

            // Cria gráfico de pizza
            XSSFDrawing drawing = sheet.createDrawingPatriarch();
            XSSFChart chart = drawing.createChart(
                    drawing.createAnchor(0, 0, 0, 0, 0, rowIdx + 2, 10, rowIdx + 20));
            chart.setTitleText(titulo);

            XDDFDataSource<String> nomesData = XDDFDataSourcesFactory.fromArray(nomes.toArray(new String[0]));
            XDDFNumericalDataSource<Double> valoresData = XDDFDataSourcesFactory.fromArray(valores.toArray(new Double[0]));

            XDDFChartData data = chart.createData(ChartTypes.PIE, null, null);
            data.setVaryColors(true);
            data.addSeries(nomesData, valoresData);
            chart.plot(data);

            // --- CONFIGURA RÓTULOS E LEGENDA ---
            org.openxmlformats.schemas.drawingml.x2006.chart.CTPlotArea plotArea = chart.getCTChart().getPlotArea();
            if (plotArea.sizeOfPieChartArray() > 0) {
                org.openxmlformats.schemas.drawingml.x2006.chart.CTPieChart ctPie = plotArea.getPieChartArray(0);

                // rótulos de dados
                org.openxmlformats.schemas.drawingml.x2006.chart.CTDLbls dLbls
                        = ctPie.isSetDLbls() ? ctPie.getDLbls() : ctPie.addNewDLbls();

                dLbls.addNewShowCatName().setVal(true); // Nome da categoria
                dLbls.addNewShowSerName().setVal(false); // Nome da série
                dLbls.addNewShowVal().setVal(true);     // Valor
            }

            // legenda
            chart.getOrAddLegend().setPosition(LegendPosition.RIGHT);

            workbook.write(out);
        }
    }

    // MÉTODOS DE EXPORTAÇÃO DE MINHAS COMPRAS (VISITANTE)
    //precisamos apenas da lista de compras gerada pelo usuário (foi guardada na sessão)
    // Exportações: Minhas Compras (Visitante) para PDF
    private void exportarComprasPdf(List<?> listaCompras, HttpServletResponse response)
            throws IOException {

        response.setContentType("application/pdf"); // define tipo da extensão para pdf
        response.setHeader("Content-Disposition", "attachment; filename=MinhasCompras.pdf"); // define nome do arquivo

        DateTimeFormatter df = DateTimeFormatter.ofPattern("dd/MM/yyyy"); // formatador de data
        NumberFormat nf = NumberFormat.getCurrencyInstance(new Locale("pt", "BR")); // formatador de moeda R$

        try (OutputStream out = response.getOutputStream()) {
            PdfWriter writer = new PdfWriter(out);
            PdfDocument pdf = new PdfDocument(writer);
            Document document = new Document(pdf, PageSize.A4.rotate()); // HORIZONTAL

            document.add(new Paragraph("Relatório - Minhas Compras").setBold().setFontSize(14)); // titulo
            document.add(new Paragraph(" "));

            // Largura relativa das colunas
            float[] columnWidths = {2, 3, 5, 2, 3}; // ID, Data, Item, Quantidade, Valor
            Table table = new Table(columnWidths);
            table.setWidth(UnitValue.createPercentValue(100)); // preenchimento da largura do arquivo

            //colunas 
            table.addHeaderCell("ID Compra");
            table.addHeaderCell("Data");
            table.addHeaderCell("Item(ns)");
            table.addHeaderCell("Quantidade");
            table.addHeaderCell("Valor Total");

            //percorre a lista de compras
            for (Object obj : listaCompras) {
                com.mycompany.luisferreira3infoh.modelo.dao.entidade.Compra compra
                        = (com.mycompany.luisferreira3infoh.modelo.dao.entidade.Compra) obj;

                // armazena os dados
                String codCompra = String.valueOf(compra.getCodCompra());
                String dataCompra = compra.getDataCompra() != null ? df.format(compra.getDataCompra()) : "-";
                String item = compra.getDescricaoTotal();
                String qtd = String.valueOf(compra.getQuantidadeTotal());
                String valor = nf.format(compra.getValorTotalCompra());

                // insere os dados na linha
                table.addCell(codCompra);
                table.addCell(dataCompra);
                table.addCell(item != null ? item : "-");
                table.addCell(qtd);
                table.addCell(valor);
            }

            document.add(table);
            document.close();
        }
    }

    // método que cria o arquivo excel para relatórios de compras do VISITANTE colunas são fixas
    private void exportarComprasExcel(List<?> listaCompras, HttpServletResponse response)
            throws IOException {

        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"); //define a extensão do arquivo com xlsx
        response.setHeader("Content-Disposition", "attachment; filename=MinhasCompras.xlsx"); //define nome do arquivo

        DateTimeFormatter df = DateTimeFormatter.ofPattern("dd/MM/yyyy"); //formatador de datas BR
        NumberFormat nf = NumberFormat.getCurrencyInstance(new Locale("pt", "BR")); //formatador de números R$

        try (Workbook workbook = new XSSFWorkbook(); OutputStream out = response.getOutputStream()) {
            Sheet sheet = workbook.createSheet("Minhas Compras");

            // linha 0 inicial da tabela (colunas)
            Row headerRow = sheet.createRow(0);
            headerRow.createCell(0).setCellValue("ID Compra");
            headerRow.createCell(1).setCellValue("Data");
            headerRow.createCell(2).setCellValue("Item(ns)");
            headerRow.createCell(3).setCellValue("Quantidade");
            headerRow.createCell(4).setCellValue("Valor Total");

            // linha 1 da tabela com dados
            int rowIdx = 1;
            // percorre a lista de compras 
            for (Object obj : listaCompras) {
                com.mycompany.luisferreira3infoh.modelo.dao.entidade.Compra compra
                        = (com.mycompany.luisferreira3infoh.modelo.dao.entidade.Compra) obj;
                // informações obtidas que são inseridas nas linhas
                Row row = sheet.createRow(rowIdx++);
                row.createCell(0).setCellValue(compra.getCodCompra());
                row.createCell(1).setCellValue(compra.getDataCompra() != null ? df.format(compra.getDataCompra()) : "-");
                row.createCell(2).setCellValue(compra.getDescricaoTotal() != null ? compra.getDescricaoTotal() : "-"); // método em compra para concatenar os nomes dos itens
                row.createCell(3).setCellValue(compra.getQuantidadeTotal());
                row.createCell(4).setCellValue(nf.format(compra.getValorTotalCompra()));
            }

            // número de colunas 
            for (int i = 0; i < 5; i++) {
                sheet.autoSizeColumn(i);
            }

            workbook.write(out);
        }
    }
}
