/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package flickr;

import java.util.ArrayList;
import org.xml.sax.Attributes;
import org.xml.sax.ContentHandler;
import org.xml.sax.Locator;
import org.xml.sax.SAXException;

/**
 *
 * @author peixoton
 */
public class ParserXML implements ContentHandler{
    
    public ArrayList<Photo> lesPhotos = new ArrayList<Photo>();
    
    public ParserXML(){
        super();
    }

    @Override
    public void setDocumentLocator(Locator locator) {
        System.out.println("Not used");
    }

    @Override
    public void startDocument() throws SAXException {
        System.out.println("Start of the document.");
    }

    @Override
    public void endDocument() throws SAXException {
        System.out.println("End of the document.");
    }

    @Override
    public void startPrefixMapping(String prefix, String uri) throws SAXException {
        System.out.println("Not used");
    }

    @Override
    public void endPrefixMapping(String prefix) throws SAXException {
        System.out.println("Not used");
    }

    @Override
    public void startElement(String uri, String localName, String qName, Attributes atts) throws SAXException {
        
        Photo onePhoto = new Photo();
        String name;
        String value;
        
        for (int index = 0; index < atts.getLength(); index++) {
            
            name = atts.getLocalName(index);
            value = atts.getValue(index);
            if(name == "id"){
                onePhoto.id = value;
            }else if(name == "secret"){
                onePhoto.secret = value;
            }else if(name == "farm"){
                onePhoto.farm = value;
            }else if(name == "owner"){
                onePhoto.owner = value;
            }else if(name == "server"){
                onePhoto.server = value;
            }else if(name == "title"){
                onePhoto.title = value;
            }
            
        }
        
        onePhoto.createURL();
        System.out.println(onePhoto);
        this.lesPhotos.add(onePhoto);
        
    }

    @Override
    public void endElement(String uri, String localName, String qName) throws SAXException {
        System.out.println("End element.");
    }

    @Override
    public void characters(char[] ch, int start, int length) throws SAXException {
        System.out.println("Not used but could be.");
    }

    @Override
    public void ignorableWhitespace(char[] ch, int start, int length) throws SAXException {
        System.out.println("Not used");
    }

    @Override
    public void processingInstruction(String target, String data) throws SAXException {
        System.out.println("Not used");
    }

    @Override
    public void skippedEntity(String name) throws SAXException {
        System.out.println("Not used");
    }
}
