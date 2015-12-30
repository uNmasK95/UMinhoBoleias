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
public class Main {

    private static Core c = new Core();
    
    public static void main(String[] args) throws Exception {
        c.registarUser();
        c.login();
    }
    
}
