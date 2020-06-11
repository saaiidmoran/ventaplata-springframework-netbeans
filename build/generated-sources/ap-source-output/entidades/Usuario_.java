package entidades;

import entidades.Detalleventa;
import javax.annotation.Generated;
import javax.persistence.metamodel.CollectionAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2019-11-06T22:17:00")
@StaticMetamodel(Usuario.class)
public class Usuario_ { 

    public static volatile SingularAttribute<Usuario, String> clave;
    public static volatile SingularAttribute<Usuario, String> usrnombre;
    public static volatile CollectionAttribute<Usuario, Detalleventa> detalleventaCollection;
    public static volatile SingularAttribute<Usuario, Integer> idusuario;

}