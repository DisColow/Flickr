/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package flickr;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.io.StringWriter;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map.Entry;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.net.ssl.HttpsURLConnection;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamConstants;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;
import oauth.signpost.OAuthConsumer;
import oauth.signpost.commonshttp.CommonsHttpOAuthConsumer;
import oauth.signpost.exception.OAuthCommunicationException;
import oauth.signpost.exception.OAuthExpectationFailedException;
import oauth.signpost.exception.OAuthMessageSignerException;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.xml.sax.ContentHandler;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.XMLReaderFactory;
import sun.misc.IOUtils;

/**
 *
 * @author peixoton
 */
public class Recherche {
    
    public String motClef;
    public ArrayList<String> Historique;
    public String resultat;
    public HashMap<String, String> searchParams = new HashMap<String, String>();
    public static final String search_url = "http://api.flickr.com/services/rest/?method=flickr.photos.search";
    public static ArrayList<Photo> lesPhotos = new ArrayList<Photo>();
    
    public Recherche(String motClef, int page, int nbParPage) throws URISyntaxException, IOException, SAXException, XMLStreamException{
        this.searchParams.put("api_key", Connexion.KEY);
        this.searchParams.put("text", motClef);
        this.searchParams.put("per_page", "" + nbParPage);
        this.searchParams.put("page", "" + page);
        this.motClef = motClef;
        this.Historique = new ArrayList<String>();
        this.Historique.add(motClef);
        this.search();
    }
    
    public void search() throws URISyntaxException, IOException, SAXException, XMLStreamException{
        OAuthConsumer consumer = new CommonsHttpOAuthConsumer(Connexion.KEY, Connexion.SECRET_KEY);
        /*URI uri = new URIBuilder()*/
        URIBuilder uri = new URIBuilder()
                .setScheme("https")
                .setHost("api.flickr.com")
                .setPath("/services/rest/")
                .setParameter("method", "flickr.photos.search");
        for(Entry<String, String> entry: this.searchParams.entrySet()){
            uri.setParameter(entry.getKey(), entry.getValue());
        }
        URI url = uri.build();
        /*consumer.setTokenWithSecret(accessToekn, accessSecret);*/
        HttpGet request = new HttpGet(url);
        System.out.println(request.getURI());
        try {
            consumer.sign(request);
            CloseableHttpClient httpclient = HttpClients.createDefault();
            CloseableHttpResponse response = httpclient.execute(request);
            try {
                HttpEntity entity = response.getEntity();
                if (entity != null) {
                    
                    InputStream instream = entity.getContent();
                    
                    this.resultat = convertStreamToString(instream);
                    System.out.println(resultat);
                    
                    /*InputStream is = new ByteArrayInputStream(this.resultat.getBytes());*/
                    InputSource s = new InputSource(new StringReader(this.resultat)); 

                    XMLReader saxReader = XMLReaderFactory.createXMLReader();
                    saxReader.setContentHandler(new ParserXML());
                    saxReader.parse(s);
                    
                    
                }
            }finally {
                response.close();
            }
        } catch (OAuthMessageSignerException ex) {
            Logger.getLogger(Recherche.class.getName()).log(Level.SEVERE, null, ex);
        } catch (OAuthExpectationFailedException ex) {
            Logger.getLogger(Recherche.class.getName()).log(Level.SEVERE, null, ex);
        } catch (OAuthCommunicationException ex) {
            Logger.getLogger(Recherche.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    static String convertStreamToString(java.io.InputStream is) {
        java.util.Scanner s = new java.util.Scanner(is).useDelimiter("\\A");
        return s.hasNext() ? s.next() : "";
    }
    
}
