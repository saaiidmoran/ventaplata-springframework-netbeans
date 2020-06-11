/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controlador;

import daoImplements.ClientesDaoImplement;
import daoImplements.DetalleVentaDaoImplement;
import daoImplements.EstatusProductoDaoImplement;
import daoImplements.EstatusVentaDaoImplement;
import daoImplements.ModalidadVentaDaoImplement;
import daoImplements.ProductoDaoImplement;
import daoImplements.VentaDaoImplement;
import entidades.Detalleventa;
import entidades.Producto;
import entidades.Venta;
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
@RequestMapping("/reporteVentas")
public class reporteVentasControlador {
    
    @Autowired
    EstatusVentaDaoImplement evdi;
    
    @Autowired
    DetalleVentaDaoImplement dvdi;
    
    @Autowired
    ProductoDaoImplement pdi;
    
    @Autowired
    VentaDaoImplement vdi;
    
    @Autowired
    ClientesDaoImplement cdi;
    
    @Autowired
    ModalidadVentaDaoImplement mvdi;
    
    @Autowired
    EstatusProductoDaoImplement epdi;
    
    @RequestMapping(value = "obtenerEstatusVenta", method = RequestMethod.GET)
    @ResponseBody    
    public String obtenerEstatusVenta(){
        StringBuilder retornoTablaEstatusVenta = new StringBuilder();
        evdi.consultarTodos().stream().forEach((ev) -> {
            retornoTablaEstatusVenta.append("<option value=\"").append(ev.getEstatusVenta()).append("\">");
        });
        return parseStringBuilderToString(retornoTablaEstatusVenta);
    }
    
    @RequestMapping(value = "obtenerVentasRegistradas", method = RequestMethod.GET)
    @ResponseBody
    public String obtenerVentasRegistradas(){
        return generarTablaVentasRegistradasTodas();
    }
    
    @RequestMapping(value = "obtenerVentasRegistradasFiltradas/{iddv}/{fecha}/{fechafin}/{cliente}/{modalidad}/{estatus}", method = RequestMethod.GET)
    @ResponseBody
    public String obtenerVentasRegistradasFiltro(@PathVariable(value = "iddv") int iddv,
                                                 @PathVariable(value = "fecha") String fecha,
                                                 @PathVariable(value = "fechafin") String fechafin,
                                                 @PathVariable(value = "cliente") String cliente,
                                                 @PathVariable(value = "modalidad") String modalidad,
                                                 @PathVariable(value = "estatus") String estatus){
        if(iddv == 0 && fecha.equals("todos") && fechafin.equals("todos") && cliente.equals("todos") && modalidad.equals("todos") && estatus.equals("todos")){
            return generarTablaVentasRegistradasTodas();
        }else{
            if(!"todos".equals(fecha)){
                fecha = formatearFechaFormularioToBackend(fecha);
            }
            if(!"todos".equals(fechafin)){
                fechafin = formatearFechaFormularioToBackend(fechafin);
            }
            return generarTablaVentasRegistradasFiltrada(iddv,fecha,fechafin,cliente,modalidad,estatus);            
        }        
    }
    
    @RequestMapping(value = "obtenerProductosVenta/{iddetalleventa}", method = RequestMethod.GET)
    @ResponseBody
    public String obtenerProductosVenta(@PathVariable(value = "iddetalleventa") int iddv){
        StringBuilder retornoTablaProductosVenta = new StringBuilder();
        Detalleventa dv = dvdi.buscarDetalleVentaPorId(iddv);
        for(Venta v : dv.getVentaCollection()){
            retornoTablaProductosVenta.append("<tr>");
            retornoTablaProductosVenta.append("<td>").append(v.getProductoIdproducto().getIdproducto()).append("</td>");
            retornoTablaProductosVenta.append("<td>").append(v.getProductoIdproducto().getNombre()).append("</td>");
            retornoTablaProductosVenta.append("<td>").append(v.getProductoIdproducto().getPrecioVent()).append("</td>");
            retornoTablaProductosVenta.append("<td><button type=\"button\" class=\"btn btn-danger\" data-toggle=\"modal\" data-target=\"#modal-eliminar-producto-venta\" title=\"Eliminar de la venta\" data-idventa=\"").append(v.getIdventa()).append("\" data-iddv=\"").append(dv.getIddetalleVenta()).append("\"><i class=\"fas fa-trash\"></i></button></td>");
            retornoTablaProductosVenta.append("</tr>");
        }
        return parseStringBuilderToString(retornoTablaProductosVenta);
    }
    
