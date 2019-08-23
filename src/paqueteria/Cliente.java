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
public class Cliente {
    private int nit;
    private boolean estado;
    public Cliente(int nit){
        this.nit = nit;
        this.estado = true;
    }

    public int getNit() {
        return nit;
    }    
    
    public boolean isEstado() {
        return estado;
    }

    public void setEstado(boolean estado) {
        this.estado = estado;
    }
    
}
