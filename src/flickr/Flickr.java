/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package flickr;

import java.awt.Container;
import java.awt.Desktop;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.border.Border;
import javax.xml.stream.XMLStreamException;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.XMLReaderFactory;

/**
 *
 * @author peixoton
 */
public class Flickr extends JFrame implements ActionListener {

    public Container contenuFenetre;
    public JPanel panelConnexion;
    public JPanel panelSearch;
    public JPanel imagePanel;
    public static final int LARGEUR_FENETRE = 600;
    public static final int HAUTEUR_FENETRE = 400;
    public static final String api_key = "5ba9bb9bbac0804efaccd0f9d5b4b756";
    public Recherche recherche;
    public ParserXML parser;
    public ArrayList<Photo> lesPhotos;

    /* Ecran de connexion */
    JLabel hint_identifiant;
    JLabel hint_password;
    JTextField identifiant;
    JPasswordField password;
    JButton submit_connexion;
    JButton submit_forgot;
    /* Model connexion */
    Connexion connexion_token;
    
    /* Ecran de recherche */
    JLabel hint_recherche;
    JTextField field_recheche;
    JButton bouton_recherche;
    
    /* Ecran des photos */
    
    int nbCol = 4;
    int nbLig = 4;
    int widthImage = 100;
    int heightImage = 100;
    JButton images[];

