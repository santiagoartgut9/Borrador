import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Tower {

    private Rectangle base;
    private Rectangle pole;
    private Map<Rectangle, Position> removedDiscs;
    private ArrayList<Rectangle> discs;
    private boolean isVisible;

    private static class Position {
        int xPosition;
        int yPosition;

        Position(int xPosition, int yPosition) {
            this.xPosition = xPosition;
            this.yPosition = yPosition;
        }
    }

    public Tower(int xPosition, int yPosition) {
        base = new Rectangle();
        base.changeSize(20, 150);
        base.changeColor("blue");
        base.moveTo(xPosition, yPosition + 100);

        pole = new Rectangle();
        pole.changeSize(100, 20);
        pole.changeColor("blue");
        pole.moveTo(xPosition + 65, yPosition);

        discs = new ArrayList<>();
        removedDiscs = new HashMap<>();
        crearDiscos(xPosition + 15, yPosition + 80);
    }

    private void crearDiscos(int xPosition, int yPosition) {
        int width = 120;
        int height = 20;

        // Crear discos con diferentes colores
        String[] colors = {"magenta", "green", "yellow", "black"};

        for (String color : colors) {
            Rectangle disc = new Rectangle();
            disc.changeSize(height, width);
            disc.changeColor(color);
            disc.moveTo(xPosition, yPosition);
            discs.add(disc);

            yPosition -= height; // Subir posición para el siguiente disco
            width -= 20; // Reducir el tamaño del siguiente disco
            xPosition += 10; // Centrar el disco más pequeño
        }
    }

    public void makeVisible() {
        base.makeVisible();
        pole.makeVisible();

        // Hacer visibles todos los discos
        for (Rectangle disc : discs) {
            disc.makeVisible();
        }

        isVisible = true;
    }

    public void makeInvisible() {
        base.makeInvisible();
        pole.makeInvisible();
        for (Rectangle disc : discs) {
            disc.makeInvisible();
        }
        isVisible = false;
    }

    public void moveTo(int x, int y) {
        base.moveTo(x, y + 100);
        pole.moveTo(x + 65, y);
        updateDiscPositions();
    }

    public Rectangle pop() {
        if (discs.isEmpty()) {
            return null; // No hay discos para sacar
        }
        Rectangle topDisc = discs.remove(discs.size() - 1); // Elimina el disco de la lista
        topDisc.makeInvisible(); // Hacer el disco invisible
        // Guarda la posición del disco eliminado
        removedDiscs.put(topDisc, new Position(topDisc.getX(), topDisc.getY()));
        return topDisc; // Devolver el disco eliminado
    }

    public void push() {
        if (removedDiscs.isEmpty()) {
            return; // No hay discos para agregar
        }

        // Recupera el disco eliminado más recientemente
        Rectangle discToPush = null;
        for (Rectangle disc : removedDiscs.keySet()) {
            discToPush = disc;
            break;
        }

        // Recupera la posición original del disco
        Position position = removedDiscs.remove(discToPush);
        if (position != null) {
            // Coloca el disco en la posición original
            discToPush.moveTo(position.xPosition, position.yPosition);
            discs.add(0, discToPush); // Añade el disco al inicio de la lista
            if (isVisible) {
                discToPush.makeVisible();
            }
            // Actualiza la posición de los discos restantes si es necesario
        }
    }

    private void updateDiscPositions() {
        int xPos = base.getX() + 15;
        int yPos = base.getY() + 80;

        // Ajusta la posición de cada disco
        for (Rectangle disc : discs) {
            disc.moveTo(xPos, yPos);
            yPos -= disc.getHeight(); // Ajusta la posición vertical
            xPos += 10; // Ajusta la posición horizontal para centrar los discos
        }
    }
}
