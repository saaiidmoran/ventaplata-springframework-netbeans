/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package daoInterfaces;

import entidades.Venta;
import java.util.List;

/**
 *
 * @author saaii
 */
public interface VentaDaoInterface {
    
    public Venta buscarVentaPorId(int id);
    public List<Venta> consultarTodas();
    public boolean agregarVenta(Venta venta);
    public boolean modificarVenta(Venta venta);
    public boolean eliminarVenta(Venta venta); 
    
}
