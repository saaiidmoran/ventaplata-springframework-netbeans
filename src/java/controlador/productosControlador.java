/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controlador;

import daoImplements.EstatusProductoDaoImplement;
import daoImplements.ProductoDaoImplement;
import entidades.Producto;
import java.io.UnsupportedEncodingException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
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
@RequestMapping("/productos")
public class productosControlador {
    
    private StringBuilder builder = null;
    private Producto p = null;
    
    @Autowired
    private ProductoDaoImplement productoImpl;
    
    @Autowired
    private EstatusProductoDaoImplement eproductoImpl;
    
    @RequestMapping(value = "productosRegistrados", method = RequestMethod.GET)
    @ResponseBody
    public String obtenerPtoductosRegistrados() throws UnsupportedEncodingException{
        return creaTablaProductosTodos();
    }
    
    @RequestMapping(value = "filtraProductosRegistrados/{idproducto}/{producto}/{estatusproducto}", method = RequestMethod.GET)
    @ResponseBody
    public String filtrarPtoductosRegistrados(@PathVariable (value = "idproducto") int idproducto,
                                              @PathVariable (value = "producto") String nproducto,
                                              @PathVariable (value = "estatusproducto") String estatusproducto) throws UnsupportedEncodingException{
        if(idproducto == 0 && nproducto.equals("todos") && estatusproducto.equals("todos")){
            return creaTablaProductosTodos();
        }else{
            return creaTablaProductosFiltrada(idproducto,nproducto,estatusproducto);
        }
    }
    
    @RequestMapping(value = "registrarProducto/{producto}/{precioInv}/{precioVent}/{fechaRegistro}/{estatus}", method = RequestMethod.POST)
    @ResponseBody
    public String registrarProducto(@PathVariable(value = "producto") String producto
            , @PathVariable(value = "precioInv") double precioInv
            , @PathVariable(value = "precioVent") double precioVent
            , @PathVariable(value = "estatus") String estatus
            , @PathVariable(value = "fechaRegistro") String fechaRegistro) throws UnsupportedEncodingException, ParseException{
        System.gc();
        fechaRegistro = new SimpleDateFormat("dd/MM/yyyy").format(new SimpleDateFormat("yyyy-MM-dd").parse(fechaRegistro));
        p = new Producto();
        builder = new StringBuilder();
        p.setNombre(producto);
        p.setPrecioInv(precioInv);
        p.setPrecioVent(precioVent);
        p.setFechaRegistro(fechaRegistro);
        p.setEstatusProductoidestatusProducto(eproductoImpl.buscarEstatusProductoPorNombre(estatus));
        if(productoImpl.agregarProducto(p)){
            builder.append("<script> alert(\"Producto agregado correctamente\"); </script>");            
            return new String(builder.toString().getBytes("UTF-8"),"ISO-8859-1");
        }else{
            builder.append("<script> alert(\"Producto no agregado debido a un error\"); </script>");            
            return new String(builder.toString().getBytes("UTF-8"),"ISO-8859-1");
        }
    }
    
    @RequestMapping(value = "modificarProducto/{idproducto}/{producto}/{precioInv}/{precioVent}/{fechaRegistro}/{estatus}", method = RequestMethod.POST)
    @ResponseBody
    public String modificarProducto(
              @PathVariable(value = "idproducto") int idproducto
            , @PathVariable(value = "producto") String producto
            , @PathVariable(value = "precioInv") double precioInv
            , @PathVariable(value = "precioVent") double precioVent
            , @PathVariable(value = "fechaRegistro") String fechaProducto
            , @PathVariable(value = "estatus") String estatus) throws UnsupportedEncodingException, ParseException{
        
        System.gc();
        fechaProducto = new SimpleDateFormat("dd/MM/yyyy").format(new SimpleDateFormat("yyyy-MM-dd").parse(fechaProducto));
        p = new Producto(idproducto,producto,precioInv,precioVent,fechaProducto);
        builder = new StringBuilder();
        p.setEstatusProductoidestatusProducto(eproductoImpl.buscarEstatusProductoPorNombre(estatus));
        if(productoImpl.modificarProducto(p)){
            builder.append("<script> alert(\"Producto modificado correctamente\"); </script>");
            return new String(builder.toString().getBytes("UTF-8"),"ISO-8859-1");
        }else{
            builder.append("<script> alert(\"Producto no modificado debido a un error\"); </script>");
            return new String(builder.toString().getBytes("UTF-8"),"ISO-8859-1");
        }
    }
    
    @RequestMapping(value = "eliminarProducto/{idproducto}", method = RequestMethod.POST)
    @ResponseBody
    public String eliminarProducto(@PathVariable(value = "idproducto") int idproducto) throws UnsupportedEncodingException{
        System.gc();
        p = productoImpl.buscarProductoPorId(idproducto);
        builder = new StringBuilder();
        if(productoImpl.eliminarProducto(p)){
            builder.append("<script> alert(\"Producto eliminado correctamente\"); </script>");            
            return new String(builder.toString().getBytes("UTF-8"),"ISO-8859-1");
        }else{
            builder.append("<script> alert(\"Producto no eliminado debido a un error\"); </script>");            
            return new String(builder.toString().getBytes("UTF-8"),"ISO-8859-1");
        }       
    }
    @RequestMapping(value = "obtenerFechaActual", method = RequestMethod.GET)
    @ResponseBody
    public String obtenerFechaActual(){
        String fechaActual = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
        return fechaActual;
    }
    
