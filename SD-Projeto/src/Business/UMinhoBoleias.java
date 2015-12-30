/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Business;

import static java.lang.System.out;

import java.util.GregorianCalendar;
import java.util.HashMap;
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

	@Override
	public String solicitarViagem(String mail, Local partida, Local destino) {
		String mail_condutor = "";
		double distancia = Double.MAX_VALUE;
		Utilizador condutor;
		Veiculo v;
		
		for(Utilizador u : utilizadores.values()){
			if(u.isCondutor() && !u.isOcupado()){
				if(u.getLoc().distancia(partida)<distancia){
					distancia = u.getLoc().distancia(partida);
					mail_condutor = u.getEmail();
				}
			}
		}
		
		condutor = utilizadores.get(mail_condutor);
		v = condutor.getVeiculo();
		
		if(distancia==0){
			return new String(v.getMatricula()+":"+v.getModelo());
		}else{
			return new String(v.getMatricula()+":"+v.getModelo()+":"+(condutor.getLoc().distancia(partida)/50));
		}
	}

	@Override
	public String disponivelViagem(String mail, Local actual, String matricula, String modelo, double custoUnitario) {
		
		return null;
	}
    
}
