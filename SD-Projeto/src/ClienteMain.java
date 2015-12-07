/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package src;

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

/**
 *
 * @author ruifreitas
 */
public class ClienteMain implements UMinhoBoleiasIface {

    private Socket sock;
    private BufferedWriter out;
    private BufferedReader in;

    private Scanner input = new Scanner(System.in);
    public ClienteMain(String remotehost, int port) {
        try {
            sock = new Socket(remotehost, port);
            in = new BufferedReader(new InputStreamReader(sock.getInputStream()));
            out = new BufferedWriter(new OutputStreamWriter(sock.getOutputStream()));
        } catch (IOException ex) {
            Logger.getLogger(ClienteMain.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        ClienteMain c1 = new ClienteMain("localhost", 6969);
        int n;

        while ((n = input.nextInt()) != 0) {
            switch (n) {
                case 1: {
                    break;
                }
                case 2: {
                    break;
                }
                case 3: {
                    break;
                }
                case 4: {
                    break;
                }
                case 5: {
                    break;
                }
            }
        }
    }

    @Override
    public boolean registaUtilizador(String user, String pass) {
        String l;
        try {
            out.write(REGISTAUTILIZADOR);
            out.newLine();
            out.flush();
            in.readLine(); // espera por uma resposta ok do servidor
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
            out.write(AUTENTICAR);
            out.newLine();
            out.flush();
            in.readLine(); // espera por uma resposta ok do servidor
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
    public String solicitarViagem(String user, Local partida, Local destino) {
        String l;
        try {
            out.write(SOLICITARVIAGEM);
            out.newLine();
            out.flush();
            in.readLine(); // espera por uma resposta ok do servidor
            out.write(user + ":" + partida.toString() + ":" + destino.toString());
            out.newLine();
            out.flush();

            l = in.readLine(); //matricula:modelo:tempo
            String[] arr = l.split(":");
            if (arr.length == 2) {
                System.out.println("O carro com a matricula " + arr[0] + ", modelo " + arr[1] + " encontra-se disponível");
            } else {
                System.out.println("O carro com a matricula " + arr[0] + ", modelo " + arr[1] + " irá encontrar-se disponivel dentro de " + arr[2] + "minutos");
            }
            l = in.readLine();
            System.out.println("Disponivel para realizar o transporte, viagem irá demorar " + l + "minutos");
            l=in.readLine();
            System.out.println("A viagem chegou ao destino, teve um custo de " + l + "€");
            out.write(OK);
        } catch (IOException ex) {
            return null;
        }
        return null;
    }

    @Override
    public String disponivelViagem(String user, Local actual, String matricula, String modelo, double custoUnitario) {
        String l;
        try {
            out.write(DISPONIVELVIAGEM);
            out.newLine();
            out.flush();
            in.readLine(); // espera por uma resposta ok do servidor
            out.write(user + ":" + actual.toString() + ":" + matricula + ":" + modelo);
            out.newLine();
            out.flush();

            System.out.print("Tem uma viagem para realizar em " + in.readLine()); //espera para receber um local de partida de uma viagem
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

}
