package com.mycompany.luisferreira3infoh.servico;

import com.sendgrid.*;
import com.sendgrid.helpers.mail.Mail;
import com.sendgrid.helpers.mail.objects.Content;
import com.sendgrid.helpers.mail.objects.Email;
import java.io.IOException;
import jakarta.servlet.http.HttpServletRequest;

public class EmailService {

    private static final String SENDGRID_API_KEY = System.getenv("SENDGRID_API_KEY"); // substitua pela chave do SendGrid
    private static final String FROM_EMAIL = System.getenv("FROM_EMAIL");
    private static final String FROM_NAME = "Parque";

    /**
     * Envia email de redefinição de senha usando SendGrid.
     * @param request HttpServletRequest para gerar a URL dinamicamente
     * @param para email do destinatário
     * @param token token de redefinição
     * @throws java.io.IOException
     */
    public static void enviarLinkRedefinicao(HttpServletRequest request, String para, String token) throws IOException {

        // Monta link completo de forma dinâmica, sem precisar mudar nada no controlador
        String baseUrl = request.getScheme() + "://" + request.getServerName() + request.getContextPath();
        String link = baseUrl + "/LoginControlador?opcao=redefinirSenha&token=" + token;

        Email from = new Email(FROM_EMAIL, FROM_NAME);
        Email to = new Email(para);
        String subject = "Redefinição de senha";
        Content content = new Content("text/plain",
                "Olá!\n\nClique no link abaixo para redefinir sua senha:\n"
                        + link + "\n\nO link expira em 1 hora."
        );

        Mail mail = new Mail(from, subject, to, content);
        SendGrid sg = new SendGrid(SENDGRID_API_KEY);
        Request requestSendGrid = new Request();

        try {
            requestSendGrid.setMethod(Method.POST);
            requestSendGrid.setEndpoint("mail/send");
            requestSendGrid.setBody(mail.build());
            Response response = sg.api(requestSendGrid);
            System.out.println("Status do envio: " + response.getStatusCode());
        } catch (IOException ex) {
            throw ex;
        }
    }
}

/*package com.mycompany.luisferreira3infoh.servico;

import java.util.Properties;
import jakarta.mail.*;
import jakarta.mail.internet.*;
import java.io.UnsupportedEncodingException;

public class EmailService {

    private static final String USER = "luis1.ferreira@alunos.ifsuldeminas.edu.br"; // e-mail remetente
    private static final String SENHA = "bvakwgtqmtszunza"; // App Password do Gmail

    public static void enviarLinkRedefinicao(String para, String token) throws MessagingException, UnsupportedEncodingException {

        String link = "http://localhost:8080/LuisFerreira3infoH/LoginControlador?opcao=redefinirSenha&token=" + token;

        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "465");             // porta SSL
        props.put("mail.smtp.ssl.enable", "true");      // habilita SSL direto
        props.put("mail.smtp.starttls.enable", "false"); // desabilita STARTTLS
        props.put("mail.smtp.ssl.trust", "smtp.gmail.com"); // confia no certificado

        Session session = Session.getInstance(props, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(USER, SENHA);
            }
        });

        Message message = new MimeMessage(session);
        message.setFrom(new InternetAddress(USER, "Parque")); // remetente com nome
        message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(para));
        message.setSubject("Redefinição de senha");
        message.setText("Olá!\n\nClique no link abaixo para redefinir sua senha:\n" + link + "\n\nO link expira em 1 hora.");

        Transport.send(message);
        System.out.println("Email enviado com sucesso para " + para);
    }
}
*/