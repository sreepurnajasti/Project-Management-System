/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package InternetConnectivity;

import java.net.Authenticator;
import java.net.HttpURLConnection;
import java.net.PasswordAuthentication;
import java.net.URL;

/**
 *
 * @author SreePurna
 */
public class InternetConnectivity {
    public boolean getInternetStatus(){
        try{
            final String authUser = "201585001";
            final String authPassword = "msit123";
            Authenticator.setDefault(
                new Authenticator() {
                    public PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(
                            authUser, authPassword.toCharArray());
                    }
                }
            );
            System.setProperty("http.proxyUser", authUser);
            System.setProperty("http.proxyPassword", authPassword);
            System.setProperty("http.proxyHost", "10.10.10.3");
            System.setProperty("http.proxyPort", "3128");
            URL url = new URL("http://www.google.co.in");
            System.out.println(url.getHost());
            HttpURLConnection con = (HttpURLConnection)url.openConnection();
            con.connect();
            System.out.println("Response code is " + con.getResponseCode());
            if(con.getResponseCode() == 200|| con.getResponseCode() == 407 ||con.getResponseCode() == 503 ){
                return true;
            }

        }catch(Exception e){}
    return false;
    }
}
