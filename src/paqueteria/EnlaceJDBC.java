/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package paqueteria;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

/**
 *
 * @author grifiun
 */
public class EnlaceJDBC {    
    Connection conection = null;    
    String USER = "root";
    String PASS = "123@MySQL";
    String STRING_CONECTION = "jdbc:mysql://localhost:3306/paqueteria";
    public EnlaceJDBC() {
        try {
            conection = DriverManager.getConnection(STRING_CONECTION, USER, PASS);
        } catch (SQLException ex) {
            Logger.getLogger(EnlaceJDBC.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
   
    
    protected int verificarPass(long cui, String password){      
        
        try{   
            System.out.println("Conexion: "+conection.getCatalog() );
            PreparedStatement declaracionPreparada = conection.prepareStatement("SELECT password, rol, estado FROM Usuarios WHERE cui = ?");
            declaracionPreparada.setLong(1, cui);
            ResultSet resultado = declaracionPreparada.executeQuery();
            while(resultado.next()){
                String passTemporal = resultado.getString("password");
                if(password.equals(passTemporal)){
                   boolean estado = resultado.getBoolean("estado");
                   if(estado){
                       int rol = resultado.getInt("rol"); 
                       return rol;                   
                   }else{
                       return 4;
                   }
                   
                }   
            }
            
        }catch(SQLException e){
            System.out.println("ERROR: fallo la conexion");
            System.out.println(e.getMessage());
        }
        return 0;        
    }
    
    protected void modificarTarifaGlobal(int nuevaTarifa){
        
        try{
            int cantReg = 0;
            System.out.println("Conexion: "+conection.getCatalog() );             
            PreparedStatement dpCantidadRegistros = conection.prepareStatement("SELECT COUNT(tarifa) FROM TarifaGlobal");
            ResultSet resultado = dpCantidadRegistros.executeQuery();
            while(resultado.next()){                
                cantReg = resultado.getInt(1) + 1;                     
            }  
            
            
            PreparedStatement dpRegistrarNuevaTarifa = conection.prepareStatement("INSERT TarifaGlobal (tarifa, codTarifa) VALUES (?, ?)");
            dpRegistrarNuevaTarifa.setInt(1, nuevaTarifa);
            dpRegistrarNuevaTarifa.setInt(2, cantReg);
            dpRegistrarNuevaTarifa.executeUpdate();
            /*
            PreparedStatement declaracionPreparada = conection.prepareStatement("INSERT TarifaGlobal()");
            */
        }catch(SQLException e){
            System.out.println("ERROR: fallo la conexion");
            System.out.println(e.getMessage());
        }
    }
    
   
}
