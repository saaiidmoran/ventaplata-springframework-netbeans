/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package daoImplements;

import daoInterfaces.VentaDaoInterface;
import entidades.Venta;
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
public class VentaDaoImplement implements VentaDaoInterface{
    
    @PersistenceUnit()
    private EntityManagerFactory entityManagerFactory;    
    
    @PersistenceContext
    private EntityManager em;
    
    @Transactional(rollbackFor = {ServicioException.class})    

    @Override
    public Venta buscarVentaPorId(int id) {
        String sql;
        Query q;
        try{
            sql="Venta.findByIdventa";        
            q = em.createNamedQuery(sql);
            q.setParameter("idventa", id);
            return (Venta)q.getSingleResult();
        }catch(Exception e){
            imprimirErrorConsulta(e);
            return null;
        }
    }

    @Override
    public List<Venta> consultarTodas() {
        String sql;
        Query q;
        List<Venta> retorno;
        try{
            sql="Venta.findAll";        
            q = em.createNamedQuery(sql);
            retorno = new ArrayList<>();
            for(Object v : q.getResultList()){
                retorno.add((Venta)v);
            }
            return retorno;
        }catch(Exception e){
            imprimirErrorConsulta(e);
            return null;
        }
    }

    @Override
    public boolean agregarVenta(Venta venta) {
        int realizado;
        String sql;
        EntityManager em2;
        try{
            em2 = entityManagerFactory.createEntityManager();
            sql = "insert into venta (producto_idproducto,detalleVenta_idDetalleVenta) values (?,?)";
            em2.getTransaction().begin();
            realizado = em2.createNativeQuery(sql)
                    .setParameter(1, venta.getProductoIdproducto().getIdproducto())
                    .setParameter(2, venta.getDetalleVentaidDetalleVenta().getIddetalleVenta())
                    .executeUpdate();
            em2.flush();
            em2.getTransaction().commit();
            em2.close();
            return realizado == 1;
        }catch(Exception e){
            System.out.println("Ocurrió un error al registrar la información de la venta " + e);
            return true;
        }
    }

    @Override
    public boolean modificarVenta(Venta venta) {
        Venta actual;
        Venta modificado;
        EntityManager em2;
        try{
            em2 = entityManagerFactory.createEntityManager();
            em2.getTransaction().begin();
            actual = venta;
            modificado = em2.merge(venta);
            em2.flush();
            em2.getTransaction().commit();
            em2.close();
            return     actual.getProductoIdproducto().equals(modificado.getProductoIdproducto())
                    && actual.getDetalleVentaidDetalleVenta().equals(modificado.getDetalleVentaidDetalleVenta());
        }catch(Exception e){
            System.out.println("Ocurrió un error al modificar la información de la venta " + e);
            return false;
        }
    }

    @Override
    public boolean eliminarVenta(Venta venta) {
        int realizado;
        String sql;
        EntityManager em2;
        try{
            em2 = entityManagerFactory.createEntityManager();
            sql = "delete from venta where (idventa=?)";
            em2.getTransaction().begin();
            realizado = em2.createNativeQuery(sql)
                    .setParameter(1, venta.getIdventa())
                    .executeUpdate();
            em2.flush();
            em2.getTransaction().commit();
            em2.close();
            return realizado == 1;
        }catch(Exception e){
            System.out.println("Ocurrió un error al eliminar la información de la venta " + e);
            return false;
        }
    }
    
    private void imprimirErrorConsulta(Exception e){
        System.out.println("Ocurrió un error al realizar la consulta " + e);
    }
    
}
