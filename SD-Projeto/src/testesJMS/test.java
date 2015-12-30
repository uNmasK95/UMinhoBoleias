package testesJMS;
import java.util.Iterator;

public class test implements Runnable{
 private Boleias b;

public test(Boleias b) {
	super();
	this.b = b;
}

@Override
public void run() {
	while (true){
		Iterator<User> i = b.getUsers().values().iterator();
		int cont=0;
		while(i.hasNext()){
			if(i.next().isLogin()){
				cont++;
			}
		}
		System.out.println("Logs:" + cont);
		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
}
 
}
