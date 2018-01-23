package drawingEditor.model.formes;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * @see Forme
 */
public abstract class FormeImpl implements Forme {

    private static class MyColorSerializable implements Serializable
    {
        double red;
        double green;
        double blue;

        public MyColorSerializable(double red, double green, double blue) {
            this.red = red;
            this.green = green;
            this.blue = blue;
        }
    }

    private final DoubleProperty x;
    private final DoubleProperty y;
    private final DoubleProperty height;
    private final DoubleProperty width;
    private final ObjectProperty<Paint> couleur;


    public FormeImpl(double x, double y, double hauteur, double largeur, Paint couleur) {
        this.x = new SimpleDoubleProperty(x);
        this.y = new SimpleDoubleProperty(y);
        this.height = new SimpleDoubleProperty(hauteur);
        this.width = new SimpleDoubleProperty(largeur);
        this.couleur = new SimpleObjectProperty<Paint>(couleur);
    }
    public FormeImpl()//default constructor for the dynamic instancation, the values of attributes must be changed manually after that.
    {
        this(0,0,0,0,Color.BLACK);
    }
    @Override
    public List<Object> serializableAttributes() {
        ArrayList<Object> list=new ArrayList<>(5);
        list.add(getClass());//On creera une nouvelle instance avec ça
        list.add(x.getValue());
        list.add(y.getValue());
        list.add(width.getValue());
        list.add(height.getValue());
        if( couleur.getValue() instanceof Color)
        {

            Color c = (Color) couleur.getValue();
            MyColorSerializable col = new MyColorSerializable(c.getRed(),c.getGreen(),c.getBlue());//on passe avec une autre classe qui est sérialisable

            list.add(col);
        }else
            list.add(new MyColorSerializable(0,0,0));
        return list;
    }

    @Override
    public void loadUnserializingAttributes(List<Object> list)
    {
        setX((double)list.get(1));
        setY((double)list.get(2));
        setWidth((double)list.get(3));
        setHeight((double)list.get(4));
        MyColorSerializable c = (MyColorSerializable) list.get(5);
        setCouleur(Color.color(c.red,c.green,c.blue));
    }

    @Override
    public ObjectProperty<Paint> couleurProperty() {
        return couleur;
    }

    @Override
    public DoubleProperty positionXProperty() {
        return x;
    }

    @Override
    public DoubleProperty positionYProperty() {
        return y;
    }

    @Override
    public DoubleProperty widthProperty() {
        return width;
    }

    @Override
    public DoubleProperty heightProperty() {
        return height;
    }

    @Override
    public Paint getCouleur() {
        return couleurProperty().getValue();
    }

    public void setX(double x) {
        this.x.set(x);
    }

    public void setY(double y) {
        this.y.set(y);
    }

    public void setHeight(double height) {
        this.height.set(height);
    }

    public void setWidth(double width) {
        this.width.set(width);
    }

    @Override
    public double getPositionX() {
        return positionXProperty().doubleValue();
    }

    @Override
    public double getPositionY() {
        return positionYProperty().doubleValue();
    }

    @Override
    public double getWidth() {
        return widthProperty().doubleValue();
    }

    @Override
    public double getHeight() {
        return heightProperty().doubleValue();
    }

    @Override
    public void setCouleur(Paint col) {
        this.couleurProperty().setValue(col);
    }

    @Override
    public void setPosition(double x, double y) {
        this.positionXProperty().setValue(x);
        this.positionYProperty().setValue(y);
    }

    @Override
    public void deplacer(double tx, double ty) {
        setPosition(positionXProperty().getValue()+tx,positionYProperty().getValue()+tx);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        FormeImpl forme = (FormeImpl) o;

        if (x != null ? !x.equals(forme.x) : forme.x != null) return false;
        if (y != null ? !y.equals(forme.y) : forme.y != null) return false;
        if (height != null ? !height.equals(forme.height) : forme.height != null) return false;
        if (width != null ? !width.equals(forme.width) : forme.width != null) return false;
        return couleur != null ? couleur.equals(forme.couleur) : forme.couleur == null;
    }

    @Override
    public int hashCode() {
        int result = x != null ? x.hashCode() : 0;
        result = 31 * result + (y != null ? y.hashCode() : 0);
        result = 31 * result + (height != null ? height.hashCode() : 0);
        result = 31 * result + (width != null ? width.hashCode() : 0);
        result = 31 * result + (couleur != null ? couleur.hashCode() : 0);
        return result;
    }
}
