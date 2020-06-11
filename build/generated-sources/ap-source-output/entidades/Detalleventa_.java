package entidades;

import entidades.Cliente;
import entidades.Estatusventa;
import entidades.Modalidadventa;
import entidades.Usuario;
import entidades.Venta;
import javax.annotation.Generated;
import javax.persistence.metamodel.CollectionAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2019-11-06T22:17:00")
@StaticMetamodel(Detalleventa.class)
public class Detalleventa_ { 

    public static volatile SingularAttribute<Detalleventa, String> fecha;
    public static volatile SingularAttribute<Detalleventa, Double> total;
    public static volatile SingularAttribute<Detalleventa, Cliente> clienteIdcliente;
    public static volatile CollectionAttribute<Detalleventa, Venta> ventaCollection;
    public static volatile SingularAttribute<Detalleventa, Estatusventa> estatusventaidestatusVenta;
    public static volatile SingularAttribute<Detalleventa, Modalidadventa> modalidadventaidmodalidadVenta;
    public static volatile SingularAttribute<Detalleventa, Usuario> usuarioIdusuario;
    public static volatile SingularAttribute<Detalleventa, Double> abono;
    public static volatile SingularAttribute<Detalleventa, Integer> iddetalleVenta;

}