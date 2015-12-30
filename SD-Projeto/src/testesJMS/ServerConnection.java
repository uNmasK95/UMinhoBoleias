package testesJMS;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.util.GregorianCalendar;

public class ServerConnection implements Runnable{
	private static double velocid=60;
	private Boleias bol;
	private Socket sock;
	private BufferedWriter out;
	private BufferedReader in;
	private BufferedWriter pairOut;
	private BufferedReader pairIn;
	private User onlogin;
	public ServerConnection(Socket sock, Boleias bol) throws IOException{
		this.bol=bol;
		this.sock=sock;
		this.out=new BufferedWriter(new OutputStreamWriter(sock.getOutputStream()));
		this.in=new BufferedReader(new InputStreamReader(sock.getInputStream()));
		this.pairOut=null;
		this.pairIn=null;
		this.onlogin = null;
	}
	
	private int lerint(String txt,boolean err) throws IOException{
		Integer ret =0;
		if(err){
			out.write("Erro ao ler inetiro!!\n");
		}
		out.write("Diga a sua localizaçao em "+txt+": ");
		out.flush();
		String inp = in.readLine();
		try{
			ret = Integer.parseInt(inp);
		}catch(Exception e){
			ret = this.lerint(txt, true);
		}
		return ret;
	}
	private int login() throws IOException{ //-1 motorista 1 passageio 0 erro
		int tipo =-1;
		out.write("Diga o tipo de Cliente que é:\n\t1-Passageiro\tOuro-Motorista\n");
		out.flush();
		
		String op = in.readLine();
		if(op.equals("1")){
			tipo=1;
		}
		out.write("Diga o seu username: ");
		out.flush();
		String username = in.readLine();
		out.write("Diga a sua Password: ");
		out.flush();
		String pass = in.readLine();
		int x = this.lerint("x",false);
		int y = this.lerint("y",false);
		if(tipo==1){
			out.write("A verificar Login e à procura de motorista!!\n");
			out.flush();
			User u = this.bol.loginPass(username, pass, x, y, this.out, this.in);
			if(u==null){
				out.write("Verifique as cardenciais\n");
				out.flush();
				tipo=0;
			}else{
				this.onlogin=u;
				User pair = u.getLink();
				this.pairIn=pair.getIn();
				this.pairOut=pair.getOut();
				String pairname= pair.getUsrname();
				String modelo = pair.getModelo();
				String matricul = pair.getMatric();
				int pairx = pair.getX();
				int pairy = pair.getY();
				double dist = u.distancia(pairx, pairy);
				double time = dist/velocid;
				out.write("Login com sucesso\n"
						+ "O utlizador "+ pairname +""
						+ " encontara-se na sua localização em aproximademante "
						+ time + " \n No veiculo "+ modelo+" com a matricula "+
						matricul+".\nPor favor aguarde\n");
				out.flush();
				
				
			}
		}else{
			out.write("Qual a matricula do seu veiculo: ");
			out.flush();
			String matric = in.readLine();
			out.write("Qual o modelo do seu veiculo: ");
			out.flush();
			String modelo = in.readLine();
			out.write("A verificar Login e à procura de passageiro!!\n");
			out.flush();
			User mot = this.bol.loginMot(username, pass, modelo, matric, x, y, out, in);
			if(mot==null){
				out.write("Verifique as cardenciais\n");
				out.flush();
				tipo = 0;
			}else{
				//out.write("A verificar Login e à procura de passageiro\n");
				this.onlogin=mot;
				User pair = mot.getLink();
				this.pairIn=pair.getIn();
				this.pairOut=pair.getOut();
				String pairname= pair.getUsrname();
				int pairx = pair.getX();
				int pairy = pair.getY();
				double dist = mot.distancia(pairx, pairy);
				double time = dist/velocid;
				out.write("Login com sucesso\n"
						+ "O utlizador "+ pairname +""
						+ " encontara-se na sua localização (" + pairx +","+pairy +") à sua espera,"
						+ " o tempo previsto de chegada é: "
						+ time + ", contudo quando chegar ao local indique escrevendo algo nos comandos\n");
				out.flush();
			}
		}
		return tipo;
	}
	
	private void logout(boolean erro) throws IOException{
		if(erro){
			out.write("Volte a tenar a ligação\n");
		}else{
			out.write("Adeus\n");
		}
		out.flush();
		if(!erro){
			this.bol.logout(this.onlogin.getUsrname());
			this.out.close();
			this.in.close();
		}
	}
	
	@Override
	public void run() {
		try{
			int tipo = this.login();
			if(tipo==0){
				this.logout(true);
			}else if(tipo==1){//passageiro
				this.pairIn.readLine();
				this.pairOut.write("O carro atribuido já se econtra no local, viagem iniciada\n");
				this.pairOut.flush();
				//o motorista ja chegou
				this.out.write("Qaundo se encontrar no local perendido indique ecrevando algo\n");
				this.out.flush();
				//String preco = this.pairIn.readLine();
				//this.out.write("O preço é "+preco+" precione algo para pagar\n" );
				this.in.readLine();
				this.pairOut.write("O cliente efectou o pagamento, Fim\n");
				this.pairOut.flush();
				this.out.write("Fim\n");
				this.out.flush();
			}else{
				this.pairIn.readLine();
				this.out.write("O cliente indica que chefou ao local\nDigao preço da viagem: ");
				this.out.flush();
				String preco = this.in.readLine();
				this.pairOut.write("O preço é "+preco+" precione algo para pagar\n");
				//this.pairOut.write("Fim de viagem\n");
				this.pairOut.flush();
				//this.out.write("O cliente efectou o pagamento, Fim");
				//this.out.flush();
			}
			this.out.write("Carregue em algo para sair");
			this.in.readLine();
			this.logout(false);
		}catch(IOException e){
			try {
				this.logout(true);
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
		
	}

}
