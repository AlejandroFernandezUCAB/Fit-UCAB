package com.fitucab.ds1617b.fitucab.Helper;

import android.content.Context;
import android.widget.Toast;

import com.fitucab.ds1617b.fitucab.Model.User;

/**
 * Created by root on 28/05/17.
 */

public class M01Util {
    private static String M01_ERROR_CONNECTION = "failed to connect to ";
    private static String M01_ERROR_CONNECTION_MESSAGE = "Revise su conexion a internet o la ip del servidor";
    private static String M01_ERROR_CONNECTION_FALSE = "Ocurrio un error inesperdado, intente de nuevo";
    private final static String  STATUS_OK="200";
    private final static String  STATUS_DUPLICATED_KEY="23505";
    private final static String  STATUS_FAIL="300";
    private final static String  STATUS_FAIL_DB="400";
    private final static String  STATUS_EMAIL_OK="500";
    private static String REGISTRY_OK_MESSAGE="Bienvenido a FitUCAB";
    private static String DUPLICATED_KEY_MESSAGE="Este username y/o email ya esta registrado";
    private static String REGISTRY_FAIL_MESSAGE="Ocurrio un error inesperado, intente de nuevo";
    private static String LOGIN_FAIL_MESSAGE ="Este username y/o contrasena son incorrectos";
    private static String RESTORE_MESSAGE="El correo con sus datos fue enviado";

    public static String validateExceptionMessage(String t){

        String[] m01ArrayString = t.split("/");
        if (m01ArrayString[0].equalsIgnoreCase(M01_ERROR_CONNECTION)){
            return M01_ERROR_CONNECTION_MESSAGE;
        }
        else{
            return M01_ERROR_CONNECTION_FALSE;
        }
     }

     public static void showToast (Context context, String message){

         Toast.makeText(context,message, Toast.LENGTH_SHORT).show();
     }

     public static boolean checkInsertResponse(User user, Context context){

         switch (user.get_status()){

             case STATUS_OK :
                 showToast(context,REGISTRY_OK_MESSAGE);
                 return true;
             case  STATUS_FAIL:
                 showToast(context,REGISTRY_FAIL_MESSAGE);
                 return false;
             case STATUS_DUPLICATED_KEY:
                 showToast(context,DUPLICATED_KEY_MESSAGE);
                 return false;
             case STATUS_FAIL_DB:
                 showToast(context, LOGIN_FAIL_MESSAGE);
                 return false;
             case STATUS_EMAIL_OK:
                 showToast(context, RESTORE_MESSAGE);
                 return false;
             default:
                 showToast(context,REGISTRY_FAIL_MESSAGE);
                 return false;
         }
     }
}