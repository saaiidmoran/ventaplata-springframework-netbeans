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
@Table(name = "estatusventa")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Estatusventa.findAll", query = "SELECT e FROM Estatusventa e"),
    @NamedQuery(name = "Estatusventa.findByIdestatusVenta", query = "SELECT e FROM Estatusventa e WHERE e.idestatusVenta = :idestatusVenta"),
    @NamedQuery(name = "Estatusventa.findByEstatusVenta", query = "SELECT e FROM Estatusventa e WHERE e.estatusVenta = :estatusVenta")})
public class Estatusventa implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idestatusVenta")
    private Integer idestatusVenta;
    @Basic(optional = false)
    @Column(name = "estatusVenta")
    private String estatusVenta;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "estatusventaidestatusVenta")
    private Collection<Detalleventa> detalleventaCollection;

    public Estatusventa() {
    }

    public Estatusventa(Integer idestatusVenta) {
        this.idestatusVenta = idestatusVenta;
    }

    public Estatusventa(Integer idestatusVenta, String estatusVenta) {
        this.idestatusVenta = idestatusVenta;
        this.estatusVenta = estatusVenta;
    }

    public Integer getIdestatusVenta() {
        return idestatusVenta;
    }

    public void setIdestatusVenta(Integer idestatusVenta) {
        this.idestatusVenta = idestatusVenta;
    }

    public String getEstatusVenta() {
        return estatusVenta;
    }

    public void setEstatusVenta(String estatusVenta) {
        this.estatusVenta = estatusVenta;
    }

    @XmlTransient
    public Collection<Detalleventa> getDetalleventaCollection() {
        return detalleventaCollection;
    }

    public void setDetalleventaCollection(Collection<Detalleventa> detalleventaCollection) {
        this.detalleventaCollection = detalleventaCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idestatusVenta != null ? idestatusVenta.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Estatusventa)) {
            return false;
        }
        Estatusventa other = (Estatusventa) object;
        if ((this.idestatusVenta == null && other.idestatusVenta != null) || (this.idestatusVenta != null && !this.idestatusVenta.equals(other.idestatusVenta))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entidades.Estatusventa[ idestatusVenta=" + idestatusVenta + " ]";
    }
    
}
