package edu.ucab.desarrollo.fitucab.dataAccessLayer.M01;

import com.google.gson.Gson;
import edu.ucab.desarrollo.fitucab.common.Registry;
import edu.ucab.desarrollo.fitucab.common.entities.Entity;
import edu.ucab.desarrollo.fitucab.common.entities.EntityFactory;
import edu.ucab.desarrollo.fitucab.common.entities.User;
import edu.ucab.desarrollo.fitucab.common.exceptions.BdConnectException;
import edu.ucab.desarrollo.fitucab.common.exceptions.M02.CreateHomeException;
import edu.ucab.desarrollo.fitucab.common.exceptions.M02.GetUserException;
import edu.ucab.desarrollo.fitucab.common.exceptions.MessageException;
import edu.ucab.desarrollo.fitucab.dataAccessLayer.Dao;
import edu.ucab.desarrollo.fitucab.dataAccessLayer.M02.DaoHome;
import edu.ucab.desarrollo.fitucab.dataAccessLayer.Security;
import org.slf4j.LoggerFactory;

import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.sql.*;
import java.util.Properties;


/**
 * Created by karo on 24/06/17.
 */
public class DaoUser extends Dao implements IDaoUser {
    //Conexion con la base de datos

    private int RESULT_CODE_OK = 200;
    private int RESULT_CODE_FAIL = 300;
    private int RESULT_USER_FAIL = 400;
    private int RESULT_EMAIL_OK = 500;
    int _id;
    String _username;
    String _phone;
    String _email;
    Entity _user;
    Date birthdate;
    private GetUserException _error;
    private Connection _bdCon;
    //Encargado de encriptar la contraseña
    private Security _sc;

    //String de conexion funciones
    String _sqlInicioSesion = "{?=call M01_INICIARSESION(?,?)}";
    String _sqlLastUser = "{?=call M01_LASTUSER()}";
    String _sqlRegistrarUsuario = "{?=call M01_REGISTRAR(?,?,?,?,?,?,?,?)}";

    //String de conexion procedimientos
    String _sqlRecoveryPassword = "{ call M01_RECUPERARPWD(?,?,?)}";
    String _sqlRegistrarUsuario1 = "{ call M01_REGISTRAR(?,?,?,?,?,?,?,?)}";



    Gson gson = new Gson();

    private static org.slf4j.Logger _logger = LoggerFactory
            .getLogger(DaoUser.class);

    public DaoUser(int _id) {
        this._id = _id;
    }

    public DaoUser(int id, String username, String phone, String email) {
        this._id = id;
        this._username = username;
        this._phone = phone;
        this._email = email;
    }
    public DaoUser(Entity _user) {
        this._user = _user;
        try {
            _bdCon = Dao.getBdConnect();
        } catch (BdConnectException e) {
            MessageException error = new MessageException(e, this.getClass().getSimpleName(),
                    Thread.currentThread().getStackTrace()[1].getMethodName());
            _logger.error("Error: ", error.toString());
        } catch (Exception e) {
            MessageException error = new MessageException(e, this.getClass().getSimpleName(),
                    Thread.currentThread().getStackTrace()[1].getMethodName());
            _logger.error("Error: ", error.toString());
        }
    }

    public DaoUser() {
        try {
            _bdCon = Dao.getBdConnect();
        } catch (BdConnectException e) {
            MessageException error = new MessageException(e, this.getClass().getSimpleName(),
                    Thread.currentThread().getStackTrace()[1].getMethodName());
            _logger.error("Error: ", error.toString());
        } catch (Exception e) {
            MessageException error = new MessageException(e, this.getClass().getSimpleName(),
                    Thread.currentThread().getStackTrace()[1].getMethodName());
            _logger.error("Error: ", error.toString());
        }
    }

    /**
     * Metodo que consulta el perfil de usuario por un query de un Stored Procedure
     * @param _id _id que identifica al Usuario a buscar en la BD
     * @throws GetUserException Exepcion personalizada del M02
     * @return _user entidad User
     */
    @Override
    public Entity read(int _id) throws GetUserException {
        try {
            _bdCon = Dao.getBdConnect();
            Statement st = _bdCon.createStatement();
            ResultSet _result = st.executeQuery("select * from m02_consultarperfilid("+ _id +")");

            while(_result.next()){
                String _usuario = _result.getString( "usuario" );
                String _email = _result.getString( "email" );
                String _sex = _result.getString( "sex" );
                String _phone = _result.getString( "phone" );
                Date _birthdate = _result.getDate( "birthdate" );
                int  _height = _result.getInt( "height" );
                int _weight = _result.getInt("weight");
                _user = EntityFactory.createUser( _id ,_usuario, _email, _sex, _phone, _birthdate, _height, _weight);
            }
            return _user;
        } catch (NullPointerException e) {
            _error = new GetUserException(e, DaoHome.class.getSimpleName(),BdConnectException.class.toString());
            _logger.debug("Debug: ", _error.toString());
            _logger.error("Error: ", _error.toString());
            throw _error;
        } catch (SQLException e) {
            _error = new GetUserException(e, DaoHome.class.getSimpleName(),BdConnectException.class.toString());
            _logger.debug("Debug: ", _error.toString());
            _logger.error("Error: ", _error.toString());
            throw _error;
        }
        catch (Exception e) {
            _error = new GetUserException(e, DaoHome.class.getSimpleName(),BdConnectException.class.toString());
            _logger.debug("Debug: ", _error.toString());
            _logger.error("Error: ", _error.toString());
            throw _error;
        }finally {
            Dao.closeConnection();
        }
    }

