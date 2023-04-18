/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package deadly21;

import static deadly21.InterfazMenuDeadly21.crearGanador;
import static deadly21.InterfazMenuDeadly21.crearPerdedores;
import static deadly21.InterfazMenuDeadly21.interfazPartida;
import static deadly21.InterfazMenuDeadly21.ubicacion;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JLabel;


/**
 *
 * @Galvys Rodriguez
 */
public class InterfazPartidaDeadly21 extends javax.swing.JFrame {
    
    static MazoDeCartas mazo= new MazoDeCartas();
    static ListaParticipantes lista = new ListaParticipantes();
    static ListaParticipantes perdedores = new ListaParticipantes();
    static ListaParticipantes listaInicial = new ListaParticipantes();
    static ListaParticipantes listaSemis = new ListaParticipantes();
    static ListaParticipantes listaFinal = new ListaParticipantes();
    static Participante ganador = new Participante();
    static int turno;
    static boolean finRonda=false;
    static Participante Jugador;
    static Participante Rival;
    
    /**
     * Creates new form InterfazPartidaDeadly21
     */
    public InterfazPartidaDeadly21() {
        initComponents();
        setIconImage(Toolkit.getDefaultToolkit().getImage(getClass().getResource("/imagen/ICONO.jpg")));
        setLocationRelativeTo(null);
        setVisible(true);
        setTitle("Deadly21");
        setResizable(false);
        Jugador = lista.obtenerParticipante(0);
        Rival = lista.obtenerParejaParticipante(0); 
        
        Jugador.pedirCarta();
        Rival.pedirCarta();
        Jugador.pedirCarta();
        Rival.pedirCarta();
        
        barridoCartas();
        
        turno = ThreadLocalRandom.current().nextInt(1,3);
 
        
        nombreJugador.setText(Jugador.obtenerNombre());
        nombreRival.setText(Rival.obtenerNombre());
        if (turno==2){
            
            Rival.decision = Rival.decidirMaquina(Jugador.sumMano());
            Rival.decidir();
            barridoCartas();
            
            turno=1;
            barridoLuces();
            Avisos nuevo = new Avisos(this,true,"El rival ha empezado",null);
        }
        barridoSumas();
    }
   
    public static void cargarParticipantes(){
        int numParticipantes=0;
        
        try {
            BufferedReader lector = new BufferedReader(new FileReader(ubicacion + "jugadores.in.txt"));
            
            while (numParticipantes<7){
                lista.ingresarAlPrincipio(lector.readLine(),lector.readLine());
                lector.readLine();
                
                numParticipantes++;
            }            
            lector.close();  
        } catch (IOException e) { 
           
        }
    }
    
    public void barridoSumas(){
        sumManoJugador.setText(String.valueOf(Jugador.sumMano()));
        sumManoRival.setText(String.valueOf(Rival.sumMano()));
    }
    
    public void barridoCartas(){
        JLabel aux = null;
        
        for (int i=0;i<=4;i++){
            switch (i){
                case 0:
                    aux=cartaJugador1;
                    break;
                case 1:
                    aux=cartaJugador2;
                    break;
                case 2:
                    aux=cartaJugador3;
                    break;
                case 3:
                    aux=cartaJugador4;
                    break;
                case 4:
                    aux=cartaJugador5;
                    break;
            }
            if(i<=Jugador.tope){
                aux.setIcon(new javax.swing.ImageIcon(getClass().getResource(Jugador.mano[i].imagen)));
            }else{
                aux.setIcon(null);
            }
        }
        
        for (int i=0;i<=4;i++){
            switch (i){
                case 0:
                    aux=cartaRival1;
                    break;
                case 1:
                    aux=cartaRival2;
                    break;
                case 2:
                    aux=cartaRival3;
                    break;
                case 3:
                    aux=cartaRival4;
                    break;
                case 4:
                    aux=cartaRival5;
                    break;
            }
            if(i<=Rival.tope){
                aux.setIcon(new javax.swing.ImageIcon(getClass().getResource(Rival.mano[i].imagen)));
            }else{
                aux.setIcon(null);
                }
        }
    }
    
