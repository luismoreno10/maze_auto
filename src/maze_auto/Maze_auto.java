
package maze_auto;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Scanner;

class Conexion{
    String etiqueta;
    public Conexion (String etiqueta){
        this.etiqueta = etiqueta;
    }
}

class Vertice {
    String etiqueta;
    List<Conexion> conexiones;
    boolean visitado;
    public Vertice(String etiqueta){
        this.etiqueta   = etiqueta;
        this.conexiones = new LinkedList<Conexion>();
        this.visitado   = false;
    }
}

class Grafo{
    List<Vertice> vertices;
    public Grafo(){
        this.vertices = new LinkedList<>();
    }
}

public class Maze_auto {

    public static void main(String[] args) {
        int opcion_lab;
        Scanner x = new Scanner (System.in);
        System.out.println("1- Laberinto fácil");
        System.out.println("2- Laberinto medio");
        System.out.println("3- Laberinto difícil");
        System.out.println("0- Salir");
        System.out.println("-----------------------");
        System.out.println("SELECCIONE OPCIÓN:");
        opcion_lab = x.nextInt();
        
        if (opcion_lab == 1){
        String[][] lab1 = CargaLaberinto(args[0]);
        String inicio,fin;
        inicio = "1,1"; //punto del asterisco
        fin = "5,6";     //punto de x
        Grafo g = CargaLaberintoGrafo(args[0], inicio, fin, lab1);
        //DespliegaGrafo(g);
        DFS(g,inicio,fin,lab1);   
        } else 
            if (opcion_lab == 2){
            String[][] lab2 = CargaLaberinto(args[1]);
            String inicio,fin;
            inicio = "1,1"; //punto del asterisco
            fin = "12,13";     //punto de x
            Grafo g = CargaLaberintoGrafo(args[1], inicio, fin, lab2);
            //DespliegaGrafo(g);
            DFS(g,inicio,fin,lab2);
            } else
                if (opcion_lab == 3){
                    String[][] lab3 = CargaLaberinto(args[2]);
                    String inicio,fin;
                    inicio = "1,1"; //punto del asterisco
                    fin = "12,13";     //punto de x
                    Grafo g = CargaLaberintoGrafo(args[2], inicio, fin, lab3);
                    //DespliegaGrafo(g);
                    DFS(g,inicio,fin,lab3);
                }    
        
        
        
        
    }
    
    public static String[][] CargaLaberinto(String archivo){
        String inicio, end;
        inicio="*";
        end="x";
        int renglon=0;
        ArrayList<String> lineas = new ArrayList<>();
        try {
            File f = new File(archivo);
            BufferedReader b = new BufferedReader(new FileReader(f));
            String readLine = "";
            while ((readLine = b.readLine()) != null){
                lineas.add(readLine);
            }
            int ancho = lineas.get(0).length();
            int largo = lineas.size();
            
            String[][] mapa = new String[largo][ancho];
            for ( String linea: lineas){
                char[] caracteres = linea.toCharArray();
                for (int columna=0; columna < caracteres.length; columna++){
                    mapa[renglon][columna] = String.valueOf(caracteres[columna]);
                }
                renglon++;
            }
            return mapa;
        } catch (IOException e){
            e.printStackTrace();
            return null;
        }
    }
    
    public static Grafo CargaLaberintoGrafo(String archivo,String asterisco,String equis,String[][] laberinto){  
        Grafo grafo = new Grafo();   
        
        for(int filas=0; filas < laberinto.length; filas++){
            for(int columnas=0; columnas < laberinto.length; columnas++){
                    String etiqueta = filas+","+columnas;  
            }
        }          
                    Vertice v = AgregaVertice(grafo,laberinto,asterisco);
                    Conexion c = AgregaConexiones(grafo,laberinto, equis); //ERROR
                    v.conexiones.add(c);
             return grafo;  
    }
    
    public static void DespliegaGrafo(Grafo grafo){
        for(Vertice vertice : grafo.vertices){
            System.out.println(vertice.etiqueta);
            for (Conexion conexion : vertice.conexiones) {
                System.out.print(conexion.etiqueta);
                System.out.print("->");
            }
            System.out.println("");
        }
    }
    
    public static Vertice AgregaVertice(Grafo g,  String[][] matriz, String inicio){
        boolean encontrado = false;
        Scanner leer = new Scanner(System.in);
        Vertice v = null;
        String  etiqueta;
        for(int filas=0; filas < matriz.length; filas++){
            for(int columnas=0; columnas < matriz.length; columnas++){
                if(matriz[filas][columnas].equals(" ")){
                    etiqueta = filas+","+columnas;
//                    for(Vertice vertice: g.vertices){
//                        if(vertice.etiqueta.equals(inicio)){
//                            encontrado = true;
//                            return vertice;
//                        }
//                    }
//                    if (encontrado == false){
                        v = new Vertice(etiqueta);
                        g.vertices.add(v);
//                    
//                    }    
                }   
            }
        }
        return v;
    } 
    
