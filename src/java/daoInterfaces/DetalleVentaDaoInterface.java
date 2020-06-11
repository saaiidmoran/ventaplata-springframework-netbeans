/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package daoInterfaces;

import entidades.Detalleventa;
import java.util.List;

/**
 *
 * @author saaii
 */
public interface DetalleVentaDaoInterface {
    
    public Detalleventa buscarDetalleVentaPorId(int id);
    public Detalleventa buscarDetalleVentaPorFecha(String fecha);
    public Detalleventa buscarUltimoAgregado();
    public List<Detalleventa> buscarTodos();
    public boolean registrarDetalleVenta(Detalleventa dv);
    public boolean modificarDetalleVenta(Detalleventa dv);
    public boolean eliminarDetalleVenta(Detalleventa dv);
    
}
