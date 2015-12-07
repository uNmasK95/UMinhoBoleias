/**
 * Classe Utilizador
 *
 * @author Octavio Maia
 * @version 1.0
 */
package src;

import java.util.*;
import java.io.*;

public class Utilizador {

    //variaveis de instancia
    private String pw;
    private String nome;
    private Veiculo v;

    public Utilizador() {
        this.pw = "";
        this.nome = "";
        this.v = null;
    }

    public Utilizador(String email, String pw, String nome, GregorianCalendar d) {
        this.pw = pw;
        this.nome = nome;
        this.v = null;
    }

    /*
     * Gets
     */
    public String getPw() {
        return pw;
    }

    public String getNome() {
        return nome;
    }

    public Veiculo getVeiculo() {
        return v;
    }

    /*
     * Sets
     */
    public void setPw(String pw) {
        this.pw = pw;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }
    
    public void setVeiculo(Veiculo v) {
        this.v = v;
    }

    public boolean isCondutor() {
        return this.v != null;
    }

    /*
     * Funcoes aux
     */
    public StringBuilder toString(Utilizador user) {
        StringBuilder str = new StringBuilder();
        str.append("-------Dados do utilizador------\n");
        str.append("Nome : " + this.getNome() + "\n");
        return str;
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if ((obj == null) || (this.getClass() != obj.getClass())) {
            return false;
        }

        Utilizador user = (Utilizador) obj;

        return (this.nome.equals(user.getNome()) && this.pw.equals(user.getPw()));
    }

}