    public void barridoLuces(){
        if (Jugador.decision==1){
            linternaJugador.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagen/BombilloTurno2.png")));
        }else{
            linternaJugador.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagen/BombilloTurno.png")));
        }
        
        if (Rival.decision==1){
            linternaRival.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagen/BombilloTurno2.png")));
        }else{
            linternaRival.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagen/BombilloTurno.png")));
        }
    }
    
    public void barridoRondas(){
        switch (Jugador.rondasGanadas){
            case 0:
                uiJugador.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagen/MarcoPerfil.png")));
                break;
            case 1:
                uiJugador.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagen/MarcoPerfil2.png")));
                break;
            case 2:
                uiJugador.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagen/MarcoPerfil3.png")));
                break;
        }
        
        switch(Rival.rondasGanadas){
            case 0:
                uiRival.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagen/MarcoPerfil.png")));
                break;
            case 1:
                uiRival.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagen/MarcoPerfil2.png")));
                break;
            case 2:
                uiRival.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagen/MarcoPerfil3.png")));
                break;
        }
    }
    
    public static void igualar(int indice, ListaParticipantes lis){
        for(int i = 0; i<indice; i++){
            lis.ingresarAlPrincipio(lista.obtenerNombreParticipante(indice-1-i), lista.obtenerActitudParticipante(indice-1-i));
        }
    }
    
    public static void mostrar(){
        if(lista.largo == 8){
            igualar(8, listaInicial);
        }else if (lista.largo == 4){
            igualar(4, listaSemis);
            
        }
        else if (lista.largo == 2){
            igualar(2, listaFinal);
        }
    }
    
    public void continuar(int a){
        lista.eliminar(a);
        if(lista.largo == 7){
            lista.partidaSimulada(1);
            lista.partidaSimulada(2);
            lista.partidaSimulada(3);
        }
        else if(lista.largo== 3){
            lista.partidaSimulada(1);
        }
        
        if(lista.largo == 1){
            ganador = lista.obtenerParticipante(0);
            perdedores.eliminarPorReferencia(ganador.obtenerNombre());
            crearPerdedores();
            crearGanador();
            lista.eliminarLista();
            setVisible(false);
       }
       
        else if(a == 0){
            while(lista.largo != 1){
                mostrar();
                if(lista.largo == 4 || lista.largo == 2){
                   lista.emparejar();
                }
                lista.partidaSimulada(0);
                if(lista.largo == 3){
                    lista.partidaSimulada(1);
                }
            }
            ganador = lista.obtenerParticipante(0);
            perdedores.eliminarPorReferencia(ganador.obtenerNombre());
            crearPerdedores();
            crearGanador();
            lista.eliminarLista();
             
            setVisible(false);
       }
       
            lista.emparejar();
            mazo.generarMazo();
            mazo.barajearMazo();
            nombreJugador.setText(Jugador.obtenerNombre());
            Rival = lista.obtenerParejaParticipante(0);
            
            nombreRival.setText(Rival.obtenerNombre());
            Jugador.rondasGanadas = 0;
            Rival.rondasGanadas = 0;
            barridoRondas();
            mensajes.setText("");
            mensajes2.setText("");
            mostrar();
            InterfazDuelos interfazDuelos = new InterfazDuelos();
            interfazDuelos.setVisible(true);
    }
    
