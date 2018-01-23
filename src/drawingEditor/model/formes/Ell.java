package drawingEditor.model.formes;

import javafx.scene.paint.Paint;

/**
 * @see FormeImpl
 * Model for Ellipse
 */
public class Ell extends FormeImpl
{
    public Ell(double x, double y, double hauteur, double largeur, Paint couleur) {
        super(x, y, hauteur, largeur, couleur);
    }
    public Ell()
    {
        super();
    }

}
