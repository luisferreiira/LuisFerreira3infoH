/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.luisferreira3infoh.servico;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.FilterConfig;

import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.annotation.WebFilter;
//import jakarta.servlet.annotation.WebServlet;
//import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
//import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 *
 * @author
 */
@WebFilter(urlPatterns = {"/*"})
public class AuthFilter implements Filter {

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;

        String path = httpRequest.getRequestURI().substring(httpRequest.getContextPath().length());

        // Caminhos públicos (não exigem login)
        if (path.startsWith("/public/")
                || path.equals("/inicio.jsp")
                || path.equals("/loginFuncionario.jsp")
                || path.equals("/loginVisitante.jsp")
                || path.equals("/CadastroVisitante.jsp")
                || path.equals("/CadastroFuncionario")
                || path.contains("/estilo/")
                || path.contains("/imagens/")
                || path.contains("LoginControlador") 
                || path.contains("FuncionarioControlador") 
                ) {
            chain.doFilter(request, response);
            return;
        }

        // Áreas protegidas por tipo de usuário
        boolean visitanteLogado = httpRequest.getSession().getAttribute("visitante") != null;
        boolean funcionarioLogado = httpRequest.getSession().getAttribute("funcionario") != null;

        // Áreas de visitante
        if (path.startsWith("/CadastroCompras.jsp") || path.startsWith("/MinhasCompras.jsp") || path.startsWith("/formAdicionarItem.jsp")) {
            if (!visitanteLogado) {
                httpRequest.getSession().setAttribute("destinoPosLogin", path);
                httpResponse.sendRedirect(httpRequest.getContextPath() + "/LoginVisitante.jsp");
                return;
            } else {
                chain.doFilter(request, response);
                return;
            }
        }

        // Áreas de funcionário
        if (path.startsWith("/CadastroBrinquedo")
                || path.startsWith("/CadastroCargo")
                || path.startsWith("/CadastroCategoria")
                || path.startsWith("/CadastroCompras")
                || path.startsWith("/CadastroEventos")
                || path.startsWith("/CadastroFuncionario")
                || path.startsWith("/CadastroIngressoBrinquedos")
                || path.startsWith("/CadastroIngressoEventos")
                || path.startsWith("/CadastroLanche")
                || path.startsWith("/RelatorioCompras")
                || path.startsWith("/RelatorioFuncionarios")
                || path.contains("/FuncionarioControlador")) {
            if (!funcionarioLogado) {
                // Salva o destino original
                httpRequest.getSession().setAttribute("destinoPosLogin", path);
                httpResponse.sendRedirect(httpRequest.getContextPath() + "/LoginFuncionario.jsp");
                return;
            } else {
                chain.doFilter(request, response);
                return;
            }
        }

        // Se não for uma rota reconhecida, deixa passar
        chain.doFilter(request, response);
    }

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
    }

    @Override
    public void destroy() {
    }
}
