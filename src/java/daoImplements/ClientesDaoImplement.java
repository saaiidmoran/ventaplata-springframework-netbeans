/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package daoImplements;

import daoInterfaces.ClienteDaoInterface;
import entidades.Cliente;
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
public class ClientesDaoImplement implements ClienteDaoInterface{
        
    @PersistenceUnit()
    private EntityManagerFactory entityManagerFactory;    
    
    @PersistenceContext
    private EntityManager em;
    
    @Transactional(rollbackFor = {ServicioException.class})

    @Override
    public Cliente buscarClientePorId(int id) {    
        String sql;
        Query q;
        try{
            em.getEntityManagerFactory().getCache().evictAll();
            sql="Cliente.findByIdcliente";        
            q = em.createNamedQuery(sql);
            q.setParameter("idcliente", id);
            return (Cliente)q.getSingleResult();
        }catch(Exception e){
            imprimirErrorConsulta(e);
            return null;
        }
    }

    @Override
    public Cliente buscarClientePorNombre(String nombre) {      
        String sql;
        Query q;
        try{
            em.getEntityManagerFactory().getCache().evictAll();
            sql = "Cliente.findByNombre";
            q = em.createNamedQuery(sql);
            q.setParameter("nombre", nombre);
            return (Cliente)q.getSingleResult();
        }catch(Exception e){
            imprimirErrorConsulta(e);
            return null;
        }
    }

    @Override
    public List<Cliente> consultarTodos() {
        List<Cliente> retorno;
        String sql;
        Query q;
        try{
            em.getEntityManagerFactory().getCache().evictAll();
            sql = "SELECT c FROM Cliente c";
            q = em.createQuery(sql);
            retorno = new ArrayList<>();
            for(Object o : q.getResultList()){
                retorno.add((Cliente)o);
            }
            return retorno;
        }catch (Exception e){
            imprimirErrorConsulta(e);
            return null;
        }
    }

    @Override
    public boolean agregarCliente(Cliente cliente) { 
        int realizado;
        String sql;
        EntityManager em2;
        try{
            em2 = entityManagerFactory.createEntityManager();
            sql = "insert into cliente (nombre,noTelefonico,correo,domicilio) values (?,?,?,?)";
            em2.getTransaction().begin();
            realizado = em2.createNativeQuery(sql)
                    .setParameter(1, cliente.getNombre())
                    .setParameter(2, cliente.getNoTelefonico())
                    .setParameter(3, cliente.getCorreo())
                    .setParameter(4, cliente.getDomicilio())
                    .executeUpdate();
            em2.flush();
            em2.getTransaction().commit();
            em2.close();
            return realizado == 1; 
        }catch(Exception e){
            System.out.println("Ocurrió un error al registrar la información del cliente" + e);
            return false;
        }
    }

    @Override
    public boolean modificarCliente(Cliente cliente) {
        Cliente actual;
        Cliente modificado;
        EntityManager em2;
        try{
            em2 = entityManagerFactory.createEntityManager();
            em2.getTransaction().begin();
            actual = cliente;
            modificado = em2.merge(cliente);
            em2.flush();
            em2.getTransaction().commit();
            em2.close();
            return  actual.getNombre().equals(modificado.getNombre()) 
                    && actual.getNoTelefonico().equals(modificado.getNoTelefonico())
                    && actual.getCorreo().equals(modificado.getCorreo())
                    && actual.getDomicilio().equals(modificado.getDomicilio());
        } catch(Exception e){
            System.out.println("Ocurrió un error al modificar la información del cliente" + e);
            return false;
        }
    }

    @Override
    public boolean eliminarCliente(Cliente cliente) {
        int realizado;
        String sql;
        EntityManager em2;
        try{
            em2 = entityManagerFactory.createEntityManager();
            sql = "delete from cliente where (idcliente=?)";
            em2.getTransaction().begin();
            realizado = em2.createNativeQuery(sql)
                    .setParameter(1, cliente.getIdcliente())
                    .executeUpdate();
            em2.flush();
            em2.getTransaction().commit();
            em2.close();
            return realizado == 1;
        }catch(Exception e){
            System.out.println("Ocurrió un error al eliminar la información del cliente" + e);
            return false;
        }
    }
    
    private void imprimirErrorConsulta(Exception e){
        System.out.println("Ocurrió un error al realizar la consulta " + e);
    }
    
}
