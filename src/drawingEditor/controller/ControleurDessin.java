package drawingEditor.controller;

import drawingEditor.model.*;
import drawingEditor.model.exceptions.*;
import drawingEditor.model.formes.*;
import drawingEditor.model.tools.LoaderSaverIO;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Shape;
import java.io.*;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;


public class ControleurDessin implements Initializable {
    public Dessin dessin;
    @FXML private AnchorPane anchorPaneIO;
    @FXML private ListView<String> listeFiles;
    @FXML private Button clear;
    @FXML private Label labelInfo;
    @FXML private Label labelIO;
    @FXML private Button valid;
    @FXML private TextField textFile;
    @FXML private VBox paneSaveAndLoad;
    @FXML private ToggleButton load;
    @FXML private ToggleButton save;
    @FXML private ToggleButton point;
    @FXML private  ToggleButton taille;
    @FXML private Label xlab;
    @FXML private Label ylab;
    @FXML private Pane pane;
    @FXML private Label x;
    @FXML private Label y;
    @FXML private ScrollPane scroll;
    @FXML private ToggleButton rectangle;
    @FXML private ToggleButton ellipse;
    @FXML private ToggleButton delete;
    @FXML private ToggleButton move;
    @FXML private ColorPicker color;
    @FXML private Spinner<Double> width;
    @FXML private Spinner<Double> height;

    private List<ToggleButton> listePossibleElements;
    private Point lastPoint;
    class DnDToMoveShape
    {
        private double pressPositionX;
        private double pressPositionY;

        DnDToMoveShape(Shape view)
        {
            view.setOnMousePressed(event -> {
                if(move.isSelected())
                {
                    Forme f = (Forme) view.getUserData();
                    pressPositionX=f.getPositionX()-event.getSceneX();//difference between the real position and the sceneX position
                    pressPositionY=f.getPositionY()-event.getSceneY();
                    xlab.setVisible(true);
                    ylab.setVisible(true);

                    x.setVisible(true);
                    y.setVisible(true);
                    x.textProperty().bind(f.positionXProperty().asString());
                    y.textProperty().bind(f.positionYProperty().asString());

                }
            });
            view.setOnMouseDragged((MouseEvent event) -> {
                if(move.isSelected()) {
                    Forme f = (Forme) view.getUserData();
                    //si curseur < 0 (sort de la fenetre en haut ou à droite) => 0 (juste la largeur/hauteur de la forme) sinon position normale
                  //  double posY=pressPositionY+event.getSceneY()-f.getHeight()/2<0?f.getHeight()/2:pressPositionY+event.getSceneY();
                //    double posX=pressPositionX+event.getSceneX()-f.getWidth()/2<0?f.getWidth()/2:pressPositionX+event.getSceneX();
                    f.setPosition(pressPositionX+event.getSceneX(), pressPositionY+event.getSceneY());
                    updateSizePane();
                }
            });
            view.setOnMouseReleased(event -> {
                xlab.setVisible(false);
                ylab.setVisible(false);
                x.setVisible(false);
                y.setVisible(false);
            });
        }
    }

