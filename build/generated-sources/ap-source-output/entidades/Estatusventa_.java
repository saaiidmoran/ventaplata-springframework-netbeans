package entidades;

import entidades.Detalleventa;
import javax.annotation.Generated;
import javax.persistence.metamodel.CollectionAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2019-11-06T22:17:00")
@StaticMetamodel(Estatusventa.class)
public class Estatusventa_ { 

    public static volatile SingularAttribute<Estatusventa, String> estatusVenta;
    public static volatile SingularAttribute<Estatusventa, Integer> idestatusVenta;
    public static volatile CollectionAttribute<Estatusventa, Detalleventa> detalleventaCollection;

}