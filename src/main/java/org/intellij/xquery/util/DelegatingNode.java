package org.intellij.xquery.util;

import org.w3c.dom.*;

import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.StringWriter;

/**
 * User: ligasgr
 * Date: 27/09/13
 * Time: 15:42
 */
public class DelegatingNode implements Node {

    private final Document originalDocument;

    public DelegatingNode(Document originalDocument) {
        this.originalDocument = originalDocument;
    }

    @Override
    public Node cloneNode(boolean b) {
        return originalDocument.cloneNode(b);
    }

    @Override
    public void normalize() {
        originalDocument.normalize();
    }

    @Override
    public boolean isSupported(String feature, String version) {
        return originalDocument.isSupported(feature, version);
    }

    @Override
    public String getNamespaceURI() {
        return originalDocument.getNamespaceURI();
    }

    @Override
    public String getPrefix() {
        return originalDocument.getPrefix();
    }

    @Override
    public void setPrefix(String prefix) throws DOMException {
        originalDocument.setPrefix(prefix);
    }

    @Override
    public String getLocalName() {
        return originalDocument.getLocalName();
    }

    @Override
    public boolean hasAttributes() {
        return originalDocument.hasAttributes();
    }

    @Override
    public String getBaseURI() {
        return originalDocument.getBaseURI();
    }

    @Override
    public short compareDocumentPosition(Node other) throws DOMException {
        return originalDocument.compareDocumentPosition(other);
    }

    @Override
    public short getNodeType() {
        return originalDocument.getNodeType();
    }

    @Override
    public Node getParentNode() {
        return originalDocument.getParentNode();
    }

    @Override
    public NodeList getChildNodes() {
        return originalDocument.getChildNodes();
    }

    @Override
    public Node getFirstChild() {
        return originalDocument.getFirstChild();
    }

    @Override
    public Node getLastChild() {
        return originalDocument.getLastChild();
    }

    @Override
    public Node getPreviousSibling() {
        return originalDocument.getPreviousSibling();
    }

    @Override
    public Node getNextSibling() {
        return originalDocument.getNextSibling();
    }

    @Override
    public NamedNodeMap getAttributes() {
        return originalDocument.getAttributes();
    }

    @Override
    public Document getOwnerDocument() {
        return originalDocument.getOwnerDocument();
    }

    @Override
    public String getNodeName() {
        return originalDocument.getNodeName();
    }

    @Override
    public String getNodeValue() throws DOMException {
        return originalDocument.getNodeValue();
    }

    @Override
    public void setNodeValue(String nodeValue) throws DOMException {
        originalDocument.setNodeValue(nodeValue);
    }

    @Override
    public Node insertBefore(Node node, Node node2) throws DOMException {
        return originalDocument.insertBefore(node, node2);
    }

    @Override
    public Node removeChild(Node node) throws DOMException {
        return originalDocument.removeChild(node);
    }

    @Override
    public Node appendChild(Node newChild) throws DOMException {
        return originalDocument.appendChild(newChild);
    }

    @Override
    public boolean hasChildNodes() {
        return originalDocument.hasChildNodes();
    }

    @Override
    public Node replaceChild(Node node, Node node2) throws DOMException {
        return originalDocument.replaceChild(node, node2);
    }

    @Override
    public String getTextContent() throws DOMException {
        return originalDocument.getTextContent();
    }

    @Override
    public void setTextContent(String s) throws DOMException {
        originalDocument.setTextContent(s);
    }

    @Override
    public boolean isSameNode(Node other) {
        return originalDocument.isSameNode(other);
    }

    @Override
    public String lookupPrefix(String namespaceURI) {
        return originalDocument.lookupPrefix(namespaceURI);
    }

    @Override
    public boolean isDefaultNamespace(String namespaceURI) {
        return originalDocument.isDefaultNamespace(namespaceURI);
    }

    @Override
    public String lookupNamespaceURI(String prefix) {
        return originalDocument.lookupNamespaceURI(prefix);
    }

    @Override
    public boolean isEqualNode(Node arg) {
        return originalDocument.isEqualNode(arg);
    }

    @Override
    public Object getFeature(String s, String s2) {
        return originalDocument.getFeature(s, s2);
    }

    @Override
    public Object setUserData(String key, Object data, UserDataHandler handler) {
        return originalDocument.setUserData(key, data, handler);
    }

    @Override
    public Object getUserData(String key) {
        return originalDocument.getUserData(key);
    }

    @Override
    public String toString() {
        DOMSource domSource = new DOMSource(originalDocument);
        StringWriter writer = new StringWriter();
        StreamResult result = new StreamResult(writer);
        TransformerFactory tf = TransformerFactory.newInstance();
        Transformer transformer = null;
        try {
            transformer = tf.newTransformer();
            transformer.transform(domSource, result);
            return writer.toString();
        } catch (TransformerConfigurationException e) {
            e.printStackTrace();
        } catch (TransformerException e) {
            e.printStackTrace();
        }
        return originalDocument.toString();
    }
}
