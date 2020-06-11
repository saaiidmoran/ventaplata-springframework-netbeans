/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package daoImplements;

/**
 *
 * @author saaii
 */
public class ServicioException extends Exception{
    public ServicioException(String message){
        super (message);
    }
    public ServicioException(String message,Throwable cause){
        super(message,cause);
    }
}