    public static Conexion AgregaConexiones(Grafo g, String[][] matriz,String etiqueta){ //DUDAS
        Vertice v = null;
        Scanner leer = new Scanner(System.in);
        Conexion c = null;
        for(int filas=0; filas < matriz.length; filas++){
            for(int columnas=0; columnas < matriz.length-1; columnas++){
                String etiq = filas+","+columnas;
                if(matriz[filas][columnas].equals(" ")){ //ERROR         
                    v = BuscaVertice(g,etiq);
                    if(matriz[filas][columnas-1].equals(" ")){ //izq
                         String tag = filas+","+(columnas-1);
                         c = new Conexion(tag);
                         v.conexiones.add(c);
                    }
                    if(matriz[filas][columnas+1].equals(" ")){ //der
                         String tag = filas+","+(columnas+1);
                         c = new Conexion(tag);
                         v.conexiones.add(c);
                    }
                    if(matriz[filas-1][columnas].equals(" ")){ //arr.
                        String tag = (filas-1)+","+columnas;
                        c = new Conexion(tag);
                        v.conexiones.add(c);
                    }
                    if(matriz[filas+1][columnas].equals(" ")){ //abajo
                        String tag = (filas+1)+","+columnas;
                        c = new Conexion(tag);
                        v.conexiones.add(c);
                    }
                }
            }
        }
        return c;
    }
    
    public static void DFS(Grafo g, String inicio, String fin, String[][] matriz){
       
        boolean encontrado = false; 
        Queue<Vertice> queue = new LinkedList<>();
        Vertice v = BuscaVertice(g, inicio);
        queue.add(v);
        do {
            v = queue.remove();
            if(v.etiqueta.equals(fin)){
                encontrado = true;
            } else {
                v.visitado = true;
                addAdjacent(queue,g,v);
            }
            //MuestraQueue(queue);
            ImprimeSolucion(queue,matriz); 
            
        }
        
        while (encontrado == false && queue.isEmpty() == false);
            if (encontrado = true){
                System.out.println("Se encontró la salida!");
            }
        
        
       
    }
    
     public static Vertice BuscaVertice (Grafo grafo, String etiqueta){
        for(Vertice vertice : grafo.vertices){
            if (vertice.etiqueta.equals(etiqueta)){
                return vertice;
            }
        }
        return null;
    }
    
    public static void addAdjacent(Queue<Vertice> q, Grafo gr, Vertice v){
        for(Conexion c: v.conexiones){
            if(c!=null){
                for(Vertice vertice: gr.vertices){
                    if(c.etiqueta.equals(vertice.etiqueta)){
                        if(vertice.visitado == false){
                            q.add(vertice);
                        }
                    }
                }
            }
        }
    }
    
    public static void MuestraQueue(Queue<Vertice> q){
        System.out.print("queue: [");
        for (Vertice v : q){
            System.out.print(v.etiqueta+";");
        }
        System.out.println("]");
    }
    
    public static void ImprimeSolucion (Queue<Vertice> q,String[][] matriz){
            String coordenadas = null;
            String matriz_coordenadas = null;
            for (Vertice v: q){
            //System.out.println(v.etiqueta);
            int a,b;
            String[] partes = v.etiqueta.split(",");
            a = Integer.parseInt(partes[0]);
            b = Integer.parseInt(partes[1]);
            coordenadas= a+","+b;
            
            //System.out.println(coordenadas);  
            for(int filas=0; filas < matriz.length; filas++){
            for(int columnas=0; columnas < matriz.length; columnas++){  
                        matriz_coordenadas = filas+","+columnas;
                        if(matriz_coordenadas.equals(coordenadas)){
                            
                            matriz[filas][columnas]="*";
                            DespliegaLaberinto(matriz);
                            
                            
                        }
            }
            System.out.println("");     
        }
            }
            
        }
    
    public static String puntos_final_g(String[][] matriz){
        int filas_final,colum_final;
        Scanner leer = new Scanner (System.in);
        int [] pos_final = new int[2];
        for (int i = 0; i < matriz.length; i++) {
             for (int j = 0; j < matriz[0].length; j++) {
                 if (matriz[i][j].equals("x")) {
                     pos_final[0]=i;
                     pos_final[1]=j;
                     
                 }
             }
         }
        filas_final=pos_final[0];
        colum_final=pos_final[1];
        String punto_final = filas_final+","+colum_final;
        return punto_final;
    }
    public static String puntos_inicio_g(String [][] matriz){
        int filas,colum;
        Scanner leer = new Scanner (System.in);
        //buscar punto de inicio 
        int [] inicio= new int[2]; 
        for (int i = 0; i < matriz.length; i++) {
             for (int j = 0; j < matriz[0].length; j++) {
                 if (matriz[i][j].equals("*")) {
                     inicio[0]=i;
                     inicio[1]=j;
                 }
             }
         }
        filas=inicio[0];
        colum=inicio[1];
        String punto_inicio = filas+","+colum;
        return punto_inicio;
    }
    
    public static void DespliegaLaberinto(String[][] laberinto){
        for (int i = 0; i < laberinto.length; i++) {
            for (int j = 0; j < laberinto[i].length; j++) {
                System.out.print(laberinto[i][j]);
            }
            System.out.println("");
        }
    }
}
