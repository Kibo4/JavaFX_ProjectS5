package drawingEditor.model.tools;

import drawingEditor.model.Dessin;
import drawingEditor.model.exceptions.LoaderSaverIOException;

import java.io.*;
import java.util.List;

/**
 * Tools to load and save a draw
 */
public class LoaderSaverIO
{

    /**
     * Load a draw from a file
     * @param name the name of the file to load
     * @return The list of object (the forms)
     * @throws LoaderSaverIOException if there is a problem
     */
    public static List<List<Object>> loadDraw(String name) throws LoaderSaverIOException {
        File fichier =  new File("save/"+name+".save") ;

        // ouverture d'un flux sur un fichier
        ObjectInputStream ois = null;
        try {
            ois = new ObjectInputStream(new FileInputStream(fichier));
            List<List<Object>> liste = (List<List<Object>>) ois.readObject() ;
            return liste;
        } catch (IOException | ClassNotFoundException e ) {
            System.err.println("Erreur lors du chargement :"+e.getMessage());
            throw new LoaderSaverIOException("Le fichier \n n'existe pas");
        }

    }

    /**
     * Save a draw into a file
     * @param name the target file to save into
     * @param dessin the draw to save
     * @throws LoaderSaverIOException when there is a problem
     */
    public static void saveCurrentDraw(String name, Dessin dessin) throws LoaderSaverIOException {

        File fichier =  new File("save/"+name+".save") ;
        ObjectOutputStream oos;
        try {
            oos =  new ObjectOutputStream(new FileOutputStream(fichier)) ;
            oos.writeObject(dessin.serializable());
            oos.close();

        } catch (IOException e) {
            System.err.println("Erreur lors de la sauvegarde : "+e.getMessage()) ;
            throw new LoaderSaverIOException("Erreur lors de la sauvegarde");

        }

    }

    /**
     * Get the list of the currents saved draws
     * @return the array of files
     */
    public static File[] getListeDessinSaved()
    {
        File directory = new File("save");
        File[] fList = null;
       try
       {
           fList = directory.listFiles();//get all the files from a directory
       }catch (SecurityException ignored)
       {}

       if(fList==null)
            fList=new File[]{};
       return fList;
    }
}
