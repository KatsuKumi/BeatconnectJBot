package beatconnect.bot.main;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.DocumentBuilder;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.w3c.dom.Node;
import org.w3c.dom.Element;
import java.io.File;

public class Settings {

    private String _username;
    private String _password;
    private String _osuAPIKey;
    private String _beatconnectAPIKey;

    public Settings(){
        try {
            File XmlFile = new File("settings.xml");
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(XmlFile);
            doc.getDocumentElement().normalize();

            this._username = doc.getElementsByTagName("username").item(0).getTextContent();
            this._password = doc.getElementsByTagName("password").item(0).getTextContent();
            this._osuAPIKey = doc.getElementsByTagName("osuAPIKey").item(0).getTextContent();
            this._beatconnectAPIKey = doc.getElementsByTagName("beatconnectAPIKey").item(0).getTextContent();


        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public String getUsername() {
        return _username;
    }

    public String getPassword() {
        return _password;
    }

    public String getOsuAPIKey() {
        return _osuAPIKey;
    }

    public String getBeatconnectAPIKey() {
        return _beatconnectAPIKey;
    }
}