    @RequestMapping(value = "obtenerTotales/{fecha1}/{fecha2}", method = RequestMethod.GET)
    @ResponseBody
    public String obtenerTotales(@PathVariable(value = "fecha1") String fe1,@PathVariable(value = "fecha2") String fe2){
        if(fe1.equals("todos") || fe2.equals("todos")){
            return generarTablaTotalTodo();            
        }else{            
            return generarTablaTotalFiltrado(formatearFechaFormularioToBackend(fe1),formatearFechaFormularioToBackend(fe2));
        }
    }
    
    @RequestMapping(value = "abonarAVenta/{iddvabonar}/{abono}", method = RequestMethod.POST)
    @ResponseBody
    public String abonarAVenta(@PathVariable(value = "iddvabonar") int iddv, @PathVariable(value = "abono") double abono){
        //Declaro variables a usar
        StringBuilder retornoMsjAbonado; // String builder que almacenará el contenido html
        Detalleventa d;
        double total,abonado;
        
        retornoMsjAbonado = new StringBuilder();
        d = dvdi.buscarDetalleVentaPorId(iddv);
        total = d.getTotal();
        abonado = d.getAbono();
        abonado += abono;
        if(abonado <= total){
            d.setAbono(abonado);
        
            //Si abono es igual a total a la venta le pongo estatus de concretada
            if(abonado == total){
                d.setEstatusventaidestatusVenta(evdi.buscarEstatusVentaPorNombre("Concretada"));
            }       

            //Agrego el mensaje correspondiente si se modifica en la base de datos o no
            if(dvdi.modificarDetalleVenta(d)){
                retornoMsjAbonado.append("<script> alert(\"Abono realizado correctamente\"); </script>");
            }else{
                retornoMsjAbonado.append("<script> alert(\"Abono no realizado correctamente debido a un error interno\"); </script>");
            }
        }else{
            retornoMsjAbonado.append("<script> alert(\"Abono no realizado correctamente debido a que la cantidad a abonar supera a la cantidad faltante para liquidar la venta\"); </script>");
        }        
        return parseStringBuilderToString(retornoMsjAbonado);
    }
    
    @RequestMapping(value = "agregarProductoAVenta/{idproducto}/{iddetalleventa}", method = RequestMethod.POST)
    @ResponseBody
    public String agregarProductoALaVenta(@PathVariable(value = "idproducto") int idproducto, @PathVariable(value = "iddetalleventa") int iddetalleventa){
        StringBuilder msjProductoAgregado;
        Venta v;
        Detalleventa dv;
        Producto pav;
        double totalDetalleventa;
        
        v = new Venta();
        v.setProductoIdproducto(pdi.buscarProductoPorId(idproducto));
        v.setDetalleVentaidDetalleVenta(dvdi.buscarDetalleVentaPorId(iddetalleventa));
        totalDetalleventa = 0;
        pav = pdi.buscarProductoPorId(idproducto);
        msjProductoAgregado = new StringBuilder();
        pav.setEstatusProductoidestatusProducto(epdi.buscarEstatusProductoPorNombre("Vendido"));
        if(vdi.agregarVenta(v)){
            if(!pdi.modificarProducto(pav)){
                msjProductoAgregado.append("<script> alert(\"Error al cambiar el estatus del producto\"); </script>");
            }
            dv = dvdi.buscarDetalleVentaPorId(iddetalleventa);
            for(Venta ve : dv.getVentaCollection()){
                totalDetalleventa += ve.getProductoIdproducto().getPrecioVent();
            }
            dv.setTotal(totalDetalleventa);
            dv.setEstatusventaidestatusVenta(evdi.buscarEstatusVentaPorNombre("Pendiente"));
            if(dvdi.modificarDetalleVenta(dv)){
                msjProductoAgregado.append("<script> alert(\"Producto agregado a la venta correctamente\"); </script>");
            }else{
                msjProductoAgregado.append("<script> alert(\"Producto agregado a la venta correctamente pero se produjo un error interno al modificar el monto total de la venta y su estatus\"); </script>");
            }            
        }else{
            msjProductoAgregado.append("<script> alert(\"Producto no agregado a la venta correctamente debido a un error interno\"); </script>");
        }        
        return parseStringBuilderToString(msjProductoAgregado);
    }
    
