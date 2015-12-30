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
        this.par=null;
        this.in=null;
        this.out=null;
    }
    
    public void logout(){
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
    
    public boolean autenticar(String pw){
    	boolean ret =false;
    	if(this.pw.equals(pw)){
    		this.activ=true;
    		ret = true;
    	}
    	return ret;
    }
    
    public String getPw() {
		return pw;
	}

	public void setPw(String pw) {
		this.pw = pw;
	}

	public Veiculo getV() {
		return v;
	}

	public void setV(Veiculo v) {
		this.v = v;
	}

	public void setCondutor(boolean condutor) {
		this.condutor = condutor;
	}

	public boolean login(String pw, boolean condutor, Veiculo v,Local l, BufferedReader in,
	BufferedWriter out,Local dest){
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
            this.out=out;
            this.dest=dest;

    	}
    	return ret;
    }

	public boolean isCondutor() {
		return condutor;
	}

	public boolean isActiv() {
		return activ;
	}

	public void setActiv(boolean activ) {
		this.activ = activ;
	}

	public boolean isOcupado() {
		return ocupado;
	}

	public void setOcupado(boolean ocupado) {
		if(this.ocupado==false){
			this.ocupado = ocupado;
			this.getPar().setOcupado(ocupado);
		}
	}
	
	public String getEmail(){
		return email;
	}

	public void setEmail(String email){
		this.email=email;
	}
	
	public Veiculo getVeiculo() {
		return v;
	}

	public void setVeiculo(Veiculo v) {
		this.v = v;
	}

	public Local getLoc() {
		return loc;
	}

	public void setLoc(Local loc) {
		this.loc = loc;
	}

	public Local getDest() {
		return dest;
	}

	public void setDest(Local dest) {
		this.dest = dest;
	}

	public Utilizador getPar() {
		return par;
	}

	public void setPar(Utilizador par) {
		if(this.par==null){
			this.par = par;
			par.setPar(this);
		}
	}

	public BufferedReader getIn() {
		return in;
	}

	public void setIn(BufferedReader in) {
		this.in = in;
	}

	public BufferedWriter getOut() {
		return out;
	}

	public void setOut(BufferedWriter out) {
		this.out = out;
	}
    
	@Override
	public int hashCode() {
		return this.email.hashCode();
	}
    

}
