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
@Table(name = "detalleventa")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Detalleventa.findAll", query = "SELECT d FROM Detalleventa d"),
    @NamedQuery(name = "Detalleventa.findByIddetalleVenta", query = "SELECT d FROM Detalleventa d WHERE d.iddetalleVenta = :iddetalleVenta"),
    @NamedQuery(name = "Detalleventa.findByFecha", query = "SELECT d FROM Detalleventa d WHERE d.fecha = :fecha"),
    @NamedQuery(name = "Detalleventa.findByTotal", query = "SELECT d FROM Detalleventa d WHERE d.total = :total"),
    @NamedQuery(name = "Detalleventa.findByAbono", query = "SELECT d FROM Detalleventa d WHERE d.abono = :abono")})
public class Detalleventa implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "iddetalleVenta")
    private Integer iddetalleVenta;
    @Basic(optional = false)
    @Column(name = "fecha")
    private String fecha;
    @Basic(optional = false)
    @Column(name = "total")
    private double total;
    @Basic(optional = false)
    @Column(name = "abono")
    private double abono;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "detalleVentaidDetalleVenta")
    private Collection<Venta> ventaCollection;
    @JoinColumn(name = "cliente_idcliente", referencedColumnName = "idcliente")
    @ManyToOne(optional = false)
    private Cliente clienteIdcliente;
    @JoinColumn(name = "estatusventa_idestatusVenta", referencedColumnName = "idestatusVenta")
    @ManyToOne(optional = false)
    private Estatusventa estatusventaidestatusVenta;
    @JoinColumn(name = "modalidadventa_idmodalidadVenta", referencedColumnName = "idmodalidadVenta")
    @ManyToOne(optional = false)
    private Modalidadventa modalidadventaidmodalidadVenta;
    @JoinColumn(name = "usuario_idusuario", referencedColumnName = "idusuario")
    @ManyToOne(optional = false)
    private Usuario usuarioIdusuario;

    public Detalleventa() {
    }

    public Detalleventa(Integer iddetalleVenta) {
        this.iddetalleVenta = iddetalleVenta;
    }

    public Detalleventa(Integer iddetalleVenta, String fecha, double total, double abono) {
        this.iddetalleVenta = iddetalleVenta;
        this.fecha = fecha;
        this.total = total;
        this.abono = abono;
    }

    public Integer getIddetalleVenta() {
        return iddetalleVenta;
    }

    public void setIddetalleVenta(Integer iddetalleVenta) {
        this.iddetalleVenta = iddetalleVenta;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }

    public double getAbono() {
        return abono;
    }

    public void setAbono(double abono) {
        this.abono = abono;
    }

    @XmlTransient
    public Collection<Venta> getVentaCollection() {
        return ventaCollection;
    }

    public void setVentaCollection(Collection<Venta> ventaCollection) {
        this.ventaCollection = ventaCollection;
    }

    public Cliente getClienteIdcliente() {
        return clienteIdcliente;
    }

    public void setClienteIdcliente(Cliente clienteIdcliente) {
        this.clienteIdcliente = clienteIdcliente;
    }

    public Estatusventa getEstatusventaidestatusVenta() {
        return estatusventaidestatusVenta;
    }

    public void setEstatusventaidestatusVenta(Estatusventa estatusventaidestatusVenta) {
        this.estatusventaidestatusVenta = estatusventaidestatusVenta;
    }

    public Modalidadventa getModalidadventaidmodalidadVenta() {
        return modalidadventaidmodalidadVenta;
    }

    public void setModalidadventaidmodalidadVenta(Modalidadventa modalidadventaidmodalidadVenta) {
        this.modalidadventaidmodalidadVenta = modalidadventaidmodalidadVenta;
    }

    public Usuario getUsuarioIdusuario() {
        return usuarioIdusuario;
    }

    public void setUsuarioIdusuario(Usuario usuarioIdusuario) {
        this.usuarioIdusuario = usuarioIdusuario;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (iddetalleVenta != null ? iddetalleVenta.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Detalleventa)) {
            return false;
        }
        Detalleventa other = (Detalleventa) object;
        if ((this.iddetalleVenta == null && other.iddetalleVenta != null) || (this.iddetalleVenta != null && !this.iddetalleVenta.equals(other.iddetalleVenta))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entidades.Detalleventa[ iddetalleVenta=" + iddetalleVenta + " ]";
    }
    
}
