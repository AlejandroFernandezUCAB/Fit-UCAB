package edu.ucab.desarrollo.fitucab.dataAccessLayer.M02;

import edu.ucab.desarrollo.fitucab.common.entities.Entity;
import edu.ucab.desarrollo.fitucab.common.exceptions.M02.GetUserException;
import edu.ucab.desarrollo.fitucab.dataAccessLayer.IDao;

import java.util.Date;

/**
 * Interfaz de la clase DAO User para el manejo de la Entidad User.
 * @author Juan Macedo, Cesar Boza, Bryan Teixeira
 * @version 1.0
 */
public interface IDaoUser extends IDao {

    /**
     * Metodo que implementa el DaoUser que recibe un _id
     * @param _id
     */
    public Entity read(int _id) throws GetUserException;

    /**
     * Metodo que implementa el DaoUser que recibe un _id
     * @param _id
     */
    public Date Fecha(int _id) ;

}