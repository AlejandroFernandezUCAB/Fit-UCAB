
import edu.ucab.desarrollo.fitucab.common.entities.Entity;
import edu.ucab.desarrollo.fitucab.common.entities.EntityFactory;
import edu.ucab.desarrollo.fitucab.dataAccessLayer.Dao;
import edu.ucab.desarrollo.fitucab.dataAccessLayer.DaoFactory;
import edu.ucab.desarrollo.fitucab.dataAccessLayer.M01.DaoUser;
import edu.ucab.desarrollo.fitucab.dataAccessLayer.Security;
import edu.ucab.desarrollo.fitucab.domainLogicLayer.Command;
import edu.ucab.desarrollo.fitucab.domainLogicLayer.CommandsFactory;
import edu.ucab.desarrollo.fitucab.domainLogicLayer.M01.CreateUserCommand;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.jboss.shrinkwrap.api.spec.JavaArchive;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.Date;
import java.sql.Types;

import static org.junit.Assert.*;

/**
 * Created by elberg on 02/07/17.
 */

public class DaoUserTest {
    static Connection conn;
    static Dao _dao;
    static Entity _user;

    @Test
    public void login() throws Exception {
        conn = _dao.getConInstance();
        _user = EntityFactory.createUser(60,"naomi","123",
                "@gmail","f","00000",
                Date.valueOf("2010-12-12"),
                12,12);

        DaoUser LoginUserDao = (DaoUser) DaoFactory.instanciateDaoUser(_user);
        Entity _userReturn = LoginUserDao.login(_user);



        conn.close();
    }

    @Test
    public void create() throws Exception {
        conn = _dao.getConInstance();
        _user = EntityFactory.createUser(60,"naomi","123",
                "@gmail","f","00000",
                Date.valueOf("2010-12-12"),
                12,12);

        Command _command = CommandsFactory.instanciateCreateUserCmd(_user);
        CreateUserCommand cmd = (CreateUserCommand) _command;
        cmd.execute();


        //Se trae el ultimo usuario registrado
        CallableStatement cstmt =conn.prepareCall("{?=call M01_LASTUSER()}");
        cstmt.registerOutParameter(1, Types.INTEGER);
        cstmt.execute();

        int id_insert = cstmt.getInt(1);


        //Busca el usuario que fue registrado

        Security _sc = new Security();
        String password =_sc.encryptPassword(String.valueOf(123));

        CallableStatement cs = conn.prepareCall("{?=call M01_INICIARSESION(?,?)}");
        cs.registerOutParameter(1, Types.INTEGER);
        cs.setString(2, "naomi");
        cs.setString(3, password);
        cs.execute();

        int id_login = cs.getInt(1);

        //Elimina la insercion
        CallableStatement cst = conn.prepareCall("{?=call M01_ELIMINARUSER(?)}");
        cst.registerOutParameter(1, Types.INTEGER);
        cst.setString(2, "naomi");
        cst.execute();
        conn.close();

        assertEquals(id_insert,id_login);
    }

    @Test
    @Ignore
    public void testEmail() throws Exception {
    }


}