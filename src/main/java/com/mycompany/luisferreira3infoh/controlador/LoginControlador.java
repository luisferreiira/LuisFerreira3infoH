package com.mycompany.luisferreira3infoh.controlador;

import com.mycompany.luisferreira3infoh.modelo.dao.FuncionarioDAO;
import com.mycompany.luisferreira3infoh.modelo.dao.Tokens_RedefinicaoDAO;
import com.mycompany.luisferreira3infoh.modelo.dao.VisitanteDAO;
import com.mycompany.luisferreira3infoh.modelo.dao.entidade.Funcionario;
import com.mycompany.luisferreira3infoh.modelo.dao.entidade.Tokens_Redefinicao;
import com.mycompany.luisferreira3infoh.modelo.dao.entidade.Visitante;
import com.mycompany.luisferreira3infoh.servico.EmailService;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.time.ZonedDateTime;
import java.time.ZoneId;
import java.time.LocalDateTime;
import org.mindrot.jbcrypt.BCrypt;

@WebServlet("/LoginControlador")
public class LoginControlador extends HttpServlet {

    ZonedDateTime agoraBrasil = ZonedDateTime.now(ZoneId.of("America/Sao_Paulo"));
    private VisitanteDAO visitanteDAO;
    private FuncionarioDAO funcionarioDAO;

    @Override
    public void init() throws ServletException {
        visitanteDAO = new VisitanteDAO();
        funcionarioDAO = new FuncionarioDAO();
    }

    // ===================== POST =====================
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String opcao = request.getParameter("opcao");
        String email = request.getParameter("email");
        String senha = request.getParameter("senha");

