/*
 * derived from http://dev.eclipse.org/svnroot/rt/org.eclipse.jetty/jetty/trunk/example-jetty-embedded/src/main/java/org/eclipse/jetty/embedded/HelloHandler.java
 */

package org.hyperdata.beeps.server;

import java.io.IOException;
import java.net.URLEncoder;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.eclipse.jetty.server.Request;
import org.eclipse.jetty.server.handler.AbstractHandler;

import org.hyperdata.beeps.DefaultEncoder;
import org.hyperdata.beeps.system.ParameterList;
import org.hyperdata.beeps.system.ParameterListFile;
import org.hyperdata.beeps.util.Tone;

public class EncodeHandler extends AbstractHandler {
    
	String path = "/home/danny/workspace/WebBeep/";
	String configFilename = path+"data/config.xml";
	String audioDir = path+"www/audio/";
	String audioPath = "audio/";
	
	DefaultEncoder encoder;
	
	public EncodeHandler(){
        encoder = new DefaultEncoder("Encoder");
		ParameterListFile plf = new ParameterListFile();
		ParameterList config = plf.load(configFilename);
		encoder.setParameters(config);
		encoder.initFromParameters();
	}
	
    public void handle(String target, Request baseRequest, HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
    	// System.out.println("METHOD="+request.getMethod());
    	
    	if(!request.getMethod().equalsIgnoreCase("POST")) return;
    	 if(!target.equals("/encode")) return;
    	 
        response.setContentType("text/html;charset=utf-8");
        response.setStatus(HttpServletResponse.SC_OK);
        baseRequest.setHandled(true);
        String inputText = request.getParameter("inputText");
        if(inputText.length()>63){ // too long
        	
        }
        if(inputText.length()<1){ // too short
        	
        }
       // System.out.println(inputText);
        
		long startTime = System.currentTimeMillis();
		Tone outTones = encoder.encode(inputText); // "http://danbri.org/foaf.rdf#danbri"

		long encodeTime = System.currentTimeMillis() - startTime;
        
		String filename = URLEncoder.encode(inputText, "UTF-8");
        
		// /home/danny/workspace/WebBeep/audio/qwe.wav (No such file or directory)
		String wavFilename = audioDir + filename+".wav";
		String mp3Filename = audioDir + filename+".mp3";
		// System.out.println("wavFilename="+wavFilename);
		// WavCodec.save(wavFilename, outTones); // SAVE
		
		MP3 converter = new MP3();
		converter.setWavFilename(wavFilename);
		converter.setMp3Filename(mp3Filename);
		converter.mp3(outTones);
		
        response.getWriter().println("<h1>Here are the beeps : </h1>");
        response.getWriter().println("<a href=\""+audioPath+filename+".mp3\">Download</a>");
    }
}