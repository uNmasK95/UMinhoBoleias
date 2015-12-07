/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package src;

import static java.lang.System.out;
import java.util.Scanner;
import java.util.TreeMap;

/**
 *
 * @author Octavio
 */
public class Core {

    private Scanner input = new Scanner(System.in);
    private TreeMap<String, Utilizador> utilizadores = new TreeMap<String, Utilizador>();

    public void registarUser() throws Exception {
        Utilizador user = new Utilizador();
        String email, pw, nome, data;

        out.println("------Registo de utilizador------");
        out.print("Introduza nome:  ");
        nome = input.next();

        out.print("Introduza password:  ");
        pw = input.next();

        utilizadores.put(user.getNome(), user);
        out.println("Registado com sucesso!\n");
    }

    public void login() {
        String email, pw;
        Utilizador user = new Utilizador();

        out.println("------Login------");
        out.print("Introduza nome:  ");
        email = input.next();
        out.print("Introduza password:  ");
        pw = input.next();

        if (utilizadores.containsKey(email) && utilizadores.get(email).getPw().equals(pw)) {
            out.println("Login com sucesso!");
        } else {
            out.println("Login sem sucesso!");
        }
    }

}
