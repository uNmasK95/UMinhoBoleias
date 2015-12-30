package Business;

import java.util.*;
import java.io.*;

/**
 * Classe Utilizador
 *
 * @author Octavio Maia
 * @version 1.0
 */
public class Utilizador implements Serializable {

    //variaveis de instancia
    private String email;
    private String pw;
    private String nome;
    private GregorianCalendar data_nascimento;
    private Veiculo v;

    public Utilizador() {
        this.email = "";
        this.pw = "";
        this.nome = "";
        this.data_nascimento = new GregorianCalendar();
        this.v=null;
    }
    
    
    public Utilizador(String email, String pw, String nome, GregorianCalendar d) {
        this.email = email;
        this.pw = pw;
        this.nome = nome;
        this.data_nascimento = d;
        this.v=null;
    }

    /*
     * Gets
     */
    public String getEmail() {
        return email;
    }

    public String getPw() {
        return pw;
    }

    public String getNome() {
        return nome;
    }

    public GregorianCalendar getDate() {
        return (GregorianCalendar) data_nascimento.clone();
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

    public void setData_nascimento(GregorianCalendar data_nascimento) {
        this.data_nascimento = data_nascimento;
    }
    
    public void setEmail(String email) {
        this.email = email;
    }
    
    public void setVeiculo(Veiculo v){
        this.v = v;
    }
    
    public boolean isCondutor(){
        return this.v!=null;
    }

    /*
     * Funcoes aux
     */
    public StringBuilder toString(Utilizador user) {
        StringBuilder str = new StringBuilder();
        str.append("-------Dados do utilizador------\n");
        str.append("Nome : " + this.getNome() + "\n");
        str.append("Email : " + this.getEmail() + "\n");
        str.append("Data de nascimento : " + this.getDate().get(Calendar.DAY_OF_MONTH) + "/"
                + this.getDate().get(Calendar.MONTH) + "/"
                + this.getDate().get(Calendar.YEAR) + "\n");
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

        return (this.nome.equals(user.getNome()) && this.email.equals(user.getEmail())
                && this.pw.equals(user.getPw())  && this.data_nascimento.equals(user.getDate()));
    }

}
