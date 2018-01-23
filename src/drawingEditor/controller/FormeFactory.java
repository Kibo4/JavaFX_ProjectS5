package drawingEditor.controller;

import drawingEditor.model.exceptions.UnknownFormException;
import drawingEditor.model.formes.*;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Ellipse;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;


public class FormeFactory
{
    /**
     * factory which get he model instance according to the id param
     * @param id
     * @param x
     * @param y
     * @param hauteur
     * @param largeur
     * @param couleur
     * @return the Form that correspond to the id
     * @throws UnknownFormException if the id correspond to none of the known form
     */
    public static Forme getModelInstance(String id, double x, double y, double hauteur, double largeur, Paint couleur) throws UnknownFormException {
            switch (id)
            {
                case "ellipse":return new Ell(x,y,hauteur,largeur,couleur);
                case "point":return new Point(x,y,hauteur,largeur,couleur);
                case "rectangle":return new Rect(x,y,hauteur,largeur,couleur);
            }

           throw new UnknownFormException("Forme inconnue");
    }

    /**
     * @param forme
     * @return a shape binded with the form forme
     */
    public static Shape getShapeInstance(Forme forme)  {
        if(forme instanceof Ell)
        {
            Ellipse ellView = new Ellipse();
            ellView.centerXProperty().bind(forme.positionXProperty());
            ellView.centerYProperty().bind(forme.positionYProperty());
            ellView.radiusXProperty().bind(forme.widthProperty().divide(2.));
            ellView.radiusYProperty().bind(forme.heightProperty().divide(2.));
            ellView.fillProperty().bind(forme.couleurProperty());
            return ellView;
        }

        if(forme instanceof Point)//un Point n'est pas modifiable
        {

            Line line = new Line(
                    forme.getPositionX()-forme.getWidth()/2.,
                    forme.getPositionY()-forme.getHeight()/2.,
                    forme.getPositionX()+forme.getWidth()/2.,
                    forme.getPositionY()+forme.getHeight()/2.
            ); // \

            Line line2 = new Line(
                    forme.getPositionX()+forme.getWidth()/2.,
                    forme.getPositionY()-forme.getHeight()/2.,
                    forme.getPositionX()-forme.getWidth()/2.,
                    forme.getPositionY()+forme.getHeight()/2.); // /


            Shape s=  Shape.union(line,line2);
            s.fillProperty().bind(forme.couleurProperty());
            return s;
        }

        if(forme instanceof Ligne)//une ligne n'est pas modifiable
        {
            Line line = new Line(
                    forme.getPositionX(),
                    forme.getPositionY(),
                    ((Ligne) forme).getFx(),
                    ((Ligne) forme).getFy()
            );
            return line;
        }


      //  if(forme instanceof Rect) {
        //by default return a Rectangle
            Rectangle recView = new Rectangle();
            recView.xProperty().bind(forme.positionXProperty().add(-forme.getWidth()/2.));
            recView.yProperty().bind(forme.positionYProperty().add(-forme.getHeight()/2.));
            recView.widthProperty().bind(forme.widthProperty());
            recView.heightProperty().bind(forme.heightProperty());
            recView.fillProperty().bind(forme.couleurProperty());
            return recView;



     //   }

    }

}
