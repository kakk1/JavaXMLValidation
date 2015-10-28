/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package javaxmlvalidation;

import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.lang.Exception;
 
import javax.xml.XMLConstants;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import javax.xml.validation.Validator;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.ParserConfigurationException;
import org.w3c.dom.Document;
import javax.xml.transform.dom.DOMSource;
 
import org.xml.sax.SAXException;
/**
 *
 * @author andres
 */
public class JavaXMLValidation {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args)
    {
       try
       {
       validate_count_elements_in_XMLFiles(args[0]);
       
//      System.out.println("EmployeeRequest.xml validates against Employee.xsd? "+validateXMLSchema("Employee.xsd", "EmployeeRequest.xml"));
//      System.out.println("EmployeeResponse.xml validates against Employee.xsd? "+validateXMLSchema("Employee.xsd", "EmployeeResponse.xml"));
//      System.out.println("employee.xml validates against Employee.xsd? "+validateXMLSchema("Employee.xsd", "employee.xml"));
       }
       catch ( ParserConfigurationException |SAXException | IOException e) {
       }
    }
    
    public static boolean validate_count_elements_in_XMLFiles(String xmlPath) throws ParserConfigurationException, SAXException, IOException
    {
        DocumentBuilderFactory DB_F = DocumentBuilderFactory.newInstance();
        DB_F.setValidating(true);
        Document document;
        String RootNodeName;
        
        String xsdPath = "";

        DocumentBuilder TaskDocument;
        
        try
        {
            TaskDocument = DB_F.newDocumentBuilder();
        }
        catch (ParserConfigurationException ex)
        {
            throw ex;
        }

        try
        {
            document = TaskDocument.parse(new File(xmlPath));//"instance.xml"));
            RootNodeName = document.getDocumentElement().getNodeName();
        }
        catch ( SAXException se )
        {
            throw se;
        }
        catch ( IOException ioe )
        {
            throw ioe;
        }
        
        try {
            // create a SchemaFactory capable of understanding WXS schemas
            //SchemaFactory factory = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);

            SchemaFactory factory = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
            
            Schema schema = factory.newSchema(new File(xsdPath));
            
            Validator validator = schema.newValidator();
            validator.validate(new StreamSource(new File(xmlPath)));
            
            // parse an XML document into a DOM tree
            
            
            try {
                // load a WXS schema, represented by a Schema instance
                StreamSource schemaFile = new StreamSource(new File(xsdPath));//"mySchema.xsd"));
                Schema schema1 = factory.newSchema(schemaFile);

                // create a Validator instance, which can be used to validate an instance document
                Validator validator1 = schema1.newValidator();

                // validate the DOM tree
                validator1.validate(new DOMSource(document));
                
//            } catch (ParserConfigurationException ex) {
//                Logger.getLogger(JavaXMLValidation.class.getName()).log(Level.SEVERE, null, ex);
//                return false;
//            }
            }
            catch (SAXException e) {
                // instance document is invalid!
                return false;
            }
            
        } catch (IOException | SAXException e) {
            System.out.println("Exception: "+e.getMessage());
            return false;
        }
        return true;
    }    
}
