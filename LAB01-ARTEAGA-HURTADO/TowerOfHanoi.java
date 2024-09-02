 import java.util.ArrayList;
import java.util.Stack;

public class TowerOfHanoi {

    private Rectangle base;
    private Rectangle pole;
    private Rectangle pole2;
    private Rectangle pole3;
    private ArrayList<Rectangle> discs;
    private boolean isVisible;

    // Crear tres columnas
    private Stack<Rectangle>[] columns;

    public TowerOfHanoi(int xPosition, int yPosition) {
        base = new Rectangle();
        base.changeSize(20, 550);
        base.changeColor("blue");
        base.moveTo(xPosition, yPosition + 100);

        pole = new Rectangle();
        pole.changeSize(100, 20);
        pole.changeColor("blue");
        pole.moveTo(xPosition + 65, yPosition);

        pole2 = new Rectangle();
        pole2.changeSize(100, 20);
        pole2.changeColor("blue");
        pole2.moveTo(xPosition + 235, yPosition);

        pole3 = new Rectangle();
        pole3.changeSize(100, 20);
        pole3.changeColor("blue");
        pole3.moveTo(xPosition + 405, yPosition);

        // Inicializar columnas
        columns = new Stack[3];
        for (int i = 0; i < 3; i++) {
            columns[i] = new Stack<>();
        }
        
        discs = new ArrayList<>();
        crearDiscos(xPosition + 15, yPosition + 80);

        // Añadir discos a la primera columna (columna 0)
        for (Rectangle disc : discs) {
            columns[0].push(disc);
        }        
        
        
        
    }



    private void crearDiscos(int xPosition, int yPosition) {
        int width = 120;
        int height = 20;

        String[] colors = {"magenta", "green", "yellow", "black"};

        for (int i = 0; i < colors.length; i++) {
                Rectangle disc = new Rectangle();
                disc.changeSize(height, width);
                disc.changeColor(colors[i]);
                disc.moveTo(xPosition, yPosition);
                disc.setNombre("Disco " + (i + 1));  // Asigna un nombre al disco, como "Disco 1", "Disco 2", etc.
                discs.add(disc);
        
                yPosition -= height;
                width -= 20;
                xPosition += 10;
            }
    }

    public void makeVisible() {
        base.makeVisible();
        pole.makeVisible();
        pole2.makeVisible();
        pole3.makeVisible();

        for (Rectangle disc : discs) {
            disc.makeVisible();
        }

        isVisible = true;
    }

    public void makeInvisible() {
        base.makeInvisible();
        pole.makeInvisible();
        pole2.makeInvisible();
        pole3.makeInvisible();
        for (Rectangle disc : discs) {
            disc.makeInvisible();
        }
        isVisible = false;
    }


    public Rectangle pop(int fromColumn) {
        if (fromColumn < 0 || fromColumn > 2) {
            System.out.println("Columna fuera de rango");
            return null;
        }

        if (columns[fromColumn].isEmpty()) {
            System.out.println("No hay discos en la columna " + fromColumn);
            return null;
        }

        return columns[fromColumn].pop(); // Saca el disco de la columna indicada
    }

    public void push(Rectangle disc, int toColumn) {
        if (toColumn < 0 || toColumn > 2) {
            System.out.println("Columna fuera de rango");
            return;
        }

        if (!columns[toColumn].isEmpty() && disc.getWidth() > columns[toColumn].peek().getWidth()) {
            System.out.println("Movimiento inválido: no se puede colocar un disco más grande sobre uno más pequeño.");
            return;
        }

        columns[toColumn].push(disc); // Coloca el disco en la columna indicada
        if (isVisible) {
            disc.makeVisible();
        }

        updateDiscPositions(); // Actualizar las posiciones de los discos en la pantalla
    }

    public void moveDisc(int fromColumn, int toColumn) {
        Rectangle topDisc = pop(fromColumn); // Sacar el disco de la columna de origen
        if (topDisc != null) {
            push(topDisc, toColumn); // Colocar el disco en la columna de destino
        }
    }

    private void updateDiscPositions() {
        int[] xPositions = {35, 215, 380}; // Posiciones X de las tres columnas
        int baseY = base.getY() -20;

        for (int i = 0; i < 3; i++) {
            int yPos = baseY;

            for (Rectangle disc : columns[i]) {
                disc.moveTo(base.getX() + xPositions[i], yPos);
                yPos -= disc.getHeight();
            }
        }
    }
}
