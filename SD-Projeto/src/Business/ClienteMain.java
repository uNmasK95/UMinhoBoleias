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
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

import Aula7.Cliente;
import Aula7.DinheiroIndisponivel;

/**
 *
 * @author ruifreitas
 */
public class ClienteMain implements UMinhoBoleiasIface {

	private String email;
	private Socket sock;
	private BufferedWriter out;
	private BufferedReader in;

	private static Scanner input = new Scanner(System.in);

	public ClienteMain(String remotehost, int port) {
		try {
			sock = new Socket(remotehost, port);
			in = new BufferedReader(new InputStreamReader(sock.getInputStream()));
			out = new BufferedWriter(new OutputStreamWriter(sock.getOutputStream()));
		} catch (IOException ex) {
			Logger.getLogger(ClienteMain.class.getName()).log(Level.SEVERE, null, ex);
		}
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	private static String menuCliente() {
		StringBuilder ap = new StringBuilder();
		ap.append("0-Fazer logout");
		ap.append("1-Registar utilizador");
		ap.append("2-Login Utilizador");
		ap.append("3-Solicitar viagem");
		ap.append("4-Disponibilizar boleia");
		return ap.toString();
	}

	@Override
	public boolean registaUtilizador(String user, String pass) {
		String l;
		try {
			out.write(REGISTAUTILIZADOR);
			out.newLine();
			out.write(user + ":" + pass);
			out.newLine();
			out.flush();
			l = in.readLine();
			if (l.equals(KO)) {
				return false;
			}
		} catch (IOException ex) {
			return false;
		}
		return true;
	}

	@Override
	public boolean autenticar(String user, String pass) {
		String l;
		try {
			out.write(REGISTAUTILIZADOR);
			out.newLine();
			out.write(user + ":" + pass);
			out.newLine();
			out.flush();
			l = in.readLine();
			if (l.equals(KO)) {
				return false;
			} else {

			}
		} catch (IOException ex) {
			return false;
		}
		return true;
	}

	/*
	 * if(distancia==0){ ret = new
	 * String(condutor.getEmail()+":"+v.getMatricula()+":"+v.getModelo());
	 * }else{
	 * condutor.getEmail()+":"+v.getMatricula()+":"+v.getModelo()+":"+(condutor.
	 * getLoc().distancia(partida)/50));
	 */

	@Override
	public String solicitarViagem(String user, Local partida, Local destino) {
		String linhaResposta;
		try {
			out.write(SOLICITARVIAGEM);
			out.newLine();
			out.write(user + ":" + partida.toString() + ":" + destino.toString());
			out.newLine();
			out.flush();
			// foi enviado o pedido para o serverconnection
			// vai ficar a espera de uma resposta por parte do servidor
			linhaResposta = in.readLine(); // user:matricula:modelo:?tempo
			String[] arr = linhaResposta.split(":");
			if (arr.length == 3) {
				// o condutar já encontra-se no de partida
				System.out.println("O Utilizador " + arr[0] + " com o carro de matricula " + arr[1] + " e modelo "
						+ arr[2] + " encontra-se disponível e já se encontra no local de partida");
			} else {
				// o condutor nao se encontra no local
				System.out.println("O Utilizador " + arr[0] + " com o carro de matricula " + arr[1] + " e modelo "
						+ arr[2] + " irá demorar " + arr[2] + " minutos a apresentar-se no local de partida");
			}
			linhaResposta = in.readLine();

			// apos o serverconnection ter feito sleep do tempo de deslocacao do
			// condutor ate ao local de partida
			if (linhaResposta.equals(OK)) {
				if (arr.length == 3) {
					System.out.println("O Utilizador " + arr[0] + " com o carro de matricula " + arr[1] + " e modelo "
							+ arr[2] + " já chegou ao local de partida");
				}
			}
			// apos o serverconnection ter fetio o sleep do tempo de viagem
			// tem de receber o custo
			linhaResposta = in.readLine();
			System.out.println("A viagem chegou ao destino, teve um custo de " + linhaResposta + "€");
		} catch (IOException ex) {
			return "Viagem nao realizada";
		}
		return "Viagem realizada com sucesso";
	}

	@Override
	public String disponivelViagem(String user, Local actual, String matricula, String modelo, double custoUnitario) {
		String l;
		try {
			out.write(DISPONIVELVIAGEM);
			out.newLine();
			out.write(user + ":" + actual.toString() + ":" + matricula + ":" + modelo + ":" + custoUnitario);
			out.newLine();
			out.flush();

			System.out.print("Tem uma viagem para realizar em " + in.readLine());
			// espera
			// para
			// receber
			// um
			// localde partida de uma viagem
			System.out.println(" com o destino " + in.readLine());

			System.out.println("Prima 1 quando chegar ao destino");
			input.nextInt();

			out.write("" + custoUnitario);
			out.newLine();
			out.flush();
		} catch (IOException ex) {
			return null;
		}
		return null;
	}

	private static Local lerLocal() {
		System.out.print("X-");
		int x = input.nextInt();
		System.out.print("Y-");
		int y = input.nextInt();
		return new Local(x, y);
	}

	public static void main(String[] args) {
		Scanner input = new Scanner(System.in);
		ClienteMain c1 = new ClienteMain("localhost", 6969);
		int n;

		System.out.println(menuCliente());
		while ((n = input.nextInt()) != 0) {
			System.out.println(menuCliente());
			switch (n) {
			case 1: {
				System.out.println("=========Registar=========");
				System.out.println("Email");
				String email = input.nextLine();
				System.out.println("Password");
				String pass = input.nextLine();
				c1.registaUtilizador(email, pass);
				break;
			}
			case 2: {
				System.out.println("=========LOGIN=========");
				System.out.println("Email");
				String email = input.nextLine();
				System.out.println("Password");
				String pass = input.nextLine();
				c1.autenticar(email, pass);
				break;
			}
			case 3: {
				System.out.println("=========Solicitar Viagem=========");
				if (c1.getEmail() != null) {
					System.out.println("Local de Partida");
					Local partida = lerLocal();
					System.out.println("Local de Destino");
					Local destino = lerLocal();
					c1.solicitarViagem(c1.getEmail(), partida, destino);
				} else {
					System.out.println("Necessita de estar autenticado");
				}
				break;
			}
			case 4: {
				System.out.println("=========Disponibilizar Viagem=========");
				if (c1.getEmail() != null) {
					System.out.println("Local de Atual");
					Local atual = lerLocal();
					System.out.println("Matricula : __-__-__");
					String matricula = input.nextLine();
					System.out.println("Modelo :");
					String modelo = input.nextLine();
					System.out.println("Custo da viagem :");
					double custoUnitario = input.nextDouble();
					c1.disponivelViagem(c1.getEmail(), atual, matricula, modelo, custoUnitario);
				} else {
					System.out.println("Necessita de estar autenticado");
				}
				break;
			}
			default:
				System.out.println("Insira um numero do menu");
			}
		}
	}

}
