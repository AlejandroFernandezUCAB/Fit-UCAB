package edu.ucab.desarrollo.fitucab.domainLogicLayer.M07;

import edu.ucab.desarrollo.fitucab.common.entities.Entity;
import edu.ucab.desarrollo.fitucab.common.exceptions.ListAllException;
import edu.ucab.desarrollo.fitucab.common.exceptions.ListByIdException;
import edu.ucab.desarrollo.fitucab.dataAccessLayer.DaoFactory;
import edu.ucab.desarrollo.fitucab.dataAccessLayer.M07.DaoPlanification;
import edu.ucab.desarrollo.fitucab.domainLogicLayer.Command;

/**
 * Created by jaorr on 30/06/17.
 */
public class GetPlanificationByIdCommand extends Command {

    private Entity _planificationEntity;

    public GetPlanificationByIdCommand(Entity planificationEntity) {
        this._planificationEntity = planificationEntity;
    }

    public void execute() throws ListAllException, ListByIdException, NoSuchMethodException {
        //invocar metodo que busca por id
        DaoPlanification dao = DaoFactory.instanciateDaoPlanification();
        try {
            _planificationEntity = dao.read(_planificationEntity);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}