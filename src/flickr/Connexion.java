/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package flickr;

/**
 *
 * @author peixoton
 */
public class Connexion {
    
    private String login;
    private String password;
    
    public Connexion(String login, String password){
        this.login = login;
        this.password = password;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        if(this.login.length() > 0)
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        if(this.password.length() > 0)
            this.password = password;
    }
    
}
