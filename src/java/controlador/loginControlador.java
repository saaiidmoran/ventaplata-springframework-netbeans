package controlador;

import beans.login;
import daoImplements.EstatusProductoDaoImplement;
import daoImplements.EstatusVentaDaoImplement;
import daoImplements.ModalidadVentaDaoImplement;
import daoImplements.UsuarioDaoImplement;
import entidades.Estatusproducto;
import entidades.Estatusventa;
import entidades.Modalidadventa;
import entidades.Usuario;
import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

/**
 *
 * Este codigo se encarga de validar el acceso al sistema
 * pero antes debe comprobar los registros previos que
 * se necesitan para que el sistema funcione correctamente
 * 
 */

@Controller
@RequestMapping("/login.htm")
public class loginControlador {
    
    //Se decraran las dependencias DAO
    
    @Autowired
    private UsuarioDaoImplement udi;
    
    @Autowired
    private EstatusVentaDaoImplement evdi;
    
    @Autowired
    private ModalidadVentaDaoImplement mvdi;
    
    @Autowired
    private EstatusProductoDaoImplement epdi;
    
    
    //Este método se encarga de validar al usuario, si el usuario es valido se redirecciona a la pantalla de inicio
    //si no se devuelve un mensaje de error
    @RequestMapping(method = RequestMethod.POST)
    public String metodoPost(@RequestParam("usuario") String nusuario,@RequestParam("clave") String clave, Model model, HttpSession sesion){
        registrosPrevios();
        login login;
        Usuario u;
        StringBuffer mensaje;
        mensaje = new StringBuffer();
        if(nusuario.equals("") || clave.equals("")){
            return "index";
        }
        u = udi.buscarUsuarioPorNombre(nusuario);
        if(u == null){
            mensaje.append("Error, no se encontró ese usuario");
            model.addAttribute("mensaje",mensaje);
            return "index";
        }else
        if(u.getClave().equals(clave)){
            login = new login(nusuario,clave);
            sesion.setAttribute("login", login);
            return "index";
        }else{
            mensaje.append("Error, contraseña incorrecta");
            model.addAttribute("mensaje",mensaje);
            return "index";
        }
    }
    
    private void registrosPrevios(){
        registrarUsuarioDefault();
        registrarModalidadesVentaDefault();
        registrarEstatusVentaDefault();
        registrarEstatusProductoDefault();        
    }
    
    private void registrarUsuarioDefault(){
        Usuario usr;     
        if(udi.buscarUsuarioPorNombre("localhost") == null){
            usr = new Usuario();
            usr.setUsrnombre("localhost");
            usr.setClave("12345");
            if(udi.agregarUsuario(usr)){
                System.out.println("Usuario por default del sistema creado correctamente");
            }else{
                System.out.println("Ocurrió un error al crear el usuario por default del sistema");
            }
        }else{
            System.out.println("El usuario por default del sistema ya fué creado anteriormente");
        }        
    }
    
    private void registrarModalidadesVentaDefault(){
        Modalidadventa[] mv = new Modalidadventa[2];
        
        if(mvdi.buscarModalidadVentaPorNombre("En pagos") == null){
            mv[0] = new Modalidadventa();
            mv[0].setModalidad("En pagos");
            if(mvdi.agregarModalidadVenta(mv[0])){
                System.out.println("Modalidad de venta \"En pagos\" creada correctamente");
            }else{
                System.out.println("Ocurrió un error al crear la modalidad de venta \"En pagos\"");
            }
        }else{
            System.out.println("La modalidad de venta \"En pagos\" ya fué creada anteriormente");
        }        
        if(mvdi.buscarModalidadVentaPorNombre("De contado") == null){
            mv[1] = new Modalidadventa();
            mv[1].setModalidad("De contado");
            if(mvdi.agregarModalidadVenta(mv[1])){
                System.out.println("Modalidad de venta \"De contado\" creada correctamente");
            }else{
                System.out.println("Ocurrió un error al crear la modalidad de venta \"De contado\"");
            }
        }else{
            System.out.println("La modalidad de venta \"De contado\" ya fué creada anteriormente");
        }
    }
    
    private void registrarEstatusVentaDefault(){
        Estatusventa[] ev = new Estatusventa[2];
        if(evdi.buscarEstatusVentaPorNombre("Concretada") == null){
            ev[0] = new Estatusventa();
            ev[0].setEstatusVenta("Concretada");
            if(evdi.agregarEstatusVenta(ev[0])){
                System.out.println("Estatus de venta \"Concretada\" creado correctamente");
            }else{
                System.out.println("Ocurrió un error al crear el estatus de venta \"Concretada\"");
            }
        }else{
            System.out.println("El estatus de venta \"Concretada\" ya fué creado anteriormente");
        }        
        if(evdi.buscarEstatusVentaPorNombre("Pendiente") == null){
            ev[1] = new Estatusventa();
            ev[1].setEstatusVenta("Pendiente");
            if(evdi.agregarEstatusVenta(ev[1])){
                System.out.println("Estatus de venta \"Pendiente\" creado correctamente");
            }else{
                System.out.println("Ocurrió un error al crear el estatus de venta \"Pendiente\"");
            }
        }else{
            System.out.println("El estatus de venta \"Pendiente\" ya fué creado anteriormente");
        }
    }
    
    private void registrarEstatusProductoDefault(){
        Estatusproducto[] ep = new Estatusproducto[2];        
        if(epdi.buscarEstatusProductoPorNombre("En venta") == null){
            ep[0] = new Estatusproducto();
            ep[0].setEstatusProducto("En venta");
            if(epdi.agregarEstatusProducto(ep[0])){
                System.out.println("Estatus de producto \"En venta\" creado correctamente");
            }else{
                System.out.println("Ocurrió un error al crear el estatus de producto \"En venta\"");
            }
        }else{
            System.out.println("El estatus de producto \"En venta\" ya fué creado anteriormente");
        }        
        if(epdi.buscarEstatusProductoPorNombre("Vendido") == null){
            ep[1] = new Estatusproducto();
            ep[1].setEstatusProducto("Vendido");
            if(epdi.agregarEstatusProducto(ep[1])){
                System.out.println("Estatus de producto \"Vendido\" creado correctamente");
            }else{
                System.out.println("Ocurrió un error al crear el estatus de producto \"Vendido\"");
            }
        }else{
            System.out.println("El estatus de producto \"Vendido\" ya fué creado anteriormente");
        }
    }
    
}