    public Flickr() {

        /*this.ecranConnexion();*/
        this.ecranRecherche();
        this.contenuFenetre = this.getContentPane();
        this.contenuFenetre.setLayout(null);
        /*this.contenuFenetre.add(this.panelConnexion);*/
        this.contenuFenetre.add(this.panelSearch);

        setVisible(true);
        setSize(Flickr.LARGEUR_FENETRE, Flickr.HAUTEUR_FENETRE);
        setTitle("Flickr Project Application");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);
    }

    public void ecranConnexion() {

        /*int width = 400;
        int height = 75;
        int x = this.LARGEUR_FENETRE / 2 - width / 2;
        int y = this.HAUTEUR_FENETRE / 2 - height / 2;

        this.panelConnexion = new JPanel();
        this.panelConnexion.setLayout(new GridLayout(3, 2));
        this.panelConnexion.setBounds(x, y, width, height);

        this.hint_identifiant = new JLabel("Entrez votre identifiant...");
        this.hint_password = new JLabel("Entrez votre mot de passe...");

        this.identifiant = new JTextField();
        this.identifiant.setText("Entrez votre identifiant");
        this.password = new JPasswordField();

        this.submit_connexion = new JButton("Connexion");
        this.submit_forgot = new JButton("Mot de passe oublié?");

        this.panelConnexion.add(hint_identifiant);
        this.panelConnexion.add(identifiant);
        this.panelConnexion.add(hint_password);
        this.panelConnexion.add(password);
        this.panelConnexion.add(submit_connexion);
        this.panelConnexion.add(submit_forgot);

        this.submit_connexion.addActionListener(this);
        this.submit_forgot.addActionListener(this);*/

    }
    
    public void ecranRecherche(){
        int width = 400;
        int height = 200;
        int x = this.LARGEUR_FENETRE / 2 - width / 2;
        int y = this.HAUTEUR_FENETRE / 2 - height / 2;
        
        this.panelSearch = new JPanel();
        this.panelSearch.setLayout(new GridLayout(3,1));
        this.panelSearch.setBounds(x, y, width, height);
        
        this.hint_recherche = new JLabel("Entrez un mot-clef pour la recherche...");
        this.field_recheche = new JTextField();
        this.bouton_recherche = new JButton("Rechercher");
        
        this.panelSearch.add(this.hint_recherche);
        this.panelSearch.add(this.field_recheche);
        this.panelSearch.add(this.bouton_recherche);
        
        this.bouton_recherche.addActionListener(this);
    }
    
    public void ecranImages(){
        try {
            int width = this.nbCol * this.widthImage;
            int height = this.nbLig * this.heightImage;
            
            this.imagePanel = new JPanel();
            this.imagePanel.setLayout(new GridLayout(this.nbLig, this.nbLig));
            this.imagePanel.setBounds(0, 0, width, height);
            
            this.images = new JButton[this.nbCol * this.nbLig];
            
            Border emptyBorder = BorderFactory.createEmptyBorder();
            
            for(int i = 0; i < this.nbCol * this.nbLig; i++){
            
                String path = this.lesPhotos.get(i).photo_url;
                System.out.println("Get Image from " + path);
                URL url = new URL(path);
                BufferedImage image = ImageIO.read(url);
                image.getWidth();
                image.getHeight();
                System.out.println("Load image into frame...");
                JButton button = new JButton(new ImageIcon(image));
                this.images[i] = button;
                this.images[i].setBorder(emptyBorder);
                this.imagePanel.add(this.images[i]);
                this.images[i].addActionListener(this);
                
            }
            
            setSize(width, height + 20);
            
        } catch (Exception exp) {
            exp.printStackTrace();
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == submit_connexion) {
            
        } else if (e.getSource() == submit_forgot) {
            try {
                URI uri = new URI("https://edit.europe.yahoo.com/forgotroot?done=https%3A%2F%2Flogin.yahoo.com%2Fconfig%2Fvalidate%3F.src%3Dflickrsignin%26.pc%3D8190%26.scrumb%3D0%26.pd%3Dc%253DJvVF95K62e6PzdPu7MBv2V8-%26.intl%3Dfr%26.done%3Dhttp%253A%252F%252Fwww.flickr.com%252Fsignin%252Fyahoo%252F%253Fredir%253Dhttp%25253A%25252F%25252Fwww.flickr.com%25252F&src=flickrsignin&partner=&intl=fr&lang=fr-FR");
                Desktop desktop = Desktop.isDesktopSupported() ? Desktop.getDesktop() : null;
                if (desktop != null && desktop.isSupported(Desktop.Action.BROWSE)) {
                    try {
                        desktop.browse(uri);
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
                }
            } catch (URISyntaxException ex) {
                Logger.getLogger(Flickr.class.getName()).log(Level.SEVERE, null, ex);
            }
        }else if(e.getSource() == this.bouton_recherche){
            try {
                
                recherche = new Recherche(this.field_recheche.getText());
                this.lesPhotos = Recherche.lesPhotos;
                if(this.lesPhotos.size() > 0){
                    this.panelSearch.setVisible(false);
                    this.ecranImages();
                    this.contenuFenetre.add(this.imagePanel);
                }else{
                    
                }
                

            } catch (URISyntaxException ex) {
                Logger.getLogger(Flickr.class.getName()).log(Level.SEVERE, null, ex);
            } catch (IOException ex) {
                Logger.getLogger(Flickr.class.getName()).log(Level.SEVERE, null, ex);
            } catch (SAXException ex) {
                Logger.getLogger(Flickr.class.getName()).log(Level.SEVERE, null, ex);
            } catch (XMLStreamException ex) {
                Logger.getLogger(Flickr.class.getName()).log(Level.SEVERE, null, ex);
            }
        }else if(e.getSource() instanceof JButton ){
            System.out.println(e.getSource());
            JButton image = new JButton();
            Photo photo = new Photo();
            for(int i = 0; i < this.nbCol * this.nbLig; i++){
                if(e.getSource() == this.images[i]){
                    image = this.images[i];
                    photo = this.lesPhotos.get(i);
                }
            }
            
            this.imagePanel.setVisible(false);
            JPanel panelTemp = new JPanel();
            panelTemp.setLayout(new GridLayout(1, 1));
            panelTemp.add(image);
            panelTemp.setVisible(true);
            panelTemp.setBounds(0, 0, image.getWidth(), image.getHeight());
            this.contenuFenetre.add(panelTemp);
            
        }
    }

    public static void main(String[] args) {
        Flickr application = new Flickr();
    }
}
