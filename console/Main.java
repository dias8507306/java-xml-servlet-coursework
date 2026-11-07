import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.validation.SchemaFactory;
import javax.xml.XMLConstants;
import org.xml.sax.SAXException;
import javax.xml.validation.Schema;
import java.io.File;
import java.io.IOException;

// ====================================================================
// HIERARCHY OF CHILD GAMES
// ====================================================================

// 1. Base Class: Game
abstract class Game implements Comparable<Game> {
    private String name;
    private double weight;
    private double price;

    public Game(String name, double weight, double price) {
        this.name = name;
        this.weight = weight;
        this.price = price;
    }

    public String getName() { return name; }
    public double getWeight() { return weight; }
    public double getPrice() { return price; }

    public String getBaseInfo() {
        // Updated to use English labels
        return "Name: " + name + ", Weight: " + weight + " kg, Price: " + price + " KZT";
    }

    public abstract String getDescription();

    @Override
    public String toString() {
        return getDescription();
    }

    // Default sorting (by weight)
    @Override
    public int compareTo(Game other) {
        return Double.compare(this.weight, other.weight);
    }
}

// 2. Subclass: BoardGame
class BoardGame extends Game {
    private int minPlayers;

    public BoardGame(String name, double weight, double price, int minPlayers) {
        super(name, weight, price);
        this.minPlayers = minPlayers;
    }

    @Override
    public String getDescription() {
        // Updated to use English labels
        return "Board Game (" + getBaseInfo() + ", Min Players: " + minPlayers + ")";
    }
}

// 3. Subclass: SoftToy
class SoftToy extends Game {
    private String material;

    public SoftToy(String name, double weight, double price, String material) {
        super(name, weight, price);
        this.material = material;
    }

    @Override
    public String getDescription() {
        // Updated to use English labels
        return "Soft Toy (" + getBaseInfo() + ", Material: " + material + ")";
    }
}

// ====================================================================
// GIFT CLASS AND LOGIC
// ====================================================================

class Gift {
    private List<Game> games;

    public Gift() {
        this.games = new ArrayList<>();
    }

    public void addGame(Game game) {
        this.games.add(game);
        System.out.println(" " + game.getName() + " added to the gift.");
    }

    // Calculates total weight
    public double calculateTotalWeight() {
        double totalWeight = 0;
        for (Game game : games) {
            totalWeight += game.getWeight();
        }
        return totalWeight;
    }

    // Sorts the games
    public void sortGames(Comparator<Game> comparator) {
        this.games.sort(comparator);
    }

    // Finds games by a specific parameter range (e.g., Weight)
    public List<Game> findGamesByWeightRange(double minWeight, double maxWeight) {
        return games.stream()
                .filter(game -> game.getWeight() >= minWeight && game.getWeight() <= maxWeight)
                .collect(Collectors.toList());
    }

    public List<Game> getGames() {
        return games;
    }
}

// ====================================================================
// XML PARSER (WITH XSD VALIDATION)
// ====================================================================

class XmlParser {

    /**
     * Validates the XML file against the XSD schema.
     */
    public static boolean validateXml(String xmlFilePath, String xsdFilePath) {
        System.out.println("\n--- Starting XSD Validation ---");
        try {
            SchemaFactory factory = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
            Schema schema = factory.newSchema(new File(xsdFilePath));
            
            javax.xml.validation.Validator validator = schema.newValidator();
            validator.validate(new javax.xml.transform.stream.StreamSource(new File(xmlFilePath)));
            
            System.out.println(" Validation successful: XML structure conforms to XSD.");
            return true;
        } catch (SAXException e) {
            System.err.println(" Validation Error: XML does not conform to XSD!");
            System.err.println("Error: " + e.getMessage());
            return false;
        } catch (IOException e) {
            System.err.println(" File Error: " + e.getMessage());
            return false;
        }
    }

