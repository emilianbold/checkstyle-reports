/** This file is part of Checkstyle Reports.
 *
 * Checkstyle Reports is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License.
 *
 * Checkstyle Reports is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with Foobar.  If not, see <http://www.gnu.org/licenses/>.
 */
package ro.emilianbold.reports.checkstyle.handler;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

/**
 * A SAX handler which acts as a proxy, sending start/endElement events to all the children.
 *
 * @author Emilian Bold
 */
public final class ProxyHandler extends DefaultHandler {
    private AbstractGeneralHandler[] list;
    
    /**
     * Create the proxy.
     * @param ls The list of handlers that will get the proxied startElement/endElement messages.
     * They should not throw any exception otherwise some of the handlers in the list might not
     * get called.
     */
    public ProxyHandler(AbstractGeneralHandler... ls){
        list=ls;
    }
    
    public void startElement(String string, String string0, String string1, Attributes attributes) throws SAXException {
        for(DefaultHandler dh : list){
            dh.startElement(string, string0,string1, attributes);
        }
    }
    
    public void endElement(String string, String string0, String string1) throws SAXException {
        for(DefaultHandler dh : list){
            dh.endElement(string, string0,string1);
        }
    }
    
    /**
     * @return The list of handlers I proxy to.
     */
    public AbstractGeneralHandler[] getHandlers() {
        return list;
    }
    
    
    
}