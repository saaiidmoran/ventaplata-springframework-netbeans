/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package daoInterfaces;

import entidades.Usuario;
import java.util.List;

/**
 *
 * @author saaii
 */

public interface UsuarioDaoInterface {
    
    public Usuario buscarUsuarioPorNombre(String usr);    
    public Usuario buscarUsuarioPorId(int idusr);
    public List<Usuario> consultarTodos();
    public boolean agregarUsuario(Usuario usr);
    public boolean eliminarUsuario(Usuario usr);
    public boolean modificarUsuario(Usuario usr);
    
}
