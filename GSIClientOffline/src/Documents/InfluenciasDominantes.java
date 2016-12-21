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
public class InfluenciasDominantes implements Serializable{
    
    public ArrayList<RelacoesAscendentes> relacoesAscendentes = new ArrayList<RelacoesAscendentes>();
    public ArrayList<RelacoesEquivalentes> relacoesEquivalentes = new ArrayList<RelacoesEquivalentes>();
    public ArrayList<RelacoesExternas> relacoesExternas = new ArrayList<RelacoesExternas>();
    public ArrayList<ControloFuncional> controloFuncional = new ArrayList<ControloFuncional>();
    
}
