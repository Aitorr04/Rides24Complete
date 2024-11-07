package businesslogic;

import configuration.ConfigXML;
import dataAccess.DataAccess;

import javax.xml.namespace.QName;
import javax.xml.ws.Service;
import java.net.MalformedURLException;
import java.net.URL;

public class BusinessLogicFactory {

    public static BLFacade createBusinessLogic(boolean local) throws MalformedURLException {
        if (local) return new BLFacadeImplementation(new DataAccess());
        else {
            ConfigXML c = ConfigXML.getInstance();

            String serviceName = "http://" + c.getBusinessLogicNode() + ":" + c.getBusinessLogicPort() + "/ws/" + c.getBusinessLogicName() + "?wsdl";

            URL url = new URL(serviceName);

            // 1st argument refers to wsdl document above
            // 2nd argument is service name, refer to wsdl document above
            QName qname = new QName("http://businessLogic/", "BLFacadeImplementationService");

            Service service = Service.create(url, qname);

            return service.getPort(BLFacade.class);
        }
    }
}
