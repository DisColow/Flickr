/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package flickr;

/**
 *
 * @author peixoton
 */
public class Photo {
    
    public String id;
    public String owner;
    public String secret;
    public String server;
    public String farm;
    public String title;
    public String photo_url;
    public String photo_url_big;
    public String photo_url_thumb;
    public String page_url;
    
    public Photo(){
        this.id = "";
        this.owner = "";
        this.secret = "";
        this.server = "";
        this.farm = "";
        this.title = "";
        this.photo_url = "";
    }
    
    public Photo(String photo_url_big, String photo_url_thumb){
        this.photo_url_big = photo_url_big;
        this.photo_url_thumb = photo_url_thumb;
        this.photo_url = photo_url_big;
    }
    
    public Photo(String id, String owner, String secret, String server, String farm, String title, String photo_url){
        this.id = id;
        this.owner = owner;
        this.secret = secret;
        this.server = server;
        this.farm = farm;
        this.title = title;
        this.photo_url = "http://farm" + farm + ".staticflickr.com/" + server + "/" + id + "_" + secret + ".jpg";
    }
    
    public void createURL(){
        this.photo_url_thumb = "http://farm" + this.farm + ".staticflickr.com/" + this.server + "/" + this.id + "_" + this.secret + "_s.jpg";
        this.photo_url = "http://farm" + this.farm + ".staticflickr.com/" + this.server + "/" + this.id + "_" + this.secret + ".jpg";
        this.photo_url_big = "http://farm" + this.farm + ".staticflickr.com/" + this.server + "/" + this.id + "_" + this.secret + "_b.jpg";
        this.page_url = "http://flickr.com/photos/" + this.owner + "/" + this.id + "/";
    }
    
    @Override
    public String toString(){
        return "ID: " + this.id +
                " OWNER: " + this.owner +
                " SECRET: " + this.secret +
                " SERVER: " + this.server +
                " FARM: " + this.farm +
                " TITLE: " + this.title +
                " PHOTO_URL: " + this.photo_url;
    }
    
}
