/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Business;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;

/**
 *
 * @author ruifreitas
 */
public class ServerConnection implements Runnable{
    
	static final String REGISTAUTILIZADOR ="1";
    static final String AUTENTICAR = "2";
    static final String SOLICITARVIAGEM ="3";
    static final String DISPONIVELVIAGEM ="4";
	
    private Socket sock;
    private UMinhoBoleias umb;
    private BufferedWriter out;
    private BufferedReader in;
    private Utilizador login;
    
    public ServerConnection(Socket sock,UMinhoBoleias umb) throws IOException{
        this.sock=sock;
        this.umb=umb;
        this.out=new BufferedWriter(new OutputStreamWriter(sock.getOutputStream()));
		this.in=new BufferedReader(new InputStreamReader(sock.getInputStream()));
    }
    
    private void regista() throws IOException{
		String info = in.readLine();
		String[] arr = info.split(":");
		
		
		if(umb.registaUtilizador(arr[0], arr[1])){ //conseguiu
			out.write("OK");
			out.newLine();
			out.flush();
		}else{ //falhou
			out.write("KO");
			out.newLine();
			out.flush();
		}
    }
    
    private void login() throws IOException{
		String info = in.readLine();
		String[] arr = info.split(":");
		
		if(umb.autenticar(arr[0], arr[1])){ //autenticou
			login = umb.getUser(arr[0]);	//atribui sessao
			out.write("OK");
			out.newLine();
			out.flush();
		}else{
			out.write("KO");				//falhou
			out.newLine();
			out.flush();
		}
    }
        
	private void solicita() throws IOException{
		String info = in.readLine();
		String[] arr = info.split(":");
		Local partida = new Local(arr[1]);
		Local destino = new Local(arr[2]);
		int sleep;
		
		login.login(login.getPw(), false, null, partida, in, out, destino); //login
		String aux = umb.solicitarViagem(arr[0], partida, destino);			//string return apos solicitar viagem
		String[] arr2 = aux.split(":");
		
		if(arr2.length==3){													//condutor ja está lá
			sleep = 0;
		}else{
			sleep = Integer.parseInt(arr2[3]);								//condutor está a caminho e demora arr2[3]
		}
		
		out.write(aux);
		out.newLine();
		out.flush();
		
		try {
			Thread.sleep(sleep*60000);										//sleep * 60 * 1000 (min * milisegundos)
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		out.write("OK");
		out.newLine();
		out.flush();
		
		login.getPar().getOut().write("OK");								//informa o cliente que conseguiu solicitar
		login.getPar().getOut().newLine();
		login.getPar().getOut().flush();
	}
	
	private void disponivel() throws IOException{
		String info = in.readLine();
		String[] arr = info.split(":");
		Utilizador cliente = umb.getUser(arr[0]);
		Local l = new Local(arr[1]);
		String matricula = arr[2];
		String modelo = arr[3];
		double custoUnitario = Double.parseDouble(arr[4]);
		
		String aux = umb.disponivelViagem(cliente.getEmail(), l, matricula, modelo, custoUnitario);
		
		
	}
	
    @Override
    public void run() {
    	String op;
    	try{
    		while(sock.isConnected()){
    			op = in.readLine();
    			switch(op){
    				case REGISTAUTILIZADOR:
    					regista();
    					break;
    				case AUTENTICAR:
    					login();
    					break;
    				case SOLICITARVIAGEM:
    					solicita();
    					break;
    				case DISPONIVELVIAGEM:
    					disponivel();
    					break;
    			}
    		}
    	}catch(IOException ex){
    		
    	}
        
    }
    
}
