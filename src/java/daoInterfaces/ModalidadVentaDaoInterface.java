/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package daoInterfaces;

import entidades.Modalidadventa;
import java.util.List;

/**
 *
 * @author saaii
 */
public interface ModalidadVentaDaoInterface {
    public Modalidadventa buscarModalidadVentaPorId(int id);
    public Modalidadventa buscarModalidadVentaPorNombre(String nombre);
    public List<Modalidadventa> consultarTodas();
    public boolean agregarModalidadVenta(Modalidadventa modalidadventa);
    public boolean modificarModalidadVenta(Modalidadventa modalidadventa);
    public boolean eliminarModalidadVenta(Modalidadventa modalidadventa);
}
