package org.example.vfs;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;
import org.w3c.dom.Element;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Base64;

public class VfsLoader {
    private static final String ROOT_TAG = "vfs";
    private static final String DIR_TAG = "dir";
    private static final String FILE_TAG = "file";

    public static VfsDirectory load(String path) throws VfsLoadException {
        Path file = Path.of(path);
        if (!Files.exists(file)) {
            throw new VfsLoadException("VFS file not found: " + path);
        }
        Document document = parseXml(file);
        Element root = document.getDocumentElement();
        if (!ROOT_TAG.equals(root.getTagName())) {
            throw new VfsLoadException("root element must be " + ROOT_TAG);
        }

        VfsDirectory rootDir = new VfsDirectory("");

        fillDirectory(root, rootDir);
        return rootDir;
    }

    private static Document parseXml(Path file) throws VfsLoadException {
        try {
            DocumentBuilder builder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
            return builder.parse(file.toFile());
        } catch (SAXException e) {
            throw new VfsLoadException("invalid XML in VFS file: " + e.getMessage());
        } catch (IOException e) {
            throw new VfsLoadException("cannot read VFS file: " + file);
        } catch (ParserConfigurationException e) {
            throw new VfsLoadException("cannot create XML parser");
        }
    }

    private static void fillDirectory(Element element, VfsDirectory directory) throws VfsLoadException {
        NodeList children = element.getChildNodes();

        for (int i = 0; i < children.getLength(); i++) {
            Node node = children.item(i);
            if (node.getNodeType() != Node.ELEMENT_NODE) {
                continue;
            }

            Element child = (Element) node;

            String name = child.getAttribute("name");

            if (name.isEmpty()) {
                throw new VfsLoadException("missing name attribute in " + child.getTagName());
            }

            if (DIR_TAG.equals(child.getTagName())) {
                VfsDirectory dir = new VfsDirectory(name);
                directory.addChild(dir);
                fillDirectory(child, dir);
            } else if (FILE_TAG.equals(child.getTagName())) {
                directory.addChild(createFile(child, name));
            } else {
                throw new VfsLoadException("unknown element " + child.getTagName());
            }
        }
    }

    private static VfsFile createFile(Element element, String name) throws VfsLoadException{
        try {
            byte[] content = Base64.getDecoder().decode(element.getTextContent().strip());
            return new VfsFile(name, content);
        } catch (IllegalArgumentException e) {
            throw new VfsLoadException("invalid base64 content in file " + name);
        }
    }
}
