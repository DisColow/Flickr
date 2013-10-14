/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package flickr;

import static flickr.Recherche.convertStreamToString;
import java.io.IOException;
import java.io.InputStream;
import java.io.StringReader;
import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URLEncoder;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.crypto.Mac;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import static javax.xml.crypto.dsig.SignatureMethod.HMAC_SHA1;
import oauth.signpost.OAuthConsumer;
import oauth.signpost.commonshttp.CommonsHttpOAuthConsumer;
import oauth.signpost.exception.OAuthCommunicationException;
import oauth.signpost.exception.OAuthExpectationFailedException;
import oauth.signpost.exception.OAuthMessageSignerException;
import oauth.signpost.http.HttpParameters;
import oauth.signpost.http.HttpRequest;
import oauth.signpost.signature.SigningStrategy;
import org.apache.commons.codec.binary.Base64;
import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.client.utils.URLEncodedUtils;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.XMLReaderFactory;

/**
 *
 * @author peixoton
 */
public class Connexion {
    
    private String login;
    private String password;
    public HashMap<String, String> authenticationParameters;
    
    public static final String KEY = "5ba9bb9bbac0804efaccd0f9d5b4b756";
    public static final String SECRET_KEY = "d44f09102f60a452";
    
    public Connexion(String login, String password) throws IOException, SAXException, URISyntaxException, OAuthMessageSignerException, OAuthExpectationFailedException, OAuthCommunicationException{
        this.login = login;
        this.password = password;
        
        BigInteger nonce = new BigInteger(75, new Random());
        Date timestamp = new Date();
        
        List<NameValuePair> qparams = new ArrayList<NameValuePair>();
        
        qparams.add(new BasicNameValuePair("oauth_callback", "oob"));
        qparams.add(new BasicNameValuePair("oauth_nonce", "oob"));
        qparams.add(new BasicNameValuePair("oauth_timestamp", "oob"));
        qparams.add(new BasicNameValuePair("oauth_consumer_key", "oob"));
        qparams.add(new BasicNameValuePair("oauth_signature_method", "oob"));
        
        
        this.authenticationParameters = new HashMap<String, String>();
        this.authenticationParameters.put("oauth_nonce", "" + nonce);
        this.authenticationParameters.put("oauth_timestamp", "" + timestamp.getTime());
        this.authenticationParameters.put("oauth_consumer_key", Connexion.KEY);
        this.authenticationParameters.put("oauth_signature_method", "HMAC-SHA1");
        this.authenticationParameters.put("oauth_callback", "oob");
        this.authenticationParameters.put("oauth_signature", calcShaHash(URLEncoder.encode("GET&http://www.flickr.com/services/oauth/request_token&" +
                URLEncoder.encode(URLEncodedUtils.format(qparams, "UTF-8")), "UTF-8"), Connexion.SECRET_KEY));
        
        OAuthConsumer consumer = new CommonsHttpOAuthConsumer(Connexion.KEY, Connexion.SECRET_KEY);
        URIBuilder uri = new URIBuilder()
                .setScheme("http")
                .setHost("flickr.com")
                .setPath("/services/oauth/request_token");
        for(Entry<String, String> entry: this.authenticationParameters.entrySet()){
            uri.setParameter(entry.getKey(), entry.getValue());
        }
        URI url = uri.build();
        /*consumer.setTokenWithSecret(Connexion.KEY, Connexion.SECRET_KEY);*/
        HttpGet request = new HttpGet(url);
        System.out.println(request.getURI());
        try {
            consumer.sign(request);
            System.out.println(request.getURI());
            CloseableHttpClient httpclient = HttpClients.createDefault();
            CloseableHttpResponse response = httpclient.execute(request);
            System.out.println(request.toString());
            try {
                HttpEntity entity = response.getEntity();
                if (entity != null) {
                    
                    InputStream instream = entity.getContent();
                    
                    System.out.println(convertStreamToString(instream));
                    /*System.out.println(resultat);*/
                    
                    /*InputStream is = new ByteArrayInputStream(this.resultat.getBytes());*/
                    /*InputSource s = new InputSource(new StringReader(this.resultat)); 

                    XMLReader saxReader = XMLReaderFactory.createXMLReader();
                    saxReader.setContentHandler(new ParserXML());
                    saxReader.parse(s);*/
                    
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
    
    @Override
    public String toString(){
        return "Login/Mot de passe : " + this.login + "/" + this.password;
    }
    
    public void login(){
        //OAuthConsumer consumer = new CommonsHttpOAuthConsumer(Connexion.KEY, Connexion.SECRET_KEY);
        //consumer.setTokenWithSecret(accessToekn, accessSecret);
        //HttpGet request = new HttpGet();
        //consumer.sign(request);
    }
    
    public static String calcShaHash (String data, String key) {
        String HMAC_SHA1_ALGORITHM = "HmacSHA1";       
        String result = null;

        try {         
            Key signingKey = new SecretKeySpec(key.getBytes(), HMAC_SHA1_ALGORITHM);
            Mac mac = Mac.getInstance(HMAC_SHA1_ALGORITHM);
            mac.init(signingKey);
            byte[] rawHmac = mac.doFinal(data.getBytes("UTF-8"));
            result = Base64.encodeBase64String(rawHmac);    
        }
        catch (Exception e) {
            e.printStackTrace(); 
        }       

        return result;
    }
    
    private static String getSignature(String url, String params)
        throws UnsupportedEncodingException, NoSuchAlgorithmException,
        InvalidKeyException {
        /**
         * base has three parts, they are connected by "&": 1) protocol 2) URL
         * (need to be URLEncoded) 3) Parameter List (need to be URLEncoded).
         */
        StringBuilder base = new StringBuilder();
        base.append("GET&");
        base.append(url);
        base.append("&");
        base.append(params);
        System.out.println("Stirng for oauth_signature generation:" + base);
        // yea, don't ask me why, it is needed to append a "&" to the end of
        // secret key.
        byte[] keyBytes = (Connexion.SECRET_KEY + "&").getBytes("UTF-8");

        SecretKey key = new SecretKeySpec(keyBytes, HMAC_SHA1);

        Mac mac = Mac.getInstance(HMAC_SHA1);
        mac.init(key);

        // encode it, base64 it, change it to string and return.
        return new String(Base64.encodeBase64String(mac.doFinal(base.toString().getBytes(
                "UTF-8")))).trim();
    }
    
}
