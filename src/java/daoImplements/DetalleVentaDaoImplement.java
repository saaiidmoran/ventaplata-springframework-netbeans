/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package daoImplements;

import daoInterfaces.DetalleVentaDaoInterface;
import entidades.Detalleventa;
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
public class DetalleVentaDaoImplement implements DetalleVentaDaoInterface{
    
    @PersistenceUnit()
    private EntityManagerFactory entityManagerFactory;   
    
    @PersistenceContext
    private EntityManager em;
    
    @Transactional(rollbackFor = {ServicioException.class})

    @Override
    public Detalleventa buscarDetalleVentaPorId(int id) {
        String sql;
        Query q;
        try{
            em.getEntityManagerFactory().getCache().evictAll();
            sql="Detalleventa.findByIddetalleVenta";        
            q = em.createNamedQuery(sql);
            q.setParameter("iddetalleVenta", id);
            return (Detalleventa)q.getSingleResult();
        }catch(Exception e){
            imprimirErrorConsulta(e);
            return null;
        }
    }

    @Override
    public Detalleventa buscarDetalleVentaPorFecha(String fecha) {
        String sql;
        Query q;
        try{
            em.getEntityManagerFactory().getCache().evictAll();
            sql="Detalleventa.findByFecha";        
            q = em.createNamedQuery(sql);
            q.setParameter("fecha", fecha);
            return (Detalleventa)q.getSingleResult();
        }catch(Exception e){
            imprimirErrorConsulta(e);
            return null;
        }
    }
    
    @Override
    public Detalleventa buscarUltimoAgregado(){
        List<Detalleventa> retorno;
        String sql;
        Query q;
        try{
            em.getEntityManagerFactory().getCache().evictAll();
            sql="Detalleventa.findAll";        
            q = em.createNamedQuery(sql);
            retorno = new ArrayList<>();
            for(Object dv : q.getResultList()){
                retorno.add((Detalleventa)dv);
            }
            return retorno.get((retorno.size()-1));
        }catch(Exception e){
            imprimirErrorConsulta(e);
            return null;
        }
    }

    @Override
    public List<Detalleventa> buscarTodos() {
        List<Detalleventa> retorno1;
        String sql;
        Query q;
        try{
            em.getEntityManagerFactory().getCache().evictAll();
            sql="Detalleventa.findAll";        
            q = em.createNamedQuery(sql);
            retorno1 = new ArrayList<>();
            for(Object dv : q.getResultList()){
                retorno1.add((Detalleventa)dv);
            }
            return retorno1;
        }catch(Exception e){
            imprimirErrorConsulta(e);
            return null;
        }
    }

    @Override
    public boolean registrarDetalleVenta(Detalleventa dv) {
        int realizado;
        String sql;
        EntityManager em2;
        try{
            em2 = entityManagerFactory.createEntityManager();
            sql = "insert into detalleventa (fecha,total,abono,cliente_idcliente,modalidadVenta_idmodalidadVenta,estatusVenta_idestatusVenta,usuario_idusuario) values (?,?,?,?,?,?,?)";
            em2.getTransaction().begin();
            realizado = em2.createNativeQuery(sql)
                    .setParameter(1, dv.getFecha())
                    .setParameter(2, dv.getTotal())
                    .setParameter(3, dv.getAbono())
                    .setParameter(4, dv.getClienteIdcliente().getIdcliente())
                    .setParameter(5, dv.getModalidadventaidmodalidadVenta().getIdmodalidadVenta())
                    .setParameter(6, dv.getEstatusventaidestatusVenta().getIdestatusVenta())
                    .setParameter(7, dv.getUsuarioIdusuario().getIdusuario())
                    .executeUpdate();
            em2.flush();
            em2.getTransaction().commit();
            em2.close();
            return realizado == 1; 
        }catch(Exception e){
            System.out.println("Ocurrió un error al registrar la información del detalle de venta " + e);
            return false;
        }
    }

    @Override
    public boolean modificarDetalleVenta(Detalleventa dv) {
        Detalleventa actual;
        Detalleventa modificado;
        EntityManager em2;
        try{
            em2 = entityManagerFactory.createEntityManager();
            em2.getTransaction().begin();
            actual = dv;
            modificado = em2.merge(dv);
            em2.flush();
            em2.getTransaction().commit();
            em2.close();
            return actual.getFecha().equals(modificado.getFecha()) 
                    && actual.getTotal() == modificado.getTotal()
                    && actual.getAbono() == modificado.getAbono()
                    && actual.getClienteIdcliente().equals(modificado.getClienteIdcliente())
                    && actual.getModalidadventaidmodalidadVenta().equals(modificado.getModalidadventaidmodalidadVenta())
                    && actual.getEstatusventaidestatusVenta().equals(modificado.getEstatusventaidestatusVenta())
                    && actual.getUsuarioIdusuario().equals(modificado.getUsuarioIdusuario());
        }catch(Exception e){
            System.out.println("Ocurrió un error al modificar la información del detalle de venta " + e);
            return false;
        }
    }

    @Override
    public boolean eliminarDetalleVenta(Detalleventa dv) {
        int realizado;
        String sql;
        EntityManager em2;
        try{
            em2 = entityManagerFactory.createEntityManager();
            sql = "delete from detalleVenta where (iddetalleVenta=?)";
            em2.getTransaction().begin();
            realizado = em2.createNativeQuery(sql)
                    .setParameter(1, dv.getIddetalleVenta())
                    .executeUpdate();
            em2.flush();
            em2.getTransaction().commit();
            em2.close();
            return realizado == 1;
        }catch(Exception e){
            System.out.println("Ocurrió un error al eliminar la información del detalle de venta " + e);
            return false;
        }
    }
    
    private void imprimirErrorConsulta(Exception e){
        System.out.println("Ocurrió un error al realizar la consulta " + e);
    }
    
}
