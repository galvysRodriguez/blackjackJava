/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package deadly21;

import static deadly21.InterfazPartidaDeadly21.mazo;

/**
 *
 * @Galvys Rodriguez
 */
public class Participante {
    
    String nombre;
    Cartas[] mano = new Cartas[5];
    int decision,rondasGanadas,tope;
    Participante sigParticipantes,pareja;
    String actitud;
    
    public Participante(){
        this.nombre="";
        this.decision=0;
        this.tope=-1;
        this.sigParticipantes=null;
        this.pareja=null;
        this.rondasGanadas=0;
    }
    
    public void establecerNombre(String nom){
        this.nombre = nom;
    }
    
    public String obtenerNombre(){
        return this.nombre;
    }
    
    public void establecerSiguiente(Participante par){
        this.sigParticipantes = par;
    }
    
    public Participante obtenerSiguiente(){
        return this.sigParticipantes;
    }
    
    public void establecerPareja(Participante par){
        this.pareja = par;
    }
    
    public Participante obtenerPareja(){
        return this.pareja;
    }
    
    public boolean esBlackjack(){
        return this.sumMano()==21;
    }
    
    public boolean sePaso(){
        return this.sumMano()>21;
    }
    
    public boolean manoCharlie(){
        return (this.sumMano()<21 && this.tope==4);
    }

    public void pedirCarta(){
        this.tope++;
        this.mano[this.tope]= mazo.desapilar();
    }
    
    public int sumMano(){
        int sum=0;
        for(int i=0; i<this.tope+1;i++){
            if (this.mano[i].obtenerValor()>10){
                sum=sum+10;
            }else if (this.mano[i].obtenerValor()==1){
                sum=sum+11;
            }else{
                sum = sum + this.mano[i].obtenerValor();
            }
        }
        
        if (sum>21){
            for(int i=0; i<this.tope+1;i++){
                if (this.mano[i].obtenerValor()==1){ 
                   sum=sum-10;
                }
                if (sum<21){
                    break;
                }
            }
        }
        return sum;
    }
    
    public void decidir(){
        switch(decision){
            case 1:
                break;
            
            case 2:
                this.pedirCarta();
                break;
        }
    }
    
    public void establecerActitud(String act){
        this.actitud = act;
    }
    
    public String obtenerActitud(){
        return this.actitud;
    }
    
    public int decidirMaquina(int sumContrario){
        switch (this.actitud){
            case "Reservado":
                if (this.sumMano()<15 && sumContrario<this.sumMano() || this.sumMano()<sumContrario){
                    this.decision=2;
                }else{
                    this.decision=1;
                }
                break;
            case "Arriesgado":
                if (this.sumMano()<18 && sumContrario<this.sumMano() || this.sumMano()<sumContrario){
                    this.decision=2;
                }else{
                    this.decision=1;
                }
                break;
        }
        return this.decision;
    }
    

    
    public void reiniciarValores(){
        this.decision=0;
        this.tope=-1;
        
        for (int i=0; i<5;i++){
            this.mano[i]=null;
        }
    }
}
