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
import java.util.ArrayList;
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
    
    protected void crearDestino(int codDestino, String destino, int tarifa){
        PreparedStatement dp;
        try {
            dp = conection.prepareStatement("INSERT Destinos VALUES(?, ?, ?)");
            dp.setInt(1, codDestino);
            dp.setString(2, destino);
            dp.setInt(3, tarifa);
            dp.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(EnlaceJDBC.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
    
    protected ArrayList<String> destinosDisp(){
        int cantReg = 0;
        ArrayList<String> lista = new ArrayList();
        try {             
            System.out.println("Conexion: "+conection.getCatalog() );
            PreparedStatement dpCantidadRegistros = conection.prepareStatement("SELECT codDestino FROM Destinos");
            ResultSet resultado = dpCantidadRegistros.executeQuery();
            
            while(resultado.next()){            
                lista.add(resultado.getString(1));
                
            }
            return lista;
                        
        } catch (SQLException ex) {
            System.out.println("ERROR: fallo la conexion");
            System.out.println(ex.getMessage());
        }
            
        
        return null;   
    
    }
    
    protected ArrayList<String> puntosControl(){
        int cantReg = 0;
        ArrayList<String> lista = new ArrayList();
        try {             
            System.out.println("Conexion: "+conection.getCatalog() );
            PreparedStatement dpCantidadRegistros = conection.prepareStatement("SELECT codRuta FROM Ruta");
            ResultSet resultado = dpCantidadRegistros.executeQuery();
            
            while(resultado.next()){            
                lista.add(resultado.getString(1));
                
            }
            return lista;
                        
        } catch (SQLException ex) {
            System.out.println("ERROR: fallo la conexion");
            System.out.println(ex.getMessage());
        }
            
        
        return null;   
    
    }
   
    protected void crearRutas (int codRuta, String nombreRuta, int codDestino){
        PreparedStatement dp;
        try {
            dp = conection.prepareStatement("INSERT Ruta VALUES (?, ?, ?)");
            dp.setInt(1, codRuta);
            dp.setString(2, nombreRuta);
            dp.setInt(3, codDestino);
            dp.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(EnlaceJDBC.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    protected void crearUsuario (long cui, String nombre, int edad, int rol, String pass){
        PreparedStatement dp;
        try {
            dp = conection.prepareStatement("INSERT Usuarios VALUES (?, ?, ?, ?, ?, ?)");
            dp.setLong(1, cui);
            dp.setString(2, nombre);
            dp.setInt(3, edad);
            dp.setInt(4, rol);
            dp.setString(5, pass);
            dp.setBoolean(6, true);
            dp.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(EnlaceJDBC.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    protected void crearPuntoControl (int codPC, int codRuta, String nombrePC, int cola){
        PreparedStatement dp;
        try {            
            PreparedStatement dpTarifa = conection.prepareStatement("SELECT tarifa FROM TarifaGlobal ORDER BY codTarifa DESC");        
            ResultSet resultado = dpTarifa.executeQuery();     
            resultado.next();
            int tarifa = resultado.getInt(1);
            
            dp = conection.prepareStatement("INSERT PuntoControl (codPuntoControl, codRuta, nombre_punto_control, cantPaqueteCola, estadoPuntoControl, tarifa) VALUES (?, ?, ?, ?, ?, ?)");
            dp.setInt(1, codPC);
            dp.setInt(2, codRuta);
            dp.setString(3, nombrePC);
            dp.setInt(4, cola);
            dp.setBoolean(5, true);
            dp.setInt(6, tarifa);
            dp.executeUpdate();
        } catch (SQLException ex) {
            System.out.println("ERROR: fallo la conexion");
            System.out.println(ex.getMessage());
        }
    }
    
    protected void cambiarNombreRuta (String nuevoNombre, int codRuta){
        PreparedStatement dp;
        try {            
            dp = conection.prepareStatement("UPDATE Ruta SET nombreRuta = '?' where codRuta = ?");
            dp.setString(1, nuevoNombre);  
            dp.setInt(2, codRuta); 
            dp.executeUpdate();
        } catch (SQLException ex) {
            System.out.println("ERROR: fallo la conexion");
            System.out.println(ex.getMessage());
        }
    }
    
   
}
