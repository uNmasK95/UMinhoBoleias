/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Business;
/**
 *
 * @author Octavio
 */
public class ServerMain {
    
    public static void main(String[] args) throws Exception {
    	int port = 6969;
    	Servidor s  = new  Servidor(port);
    	System.out.println("Vou inicicar o servidor na porta " + port);
    	s.startServer();
    }
    
}
