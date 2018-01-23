package drawingEditor.model;

import drawingEditor.model.formes.Forme;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;


/**
 * @see Dessin
 */
public class DessinImpl implements Dessin{
   public final ObservableList<Forme> formes;

    public DessinImpl() {
        this.formes=FXCollections.observableArrayList();
    }


    @Override
    public List<List<Object>> serializable() {
        ArrayList<List<Object>> li=new ArrayList<>(formes.size());
        for(Forme f: formes)
        {
            li.add(f.serializableAttributes());
        }
        return li;
    }

    @Override
    public void loadFromSerialized(List<List<Object>> l) {

        formes.clear();
        for(List<Object> listeAtt : l)
        {
            try {
                Forme f = (Forme) ((Class) listeAtt.get(0)).newInstance();
                f.loadUnserializingAttributes(listeAtt);
                formes.add(f);
            }catch (InstantiationException | ClassCastException | IllegalAccessException e) {

                System.err.println("Erreur lors de la désérialisation \n attention à avoir un constructeur vide dans toutes les formes");
            }
        }
    }

    @Override
    public void ajouterForme(Forme shape) {
        formes.add(shape);

    }

    @Override
    public void supprimerForme(Forme shape) {
        formes.remove(shape);

    }

    @Override
    public ObservableList<Forme> getFormes() {
        return this.formes;
    }

    @Override
    public void vider() {
        formes.clear();
    }
}
