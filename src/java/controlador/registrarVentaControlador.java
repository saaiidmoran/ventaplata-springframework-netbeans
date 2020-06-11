/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controlador;

import beans.beanPilaVenta;
import beans.login;
import daoImplements.ClientesDaoImplement;
import daoImplements.DetalleVentaDaoImplement;
import daoImplements.EstatusProductoDaoImplement;
import daoImplements.EstatusVentaDaoImplement;
import daoImplements.ModalidadVentaDaoImplement;
import daoImplements.ProductoDaoImplement;
import daoImplements.UsuarioDaoImplement;
import daoImplements.VentaDaoImplement;
import entidades.Detalleventa;
import entidades.Producto;
import entidades.Usuario;
import entidades.Venta;
import java.io.UnsupportedEncodingException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
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
@RequestMapping("/realizarVenta")
public class registrarVentaControlador {
    
    @Autowired
    private EstatusProductoDaoImplement eproductoImpl;
    
    @Autowired
    private ProductoDaoImplement productoImpl;
    
    @Autowired
    private ModalidadVentaDaoImplement mvdi;
    
    @Autowired
    private ClientesDaoImplement cdi;
    
    @Autowired
    private  DetalleVentaDaoImplement dvdi;
    
    @Autowired
    private VentaDaoImplement vdi;  
    
    @Autowired
    private EstatusVentaDaoImplement evdi;
    
    @Autowired
    private UsuarioDaoImplement udi;
    
    @RequestMapping(value = "productosEnVenta", method = RequestMethod.GET)
    @ResponseBody
    public String obtenerProductosEnVenta(HttpSession sesion){
        return creaTablaProductosEnVentaTodos(sesion);
    }  
    
    @RequestMapping(value = "filtraProductosEnVenta/{idproducto}/{producto}", method = RequestMethod.GET)
    @ResponseBody
    public String filtraProductosEnVenta(HttpSession sesion,
                                         @PathVariable (value = "idproducto") int idproducto,
                                         @PathVariable (value = "producto") String nproducto){
        if(idproducto == 0 && nproducto.equals("todos")){
            return creaTablaProductosEnVentaTodos(sesion);
        }else{
            return creaTablaProductosEnVentaFiltrada(sesion,idproducto,nproducto);
        }
    }
    
    @RequestMapping(value = "obtenerPilaDeVenta", method = RequestMethod.GET)
    @ResponseBody
    public String obtenerPilaDeVenta(HttpSession sesion){
        System.gc();
        StringBuilder returnBuilder = new StringBuilder();
        beanPilaVenta beanPilaVenta = (beanPilaVenta)sesion.getAttribute("pilaVenta");
        double total = 0;
        if(beanPilaVenta != null){
            for(Producto productoPila : beanPilaVenta.getPilaVenta()){
                    returnBuilder.append("<tr>");
                    returnBuilder.append("<td>").append(productoPila.getIdproducto()).append("</td>");
                    returnBuilder.append("<td>").append(productoPila.getNombre()).append("</td>");
                    returnBuilder.append("<td>$").append(productoPila.getPrecioVent()).append("</td>");
                    total += productoPila.getPrecioVent();
                    returnBuilder.append("<td>$").append(total).append("</td>");
                    returnBuilder.append("<td><button type=\"button\" class=\"btn btn-danger\" data-toggle=\"modal\" data-target=\"#modal-elim-producto-pilav\" data-idproducto=\"").append(productoPila.getIdproducto()).append("\" title=\"Quitar de la pila de venta\"><i class=\"fas fa-minus-circle\"></i></button> "
                            +"</td>");
                    returnBuilder.append("</tr>");
            }
        }
        return parseStringBuilderToString(returnBuilder);
    }
        
    @RequestMapping(value = "agregarAPilaDeVenta/{idproducto}", method = RequestMethod.POST)
    @ResponseBody
    public String agregarAPilaDeVenta(HttpSession sesion, @PathVariable(value = "idproducto") int idProducto){
        System.gc();
        StringBuilder returnBuilder = new StringBuilder();
        beanPilaVenta beanPilaVenta = (beanPilaVenta)sesion.getAttribute("pilaVenta");
        if(beanPilaVenta == null){
            beanPilaVenta = new beanPilaVenta();
            beanPilaVenta.setPilaVenta(new ArrayList<>());
            sesion.setAttribute("pilaVenta", beanPilaVenta);
        }
        beanPilaVenta.getPilaVenta().add(productoImpl.buscarProductoPorId(idProducto));
        return parseStringBuilderToString(returnBuilder);
    }
    
