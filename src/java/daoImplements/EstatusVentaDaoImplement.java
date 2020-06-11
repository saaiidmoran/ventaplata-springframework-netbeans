/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package daoImplements;

import daoInterfaces.EstatusVentaDaoInterface;
import entidades.Estatusventa;
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
public class EstatusVentaDaoImplement implements EstatusVentaDaoInterface{
    
    @PersistenceUnit()
    private EntityManagerFactory entityManagerFactory;
    
    
    @PersistenceContext
    private EntityManager em;
    
    @Transactional(rollbackFor = {ServicioException.class})

    @Override
    public Estatusventa buscarEstatusVentaPorId(int id) {
        String sql;
        Query q;
        try{
            sql = "Estatusventa.findByIdestatusVenta";
            q = em.createNamedQuery(sql);
            q.setParameter("idestatusVenta", id);
            return (Estatusventa)q.getSingleResult();
        }catch(Exception e){
            imprimirErrorConsulta(e);
            return null;
        }
    }

    @Override
    public Estatusventa buscarEstatusVentaPorNombre(String nombre) {
        String sql;
        Query q;
        try{
            sql = "Estatusventa.findByEstatusVenta";
            q = em.createNamedQuery(sql);
            q.setParameter("estatusVenta", nombre);
            return (Estatusventa)q.getSingleResult();
        }catch(Exception e){
            imprimirErrorConsulta(e);
            return null;
        }
    }

    @Override
    public List<Estatusventa> consultarTodos() {
        String sql;
        Query q;
        List<Estatusventa> retorno;
        try{
            sql = "SELECT e FROM Estatusventa e";
            q = em.createQuery(sql);
            retorno = new ArrayList<>();
            for(Object ev : q.getResultList()){
                retorno.add((Estatusventa)ev);
            }
            return retorno;
        }catch (Exception e){
            imprimirErrorConsulta(e);
            return null;
        }
    }

    @Override
    public boolean agregarEstatusVenta(Estatusventa estatusventa) {
        String sql;
        int realizado;
        EntityManager em2;
        try{
            em2 = entityManagerFactory.createEntityManager();
            sql = "insert into estatusVenta (estatusVenta) values (?)";
            em2.getTransaction().begin();
            realizado = em2.createNativeQuery(sql)
                    .setParameter(1, estatusventa.getEstatusVenta())
                    .executeUpdate();
            em2.flush();
            em2.getTransaction().commit();
            em2.close();
            return realizado == 1;
        }catch(Exception e){
            System.out.println("Ocurrió un error al registrar la información del estatus de venta " + e);
            return false;
        }
    }

    @Override
    public boolean modificarEstatusVenta(Estatusventa estatusventa) {
        Estatusventa actual;
        Estatusventa modificado;
        EntityManager em2;
        try{
            em2 = entityManagerFactory.createEntityManager();
            em2.getTransaction().begin();
            actual = estatusventa;
            modificado = em2.merge(estatusventa);
            em2.flush();
            em2.getTransaction().commit();
            em2.close();
            return actual.getEstatusVenta().equals(modificado.getEstatusVenta());
        }catch(Exception e){
            System.out.println("Ocurrió un error al modificar la información del estatus de venta " + e);
            return false;
        }
    }

    @Override
    public boolean eliminarEstatusVenta(Estatusventa estatusventa) {
        String sql;
        int realizado;
        EntityManager em2;
        try{
            em2 = entityManagerFactory.createEntityManager();
            sql = "delete from estatusVenta where (idestatusVenta=?)";
            em2.getTransaction().begin();
            realizado = em2.createNativeQuery(sql)
                    .setParameter(1, estatusventa.getIdestatusVenta())
                    .executeUpdate();
            em2.flush();
            em2.getTransaction().commit();
            em2.close();
            return realizado == 1;
        }catch(Exception e){
            System.out.println("Ocurrió un error al eliminar la información del estatus de venta " + e);
            return false;
        }
    }  
    
    private void imprimirErrorConsulta(Exception e){
        System.out.println("Ocurrió un error al realizar la consulta " + e);
    }
    
}
