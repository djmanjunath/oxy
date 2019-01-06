package oxy.core.rewriter;
 
import org.apache.sling.rewriter.Transformer;
import org.apache.sling.rewriter.TransformerFactory;
import org.osgi.service.component.annotations.Component;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
 
/**
 * A TransformerFactory service instance for creating a Transformer to add link
 * type icons into links to documents.
 * 
 * @author dklco@apache.org
 * @see com.perficient.adobe.rewriter.LinkTypeTransformer
 */
@Component(property = { "pipeline.type=linktype" }, service = { TransformerFactory.class })
public class LinkTypeTransformerFactory implements TransformerFactory {
 
    private static final Logger log = LoggerFactory.getLogger(LinkTypeTransformerFactory.class);
 
    /*
     * (non-Javadoc)
     * 
     * @see org.apache.sling.rewriter.TransformerFactory#createTransformer()
     */
    @Override
    public Transformer createTransformer() {
        log.trace("createTransformer");
        return new LinkTypeTransformer();
    }
 
}