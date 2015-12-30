/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Business;

import static java.lang.System.out;

import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

/**
 *
 * @author ruifreitas
 */
public class UMinhoBoleias implements UMinhoBoleiasIface {

	private Map<String, Utilizador> utilizadores;
	ReentrantLock lockUsers;
	Condition esperaPass;
	Condition esperaCond;
    
	
	public UMinhoBoleias(){
		this.utilizadores = new HashMap<>();
		this.lockUsers = new ReentrantLock();
		this.esperaPass = this.lockUsers.newCondition();
		this.esperaCond = this.lockUsers.newCondition();
	}
	
	@Override
	public boolean registaUtilizador(String mail, String pass) {
		boolean ret = false;
		try{
			this.lockUsers.lock();
			if(!utilizadores.containsKey(mail)){
				utilizadores.put(mail, new Utilizador(mail, pass));
		        out.println("Registado com sucesso!\n");
		        ret= true;
			}else{
				out.println("Já existe um utilizador com esse nome");
			}
		}finally{
			this.lockUsers.unlock();
		}
		return ret;
    }

	@Override
	public boolean autenticar(String mail, String pass) {
		boolean ret = false;
		try{
			this.lockUsers.lock();
			if(utilizadores.containsKey(mail)){
				Utilizador u = utilizadores.get(mail);
				if(!u.isActiv() && u.autenticar(pass)){
					ret = true;
				}
			}
		}finally{
			this.lockUsers.unlock();
		}
		return ret;
	}

	public Utilizador getUser(String email){
		Utilizador ret = null;
		try{
			this.lockUsers.lock();
			ret = this.utilizadores.get(email);
			
		}finally{
			this.lockUsers.unlock();
		}
		return ret;
	}
	
	private boolean existPassEspera(){
		boolean ret = false;
		Utilizador u = null;
		Iterator<Utilizador> ui  = utilizadores.values().iterator();
		while(ret==false && ui.hasNext()){
			 u = ui.next();
			if(u.isActiv() && !u.isCondutor() && !u.isOcupado()){
				ret = true;
			}
		}
		return ret;
	}
	
	private boolean existCondDisp(){
		boolean ret = false;
		Utilizador u = null;
		Iterator<Utilizador> ui  = utilizadores.values().iterator();
		while(ret==false && ui.hasNext()){
			 u = ui.next();
			if(u.isActiv() && u.isCondutor() && !u.isOcupado()){
				ret = true;
			}
		}
		return ret;
	}
	
	
	@Override
	public String solicitarViagem(String mail, Local partida, Local destino) {
		String ret = null;
		double distancia = Double.MAX_VALUE;
		Utilizador condutor=null;
		Veiculo v;
		Utilizador log =null;
		
		try{
			this.lockUsers.lock();
			log=utilizadores.get(mail);
			while(!this.existCondDisp()){
				try {
					this.esperaCond.await();
				} catch (InterruptedException e) {
					System.out.println("Estrorei no await");
					e.printStackTrace();
				}
			}
			//ja tenho condutor
			for(Utilizador u : utilizadores.values()){
				if(u.isCondutor() && !u.isOcupado()){
					if(u.getLoc().distancia(partida)<distancia){
						distancia = u.getLoc().distancia(partida);
						condutor = u;
					}
				}
			}
			//ja estao coupados
			condutor.setOcupado(true);
			log.setOcupado(true);
			//acossiar
			log.setPar(condutor);
			condutor.setPar(log);
			//estao acosiados
			
		}finally{
			this.lockUsers.unlock();
		}
		
		v = condutor.getVeiculo();
		
		if(distancia==0){
			ret =  new String(condutor.getEmail()+":"+v.getMatricula()+":"+v.getModelo());
		}else{
			ret =  new String(condutor.getEmail()+":"+v.getMatricula()+":"+v.getModelo()+":"+(condutor.getLoc().distancia(partida)/50));
		}
		return ret;
	}

	@Override
	public String disponivelViagem(String mail, Local actual, String matricula, String modelo, double custoUnitario) {
		String ret = null;
		Utilizador pass=null;
		Veiculo v;
		Utilizador log =null;
		
		try{
			this.lockUsers.lock();
			log=utilizadores.get(mail);
			while(!this.existCondDisp()){
				try {
					this.esperaCond.await();
				} catch (InterruptedException e) {
					System.out.println("Estrorei no await");
					e.printStackTrace();
				}
			}
			//ja tenho condutor
			for(Utilizador u : utilizadores.values()){
				if(u.isCondutor() && !u.isOcupado()){
					if(u.getLoc().distancia(partida)<distancia){
						distancia = u.getLoc().distancia(partida);
						condutor = u;
					}
				}
			}
			//ja estao coupados
			condutor.setOcupado(true);
			log.setOcupado(true);
			//acossiar
			log.setPar(condutor);
			condutor.setPar(log);
			//estao acosiados
			
		}finally{
			this.lockUsers.unlock();
		}
		
		v = condutor.getVeiculo();
		
		if(distancia==0){
			ret =  new String(condutor.getEmail()+":"+v.getMatricula()+":"+v.getModelo());
		}else{
			ret =  new String(condutor.getEmail()+":"+v.getMatricula()+":"+v.getModelo()+":"+(condutor.getLoc().distancia(partida)/50));
		}
		return ret;
	}
    
}
