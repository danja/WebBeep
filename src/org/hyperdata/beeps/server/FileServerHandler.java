/**
 * 
 */
package org.hyperdata.beeps.server;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.eclipse.jetty.server.Request;
import org.eclipse.jetty.server.handler.ResourceHandler;

/**
 * @author danny
 *
 */
public class FileServerHandler extends ResourceHandler{
	
	public FileServerHandler(){
		setDirectoriesListed(true);
		setWelcomeFiles(new String[] { "index.html" });
		setResourceBase("www");
	}
	
	public void handle(String target, Request baseRequest,
			HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {
		System.out.println(target);
//		if(target.startsWith("/audio/")){
//			target = "/audio/" + escapeSlashes(target.substring(7, target.length()));
//			System.out.println("new="+target);
//		}
		
		super.handle(target, baseRequest, request, response);
	}

	/**
	 * @param substring
	 * @return
	 */
	public static String escapees(String string) {
		string = string.replaceAll("/", "S_");
		string = string.replaceAll("#", "H_");
	// 	string = string.replaceAll("?", "%_Q%"); regexes not strings!
	//	string = string.replaceAll("&", "%_A%");
		return string;
	}

}
