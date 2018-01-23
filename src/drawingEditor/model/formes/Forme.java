package drawingEditor.model.formes;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.ObjectProperty;
import javafx.scene.paint.Paint;

import java.util.List;

public interface Forme {

	/**
	 * @return a serializable list containing the attributes
	 */
	List<Object> serializableAttributes();

	/**
	 * Load the attributes from the list
	 * @param list
	 */
	void loadUnserializingAttributes(List<Object> list);

	Paint getCouleur();
	
	double getPositionX();
	
	double getPositionY();
	
	double getWidth();
	
	double getHeight();

	 void setX(double x);

	 void setY(double y);

	 void setHeight(double height);

	 void setWidth(double width);

	void setCouleur(final Paint col);
	
	void setPosition(final double x, final double y);
	
	void deplacer(final double tx, final double ty);
	
//	void setWidth(final double w);
	
//	void setHeight(final double h);
	
	ObjectProperty<Paint> couleurProperty();
	
	DoubleProperty positionXProperty();
	
	DoubleProperty positionYProperty();
	
	DoubleProperty widthProperty();
	
	DoubleProperty heightProperty();
}
