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
    
    private void login() throws IOException{
		String info = in.readLine();
		String[] arr = info.split(":");
		
		if(umb.autenticar(arr[0], arr[1])){
			login = umb.getUser(arr[0]);
			out.write("OK");
			out.newLine();
			out.flush();
		}else{
			out.write("KO");
			out.newLine();
			out.flush();
		}
    }
    
	private void regista() throws IOException{
		String info = in.readLine();
		String[] arr = info.split(":");
		
		if(umb.registaUtilizador(arr[0], arr[1])){
			out.write("OK");
			out.newLine();
			out.flush();
		}else{
			out.write("KO");
			out.newLine();
			out.flush();
		}
    }
    
	private void solicita() throws IOException{
		String info = in.readLine();
		String[] arr = info.split(":");
		Local partida = new Local(arr[1]);
		Local destino = new Local(arr[2]);
		login.login(login.getPw(), false, null, partida, in, out, destino);
		
		out.write(umb.solicitarViagem(arr[0], partida, destino));
		out.newLine();
		out.flush();
	}
	
	private void disponivel() throws IOException{
		String info = in.readLine();
		String[] arr = info.split(":");
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
