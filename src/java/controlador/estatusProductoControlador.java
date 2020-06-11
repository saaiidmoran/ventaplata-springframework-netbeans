/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controlador;

import daoImplements.EstatusProductoDaoImplement;
import entidades.Estatusproducto;
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
@RequestMapping("/estatusProducto")
public class estatusProductoControlador {
    
    private StringBuilder builder = null;
    private Estatusproducto ep = null;
    
    @Autowired
    private EstatusProductoDaoImplement estatusProdcutoImpl;
    
    @RequestMapping(value = "estatusProdictoRegistrados", method = RequestMethod.GET)
    @ResponseBody
    public String consultarEstatusProducto() throws UnsupportedEncodingException{
        System.gc();
        builder = new StringBuilder();
        estatusProdcutoImpl.consultarTodos().stream().forEach((estatusproducto) -> {
            if(estatusproducto.getEstatusProducto().equals("En venta")){
                builder.append("<option selected>").append(estatusproducto.getEstatusProducto()).append("</option>");
            }else{
                builder.append("<option>").append(estatusproducto.getEstatusProducto()).append("</option>");
            }
        });
        return new String(builder.toString().getBytes("UTF-8"),"ISO-8859-1");
    }
    
    @RequestMapping(value = "registrarEstatusProdicto/{estatusProducto}", method = RequestMethod.POST)
    @ResponseBody
    public String registrarEstatusProducto(@PathVariable(value = "estatusProducto") String estatusProducto) throws UnsupportedEncodingException{
        System.gc();
        ep = new Estatusproducto();
        builder = new StringBuilder();
        ep.setEstatusProducto(estatusProducto);
        if(estatusProdcutoImpl.agregarEstatusProducto(ep)){
            builder.append("<script> alert(\"Estatus de producto agregado correctamente\"); </script>");
            return new String(builder.toString().getBytes("UTF-8"),"ISO-8859-1");
        }else{
            builder.append("<script> alert(\"Estatus de producto no agregado debido a un error\"); </script>");
            return new String(builder.toString().getBytes("UTF-8"),"ISO-8859-1");
        }
    }
    
    @RequestMapping(value = "modificarEstatusProducto/{idEstatusProducto}/{estatusProducto}", method = RequestMethod.POST)
    @ResponseBody
    public String modificarEstatusProducto(
             @PathVariable(value = "idEstatusProducto") int idEstatusProducto
            ,@PathVariable(value = "estatusProducto") String estatusProducto
    ) throws UnsupportedEncodingException{
        System.gc();
        ep = new Estatusproducto(idEstatusProducto, estatusProducto);
        builder = new StringBuilder();
        if(estatusProdcutoImpl.modificarEstatusProducto(ep)){
            builder.append("<script> alert(\"Estatus de producto modificado correctamente\"); </script>");
            return new String(builder.toString().getBytes("UTF-8"),"ISO-8859-1");
        }else{
            builder.append("<script> alert(\"Estatus de producto no modificado debido a un error\"); </script>");
            return new String(builder.toString().getBytes("UTF-8"),"ISO-8859-1");
        }
    }
    
    @RequestMapping(value = "eliminarEstatusProducto/{idEstatusProducto}", method = RequestMethod.POST)
    @ResponseBody
    public String eliminarEstatusProducto(@PathVariable(value = "idEstatusProducto") int idEstatusProducto) throws UnsupportedEncodingException{
        System.gc();
        ep = estatusProdcutoImpl.buscarEstatusProductoPorId(idEstatusProducto);
        if(estatusProdcutoImpl.eliminarEstatusProducto(ep)){
            builder.append("<script> alert(\"Estatus de producto eliminado correctamente\"); </script>");
            return new String(builder.toString().getBytes("UTF-8"),"ISO-8859-1");
        }else{
            builder.append("<script> alert(\"Estatus de producto no eliminado debido a un error\"); </script>");
            return new String(builder.toString().getBytes("UTF-8"),"ISO-8859-1");
        }
    }
}
