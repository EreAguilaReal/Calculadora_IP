/*
    Aqui se agregan las funciones que sirven tanto para cidr como vlsm
 */
package calcudireccionesip;

import java.util.Arrays;
import java.util.ArrayList;

public class FunGenerales {
    
    int [] valorError = {-1, -1, -1, -1};
    
    public int bitsHost(int [] mask, int pos){
        int bitsH = 0;
        int j;

        String BinIP = Integer.toBinaryString(mask[pos]);

        for (j = 0; j < BinIP.length(); j++) {
            if (BinIP.toCharArray()[j] != '1') {
                bitsH++;
            }
        }

        return bitsH;
    }
    
    public int bitsRed(int [] mask, int pos){
        int bitsR = 0;
        int j;

        String BinIP = Integer.toBinaryString(mask[pos]);

        for (j = 0; j < BinIP.length(); j++) {
            if (BinIP.toCharArray()[j] == '1') {
                bitsR++;
            }
        }

        return bitsR;
    }
    
    public boolean esNum(String s) {
        try {
            double d = Double.parseDouble(s);
        } catch (NumberFormatException e) {
            return false;
        }
        return true;
    }
    
    public int [] RedBase( String IP, int Mask ){
        
        String [] partes_IP = IP.split("\\.");

        if( partes_IP.length != 4 ){
            return valorError;
        }

        for( String p : partes_IP ){
            if( !p.matches("\\d{1,3}") ){
                return valorError;
            }
        }
        
        int [] par_IP = new int[partes_IP.length];
        int i, j;
        
        for( i=0 ; i<partes_IP.length ; i++ ){
            if ( Integer.parseInt(partes_IP[i]) < 0 || Integer.parseInt(partes_IP[i]) > 255 ){
                return valorError;
            }
            
            par_IP[i] = Integer.parseInt(partes_IP[i]);
        }
        
        if( partes_IP.length != 4 ){
            
            return valorError;
            
        } else {
            
            for( i=0 ; i<partes_IP.length ; i++ ){
                if( !esNum(partes_IP[i]) ){
                    return valorError;
                }
            }
            
            int [] mascara = gen_Mask(Mask);
            
            String BinIP;
            int redIni, redSeg, bitsH, cambio;
            
            for ( i = 0; i<partes_IP.length ; i++ ){
                if ( mascara[i] == 0 ){
                    
                    par_IP[i] = 0;
                    
                } else if( mascara[i] != 255 ){
                    
                    bitsH = bitsHost(mascara, i);
                    
                    cambio = (int) Math.pow(2, bitsH);
                    
                    redIni = 0; redSeg = cambio;
                    
                    while(redIni != 256){
                        
                        if (  par_IP[i] >= redIni && par_IP[i] < redSeg ){
                            par_IP[i] = redIni;
                        }
                        redIni+=cambio; redSeg+=cambio;
                    }
                    
                }
            }
            
        }
                
        return par_IP;
        
    }
    
    public int[] gen_Mask( int mask ){
        
        int[] mascara = new int[4];
        int i, j, cont;
        
        j = 0;
        cont = 0;
        
        for( i=1 ;i<4; i++){
            mascara[j] = 0;
        }
        
        for ( i =0 ; i<mask ; i++ ) {
            
            cont++;
            if( cont == 8 ){
                
                mascara[j] = 255;
                
                j++;
                cont = 0;
            }
            
        }
        
        int valorM  = 0, pot = 7;
        if (cont != 0){
            
            for(i = 0 ; i<cont; i++){
                valorM += Math.pow(2, pot);
                pot--;
            }
            
            mascara[j] = valorM;
            
        }
        
        return mascara;
        
    }
    
    public int [] IP_ini(int [] IP){
        
        int [] IP_I = Arrays.copyOf(IP, IP.length);
        
        IP_I[3]+=1;
        
        return IP_I;
        
    }
    
     public int [] IP_fin(int [] IP, int [] mask){
        
        int salto;
        int [] IP_F = Arrays.copyOf(IP, IP.length); ;
        int i, j=0;
        
        for( i=0 ; i<mask.length ; i++ ){
            if ( mask[i] == 0 || mask[i] != 255 || (mask[i] == 0 && i == 0) ) {
                
                j = i;
                i = mask.length;
                
            }
        }
        
        salto = 256-mask[j];
        
        if(j != 3){
            salto--;
            
            IP_F [j] += salto;
            
            j++;
            
            while(j<mask.length-1){
                
                IP_F[j] = 255;
                
                j++;
            }
            IP_F[j] = 254;
            
        } else {
            salto = salto - 2;
            IP_F [j] += salto;
        }
        
        return IP_F;
        
    }
    
