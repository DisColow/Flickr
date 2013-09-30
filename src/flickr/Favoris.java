/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package flickr;

import java.util.ArrayList;

/**
 *
 * @author peixoton
 */
public class Favoris {

    private ArrayList<String> listeFavoris;
    private int nbFavoris;
    private String currentFavoris;
    
    public Favoris(){
        this.nbFavoris = 0;
        this.listeFavoris = new ArrayList<String>();
    }
    
    public void loadFavoris(){
        /* Chargement des favoris depuis je ne sais quoi; s$urement un fichier texte. */
    }

    public ArrayList<String> getListeFavoris() {
        return listeFavoris;
    }

    public void setListeFavoris(ArrayList<String> listeFavoris) {
        this.listeFavoris = listeFavoris;
    }

    public int getNbFavoris() {
        return nbFavoris;
    }

    public void setNbFavoris(int nbFavoris) {
        this.nbFavoris = nbFavoris;
    }

    public String getCurrentFavoris() {
        return currentFavoris;
    }

    public void setCurrentFavoris(String currentFavoris) {
        this.currentFavoris = currentFavoris;
    }
    
}
