/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package flickr;

import java.awt.Container;
import java.awt.Desktop;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

/**
 *
 * @author peixoton
 */
public class Flickr extends JFrame implements ActionListener {

    public Container contenuFenetre;
    public JPanel panelConnexion;
    public static final int LARGEUR_FENETRE = 600;
    public static final int HAUTEUR_FENETRE = 400;

    /* Ecran de connexion */
    JLabel hint_identifiant;
    JLabel hint_password;
    JTextField identifiant;
    JPasswordField password;
    JButton submit_connexion;
    JButton submit_forgot;
    /* Model connexion */
    Connexion connexion_token;

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
        this.submit_forgot.addActionListener(this);

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == submit_connexion) {
            if (this.identifiant.getText().length() > 0 && this.password.getText().length() > 0) {
                this.connexion_token = new Connexion(this.identifiant.getText(), this.password.getText());
                System.out.println(this.connexion_token);
            }
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
        }
    }

    public static void main(String[] args) {
        Flickr application = new Flickr();
    }
}
