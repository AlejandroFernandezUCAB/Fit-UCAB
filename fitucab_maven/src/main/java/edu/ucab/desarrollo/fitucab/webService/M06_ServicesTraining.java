package edu.ucab.desarrollo.fitucab.webService;

import javax.ws.rs.*;


import com.google.gson.Gson;
import edu.ucab.desarrollo.fitucab.common.Registry;
import edu.ucab.desarrollo.fitucab.common.entities.Activity;
import edu.ucab.desarrollo.fitucab.common.entities.Entity;
import edu.ucab.desarrollo.fitucab.common.entities.EntityFactory;
import edu.ucab.desarrollo.fitucab.common.exceptions.ListAllException;
import edu.ucab.desarrollo.fitucab.common.exceptions.ListByIdException;
import edu.ucab.desarrollo.fitucab.domainLogicLayer.Command;
import edu.ucab.desarrollo.fitucab.domainLogicLayer.CommandsFactory;
import edu.ucab.desarrollo.fitucab.domainLogicLayer.M06.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ws.rs.*;
import java.security.InvalidParameterException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;


//AGREGAR USUARIOS


@Path( "/M06_ServicesTraining" )
public class M06_ServicesTraining
{
    private static Logger logger = LoggerFactory.getLogger( M06_ServicesTraining.class );
    Gson gson = new Gson();



    @POST
    @Path( "/changeTrainingName" )
    @Produces( "application/json" )

    /**
     * Metodo utilizado a traves de webservice para cambiar el nombre de un entranamiento
     * la base de datos
     * @param idTraining
     * @param trainingName
     * @return
     */

    public String renameTraining( @QueryParam( "idTraining" ) int id,
                                  @QueryParam( "trainingName" ) String name)
    {
        Entity updatedTrainingObject = EntityFactory.createTraining( id, name);
        ChangeTrainingNameCommand cmd = CommandsFactory.instanciateChangeTrainingNameCmd(updatedTrainingObject);

        try
        {
            cmd.execute();
            return gson.toJson( true );
        }
        catch ( Exception e )
        {
            return gson.toJson( false );
        }

    }



    @GET
    @Path( "/displayTraining" )
    @Produces( "application/json" )
    /**
     * Metodo utilizado a traves de web service para visualizar los entrenamientos que posee el usuario
     * @param trainingId
     * @return
     */

    public String getTraining( @QueryParam( "userId" ) int userId,
            @QueryParam( "trainingId" ) int trainingId )
    {

        //    String query = "SELECT * from M06_obtenerEntrenamientos("+ userId +")";
        CheckTrainingCommand cmd = CommandsFactory.instanciateCheckTrainingCmd( trainingId, userId );


        try
        {
            cmd.execute();
            Entity returnedTraining = cmd.returnedTraining;
            return gson.toJson( true );
        }
        catch ( Exception e )
        {
            return gson.toJson( false );
        }
    }

    @GET
    @Path( "/deleteTraining" )
    @Produces( "application/json" )
    /**
     * Metodo utilizado a traves de web service para eliminar un entrenamiento
     * @param trainingId
     * @param trainingName
     * @return
     */

    public String deleteTraining( @QueryParam( "trainingId" ) int trainingId,
                                  @QueryParam( "trainingName" ) String trainingName )
    {

        Entity deleteTrainingObject = EntityFactory.createTraining(trainingId, trainingName);

        DeleteTrainingCommand cmd = CommandsFactory.instanciateDeleteTrainingCmd(deleteTrainingObject);


        try
        {
            cmd.execute();
            return gson.toJson( true );
        }
        catch ( Exception e )
        {
            return gson.toJson( false );
        }

    }