    public char tipoRed(int ip){
        char tipo;
        
        if(ip >= 240){
            tipo = 'E';
        } else if ( ip >= 224 ){
            tipo = 'D';
        } else if ( ip >= 192 ){
            tipo = 'C';
        } else if ( ip >= 128 ){
            tipo = 'B';
        } else{
            tipo = 'A';
        }
        
        return tipo;
    }
        
    //Bits de host necesarios para la cantidad de host que se desea
    public int bitsParaHosts(int hosts){
        int bits = 2; //minimo 30
        while( Math.pow(2, bits) - 2 < hosts ){
            bits++;
        }
        return bits;
    }

    //Mascara de subred para esa cantidad de hosts
    public int maskParaHosts(int hosts){
        return 32 - bitsParaHosts(hosts);
    }

    //Tamano total del bloque (incluye red y broadcast)
    public int tamanoBloque(int hosts){
        return (int)Math.pow(2, bitsParaHosts(hosts)); // 2^bits
    }

    //Ordena la lista de subredes de MAYOR a MENOR numero de hosts
    public void ordenarPorHosts(ArrayList<Red> lista){
        lista.sort( (a,b) -> b.getHostsPedidos() - a.getHostsPedidos() );
    }

    //Convierte {148.204.0.0} a un solo numero de 32 bits
    public long ipALong(int[] ip){
        return((long) ip[0]<<24) |
              ((long) ip[1]<<16) |
              ((long) ip[2]<<8)  |
              ((long) ip[3]);
    }

    //Convierte el numero de 32 bits de vuelta a {148,204,0,0}
    public int[] longAIp(long v){
        return new int[]{
            (int) ((v >> 24) & 255),
            (int) ((v >> 16) & 255),
            (int) ((v >> 8) & 255),
            (int) (v & 255)
        };
    }

    //Asigna a cada subred su IP de red y su mascara, una tras otra. 
    //Devuelve false si las subredes no caben en la red base.
    public boolean asignarRedes(ArrayList<Red> lista, Red base){
        long actual = ipALong(base.getIP());
        long limite = actual + (1L << (32 - base.getMask())); //IP de broadcast + 1

        for(Red s : lista){
            long bloque = tamanoBloque(s.getHostsPedidos());

            if(actual + bloque > limite){
                return false; //No caben todas las subredes
            }

            s.setIP(longAIp(actual));
            s.setMask(maskParaHosts(s.getHostsPedidos()));

            actual += bloque;
        }

        return true;
    }

    // Completa, para cada subred que ya tiene IP y mascara:
    // mascara en formato IP, primer host, ultimo host y broadcast
    public void calcularLimites(ArrayList<Red> lista){

        for( Red s : lista ){

            long red       = ipALong(s.getIP());
            long bloque    = 1L << (32 - s.getMask());   // total de direcciones del bloque
            long broadcast = red + bloque - 1;

            s.setMascara(gen_Mask(s.getMask()));
            s.setIP_Inicio(longAIp(red + 1));
            s.setIP_Final(longAIp(broadcast - 1));
            s.setBroadcast(longAIp(broadcast));
            s.setTipo(tipoRed(s.getIP()[0]));
        }
    }

    //////////////////////////////////////////////Funciones de Impresion/////////////////////////7
    
    public void printIP( int [] arr, int msk){
        
        System.out.println(arr[0] + "." + arr[1] + "." + arr[2] + "." + arr[3] + "/" + msk);
        
    }
    
    public void printIP( int [] arr){
        
        System.out.println(arr[0] + "." + arr[1] + "." + arr[2] + "." + arr[3]);
        
    }
    
    public void printBit( String [] arr){
        
        System.out.println(arr[0] + "." + arr[1] + "." + arr[2] + "." + arr[3]);
        
    }
    
    public String ipAString(int [] ip){
        return ip[0] + "." + ip[1] + "." + ip[2] + "." + ip[3];
    }
    
}