    /**
     * Devuelve el usuario que esté registrado
     *
     * @param e
     * @return
     */

    public Entity login(Entity e) throws SQLException {
        _sc = new Security();

        CallableStatement cstmt;

        User _user = (User) e;

        String password = _sc.encryptPassword(_user.getPassword());

        try {
            cstmt = _bdCon.prepareCall(_sqlInicioSesion.toString());

            //1er signo de interrogacion el parametro de salida
            cstmt.registerOutParameter(1, Types.INTEGER);

            //2do y 3er signo de interrogacion parametros de entrada
            cstmt.setString(2, _user.getUser());
            cstmt.setString(3, password);

            cstmt.execute();

            int id = cstmt.getInt(1);
            System.out.printf(String.valueOf(id));

            _user.setId(id);
            return _user;

        } catch (SQLException ex) {
            MessageException error = new MessageException(ex, this.getClass().getSimpleName(),
                    Thread.currentThread().getStackTrace()[1].getMethodName());
            _logger.debug("Debug: ", error.toString());
            _logger.error("Error: ", error.toString());

            //Retorna null por el error
            return null;
        } catch (Exception ex) {
            MessageException error = new MessageException(ex, this.getClass().getSimpleName(),
                    Thread.currentThread().getStackTrace()[1].getMethodName());
            _logger.debug("Debug: ", error.toString());
            _logger.error("Error: ", error.toString());
            return null;
        } finally {
            _bdCon.close();
        }
    }


    /**
     * Metodo que es llamado a traves del web service para agregar a la base de datos
     * los parametros recibidos
     *
     * @return
     */

    @Override
    public Entity create(Entity e) throws Exception {

        _sc = new Security();

        User _user = (User) e;

        String password = _sc.encryptPassword(_user.getPassword());

        CallableStatement cstmt, cs;


        try {
            cstmt = _bdCon.prepareCall(_sqlRegistrarUsuario.toString());
            //Parametro de salida
            cstmt.registerOutParameter(1, Types.INTEGER);

            //Parametros de entrada
            cstmt.setString(2, _user.getUser());
            cstmt.setString(3, password);
            cstmt.setString(4, _user.getEmail());
            cstmt.setString(5, _user.getSex());
            cstmt.setString(6, _user.getPhone());
            cstmt.setDate(7, _user.getBirthdate());
            cstmt.setInt(8, _user.getWeight());
            cstmt.setInt(9, _user.getHeight());
            cstmt.execute();

            int id = cstmt.getInt(1);
            _user.setId(id);

            return _user;

        } catch (SQLException ex) {
            MessageException error = new MessageException(ex, this.getClass().getSimpleName(),
                    Thread.currentThread().getStackTrace()[1].getMethodName());
            _logger.debug("Debug: ", error.toString());
            _logger.error("Error: ", error.toString());

            //Retorna null por el error
            return null;
        } catch (Exception ex) {
            MessageException error = new MessageException(ex, this.getClass().getSimpleName(),
                    Thread.currentThread().getStackTrace()[1].getMethodName());
            _logger.debug("Debug: ", error.toString());
            _logger.error("Error: ", error.toString());
            return null;
        } finally {
            _bdCon.close();
        }
    }

