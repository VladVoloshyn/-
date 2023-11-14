import org.w3c.dom.*;
import org.xml.sax.SAXException;

import javax.xml.parsers.*;
import javax.xml.transform.*;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import javax.xml.validation.Validator;
import java.io.File;
import java.io.IOException;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class Main {

    public static void main(String[] args) {
        try {
            validateXMLAgainstXSD("knife.xml", "knife.xsd");

            Knife knife = parseXML("knife.xml");

            System.out.println("Knife Type: " + knife.getType());
            System.out.println("Handy: " + knife.getHandy());
            System.out.println("Origin: " + knife.getOrigin());
            System.out.println("Value: " + knife.getValue());

            System.out.println("Visual Characteristics:");
            for (Visual visual : knife.getVisual()) {
                System.out.println("Blade Length: " + visual.getBlade().getLength() + " cm");
                System.out.println("Blade Width: " + visual.getBlade().getWidth() + " mm");
                System.out.println("Blade Material: " + visual.getBlade().getMaterial());
                Visual vs = knife.getVisual().get(0);
                if (vs.getHandle() != null) {
                    System.out.println("Handle Type: " + vs.getHandle().getType());
                } else {
                    System.out.println("Handle Type is not specified.");
                }
                System.out.println("Blood Groove: " + visual.getBloodGroove());
            }

            List<Knife> knives = new ArrayList<>();
            knives.add(knife);

            Collections.sort(knives, new KnifeComparator());

            System.out.println("Sorted Knives:");
            for (Knife sortedKnife : knives) {
                System.out.println("Knife Type: " + sortedKnife.getType());
            }

            String xmlString = convertToXML(knife);
            System.out.println("XML Representation of Knife:");
            System.out.println(xmlString);

        } catch (SAXException | IOException | ParserConfigurationException | TransformerException e) {
            e.printStackTrace();
        }
    }

    public static Knife parseXML(String xmlFilePath) throws IOException, SAXException, ParserConfigurationException {
        DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();

        Document document = documentBuilder.parse(new File(xmlFilePath));
        Element knifeElement = document.getDocumentElement();

        Knife knife = new Knife();
        knife.setType(getSingleChildValue(knifeElement, "Type"));
        knife.setHandy(getSingleChildValue(knifeElement, "Handy"));
        knife.setOrigin(getSingleChildValue(knifeElement, "Origin"));
        knife.setValue(getSingleChildValue(knifeElement, "Value"));

        NodeList visualNodes = knifeElement.getElementsByTagName("Visual");
        List<Visual> visuals = new ArrayList<>();
        for (int i = 0; i < visualNodes.getLength(); i++) {
            Element visualElement = (Element) visualNodes.item(i);
            Visual visual = new Visual();
            Element bladeElement = getSingleChild(visualElement, "Blade");
            Blade blade = new Blade();
            blade.setLength(Integer.parseInt(getSingleChildValue(bladeElement, "Length")));
            blade.setWidth(Integer.parseInt(getSingleChildValue(bladeElement, "Width")));
            blade.setMaterial(getSingleChildValue(bladeElement, "Material"));
            visual.setBlade(blade);
            visual.setBloodGroove(getSingleChildValue(visualElement, "BloodGroove"));
            visuals.add(visual);
        }
        knife.setVisual(visuals);

        return knife;
    }

    public static void validateXMLAgainstXSD(String xmlFilePath, String xsdFilePath) throws SAXException, IOException {
        try {
            SchemaFactory factory = SchemaFactory.newInstance("http://www.w3.org/2001/XMLSchema");
            Schema schema = factory.newSchema(new File(xsdFilePath));
            Validator validator = schema.newValidator();
            validator.validate(new StreamSource(new File(xmlFilePath)));
        } catch (SAXException e) {
            throw e;
        }
    }

    public static String getSingleChildValue(Element parent, String childName) {
        Element child = getSingleChild(parent, childName);
        return (child != null) ? child.getTextContent() : null;
    }

    public static Element getSingleChild(Element parent, String childName) {
        NodeList children = parent.getElementsByTagName(childName);
        return (children.getLength() > 0) ? (Element) children.item(0) : null;
    }

    static class KnifeComparator implements Comparator<Knife> {
        @Override
        public int compare(Knife knife1, Knife knife2) {
            return knife1.getType().compareTo(knife2.getType());
        }
    }

    public static String convertToXML(Knife knife) throws ParserConfigurationException, TransformerException {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        Document doc = builder.newDocument();

        Element knifeElement = doc.createElement("Knife");
        doc.appendChild(knifeElement);

        addChildElement(doc, knifeElement, "Type", knife.getType());
        addChildElement(doc, knifeElement, "Handy", knife.getHandy());
        addChildElement(doc, knifeElement, "Origin", knife.getOrigin());
        addChildElement(doc, knifeElement, "Value", knife.getValue());

        for (Visual visual : knife.getVisual()) {
            Element visualElement = doc.createElement("Visual");
            addChildElement(doc, visualElement, "Blade", "");
            Element bladeElement = doc.createElement("Blade");
            addChildElement(doc, bladeElement, "Length", String.valueOf(visual.getBlade().getLength()));
            addChildElement(doc, bladeElement, "Width", String.valueOf(visual.getBlade().getWidth()));
            addChildElement(doc, bladeElement, "Material", visual.getBlade().getMaterial());
            visualElement.appendChild(bladeElement);

            if (visual.getHandle() != null) {
                Element handleElement = doc.createElement("Handle");
                addChildElement(doc, handleElement, "Type", visual.getHandle().getType());
                visualElement.appendChild(handleElement);
            }

            addChildElement(doc, visualElement, "BloodGroove", visual.getBloodGroove());
            knifeElement.appendChild(visualElement);
        }

        Transformer transformer = TransformerFactory.newInstance().newTransformer();
        transformer.setOutputProperty(OutputKeys.INDENT, "yes");
        DOMSource source = new DOMSource(doc);
        StringWriter writer = new StringWriter();
        StreamResult result = new StreamResult(writer);
        transformer.transform(source, result);

        return writer.toString();
    }

    public static void addChildElement(Document doc, Element parent, String elementName, String textContent) {
        Element child = doc.createElement(elementName);
        child.setTextContent(textContent);
        parent.appendChild(child);
    }
}
