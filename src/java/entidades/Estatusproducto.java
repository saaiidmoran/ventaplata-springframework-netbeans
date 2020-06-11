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
@Table(name = "estatusproducto")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Estatusproducto.findAll", query = "SELECT e FROM Estatusproducto e"),
    @NamedQuery(name = "Estatusproducto.findByIdestatusProducto", query = "SELECT e FROM Estatusproducto e WHERE e.idestatusProducto = :idestatusProducto"),
    @NamedQuery(name = "Estatusproducto.findByEstatusProducto", query = "SELECT e FROM Estatusproducto e WHERE e.estatusProducto = :estatusProducto")})
public class Estatusproducto implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idestatusProducto")
    private Integer idestatusProducto;
    @Basic(optional = false)
    @Column(name = "estatusProducto")
    private String estatusProducto;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "estatusProductoidestatusProducto")
    private Collection<Producto> productoCollection;

    public Estatusproducto() {
    }

    public Estatusproducto(Integer idestatusProducto) {
        this.idestatusProducto = idestatusProducto;
    }

    public Estatusproducto(Integer idestatusProducto, String estatusProducto) {
        this.idestatusProducto = idestatusProducto;
        this.estatusProducto = estatusProducto;
    }

    public Integer getIdestatusProducto() {
        return idestatusProducto;
    }

    public void setIdestatusProducto(Integer idestatusProducto) {
        this.idestatusProducto = idestatusProducto;
    }

    public String getEstatusProducto() {
        return estatusProducto;
    }

    public void setEstatusProducto(String estatusProducto) {
        this.estatusProducto = estatusProducto;
    }

    @XmlTransient
    public Collection<Producto> getProductoCollection() {
        return productoCollection;
    }

    public void setProductoCollection(Collection<Producto> productoCollection) {
        this.productoCollection = productoCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idestatusProducto != null ? idestatusProducto.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Estatusproducto)) {
            return false;
        }
        Estatusproducto other = (Estatusproducto) object;
        if ((this.idestatusProducto == null && other.idestatusProducto != null) || (this.idestatusProducto != null && !this.idestatusProducto.equals(other.idestatusProducto))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entidades.Estatusproducto[ idestatusProducto=" + idestatusProducto + " ]";
    }
    
}
