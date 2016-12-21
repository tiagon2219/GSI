/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pqm.client;

import Documents.*;
import net.comm.Message;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.URI;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.websocket.ClientEndpoint;
import javax.websocket.ContainerProvider;
import javax.websocket.DeploymentException;
import javax.websocket.OnMessage;
import javax.websocket.Session;
import javax.websocket.WebSocketContainer;
import java.util.Scanner;


/**
 *
 * @author Tiago
 */
@ClientEndpoint
public class ClientGUI {
    
    
    public long id;
    public Documento docActual = null;
    public boolean editing = false;
    public Session session;
    public WebSocketContainer container = null;
    public boolean initiated = false;
    
    @OnMessage
    public void onMessage(String message) {
        Message msg = (Message)stringToObj(message);
        
        if(msg.command.equals("registar")){
            System.out.println("#Registar: "+msg.correct);
        }
        else if(msg.command.equals("login")){
            if(msg.correct)
                id = msg.id;
            System.out.println("#Login: "+msg.correct+" "+id);
        }
        else if(msg.command.equals("verDocumentos")){
            System.out.println("Documentos:\n"+msg.msg);
        }
        else if(msg.command.equals("getDocumento")){
            if(msg.correct){
                System.out.println("#Abriu o documento: "+((Documento)msg.object).title);
                docActual = (Documento)msg.object;
            }
            else
                System.out.println("Nao foi possivel receber o documento");
        }
        else if(msg.command.equals("editDocumento")){
            if(msg.correct){
                docActual = (Documento)msg.object;
                editing = true;
                System.out.println("Editing: "+docActual.title);
            }
            else{
                editing = false;
                System.out.println("Nao pode editar o documento");
            }
        }
        else if(msg.command.equals("inviteUser")){
            if(!msg.correct)
                System.out.println("Nao consegui adicionar o utilizador");
            else
                System.out.println("Utilizador adicionado");
        }
        else{
            System.out.println("A MENSAGEM ESTA ERRADA");
        }
        System.out.println("Message: "+id);
    }
    
