package drawingEditor.model.formes;

import javafx.scene.paint.Paint;

/**
 * @see FormeImpl
 * Model for Ellipse
 */
public class Point extends FormeImpl {
    public Point(double x, double y, double hauteur, double largeur, Paint couleur) {
        super(x, y, hauteur, largeur, couleur);
    }

    public Point()
    {
        super();
    }
}
