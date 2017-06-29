package edu.ucab.desarrollo.fitucab.webService;

/**
 * Created by elberg on 28/06/17.
 */

import com.google.gson.Gson;
import edu.ucab.desarrollo.fitucab.common.entities.Entity;
import edu.ucab.desarrollo.fitucab.common.entities.EntityFactory;
import edu.ucab.desarrollo.fitucab.common.entities.User;
import edu.ucab.desarrollo.fitucab.dataAccessLayer.M01.DaoUser;
import edu.ucab.desarrollo.fitucab.domainLogicLayer.CommandsFactory;
import edu.ucab.desarrollo.fitucab.domainLogicLayer.M01.CreateUserCommand;
import edu.ucab.desarrollo.fitucab.domainLogicLayer.M01.RecoverPasswordCommand;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

import static edu.ucab.desarrollo.fitucab.webService.Sql.getConInstance;

/**
 * Clase de Servicios Web del Modulo 01
 */
@Path("/M01_ServicesUser1")
public class M01_ServicesUser1 {
    Gson gson = new Gson();

    public M01_ServicesUser1() {}

    /**
     * Metodo que es llamado a traves del web service para consultar un usuario
     * existente en la base de datos
     * @return el usuario con los datos que trae la consulta
     */
    @GET
    @Path("/login_user")
    @Produces("application/json")
    public String getUser(@QueryParam("username") String username,
                          @QueryParam("password") String password)
    {
        return null ;
    }


    /**
     * Metodo para registrar al usuario
     * @param username
     * @param password
     * @param email
     * @param sex
     * @param phone
     * @param birthdate
     * @param weight
     * @param height
     * @return
     */

    @GET
    @Path("/insertRegistry")
    @Produces("application/json")
    public String insertUser(@QueryParam("username") String username,
                             @QueryParam("password") String password,
                             @QueryParam("email") String email,
                             @QueryParam("sex") String sex,
                             @QueryParam("phone") String phone,
                             @QueryParam("birthdate") java.sql.Date birthdate,
                             @QueryParam("weight") int weight,
                             @QueryParam("height") int height) {


        Entity createUserObject = EntityFactory.createUser(username, password, email,
                                                 sex, phone, birthdate, weight, height);

        Logger.getLogger(M01_ServicesUser1.class.getName()).info("EL USUARIO ES " + username);


        CreateUserCommand cmd = CommandsFactory.instanciateCreateUserCmd(createUserObject);

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

    /***
     * Metodo que con el email recuperas tu usuario y contraseña
     * @param email
     * @return
     */
    @GET
    @Path("/userView")
    @Produces("application/json")
    public String userOnly(@QueryParam("email") String email)
    {
        //Duda sobre si es necesario crear el objeto recupUserPswObject
        
        Entity recupUserPswObject = EntityFactory.createUser(email);
        RecoverPasswordCommand cmd = CommandsFactory.instanciateRecoverPasswordCmd(email);

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

}