/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package beans;

import entidades.Producto;
import java.util.Collection;

/**
 *
 * @author saaii
 */
public class beanPilaVenta {
    
    private Collection<Producto> pilaVenta;
    private double total;
    
    public beanPilaVenta(){
        
    }
    
    public beanPilaVenta(Collection<Producto> pilaVenta, double total){
        this.pilaVenta = pilaVenta;
        this.total = total;
    }
    
    public Collection<Producto> getPilaVenta() {
        return pilaVenta;
    }

    public void setPilaVenta(Collection<Producto> pilaVenta) {
        this.pilaVenta = pilaVenta;
    }     

    public double getTotal() {
        double bufferTotal = 0;
        bufferTotal = pilaVenta.stream().map((p) -> p.getPrecioVent()).reduce(bufferTotal, (accumulator, _item) -> accumulator + _item);
        this.total = bufferTotal;
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }
}
