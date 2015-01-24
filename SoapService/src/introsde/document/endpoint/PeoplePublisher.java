package introsde.document.endpoint;

import introsde.document.ws.PeopleImpl;

import javax.xml.ws.Endpoint;

/**
 * Created by gt on 11/20/14.
 */
public class PeoplePublisher {
    public static String SERVER_URL = "http://10.23.179.211";
    public static String PORT = "8000";
    public static String BASE_URL = "/ws/people";

    public static String getEndpointURL() {
        return SERVER_URL+":"+PORT+BASE_URL;
    }

    public static void main(String[] args) {
        String endpointUrl = getEndpointURL();
        System.out.println("Starting People Service...");
        System.out.println("--> Published at = "+endpointUrl);

        Endpoint.publish(endpointUrl, new PeopleImpl());
    }
}