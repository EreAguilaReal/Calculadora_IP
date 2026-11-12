/*
    Aqui se agregan las funciones que sirven tanto para cidr como vlsm
 */
package calcudireccionesip;

public class FunGenerales {
    
    int [] valorError = {-1, -1, -1, -1};
    
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
        
        int [] par_IP = new int[partes_IP.length];
        int i;
        String IP_Base;
        
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
            
            for ( i = 0; i<partes_IP.length ; i++ ){
                if( mascara[i] != 255 ){
                    par_IP[i] = 0;
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
    
    public char tipoRed(int msk){
        char tipo;
        
        if(msk >= 24){
            tipo = 'C';
        } else if ( msk >= 16 ){
            tipo = 'B';
        } else {
            tipo = 'A';
        }
        
        return tipo;
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
    
    
    
}