    private LinkedList<Entity> activityList (LinkedList<String> activities){
        LinkedList<Entity> activitiesList = new LinkedList<Entity>();
        for(int i = 0; i <= activities.size() - 1; i++){
            Entity act = EntityFactory.createActivity();
            if (activities.get(i).equals("Caminar")){
                act = EntityFactory.createActivity(1, activities.get(i), 2);
            }
            else if (activities.get(i).equals("Trotar")){
                act = EntityFactory.createActivity(2, activities.get(i), 1);
            }
            else if (activities.get(i).equals("Bicicleta")){
                act = EntityFactory.createActivity(3, activities.get(i), 2);
            }
            else if (activities.get(i).equals("Natacion")){
                act = EntityFactory.createActivity(4, activities.get(i), 2);
            }
            else if (activities.get(i).equals("Yoga")){
                act = EntityFactory.createActivity(5, activities.get(i), 3);
            }
            else if (activities.get(i).equals("Estiramientos")){
                act = EntityFactory.createActivity(6, activities.get(i), 1);
            }
            else if (activities.get(i).equals("Eliptica")){
                act = EntityFactory.createActivity(7, activities.get(i), 1);
            }
            else if (activities.get(i).equals("Escaleras")){
                act = EntityFactory.createActivity(8, activities.get(i), 1);
            }
            else if (activities.get(i).equals("Bailar")){
                act = EntityFactory.createActivity(9, activities.get(i), 2);
            }
            else if (activities.get(i).equals("Aerobic")){
                act = EntityFactory.createActivity(10, activities.get(i), 2);
            }
            else if (activities.get(i).equals("Remo")){
                act = EntityFactory.createActivity(11, activities.get(i), 2);
            }
            else if (activities.get(i).equals("Basketball")){
                act = EntityFactory.createActivity(12, activities.get(i), 2);
            }
            else if (activities.get(i).equals("Futbol")){
                act = EntityFactory.createActivity(13, activities.get(i), 2);
            }
            else if (activities.get(i).equals("Tenis")){
                act = EntityFactory.createActivity(14, activities.get(i), 2);
            }
            else if (activities.get(i).equals("Voleibol")){
                act = EntityFactory.createActivity(15, activities.get(i), 2);
            }
            activitiesList.add(act);
        }
        return activitiesList;
    }

    /**
     * Servicio Web que retorna el entrenamiento a detalle
     * @param trainingId entrenamiento elegido
     * @return entrenamiento
     */
    @POST
    @Path( "/getTrainingDetail" )
    @Produces( "application/json" )
    public String getTrainingDetail(@QueryParam( "trainingId" ) int trainingId )
    {

        Entity training = null, commandResult = null;
        Command command = null;
        String response = null;

        try
        {
            if ( trainingId > 0 )
            {
                training = EntityFactory.createTraining( trainingId );
            }
            else
            {
                throw new InvalidParameterException( Registry.ERROR_PARAM_WS );

            }

            command = CommandsFactory.instanciateGetTrainingDetailCmd( training );
            command.execute();

            commandResult =  ( ( GetTrainingDetailCommand ) command ).get_output();
            commandResult.set_errorCode( Registry.RESULT_CODE_OK );
            response = gson.toJson( commandResult );
        }
        catch ( ListByIdException e )
        {
            commandResult.set_errorCode( e.ERROR_CODE );
            commandResult.set_errorMsg( e.ERROR_MSG );
            response = gson.toJson( commandResult );

            logger.error( "Metodo: {} {}", "getTrainingDetail", e.toString() );
        }
        catch( Exception e )
        {
            commandResult.set_errorCode( Registry.RESULT_CODE_FAIL );
            commandResult.set_errorMsg( Registry.RESULT_CODE_FAIL_MSG );
            response = gson.toJson( commandResult );

            logger.error( "Metodo: {} {}", "getTrainingDetail", e.toString() );
        }

        return response;

    }

    /**
     * Servicio Web para mostrar todos los entrenamientos
     * @param userId
     * @return lista de entrenamientos
     */
    @POST
    @Path( "/getAllTraining" )
    @Produces( "application/json" )
    public String getAllTraining( @QueryParam( "userId" ) int userId )
    {
        Entity training, cmdResult= null;
        List<Entity> commandResult = null;
        Command command;
        String response = null;

        try
        {
            if ( userId > 0 )
            {
                training = EntityFactory.createTraining( userId );
            }
            else
            {
                throw new InvalidParameterException( Registry.ERROR_PARAM_WS );
            }

            command = CommandsFactory.instanciateGetAllTrainingCmd( training );
            command.execute();

            commandResult =  ( ( GetAllTrainingCommand ) command ).get_output();
            //cmdResult.set_errorCode( Registry.RESULT_CODE_OK );
            response = gson.toJson( commandResult );
        }
        catch ( ListAllException e )
        {
            cmdResult.set_errorCode( e.ERROR_CODE );
            cmdResult.set_errorMsg( e.ERROR_MSG );
            commandResult.add(cmdResult);

            response = gson.toJson( commandResult );

            logger.error( "Metodo: {} {}", "getAllTraining", e.toString() );
        }
        catch ( Exception e )
        {
            cmdResult.set_errorCode( Registry.RESULT_CODE_FAIL );
            cmdResult.set_errorMsg( Registry.RESULT_CODE_FAIL_MSG );
            commandResult.add(cmdResult);
            response = gson.toJson( commandResult );

            logger.error( "Metodo: {} {}", "getAllTraining", e.toString() );
        }

        return response;
    }
}
