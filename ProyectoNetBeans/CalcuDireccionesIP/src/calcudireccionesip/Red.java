/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package calcudireccionesip;

public class Red {
    
    int [] IP;          //IP de la red
    int Mask;           //Valor numerico de la mascara
    int [] mascara;     //valor en formato de IP de la mascara
    int [] Broadcast;   //IP de Broadcast
    int [] IP_Inicio;   //IP Inicico de hosts
    int [] IP_Final;    //IP Final de hosts
    char tipo;          //Tipo de red
    String nombre;      //Nombre de la subred (VLAN1, Enlace1...)
    int hostsPedidos;   //Cantidad de hosts pedidos por el usuario
    
    public Red(){
        
    }
    

    public int[] getIP() {
        return IP;
    }

    public void setIP(int[] IP) {
        this.IP = IP;
    }
    
    

    public int[] getBroadcast() {
        return Broadcast;
    }

    public void setBroadcast(int[] Broadcast) {
        this.Broadcast = Broadcast;
    }

    
    
    public int[] getIP_Final() {
        return IP_Final;
    }

    public void setIP_Final(int[] IP_Final) {
        this.IP_Final = IP_Final;
    }

    
    
    public int[] getIP_Inicio() {
        return IP_Inicio;
    }    

    public void setIP_Inicio(int[] IP_Inicio) {
        this.IP_Inicio = IP_Inicio;
    }
        
    

    public void setMascara(int[] mascara) {
        this.mascara = mascara;
    }

    public int[] getMascara() {
        return mascara;
    }
  
    

    public char getTipo() {
        return tipo;
    }

    public void setTipo(char tipo) {
        this.tipo = tipo;
    }

    
    
    public int getMask() {
        return Mask;
    }

    public void setMask(int Mask) {
        this.Mask = Mask;
    }
    

    public String getNombre(){
        return nombre;
    }

    public void setNombre(String nombre){
        this.nombre = nombre;
    }

    public int getHostsPedidos() {
        return hostsPedidos;
    }

    public void setHostsPedidos(int hostsPedidos) {
        this.hostsPedidos = hostsPedidos;
    }
    
    
}