    /**
     * Metodo del M02 para actualizar atributos de la Entidad User
     * @author Juan Macedo, Cesar Boza, Bryan Teixeira
     */
    public boolean Update() {
        try {
            if (!_username.equals("")) {
                UpdateName(_username);
            }
            if (!_email.equals("")) {
                UpdateEmail(_email);
            }
            if (!_phone.equals("")) {
                UpdatePhone(_phone);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (BdConnectException e) {
            e.printStackTrace();
        }
        return true;
    }

    /**
     * Metodo del M02 para actualizar el username de un usuario
     * @author Juan Macedo, Cesar Boza, Bryan Teixeira
     * @param _name
     */
    public void UpdateName(String _name) throws BdConnectException, SQLException {
        String updatename = _name;
        try {
            _bdCon = Dao.getBdConnect();
            Statement _st = _bdCon.createStatement();
            ResultSet _result = _st.executeQuery("select m02_modperfilname(" + _id + ", '" + updatename + "')");

        }catch (SQLException e) {
            e.printStackTrace();
        } catch (BdConnectException e) {
            e.printStackTrace();
        }

    }

    /**
     * Metodo del M02 para actualizar el email de un usuario
     * @author Juan Macedo, Cesar Boza, Bryan Teixeira
     * @param _email
     */
    public void UpdateEmail(String _email) {
        String updatemail = _email;
        try {
            _bdCon = Dao.getBdConnect();
            Statement _st = _bdCon.createStatement();
            ResultSet _result = _st.executeQuery("select m02_modperfilmail(" + _id + ", '" + updatemail + "')");

        } catch (SQLException e1) {
            e1.printStackTrace();
        } catch (BdConnectException e1) {
            e1.printStackTrace();
        }
    }

    /**
     * Metodo del M02 para actualizar el numero telefonico de un usuario
     * @author Juan Macedo, Cesar Boza, Bryan Teixeira
     * @param _phone
     */
    public void UpdatePhone(String _phone) {
        String updatephone = _phone;
        try {
            _bdCon = Dao.getBdConnect();
            Statement _st = _bdCon.createStatement();
            ResultSet _result = _st.executeQuery("select m02_modperfilphone(" + _id + ", '" + updatephone + "')");

        } catch (SQLException e1) {
            e1.printStackTrace();
        } catch (BdConnectException e1) {
            e1.printStackTrace();
        }
    }

    /**
     * Sevicio Web para poder enviar el correo al usuario con su password
     *
     * @return por ahora retorna un String
     */
    @Override
    public String testEmail(String email) throws SQLException {
        User user = null;
        Boolean validaEmail = false;
        String usernameResult = "";
        String passwordResult = "";

        try {

            Properties props = new Properties();
            props.put("mail.smtp.auth", "true");
            props.put("mail.smtp.starttls.enable", "true");
            props.put("mail.smtp.host", "smtp.gmail.com");
            props.put("mail.smtp.port", "587");

            //Se traen los datos del usuario de la base de datos.
            CallableStatement cstmt;
            cstmt = _bdCon.prepareCall(_sqlRecoveryPassword);
            cstmt.registerOutParameter(1, Types.VARCHAR);
            cstmt.registerOutParameter(2, Types.VARCHAR);
            cstmt.setString(3, email);

            cstmt.execute();

            validaEmail = true;
            if (validaEmail == true) {
                usernameResult = cstmt.getString(1);
                passwordResult = cstmt.getString(2);
                passwordResult = _sc.decryptPassword(passwordResult);
                System.out.print("Debug: user " + usernameResult);
                //Se crea la sesion para autenticar

                Session session = Session.getInstance(props,
                        new javax.mail.Authenticator() {
                            protected PasswordAuthentication getPasswordAuthentication() {
                                return new PasswordAuthentication(Registry.RECOVERY_EMAIL_USERNAME,
                                        Registry.RECOVERY_EMAIL_PASS);
                            }
                        });

                //creamos un objeto MIME
                javax.mail.Message message = new MimeMessage(session);
                //ponemos el remitente
                message.setFrom(new InternetAddress("fitucabprueba2@gmail.com"));
                message.setRecipients(javax.mail.Message.RecipientType.TO,
                        //aqui va el destinatario
                        InternetAddress.parse(email));
                //El tema del correo
                message.setSubject("Recuperar contraseña FitUCAB");
                //El contenido del correo
                message.setText(" Hola FitUcabista! " +
                        " tu usuario es: " + usernameResult +
                        " y tu clave:" + passwordResult + " " +
                        " Ahora puedes seguir entrenando");
                //Enviamos
                Transport.send(message);
                //Aqui esta la validacion
                User userOk = new User();
                userOk.set_status(Integer.toString(RESULT_EMAIL_OK));
                return gson.toJson(userOk);
            } else {
                System.out.print("Debug: user " + usernameResult);
                User userFail = new User();
                userFail.set_status(Integer.toString(RESULT_USER_FAIL));
                return gson.toJson(userFail);
            }

        } catch (SQLException e) {
            MessageException error = new MessageException(e, this.getClass().getSimpleName(),
                    Thread.currentThread().getStackTrace()[1].getMethodName());
            _logger.error("Error: ", error.toString());
            System.out.print("Error: " + error.toString());
            return e.getSQLState();
        } catch (Exception e) {
            MessageException error = new MessageException(e, this.getClass().getSimpleName(),
                    Thread.currentThread().getStackTrace()[1].getMethodName());
            _logger.error("Error: ", error.toString());
            System.out.print("Error: " + error.toString());
            return e.getMessage();
        } finally {
            _bdCon.close();
        }
    }

    public Entity update(Entity e) {
        return null;
    }

    @Override
    public Entity read(Entity e) throws CreateHomeException, SQLException, BdConnectException {
        return null;
    }



}