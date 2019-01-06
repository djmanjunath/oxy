package oxy.core.rewriter;
 
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.regex.*;
 
import javax.swing.text.html.HTML;
 
import org.apache.jackrabbit.oak.commons.StringUtils;
import org.apache.sling.rewriter.ProcessingComponentConfiguration;
import org.apache.sling.rewriter.ProcessingContext;
import org.apache.sling.rewriter.Transformer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.xml.sax.Attributes;
import org.xml.sax.ContentHandler;
import org.xml.sax.Locator;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.AttributesImpl;
 
import com.drew.lang.StringUtil;
 
/**
 * A Sling Rewriter Transformer which appends in an icon to the beginning of
 * links to document links.
 * 
 * @author dklco@apache.org
 */
public class LinkTypeTransformer implements Transformer {
 
    private static final Logger log = LoggerFactory.getLogger(LinkTypeTransformer.class);
 
    private ContentHandler contentHandler;
 
    private Map<Pattern, String> iconMap = new HashMap<Pattern, String>();
 
    @Override
    public void setDocumentLocator(Locator locator) {
        contentHandler.setDocumentLocator(locator);
    }
 
    @Override
    public void startDocument() throws SAXException {
        contentHandler.startDocument();
    }
 
    @Override
    public void endDocument() throws SAXException {
        contentHandler.endDocument();
    }
 
    @Override
    public void startPrefixMapping(String prefix, String uri) throws SAXException {
        contentHandler.startPrefixMapping(prefix, uri);
    }
 
    @Override
    public void endPrefixMapping(String prefix) throws SAXException {
        contentHandler.endPrefixMapping(prefix);
    }
 
    @Override
	public void startElement(String uri, String localName, String qName, Attributes atts) throws SAXException {
	    final AttributesImpl attributes = new AttributesImpl(atts);
	
	    final String href = attributes.getValue("href");
	
	    if (href != null) {
	        for (int i = 0; i < attributes.getLength(); i++) {
	            if ("href".equalsIgnoreCase(attributes.getQName(i))) {
	            	
	                  
	                String shortenPath = attributes.getValue(i);
	                log.info("shortenPath-------------------> :{}",shortenPath);
	                String test = "/content/dam/we-retail/en/activities/water-sports/";
	                if(shortenPath.contains("/content/dam/we-retail/en/activities/water-sports/")) {
	                	 log.info("result-------------------> :{}",shortenPath.replaceAll(test,""));
	 	                attributes.setValue(i,shortenPath.replaceAll(test,""));
	                	
	                }
	               
	                break;
	            }
	        }
	    }
	
	    contentHandler.startElement(uri, localName, qName, attributes);
	}
 
 
    @Override
    public void endElement(String uri, String localName, String qName) throws SAXException {
        contentHandler.endElement(uri, localName, qName);
    }
 
    @Override
    public void characters(char[] ch, int start, int length) throws SAXException {
        contentHandler.characters(ch, start, length);
    }
 
    @Override
    public void ignorableWhitespace(char[] ch, int start, int length) throws SAXException {
        contentHandler.ignorableWhitespace(ch, start, length);
    }
 
    @Override
    public void processingInstruction(String target, String data) throws SAXException {
        contentHandler.processingInstruction(target, data);
    }
 
    @Override
    public void skippedEntity(String name) throws SAXException {
        contentHandler.skippedEntity(name);
    }
 
    @Override
    public void dispose() {
        // TODO Auto-generated method stub
 
    }
 
    @Override
    public void init(ProcessingContext context, ProcessingComponentConfiguration config) throws IOException {
        log.trace("init");
        for (String type : new String[] { "pdf", "doc", "xls" }) {
            iconMap.put(Pattern.compile(".+\\." + type + "($|#.*|\\?.*)", Pattern.CASE_INSENSITIVE),
                    "/etc/designs/sling-rewriting-pipeline-demo/img/" + type + ".png");
        }
    }
 
    @Override
    public void setContentHandler(ContentHandler handler) {
        this.contentHandler = handler;
    }
 
}