package utils;

import java.io.File;
import java.io.StringReader;
import java.io.StringWriter;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

import org.apache.log4j.Logger;

import utils.JaxbKit;

/**
 * 
 * Xml object conversion
 * 
 */
public class JaxbKit {

    protected final static Logger LOG = Logger.getLogger(JaxbKit.class);

    /** 
     * string -> object
     */
    @SuppressWarnings("unchecked")
    public static <T> T unmarshal(String src, Class<T> clazz) {
        T result = null;
        try {
            Unmarshaller avm = JAXBContext.newInstance(clazz).createUnmarshaller();
            result = (T) avm.unmarshal(new StringReader(src));
        } catch (JAXBException e) {
            throw new RuntimeException(e);
        }
        return result;
    }

    @SuppressWarnings("unchecked")
    public static <T> T unmarshal(File xmlFile, Class<T> clazz) {
        T result = null;
        try {
            Unmarshaller avm = JAXBContext.newInstance(clazz).createUnmarshaller();
            result = (T) avm.unmarshal(xmlFile);
        } catch (JAXBException e) {
        	throw new RuntimeException(e);
        }
        return result;
    }

    /**
     * 
     * object -> string
     * @param src
     * @param clazz
     * @return
     */
    public static String marshal(Object jaxbElement) {
        StringWriter sw = null;
        try {
            Marshaller fm = JAXBContext.newInstance(jaxbElement.getClass()).createMarshaller();
            fm.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
            sw = new StringWriter();
            fm.marshal(jaxbElement, sw);
        } catch (JAXBException e) {
            throw new RuntimeException(e);
        }
        return sw.toString();
    }
    /**
     * 
     * object -> file
     * @param jaxbElement
     * @param output
     * @return
     */
    
    public static void marshal( Object jaxbElement, File output ) {
    	try {
    		Marshaller fm = JAXBContext.newInstance(jaxbElement.getClass()).createMarshaller();
    		fm.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
    		fm.marshal(jaxbElement, output);
    	} catch (JAXBException e) {
    		throw new RuntimeException(e);
    	}
    }
    /**
     * 
     * object -> file
     * @param jaxbElement
     * @param output
     * @return
     */
    
    public static void marshal( Object jaxbElement, java.io.Writer writer ) {
    	try {
    		Marshaller fm = JAXBContext.newInstance(jaxbElement.getClass()).createMarshaller();
    		fm.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
    		fm.marshal(jaxbElement, writer);
    	} catch (JAXBException e) {
    		throw new RuntimeException(e);
    	}
    }
}
