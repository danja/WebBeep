/**
 * 
 */
package org.hyperdata.beeps.server;

import java.io.IOException;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.eclipse.jetty.server.Request;
import org.eclipse.jetty.server.handler.AbstractHandler;
import org.eclipse.jetty.util.ajax.JSON;

public class ParamHandler extends AbstractHandler {
	
	public void handle(String target, Request baseRequest,
			HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {
		Map params = request.getParameterMap();
		System.out.println(JSON.toString(params));
		
		if (params.size() > 0) {
			response.setContentType("text/plain");
			response.getWriter().println(JSON.toString(params));
			((Request) request).setHandled(true);
		}
	}
}