    public void Partida(int i,int decision){
        barridoSumas();
        mensajes.setText("");
        mensajes2.setText("");
        Jugador.decision=decision;
        barridoLuces();
        if (Rival.sePaso()){
            finRonda=true;
        }else if (decision==2){
            if (Jugador.tope!=4){
               Jugador.pedirCarta(); 
            }
            barridoCartas();
            barridoSumas();
            
            if (Jugador.manoCharlie() || Jugador.esBlackjack() || Jugador.sePaso()){
                finRonda=true;
                botPedir.setEnabled(false);
                botPlantar.setEnabled(false);
                decision=1;
            }
        }else if (decision==1){
                botPedir.setEnabled(false);
                botPlantar.setEnabled(false);
                
            if (Rival.decision==1 || Rival.esBlackjack()){
                finRonda=true;
            }
        }
        
        Jugador.decision=decision;
        barridoLuces();
        if (finRonda==false && Rival.decision!=1){
             Avisos nuevo = new Avisos(this,true,"Turno del rival",null);
            if (decision==1){
                while(Rival.decision!=1){
                    Rival.decidirMaquina(Jugador.sumMano());
                    Rival.decidir(); 

                    if (Rival.manoCharlie() || Rival.esBlackjack() || Rival.sePaso()){
                        Rival.decision=1;
                    }
                    finRonda=true;
                }
            }else{
                Rival.decision = Rival.decidirMaquina(Jugador.sumMano());
                Rival.decidir();
                
            }
        }
        barridoCartas();  
        barridoSumas();
        barridoLuces();
        
        if (Jugador.sePaso()){
            mensajes2.setText(Jugador.obtenerNombre() + " se ha pasado");
        }else if (Rival.sePaso()){
            mensajes2.setText(Rival.obtenerNombre() + " se ha pasado");
        }
        
        
        
        if (finRonda==true){
            finRonda=false;
            Jugador.decision=0;
            Rival.decision=0;
            
            if (Jugador.rondasGanadas==2 && Rival.rondasGanadas==2){
                Avisos finR = new Avisos(this,true,"Fin de la Ronda",null);
            }else{
                Avisos finR2 = new Avisos(this,true,"Fin de la Ronda","Inicia una nueva");
            }
            
            if(Jugador.manoCharlie()){
                Jugador.rondasGanadas++;
                mensajes.setText(Jugador.obtenerNombre() + " gano por mano de Charlie");
            }else if(Rival.manoCharlie()){
                Rival.rondasGanadas++;
                mensajes.setText(Rival.obtenerNombre() + " gano por mano de Charlie");
            }else{
                if (Jugador.sumMano()==Rival.sumMano()){
                    mensajes.setText("Ronda empatada");
                }else if (Jugador.sumMano()>Rival.sumMano() && !Jugador.sePaso() || Rival.sePaso() && !Jugador.sePaso()){
                    Jugador.rondasGanadas++;
                    mensajes.setText("Gano esta ronda " + Jugador.obtenerNombre() );
                    if(Jugador.sumMano()>Rival.sumMano() && !Jugador.sePaso()){
                        mensajes2.setText("Ha tenido un numero mayor al rival \n\r Rival: " + Rival.sumMano() + "     Jugador: " + Jugador.sumMano());
                    }
                }else if (Jugador.sumMano()<Rival.sumMano() && !Rival.sePaso() || Jugador.sePaso() && !Rival.sePaso()){
                    Rival.rondasGanadas++;
                    mensajes.setText("Gano esta ronda " + Rival.obtenerNombre() );
                    if(Jugador.sumMano()<Rival.sumMano() && !Rival.sePaso()){
                        mensajes2.setText("El rival ha tenido un numero mayor \n\r Rival: " + Rival.sumMano() + "     Jugador: " + Jugador.sumMano());
                    }
                }
                
                
            }
            
            barridoRondas();
            barridoCartas();
            barridoSumas();
            barridoLuces();
            
            
            
            Jugador.reiniciarValores();
            Rival.reiniciarValores(); 
            botPedir.setEnabled(true);
            botPlantar.setEnabled(true);
            barridoCartas();
            
            Jugador.pedirCarta(); 
            Rival.pedirCarta();
            Jugador.pedirCarta();
            Rival.pedirCarta();
            barridoCartas();
            barridoSumas();
            
            
            if (Jugador.rondasGanadas==2){
                continuar(1);
            }else if(Rival.rondasGanadas==2){
                continuar(0);
            }
            
        }
    }
    

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        fondoColor = new javax.swing.JPanel();
        mensajes3 = new javax.swing.JLabel();
        mensajes2 = new javax.swing.JLabel();
        mensajes = new javax.swing.JLabel();
        sumManoJugador = new javax.swing.JLabel();
        sumManoRival = new javax.swing.JLabel();
        nombreJugador = new javax.swing.JLabel();
        nombreRival = new javax.swing.JLabel();
        uiRival = new javax.swing.JLabel();
        uiJugador = new javax.swing.JLabel();
        botPlantar = new javax.swing.JButton();
        botPedir = new javax.swing.JButton();
        cartasMesa = new javax.swing.JLabel();
        cartaJugador5 = new javax.swing.JLabel();
        cartaJugador4 = new javax.swing.JLabel();
        cartaJugador3 = new javax.swing.JLabel();
        cartaJugador2 = new javax.swing.JLabel();
        cartaJugador1 = new javax.swing.JLabel();
        cartaRival1 = new javax.swing.JLabel();
        cartaRival2 = new javax.swing.JLabel();
        cartaRival3 = new javax.swing.JLabel();
        cartaRival4 = new javax.swing.JLabel();
        cartaRival5 = new javax.swing.JLabel();
        linternaRival = new javax.swing.JLabel();
        linternaJugador = new javax.swing.JLabel();
        fondoMesa = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        fondoColor.setBackground(new java.awt.Color(153, 153, 153));
        fondoColor.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        mensajes3.setFont(new java.awt.Font("Chiller", 1, 36)); // NOI18N
        mensajes3.setForeground(new java.awt.Color(255, 255, 255));
        mensajes3.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        fondoColor.add(mensajes3, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 450, 1270, -1));

        mensajes2.setFont(new java.awt.Font("Chiller", 1, 36)); // NOI18N
        mensajes2.setForeground(new java.awt.Color(255, 255, 255));
        mensajes2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        fondoColor.add(mensajes2, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 400, 1270, -1));

        mensajes.setFont(new java.awt.Font("Chiller", 1, 48)); // NOI18N
        mensajes.setForeground(new java.awt.Color(255, 255, 255));
        mensajes.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        fondoColor.add(mensajes, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 330, 1270, -1));

        sumManoJugador.setFont(new java.awt.Font("Ink Free", 1, 60)); // NOI18N
        sumManoJugador.setForeground(new java.awt.Color(0, 0, 0));
        fondoColor.add(sumManoJugador, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 620, -1, -1));

        sumManoRival.setFont(new java.awt.Font("Ink Free", 1, 60)); // NOI18N
        sumManoRival.setForeground(new java.awt.Color(0, 0, 0));
        fondoColor.add(sumManoRival, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 60, -1, -1));

        nombreJugador.setFont(new java.awt.Font("Chiller", 1, 48)); // NOI18N
        nombreJugador.setForeground(new java.awt.Color(255, 255, 255));
        fondoColor.add(nombreJugador, new org.netbeans.lib.awtextra.AbsoluteConstraints(190, 580, -1, -1));

        nombreRival.setFont(new java.awt.Font("Chiller", 1, 48)); // NOI18N
        nombreRival.setForeground(new java.awt.Color(255, 255, 255));
        nombreRival.setText("aaa");
        fondoColor.add(nombreRival, new org.netbeans.lib.awtextra.AbsoluteConstraints(190, 20, -1, -1));

        uiRival.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagen/MarcoPerfil.png"))); // NOI18N
        fondoColor.add(uiRival, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 10, 360, 180));

        uiJugador.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagen/MarcoPerfil.png"))); // NOI18N
        fondoColor.add(uiJugador, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 570, 360, 180));

        botPlantar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagen/BotonPlantarse.png"))); // NOI18N
        botPlantar.setText("  ");
        botPlantar.setBorder(null);
        botPlantar.setBorderPainted(false);
        botPlantar.setContentAreaFilled(false);
        botPlantar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botPlantarActionPerformed(evt);
            }
        });
        fondoColor.add(botPlantar, new org.netbeans.lib.awtextra.AbsoluteConstraints(1020, 460, 258, 240));

        botPedir.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagen/BotonPedir.png"))); // NOI18N
        botPedir.setText("  ");
        botPedir.setBorder(null);
        botPedir.setBorderPainted(false);
        botPedir.setContentAreaFilled(false);
        botPedir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botPedirActionPerformed(evt);
            }
        });
        fondoColor.add(botPedir, new org.netbeans.lib.awtextra.AbsoluteConstraints(830, 460, 258, 240));

        cartasMesa.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagen/Cartas/back_black_basic_white.png"))); // NOI18N
        fondoColor.add(cartasMesa, new org.netbeans.lib.awtextra.AbsoluteConstraints(1070, 260, 120, 170));
        fondoColor.add(cartaJugador5, new org.netbeans.lib.awtextra.AbsoluteConstraints(710, 580, 120, 170));
        fondoColor.add(cartaJugador4, new org.netbeans.lib.awtextra.AbsoluteConstraints(640, 580, 120, 170));
        fondoColor.add(cartaJugador3, new org.netbeans.lib.awtextra.AbsoluteConstraints(560, 580, 120, 170));
        fondoColor.add(cartaJugador2, new org.netbeans.lib.awtextra.AbsoluteConstraints(480, 580, 120, 170));
        fondoColor.add(cartaJugador1, new org.netbeans.lib.awtextra.AbsoluteConstraints(400, 580, 120, 170));
        fondoColor.add(cartaRival1, new org.netbeans.lib.awtextra.AbsoluteConstraints(760, 10, 120, 170));
        fondoColor.add(cartaRival2, new org.netbeans.lib.awtextra.AbsoluteConstraints(690, 10, 120, 170));
        fondoColor.add(cartaRival3, new org.netbeans.lib.awtextra.AbsoluteConstraints(610, 10, 120, 170));
        fondoColor.add(cartaRival4, new org.netbeans.lib.awtextra.AbsoluteConstraints(530, 10, 120, 170));
        fondoColor.add(cartaRival5, new org.netbeans.lib.awtextra.AbsoluteConstraints(450, 10, 120, 170));

        linternaRival.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagen/BombilloTurno.png"))); // NOI18N
        fondoColor.add(linternaRival, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 250, -1, -1));

        linternaJugador.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagen/BombilloTurno.png"))); // NOI18N
        fondoColor.add(linternaJugador, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 410, -1, -1));

        fondoMesa.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagen/FondoMesa.png"))); // NOI18N
        fondoMesa.setToolTipText("");
        fondoColor.add(fondoMesa, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 1280, 800));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(fondoColor, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(fondoColor, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void botPlantarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botPlantarActionPerformed
        // TODO add your handling code here:
        Partida(0,1);
    }//GEN-LAST:event_botPlantarActionPerformed

    private void botPedirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botPedirActionPerformed
        // TODO add your handling code here:
        Partida(0,2);
    }//GEN-LAST:event_botPedirActionPerformed
    


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton botPedir;
    private javax.swing.JButton botPlantar;
    private javax.swing.JLabel cartaJugador1;
    private javax.swing.JLabel cartaJugador2;
    private javax.swing.JLabel cartaJugador3;
    private javax.swing.JLabel cartaJugador4;
    private javax.swing.JLabel cartaJugador5;
    private javax.swing.JLabel cartaRival1;
    private javax.swing.JLabel cartaRival2;
    private javax.swing.JLabel cartaRival3;
    private javax.swing.JLabel cartaRival4;
    private javax.swing.JLabel cartaRival5;
    private javax.swing.JLabel cartasMesa;
    private javax.swing.JPanel fondoColor;
    private javax.swing.JLabel fondoMesa;
    private javax.swing.JLabel linternaJugador;
    private javax.swing.JLabel linternaRival;
    private javax.swing.JLabel mensajes;
    private javax.swing.JLabel mensajes2;
    private javax.swing.JLabel mensajes3;
    private javax.swing.JLabel nombreJugador;
    private javax.swing.JLabel nombreRival;
    private javax.swing.JLabel sumManoJugador;
    private javax.swing.JLabel sumManoRival;
    private javax.swing.JLabel uiJugador;
    private javax.swing.JLabel uiRival;
    // End of variables declaration//GEN-END:variables
}