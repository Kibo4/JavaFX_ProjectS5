package drawingEditor.model;

import drawingEditor.model.formes.Forme;
import javafx.collections.ObservableList;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public interface Dessin {

	/**
	 * @return a serializable list representing the 'dessin'
	 */
	List<List<Object>> serializable();//les éléments observables ne sont pas sérializables

	/**
	 * Update the 'Dessin' with the list 'l' in order to load a draw
	 * @param l
	 */
	void loadFromSerialized(List<List<Object>> l );

	/**
	 * Add a shape in the draw
	 * @param shape
	 */
	void ajouterForme(final Forme shape);

	/**
	 * Delete the shape from the draw
	 * @param shape
	 */
	void supprimerForme(final Forme shape);

	/**
	 * getter of the observable list
	 * @return the observable list
	 */
	ObservableList<Forme> getFormes();

	/**
	 * empty the draw
	 */
	void vider();
}