    /**
     * Main method which initialize the app
     * @param location
     * @param resources
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        listePossibleElements=new ArrayList<>();//the list of possibles drawing actions
        listePossibleElements.add(rectangle);
        listePossibleElements.add(ellipse);
        listePossibleElements.add(point);

        dessin=new DessinImpl();

        rectangle.setSelected(true);
        color.setValue(Color.RED);
        xlab.setVisible(false);
        ylab.setVisible(false);
        x.setVisible(false);
        y.setVisible(false);

        pane.setBackground(new Background(new BackgroundFill(Color.WHITE, null, null)));

        pane.setOnMouseClicked(this::onPaneClick);

        dessin.getFormes().addListener(this::onListDessinChanged);
        pane.setPrefWidth(scroll.getPrefWidth());
        pane.setPrefHeight(scroll.getPrefHeight());

        hidePaneSave(); //hide the IO pane
        save.setOnMouseClicked(event -> openPaneIO());
        load.setOnMouseClicked(event -> openPaneIO());
        valid.setOnMouseClicked(event -> {
            if(save.isSelected())
                saveCurrentDraw();
            else
                loadDraw();
        });

        clear.setOnMouseClicked(event -> dessin.vider());
        updateListeDessinSaved();

        listeFiles.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> textFile.setText(newValue));
    }

    /**
     * Open the pane that allow to save and load a draw
     */
    private void openPaneIO() {
        if(save.isSelected())
        {
            labelIO.setText("Save file :");
            textFile.setEditable(true);
        }
        else if (load.isSelected())
        {
            labelIO.setText("Load file :");
            textFile.setEditable(false);
            updateListeDessinSaved();//refresh the list
        }
        else//when unselect
        {
            hidePaneSave();
            return;
        }
        labelInfo.setVisible(true);
        labelInfo.setText("");
        listeFiles.setVisible(true);
        labelIO.setVisible(true);
        valid.setVisible(true);
        textFile.setVisible(true);
        anchorPaneIO.setVisible(true);

        paneSaveAndLoad.setPrefWidth(100.0);
        anchorPaneIO.setPrefWidth(100);


    }

    /**
     * Hide the pane that allow to save an load a draw
     */
    private void hidePaneSave() {
        labelInfo.setVisible(false);
        anchorPaneIO.setVisible(false);
        anchorPaneIO.setPrefWidth(0);
        paneSaveAndLoad.setPrefWidth(0);
        valid.setVisible(false);
        labelIO.setVisible(false);
        textFile.setVisible(false);
        listeFiles.setVisible(false);

    }

    /**
     * Load the draw in the file specified in the field 'TextFile'
     */
    private void loadDraw()
    {
        try {
            dessin.loadFromSerialized(LoaderSaverIO.loadDraw(textFile.getText()));
            labelInfo.setText("");
        } catch (LoaderSaverIOException e ) {
            labelInfo.setText("Le fichier \n n'existe pas");
        }
    }

    /**
     * Save the current draw in the file specified in the field 'TextFile'
     */
    private void saveCurrentDraw()
    {
        try {
           LoaderSaverIO.saveCurrentDraw(textFile.getText(),dessin);
            labelInfo.setText("");
            updateListeDessinSaved();
        } catch (LoaderSaverIOException e) {
            labelInfo.setText("Erreur de sauvegarde");
        }
    }


    /**
     * Create the view from the model
     * @param forme - the form to create the view from
     * @return the shape associated to the model
     * */
    private Shape createViewShapeFromShape(final Forme forme)
    {
        Shape s=  FormeFactory.getShapeInstance(forme);

        s.setUserData(forme);
        s.setOnMouseClicked((MouseEvent event) ->
        {
            if(delete.isSelected())
            {
                dessin.supprimerForme(forme);
            }else if(taille.isSelected()  )
            {
                //on limite pour la diminution
                //click gauche -> agrandit,click droit -> réduit (à condition qu'on puisse le réduire)
                int reduitOuAgrandit = (event.getButton()== MouseButton.PRIMARY?1:(forme.getWidth()>5 && forme.getHeight()>11?-1:0));
                //reduitOuAgrandit vaut 1 (agrandit), 0(bouge pas, taille mini atteinte) ou -1(réduit)

                dessin.supprimerForme(forme);//besoin de supprimer et remettre pour remettre à jour le X et Y qui correspond au centre (sinon probleme sur rectangle)
                forme.setWidth(forme.getWidth()+10*reduitOuAgrandit);
                forme.setHeight(forme.getHeight()+10*reduitOuAgrandit);
                dessin.ajouterForme(forme);
                updateSizePane();
            }
        });
        new DnDToMoveShape(s);//pour les listener des mouvements
        return s;
    }