    @RequestMapping(value = "eliminarProductoDeVenta/{idventa}/{iddetalleventa}", method = RequestMethod.POST)
    @ResponseBody
    public String eliminarProductoDeVenta(@PathVariable(value = "idventa") int idventa, @PathVariable(value = "iddetalleventa") int iddetalleventa) {
        StringBuilder msjProductoEliminado;
        Venta vpe;
        Producto pe;
        Detalleventa dvpe;
        double totalRecalcular, totalInicial;
        
        vpe = vdi.buscarVentaPorId(idventa);
        pe = vpe.getProductoIdproducto();
        msjProductoEliminado = new StringBuilder();
        
        if(vdi.eliminarVenta(vpe)){
            dvpe = dvdi.buscarDetalleVentaPorId(iddetalleventa);
            totalRecalcular = 0;
            totalInicial = dvpe.getTotal();
            for(Venta ve : dvpe.getVentaCollection()){
                totalRecalcular += ve.getProductoIdproducto().getPrecioVent();
            }
            dvpe.setTotal(totalRecalcular);
            if(dvpe.getAbono() >= dvpe.getTotal()){
                dvpe.setAbono(dvpe.getTotal());
                dvpe.setEstatusventaidestatusVenta(evdi.buscarEstatusVentaPorNombre("Concretada"));
                msjProductoEliminado.append("<script> alert(\"El abono de la venta supera el monto total de la venta, devuelva al cliente su cambio: $").append((totalInicial-totalRecalcular)).append("\"); </script>");
            }
            if(dvdi.modificarDetalleVenta(dvpe)){
                msjProductoEliminado.append("<script> alert(\"Producto eliminado de la venta correctamente\"); </script>");
            }else{
                msjProductoEliminado.append("<script> alert(\"Producto eliminado de la venta correctamente pero se produjo un error al modificar el monto total de la venta\"); </script>");
            }
            pe.setEstatusProductoidestatusProducto(epdi.buscarEstatusProductoPorNombre("En venta"));
            if(!pdi.modificarProducto(pe)){
                msjProductoEliminado.append("<script> alert(\"Error al cambiar el estatus del producto\"); </script>");
            }
        }else{
            msjProductoEliminado.append("<script> alert(\"Producto no eliminado de la venta debido a un error interno\"); </script>");
        }
        return parseStringBuilderToString(msjProductoEliminado);
        
    }
    
    @RequestMapping(value = "modificarVenta/{iddv}/{fecha}/{total}/{abono}/{cliente}/{modalidad}/{estatus}", method = RequestMethod.POST)
    @ResponseBody
    public String modificarVenta(@PathVariable(value = "iddv") int iddv,
                                 @PathVariable(value = "fecha") String fecha, 
                                 @PathVariable(value = "total") double total, 
                                 @PathVariable(value = "abono") double abono, 
                                 @PathVariable(value = "cliente") String cliente, 
                                 @PathVariable(value = "modalidad") String modalidad, 
                                 @PathVariable(value = "estatus") String estatus){
        StringBuilder msjModificarVenta = new StringBuilder();
        Detalleventa dvm;
        
        dvm = dvdi.buscarDetalleVentaPorId(iddv);
        dvm.setFecha(formatearFechaFormularioToBackend(fecha));
        dvm.setTotal(total);
        dvm.setAbono(abono);
        dvm.setClienteIdcliente(cdi.buscarClientePorNombre(cliente));
        dvm.setModalidadventaidmodalidadVenta(mvdi.buscarModalidadVentaPorNombre(modalidad));
        dvm.setEstatusventaidestatusVenta(evdi.buscarEstatusVentaPorNombre(estatus));
        if(dvdi.modificarDetalleVenta(dvm)){
            msjModificarVenta.append("<script> alert(\"Venta modificada correctamente\"); </script>");
        }else{
            msjModificarVenta.append("<script> alert(\"Venta no modificada correctamente debido a un error interno\"); </script>");
        }
        return parseStringBuilderToString(msjModificarVenta);
    }
    
