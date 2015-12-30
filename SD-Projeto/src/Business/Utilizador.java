package Business;

import java.util.*;
import java.io.*;

/**
 * Classe Utilizador
 *
 * @author Octavio Maia
 * @version 1.0
 */
public class Utilizador {

    //variaveis de instancia
    private String email;
    private String pw;
    private boolean condutor;
    private boolean activ;
    private boolean ocupado;
    private Veiculo v;
    private Local loc;
    private Local dest;
    private int custoUnitario;
    private Utilizador par;
    private BufferedReader in;
	private BufferedWriter out;
    
    
    public Utilizador(String mail,String pw) {
        this.email = mail;
        this.pw = pw;
        this.condutor=false;
        this.activ=false;
        this.ocupado=false;
        this.v= null;
        this.loc = null;
        this.dest= null;
        this.custoUnitario = -1;
        this.par=null;
        this.in=null;
        this.out=null;
    }
    
    public synchronized void logout(){
        this.condutor=false;
        this.activ=false;
        this.v= null;
        this.loc = null;
        this.dest= null;
        this.par=null;
        this.in=null;
        this.out=null;
        this.ocupado=false;
    }
    
    public synchronized boolean autenticar(String pw){
    	boolean ret =false;
    	if(this.pw.equals(pw)){
    		this.activ=true;
    		ret = true;
    	}
    	return ret;
    }

	public synchronized void setPw(String pw) {
		this.pw = pw;
	}

	public synchronized Veiculo getV() {
		return v;
	}

	public synchronized void setV(Veiculo v) {
		this.v = v;
	}

	public synchronized void setCondutor(boolean condutor) {
		this.condutor = condutor;
	}

    public synchronized boolean login(String pw, boolean condutor, Veiculo v,Local l, BufferedReader in, BufferedWriter out,Local dest){
    	boolean ret = false;
    	if(this.pw.equals(pw)){
    		ret = true;
    		this.condutor=condutor;
            this.activ=true;
            this.ocupado=false;
            this.v= v;
            this.loc =l;
            this.par=null;
            this.in=in;
            this.custoUnitario = -1;
            this.out=out;
            this.dest=dest;

    	}
    	return ret;
    }
    
    public synchronized String getPw(){
    	return pw;
    }

	public synchronized boolean isCondutor() {
		return condutor;
	}

	public synchronized boolean isActiv() {
		return activ;
	}

	public synchronized void setActiv(boolean activ) {
		this.activ = activ;
	}

	public synchronized boolean isOcupado() {
		return ocupado;
	}

	public synchronized void setOcupado(boolean ocupado) {
		if(this.ocupado==false){
			this.ocupado = ocupado;
			this.getPar().setOcupado(ocupado);
		}
	}
	
	public synchronized String getEmail(){
		return email;
	}

	public synchronized void setEmail(String email){
		this.email=email;
	}
	
	public synchronized Veiculo getVeiculo() {
		return v;
	}

	public synchronized void setVeiculo(Veiculo v) {
		this.v = v;
	}

	public synchronized Local getLoc() {
		return loc;
	}

	public synchronized void setLoc(Local loc) {
		this.loc = loc;
	}

	public synchronized Local getDest() {
		return dest;
	}

	public synchronized void setDest(Local dest) {
		this.dest = dest;
	}

	public synchronized int getCustoUnitario() {
		return custoUnitario;
	}

	public synchronized void setCustoUnitario(int custoUnitario) {
		this.custoUnitario = custoUnitario;
	}

	public synchronized Utilizador getPar() {
		return par;
	}
 
	public synchronized void setPar(Utilizador par) {
		if(this.par==null){
			this.par = par;
			par.setPar(this);
		}
	}

	public synchronized BufferedReader getIn() {
		return in;
	}

	public synchronized void setIn(BufferedReader in) {
		this.in = in;
	}

	public synchronized BufferedWriter getOut() {
		return out;
	}

	public synchronized void setOut(BufferedWriter out) {
		this.out = out;
	}
    
	@Override
	public synchronized int hashCode() {
		return this.email.hashCode();
	}
}
