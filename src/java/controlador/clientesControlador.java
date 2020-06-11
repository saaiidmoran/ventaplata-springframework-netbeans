/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controlador;

import daoImplements.ClientesDaoImplement;
import entidades.Cliente;
import entidades.Detalleventa;
import java.io.UnsupportedEncodingException;
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
@RequestMapping("/clientes")
public class clientesControlador {
    
    private StringBuilder builder = null;
    private Cliente c = null;
    
    @Autowired
    private ClientesDaoImplement clienteImpl;
    
    @RequestMapping(value = "clientesRegistrados", method = RequestMethod.GET)
    @ResponseBody
    public String muestraClientes() throws UnsupportedEncodingException{
        return creaTablaClientesTodos();
    }
    
    @RequestMapping(value = "filtraClientesRegistrados/{idcliente}/{cliente}", method = RequestMethod.GET)
    @ResponseBody
    public String filtraClientes(@PathVariable (value = "idcliente") int idcliente,
                                 @PathVariable (value = "cliente") String cliente) throws UnsupportedEncodingException{
        if(idcliente == 0 && cliente.equals("todos")){
            return creaTablaClientesTodos();
        }else{
            return creaTablaClientesFiltrada(idcliente,cliente);            
        }
    }
    
    @RequestMapping(value = "registrarCliente/{nombre}/{telefono}/{correo}/{domicilio}", method = RequestMethod.POST)
    @ResponseBody
    public String registrarCliente(@PathVariable(value = "nombre") String nombre
            , @PathVariable(value = "telefono") String telefono
            , @PathVariable(value = "correo") String correo
            , @PathVariable(value = "domicilio") String domicilio) throws UnsupportedEncodingException{
        
        System.gc();
        c = new Cliente();
        builder = new StringBuilder();
        c.setNombre(nombre);
        c.setNoTelefonico(telefono);
        c.setCorreo(correo);
        c.setDomicilio(domicilio);
        if(clienteImpl.agregarCliente(c)){
            builder.append("<script> alert(\"Cliente agregado correctamente\"); </script>");
            return new String(builder.toString().getBytes("UTF-8"),"ISO-8859-1");
        }else{
            builder.append("<script> alert(\"Cliente no agregado debido a un error\"); </script>");
            return new String(builder.toString().getBytes("UTF-8"),"ISO-8859-1");
        }       
    }
    
    @RequestMapping(value = "modificarCliente/{idcliente}/{nombre}/{telefono}/{correo}/{domicilio}", method = RequestMethod.POST)
    @ResponseBody
    public String modificarCliente(
              @PathVariable(value = "idcliente") int idcliente
            , @PathVariable(value = "nombre") String nombre
            , @PathVariable(value = "telefono") String telefono
            , @PathVariable(value = "correo") String correo
            , @PathVariable(value = "domicilio") String domicilio) throws UnsupportedEncodingException{
        System.gc();
        c = new Cliente(idcliente,nombre,telefono,correo,domicilio);
        builder = new StringBuilder();
        if(clienteImpl.modificarCliente(c)){
            builder.append("<script> alert(\"Cliente modificado correctamente\"); </script>");
            return new String(builder.toString().getBytes("UTF-8"),"ISO-8859-1");
        }else{
            builder.append("<script> alert(\"Cliente no modificado debido a un error\"); </script>");
            return new String(builder.toString().getBytes("UTF-8"),"ISO-8859-1");
        }
    }
    
    @RequestMapping(value = "eliminarCliente/{idcliente}", method = RequestMethod.POST)
    @ResponseBody
    public String eliminarCliente(@PathVariable(value = "idcliente") int idcliente) throws UnsupportedEncodingException{
        System.gc();
        c = clienteImpl.buscarClientePorId(idcliente);
        builder = new StringBuilder();
        if(clienteImpl.eliminarCliente(c)){
            builder.append("<script> alert(\"Cliente eliminado correctamente\"); </script>");
            return new String(builder.toString().getBytes("UTF-8"),"ISO-8859-1");
        }else{
            builder.append("<script> alert(\"Cliente no eliminado debido a un error\"); </script>");
            return new String(builder.toString().getBytes("UTF-8"),"ISO-8859-1");
        }       
    }
    
    @RequestMapping(value = "comprasCliente/{idcliente}", method = RequestMethod.GET)
    @ResponseBody
    public String comprasCliente(@PathVariable(value = "idcliente") int idcliente) throws UnsupportedEncodingException{
        System.gc();
        c = clienteImpl.buscarClientePorId(idcliente);
        builder = new StringBuilder();
        if(!c.getDetalleventaCollection().isEmpty()){
            for(Detalleventa compra : c.getDetalleventaCollection()){
                builder.append("<tr>");
                builder.append("<td>").append(compra.getFecha()).append("</td>");
                builder.append("<td>$").append(compra.getTotal()).append("</td>");
                builder.append("<td>$").append(compra.getAbono()).append("</td>");
                builder.append("<td>").append(compra.getModalidadventaidmodalidadVenta().getModalidad()).append("</td>");
                builder.append("<td>").append(compra.getEstatusventaidestatusVenta().getEstatusVenta()).append("</td>");
                builder.append("<td>").append(compra.getUsuarioIdusuario().getUsrnombre()).append("</td>");
                builder.append("</tr>");                
            }            
        }else{
            builder.append("<tr><td colspan=\"3\">No se encuentran compras registradas con este cliente</td></tr>");
        }        
        return new String(builder.toString().getBytes("UTF-8"),"ISO-8859-1");
    }
    