    @RequestMapping(value = "eliminarVenta/{iddv}", method = RequestMethod.POST)
    @ResponseBody
        public String eliminarVenta(@PathVariable(value = "iddv") int iddv){
        
        StringBuilder msjElminarVenta;
        Detalleventa dvev;
        Producto p;
        
        msjElminarVenta = new StringBuilder();
        dvev = dvdi.buscarDetalleVentaPorId(iddv);
        for(Venta v : dvev.getVentaCollection()){
            p = v.getProductoIdproducto();
            p.setEstatusProductoidestatusProducto(epdi.buscarEstatusProductoPorNombre("En venta"));
            if(vdi.eliminarVenta(v)){
                if(!pdi.modificarProducto(p)){
                    msjElminarVenta.append("<script> alert(\"Ocurrió un error interno al modificar el estatus del producto: ").append(p.getNombre()).append("\"); </script>");
                }
            }else{
                msjElminarVenta.append("<script> alert(\"Ocurrió un error interno al eliminar el producto: ").append(p.getNombre()).append(" de la venta\"); </script>");
            }                   
        }
        if(dvdi.eliminarDetalleVenta(dvev)){
            msjElminarVenta.append("<script> alert(\"Venta eliminada correctamente\"); </script>");
        }else{
            msjElminarVenta.append("<script> alert(\"Venta no eliminada correctamente debido a un error interno\"); </script>");
        }
        return parseStringBuilderToString(msjElminarVenta);
    }
    
    private String generarTablaVentasRegistradasTodas(){
        System.gc();
        StringBuilder retornoTablaVentasRegistradas = new StringBuilder();        
        dvdi.buscarTodos().stream().forEach((dv) -> {
            retornoTablaVentasRegistradas.append("<tr>");
            retornoTablaVentasRegistradas.append("<td>").append(dv.getIddetalleVenta()).append("</td>");
            retornoTablaVentasRegistradas.append("<td>").append(dv.getFecha()).append("</td>");
            retornoTablaVentasRegistradas.append("<td>$").append(dv.getTotal()).append("</td>");
            retornoTablaVentasRegistradas.append("<td>$").append(dv.getAbono()).append("</td>");
            retornoTablaVentasRegistradas.append("<td>").append(dv.getClienteIdcliente().getNombre()).append("</td>");
            retornoTablaVentasRegistradas.append("<td>").append(dv.getModalidadventaidmodalidadVenta().getModalidad()).append("</td>");
            retornoTablaVentasRegistradas.append("<td>").append(dv.getEstatusventaidestatusVenta().getEstatusVenta()).append("</td>");
            retornoTablaVentasRegistradas.append("<td>").append(dv.getUsuarioIdusuario().getUsrnombre()).append("</td>");
            if(!dv.getEstatusventaidestatusVenta().getEstatusVenta().equals("Concretada")){
                retornoTablaVentasRegistradas.append("<td><button type=\"button\" class=\"btn btn-success\" data-toggle=\"modal\" data-target=\"#modal-abonar-venta\" title=\"Abonar\" data-iddetalleventa=\"").append(dv.getIddetalleVenta()).append("\" data-totaldetalleventa=\"").append(dv.getTotal()).append("\" data-abonodetalleventa=\"").append(dv.getAbono()).append("\"><i class=\"fas fa-hand-holding-usd\"></i></button></td>");
            }else{
                retornoTablaVentasRegistradas.append("<td></td>");
            }
            retornoTablaVentasRegistradas.append("<td><button type=\"button\" class=\"btn btn-info\" data-toggle=\"modal\" data-target=\"#modal-productos-venta\" title=\"Productos\" data-iddetalleventa=\"").append(dv.getIddetalleVenta()).append("\"><i class=\"fas fa-cart-arrow-down\"></i></button></td>");            
            retornoTablaVentasRegistradas.append("<td><button type=\"button\" class=\"btn btn-warning\" data-toggle=\"modal\" data-target=\"#modal-modificar-venta\" title=\"Modificar\" data-iddetalleventa=\"").append(dv.getIddetalleVenta()).append("\" data-fechaventa=\"").append(formatearFechaBackendToFormulario(dv.getFecha())).append("\" data-totalventa=\"").append(dv.getTotal()).append("\" data-abonoventa=\"").append(dv.getAbono()).append("\" data-clienteventa=\"").append(dv.getClienteIdcliente().getNombre()).append("\" data-modalidadventa=\"").append(dv.getModalidadventaidmodalidadVenta().getModalidad()).append("\" data-estatus=\"").append(dv.getEstatusventaidestatusVenta().getEstatusVenta()).append("\"><i class=\"fas fa-edit\"></i></button></td>");
            retornoTablaVentasRegistradas.append("<td><button type=\"button\" class=\"btn btn-danger\" data-toggle=\"modal\" data-target=\"#modal-eliminar-venta\" data-iddv=\"").append(dv.getIddetalleVenta()).append("\" title=\"Eliminar\"><i class=\"fas fa-trash\"></i></button></td>");
            retornoTablaVentasRegistradas.append("</tr>"); 
        });
        return parseStringBuilderToString(retornoTablaVentasRegistradas);
    }
    
