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

/**
 *
 * @author ruifreitas
 */
public class UMinhoBoleias implements UMinhoBoleiasIface {

	private Map<String, Utilizador> utilizadores = new HashMap<String, Utilizador>();
    
	@Override
	public boolean registaUtilizador(String mail, String pass) {
		if(!utilizadores.containsKey(mail)){
			utilizadores.put(mail, new Utilizador(mail, pass));
	        out.println("Registado com sucesso!\n");
	        return true;
		}else{
			out.println("Já existe um utilizador com esse nome");
			return false;
		}
		return false;
    }

	@Override
	public boolean autenticar(String mail, String pass) {
		if(!utilizadores.containsKey(mail)){
			return false;
		}else if(utilizadores.get(mail).getPw().equals(pass)){
			return true;
		}
		return false;
	}

	@Override
	public String solicitarViagem(String mail, Local partida, Local destino) {
		String mail_condutor;
		double distancia = Double.MAX_VALUE;
		Utilizador condutor;
		Veiculo v;
		
		for(Utilizador u : utilizadores.values()){
			if(u.isCondutor() && !u.isOcupado()){
				if(u.getLocal().distancia(partida)<distancia){
					distancia = u.getLocal().distancia(partida);
					mail_condutor = u.getEmail();
				}
			}
		}
		
		condutor = utilizadores.get(mail_condutor);
		v = condutor.getVeiculo();
		
		if(distancia==0){
			return new String(v.getMatricula()+":"+v.getModelo());
		}else{
			return new String(v.getMatricula()+":"+v.getModelo()+":"+(condutor.getLocal().distancia(partida)/50));
		}
	}

	@Override
	public String disponivelViagem(String mail, Local actual, String matricula, String modelo, double custoUnitario) {
		
		return null;
	}
    
}
