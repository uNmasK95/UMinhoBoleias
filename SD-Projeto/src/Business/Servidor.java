package Business;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Servidor {
    
	private ServerSocket servidor;
	private int port;
	private UMinhoBoleias boleias;
	
	public Servidor(int port) throws IOException {
		this.port = port;
		this.boleias = new UMinhoBoleias();
		this.servidor = new ServerSocket(port);
	}
	
	public void startServer() throws IOException{
		while(true){
            Socket cliente = null;
            try {
                cliente = servidor.accept();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
            Thread t = new Thread(new ServerConnection(cliente,boleias));
			t.start();            
        }
	}
}
