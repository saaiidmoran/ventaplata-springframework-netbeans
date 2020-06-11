package entidades;

import entidades.Estatusproducto;
import entidades.Venta;
import javax.annotation.Generated;
import javax.persistence.metamodel.CollectionAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2019-11-06T22:17:00")
@StaticMetamodel(Producto.class)
public class Producto_ { 

    public static volatile CollectionAttribute<Producto, Venta> ventaCollection;
    public static volatile SingularAttribute<Producto, String> fechaRegistro;
    public static volatile SingularAttribute<Producto, Double> precioVent;
    public static volatile SingularAttribute<Producto, Integer> idproducto;
    public static volatile SingularAttribute<Producto, String> nombre;
    public static volatile SingularAttribute<Producto, Double> precioInv;
    public static volatile SingularAttribute<Producto, Estatusproducto> estatusProductoidestatusProducto;

}