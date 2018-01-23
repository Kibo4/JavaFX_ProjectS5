package drawingEditor.model.formes;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;

import java.util.ArrayList;
import java.util.List;

/**
 * @see FormeImpl
 * Model for ligne
 */
public class Ligne extends FormeImpl {
    private DoubleProperty fx;
    private DoubleProperty fy;
    public Ligne(double dx, double dy, double fx,double fy,double hauteur, double largeur, Paint couleur) {
        super(dx, dy, hauteur, largeur, couleur);
        this.fx = new SimpleDoubleProperty(fx);
        this.fy = new SimpleDoubleProperty(fy);
    }
    public Ligne()
    {
       this(0,0,0,0,0,0,Color.BLACK);
    }

    @Override
    public List<Object> serializableAttributes() {
        List<Object> list=super.serializableAttributes();
        list.add(fx.getValue());
        list.add(fy.getValue());
        return list;
    }

    @Override
    public void loadUnserializingAttributes(List<Object> list)
    {
        super.loadUnserializingAttributes(list);
        setFx((double)list.get(6));
        setFy((double)list.get(7));
    }

    public double getFx() {
        return fx.get();
    }

    public DoubleProperty fxProperty() {
        return fx;
    }

    public void setFx(double fx) {
        this.fx.set(fx);
    }

    public double getFy() {
        return fy.get();
    }

    public DoubleProperty fyProperty() {
        return fy;
    }

    public void setFy(double fy) {
        this.fy.set(fy);
    }
}
