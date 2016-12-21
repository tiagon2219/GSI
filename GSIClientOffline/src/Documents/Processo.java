/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Documents;

import java.io.Serializable;
import java.util.ArrayList;

/**
 *
 * @author Tiago
 */
public class Processo implements Serializable{
    
    public String id;
    public String texto;
    public ArrayList<Relacao> relacoes = new ArrayList<Relacao>();
    public String qualidadep;
    public boolean gastador;
    public String qualidaden;
    public String qualidadet;
    
    
}
