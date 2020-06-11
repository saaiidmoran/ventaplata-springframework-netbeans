/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package daoImplements;

import daoInterfaces.ModalidadVentaDaoInterface;
import entidades.Modalidadventa;
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
public class ModalidadVentaDaoImplement implements ModalidadVentaDaoInterface{
    @PersistenceUnit()
    private EntityManagerFactory entityManagerFactory;   
    
    @PersistenceContext
    private EntityManager em;
    
    @Transactional(rollbackFor = {ServicioException.class})

    @Override
    public Modalidadventa buscarModalidadVentaPorId(int id) {
        String sql;
        Query q;
        try{
            sql = "Modalidadventa.findByIdmodalidadVenta";
            q = em.createNamedQuery(sql);
            q.setParameter("idmodalidadVenta", id);
            return (Modalidadventa)q.getSingleResult();
        }catch(Exception e){
            imprimirErrorConsulta(e);
            return null;
        }
    }

    @Override
    public Modalidadventa buscarModalidadVentaPorNombre(String nombre) {
        String sql;
        Query q;
        try{
            sql = "Modalidadventa.findByModalidad";
            q = em.createNamedQuery(sql);
            q.setParameter("modalidad", nombre);
            return (Modalidadventa)q.getSingleResult();
        }catch(Exception e){
            imprimirErrorConsulta(e);
            return null;
        }
    }

    @Override
    public List<Modalidadventa> consultarTodas() {
        String sql;
        Query q;
        List<Modalidadventa> retorno;
        try{
            sql = "SELECT m FROM Modalidadventa m";
            q = em.createQuery(sql);
            retorno = new ArrayList<>();
            for(Object mv : q.getResultList()){
                retorno.add((Modalidadventa)mv);
            }
            return retorno;
        }catch (Exception e){
            imprimirErrorConsulta(e);
            return null;
        }
    }

    @Override
    public boolean agregarModalidadVenta(Modalidadventa modalidadventa) {
        String sql;
        int realizado;
        EntityManager em2;
        try{
            em2 = entityManagerFactory.createEntityManager();
            sql = "insert into modalidadVenta (modalidad) values (?)";
            em2.getTransaction().begin();
            realizado = em2.createNativeQuery(sql)
                    .setParameter(1, modalidadventa.getModalidad())
                    .executeUpdate();
            em2.flush();
            em2.getTransaction().commit();
            em2.close();
            return realizado == 1;
        }catch(Exception e){
            System.out.println("Ocurrió un error al agregar la información de la modalidad de venta " + e);
            return false;
        }
    }

    @Override
    public boolean modificarModalidadVenta(Modalidadventa modalidadventa) {
        Modalidadventa actual;
        Modalidadventa modificado;
        EntityManager em2;
        try{
            em2 = entityManagerFactory.createEntityManager();
            em2.getTransaction().begin();
            actual = modalidadventa;
            modificado = em2.merge(modalidadventa);
            em2.flush();
            em2.getTransaction().commit();
            em2.close();
            return actual.getModalidad().equals(modificado.getModalidad());
        }catch(Exception e){
            System.out.println("Ocurrió un error al modificar la información de la modalidad de venta " + e);
            return false;
        }
    }

    @Override
    public boolean eliminarModalidadVenta(Modalidadventa modalidadventa) {
        String sql;
        int realizado;
        EntityManager em2;
        try{
            em2 = entityManagerFactory.createEntityManager();
            sql = "delete from modalidadVenta where (idmodalidadVenta=?)";
            em2.getTransaction().begin();
            realizado = em2.createNativeQuery(sql)
                    .setParameter(1, modalidadventa.getIdmodalidadVenta())
                    .executeUpdate();
            em2.flush();
            em2.getTransaction().commit();
            em2.close();
            return realizado == 1;
        }catch(Exception e){
            System.out.println("Ocurrió un error al eliminar la información de la modalidad de venta " + e);
            return false;
        }
    }
    
    private void imprimirErrorConsulta(Exception e){
        System.out.println("Ocurrió un error al realizar la consulta " + e);
    }
    
}
