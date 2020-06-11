/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controlador;

import javax.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 *
 * Esta clase contiene el metodo de logout, invalida las varibales de la sesión y 
 * redirige a la pagina de inicio pero como ya no hay sesión activa redirigirá nuevamente
 * ahora a la pagina de inicio de sesión
 */
@Controller
@RequestMapping("/logout.htm")
public class logoutControlador {
    
    @RequestMapping(method = RequestMethod.POST)
    public String metodoPost(HttpSession sesion){
        sesion.removeAttribute("login");
        sesion.removeAttribute("pilaVenta");
        sesion.invalidate();
        return "inicio";
    }
    
}
