/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controlador;

import beans.login;
import daoImplements.UsuarioDaoImplement;
import entidades.Usuario;
import java.io.UnsupportedEncodingException;
import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 *
 * @author saaii
 */
@Controller
@RequestMapping("/usuarios")
public class usuariosControlador {
    
    private StringBuilder builder = null;
    private login login = null;
    private Usuario u = null;
    
    @Autowired
    private UsuarioDaoImplement usr;
    
    @RequestMapping(value = "usuariosRegistrados", method = RequestMethod.GET)
    @ResponseBody
    public String muestraUsuarios(HttpSession sesion) throws UnsupportedEncodingException {
        return creaTablaTodos(sesion);        
    }
    
    @RequestMapping(value = "filtrarUsuariosRegistrados/{idusuario}/{usuario}", method = RequestMethod.GET)
    @ResponseBody
    public String filtraUsuariosRegistrados(@PathVariable (value = "idusuario") int idusuario,
                                            @PathVariable (value = "usuario") String usuario, HttpSession sesion) 
                                            throws UnsupportedEncodingException{
        if(idusuario == 0 && usuario.equals("todos")){
            return creaTablaTodos(sesion); 
        }else{
            return creaTablaFiltrada(sesion,idusuario,usuario);
        }
    }
    
    @RequestMapping(value = "registrarUsuario/{nombre}/{clave}", method = RequestMethod.POST)
    @ResponseBody
    public String registrarUsuario(@PathVariable(value = "nombre") String nombre, @PathVariable(value="clave") String clave) throws UnsupportedEncodingException{
        System.gc();
        u = new Usuario();
        builder = new StringBuilder();
        u.setUsrnombre(nombre);
        u.setClave(clave);
        if(usr.agregarUsuario(u)){
            builder.append("<script> alert(\"Usuario agregado correctamente\"); </script>");
            return new String(builder.toString().getBytes("UTF-8"),"ISO-8859-1");
        }else{
            builder.append("<script> alert(\"Usuario no agregado debido a un error\"); </script>");
            return new String(builder.toString().getBytes("UTF-8"),"ISO-8859-1");
        }
       
    }
    
    @RequestMapping(value = "eliminarUsuario/{idusr}", method = RequestMethod.POST)
    @ResponseBody
    public String eliminarUsuario(@PathVariable(value = "idusr") int idUsr) throws UnsupportedEncodingException{
        System.gc();
        u = usr.buscarUsuarioPorId(idUsr);
        builder = new StringBuilder();
        if(usr.eliminarUsuario(u)){
            builder.append("<script> alert(\"Usuario eliminado correctamente\"); </script>");
            return new String(builder.toString().getBytes("UTF-8"),"ISO-8859-1");
        }else{
            builder.append("<script> alert(\"Usuario no eliminado debido a un error\"); </script>");
            return new String(builder.toString().getBytes("UTF-8"),"ISO-8859-1");
        }
        
    }
    
    @RequestMapping(value = "modificarUsuario/{idusr}/{usrnombre}/{usrclave}", method = RequestMethod.POST)
    @ResponseBody
    public String modificarUsuario(@PathVariable(value = "idusr") int idusr, @PathVariable(value = "usrnombre") String usrnombre, @PathVariable(value = "usrclave") String usrclave) throws UnsupportedEncodingException{
        System.gc();
        u = new Usuario(idusr,usrnombre,usrclave);
        builder = new StringBuilder();
        if(usr.modificarUsuario(u)){
            builder.append("<script> alert(\"Usuario modificado correctamente\"); </script>");
            return new String(builder.toString().getBytes("UTF-8"),"ISO-8859-1");
        }else{
            builder.append("<script> alert(\"Usuario no modificado debido a un error\"); </script>");
            return new String(builder.toString().getBytes("UTF-8"),"ISO-8859-1");            
        }
    }
    
    private String creaTablaTodos(HttpSession sesion) throws UnsupportedEncodingException{
        System.gc();
        login = (login)sesion.getAttribute("login");
        builder = new StringBuilder();
        usr.consultarTodos().stream().filter((listaUsuario) -> (!listaUsuario.getUsrnombre().equals(login.getUsuario()))).map((listaUsuario) -> {
            builder.append("<tr>");
            return listaUsuario;
        }).map((listaUsuario) -> {
            builder.append("<td>").append(listaUsuario.getIdusuario()).append("</td>");
            return listaUsuario;
        }).map((listaUsuario) -> {
            builder.append("<td>").append(listaUsuario.getUsrnombre()).append("</td>");
            return listaUsuario;
        }).map((listaUsuario) -> {
             builder.append("<td><button type=\"button\" class=\"btn btn-warning\" data-toggle=\"modal\" data-target=\"#modal-mod-usuario\" data-idusr=\"").append(listaUsuario.getIdusuario()).append("\" data-nombreusr=\"").append(listaUsuario.getUsrnombre()).append("\" data-claveusr=\"").append(listaUsuario.getClave()).append("\" title=\"Modificar\"><i class=\"fas fa-edit\"></i></button>  <button type=\"button\" class=\"btn btn-danger\" data-toggle=\"modal\" data-target=\"#modal-elim-usuario\" title=\"Eliminar\" data-idusr=\"").append(listaUsuario.getIdusuario()).append("\"><i class=\"fas fa-trash\"></i></button></td>");
            return listaUsuario;
        }).forEach((_item) -> {
            builder.append("</tr>");
        });
        return new String(builder.toString().getBytes("UTF-8"),"ISO-8859-1");
    }
    
    private String creaTablaFiltrada(HttpSession sesion, int idusuario, String usuario)throws UnsupportedEncodingException{
        System.gc();
        login = (login)sesion.getAttribute("login");
        builder = new StringBuilder();
        usr.consultarTodos().stream().filter((listaUsuario) -> (!listaUsuario.getUsrnombre().equals(login.getUsuario()) && (listaUsuario.getUsrnombre().equals(usuario) | listaUsuario.getIdusuario() == idusuario))).map((listaUsuario) -> {
            builder.append("<tr>");
            return listaUsuario;
        }).map((listaUsuario) -> {
            builder.append("<td>").append(listaUsuario.getIdusuario()).append("</td>");
            return listaUsuario;
        }).map((listaUsuario) -> {
            builder.append("<td>").append(listaUsuario.getUsrnombre()).append("</td>");
            return listaUsuario;
        }).map((listaUsuario) -> {
            builder.append("<td><button type=\"button\" class=\"btn btn-warning\" data-toggle=\"modal\" data-target=\"#modal-mod-usuario\" data-idusr=\"").append(listaUsuario.getIdusuario()).append("\" data-nombreusr=\"").append(listaUsuario.getUsrnombre()).append("\" data-claveusr=\"").append(listaUsuario.getClave()).append("\" title=\"Modificar\"><i class=\"fas fa-edit\"></i></button>  <button type=\"button\" class=\"btn btn-danger\" data-toggle=\"modal\" data-target=\"#modal-elim-usuario\" title=\"Eliminar\" data-idusr=\"").append(listaUsuario.getIdusuario()).append("\"><i class=\"fas fa-trash\"></i></button></td>");
            return listaUsuario;
        }).forEach((_item) -> {
            builder.append("</tr>");
        });
        return new String(builder.toString().getBytes("UTF-8"),"ISO-8859-1");
    }
    
}
