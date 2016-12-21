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
public class Documento implements Serializable{
    
    public String title;
    public Introducao introducao;
    public Missao missao;
    public InfluenciasDominantes influenciasDominantes;
    public ArrayList<Fcs> fcs = new ArrayList<Fcs>();
    public ProcessosNegocio processosNegocio = new ProcessosNegocio();
    public Conclusao conclusao;
    
    public long id;
    public ArrayList<Long> userId = new ArrayList<Long>();
    
    
}