    private String creaTablaProductosTodos() throws UnsupportedEncodingException{
        System.gc();
        builder = new StringBuilder();
        productoImpl.consultarTodos().stream().map((producto) -> {
            builder.append("<tr>");
            return producto;
        }).map((producto) -> {
            builder.append("<td>").append(producto.getIdproducto()).append("</td>");
            return producto;
        }).map((producto) -> {
            builder.append("<td>").append(producto.getNombre()).append("</td>");
            return producto;
        }).map((producto) -> {
            builder.append("<td>$").append(producto.getPrecioInv()).append("</td>");
            return producto;
        }).map((producto) -> {
            builder.append("<td>$").append(producto.getPrecioVent()).append("</td>");
            return producto;
        }).map((producto) -> {
            builder.append("<td>").append(producto.getFechaRegistro()).append("</td>");
            return producto;
        }).map((producto) -> {
            builder.append("<td>").append(producto.getEstatusProductoidestatusProducto().getEstatusProducto()).append("</td>");
            return producto;
        }).map((producto) -> {
            try {
                builder.append("<td><button type=\"button\" class=\"btn btn-warning\" data-toggle=\"modal\" data-target=\"#modal-mod-producto\" data-idproducto=\"").append(producto.getIdproducto()).append("\" data-nombreproducto=\"").append(producto.getNombre()).append("\" data-precioInv=\"").append(producto.getPrecioInv()).append("\" data-precioVent=\"").append(producto.getPrecioVent()).append("\" data-estatusProducto=\"").append(producto.getEstatusProductoidestatusProducto().getEstatusProducto()).append("\" data-fechaProducto=\"").append(formatearFecha(producto.getFechaRegistro())).append("\" title=\"Modificar\"><i class=\"fas fa-edit\"></i></button>  <button type=\"button\" class=\"btn btn-danger\" data-toggle=\"modal\" data-target=\"#modal-elim-producto\" title=\"Eliminar\" data-idproducto=\"").append(producto.getIdproducto()).append("\"><i class=\"fas fa-trash\"></i></button> "
                        +"</td>");
            } catch (ParseException ex) {
                System.out.println("Error al formatear fecha..." + ex);
            }
            return producto;
        }).forEach((_item) -> {
            builder.append("</tr>");
        });
        return  new String(builder.toString().getBytes("UTF-8"),"ISO-8859-1");
    }
    
    private String creaTablaProductosFiltrada(int idproducto, String nproducto,String estatusproducto) throws UnsupportedEncodingException{
        System.gc();
        builder = new StringBuilder();
        productoImpl.consultarTodos().stream().filter((producto) -> (producto.getIdproducto() == idproducto) | producto.getNombre().equals(nproducto) | producto.getEstatusProductoidestatusProducto().getEstatusProducto().equals(estatusproducto)).map((producto) -> {
            builder.append("<tr>");
            return producto;
        }).map((producto) -> {
            builder.append("<td>").append(producto.getIdproducto()).append("</td>");
            return producto;
        }).map((producto) -> {
            builder.append("<td>").append(producto.getNombre()).append("</td>");
            return producto;
        }).map((producto) -> {
            builder.append("<td>$").append(producto.getPrecioInv()).append("</td>");
            return producto;
        }).map((producto) -> {
            builder.append("<td>$").append(producto.getPrecioVent()).append("</td>");
            return producto;
        }).map((producto) -> {
            builder.append("<td>").append(producto.getFechaRegistro()).append("</td>");
            return producto;
        }).map((producto) -> {
            builder.append("<td>").append(producto.getEstatusProductoidestatusProducto().getEstatusProducto()).append("</td>");
            return producto;
        }).map((producto) -> {
            try {
                builder.append("<td><button type=\"button\" class=\"btn btn-warning\" data-toggle=\"modal\" data-target=\"#modal-mod-producto\" data-idproducto=\"").append(producto.getIdproducto()).append("\" data-nombreproducto=\"").append(producto.getNombre()).append("\" data-precioInv=\"").append(producto.getPrecioInv()).append("\" data-precioVent=\"").append(producto.getPrecioVent()).append("\" data-estatusProducto=\"").append(producto.getEstatusProductoidestatusProducto().getEstatusProducto()).append("\" data-fechaProducto=\"").append(formatearFecha(producto.getFechaRegistro())).append("\" title=\"Modificar\"><i class=\"fas fa-edit\"></i></button>  <button type=\"button\" class=\"btn btn-danger\" data-toggle=\"modal\" data-target=\"#modal-elim-producto\" title=\"Eliminar\" data-idproducto=\"").append(producto.getIdproducto()).append("\"><i class=\"fas fa-trash\"></i></button> "
                        +"</td>");
            } catch (ParseException ex) {
                System.out.println("Error al formatear fecha..." + ex);
            }
            return producto;
        }).forEach((_item) -> {
            builder.append("</tr>");
        });
        return  new String(builder.toString().getBytes("UTF-8"),"ISO-8859-1");
    }
    
    private String formatearFecha(String fecha) throws ParseException{
        String fechaf = new SimpleDateFormat("yyyy-MM-dd").format(new SimpleDateFormat("dd/MM/yyyy").parse(fecha));
        return fechaf;
    }
    
    
}
