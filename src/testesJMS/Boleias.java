package testesJMS;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.util.Iterator;
import java.util.Map;
import java.util.TreeMap;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class Boleias {
	private ReentrantLock motoLock;
	private Map<String,User> moto;
	private Condition noMoto;
//	private ReentrantLock motoOLock;
//	private Map<String,User> motoO;
//	private ReentrantLock passLLock;
//	private Map<String,User> passL;
	private ReentrantLock passLock;
	private Condition waitPass;
	private Map<String,User> pass;
	private ReentrantLock usersLock;
	private Map<String,User> users;
	public Boleias() {
		this.motoLock = new ReentrantLock();
		this.moto = new TreeMap<>();
		this.noMoto=this.motoLock.newCondition();
//		this.motoOLock = new ReentrantLock();
//		this.motoO =  new TreeMap<>();
//		this.passLLock = new ReentrantLock();
//		this.passL =  new TreeMap<>();
		
		this.passLock = new ReentrantLock();
		this.waitPass = this.passLock.newCondition();
		this.pass =  new TreeMap<>();
		this.usersLock = new ReentrantLock();
		this.users =  new TreeMap<>();
	}
	
	public Map<String,User> getUsers(){return this.users;}	
	public boolean existUser(String usename){
		boolean ret =false;
		try{
			this.usersLock.lock();
			ret=this.users.containsKey(usename);
		}finally{
			this.usersLock.unlock();
		}
		return ret;
	}
	
	public String regista(String username, String pass){ //retorna null se registar com o ser name que demos senao retorna o registado
		String u1= username;
		String ret =null;
		int i=1;
		try{
			this.usersLock.lock();
			while(this.existUser(u1)){
				u1=u1+i;
			}
		}finally{
			this.usersLock.unlock();
		}
		User novo = new User(u1, pass);
		ret=u1;
		try{
			this.usersLock.lock();
			this.users.put(u1, novo);
		}finally{
			this.usersLock.unlock();
		}
		
		return ret;
	}
	
	public User loginPass(String username, String pass, int x, int y,BufferedWriter out, BufferedReader in){//retorna o passageiro que tem la dentro o motorista
		User u =null;
		User muser=null;
		//vou ver se existe e buscar o user indicado
		try{
			this.usersLock.lock();
			u = this.users.get(username);
			if(u==null) return null;
			if(!u.loginPass(pass, x, y)){
				u=null;
			}
		}finally{
			this.usersLock.unlock();
		}
		//ja tenho o user indicado e login tentado
		if(u==null){
			return null;
		}else{
			//existe utilizadir a login feito
			try{
				//espero que haja motpristas para escolher
				System.out.println("buscar lock");
				this.motoLock.lock();
				System.out.println("tenho lock");
				while(this.moto.isEmpty()){
					try {
						System.out.println("Vou esperar");
						this.noMoto.await();
						System.out.println("sai espera esperar");
					} catch (InterruptedException e) {
						e.printStackTrace();
						throw new RuntimeException(e.getMessage());
					}
				}
				//escolher ototrista mais perto
				Iterator<String> mots = this.moto.keySet().iterator();
				String mmot=mots.next();
				muser=this.moto.get(mmot);
				double distm = u.distancia(muser.getX(), muser.getY());
				while(mots.hasNext()){
					String mid = mots.next();
					User mot = this.moto.get(mid);
					double distt = u.distancia(mot.getX(), mot.getY());
					if(distt<distm){
						mmot = mid;
						muser = mot;
						distm=distt;
					}
				}
				//Ja tem o motorista para ele 
				//tirar-lo dos disponiveis
				this.moto.remove(mmot);
			}finally{
				this.motoLock.unlock();
			}
			//Fazer a acosiacao motorista passageiro
			u.setLink(muser);
			u.setIn(in);
			u.setOut(out);
			muser.setLink(u);
			//acho que o seguinte nao é preciso
			try{
				this.passLock.lock();
				this.pass.put(u.getUsrname(),u);
				this.waitPass.signalAll();
			}finally{
				this.passLock.unlock();
			}
			// faz isto para os motristas verem quem tem passaeiro uma vez que o passageiro ja escolheu
			//estava qui o signal
			
					
		}
		return u;
	}
	
	//qualquer um pode fazer logout
	
	public void logout(String username){
		try{
			this.usersLock.lock();
			User user = this.users.get(username);
			if(user!=null){
				User link = user.getLink();
				user.logout();
				link.logout();
				/* acho que nao ºe preciso
				try{
					this.usersLock.lock();
					this.users.put(user.getUsrname(), user);
					this.users.put(link.getUsrname(), link);
				}finally{
					this.usersLock.unlock();
				}
				// uma vez que a memodia é partilhada*/
			}
		}finally{
			this.usersLock.unlock();
		}
		
	}

	public User loginMot(String username, String pass, String modelo, String matric,int x, int y,BufferedWriter out, BufferedReader in){
		User u = null;
		try{
			this.usersLock.lock();
			u = this.users.get(username);
		}finally{
			this.usersLock.unlock();
		}
		if(u==null) return null;
		if(u.loginMot(pass, matric, modelo, x, y)){//fazer login
			//login feito
			u.setIn(in);
			u.setOut(out);
			//todos entram sem cliente acossiado, os clientes é que escolhem
			u.setLink(null);
			//vai dizer que esta dsiponivel para conduzir
			try{
				this.motoLock.lock();
				this.moto.put(u.getUsrname(), u);
				this.noMoto.signalAll();
			}finally{
				this.motoLock.unlock();
			}
			//aletrata todos os passageiros que ja ha motorista
			//esta aqui o signal
			//vai esperar que algum passageiro o escolha
			try{
				this.passLock.lock();
				while(u.getLink()==null){
					try {
						this.waitPass.await();
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
						throw new RuntimeException(e.getMessage());
					}
				}
			}finally{
				this.passLock.lock();
			}
			//ja tem passageiro acossiado
		}else{
			//erro no login nao retorna passageiro
			u=null;
		}
		return u;
	}
	
}
