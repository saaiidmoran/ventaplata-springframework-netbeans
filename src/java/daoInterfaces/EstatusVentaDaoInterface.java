/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package daoInterfaces;

import entidades.Estatusventa;
import java.util.List;

/**
 *
 * @author saaii
 */
public interface EstatusVentaDaoInterface {
    
    public Estatusventa buscarEstatusVentaPorId(int id);
    public Estatusventa buscarEstatusVentaPorNombre(String nombre);
    public List<Estatusventa> consultarTodos();
    public boolean agregarEstatusVenta(Estatusventa estatusventa);
    public boolean modificarEstatusVenta(Estatusventa estatusventa);
    public boolean eliminarEstatusVenta(Estatusventa estatusventa);
    
}
