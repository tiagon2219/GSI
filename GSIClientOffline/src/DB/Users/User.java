/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DB.Users;

import java.io.Serializable;

/**
 *
 * @author Tiago
 */
public class User implements Serializable {
    
    public String username;
    public String password;
    public long id;
    
    public User(String user, String pass, long _id){
        username = user;
        password = pass;
        id = _id;
    }
    
}
