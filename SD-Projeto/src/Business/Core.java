/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Business;
import static java.lang.System.out;
import java.util.GregorianCalendar;
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
        for (int i = 0; i < 100; i++) out.println();

        Utilizador user = new Utilizador();
        String email, pw, nome, data;
        String[] dataSplit;
        int dia, mes, ano;

        out.println("------Registo de utilizador------");
        out.print("Introduza email:  ");
        email = input.next();

        out.print("Introduza password:  ");
        pw = input.next();

        out.print("Introduza nome:  ");
        nome = input.next();

        out.print("Introduza data de nascimento: (Dia/Mes/Ano) ");
        data = input.next();
        dataSplit = data.split("/");

        try{
            dia = Integer.parseInt(dataSplit[0]);
            mes = Integer.parseInt(dataSplit[1]);
            ano = Integer.parseInt(dataSplit[2]);
        }catch (Exception e) { //nao sei de outra maneira de o fazer
            throw new Exception();
        }

        if (dia >= 1 && dia <= 31 && mes >= 1 && mes <= 12 && String.valueOf(ano).length() == 4) {
            GregorianCalendar nascimento = new GregorianCalendar(ano, mes, dia);
            user = new Utilizador(email, pw, nome, nascimento);
        }else
            throw new Exception();

        utilizadores.put(user.getEmail(), user);
        out.println("Registado com sucesso!\n");
    }

    public void login() {
        String email, pw;
        Utilizador user = new Utilizador();

        out.println("------Login------");
        out.print("Introduza email:  ");
        email = input.next();
        out.print("Introduza password:  ");
        pw = input.next();

        if (utilizadores.containsKey(email) && utilizadores.get(email).getPw().equals(pw))
            out.println("Login com sucesso!");
        else 
            out.println("Login sem sucesso!");
    }

    
}