    @RequestMapping(value = "quitarDePilaDeVenta/{idproducto}", method = RequestMethod.POST)
    @ResponseBody
    public String quitarDePilaDeVenta(HttpSession sesion, @PathVariable(value = "idproducto") int idProducto){
        System.gc();
        StringBuilder returnBuilder = new StringBuilder();
        beanPilaVenta beanPilaVenta = (beanPilaVenta)sesion.getAttribute("pilaVenta");
        beanPilaVenta.getPilaVenta().remove(productoImpl.buscarProductoPorId(idProducto));
        return parseStringBuilderToString(returnBuilder);
    }
    
    @RequestMapping(value = "cancelarVenta", method = RequestMethod.POST)
    @ResponseBody
    public String cancelarVenta(HttpSession sesion){
        System.gc();
        StringBuilder returnBuilder = new StringBuilder();
        sesion.removeAttribute("pilaVenta");        
        return parseStringBuilderToString(returnBuilder);
    }
    
    @RequestMapping(value = "obtenerModalidadesDeVenta", method = RequestMethod.GET)
    @ResponseBody
    public String obtenerModalidadesDeVenta(){
        System.gc();
        StringBuilder returnBuilder = new StringBuilder();        
        mvdi.consultarTodas().stream().forEach((mventa) -> {
            returnBuilder.append("<option value=\"").append(mventa.getModalidad()).append("\">");
        });
        return parseStringBuilderToString(returnBuilder);
    }
    
    @RequestMapping(value = "obtenerListaClientes", method = RequestMethod.GET)
    @ResponseBody
    public String obtenerListaClientes(){
        System.gc();
        StringBuilder returnBuilder = new StringBuilder();  
        cdi.consultarTodos().stream().forEach((c) -> {
            returnBuilder.append("<option value=\"").append(c.getNombre()).append("\">");
        });                    
        return parseStringBuilderToString(returnBuilder);
    }
    
    @RequestMapping(value = "registrarVenta/{nombreCliente}/{abono}/{modalidadVenta}/{fecha}", method = RequestMethod.POST)
    @ResponseBody
    public String registrarVenta(HttpSession sesion, 
                                @PathVariable(value = "nombreCliente") String nombreCliente,
                                @PathVariable(value = "abono") double abono,
                                @PathVariable(value = "modalidadVenta") String modalidadVenta,
                                @PathVariable(value = "fecha") String fecha){
        System.gc();
        StringBuilder returnBuilder = new StringBuilder(); 
        String estatusVenta;
        Venta v;        
        String productosNoRegistradosEnLaVenta = "";
        login login = (login)sesion.getAttribute("login");
        Usuario u = udi.buscarUsuarioPorNombre(login.getUsuario()); 
        Detalleventa dv = new Detalleventa();   
        beanPilaVenta beanPilaVenta = (beanPilaVenta)sesion.getAttribute("pilaVenta");
        
        if(beanPilaVenta == null){
            returnBuilder.append("<script> alert(\"La pila de venta está vacía\"); </script>");
            return parseStringBuilderToString(returnBuilder);
        }
        
        if(beanPilaVenta.getPilaVenta().isEmpty()){
            returnBuilder.append("<script> alert(\"La pila de venta está vacía\"); </script>");
            return parseStringBuilderToString(returnBuilder);
        }
        
        if(modalidadVenta.equals("En pagos")){
            estatusVenta = "Pendiente";
        }else{
            estatusVenta = "Concretada";
        } 
        
        dv.setClienteIdcliente(cdi.buscarClientePorNombre(nombreCliente));
        dv.setFecha(formatearFechaFormularioToBackend(fecha));
        dv.setAbono(abono);
        dv.setTotal(beanPilaVenta.getTotal());
        dv.setModalidadventaidmodalidadVenta(mvdi.buscarModalidadVentaPorNombre(modalidadVenta));
        dv.setEstatusventaidestatusVenta(evdi.buscarEstatusVentaPorNombre(estatusVenta));
        dv.setUsuarioIdusuario(u);
            
        
        if(!dvdi.registrarDetalleVenta(dv)){
            returnBuilder.append("<script> alert(\"Venta no registrada correctamente debido a un error\"); </script>");
            return parseStringBuilderToString(returnBuilder);
        } 
        dv = dvdi.buscarUltimoAgregado();           
        for(Producto p : beanPilaVenta.getPilaVenta()){
            v = new Venta();
            p = productoImpl.buscarProductoPorId(p.getIdproducto());
            v.setDetalleVentaidDetalleVenta(dv);
            v.setProductoIdproducto(p);            
            if(p.getEstatusProductoidestatusProducto().getEstatusProducto().equals("En venta") && vdi.agregarVenta(v)){
                p.setEstatusProductoidestatusProducto(eproductoImpl.buscarEstatusProductoPorNombre("Vendido"));
                productoImpl.modificarProducto(p);
            }else{
                productosNoRegistradosEnLaVenta += p.getNombre() + " ";
            }           
        }
        sesion.removeAttribute("pilaVenta");
        if(productosNoRegistradosEnLaVenta.equals("")){
            returnBuilder.append("<script> alert(\"Venta registrada correctamente\"); </script>");
            return parseStringBuilderToString(returnBuilder);
        }else{
            returnBuilder.append("<script> alert(\"Venta registrada correctamente, pero los productos: ").append(productosNoRegistradosEnLaVenta).append("no se pudieron agregar a la venta\"); </script>");
            return parseStringBuilderToString(returnBuilder);
        }        
    }

