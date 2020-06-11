package entidades;

import entidades.Detalleventa;
import entidades.Producto;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2019-11-06T22:17:00")
@StaticMetamodel(Venta.class)
public class Venta_ { 

    public static volatile SingularAttribute<Venta, Integer> idventa;
    public static volatile SingularAttribute<Venta, Detalleventa> detalleVentaidDetalleVenta;
    public static volatile SingularAttribute<Venta, Producto> productoIdproducto;

}