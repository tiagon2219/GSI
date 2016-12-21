/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.comm;

import java.io.Serializable;

/**
 *
 * @author Tiago
 */
public class Message implements Serializable{
    
    public String command;
    public String msg;
    public Object object;
    public boolean correct;
    public long id;
    public boolean logedIn;
    
}
