package com.fitucab.ds1617b.fitucab.Helper;

/**
 * Esta clase será d edonde se va sacar el ip a donde se va a conectar
 * Created by Alejandro Fernandez on 26/5/2017.
 * Absoultamente TODOS debemos usar esta clase para al momento de ejecutar se tenga el mismo string
 */
 *

public class IpStringConnection {
    private String _ip = "http://190.74.250.81:8888/PruebaDeServicioWeb_war_exploded/";


    public IpStringConnection() {

        this._ip = "http://190.74.250.81:8888/PruebaDeServicioWeb_war_exploded/";

    }

    public String getIp() {


        return _ip;
    }

}
