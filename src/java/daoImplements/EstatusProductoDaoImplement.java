/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package daoImplements;

import daoInterfaces.EstatusProductoDaoInterface;
import entidades.Estatusproducto;
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
public class EstatusProductoDaoImplement implements EstatusProductoDaoInterface{
    
    @PersistenceUnit()
    private EntityManagerFactory entityManagerFactory;    
    
    @PersistenceContext
    private EntityManager em;
    
    @Transactional(rollbackFor = {ServicioException.class})
    
    @Override
    public Estatusproducto buscarEstatusProductoPorId(int id) {
        String sql;
        Query q;
        try{
            em.getEntityManagerFactory().getCache().evictAll(); //Evita tomar el cache de la persistencia y vuelve a consultar directamente con la base de datos
            sql = "Estatusproducto.findByIdestatusProducto";
            q = em.createNamedQuery(sql);
            q.setParameter("idestatusProducto", id);
            return (Estatusproducto)q.getSingleResult();
        }catch(Exception e){
            imprimirErrorConsulta(e);
            return null;
        }
    }

    @Override
    public Estatusproducto buscarEstatusProductoPorNombre(String nombre) {
        String sql;
        Query q;
        try{
            em.getEntityManagerFactory().getCache().evictAll();
            sql = "Estatusproducto.findByEstatusProducto";
            q = em.createNamedQuery(sql);
            q.setParameter("estatusProducto", nombre);
            return (Estatusproducto)q.getSingleResult();
        }catch(Exception e){
            imprimirErrorConsulta(e);
            return null;
        }
    }

    @Override
    public List<Estatusproducto> consultarTodos() {
        String sql;
        Query q;
        List<Estatusproducto> retorno;
        try{
            em.getEntityManagerFactory().getCache().evictAll();
            sql = "SELECT e FROM Estatusproducto e";
            q = em.createQuery(sql);
            retorno = new ArrayList<>();
            for(Object ep : q.getResultList()){
                retorno.add((Estatusproducto)ep);
            }            
            return retorno;
        }catch (Exception e){
            imprimirErrorConsulta(e);
            return null;
        }
    }

    @Override
    public boolean agregarEstatusProducto(Estatusproducto estatusproducto) {
        String sql;
        int realizado;
        EntityManager em2;
        try{
            em2 = entityManagerFactory.createEntityManager();
            sql = "insert into estatusProducto (estatusProducto) values (?)";
            em2.getTransaction().begin();
            realizado = em2.createNativeQuery(sql)
                    .setParameter(1, estatusproducto.getEstatusProducto())
                    .executeUpdate();
            em2.flush();
            em2.getTransaction().commit();
            em2.close();
            return realizado == 1;
        }catch(Exception e){
            System.out.println("Ocurrió un error al registrar la información del estatus de producto " + e);
            return false;
        }
    }

    @Override
    public boolean modificarEstatusProducto(Estatusproducto estatusproducto) {
        Estatusproducto actual;
        Estatusproducto modificado;
        EntityManager em2;
        try{
            em2 = entityManagerFactory.createEntityManager();
            em2.getTransaction().begin();
            actual = estatusproducto;
            modificado = em2.merge(estatusproducto);
            em2.flush();
            em2.getTransaction().commit();
            em2.close();
            return actual.getEstatusProducto().equals(modificado.getEstatusProducto());
        }catch(Exception e){
            System.out.println("Ocurrió un error al modificar la información del estatus de producto " + e);
            return false;
        }
    }

    @Override
    public boolean eliminarEstatusProducto(Estatusproducto estatusproducto) {
        String sql;
        int realizado;
        EntityManager em2;
        try{
            em2 = entityManagerFactory.createEntityManager();
            sql = "delete from estatusProducto where (idestatusProducto=?)";
            em2.getTransaction().begin();
            realizado = em2.createNativeQuery(sql)
                    .setParameter(1, estatusproducto.getIdestatusProducto())
                    .executeUpdate();
            em2.flush();
            em2.getTransaction().commit();
            em2.close();
            return realizado == 1;
        }catch(Exception e){
            System.out.println("Ocurrió un error al eliminar la información del estatus de producto " + e);
            return false;
        }
    }
    
    private void imprimirErrorConsulta(Exception e){
        System.out.println("Ocurrió un error al realizar la consulta " + e);
    }
    
}
