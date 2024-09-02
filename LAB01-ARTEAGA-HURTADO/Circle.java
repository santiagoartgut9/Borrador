import java.awt.*;
import java.awt.geom.*;
import java.util.Random;

/**
 * A circle that can be manipulated and that draws itself on a canvas.
 * 
 * @author  Michael Kolling and David J. Barnes
 * @version 1.0.  (15 July 2000) 
 */

public class Circle{

    public static final float PI=3.1416f;
    
    private int diameter;
    private int xPosition;
    private int yPosition;
    private String color;
    private boolean isVisible;
    

    public Circle(){
        diameter = 30;
        xPosition = 20;
        yPosition = 15;
        color = "blue";
        isVisible = false;
    }


    /**
     * constructor 2
     */
    
    public Circle(int diameter, int xPosition, int yPosition, String color, boolean isVisible) {
        this.diameter = diameter;
        this.xPosition = xPosition;
        this.yPosition = yPosition;
        this.color = color;
        this.isVisible = isVisible;

        if (isVisible) {
            draw();  // Dibuja el círculo si es visible al crearlo
        }
    }
    
    /**
     * perimeter of a circle
     */
    public float perimetro() {
        return PI * diameter;
    }
    
     public double area() {
        double radius = diameter / 2.0;
        return PI * radius * radius;
    }

 
    public void change(char operator, int value) {
        double currentArea = area();
        double newArea = currentArea;

        switch (operator) {
            case '+':
                newArea = currentArea + value;
                break;
            case '-':
                newArea = currentArea - value;
                break;
            case '*':
                newArea = currentArea * value;
                break;
            case '/':
                if (value != 0) {
                    newArea = currentArea / value;
                } else {
                    System.out.println("Error: División por cero");
                    return;
                }
                break;
            default:
                System.out.println("Operador no válido");
                return;
        }

        if (newArea > 0) {
            
            diameter = (int) Math.sqrt(newArea / PI) * 2;
            draw();
        } else {
            System.out.println("El área resultante es inválida");
        }
    }
    
    public void roll(int times, int maxDistance) {
        Random random = new Random();

        for (int i = 0; i < times; i++) {
            int distance = random.nextInt(maxDistance);
            int angle = random.nextInt(91); 

            double radians = Math.toRadians(angle);
            int deltaX = (int) (distance * Math.cos(radians));
            int deltaY = (int) (distance * Math.sin(radians));

            xPosition += deltaX;
            yPosition += deltaY;

            draw();
        }
    }
    
    /**
     * MoveTo method 
     */    
    public void moveTo(int newX, int newY) {
        erase(); 
        xPosition = newX; 
        yPosition = newY; 
        draw(); 
    }


       
    public void makeVisible(){
        isVisible = true;
        draw();
    }
    

    public void makeInvisible(){
        erase();
        isVisible = false;
    }

    private void draw(){
        if(isVisible) {
            Canvas canvas = Canvas.getCanvas();
            canvas.draw(this, color, 
                new Ellipse2D.Double(xPosition, yPosition, 
                diameter, diameter));
            canvas.wait(10);
        }
    }

    private void erase(){
        if(isVisible) {
            Canvas canvas = Canvas.getCanvas();
            canvas.erase(this);
        }
    }
    
    /**
     * Move the circle a few pixels to the right.
     */
    public void moveRight(){
        moveHorizontal(20);
    }

    /**
     * Move the circle a few pixels to the left.
     */
    public void moveLeft(){
        moveHorizontal(-20);
    }

    /**
     * Move the circle a few pixels up.
     */
    public void moveUp(){
        moveVertical(-20);
    }

    /**
     * Move the circle a few pixels down.
     */
    public void moveDown(){
        moveVertical(20);
    }

    /**
     * Move the circle horizontally.
     * @param distance the desired distance in pixels
     */
    public void moveHorizontal(int distance){
        erase();
        xPosition += distance;
        draw();
    }

    /**
     * Move the circle vertically.
     * @param distance the desired distance in pixels
     */
    public void moveVertical(int distance){
        erase();
        yPosition += distance;
        draw();
    }

    /**
     * Slowly move the circle horizontally.
     * @param distance the desired distance in pixels
     */
    public void slowMoveHorizontal(int distance){
        int delta;

        if(distance < 0) {
            delta = -1;
            distance = -distance;
        } else {
            delta = 1;
        }

        for(int i = 0; i < distance; i++){
            xPosition += delta;
            draw();
        }
    }

    /**
     * Slowly move the circle vertically
     * @param distance the desired distance in pixels
     */
    public void slowMoveVertical(int distance){
        int delta;

        if(distance < 0) {
            delta = -1;
            distance = -distance;
        }else {
            delta = 1;
        }

        for(int i = 0; i < distance; i++){
            yPosition += delta;
            draw();
        }
    }

    /**
     * Change the size.
     * @param newDiameter the new size (in pixels). Size must be >=0.
     */
    public void changeSize(int newDiameter){
        erase();
        diameter = newDiameter;
        draw();
    }

    /**
     * Change the color. 
     * @param color the new color. Valid colors are "red", "yellow", "blue", "green",
     * "magenta" and "black".
     */
    public void changeColor(String newColor){
        color = newColor;
        draw();
    }



}
