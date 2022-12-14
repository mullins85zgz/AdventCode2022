package day12;

import utils.Constantes;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.*;

public class Day12_1 {

    static ArrayList<Point> movementGrid = new ArrayList<>();

    static boolean firstSearch=true;
    static int cont = 0;
    static int cont2 = 0;
    static boolean finished=false;

    static int GRID_COLS = 8;
    static int GRID_ROWS = 5;

    static int BUFFERS_MAX_MOVES = 999999;

    static HashMap<String,Point> visitados = new HashMap<>();

    public static void main(String[] args) {
        File archivo = null;
        FileReader fr = null;
        BufferedReader br = null;


        try {
            archivo = new File (Constantes.inputRoot + "input12tst.txt");
            fr = new FileReader(archivo);
            br = new BufferedReader(fr);

            Grid grid = new Grid(GRID_ROWS,GRID_COLS);

            // Lectura del fichero
            String linea;
            int cont =0;

            //init movements
            movementGrid.add(new Point(-1,0));  //E
            movementGrid.add(new Point(1,0));   //N
            movementGrid.add(new Point(0,1));   //S
            movementGrid.add(new Point(0,-1));  //W

            while((linea=br.readLine())!=null){
                if(linea.trim().length()!=0){
                    lee(linea,cont,grid);
                    cont++;
                }
            }

            //Algoritmo navegacion
            Path sorthest = itera(grid);

//            ArrayList<Point> points = new ArrayList<>();
//            points.add(grid.getStartingPoint());
//            System.out.println(moveAll(grid,points));

            System.out.println(BUFFERS_MAX_MOVES);
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

    public static int moveAll(Grid grid,ArrayList<Point> points){
        while(!finished){
            //Busqueda
            for(Point point:points){
                visitados.put("COL" + point.getCol() + "ROW" + point.getRow() ,point);
                if(grid.getGrid()[point.getRow()][point.getCol()] == 0)
                    break;  //Encontrado!!

                ArrayList<Point> nextPoints = new ArrayList<>();

                //Movimientos
                for(Point dir:movementGrid){
                    int row = point.getRow() + dir.getRow();
                    int col = point.getCol() + dir.getCol();
                    if((col==-1 || row==-1 || col>=GRID_COLS || row >= GRID_ROWS)==false){
                        int canMove = isMovementPossible2(point,dir,grid);
                        if(grid.getGrid()[row][col]==0) finished = true;
                        if(canMove==1)
                            nextPoints.add(new Point(row,col));
                    }
                }
                cont2++;
                System.out.println(cont2);
                moveAll(grid,nextPoints);
            }
            //Iteracion
        }
        return cont2;
    }

    public static Path itera(Grid grid){
        Path ìnitPath = new Path();
        ìnitPath.setActualPoint(grid.getStartingPoint());
        ìnitPath.getVisited().put("COL" + grid.getStartingPoint().getCol() + "ROW" + grid.getStartingPoint().getRow() ,grid.getStartingPoint());

        ArrayList<Path> paths = new ArrayList<>();
        paths.add(ìnitPath);

        generaPaths(paths,grid);

        return ìnitPath;        //todo cambiar esto
    }

    public static void generaPaths(ArrayList<Path> paths,Grid grid ){
        while(cont<paths.size()){
            Path path = paths.get(cont);
            Point actualPathPoint = path.getActualPoint();
            boolean openPath=false;
            boolean closedPath = true;
            for(Point cardinal:movementGrid){
                int canMove = isMovementPossible(actualPathPoint,cardinal,grid,path);
                if(canMove==0 || canMove==2){
                    //Si no podemos mover o es el final,finalizamos el Path, por tanto sumamos uno

                    if(canMove==2)
                        move(path,cardinal);
                }else{
                    closedPath = false;
                    //Si podemos mover y no estamos
                    if(openPath){
                        Path newPath = new Path();
                        newPath.setNumMovs(path.getNumMovs());
                        newPath.setActualPoint(path.getActualPoint());
                        newPath.setVisited(path.getVisited());
                        paths.add(newPath);
                    }else{
                        openPath=true;
                        //Now we move
                        move(path,cardinal); //Solo 1 mov
                    }
                }
            }
            if(closedPath)  cont++;
        }
    }

    /**
     * 0 = NO MOVE
     * 1 = MOVE
     * 2 = END
     * @param origen
     * @param dir
     * @param grid
     * @param actualPath
     * @return
     */
    public static int isMovementPossible(Point origen,Point dir,Grid grid,Path actualPath){

        int ret=0;

        int row = origen.getRow() + dir.getRow();
        int col = origen.getCol() + dir.getCol();

        //Limits
        if(col==-1 || row==-1 || col>=GRID_COLS || row >= GRID_ROWS)
            return 0;

        //Visited
        if(actualPath.getVisited().containsKey("COL" + col + "ROW" + row))
            return 0;

        //Can move?
        int valueActualPoint = grid.getGrid()[row][col];
        int destActualPoint = grid.getGrid()[row][col];
        if(cont==0){
            System.out.println("ROW:" +  origen.getRow() + " COL:"+ origen.getCol() );
            System.out.println("ACT:" + valueActualPoint + " DEST:"+ destActualPoint);
        }

        if(destActualPoint - valueActualPoint > 1)
            return 0;

        //We can move
//        actualPath.incMovs();
//        actualPath.setActualPoint(new Point(row,col));
//        actualPath.getVisited().put("COL" + col + "ROW" + row, new Point(row,col));

        //SI los movimientos superan a uno finalizado cortamos para ahorrar recursos
        if(actualPath.getNumMovs()>=BUFFERS_MAX_MOVES)
            return 0;

        //Is End
        if(destActualPoint==0) {
            ret =  2;
            actualPath.setFinished(true);
            BUFFERS_MAX_MOVES = actualPath.getNumMovs();
        }else{
            ret = 1;
        }

        return ret;
    }

    public static int isMovementPossible2(Point origen,Point dir,Grid grid){

        int ret=0;

        int row = origen.getRow() + dir.getRow();
        int col = origen.getCol() + dir.getCol();

        //Limits
        if(col==-1 || row==-1 || col>=GRID_COLS || row >= GRID_ROWS)
            return 0;

        //Visited
        if(visitados.containsKey("COL" + col + "ROW" + row))
            return 0;

        //Can move?
        int valueActualPoint = grid.getGrid()[row][col];
        int destActualPoint = grid.getGrid()[row][col];

        if(destActualPoint > valueActualPoint + 1)
            return 0;

        //We can move
//        actualPath.incMovs();
//        actualPath.setActualPoint(new Point(row,col));
//        actualPath.getVisited().put("COL" + col + "ROW" + row, new Point(row,col));

        //SI los movimientos superan a uno finalizado cortamos para ahorrar recursos


        //Is End
        if(destActualPoint==0) {
            ret =  2;
        }else{
            ret = 1;
        }

        return ret;
    }

    public static void move(Path path,Point dir){
        Point origen = path.getActualPoint();
        int row = origen.getRow() + dir.getRow();
        int col = origen.getCol() + dir.getCol();
        //We can move
        path.incMovs();
        path.setActualPoint(new Point(row,col));
        path.getVisited().put("COL" + col + "ROW" + row, new Point(row,col));
        if(cont==0) System.out.println(dir.toString());
    }

    public static void lee(String linea,int row,Grid grid){
        for(int i=0;i<linea.length();i++){
//            System.out.println("LINEA " + row + " CHAR " + linea.charAt(i) + " INT " + (int)linea.charAt(i));
            if("S".equals(String.valueOf(linea.charAt(i)))){
                grid.getGrid()[row][i] = 999;
                grid.getStartingPoint().setRow(row);
                grid.getStartingPoint().setCol(i);

            }else if("E".equals(String.valueOf(linea.charAt(i)))){
                grid.getGrid()[row][i] = 0; //A la salida la marcamos como 0 para que siempre lleguen
            }else {
                grid.getGrid()[row][i] = linea.charAt(i);
            }
        }
    }

}

class Path{
    int numMovs = 0;
    ArrayList<int[]> movements = new ArrayList<>();
    Point actualPoint;

    boolean finished = false;

    HashMap<String,Point> visited = new HashMap();

    public void incMovs(){
        numMovs++;
    }

    public int getNumMovs() {
        return numMovs;
    }

    public void setNumMovs(int numMovs) {
        this.numMovs = numMovs;
    }

    public ArrayList<int[]> getMovements() {
        return movements;
    }

    public void setMovements(ArrayList<int[]> movements) {
        this.movements = movements;
    }

    public Point getActualPoint() {
        return actualPoint;
    }

    public void setActualPoint(Point actualPoint) {
        this.actualPoint = actualPoint;
    }

    public HashMap<String, Point> getVisited() {
        return visited;
    }

    public void setVisited(HashMap<String, Point> visited) {
        this.visited = visited;
    }

    public boolean isFinished() {
        return finished;
    }

    public void setFinished(boolean finished) {
        this.finished = finished;
    }
}

class Point{
    int row;
    int col;

    public Point(int row, int col) {
        this.row = row;
        this.col = col;
    }

    public int getRow() {
        return row;
    }

    public void setRow(int row) {
        this.row = row;
    }

    public int getCol() {
        return col;
    }

    public void setCol(int col) {
        this.col = col;
    }

    @Override
    public String toString() {

        String ret = "UNKNOWN";

        if(row==-1 && col==0 ) ret = "W";
        if(row==1 && col==0 ) ret = "N";
        if(row==0 && col==1 ) ret = "S";
        if(row==-0 && col==-1 ) ret = "E";

        return ret;
    }
}

class Grid{
    int[][] grid;

    Point startingPoint = new Point(0,0);

    int rows = 1;
    int cols = 1;

    Grid(int rows,int cols){
        this.rows = rows;
        this.cols = cols;
        grid = new int[rows][cols];
    }

    public int[][] getGrid() {
        return grid;
    }

    public void setGrid(int[][] grid) {
        this.grid = grid;
    }

    public int getRows() {
        return rows;
    }

    public void setRows(int rows) {
        this.rows = rows;
    }

    public int getCols() {
        return cols;
    }

    public void setCols(int cols) {
        this.cols = cols;
    }

    public Point getStartingPoint() {
        return startingPoint;
    }

    public void setStartingPoint(Point startingPoint) {
        this.startingPoint = startingPoint;
    }
}
