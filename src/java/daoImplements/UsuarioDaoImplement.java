/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package daoImplements;

import daoInterfaces.UsuarioDaoInterface;
import entidades.Usuario;
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
public class UsuarioDaoImplement implements UsuarioDaoInterface{
   
   @PersistenceUnit()
    private EntityManagerFactory entityManagerFactory;   
    
    @PersistenceContext
    private EntityManager em;
    
    @Transactional(rollbackFor = {ServicioException.class})  
    
    @Override
    public Usuario buscarUsuarioPorNombre(String usr){ 
        String sql;
        Query q;
        try{
            sql="Usuario.findByUsrnombre";        
            q = em.createNamedQuery(sql);
            q.setParameter("usrnombre", usr);
            return (Usuario)q.getSingleResult();
        }catch(Exception e){
            imprimirErrorConsulta(e);
            return null;
        }
    }
    
    @Override
    public Usuario buscarUsuarioPorId(int idusr) {
        String sql;
        Query q;
        try{
            sql="Usuario.findByIdusuario";
            q = em.createNamedQuery(sql);
            q.setParameter("idusuario", idusr);
            return (Usuario)q.getSingleResult();
        }catch(Exception e){
            imprimirErrorConsulta(e);
            return null;
        }
    }

    @Override
    public List<Usuario> consultarTodos() {
        String sql;
        Query q;
        List<Usuario> retorno;
        try{
            sql = "SELECT u FROM Usuario u";
            q = em.createQuery(sql);
            retorno = new ArrayList<>();
            for(Object u : q.getResultList()){
                retorno.add((Usuario) u);
            }
            return retorno;
        }catch(Exception e){
            imprimirErrorConsulta(e);
            return null;
        }       
    }

    @Override
    public boolean agregarUsuario(Usuario usr) {
        String sql;
        int realizado;
        EntityManager em2;
        try{
            em2 = entityManagerFactory.createEntityManager();        
            sql = "insert into usuario (usrnombre,clave) values (?,?)";
            em2.getTransaction().begin();
            realizado = em2.createNativeQuery(sql)
                    .setParameter(1, usr.getUsrnombre())
                    .setParameter(2, usr.getClave())
                    .executeUpdate();
            em2.flush();
            em2.getTransaction().commit();
            em2.close();
            return realizado==1;
        }catch(Exception e){
            System.out.println("Ocurrió un error al agregar la información del usuario" + e);
            return false;
        }       
    }    

    @Override
    public boolean eliminarUsuario(Usuario usr) {
        String sql;
        int realizado;
        EntityManager em2;
        try{
            em2 = entityManagerFactory.createEntityManager();
            sql = "delete from usuario where (idusuario=?)";
            em2.getTransaction().begin();
            realizado = em2.createNativeQuery(sql)
                    .setParameter(1,usr.getIdusuario())
                    .executeUpdate();
            em2.flush();
            em2.getTransaction().commit();
            em2.close();
            return realizado==1;
        }catch(Exception e){
            System.out.println("Ocurrió un error al eliminar la información del usuario " + e);
            return false;
        }       
    }

    @Override
    public boolean modificarUsuario(Usuario usr) {
        Usuario actual;
        Usuario modificado;
        EntityManager em2;
        try{
            em2 = entityManagerFactory.createEntityManager();
            em2.getTransaction().begin();
            actual = usr;
            modificado = em2.merge(usr);        
            em2.flush();
            em2.getTransaction().commit();
            em2.close();
            return actual.getUsrnombre().equals(modificado.getUsrnombre()) 
                    && actual.getClave().equals(modificado.getClave());
        }catch(Exception e){
            System.out.println("Ocurrió un error al modificar la información del usuario" + e);
            return false;
        }       
    }
    private void imprimirErrorConsulta(Exception e){
        System.out.println("Ocurrió un error al realizar la consulta " + e);
    }
}
