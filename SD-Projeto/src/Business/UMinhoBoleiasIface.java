/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Business;

/**
 *
 * @author joaosilva
 */
public interface UMinhoBoleiasIface {
    public static final int REGISTAUTILIZADOR =1;
    public static final int AUTENTICAR =2;
    public static final int SOLICITARVIAGEM =3;
    public static final int DISPONIVELVIAGEM =4;
    
    public static final String OK = "OK";
    public static final String KO = "KO";
    
    public boolean registaUtilizador(String user, String pass); //pode sre throws 
    public boolean autenticar(String user, String pass);
    public String solicitarViagem(String user, Local partida, Local destino); //negativo demora x a estar disponivel, positivo em x tempo esta no local
    public String disponivelViagem(String user, Local actual, String matricula, String modelo,double custoUnitario);  
    
}
