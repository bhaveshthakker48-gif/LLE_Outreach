package org.impactindia.llemeddocket.ws;

import com.google.gson.JsonSyntaxException;
import org.impactindia.llemeddocket.pojo.WsModel;
import java.io.IOException;

/**
 * Created by devandroid on 17/5/17.
 */

public interface IWebService<T extends WsModel> {

    /**
     * Gets the response object returned from webservices.
     * @return the response object
     * @throws IOException
     * @throws IllegalStateException
     * @throws JsonSyntaxException
     */
    public T getResponseObject() throws IOException, IllegalStateException, JsonSyntaxException;

    /**
     * Gets the response array, useful for services that return array of objects.
     * @return the response array
     * @throws IOException
     * @throws IllegalStateException
     * @throws JsonSyntaxException
     */
    public T getResponseArray() throws IOException, IllegalStateException, JsonSyntaxException;

    /**
     * Gets the response as plain String, useful for services that return plain text.
     * @return the response in String format
     * @throws IOException
     */
    public String getResponseString() throws IOException;
}
