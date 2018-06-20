package ru.javaops.masterjava;

import com.google.common.io.Resources;
import mypackage.ObjectFactory;
import mypackage.Payload;
import mypackage.Project;
import mypackage.User;
import ru.javaops.masterjava.xml.util.JaxbParser;
import ru.javaops.masterjava.xml.util.Schemas;
import ru.javaops.masterjava.xml.util.StaxStreamProcessor;

import javax.xml.stream.XMLStreamConstants;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;
import javax.xml.transform.*;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.util.*;
import java.util.stream.Collectors;

public class MainXml {

    public static void main(String[] args) throws Exception {
        if (args.length != 1) {
            System.out.println("Format: projectName");
            System.exit(1);
        }
        String projectName = args[0];
        URL payloadUrl = Resources.getResource("payload.xml");

//        Set<User> users = parseByJaxb(projectName, payloadUrl);
//        users.forEach(System.out::println);
//
//        Set<User> usersEmails = parseByStax(projectName, payloadUrl);
//        usersEmails.forEach(e -> System.out.println(e.getEmail() + " " + e.getValue()));

        createHtml();
    }

    private static Set<User> parseByStax(String projectName, URL payloadUrl) throws Exception {
        Set<User> users = new HashSet<>();
        Set<String> groups = new HashSet<>();
        try (StaxStreamProcessor processor =
                     new StaxStreamProcessor(payloadUrl.openStream())) {
            XMLStreamReader reader = processor.getReader();
            for (int event = reader.next(); event != XMLStreamConstants.END_DOCUMENT; event = reader.next()) {
                switch (event) {
                    case XMLStreamConstants.START_ELEMENT:
                        if (projectName.equals(reader.getAttributeValue(0))) {
                            groups = getGroups(reader);
                        }
                        if ("Users".equals(reader.getName().getLocalPart())) {
                            users = getUsers(reader, groups);
                        }
                        break;
                    default:
                        break;
                }
            }
        }
        return users;
    }

    private static Set<String> getGroups(XMLStreamReader reader) throws XMLStreamException {
        Set<String> groups = new HashSet<>();
        for (int event = reader.next(); event != XMLStreamConstants.END_DOCUMENT; event = reader.next()) {
            switch (event) {
                case XMLStreamConstants.START_ELEMENT:
                    if ("Group".equals(reader.getLocalName())) {
                        groups.add(reader.getAttributeValue(0));
                    }
                    break;
                case XMLStreamConstants.END_ELEMENT:
                    if ("Project".equals(reader.getLocalName())) {
                        return groups;
                    }
                default:
                    break;
            }
        }
        return groups;
    }

    private static Set<User> getUsers(XMLStreamReader reader, Set<String> groups) throws XMLStreamException {
        HashSet<User> users = new HashSet<>();
        for (int event = reader.next(); event != XMLStreamConstants.END_DOCUMENT; event = reader.next()) {
            switch (event) {
                case XMLStreamConstants.START_ELEMENT:
                    if ("User".equals(reader.getLocalName())) {
                        try {
                            if (!Collections.disjoint(groups, Arrays.asList(reader.getAttributeValue(3).split(" ")))) {
                                User user = new User();
                                user.setEmail(reader.getAttributeValue(2));
                                user.setValue(reader.getElementText());
                                users.add(user);
                            }
                        } catch (Exception e) {
                            System.out.println("This user don't have projects");
                        }
                    }
                    break;
                case XMLStreamConstants.END_ELEMENT:
                    if ("Users".equals(reader.getLocalName())) {
                        return users;
                    }
                default:
                    break;
            }
        }
        return users;
    }

    private static Set<User> parseByJaxb(String projectName, URL payloadUrl) throws Exception {
        JaxbParser jaxbParser = new JaxbParser(ObjectFactory.class);
        jaxbParser.setSchema(Schemas.ofClasspath("payload.xsd"));
        Payload payload;
        try (InputStream is = payloadUrl.openStream()) {
            payload = jaxbParser.unmarshal(is);
        }

        Project project1 = payload.getProjects().getProject()
                .stream()
                .filter(project -> project.getName().equals(projectName))
                .findAny().get();

        return payload.getUsers().getUser()
                .stream()
                .filter(user -> Collections.disjoint(user.getGroupRefs(), project1.getGroup()))
                .collect(Collectors
                        .toCollection(() -> new TreeSet<>(Comparator
                                .comparing(User::getValue).thenComparing(User::getEmail))));
    }

    public static void createHtml() {
        TransformerFactory tFactory = TransformerFactory.newInstance();

        Source xslDoc = new StreamSource("C:\\java projects\\masterjava\\src\\test\\resources\\cities.xsl");
        Source xmlDoc = new StreamSource("C:\\java projects\\masterjava\\src\\test\\resources\\payload.xml");

        String outputFileName = "C:\\java projects\\masterjava\\src\\test\\resources\\CompanyInfo.html";

        OutputStream htmlFile = null;
        try {
            htmlFile = new FileOutputStream(outputFileName);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        Transformer trasform = null;
        try {
            trasform = tFactory.newTransformer(xslDoc);
        } catch (TransformerConfigurationException e) {
            e.printStackTrace();
        }
        try {
            trasform.transform(xmlDoc, new StreamResult(htmlFile));
        } catch (TransformerException e) {
            e.printStackTrace();
        }
    }
}
