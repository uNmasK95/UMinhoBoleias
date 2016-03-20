package testesJMS;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.util.concurrent.locks.ReentrantLock;

public class User {
	private ReentrantLock lock;
	private String usrname;
	private String pass;
	private String matric;
	private String modelo;
	private boolean condutor; //0 passageiro 1 condutor
	private boolean login; //o fora de seçao 1 secao iniciada
	private int x;
	private int y;
	private User link;
	private BufferedReader in;
	private BufferedWriter out;
	public User(String usrname, String pass) {
		this.lock = new ReentrantLock();
		this.usrname = usrname;
		this.pass = pass;
		this.matric=null;
		this.modelo=null;
		this.condutor=false;
		this.login=false;
		this.x=0;
		this.y=0;
		this.link=null;
		this.out = null;
		this.in=null;
		
	}
	
	public User getLink() {
		return link;
	}

	public void setLink(User link) {
		this.link = link;
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

	public double distancia(int x, int y){
		return (Math.abs(this.x-x) + Math.abs(this.y-y));
	}
	
	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}

	public ReentrantLock getLock() {
		return lock;
	}

	private boolean autentic(String pass){
		return this.pass.equals(pass);
	}
	
	public boolean loginPass(String Pass, int x, int y){
		boolean log = this.autentic(Pass);
		if(log){
			this.login=true;
			this.x=x;
			this.y=y;
		}
		return log;
	}
	
	public boolean loginMot(String pas, String matric, String modelo, int x,int y){
		boolean log = this.autentic(pass);
		if(log){
			this.login=true;
			this.condutor=true;
			this.matric=matric;
			this.modelo=modelo;
			this.x=x;
			this.y=y;
		}
		return log;
	}
	public void logout(){
		this.matric=null;
		this.modelo=null;
		this.condutor=false;
		this.login=false;
		this.x=0;
		this.y=0;
		this.link=null;
		this.out = null;
		this.in=null;
	}
	
	public String getUsrname() {
		return usrname;
	}

	public String getMatric() {
		return matric;
	}
	public void setMatric(String matric) {
		this.matric = matric;
	}
	public String getModelo() {
		return modelo;
	}
	public void setModelo(String modelo) {
		this.modelo = modelo;
	}
	public boolean isCondutor() {
		return condutor;
	}
	public void setCondutor(boolean condutor) {
		this.condutor = condutor;
	}
	public boolean isLogin() {
		return login;
	}

	
}
