/*
 * derived from http://dev.eclipse.org/svnroot/rt/org.eclipse.jetty/jetty/trunk/example-jetty-embedded/src/main/java/org/eclipse/jetty/embedded/HelloHandler.java
 */

package org.hyperdata.server;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.eclipse.jetty.server.Request;
import org.eclipse.jetty.server.handler.AbstractHandler;

public class HelloHandler extends AbstractHandler
{
    final String _greeting;
    final String _body;
    
    public HelloHandler()
    {
        _greeting="Hello World";
        _body=null;
    }
    
    public HelloHandler(String greeting)
    {
        _greeting=greeting;
        _body=null;
    }
    
    public HelloHandler(String greeting,String body)
    {
        _greeting=greeting;
        _body=body;
    }
    
    public void handle(String target, Request baseRequest, HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException
    {
        response.setContentType("text/html;charset=utf-8");
        response.setStatus(HttpServletResponse.SC_OK);
        baseRequest.setHandled(true);
        
        response.getWriter().println("<h1>"+_greeting+"</h1>");
        if (_body!=null)
            response.getWriter().println(_body);
    }
}