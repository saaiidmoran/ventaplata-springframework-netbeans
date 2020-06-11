/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package daoInterfaces;

import entidades.Producto;
import java.util.List;

/**
 *
 * @author saaii
 */
public interface ProductoDaoInterface {
    
    public Producto buscarProductoPorId(int id);
    public Producto buscarProductoPorNombre(String nombre);
    public List<Producto> consultarTodos();
    public boolean agregarProducto(Producto producto);
    public boolean modificarProducto(Producto producto);
    public boolean eliminarProducto(Producto producto); 
    
}
