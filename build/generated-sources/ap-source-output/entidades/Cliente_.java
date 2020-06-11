package entidades;

import entidades.Detalleventa;
import javax.annotation.Generated;
import javax.persistence.metamodel.CollectionAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2019-11-06T22:17:00")
@StaticMetamodel(Cliente.class)
public class Cliente_ { 

    public static volatile SingularAttribute<Cliente, String> noTelefonico;
    public static volatile SingularAttribute<Cliente, String> domicilio;
    public static volatile SingularAttribute<Cliente, String> correo;
    public static volatile CollectionAttribute<Cliente, Detalleventa> detalleventaCollection;
    public static volatile SingularAttribute<Cliente, Integer> idcliente;
    public static volatile SingularAttribute<Cliente, String> nombre;

}