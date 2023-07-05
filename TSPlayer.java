import javafx.geometry.Point2D;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

import java.util.ArrayList;

public class TSPlayer {

    /*
    may need an object to store the stamp num int -- arraylist<obg> index is the ordering of the stamps
    obj stores: time stamp end (double), circles - create from coord and color of the circles info, color of the background, move
    obj name will be - TSPlayer
     */

    private double tsNum; //marks the end of the time stamp
    private Color backgroundFill;
    private ArrayList<Circle> dots;
    private String movement;
    private Color dotCol;

    public TSPlayer(double endTS, Color circCol, Color bgCol, ArrayList<Point2D> coords, String move){

        dotCol = circCol;
        tsNum = endTS;
        movement = move;
        dots = new ArrayList<>();
        backgroundFill = bgCol;

        for(int i = 0; i < coords.size(); i++){
            Point2D c = coords.get(i);
            Circle dot = new Circle(c.getX(), c.getY(), Constants.DOT_RADIUS);
            dot.setFill(circCol);
            dots.add(dot);
        }

    }

    public ArrayList<Circle> getDs(){
        return dots;
    }

    public Color getDotCol(){
        return dotCol;
    }

    public Color getBG(){
        return backgroundFill;
    }

    public double getEnd(){
        return tsNum;
    }

    public String getMove(){
        return movement;
    }

    //helper -- only activated during testing
    public void printContents(){
        System.out.println("TIME STAMP: ENDTS " + tsNum + " movement " + movement + " dots " + dots.toString() + " background " + backgroundFill);
    }
}
