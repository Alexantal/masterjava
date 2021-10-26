package ru.javaops.masterjava.xml;

import com.google.common.io.Resources;
import ru.javaops.masterjava.xml.schema.ObjectFactory;
import ru.javaops.masterjava.xml.schema.Payload;
import ru.javaops.masterjava.xml.schema.User;
import ru.javaops.masterjava.xml.util.JaxbParser;
import ru.javaops.masterjava.xml.util.Schemas;

import javax.xml.bind.JAXBException;
import java.io.IOException;
import java.util.Comparator;
import java.util.List;

public class MainXml {
    private static final JaxbParser JAXB_PARSER = new JaxbParser(ObjectFactory.class);

    static {
        JAXB_PARSER.setSchema(Schemas.ofClasspath("payload.xsd"));
    }

    public void mainXmlReader(String xmlPath) throws IOException, JAXBException {
        Payload payload = JAXB_PARSER.unmarshal(
                Resources.getResource(xmlPath).openStream());
        List<User> users = payload.getUsers().getUser();
       users.sort(Comparator.comparing(User::getFullName));
        for (User u : users) {
            System.out.println(u.getFullName());
        }
    }

    public static void main(String[] args) throws JAXBException, IOException {
        MainXml mainXml = new MainXml();
        mainXml.mainXmlReader("payload.xml");
    }
}