    @RequestMapping(value = "obtenerTotalVenta", method = RequestMethod.GET)
    @ResponseBody
    public String obtenerTotal(HttpSession sesion){
        System.gc();
        StringBuilder returnBuilder = new StringBuilder();
        beanPilaVenta beanPilaVenta = (beanPilaVenta)sesion.getAttribute("pilaVenta");
        if(beanPilaVenta != null){
            returnBuilder.append(beanPilaVenta.getTotal());
        }else{
            returnBuilder.append("0.0");
        }
        return parseStringBuilderToString(returnBuilder);
    }
    
    private String creaTablaProductosEnVentaTodos(HttpSession sesion){
        System.gc();
        StringBuilder returnBuilder = new StringBuilder("");
        beanPilaVenta beanPilaVenta = (beanPilaVenta)sesion.getAttribute("pilaVenta");
        for(Producto producto : eproductoImpl.buscarEstatusProductoPorNombre("En venta").getProductoCollection()){
            if(!(beanPilaVenta != null && beanPilaVenta.getPilaVenta().contains(producto))){
                returnBuilder.append("<tr>");
                returnBuilder.append("<td>").append(producto.getIdproducto()).append("</td>");
                returnBuilder.append("<td>").append(producto.getNombre()).append("</td>");
                returnBuilder.append("<td>$").append(producto.getPrecioVent()).append("</td>");
                returnBuilder.append("<td><button type=\"button\" class=\"btn btn-success\" data-toggle=\"modal\" data-target=\"#modal-add-producto-pilav\" data-idproducto=\"").append(producto.getIdproducto()).append("\" title=\"Agregar a la pila de venta\"><i class=\"fas fa-cart-arrow-down\"></i></button></td>");                
                returnBuilder.append("</tr>");
            }                    
        }
        return parseStringBuilderToString(returnBuilder);
    }
    
    private String creaTablaProductosEnVentaFiltrada(HttpSession sesion, int idproducto, String nproducto){
        System.gc();
        StringBuilder returnBuilder = new StringBuilder("");
        beanPilaVenta beanPilaVenta = (beanPilaVenta)sesion.getAttribute("pilaVenta");
        for(Producto producto : eproductoImpl.buscarEstatusProductoPorNombre("En venta").getProductoCollection()){
            if(!(beanPilaVenta != null && beanPilaVenta.getPilaVenta().contains(producto))){
                if(producto.getIdproducto() == idproducto | producto.getNombre().equals(nproducto)){
                    returnBuilder.append("<tr>");
                    returnBuilder.append("<td>").append(producto.getIdproducto()).append("</td>");
                    returnBuilder.append("<td>").append(producto.getNombre()).append("</td>");
                    returnBuilder.append("<td>$").append(producto.getPrecioVent()).append("</td>");
                    returnBuilder.append("<td><button type=\"button\" class=\"btn btn-success\" data-toggle=\"modal\" data-target=\"#modal-add-producto-pilav\" data-idproducto=\"").append(producto.getIdproducto()).append("\" title=\"Agregar a la pila de venta\"><i class=\"fas fa-cart-arrow-down\"></i></button></td>");                
                    returnBuilder.append("</tr>");
                }                
            }                    
        }
        return parseStringBuilderToString(returnBuilder);
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
