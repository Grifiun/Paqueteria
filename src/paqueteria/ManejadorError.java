/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package paqueteria;

/**
 *
 * @author grifiun
 */
public class ManejadorError {
    public static boolean verificarCUI(String cui){
        if(cui.length() == 13){
            return true;
        }
        return false;
    }
    
    public static boolean verificarCampo(String campo){
        if(campo.length() > 0){
            return true;
        }        
        return false;
    }
}
