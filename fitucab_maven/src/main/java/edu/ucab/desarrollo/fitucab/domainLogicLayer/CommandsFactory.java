package edu.ucab.desarrollo.fitucab.domainLogicLayer;

import edu.ucab.desarrollo.fitucab.common.entities.Entity;
import edu.ucab.desarrollo.fitucab.domainLogicLayer.M01.RecoverPasswordCommand;
import edu.ucab.desarrollo.fitucab.domainLogicLayer.M06.*;
import edu.ucab.desarrollo.fitucab.domainLogicLayer.M01.CheckUserCommand;
import edu.ucab.desarrollo.fitucab.domainLogicLayer.M01.CreateUserCommand;
import edu.ucab.desarrollo.fitucab.domainLogicLayer.M06.CheckTrainingCommand;
import edu.ucab.desarrollo.fitucab.domainLogicLayer.M06.CreateTrainingCommand;
import edu.ucab.desarrollo.fitucab.domainLogicLayer.M06.UpdateTrainingCommand;
import edu.ucab.desarrollo.fitucab.domainLogicLayer.M09.AchieveChallengeCommand;
import edu.ucab.desarrollo.fitucab.domainLogicLayer.M09.FillChartCommand;
import edu.ucab.desarrollo.fitucab.domainLogicLayer.M09.LevelUpCommand;
import edu.ucab.desarrollo.fitucab.domainLogicLayer.M09.ScoreCommand;

import java.util.ArrayList;
import java.util.List;

/**
 * Fabrica de comandos
 */
public class CommandsFactory {

    // Comandos LOGIN M01

    /**
     * Intancia del CreateUserCommad
     * @param user Objeto con todos los parametros de User
     * @return CreateUserCommad con parametro de devolucion user
     */

    static public CreateUserCommand instanciateCreateUserCmd(Entity user){
        return new CreateUserCommand(user);
    }

    /**
     *  Intancia del RecoverPasswordCommand
     * @param email String
     * @return RecoverPasswordCommand con parametro email para la recuperacion de la cuenta
     */

    static public RecoverPasswordCommand instanciateRecoverPasswordCmd(String email){
        return new RecoverPasswordCommand(email);
    }

    /**
     *  Intancia del CheckUserCommand
     * @param user String de username
     * @param password String contraseña
     * @return CheckUserCommand para vericar el registro del usuario
     */
    static public CheckUserCommand instanciateCheckUserCmd(String user, String password){
        return new CheckUserCommand(user, password);
    }


    // Comandos M06

    static public CreateTrainingCommand instanciateCreateTrainingCmd(Entity training, int userId){
        return new CreateTrainingCommand(training, userId);

    }

    static public UpdateTrainingCommand instanciateUpdateTrainingCmd(Entity training){
        return new UpdateTrainingCommand(training);

    }
    static public CheckTrainingCommand instanciateCheckTrainingCmd(int trainingId, int userId) {

        return new CheckTrainingCommand(trainingId, userId);
    }

    /**
     * Metodo para instanciar el comando GetAllTraining
     * @param training
     * @return el comando GetAllTraining
     */
    public static Command instanciateGetAllTrainingCmd( Entity training )
    {
        return new GetAllTrainingCommand(training);
    }

    /**
     * Metodo para instanciar el comando GetTrainingDetail
     * @param training
     * @return el comando GetTrainingDetail
     */
    public static Command instanciateGetTrainingDetailCmd (Entity training){
        return new GetTrainingDetailCommand(training);
    }

    //Modulo 9
    static public AchieveChallengeCommand instanciateAchieveChallengeCmd(int id){
        return new AchieveChallengeCommand(id);
    }

    static public ScoreCommand instanciateScoreCmd(int id){
        return new ScoreCommand(id);
    }

    static public FillChartCommand instanciateFillChartCmd(int id){
        return new FillChartCommand(id);
    }

    static public LevelUpCommand instanciateLevelUpCmd(int id){
        return new LevelUpCommand(id);
    }

    static  public List<Entity> getChallenges(){return new ArrayList<Entity>();}
    //Fin Modulo 9


}

