/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package deadly21;

import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

/**
 *
 * @Galvys Rodriguez
 */
public class MazoDeCartas {
    Cartas mazo[];
    int tope;

    public MazoDeCartas() {
        this.mazo = new Cartas[52];
        tope=-1;
    }
    
    public int obtenerTope(){
        return tope;
    }
    
    public boolean estaVacio(){
        return obtenerTope() == -1;
    }
    
    public boolean estaLleno(){
        return obtenerTope() == 51;
    }
 
    public void apilar(Cartas car){
        if(!this.estaLleno()){
            this.tope++;
            this.mazo[this.tope] = car;
        }else{
            System.out.println("Mensaje de error, mazo lleno");
        }
    }
    
    public void vaciarMazo(){
        for (int i=0; i<51;i++){
            this.mazo[i]=null;
            this.tope=-1;
        }
    }
    
    public Cartas desapilar(){
        if(!this.estaVacio()){
            this.tope--;
            return this.mazo[this.tope];
        }else{
            System.out.println("Mensaje de error, mazo vacio");
            return null;
        }
    }   
    
    public void generarMazo(){
        String palo=" ";
        String valor=" ";
        
        this.vaciarMazo();
        
        for (int i=0; i<=3; i++){
            for (int j=1; j<14; j++){
                switch (i){
                    case 0:
                        palo="Corazon";
                        break;
                    case 1:
                        palo="Diamante";
                        break;
                    case 2:
                        palo="Trebol";
                        break;
                    case 3:
                        palo="Pica";
                        break;   
                }
                
                switch(j){
                    case 1:
                        valor="ace";
                        break;
                    case 11:
                        valor="jack";
                        break;
                    case 12:
                        valor="queen";
                        break;
                    case 13:
                        valor="king";
                        break;
                    default:
                        valor = String.valueOf(j);
                        break;
                }
                
                Cartas nueva = new Cartas();
                nueva.establecerPalo(palo);
                nueva.establecerValor(j);
                
                switch (i){
                    case 0:
                        palo="hearts";
                        break;
                    case 1:
                        palo= "diamonds";
                        break;
                    case 2:
                        palo="clubs";
                        break;
                    case 3:
                        palo="spades";
                        break;   
                }
                
                String imagen ="/imagen/Cartas/"+valor+"_"+palo+"_white.png";
                
                nueva.establecerImagen(imagen);
                
                
                apilar(nueva);
            }
        }
    }
    
    public void barajearMazo(){
        Random rnd = ThreadLocalRandom.current();
        
        for (int i = 51; i >0; i--){
            int indAux = rnd.nextInt(i);
            
            Cartas aux = this.mazo[indAux];
            this.mazo[indAux]=this.mazo[i];
            this.mazo[i]= aux;
        }
    }
}