    private String generarTablaVentasRegistradasFiltrada(int iddv, String fecha, String fechafin, String cliente, String modalidad, String estatus){
        System.gc();
        StringBuilder retornoTablaVentasRegistradasFiltrada = new StringBuilder();
        dvdi.buscarTodos().stream().filter(dv -> (dv.getIddetalleVenta()==iddv) || validadorFechas(fecha,fechafin,dv.getFecha()) || dv.getClienteIdcliente().getNombre().equals(cliente) || dv.getModalidadventaidmodalidadVenta().getModalidad().equals(modalidad) || dv.getEstatusventaidestatusVenta().getEstatusVenta().equals(estatus)).forEach((dv) -> {
            retornoTablaVentasRegistradasFiltrada.append("<tr>");
            retornoTablaVentasRegistradasFiltrada.append("<td>").append(dv.getIddetalleVenta()).append("</td>");
            retornoTablaVentasRegistradasFiltrada.append("<td>").append(dv.getFecha()).append("</td>");
            retornoTablaVentasRegistradasFiltrada.append("<td>$").append(dv.getTotal()).append("</td>");
            retornoTablaVentasRegistradasFiltrada.append("<td>$").append(dv.getAbono()).append("</td>");
            retornoTablaVentasRegistradasFiltrada.append("<td>").append(dv.getClienteIdcliente().getNombre()).append("</td>");
            retornoTablaVentasRegistradasFiltrada.append("<td>").append(dv.getModalidadventaidmodalidadVenta().getModalidad()).append("</td>");
            retornoTablaVentasRegistradasFiltrada.append("<td>").append(dv.getEstatusventaidestatusVenta().getEstatusVenta()).append("</td>");
            retornoTablaVentasRegistradasFiltrada.append("<td>").append(dv.getUsuarioIdusuario().getUsrnombre()).append("</td>");
            if(!dv.getEstatusventaidestatusVenta().getEstatusVenta().equals("Concretada")){
                retornoTablaVentasRegistradasFiltrada.append("<td><button type=\"button\" class=\"btn btn-success\" data-toggle=\"modal\" data-target=\"#modal-abonar-venta\" title=\"Abonar\" data-iddetalleventa=\"").append(dv.getIddetalleVenta()).append("\" data-totaldetalleventa=\"").append(dv.getTotal()).append("\" data-abonodetalleventa=\"").append(dv.getAbono()).append("\"><i class=\"fas fa-hand-holding-usd\"></i></button></td>");
            }else{
                retornoTablaVentasRegistradasFiltrada.append("<td></td>");
            }
            retornoTablaVentasRegistradasFiltrada.append("<td><button type=\"button\" class=\"btn btn-info\" data-toggle=\"modal\" data-target=\"#modal-productos-venta\" title=\"Productos\" data-iddetalleventa=\"").append(dv.getIddetalleVenta()).append("\"><i class=\"fas fa-cart-arrow-down\"></i></button></td>");          
            retornoTablaVentasRegistradasFiltrada.append("<td><button type=\"button\" class=\"btn btn-warning\" data-toggle=\"modal\" data-target=\"#modal-modificar-venta\" title=\"Modificar\" data-iddetalleventa=\"").append(dv.getIddetalleVenta()).append("\" data-fechaventa=\"").append(formatearFechaBackendToFormulario(dv.getFecha())).append("\" data-totalventa=\"").append(dv.getTotal()).append("\" data-abonoventa=\"").append(dv.getAbono()).append("\" data-clienteventa=\"").append(dv.getClienteIdcliente().getNombre()).append("\" data-modalidadventa=\"").append(dv.getModalidadventaidmodalidadVenta().getModalidad()).append("\" data-estatus=\"").append(dv.getEstatusventaidestatusVenta().getEstatusVenta()).append("\"><i class=\"fas fa-edit\"></i></button></td>");
            retornoTablaVentasRegistradasFiltrada.append("<td><button type=\"button\" class=\"btn btn-danger\" title=\"Eliminar\" data-toggle=\"modal\" data-target=\"#modal-eliminar-venta\" data-iddv=\"").append(dv.getIddetalleVenta()).append("\"><i class=\"fas fa-trash\"></i></button></td>");
            retornoTablaVentasRegistradasFiltrada.append("</tr>"); 
        });
        return parseStringBuilderToString(retornoTablaVentasRegistradasFiltrada);
    }    
    
