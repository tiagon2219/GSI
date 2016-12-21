/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pqm.client;

import Documents.Documento;
import Documents.Fcs;
import Documents.Processo;
import Documents.ProcessosNegocio;
import Documents.Relacao;

/**
 *
 * @author goncalo
 */
public class teste {
    
    Documento doc_teste = new Documento();

    public Documento escreve (){
        
        Fcs fcs1 = new Fcs();
        Fcs fcs2 = new Fcs();
        fcs1.texto = "fcs1";
        fcs2.texto = "fcs2";
        doc_teste.fcs.add(fcs1);
        doc_teste.fcs.add(fcs2);
        
        ProcessosNegocio proc_neg = new ProcessosNegocio();
        Processo proc_teste = new Processo();
        proc_teste.id = "p1";
        proc_teste.texto = "proc_negocio 1";
        proc_teste.gastador = true;
        proc_teste.qualidaden = "E";
        proc_teste.qualidadet= "C";
        proc_teste.qualidadep = "B";
        
        Processo proc_teste2 = new Processo();
        proc_teste2.id = "p2";
        proc_teste2.texto = "proc_negocio 2";
        proc_teste2.gastador = true;
        proc_teste2.qualidaden = "D";
        proc_teste2.qualidadet= "B";
        proc_teste2.qualidadep = "E";
        
        
        Relacao rel = new Relacao();
        rel.estado = true;
        Relacao rel2 = new Relacao();
        rel2.estado = false;
        
        proc_teste.relacoes.add(rel);
        proc_teste.relacoes.add(rel2);
        
        Relacao rel3 = new Relacao();
        rel3.estado = true;
        proc_teste2.relacoes.add(rel);
        proc_teste2.relacoes.add(rel3);
        proc_neg.processos.add(proc_teste);
        proc_neg.processos.add(proc_teste2);
        doc_teste.processosNegocio = proc_neg;
        
        return doc_teste;
    }
    
}

