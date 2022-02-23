package zula.parser;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import zula.dragon.Color;
import zula.dragon.Coordinates;
import zula.dragon.Dragon;
import zula.dragon.DragonCave;
import zula.dragon.DragonType;
import zula.dragon.DragonValidator;
import zula.exceptions.WrongArgumentException;
import org.xml.sax.SAXException;
import zula.util.ArgumentReader;
import zula.util.ConsoleManager;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.TransformerFactoryConfigurationError;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.function.Predicate;

/**
 * class that contains methods that needs to save or download data
 */
public class Mapper {
    private String name = null;
    private Coordinates coordinates = null;
    private Date creationDate = null;
    private long age = -1;
    private float wingspan = -1;
    private Color color = null;
    private DragonType type = null;
    private DragonCave cave = null;
    private int id = -1;
    private final HashMap<String, Integer> flags = new HashMap<String, Integer>();
    private final ArgumentReader argumentReader;
    private final DragonValidator dragonValidator;
    private final ConsoleManager consoleManager;
    public Mapper(ConsoleManager consoleManager) {
        this.argumentReader = new ArgumentReader(consoleManager);
        this.dragonValidator = new DragonValidator(consoleManager);
        this.consoleManager = consoleManager;
    }
    public void fromXML(String path) throws WrongArgumentException, ParserConfigurationException, IOException, SAXException {
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        DocumentBuilder db = dbf.newDocumentBuilder();
        Document document = db.parse(path);
        if (!path.matches("^.*xml$")) {
            throw new IOException();
        }
        NodeList dragos = document.getDocumentElement().getElementsByTagName("dragon");
        for (int i = 0; i < dragos.getLength(); i++) {
            flags.clear();
            Node dragon = dragos.item(i);
            NodeList childs = dragon.getChildNodes();
            checkTextContext(childs);

            if (!flags.containsKey("name") | !flags.containsKey("coordinates") | !flags.containsKey("creationDate") | !flags.containsKey("age") | !flags.containsKey("wingspan") | !flags.containsKey("color") | !flags.containsKey("type") | !flags.containsKey("cave") | !flags.containsKey("id")) {
                throw new WrongArgumentException();
            }

            if (flags.get("name") == 1 && flags.get("coordinates") == 1 && flags.get("creationDate") == 1 && flags.get("age") == 1 && flags.get("wingspan") == 1 && flags.get("color") == 1 && flags.get("type") == 1 && flags.get("cave") == 1 && flags.get("id") == 1) {
                Dragon drago = new Dragon(name, coordinates, age, wingspan, color, type, cave);
                drago.addAttributes(creationDate, id);
                consoleManager.getListManager().addDragon(drago);
            } else {
                throw new WrongArgumentException();
            }
        }
    }





    private  <T> T parseSimpleArgs(Node node, Predicate<T> predicate, StringConverter<T> stringConverter) throws WrongArgumentException {
        String textContent = node.getTextContent();
        if ("null".equals(textContent)) {
           textContent = "";
        }
        if (flags.containsKey(node.getNodeName())) {
            flags.put(node.getNodeName(), flags.get(node.getNodeName()) + 1);
        } else {
            flags.put(node.getNodeName(), 1);
        }
        return argumentReader.parseArgFromString(textContent, predicate, stringConverter);

    }
    private Coordinates parseCoordinates(Node node) throws WrongArgumentException {
        NamedNodeMap atr = node.getAttributes();
        if (atr.getLength() != 2) {
            throw new WrongArgumentException();
        }
        Node xNode = atr.getNamedItem("x");
        Node yNode = atr.getNamedItem("y");
        Double x = parseSimpleArgs(xNode, dragonValidator::xValidator, Double::parseDouble);
        Integer y = parseSimpleArgs(yNode, dragonValidator::yValidator, Integer::parseInt);
        if (flags.containsKey("coordinates")) {
            flags.put("coordinates", flags.get("coordinates") + 1);
        } else {
            flags.put("coordinates", 1);
        }
        return new Coordinates(x, y);
    }

    private DragonCave parseCave(Node node) throws WrongArgumentException {
        NamedNodeMap atr = node.getAttributes();
        if (atr.getLength() != 2) {
            throw new WrongArgumentException();
        }
        Node depthNode = atr.getNamedItem("depth");
        Node numberOfTreasuresNode = atr.getNamedItem("numberOfTreasures");
        Float depth = parseSimpleArgs(depthNode, dragonValidator::depthValidator, Float::parseFloat);
        Double numberOfTreasures = parseSimpleArgs(numberOfTreasuresNode, dragonValidator::numberOfTreasuresValidator, Double::parseDouble);
        if (flags.containsKey("cave")) {
            flags.put("cave", flags.get("cave") + 1);
        } else {
            flags.put("cave", 1);
        }
        return new DragonCave(depth, numberOfTreasures);
    }

