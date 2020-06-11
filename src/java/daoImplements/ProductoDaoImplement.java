/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package daoImplements;

import daoInterfaces.ProductoDaoInterface;
import entidades.Producto;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceUnit;
import javax.persistence.Query;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author saaii
 */
@Service
public class ProductoDaoImplement implements ProductoDaoInterface{
        
    @PersistenceUnit()
    private EntityManagerFactory entityManagerFactory;    
    
    @PersistenceContext
    private EntityManager em;
    
    @Transactional(rollbackFor = {ServicioException.class})

    @Override
    public Producto buscarProductoPorId(int id) {
        String sql;
        Query q;
        try{
            sql="Producto.findByIdproducto";        
            q = em.createNamedQuery(sql);
            q.setParameter("idproducto", id);
            return (Producto)q.getSingleResult();
        }catch(Exception e){
            imprimirErrorConsulta(e);
            return null;
        }
    }

    @Override
    public Producto buscarProductoPorNombre(String nombre) {
        String sql;
        Query q;
        try{
            sql = "Producto.findByNombre";
            q = em.createNamedQuery(sql);
            q.setParameter("nombre", nombre);
            return (Producto)q.getSingleResult();
        }catch(Exception e){
            imprimirErrorConsulta(e);
            return null;
        }
    }

    @Override
    public List<Producto> consultarTodos() {
        String sql;
        Query q;
        List<Producto> retorno;
        try{
            sql = "SELECT p FROM Producto p";
            q = em.createQuery(sql);
            retorno = new ArrayList<>();
            for(Object p : q.getResultList()){
                retorno.add((Producto)p);
            }
            return retorno;
        }catch (Exception e){
            imprimirErrorConsulta(e);
            return null;
        }
    }

    @Override
    public boolean agregarProducto(Producto producto) {
        int realizado;
        String sql;
        EntityManager em2;
        try{
            em2 = entityManagerFactory.createEntityManager();
            sql = "insert into producto (nombre,precioInv,precioVent,fechaRegistro,estatusProducto_idestatusProducto) values (?,?,?,?,?)";
            em2.getTransaction().begin();
            realizado = em2.createNativeQuery(sql)
                    .setParameter(1, producto.getNombre())
                    .setParameter(2, producto.getPrecioInv())
                    .setParameter(3, producto.getPrecioVent())
                    .setParameter(4, producto.getFechaRegistro())
                    .setParameter(5, producto.getEstatusProductoidestatusProducto().getIdestatusProducto())
                    .executeUpdate();
            em2.flush();
            em2.getTransaction().commit();
            em2.close();
            return realizado == 1; 
        }catch(Exception e){
            System.out.println("Ocurrió un error al registrar la información del producto " + e);
            return false;
        }
    }

    @Override
    public boolean modificarProducto(Producto producto) {
        Producto actual;
        Producto modificado;
        EntityManager em2;
        try{
            em2 = entityManagerFactory.createEntityManager();
            em2.getTransaction().begin();
            actual = producto;
            modificado = em2.merge(producto);
            em2.flush();
            em2.getTransaction().commit();
            em2.close();
            return actual.getNombre().equals(modificado.getNombre()) 
                    && actual.getPrecioInv() == modificado.getPrecioInv()
                    && actual.getPrecioVent() == modificado.getPrecioVent()
                    && actual.getFechaRegistro().equals(modificado.getFechaRegistro())
                    && actual.getEstatusProductoidestatusProducto().equals(modificado.getEstatusProductoidestatusProducto());
        }catch(Exception e){
            System.out.println("Ocurrió un error al modificar la información del producto " + e);
            return false;
        }
    }

    @Override
    public boolean eliminarProducto(Producto producto) {
        int realizado;
        String sql;
        EntityManager em2;
        try{
            em2 = entityManagerFactory.createEntityManager();
            sql = "delete from producto where (idproducto=?)";
            em2.getTransaction().begin();
            realizado = em2.createNativeQuery(sql)
                    .setParameter(1, producto.getIdproducto())
                    .executeUpdate();
            em2.flush();
            em2.getTransaction().commit();
            em2.close();
            return realizado == 1;
        }catch(Exception e){
            System.out.println("Ocurrió un error al eliminar la información del producto " + e);
            return false;
        }
    }   
    
    private void imprimirErrorConsulta(Exception e){
        System.out.println("Ocurrió un error al realizar la consulta " + e);
    }
    
}
