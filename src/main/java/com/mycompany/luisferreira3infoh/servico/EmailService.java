package com.mycompany.luisferreira3infoh.servico;

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
