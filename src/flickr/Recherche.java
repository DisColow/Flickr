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
public class Recherche {
    
    public String motClef;
    public ArrayList<String> Historique;
    public String resultat;
    
    public Recherche(String motClef){
        this.motClef = motClef;
        this.Historique = new ArrayList<String>();
        this.Historique.add(motClef);
        this.search();
    }
    
    public String search(){
        /* Code de la recherche */
        return this.resultat;
    }
    
}
