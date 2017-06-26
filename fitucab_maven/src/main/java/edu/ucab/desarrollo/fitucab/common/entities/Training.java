package edu.ucab.desarrollo.fitucab.common.entities;

import java.security.InvalidParameterException;
import java.util.ArrayList;
import java.util.LinkedList;

/**
 * Clase Entrenamiento M06
 */
public class Training extends Entity {

    private String _trainingName;
    private int _trainingPeriod;
    private ArrayList<Entity> _activitylist;

    /**
     * Constructor vacio
     */
    public Training() {}

    /**
     * Constructor con id
     * @param id identificador
     * @param trainingName nombre del entrenamiento
     * @param trainingPeriod hora(s) que puede durar la actividad que incluye en el entrenamiento
     * @param activitylist lista de actividades que tiene el entrenamiento
     */
    public Training(int id, String trainingName, int trainingPeriod, LinkedList activitylist) {
        this._id = id;
        this._trainingName = trainingName;
        this._trainingPeriod = trainingPeriod;
    }

    /**
     * Constructor sin id
     * @param trainingName nombre del entrenamiento
     * @param trainingPeriod hora(s) que puede durar la actividad que incluye en el entrenamiento
     * @param activitylist lista de actividades que tiene el entrenamiento
     */
    public Training(String trainingName, int trainingPeriod, LinkedList activitylist) {
        this._trainingName = trainingName;
        this._trainingPeriod = trainingPeriod;
    }

    /**
     * Contructor con nombre y periodo
     * @param _trainingName
     * @param _trainingPeriod
     */
    public Training(String _trainingName, int _trainingPeriod)
    {

        this._trainingName = _trainingName;
        this._trainingPeriod = _trainingPeriod;
    }

    /**
     * Contructor sin la lista de actividades
     * @param _id
     * @param _trainingName
     * @param _trainingPeriod
     */
    public Training(int _id, String _trainingName, int _trainingPeriod)
    {
        super(_id);
        this._trainingName = _trainingName;
        this._trainingPeriod = _trainingPeriod;
    }
    /**
     * constructor para instanciar solo con el parametro id
     * @param userId el identificador de base de datos
     *
     */
    public Training(int userId)
    {
        set_id( userId );
    }

    /**
     * Contructor con id y nombre
     * @param id
     * @param name
     */
    public Training( int id, String name )
    {
        set_id( id );

        setTrainingName( name );

    }

    //Getters and setters basicos

    public String getTrainingName() {
        return _trainingName;
    }

    public void setTrainingName(String trainingName)
    {
        if ( trainingName.equals( "" ) )
            new InvalidParameterException( "mensaje" );

        _trainingName = trainingName;
    }

    public int getTrainingPeriod() {
        return _trainingPeriod;
    }

    public void setTrainingPeriod(int trainingPeriod) {
        this._trainingPeriod = trainingPeriod;
    }

    public ArrayList<Entity> get_activitylist()
    {
        return _activitylist;
    }

    public void set_activitylist(ArrayList<Entity> _activitylist)
    {
        this._activitylist = _activitylist;
    }
}