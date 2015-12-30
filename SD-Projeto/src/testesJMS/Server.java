package testesJMS;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
	private ServerSocket sSock = null;
	private int port;
	private Boleias bol;
	public Server(int port,Boleias bol) throws IOException {
		this.port = port;
		this.bol=bol;
		this.sSock = new ServerSocket(this.port);
	}
	public Server(int port) throws IOException {
		this.port = port;
		this.bol=new Boleias();
		this.sSock = new ServerSocket(this.port);
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