        switch (opcao) {
            case "loginVisitante":
                fazerLoginVisitante(request, response, email, senha);
                break;

            case "loginFuncionario":
                fazerLoginFuncionario(request, response, email, senha);
                break;

            case "esqueciSenha":
                String emailRedef = request.getParameter("email");
                String tipoUsuario = request.getParameter("tipoUsuario");
                tratarEsqueciSenha(request, response, emailRedef, tipoUsuario);
                break;

            case "redefinirSenhaSalvar":
                tratarRedefinicaoSenha(request, response);
                break;

            default:
                response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Opção inválida.");
        }
    }

    private void fazerLoginVisitante(HttpServletRequest request, HttpServletResponse response,
            String email, String senha) throws ServletException, IOException {
        Visitante visitante = visitanteDAO.buscarPorEmail(email);

        if (visitante == null) {
            request.setAttribute("erro", "E-mail não encontrado. Verifique e tente novamente.");
            request.getRequestDispatcher("/LoginVisitante.jsp").forward(request, response);
            return;
        }

        if (!BCrypt.checkpw(senha, visitante.getSenha())) {
            request.setAttribute("erro", "Senha incorreta. Tente novamente.");
            request.getRequestDispatcher("/LoginVisitante.jsp").forward(request, response);
            return;
        }

        // Login bem-sucedido
        HttpSession session = request.getSession();
        session.setAttribute("visitante", visitante);
        session.setAttribute("usuarioLogado", visitante);
        session.setAttribute("tipo", "visitante");

        String destino = (String) session.getAttribute("destinoPosLogin");
        if (destino != null) {
            session.removeAttribute("destinoPosLogin");
            response.sendRedirect(request.getContextPath() + destino);
        } else {
            response.sendRedirect(request.getContextPath() + "/CompraControlador?opcao=listar");
        }
    }

    private void fazerLoginFuncionario(HttpServletRequest request, HttpServletResponse response,
            String email, String senha) throws ServletException, IOException {
        Funcionario funcionario = funcionarioDAO.buscarPorEmail(email);

        if (funcionario == null) {
            request.setAttribute("erro", "E-mail não encontrado. Verifique e tente novamente.");
            request.getRequestDispatcher("/LoginFuncionario.jsp").forward(request, response);
            return;
        }

        if (!BCrypt.checkpw(senha, funcionario.getSenha())) {
            request.setAttribute("erro", "Senha incorreta. Tente novamente.");
            request.getRequestDispatcher("/LoginFuncionario.jsp").forward(request, response);
            return;
        }

        // Login bem-sucedido
        HttpSession session = request.getSession();
        session.setAttribute("tipo", "funcionario");
        session.setAttribute("funcionario", funcionario);
        session.setAttribute("usuarioLogado", funcionario);

        String destino = (String) session.getAttribute("destinoPosLogin");
        if (destino != null) {
            session.removeAttribute("destinoPosLogin");
            response.sendRedirect(request.getContextPath() + destino);
        } else {
            response.sendRedirect(request.getContextPath() + "/inicio.jsp");
        }
    }

    private void tratarEsqueciSenha(HttpServletRequest request, HttpServletResponse response,
            String email, String tipoUsuario) throws ServletException, IOException {
        boolean emailExiste = false;

        if ("visitante".equals(tipoUsuario)) {
            emailExiste = visitanteDAO.buscarPorEmail(email) != null;
        } else if ("funcionario".equals(tipoUsuario)) {
            emailExiste = funcionarioDAO.buscarPorEmail(email) != null;
        }

        if (emailExiste) {
            Tokens_RedefinicaoDAO tokenDAO = new Tokens_RedefinicaoDAO();
            Tokens_Redefinicao token = new Tokens_Redefinicao();
            token.setEmail(email);
            token.setTipoUsuario(tipoUsuario);
            token.setToken(java.util.UUID.randomUUID().toString());
            token.setExpiracao(LocalDateTime.now().plusHours(1));
            token.setCriadoEm(LocalDateTime.now());

            tokenDAO.salvar(token);

            try {
                // TODO: ajustar baseUrl dinamicamente (ex.: request.getContextPath())
                EmailService.enviarLinkRedefinicao((javax.servlet.http.HttpServletRequest) request, email, token.getToken());
                request.setAttribute("mensagem", "Um link de redefinição foi enviado para o seu email.");
            } catch (Exception e) {
                e.printStackTrace();
                request.setAttribute("mensagem", "Erro ao enviar o email. Tente novamente.");
            }
        } else {
            request.setAttribute("mensagem", "Email não encontrado.");
        }

        String jspDestino;
        if ("visitante".equals(tipoUsuario)) {
            jspDestino = "/esqueciSenhaVisitante.jsp";
        } else if ("funcionario".equals(tipoUsuario)) {
            jspDestino = "/esqueciSenhaFuncionario.jsp";
        } else {
            jspDestino = "/esqueciSenha.jsp"; // fallback
        }

        request.getRequestDispatcher(jspDestino).forward(request, response);
    }

    private void tratarRedefinicaoSenha(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String tokenStr = request.getParameter("token");
        if (tokenStr == null || tokenStr.isEmpty()) {
            request.setAttribute("mensagem", "Token inválido.");
            request.getRequestDispatcher("/esqueciSenha.jsp").forward(request, response);
            return;
        }

        Tokens_RedefinicaoDAO tokenDAO = new Tokens_RedefinicaoDAO();
        Tokens_Redefinicao token = tokenDAO.buscarPorToken2(tokenStr, null);

        if (token == null) {
            request.setAttribute("mensagem", "Token inválido ou expirado.");
            request.getRequestDispatcher("/esqueciSenha.jsp").forward(request, response);
            return;
        }

        String novaSenha = request.getParameter("novaSenha");
        String confirmarSenha = request.getParameter("confirmarSenha");

        if (novaSenha == null || confirmarSenha == null || !novaSenha.equals(confirmarSenha)) {
            request.setAttribute("mensagem", "Senhas não conferem.");
            request.setAttribute("token", tokenStr);
            request.getRequestDispatcher("/redefinirSenha.jsp").forward(request, response);
            return;
        }

        String tipoUsuario = token.getTipoUsuario();

        if ("visitante".equalsIgnoreCase(tipoUsuario)) {
            Visitante visitante = visitanteDAO.buscarPorEmail(token.getEmail());
            if (visitante != null) {
                visitante.setSenha(BCrypt.hashpw(novaSenha, BCrypt.gensalt()));
                visitanteDAO.redefinirSenha(visitante);
            }
        } else if ("funcionario".equalsIgnoreCase(tipoUsuario)) {
            Funcionario funcionario = funcionarioDAO.buscarPorEmail(token.getEmail());
            if (funcionario != null) {
                funcionario.setSenha(BCrypt.hashpw(novaSenha, BCrypt.gensalt()));
                funcionarioDAO.redefinirSenha(funcionario);
            }
        }

        tokenDAO.apagarToken(tokenStr);

        request.setAttribute("mensagem", "Senha alterada com sucesso!");
        request.getRequestDispatcher("/senhaAlterada.jsp").forward(request, response);
    }

    // ===================== GET =====================
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String opcao = request.getParameter("opcao");

        if ("logout".equals(opcao)) {
            HttpSession sessao = request.getSession(false);
            if (sessao != null) {
                sessao.invalidate();
            }
            response.sendRedirect(request.getContextPath() + "/inicio.jsp");
            return;
        }

        if ("redefinirSenha".equals(opcao)) {
            String tokenStr = request.getParameter("token");
            if (tokenStr == null || tokenStr.isEmpty()) {
                request.setAttribute("mensagem", "Token inválido.");
                request.getRequestDispatcher("/esqueciSenha.jsp").forward(request, response);
                return;
            }

            Tokens_RedefinicaoDAO tokenDAO = new Tokens_RedefinicaoDAO();
            Tokens_Redefinicao token = tokenDAO.buscarPorToken2(tokenStr, null);

            if (token == null) {
                request.setAttribute("mensagem", "Token inválido ou expirado.");
                request.getRequestDispatcher("/esqueciSenha.jsp").forward(request, response);
                return;
            }

            request.setAttribute("token", tokenStr);
            request.setAttribute("tipoUsuario", token.getTipoUsuario());
            request.getRequestDispatcher("/redefinirSenha.jsp").forward(request, response);
            return;
        }

        response.sendError(HttpServletResponse.SC_NOT_FOUND);
    }
}
