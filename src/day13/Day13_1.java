package day13;

import utils.Constantes;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.Vector;
import java.util.*;

public class Day13_1 {

    static class Node implements Comparable<Node>
    {
        int key;
        Vector<Node > child = new Vector<>();


        public int compareTo(Node o) {
            int ret = -1;
            for(int i= 0; i <this.child.size(); i++){
                int valorNodo = this.child.get(i).key;

                if(valorNodo==-1){
                    //Es otro nodo
                    valorNodo = getValorNodo(this.child.get(i).child.get(0));
                }

                //Comprobar si el Nodo 2 tiene elementos
                int valorNodoComparativo = getValorNodo(o);
            }
            return ret;
        }

        public int getValorNodo(Node nodo){
            int ret = -1;


            return ret;
        }
    };




    static int cont = 0;

    public static void main(String[] args) {
        File archivo = null;
        FileReader fr = null;
        BufferedReader br = null;


        try {
            archivo = new File (Constantes.inputRoot + "input9.txt");
            fr = new FileReader(archivo);
            br = new BufferedReader(fr);

            Node root = new Node();
            (root.child).add(newNode(1));
            (root.child).add(newNode(-1));  //1
            (root.child).add(newNode(8));
            (root.child).add(newNode(9));
            (root.child.get(1).child).add(newNode(2));
            (root.child.get(0).child).add(newNode(-1));
            (root.child.get(0).child.get(1).child).add(newNode(3));
            (root.child.get(0).child.get(1).child).add(newNode(-1));
            (root.child.get(0).child.get(1).child.get(1).child).add(newNode(4));
            (root.child.get(0).child.get(1).child.get(1).child).add(newNode(-1));
            (root.child.get(0).child.get(1).child.get(1).child.get(1).child).add(newNode(8);
            (root.child.get(0).child.get(1).child.get(1).child.get(1).child).add(newNode(6);
            (root.child.get(0).child.get(1).child.get(1).child.get(1).child).add(newNode(7);

            Node root2 = new Node();
            (root2.child).add(newNode(1));
            (root2.child).add(newNode(-1));  //1
            (root2.child).add(newNode(8));
            (root2.child).add(newNode(9));
            (root2.child.get(1).child).add(newNode(2));
            (root2.child.get(0).child).add(newNode(-1));
            (root2.child.get(0).child.get(1).child).add(newNode(3));
            (root2.child.get(0).child.get(1).child).add(newNode(-1));
            (root2.child.get(0).child.get(1).child.get(1).child).add(newNode(4));
            (root2.child.get(0).child.get(1).child.get(1).child).add(newNode(-1));
            (root2.child.get(0).child.get(1).child.get(1).child.get(1).child).add(newNode(8);
            (root2.child.get(0).child.get(1).child.get(1).child.get(1).child).add(newNode(6);
            (root2.child.get(0).child.get(1).child.get(1).child.get(1).child).add(newNode(7);




            // Lectura del fichero
            String linea;

            while((linea=br.readLine())!=null){
                if(linea.trim().length()!=0){
//                    procesaLinea(linea);
                }
            }





        }
        catch(Exception e){
            e.printStackTrace();
        }finally{
            try{
                if( null != fr ){
                    fr.close();
                }
            }catch (Exception e2){
                e2.printStackTrace();
            }
        }
    }

    static void LevelOrderTraversal(Node root)
    {
        if (root == null)
            return;

        // Standard level order traversal code
        // using queue
        Queue<Node> q = new LinkedList<>(); // Create a queue
        q.add(root); // Enqueue root
        while (!q.isEmpty())
        {
            int n = q.size();

            // If this node has children
            while (n > 0)
            {
                // Dequeue an item from queue
                // and print it
                Node p = q.peek();
                q.remove();
                System.out.print(p.key + " ");

                // Enqueue all children of
                // the dequeued item
                for (int i = 0; i < p.child.size(); i++)
                    q.add(p.child.get(i));
                n--;
            }

            // Print new line between two levels
            System.out.println();
        }
    }

    static Node newNode(int key)
    {
        Node temp = new Node();
        temp.key = key;
        return temp;
    }


}
