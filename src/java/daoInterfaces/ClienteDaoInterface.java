/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package daoInterfaces;

import entidades.Cliente;
import java.util.List;

/**
 *
 * @author saaii
 */
public interface ClienteDaoInterface {
    
    public Cliente buscarClientePorId(int id);
    public Cliente buscarClientePorNombre(String nombre);
    public List<Cliente> consultarTodos();
    public boolean agregarCliente(Cliente cliente);
    public boolean modificarCliente(Cliente cliente);
    public boolean eliminarCliente(Cliente cliente);    
}
