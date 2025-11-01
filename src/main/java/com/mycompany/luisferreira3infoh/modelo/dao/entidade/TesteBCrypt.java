package com.mycompany.luisferreira3infoh.modelo.dao.entidade;

import org.mindrot.jbcrypt.BCrypt;

public class TesteBCrypt {
    public static void main(String[] args) {
        String hash = "$2a$10$UvOGqbVtst6.yNAFIYu1Rejc49OsKYb53ZFFaiWGeugOOz6.gww4i";

        System.out.println("123: " + BCrypt.checkpw("123", hash));
        System.out.println("0: " + BCrypt.checkpw("0", hash));
    }
}
