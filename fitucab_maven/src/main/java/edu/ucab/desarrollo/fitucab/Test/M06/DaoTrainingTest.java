package edu.ucab.desarrollo.fitucab.Test.M06;


import edu.ucab.desarrollo.fitucab.common.entities.Entity;
import edu.ucab.desarrollo.fitucab.common.entities.Training;
import edu.ucab.desarrollo.fitucab.common.entities.User;
import edu.ucab.desarrollo.fitucab.common.exceptions.ListAllException;

import edu.ucab.desarrollo.fitucab.common.entities.Activity;
import edu.ucab.desarrollo.fitucab.common.entities.EntityFactory;
import edu.ucab.desarrollo.fitucab.common.exceptions.*;
import edu.ucab.desarrollo.fitucab.dataAccessLayer.DaoFactory;
import edu.ucab.desarrollo.fitucab.dataAccessLayer.M06.DaoTraining;
import org.junit.jupiter.api.Test;


import java.util.LinkedList;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Created by Barbara Fernadez on 7/2/2017.
 */
class DaoTrainingTest {
    @Test
    void create() {
        Activity act = EntityFactory.createActivity(1, "Caminar", 2);
        ArrayList<Entity> activities = new ArrayList<Entity>();
        activities.add(act);
        Entity training = EntityFactory.createTraining(1, "PruebaUnitaria", activities);
        DaoTraining dao = DaoFactory.instanceDaoTraining( training );
        try {
            Entity result = dao.create(training);
            assertEquals(1, result.get_id());
        }catch (Exception ex){
            assertTrue(ex instanceof AddException);
        }
    }

    @Test
    void read() {
        DaoTraining dao = DaoFactory.instanceDaoTraining(null);
        assertNull(dao.read(null));
    }

    @Test
    void update() throws UpdateException
    {
        DaoTraining dao = DaoFactory.instanceDaoTraining(null);
        assertNull(dao.update(null));
    }

    @Test
    void delete() {
        Activity act = EntityFactory.createActivity(1, "Caminar", 2);
        ArrayList<Entity> activities = new ArrayList<Entity>();
        activities.add(act);
        Entity training = EntityFactory.createTraining(1, "PruebaUnitaria", activities);
        DaoTraining dao = DaoFactory.instanceDaoTraining( training );
        try {
            Entity result = dao.create(training);
            Boolean boolresult = dao.delete(result);
            assertEquals(true, boolresult);
        }catch (Exception ex){
            assertTrue(ex instanceof DeleteException);
        }
    }

    @Test
    void listAll() throws ListAllException {

    }

    @Test
    void trainingDetail() {
    }

    @Test
    void activateTraining() throws ActiveTrainingException
    {
        DaoTraining dao = DaoFactory.instanceDaoTraining(null);
        assertNull(dao.activateTraining(null,null));
    }
    

    @Test
    void listTrainingsShared() {
        DaoTraining dao = DaoFactory.instanceDaoTraining(null);
        assertNull(dao.listTrainingsShared(null));
    }


    @Test
    public void testListAll() throws Exception
    {
        User usuario = new User();
        Training entrenamiento = new Training();
        DaoTraining dao = new DaoTraining(entrenamiento);
        LinkedList<Entity> lista;

        usuario.set_id( 1 );

        lista = dao.listAll( usuario );

        assertFalse( lista.isEmpty() );

    }




    @Test
    public void testTrainingDetail() throws Exception
    {
        Training entrenamiento = new Training();
        DaoTraining dao = new DaoTraining(entrenamiento);
        Entity resultado;

        entrenamiento.set_id( 1 );

        resultado = ( Training ) dao.trainingDetail( entrenamiento );

        assertEquals( 1, resultado.get_id() );
        assertEquals( "default", ( ( Training )resultado ).getTrainingName() );

    }
}