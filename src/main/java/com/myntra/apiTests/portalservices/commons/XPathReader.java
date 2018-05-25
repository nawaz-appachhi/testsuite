package com.myntra.apiTests.portalservices.commons;

import java.io.File;
import java.io.IOException;
import java.io.StringReader;

import javax.xml.XMLConstants;
import javax.xml.namespace.QName;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import javax.xml.validation.Validator;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

/**
 * @author shankara.c
 *
 */
public class XPathReader 
{
	private String xmlFile;
	private Document xmlDocument;
	private XPath xPath;
	
	public XPathReader(String xmlFile) 
	{
	   this.xmlFile = xmlFile;
	   initObjects();
	}
	
	private void initObjects()
	{
	    try {
	    	InputSource inputSource = new InputSource();
	    	inputSource.setCharacterStream(new StringReader(xmlFile));
	    	xmlDocument = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(inputSource);
	    	xPath =  XPathFactory.newInstance().newXPath();
		} catch (Exception ex) {
			 ex.printStackTrace();
		}
	 }
	
	public Object read(String expression)
	{
		try {
			XPathExpression xPathExpression = xPath.compile(expression);
			return xPathExpression.evaluate(xmlDocument);
		} catch (Exception ex) {
			ex.printStackTrace();
			return null;
		}
	}
	
	public Object read(String expression, QName returnType)
	{
		try {
			XPathExpression xPathExpression = xPath.compile(expression);
			return xPathExpression.evaluate(xmlDocument, returnType);
		} catch (Exception ex) {
			ex.printStackTrace();
			return null;
		}
	}
	
	public static void traverse(NodeList rootNode)
	{
	    for(int index = 0; index < rootNode.getLength(); index++)
	    {
	      Node aNode = rootNode.item(index);
	      if (aNode.getNodeType() == Node.ELEMENT_NODE){
	        NodeList childNodes = aNode.getChildNodes();
	        if (childNodes.getLength() < 0){
	          System.out.println("Node Name--&gt;" +aNode.getNodeName() +" , Node Value--&gt;" +aNode.getTextContent());
	        }
	        traverse(aNode.getChildNodes());
	      }
	    }
	  }
	
	
	 public static boolean validateXMLSchema(String xsdPath, String xmlPath)
	 {
		 try {
	            SchemaFactory factory = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
	            Schema schema = factory.newSchema(new File(xsdPath));
	            Validator validator = schema.newValidator();
	            validator.validate(new StreamSource(new File(xmlPath)));
	        } catch (IOException | SAXException e) {
	            System.out.println("Exception: "+e.getMessage());
	            return false;
	        }
	        return true;
	 }
         
	       
}
