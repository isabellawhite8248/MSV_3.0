import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

public class objStatus {

    private Circle cir;
    private boolean selected;
    private Color originalColor;

    /*
    class is used to record status of user input of each circle in the
    anime pane - when the user clicks the status is updated -- of what color it needs
    to be using this object in an array stored in the animeBoard class.
     */

    public objStatus(Circle c){
        cir = c;
        selected = false;
        originalColor = (Color)cir.getFill();
    }

    public void changeStatus(Color currCol){
        if(!selected){
            selected = true;
            cir.setFill(currCol);
        } else {
            cir.setFill(originalColor);
            selected = false;
        }
    }

    //checks if a coordinate is in the bounds of a circle - pass the mouse coordinates through
    //through here to check if the status needs to be updated
    public boolean inBounds(double x, double y){

        double centerX = cir.getCenterX();
        double centerY = cir.getCenterY();
        double rad = cir.getRadius();

        if((x > (centerX - rad)) && (x < (centerX + rad))){ //check if it is in the x bounds
            if((y < (centerY + rad)) && (y > (centerY - rad))){ //check if it is in the y bounds
                return true;
            }
        }

        return false;
    }
}
