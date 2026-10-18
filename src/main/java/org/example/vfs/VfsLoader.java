package org.example.vfs;

import org.w3c.dom.Document;
import org.xml.sax.SAXException;
import org.w3c.dom.Element;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class VfsLoader {
    private static final String ROOT_TAG = "vfs";
    private static final String DIR_TAG = "dir";
    private static final String FILE_TAG = "file";

    public static VfsDirectory load(String path) throws VfsLoadException {
        Path file = Path.of(path);
        if (!Files.exists(file)){
            throw new VfsLoadException("VFS file not found: " + path);
        }
        Document document = parseXml(file);
        Element root = document.getDocumentElement();
        if(!ROOT_TAG.equals(root.getTagName())){
            throw new VfsLoadException("root element must be " + ROOT_TAG);
        }

        VfsDirectory rootDir = new VfsDirectory("");
        return rootDir;
    }

    private static Document parseXml(Path file) throws VfsLoadException {
        try{
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
}