    private void checkTextContext(NodeList childs) throws WrongArgumentException {
        for (int j = 0; j < childs.getLength(); j++) {
            Node node = childs.item(j);
            String nodeName = node.getNodeName();
            if ("name".equals(nodeName)) {
                name = parseSimpleArgs(node, dragonValidator::nameValidator, (s) -> s);
            }
            if ("coordinates".equals(nodeName)) {
                coordinates = parseCoordinates(node);
            }
            if ("creationDate".equals(nodeName)) {
                creationDate = parseSimpleArgs(node, dragonValidator::dateValidator, ArgumentParser::parseDate);
            }
            if ("age".equals(nodeName)) {
                age = parseSimpleArgs(node, dragonValidator::ageValidator, Long::parseLong);
            }
            if ("wingspan".equals(nodeName)) {
                wingspan = parseSimpleArgs(node, dragonValidator::wingspanValidator, Float::parseFloat);
            }
            if ("color".equals(nodeName)) {
                color = parseSimpleArgs(node, dragonValidator::colorValidator, Color::valueOf);
            }
            if ("type".equals(nodeName)) {
                type = parseSimpleArgs(node, dragonValidator::typeValidator, DragonType::valueOf);
            }
            if ("cave".equals(nodeName)) {
                cave = parseCave(node);
            }
            if ("id".equals(nodeName)) {
                id = parseSimpleArgs(node, dragonValidator::idValidator, Integer::parseInt);
                consoleManager.getListManager().getUsedId().add(id);
            }
        }
    }
    public void toXML(LinkedList<Dragon> dragons, String path) throws  ParserConfigurationException {
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        DocumentBuilder db = dbf.newDocumentBuilder();
        Document document = db.newDocument();
        Element root = document.createElement("dragons");
        document.appendChild(root);
        for (Dragon dragon : dragons) {
            Node dragonNode = createSubNodes(dragon, document);
            root.appendChild(dragonNode);
        }
        try {
            writeDocument(document, path);
        } catch (Exception e) {
            System.out.println("");
        }
    }
    private Element createSubNodes(Dragon dragon, Document document) {
        Element nodeName = document.createElement("name");
        nodeName.setTextContent(dragon.getName());
        Element nodeAge = document.createElement("age");
        nodeAge.setTextContent(Long.toString(dragon.getAge()));
        Element nodeWingspan = document.createElement("wingspan");
        nodeWingspan.setTextContent(Float.toString(dragon.getWingspan()));
        Element nodeColor = document.createElement("color");
        nodeColor = setColor(dragon, nodeColor);
        Element nodeType = document.createElement("type");
        nodeType.setTextContent(dragon.getType().toString());
        Element nodeCoordinates = document.createElement("coordinates");
        nodeCoordinates.setAttribute("x", Double.toString(dragon.getCoordinates().getX()));
        nodeCoordinates.setAttribute("y", Integer.toString(dragon.getCoordinates().getY()));
        Element nodeCreationDate = document.createElement("creationDate");
        nodeCreationDate.setTextContent(dragon.getCreationDate().toString());
        Element nodeCave = document.createElement("cave");
        nodeCave = setCave(dragon, nodeCave);
        Element nodeId = document.createElement("id");
        nodeId.setTextContent(Integer.toString(dragon.getId()));
        Element dragonNode = document.createElement("dragon");
        dragonNode.appendChild(nodeName);
        dragonNode.appendChild(nodeAge);
        dragonNode.appendChild(nodeWingspan);
        dragonNode.appendChild(nodeColor);
        dragonNode.appendChild(nodeType);
        dragonNode.appendChild(nodeCoordinates);
        dragonNode.appendChild(nodeCreationDate);
        dragonNode.appendChild(nodeCave);
        dragonNode.appendChild(nodeId);
        return dragonNode;
    }
    public Element setColor(Dragon dragon, Element nodeColor) {
        if (dragon.getColor() != null) {
            nodeColor.setTextContent(dragon.getColor().toString());
        } else {
            nodeColor.setTextContent("null");
        }
        return nodeColor;
    }
    public Element setCave(Dragon dragon, Element nodeCave) {
        if (dragon.getCave().getDepth() == null) {
            nodeCave.setAttribute("depth", "null");
        } else {
            nodeCave.setAttribute("depth", Float.toString(dragon.getCave().getDepth()));
        }
        if (dragon.getCave().getNumberOfTreasures() == null) {
            nodeCave.setAttribute("numberOfTreasures", "null");
        } else {
            nodeCave.setAttribute("numberOfTreasures", Double.toString(dragon.getCave().getNumberOfTreasures()));
        }
        return nodeCave;
    }
    public void writeDocument(Document document, String path) throws TransformerFactoryConfigurationError, TransformerException, IOException {
        TransformerFactory transformerFactory = TransformerFactory.newInstance();
        Transformer transformer = transformerFactory.newTransformer();
        DOMSource source = new DOMSource(document);
        transformer.setOutputProperty(OutputKeys.INDENT, "yes");
        StreamResult streamResult = new StreamResult(new FileWriter(path, false));
        transformer.transform(source, streamResult);
        String res = streamResult.getWriter().toString();
        streamResult.getWriter().write(res);
    }

}
