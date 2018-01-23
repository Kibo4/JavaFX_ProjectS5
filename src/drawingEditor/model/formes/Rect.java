package drawingEditor.model.formes;

import javafx.scene.paint.Paint;

/**
 * @see FormeImpl
 * Model for Rectangle
 */
public class Rect extends FormeImpl {
    public Rect(double x, double y, double hauteur, double largeur, Paint couleur) {
        super(x, y, hauteur, largeur, couleur);
    }

    public Rect() {
        super();
    }


}
