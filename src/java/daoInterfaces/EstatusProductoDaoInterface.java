/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package daoInterfaces;

import entidades.Estatusproducto;
import java.util.List;

/**
 *
 * @author saaii
 */
public interface EstatusProductoDaoInterface {
    
    public Estatusproducto buscarEstatusProductoPorId(int id);
    public Estatusproducto buscarEstatusProductoPorNombre(String nombre);
    public List<Estatusproducto> consultarTodos();
    public boolean agregarEstatusProducto(Estatusproducto estatusproducto);
    public boolean modificarEstatusProducto(Estatusproducto estatusproducto);
    public boolean eliminarEstatusProducto(Estatusproducto estatusproducto);
    
}