    private String generarTablaTotalTodo(){
        double totalInversion = 0;
        double totalVentas = 0;
        StringBuilder retornoTotalTodo = new StringBuilder();
        totalVentas = dvdi.buscarTodos().stream().map((dv) -> dv.getAbono()).reduce(totalVentas, (accumulator, _item) -> accumulator + _item);
        totalInversion = pdi.consultarTodos().stream().map((p) -> p.getPrecioInv()).reduce(totalInversion, (accumulator, _item) -> accumulator + _item);
        retornoTotalTodo.append("<tr>");
        retornoTotalTodo.append("<td>$").append(totalInversion).append("</td>");
        retornoTotalTodo.append("<td>$").append(totalVentas).append("</td>");
        retornoTotalTodo.append("<td>$").append(totalVentas - totalInversion).append("</td>");
        retornoTotalTodo.append("</tr>");
        return parseStringBuilderToString(retornoTotalTodo);
    }
    
    private String generarTablaTotalFiltrado(String f1, String f2){
        double totalInversion = 0;
        double totalVentas = 0;
        StringBuilder retornoTotalFiltrado = new StringBuilder();
        totalVentas = dvdi.buscarTodos().stream().filter((dv) -> (validadorFechas(f1, f2, dv.getFecha()))).map((dv) -> dv.getAbono()).reduce(totalVentas, (accumulator, _item) -> accumulator + _item);
        totalInversion = pdi.consultarTodos().stream().filter((p) -> {
            return validadorFechas(f1, f2, p.getFechaRegistro());
        }).map((p) -> p.getPrecioInv()).reduce(totalInversion, (accumulator, _item) -> accumulator + _item);
        retornoTotalFiltrado.append("<tr>");
        retornoTotalFiltrado.append("<td>$").append(totalInversion).append("</td>");
        retornoTotalFiltrado.append("<td>$").append(totalVentas).append("</td>");
        retornoTotalFiltrado.append("<td>$").append(totalVentas - totalInversion).append("</td>");
        retornoTotalFiltrado.append("</tr>");
            return parseStringBuilderToString(retornoTotalFiltrado);
    }
    
    private boolean validadorFechas(String fecha1, String fecha2, String fecha3){
        Date f1,f2,f3;
        try {
            f1 = new SimpleDateFormat("dd/MM/yyyy").parse(fecha1);
            f2 = new SimpleDateFormat("dd/MM/yyyy").parse(fecha2);
            f3 = new SimpleDateFormat("dd/MM/yyyy").parse(fecha3);
            return f3.after(f1) && f3.before(f2);
        } catch (ParseException ex) {
            System.out.println("Ocurrió un error validando las fechas: " + ex);
            return false;
        }
        
    }
    private String formatearFechaBackendToFormulario(String fecha){
        try {
            return new SimpleDateFormat("yyyy-MM-dd").format(new SimpleDateFormat("dd/MM/yyyy").parse(fecha));
        } catch (ParseException ex) {
            System.out.println("Ocurrió un error formateando la fecha: " + ex);
            return "";
        }
    }
    
    private String formatearFechaFormularioToBackend(String fecha){
        try{
            return new SimpleDateFormat("dd/MM/yyyy").format(new SimpleDateFormat("yyyy-MM-dd").parse(fecha));
        }catch(ParseException ex){
            System.out.println("Ocurrió un error formateando la fecha: " + ex);
            return "";
        }
    }
    
    private String parseStringBuilderToString(StringBuilder sb){
        try{
            return new String(sb.toString().getBytes("UTF-8"),"ISO-8859-1");
        }catch(UnsupportedEncodingException ex){
            System.out.println("Ocurrió un error mientras se convertía el StrinngBuilder a String" + ex);
            return "";
        }
    } 
    
}