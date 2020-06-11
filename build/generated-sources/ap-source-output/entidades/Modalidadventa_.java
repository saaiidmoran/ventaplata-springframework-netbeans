package entidades;

import entidades.Detalleventa;
import javax.annotation.Generated;
import javax.persistence.metamodel.CollectionAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2019-11-06T22:17:00")
@StaticMetamodel(Modalidadventa.class)
public class Modalidadventa_ { 

    public static volatile SingularAttribute<Modalidadventa, String> modalidad;
    public static volatile CollectionAttribute<Modalidadventa, Detalleventa> detalleventaCollection;
    public static volatile SingularAttribute<Modalidadventa, Integer> idmodalidadVenta;

}