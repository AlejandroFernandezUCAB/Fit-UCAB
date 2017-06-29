package edu.ucab.desarrollo.fitucab.common.entities;

import edu.ucab.desarrollo.fitucab.common.exceptions.ListAllException;
import edu.ucab.desarrollo.fitucab.common.exceptions.ListByIdException;
import edu.ucab.desarrollo.fitucab.domainLogicLayer.Command;

/**
 * Actividad que
 * @author David Garcia, Juan Mendez, Mario Salazar
 * @version 2.0
 */
public class Active extends Entity {
    private Command _command;

    public Active(Command command) {
        _command = command;
    }

    public void exec() throws ListByIdException, ListAllException {
        _command.execute();
    }
}
