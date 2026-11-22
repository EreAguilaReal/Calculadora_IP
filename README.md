# Calculadora_IP
# Calculadora IP: CIDR y VLSM

Proyecto 2 de Redes de Computadoras, ESCOM-IPN, grupo 5CM2.
Aplicación de escritorio en Java (Swing) con dos herramientas:
1. **Calculadora CIDR**: red, máscara, rango de hosts, broadcast y división en subredes iguales.
2. **Calculadora VLSM**: asignación de subredes según hosts requeridos, sin solapamientos.

## Integrantes y roles
Carlos : Estructura general 
Johan  : Cálculos CIDR 
Ere : Cálculos VLSM 

## Requisitos
- JDK 
- NetBeans 
- Sin dependencias externas (solo la biblioteca estándar de Java)

## Estructura del proyecto
```
src/calcudireccionesip/
├── Red.java            # Clase de datos (IP, máscara, broadcast, hosts...)
├── FunGenerales.java   # Lógica de cálculo CIDR y VLSM
├── VentanaCalcu.java   # Ventana principal (captura y validación)
└── Ventana_info.java   # Ventana de resultados
```

## Cómo ejecutar
**Opción 1: ejecutable (.jar)**
1. Descargar `CalculadoraIP.jar` de [Releases / carpeta dist].
2. Ejecutar: `java -jar CalculadoraIP.jar` (o doble clic en `ejecutar.bat` / `ejecutar.sh`).

**Opción 2: desde el código**
1. Clonar: `git clone [URL del repo]`
2. Abrir en NetBeans y presionar *Run*, o compilar por consola:
   ```
   javac -d out src/calcudireccionesip/*.java
   java -cp out calcudireccionesip.VentanaCalcu
   ```

## Uso
**CIDR:** capturar IP, máscara de red (ej. 24) y máscara de subred (ej. 26) → *Calcular*.
**VLSM:** capturar IP, máscara de red y número de subredes → *Siguiente* → llenar la tabla con nombre y hosts → *Calcular*.

## Validaciones y errores
- IP con sintaxis inválida u octetos fuera de 0-255
- Máscaras fuera de 1-30; máscara de subred menor o igual a la de red
- Hosts no numéricos o menores a 1
- Subredes que no caben en la red base ("Las subredes no caben...")

## Pruebas
| Caso | Entrada | Resultado esperado |
|---|---|---|
| CIDR | 192.168.10.0/24 | Red 192.168.10.0, hosts .1 a .254, broadcast .255 |
| CIDR con subredes | 192.168.10.0/24 → /26 | 4 subredes: .0, .64, .128, .192 |
| VLSM | 192.168.0.0/24; A=100, B=50, C=25, D=10 | A /25, B /26, C /27, D /28; 16 libres desde 192.168.0.240 |
| Límite | /24 con 200 y 100 hosts | Error: no caben |

[Si agregan pruebas unitarias: cómo correrlas.]

## Licencia / atribución
[Licencia elegida, o "Sin bibliotecas externas".]