    public ClientGUI(int in){
        System.out.println("In: "+in);
        if(initiated)
            return;
        initiated = true;
        System.out.println("Initiate");
        id = -1;
        
        session = null;
        container = ContainerProvider.getWebSocketContainer();
        
        try {
            session = container.connectToServer(ClientGUI.class, URI.create("ws://localhost:8080/GSIServer/actions"));
        } catch (DeploymentException ex) {
            Logger.getLogger(ClientGUI.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(ClientGUI.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void work(int option, String ... args){
        
        /*
        for(String arg:args)
            arg
        */
        try {
            if(option == 1){
                Message msg = new Message();
                msg.command = "registar";
                msg.msg = "";
                for(String arg:args)
                    msg.msg += arg;
                session.getBasicRemote().sendText(objToString(msg));
            }
            else if(option == 2){
                Message msg = new Message();
                msg.command = "login";
                msg.msg = "";
                for(String arg:args)
                    msg.msg += arg+" ";
                session.getBasicRemote().sendText(objToString(msg));
                sleep(1000);
                System.out.println("Login 1: "+id);
            }
            else if(option == 3){
                Message msg = new Message();
                msg.command = "verDocumentos";
                session.getBasicRemote().sendText(objToString(msg));
            }
            else if(option == 4){
                if(id >= 0){
                    Documento doc = new Documento();
                    Message msg = new Message();
                    msg.msg = "";
                    for(String arg:args)
                        msg.msg += arg+" ";
                    doc.title = msg.msg;
                    doc.userId.add(id);
                    msg.object = doc;
                    msg.command = "novoDocumento";
                    session.getBasicRemote().sendText(objToString(msg));
                }
                else
                    System.out.println("Precisa de fazer o login");
            }
            else if(option == 5){
                if(id >= 0){
                    Message msg = new Message();
                    System.out.println("Abrir documento:\nTitulo: ");
                    msg = new Message();
                    msg.msg = "";
                    for(String arg:args)
                        msg.msg += arg+" ";
                    msg.command = "getDocumento";
                    session.getBasicRemote().sendText(objToString(msg));
                }
                else
                    System.out.println("Precisa de fazer o login");
            }
        } catch (IOException ex) {
            Logger.getLogger(ClientGUI.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        sleep(500);
        System.out.println("Client "+id);
    }
    
    public void sleep(int time){
        try {
            Thread.sleep(time);
        } catch (InterruptedException ex) {
            Logger.getLogger(ClientGUI.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    
    
    /*
    public void mainAnt() {
        WebSocketContainer container = null;
        Session session = null;
        
        container = ContainerProvider.getWebSocketContainer();
        
        Random rand = new Random();
        id = rand.nextInt(10);
        
        try {
            session = container.connectToServer(ClientTUI.class, URI.create("ws://localhost:8080/GSIServer/actions"));
            
            Scanner sc = new Scanner(System.in);
            int option = -1;
            
            while(option != 0){
                System.out.println("\nOptions:\n0-Sair\n1-Registar\n2-Login\n3-Ver Documentos\n4-Novo Documento\n5-Entrar no documento\n");
                option = Integer.parseInt(sc.nextLine());
                
                if(option == 1){
                    System.out.println("\nRegistar:\nUsername password: ");
                    Message msg = new Message();
                    msg.command = "registar";
                    msg.msg = sc.nextLine();
                    session.getBasicRemote().sendText(objToString(msg));
                }
                else if(option == 2){
                    System.out.println("\nLogin:\nUsername password: ");
                    Message msg = new Message();
                    msg.command = "login";
                    msg.msg = sc.nextLine();
                    session.getBasicRemote().sendText(objToString(msg));
                }
                else if(option == 3){
                    Message msg = new Message();
                    msg.command = "verDocumentos";
                    session.getBasicRemote().sendText(objToString(msg));
                }
                else if(option == 4){
                    if(id >= 0){
                        Documento doc = new Documento();
                        System.out.println("Novo documento:\nTitulo: ");
                        doc.title = sc.nextLine();
                        doc.userId.add(id);
                        Message msg = new Message();
                        msg.object = doc;
                        msg.command = "novoDocumento";
                        session.getBasicRemote().sendText(objToString(msg));
                    }
                    else
                        System.out.println("Precisa de fazer o login");
                }
                else if(option == 5){
                    if(id >= 0){
                        Message msg = new Message();
                        msg.command = "verDocumentos";
                        session.getBasicRemote().sendText(objToString(msg));
                        
                        try {
                            Thread.sleep(100);
                        } catch (InterruptedException ex) {
                            Logger.getLogger(ClientTUI.class.getName()).log(Level.SEVERE, null, ex);
                        }
                        
                        System.out.println("Abrir documento:\nTitulo: ");
                        msg = new Message();
                        msg.msg = sc.nextLine();
                        msg.command = "getDocumento";
                        session.getBasicRemote().sendText(objToString(msg));
                    }
                    else
                        System.out.println("Precisa de fazer o login");
                }
                
                try {
                    Thread.sleep(500);
                } catch (InterruptedException ex) {
                    Logger.getLogger(ClientTUI.class.getName()).log(Level.SEVERE, null, ex);
                }
                if(docActual != null){
                    DocState(session);
                    docActual = null;
                    editing = false;
                }
            }
            
        } catch (DeploymentException | IOException ex) {
            Logger.getLogger(ClientTUI.class.getName()).log(Level.SEVERE, null, ex);
        } 
    }
    
    public static void DocState(Session session){
        Scanner sc = new Scanner(System.in);
        int option = -1;

        try {
            while(option != 0){
                System.out.println("\nDocumento:\n0-Sair\n1-Editar\n2-Intro\n3-Convidar utilizador\n4-Gerar docx\n");
                option = Integer.parseInt(sc.nextLine());

                Message msg = new Message();
                msg = new Message();
                msg.msg = docActual.title;
                msg.command = "getDocumento";
                session.getBasicRemote().sendText(objToString(msg));
                
                if(option == 0){
                    msg = new Message();
                    msg.command = "unlockDocumento";
                    session.getBasicRemote().sendText(objToString(msg));
                }
                else if(option == 1){
                    msg = new Message();
                    msg.command = "editDocumento";
                    msg.msg = docActual.title;
                    session.getBasicRemote().sendText(objToString(msg));
                }
                else if(option == 2){
                    System.out.println("Intro: "+docActual.introducao.texto);
                }
                else if(option == 3){
                    if(editing){
                        System.out.println("Username: ");
                        String username = sc.nextLine();
                        msg = new Message();
                        msg.command = "inviteUser";
                        msg.msg = username;
                        msg.object = docActual;
                        session.getBasicRemote().sendText(objToString(msg));
                    }
                    else
                        System.out.println("Nao pode convidar utilizadores sem estar a editar o documento");
                }
                else if(option == 4){
                    DocGenerator generator = new DocGenerator(docActual);
                    generator.work();
                }

                try {
                    Thread.sleep(500);
                } catch (InterruptedException ex) {
                    Logger.getLogger(ClientTUI.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        } catch (IOException ex) {
            Logger.getLogger(ClientTUI.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
    */
    
    
    
    
    
    
    
    
    
    
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
    
}
