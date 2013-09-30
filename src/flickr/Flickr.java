/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package flickr;

import java.awt.Container;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

/**
 *
 * @author peixoton
 */
public class Flickr extends JFrame implements ActionListener{

    public Container contenuFenetre;
    public JPanel panelConnexion;
    public static final int LARGEUR_FENETRE = 600;
    public static final int HAUTEUR_FENETRE = 400;

    /* Ecran de connexion */
    JLabel hint_identifiant;
    JLabel hint_password;
    JTextField identifiant;
    JTextField password;
    JButton submit_connexion;
    JButton submit_forgot;

    public Flickr() {

        this.ecranConnexion();
        this.contenuFenetre = this.getContentPane();
        this.contenuFenetre.setLayout(null);
        this.contenuFenetre.add(this.panelConnexion);

        setVisible(true);
        setSize(Flickr.LARGEUR_FENETRE, Flickr.HAUTEUR_FENETRE);
        setTitle("Flickr Project Application");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);
    }

    public void ecranConnexion() {

        int width = 400;
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
        this.password = new JTextField();
        this.password.setText("Entrez votre password");

        this.submit_connexion = new JButton("Connexion");
        this.submit_forgot = new JButton("Mot de passe oublié?");

        this.panelConnexion.add(hint_identifiant);
        this.panelConnexion.add(identifiant);
        this.panelConnexion.add(hint_password);
        this.panelConnexion.add(password);
        this.panelConnexion.add(submit_connexion);
        this.panelConnexion.add(submit_forgot);
        
        this.submit_connexion.addActionListener(this);

    }
    
    public void actionPerformed(ActionEvent e){
        if(e.getSource() == submit_connexion){
            
        }
    }

    public static void main(String[] args) {
        Flickr application = new Flickr();
    }
}
