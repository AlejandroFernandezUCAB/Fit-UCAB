package edu.ucab.desarrollo.fitucab.dataAccessLayer;

import edu.ucab.desarrollo.fitucab.common.entities.Entity;
import edu.ucab.desarrollo.fitucab.common.exceptions.AddException;

import java.sql.SQLException;
import java.util.List;

/**
 * Interface que implementa el DAO
 * que actua como contrato con los metodos comunes
 */
public interface IDao
{
    public Entity create(Entity e) throws Exception;

    public Entity read(Entity e) throws SQLException;

    public Entity update(Entity e);
    
}