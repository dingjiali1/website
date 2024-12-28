package response;


import java.util.ArrayList;
import java.util.Stack;
import java.io.*; 
import org.xml.sax.*;
import org.xml.sax.helpers.*;
import java.lang.reflect.*;

 
class MenuParserHandler extends DefaultHandler
{
    //This is the list which shall be populated while parsing the XML.
    public ArrayList menuList = new ArrayList();
 
    //As we read any XML element we will push that in this stack
    private Stack<String> elementStack = new Stack();
 
    //As we complete one user block in XML, we will push the User instance in userList
    private Stack<MenuInfo> objectStack = new Stack();
 
    public void startDocument() throws SAXException
    {
        //ln("start of the document   : ");
    }
 
    public void endDocument() throws SAXException
    {
        //ln("end of the document document     : ");
    }
 
    public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException
    {
        //Push it in element stack
        this.elementStack.push(qName);
 
        //If this is start of 'user' element then prepare a new User instance and push it in object stack
        if ("item".equals(qName))
        {
            MenuInfo m = new MenuInfo(0,"","","");
            m.id = 1;//attributes.getValue(0);
    m.icon = attributes.getValue(1);
    m.showAsAction = attributes.getValue(2);
    if (m.showAsAction!=null && m.showAsAction.indexOf("@string/")>=0) 
    try
    { 
           m.showAsAction = m.showAsAction.replaceFirst("@string/","");
           Class  aClass = R.string.class;
           Field field = aClass.getField(m.showAsAction);
           m.showAsAction = (String)(field.get(new R.string()));
    }
    catch(Exception e){}
    m.title = attributes.getValue(3);
    if (m.title!=null && m.title.indexOf("@string/")>=0) 
    try
    { 
           m.title = m.title.replaceFirst("@string/","");
           Class  aClass = R.string.class;
           Field field = aClass.getField(m.title);
           m.title = (String)(field.get(new R.string()));
    }
    catch(Exception e){}
 
            this.objectStack.push(m);
        }
    }
 
    public void endElement(String uri, String localName, String qName) throws SAXException
    {
        //Remove last added  element
        this.elementStack.pop();
 
        //User instance has been constructed so pop it from object stack and push in userList
        if ("item".equals(qName))
        {
            menuList.add(this.objectStack.pop());
        }
    }
 
    
    public void characters(char[] ch, int start, int length) throws SAXException
    {
        String value = new String(ch, start, length).trim();
 
        if (value.length() == 0)
        {
            return; // ignore white space
        }
  
    }
}

class MenusXmlParser
{
    public ArrayList parseXml(InputStream in)
    {
        //Create a empty link of users initially
        ArrayList<MenuInfo> menu  = new ArrayList();
        try
        {
            //Create default handler instance
            MenuParserHandler handler = new MenuParserHandler();
 
            //Create parser from factory
            XMLReader parser = XMLReaderFactory.createXMLReader();
 
            //Register handler with parser
            parser.setContentHandler(handler);
 
            //Create an input source from the XML input stream
            InputSource source = new InputSource(in);
 
            //parse the document
            parser.parse(source);
 
            //populate the parsed users list in above created empty list; You can return from here also.
            menu = handler.menuList;
 
        } catch (SAXException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } finally {
 
        }
        return menu;
    }
}
public class Parse {
    
 
    public static void main(String[] args) throws FileNotFoundException
    {
        //Locate the file
        File xmlFile = new File("res" + File.separator + "menu" + File.separator + "browse_menu.xml");
 
        //Create the parser instance
        MenusXmlParser parser = new MenusXmlParser();
 
        //Parse the file
        ArrayList users = parser.parseXml(new FileInputStream(xmlFile));
  
    }
}

