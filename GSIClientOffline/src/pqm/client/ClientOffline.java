/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pqm.client;

import DB.Users.*;
import Documents.*;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.logging.Level;
import java.util.logging.Logger;
import net.comm.Message;


/**
 *
 * @author Tiago
 */
public class ClientOffline {
    
    public long id = -1;
    public Documento docActual = null;
    public boolean editing = false;
    
    public ClientOffline(){
        
    }
    
    public void work(int option, String ... args){
        if(option == 1){ /*registar user*/
            String msg = "";
            for(String arg: args)
                msg += arg+" ";
            String[] temp = msg.split(" ");
            registar(temp[0], temp[1]);   
        }
        else if(option == 2){
            Users users = (Users)deserializeAdd(1);
            String msg = "";
            for(String arg:args)
                msg += arg+" ";
            String[] temp = msg.split(" ");
            login(temp[0],temp[1]);
        }
        else if(option == 3){
            
        }
        else if(option == 4){
        }
        else if(option == 5){
            String msg = "";
            for(String arg:args)
                msg += arg;
            getDocumento(msg);
        }
        else if(option == 6){
            updateDocs(docActual);
        }
    }
    
    public void registar(String user, String password){
        System.out.println("registar "+user+" "+password);
        Users _users = (Users) deserializeAdd(1);
        if(_users == null){
            System.out.println("Nao existe BD de utilizadores");
            User newUser = new User(user, password, 1);
            Users newUsers = new Users();
            newUsers.userList.add(newUser);
            serialize(newUsers,1);
        }
        
        for(int i=0; i<_users.userList.size(); i++){
            if(_users.userList.get(i).username.equals(user)){
                System.out.println("O utilisador já existe");
                return;
            }
        }
        User newUser = new User(user, password, (long)(_users.userList.get(_users.userList.size()-1).id+1));
        _users.userList.add(newUser);
        serialize(_users,1);
    }
    
    
    public void login(String user, String password){
        
        Users users = (Users)deserializeAdd(1);
        if(users == null){
            System.out.println("Nao existem utilizadores");
            return;
        }
        
        for(int i=0; i<users.userList.size(); i++){
            if(users.userList.get(i).username.equals(user)){
                if(users.userList.get(i).password.equals(password)){
                    System.out.println("Login-> "+user+" "+password+" | "+users.userList.get(i).username+" "+users.userList.get(i).password);
                    id = users.userList.get(i).id;
                    return;
                }
            }
        }
    }
    
    public void getDocumento(String msg){
        Documentos docs = (Documentos)deserializeAdd(2);
        
        for(int i=0; i<docs.documentsList.size(); i++){
            if(docs.documentsList.get(i).title.equals(msg)){
                docActual = docs.documentsList.get(i);
                return;
            }
        }
    }
    
    public static Documentos updateDocs(Documento doc){
        Documentos docs = (Documentos)deserializeAdd(2);
        for(int i=0; i<docs.documentsList.size(); i++){
            if(docs.documentsList.get(i).title.equals(doc.title)){
                docs.documentsList.set(i, doc);
            }
        }
        return docs;
    }
    
    
    
    
    
    
    
    public String objToString(Object obj){
        try {
            ByteArrayOutputStream bo = new ByteArrayOutputStream();
            ObjectOutputStream so = new ObjectOutputStream(bo);
            so.writeObject(obj);
            so.flush();
            String serializedObject = new sun.misc.BASE64Encoder().encode(bo.toByteArray());
            return serializedObject;
        } catch (Exception e) {
            System.out.println(e);
        }
        System.out.println("Erro: NULL write");
        return "";
    }
    
    public Object stringToObj(String str){
        try {
            byte b[] =  new sun.misc.BASE64Decoder().decodeBuffer(str);
            ByteArrayInputStream bi = new ByteArrayInputStream(b);
            ObjectInputStream si = new ObjectInputStream(bi);
            Object obj = si.readObject();
            return obj;
        } catch (Exception e) {
            System.out.println(e);
        }
        System.out.println("Erro: NULL read");
        return null;
    }
    
    public static Object deserializeAdd(int op){
        FileInputStream fin = null;
	ObjectInputStream ois = null;
        try {
            if(op==1)
                fin = new FileInputStream("C:\\Users\\Tiago\\Desktop\\Users.ser");
            else
                fin = new FileInputStream("C:\\Users\\Tiago\\Desktop\\Docs.ser");
            ois = new ObjectInputStream(fin);
            Object ret = ois.readObject();
            return ret;

        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            if (fin != null) {
                try {
                    fin.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (ois != null) {
                try {
                    ois.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return null;
    }
    
    public static void serialize(Object d1, int op){
        try {
            FileOutputStream fout;
            if(op == 1)
                fout = new FileOutputStream("C:\\Users\\Tiago\\Desktop\\Users.ser");
            else
                fout = new FileOutputStream("C:\\Users\\Tiago\\Desktop\\Docs.ser");
            ObjectOutputStream oos = new ObjectOutputStream(fout);
            oos.writeObject(d1);
            oos.close();
            fout.close();
            
        } catch (FileNotFoundException ex) {
            Logger.getLogger(ClientOffline.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(ClientOffline.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
}