    private String creaTablaClientesTodos() throws UnsupportedEncodingException{
        System.gc();
        builder = new StringBuilder();
        clienteImpl.consultarTodos().stream().map((listaCliente) -> {
            builder.append("<tr>");
            return listaCliente;
        }).map((listaCliente) -> {
            builder.append("<td>").append(listaCliente.getIdcliente()).append("</td>");
            return listaCliente;
        }).map((listaCliente) -> {
            builder.append("<td>").append(listaCliente.getNombre()).append("</td>");
            return listaCliente;
        }).map((listaCliente) -> {
            builder.append("<td>").append(listaCliente.getNoTelefonico()).append("</td>");
            return listaCliente;
        }).map((listaCliente) -> {
            builder.append("<td>").append(listaCliente.getCorreo()).append("</td>");
            return listaCliente;
        }).map((listaCliente) -> {
            builder.append("<td>").append(listaCliente.getDomicilio()).append("</td>");
            return listaCliente;
        }).map((listaCliente) -> {
            builder.append("<td><button type=\"button\" class=\"btn btn-warning\" data-toggle=\"modal\" data-target=\"#modal-mod-cliente\" data-idcliente=\"").append(listaCliente.getIdcliente()).append("\" data-nombrecliente=\"").append(listaCliente.getNombre()).append("\" data-telefono=\"").append(listaCliente.getNoTelefonico()).append("\" data-correo=\"").append(listaCliente.getCorreo()).append("\" data-domicilio=\"").append(listaCliente.getDomicilio()).append("\" title=\"Modificar\"><i class=\"fas fa-edit\"></i></button>  <button type=\"button\" class=\"btn btn-danger\" data-toggle=\"modal\" data-target=\"#modal-elim-cliente\" title=\"Eliminar\" data-idcliente=\"").append(listaCliente.getIdcliente()).append("\"><i class=\"fas fa-trash\"></i></button>  <button type=\"button\" class=\"btn btn-info\" data-toggle=\"modal\" data-target=\"#modal-compras-cliente\" title=\"Compras del cliente\" data-idcliente=\"").append(listaCliente.getIdcliente()).append("\"><i class=\"fas fa-user-tag\"></i></button></td>");
            return listaCliente;
        }).forEach((_item) -> {
            builder.append("</tr>");
        });
        return new String(builder.toString().getBytes("UTF-8"),"ISO-8859-1");
    }
    
    private String creaTablaClientesFiltrada(int idcliente, String cliente) throws UnsupportedEncodingException{
        System.gc();
        builder = new StringBuilder();
        clienteImpl.consultarTodos().stream().filter((listaCliente) -> (listaCliente.getIdcliente().equals(idcliente) | listaCliente.getNombre().equals(cliente))).map((listaCliente) -> {
                builder.append("<tr>");
                return listaCliente;
            }).map((listaCliente) -> {
                builder.append("<td>").append(listaCliente.getIdcliente()).append("</td>");
                return listaCliente;
            }).map((listaCliente) -> {
                builder.append("<td>").append(listaCliente.getNombre()).append("</td>");
                return listaCliente;
            }).map((listaCliente) -> {
                builder.append("<td>").append(listaCliente.getNoTelefonico()).append("</td>");
                return listaCliente;
            }).map((listaCliente) -> {
                builder.append("<td>").append(listaCliente.getCorreo()).append("</td>");
                return listaCliente;
            }).map((listaCliente) -> {
                builder.append("<td>").append(listaCliente.getDomicilio()).append("</td>");
                return listaCliente;
            }).map((listaCliente) -> {
                builder.append("<td><button type=\"button\" class=\"btn btn-warning\" data-toggle=\"modal\" data-target=\"#modal-mod-cliente\" data-idcliente=\"").append(listaCliente.getIdcliente()).append("\" data-nombrecliente=\"").append(listaCliente.getNombre()).append("\" data-telefono=\"").append(listaCliente.getNoTelefonico()).append("\" data-correo=\"").append(listaCliente.getCorreo()).append("\" data-domicilio=\"").append(listaCliente.getDomicilio()).append("\" title=\"Modificar\"><i class=\"fas fa-edit\"></i></button>  <button type=\"button\" class=\"btn btn-danger\" data-toggle=\"modal\" data-target=\"#modal-elim-cliente\" title=\"Eliminar\" data-idcliente=\"").append(listaCliente.getIdcliente()).append("\"><i class=\"fas fa-trash\"></i></button>  <button type=\"button\" class=\"btn btn-info\" data-toggle=\"modal\" data-target=\"#modal-compras-cliente\" title=\"Compras del cliente\" data-idcliente=\"").append(listaCliente.getIdcliente()).append("\"><i class=\"fas fa-user-tag\"></i></button></td>");
                return listaCliente;
            }).forEach((_item) -> {
                builder.append("</tr>");
            });
        return new String(builder.toString().getBytes("UTF-8"),"ISO-8859-1");
    }
}
