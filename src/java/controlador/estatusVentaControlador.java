/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controlador;

import daoImplements.EstatusVentaDaoImplement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 *
 * @author saaii
 */
@Controller
@RequestMapping("/estatusVenta")
public class estatusVentaControlador {
    
    @Autowired
    private EstatusVentaDaoImplement evdi;
    
    
    
}
