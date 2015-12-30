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
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author ruifreitas
 */
public class ServerConnection implements Runnable{
    
    private Socket sock;
    private UMinhoBoleias umb;
    private BufferedWriter out;
    private BufferedReader in;

    
    public ServerConnection(Socket sock,UMinhoBoleias umb){
        try {
            sock = new Socket(remotehost, port);
            in = new BufferedReader(new InputStreamReader(sock.getInputStream()));
            out = new BufferedWriter(new OutputStreamWriter(sock.getOutputStream()));
        } catch (IOException ex) {
            Logger.getLogger(ClienteMain.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        
    }
    
    @Override
    public void run() {
        
    }
    
}
