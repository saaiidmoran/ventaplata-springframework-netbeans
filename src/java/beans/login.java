/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package beans;

/**
 *
 * @author saaii
 */
public class login {
    private String usuario,clave;
    public login(){
        usuario = null;
        clave = null;
    }
    
    public login(String usuario,String clave){
        this.usuario=usuario;
        this.clave=clave;
    }    

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public String getClave() {
        return clave;
    }

    public void setClave(String clave) {
        this.clave = clave;
    }
    
}
