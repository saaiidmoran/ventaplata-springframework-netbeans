/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entidades;

import java.io.Serializable;
import java.util.Collection;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author saaii
 */
@Entity
@Table(name = "producto")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Producto.findAll", query = "SELECT p FROM Producto p"),
    @NamedQuery(name = "Producto.findByIdproducto", query = "SELECT p FROM Producto p WHERE p.idproducto = :idproducto"),
    @NamedQuery(name = "Producto.findByNombre", query = "SELECT p FROM Producto p WHERE p.nombre = :nombre"),
    @NamedQuery(name = "Producto.findByPrecioInv", query = "SELECT p FROM Producto p WHERE p.precioInv = :precioInv"),
    @NamedQuery(name = "Producto.findByPrecioVent", query = "SELECT p FROM Producto p WHERE p.precioVent = :precioVent"),
    @NamedQuery(name = "Producto.findByFechaRegistro", query = "SELECT p FROM Producto p WHERE p.fechaRegistro = :fechaRegistro")})
public class Producto implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idproducto")
    private Integer idproducto;
    @Basic(optional = false)
    @Column(name = "nombre")
    private String nombre;
    @Basic(optional = false)
    @Column(name = "precioInv")
    private double precioInv;
    @Basic(optional = false)
    @Column(name = "precioVent")
    private double precioVent;
    @Basic(optional = false)
    @Column(name = "fechaRegistro")
    private String fechaRegistro;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "productoIdproducto")
    private Collection<Venta> ventaCollection;
    @JoinColumn(name = "estatusProducto_idestatusProducto", referencedColumnName = "idestatusProducto")
    @ManyToOne(optional = false)
    private Estatusproducto estatusProductoidestatusProducto;

    public Producto() {
    }

    public Producto(Integer idproducto) {
        this.idproducto = idproducto;
    }

    public Producto(Integer idproducto, String nombre, double precioInv, double precioVent, String fechaRegistro) {
        this.idproducto = idproducto;
        this.nombre = nombre;
        this.precioInv = precioInv;
        this.precioVent = precioVent;
        this.fechaRegistro = fechaRegistro;
    }

    public Integer getIdproducto() {
        return idproducto;
    }

    public void setIdproducto(Integer idproducto) {
        this.idproducto = idproducto;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public double getPrecioInv() {
        return precioInv;
    }

    public void setPrecioInv(double precioInv) {
        this.precioInv = precioInv;
    }

    public double getPrecioVent() {
        return precioVent;
    }

    public void setPrecioVent(double precioVent) {
        this.precioVent = precioVent;
    }

    public String getFechaRegistro() {
        return fechaRegistro;
    }

    public void setFechaRegistro(String fechaRegistro) {
        this.fechaRegistro = fechaRegistro;
    }

    @XmlTransient
    public Collection<Venta> getVentaCollection() {
        return ventaCollection;
    }

    public void setVentaCollection(Collection<Venta> ventaCollection) {
        this.ventaCollection = ventaCollection;
    }

    public Estatusproducto getEstatusProductoidestatusProducto() {
        return estatusProductoidestatusProducto;
    }

    public void setEstatusProductoidestatusProducto(Estatusproducto estatusProductoidestatusProducto) {
        this.estatusProductoidestatusProducto = estatusProductoidestatusProducto;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idproducto != null ? idproducto.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Producto)) {
            return false;
        }
        Producto other = (Producto) object;
        if ((this.idproducto == null && other.idproducto != null) || (this.idproducto != null && !this.idproducto.equals(other.idproducto))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entidades.Producto[ idproducto=" + idproducto + " ]";
    }
    
}
