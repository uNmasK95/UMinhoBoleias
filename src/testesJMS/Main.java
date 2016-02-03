package testesJMS;
import java.io.IOException;

public class Main {

	public static void main(String[] args) throws IOException {
		Boleias b = new Boleias();
		test t = new test(b);
		b.regista("jms", "12345");
		b.regista("jms2", "12345");
		b.regista("jms4", "12345");
		b.regista("jms5", "12345");
		b.regista("jms6", "12345");
		Server s = new Server(12345, b);
		Thread ta = new Thread(t);
		ta.start();
		System.out.println("Server criado");
		s.startServer();

	}

}