    /**
     * Listener of pane.setOnMouseClicked
     * Executed when the pane in clicked
     * @param event
     */
    private void onPaneClick(MouseEvent event) {
        double posX = event.getX();
        double posY = event.getY();
        String fxid=null;
        for(ToggleButton t: listePossibleElements)
        {
            if(t.isSelected())
            {
                fxid=t.getId();
                break;
            }
        }

        if(fxid==null)//si aucun bouton enclenché
            return;
        Forme form;
        try {
            form = FormeFactory.getModelInstance(fxid,posX,posY,height.getValue(),width.getValue(),color.getValue());
        } catch (UnknownFormException e) {
            System.err.println("Forme inconnue");
            form=new Rect(posX,posY,height.getValue(),width.getValue(),color.getValue());//en cas de probleme, on dessine un rectangle
        }
        dessin.ajouterForme(form);

        if(form instanceof Point)//si c'est un point, on dessine un trait entre lui et le point précédent
        {
            if(event.getButton()==MouseButton.SECONDARY)//clique droit : on ne fait pas de trait entre le précédent et celui ci.
                lastPoint=null;

            if(lastPoint==null)
                lastPoint=(Point)form;
            else
            {
                join2points(lastPoint,(Point)form);
                lastPoint=(Point)form;
            }
        }
    }

    /**
     * draw a line between 2 points
     * @param lastPoint the 1st point
     * @param target the second point
     */
    private void join2points(Point lastPoint, Point target)
    {
        Ligne line = new Ligne(lastPoint.getPositionX(),lastPoint.getPositionY(),target.getPositionX(),target.getPositionY(),0,0,target.getCouleur());
        dessin.ajouterForme(line);
    }

    /**
     * Listener of dessin.getFormes()
     * Executed when a change happened in this list
     * @param event
     */
    private void onListDessinChanged(javafx.collections.ListChangeListener.Change<? extends Forme> event) {
        if(event.next())
            if(event.wasAdded()) {
                for(Forme f : event.getAddedSubList()) {//en cas de AddAll
                    Shape viewShapeFromShape = createViewShapeFromShape(f);
                    pane.getChildren().add(viewShapeFromShape);
                }
            } else if(event.wasRemoved()) {
                Node n =null;
                for(Forme remv : event.getRemoved())
                {
                    for (Node el : pane.getChildren())//on regarde tout elements, pas très opti
                        if (el.getUserData().equals(remv))
                        {
                            n=el;
                            break;
                        }
                    if (n!=null)
                        pane.getChildren().remove(n);//safe delete, on aurait pu le faire avec un itérateur (.remove)
                }
            }
        updateSizePane();
    }


    private void updateSizePane()
    {
        double minX=0;
        double minY=0;
        double maxX=0;
        double maxY=0;
        for(Forme f : dessin.getFormes())
        {
            if(f.getPositionX()+f.getWidth()/2>maxX)
                maxX=f.getPositionX()+f.getWidth()/2;
            if(f.getPositionY()+f.getHeight()/2>maxY)
                maxY=f.getPositionY()+f.getHeight()/2;
            if(f.getPositionX()-f.getWidth()/2<minX)
                minX=f.getPositionX()-f.getWidth()/2;
            if(f.getPositionY()-f.getHeight()/2<minY)
                minY=f.getPositionY()-f.getHeight()/2;
        }
        maxX=Math.max(scroll.getPrefWidth()-minX, maxX-minX);
        pane.setPrefWidth(maxX);
        maxY=Math.max(scroll.getPrefHeight()-minY, maxY-minY);
        pane.setPrefHeight(maxY);

        pane.setTranslateX(-minX);
        pane.setTranslateY(-minY);
        pane.setBackground(new Background(new BackgroundFill(Color.WHITE, null, new Insets(minY,-minX,-minY,minX))));

    }

    /**
     * Update the list of saved draws
     */
    private void updateListeDessinSaved()
    {
        final ObservableList<String> files = FXCollections.observableArrayList();
        File[] fList = LoaderSaverIO.getListeDessinSaved();//can't return null
        for(File f : fList)
        {
            files.add(f.getName().replace(".save",""));
        }
        listeFiles.setItems(files);
    }
}
