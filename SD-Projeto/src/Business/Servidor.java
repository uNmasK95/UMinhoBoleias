/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Business;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import testesJMS.Boleias;
import testesJMS.ServerConnection;

/**
 *
 * @author ruifreitas
 */
public class Servidor {
    
	private ServerSocket sSock;
	private int port;
	private UMinhoBoleias boleias;
	
	public Servidor(int port) throws IOException {
		this.port = port;
		this.boleias = new UMinhoBoleias();
		this.sSock = new ServerSocket(port);
	}
	
	public void startServer(){
		while(true){
            Socket sock = null;
            try {
                sock = sSock.accept();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
            Thread t;
			try {
				t = new Thread(new ServerConnection(sock,bol));
				t.start();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
            
        }
	}
}