    /**
     * Parses the XML file and creates Game objects.
     */
    public static Gift parseXmlToGift(String filePath) {
        Gift gift = new Gift();
        
        try {
            File xmlFile = new File(filePath);
            
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(xmlFile);
            doc.getDocumentElement().normalize();

            NodeList gameNodes = doc.getElementsByTagName("game");
            
            for (int i = 0; i < gameNodes.getLength(); i++) {
                Node gameNode = gameNodes.item(i);
                if (gameNode.getNodeType() == Node.ELEMENT_NODE) {
                    Element gameElement = (Element) gameNode;
                    
                    String type = gameElement.getAttribute("type");
                    String name = gameElement.getAttribute("name");
                    double weight = Double.parseDouble(gameElement.getAttribute("weight"));
                    double price = Double.parseDouble(gameElement.getAttribute("price"));
                    
                    Game game = null;
                    NodeList attributeNodes = gameElement.getElementsByTagName("attribute");
                    
                    if (type.equals("BoardGame")) {
                        int minPlayers = 0;
                        for (int j = 0; j < attributeNodes.getLength(); j++) {
                            Element attr = (Element) attributeNodes.item(j);
                            if (attr.getAttribute("name").equals("minPlayers")) {
                                minPlayers = Integer.parseInt(attr.getTextContent());
                                break;
                            }
                        }
                        game = new BoardGame(name, weight, price, minPlayers);
                    } else if (type.equals("SoftToy")) {
                        String material = "Unknown";
                         for (int j = 0; j < attributeNodes.getLength(); j++) {
                            Element attr = (Element) attributeNodes.item(j);
                            if (attr.getAttribute("name").equals("material")) {
                                material = attr.getTextContent();
                                break;
                            }
                        }
                        game = new SoftToy(name, weight, price, material);
                    }
                    
                    if (game != null) {
                        gift.addGame(game);
                    }
                }
            }
            
        } catch (Exception e) {
            System.err.println(" Parsing Error (after Validation): " + e.getMessage());
        }
        
        return gift;
    }
}

// ====================================================================
// MAIN EXECUTION CLASS
// ====================================================================

public class Main {

    private static final String XML_FILE_PATH = "gift.xml";
    private static final String XSD_FILE_PATH = "gift.xsd";

    public static void main(String[] args) {
        
        System.out.println("=============================================");
        
        // 1. Validate XML document using XSD
        boolean isValid = XmlParser.validateXml(XML_FILE_PATH, XSD_FILE_PATH);
        
        if (!isValid) {
            System.out.println("\n XML document does not conform to the schema. Execution stopped.");
            return;
        }

        // 2. Read XML and populate the Gift object
        System.out.println("\n--- 2.  Reading and Processing XML File ---");
        Gift newYearGift = XmlParser.parseXmlToGift(XML_FILE_PATH);
        
        if (newYearGift.getGames().isEmpty()) {
            System.out.println(" No games were read from the XML file.");
            return;
        }

        // 3. Determine total weight of the gift
        System.out.println("\n--- 3.  Calculating Total Gift Weight ---");
        double totalWeight = newYearGift.calculateTotalWeight();
        System.out.printf("Total number of games added: %d\n", newYearGift.getGames().size());
        System.out.printf("Total gift weight: %.2f kg\n", totalWeight);

        // 4. Sorting (Sorting games by Price in ascending order)
        Comparator<Game> priceComparator = Comparator.comparing(Game::getPrice);
        newYearGift.sortGames(priceComparator);
        
        System.out.println("\n--- 4.  Games Sorted by Price ---");
        for (Game game : newYearGift.getGames()) {
            System.out.println(game);
        }

        // 5. Finding a game corresponding to a given parameter range (Weight: 0.4kg to 0.9kg)
        double minSearchWeight = 0.40;
        double maxSearchWeight = 0.90;
        
        List<Game> foundGames = newYearGift.findGamesByWeightRange(minSearchWeight, maxSearchWeight);

        System.out.printf("\n--- 5.  Finding Games in Weight Range %.2f kg to %.2f kg ---\n", minSearchWeight, maxSearchWeight);
        if (foundGames.isEmpty()) {
            System.out.println("No game found corresponding to the given range.");
        } else {
            for (Game game : foundGames) {
                System.out.println(game);
            }
        }
        System.out.println("=============================================");
    }
}