/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.luisferreira3infoh.modelo.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 *
 * @author
 */
public class ConnectionFactory {

    //servidor local
    //private static final String DB_URL = "jdbc:mysql://localhost:3307/bdestudoluisferreira?useSSL=false";;
    /*private static final String DB_URL = "jdbc:mysql://localhost:3307/luis_parque?useSSL=false";
    private static final String DB_DRIVER = "com.mysql.cj.jdbc.Driver";
    private static final String DB_USER = "root";
    private static final String DB_PASSWORD = "1234";*/
    
    // servidor em alwaysdata
    private static final String DB_URL = "jdbc:mysql://mysql-diversoesparque.alwaysdata.net:3306/diversoesparque_banco?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=America/Sao_Paulo";
    private static final String DB_DRIVER = "com.mysql.cj.jdbc.Driver";
    private static final String DB_USER = "437841_luis";
    private static final String DB_PASSWORD = "luisferreira2025p";

    //servidor em railway
    /*private static final String DB_URL = "jdbc:mysql://switchyard.proxy.rlwy.net:42683/railway?useSSL=false&serverTimezone=America/Sao_Paulo";
    private static final String DB_DRIVER = "com.mysql.cj.jdbc.Driver";
    private static final String DB_USER = "root";
    private static final String DB_PASSWORD = "fZbtHGkrjfcCZQFlZyLzdfwxnPhhJZMo";*/

    //Variável estática que mantém a instância única de ConnectionFactory
    private static ConnectionFactory instance;

    //Construtor privado para impedir a criação direta de instâncias da classe forad ela mesma
    private ConnectionFactory() {
        try {
            Class.forName(DB_DRIVER);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException("Driver do Banco de Dados não Encontrado", e);
        }
    }

    //Método "static ConnectionFactory" : permite o acesso à instância única
    // da ConnectionFactory -> Padrão Singleton garente que apenas uma instãncia seja usada em toda a aplicação
    public static ConnectionFactory getInstance() {
        if (instance == null) {
            instance = new ConnectionFactory();
        }
        return instance;
    }

    public Connection getConnection() throws SQLException {
        return DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
    }

    public PreparedStatement getPreparedStatement(String sql) throws SQLException {
        Connection con = getConnection();
        // PreparedStatement.RETURN_GENERATED_KEYS -> recupera ID gerado pelo banco após inserção de registro
        return con.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);
    }
}
