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
@Table(name = "modalidadventa")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Modalidadventa.findAll", query = "SELECT m FROM Modalidadventa m"),
    @NamedQuery(name = "Modalidadventa.findByIdmodalidadVenta", query = "SELECT m FROM Modalidadventa m WHERE m.idmodalidadVenta = :idmodalidadVenta"),
    @NamedQuery(name = "Modalidadventa.findByModalidad", query = "SELECT m FROM Modalidadventa m WHERE m.modalidad = :modalidad")})
public class Modalidadventa implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idmodalidadVenta")
    private Integer idmodalidadVenta;
    @Basic(optional = false)
    @Column(name = "modalidad")
    private String modalidad;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "modalidadventaidmodalidadVenta")
    private Collection<Detalleventa> detalleventaCollection;

    public Modalidadventa() {
    }

    public Modalidadventa(Integer idmodalidadVenta) {
        this.idmodalidadVenta = idmodalidadVenta;
    }

    public Modalidadventa(Integer idmodalidadVenta, String modalidad) {
        this.idmodalidadVenta = idmodalidadVenta;
        this.modalidad = modalidad;
    }

    public Integer getIdmodalidadVenta() {
        return idmodalidadVenta;
    }

    public void setIdmodalidadVenta(Integer idmodalidadVenta) {
        this.idmodalidadVenta = idmodalidadVenta;
    }

    public String getModalidad() {
        return modalidad;
    }

    public void setModalidad(String modalidad) {
        this.modalidad = modalidad;
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
        hash += (idmodalidadVenta != null ? idmodalidadVenta.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Modalidadventa)) {
            return false;
        }
        Modalidadventa other = (Modalidadventa) object;
        if ((this.idmodalidadVenta == null && other.idmodalidadVenta != null) || (this.idmodalidadVenta != null && !this.idmodalidadVenta.equals(other.idmodalidadVenta))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entidades.Modalidadventa[ idmodalidadVenta=" + idmodalidadVenta + " ]";
    }
    
}
