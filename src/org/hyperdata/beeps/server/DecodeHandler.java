package org.hyperdata.beeps.server;

import java.io.File;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.eclipse.jetty.server.Request;
import org.eclipse.jetty.server.handler.AbstractHandler;

import org.hyperdata.beeps.DefaultDecoder;
import org.hyperdata.beeps.system.ParameterList;
import org.hyperdata.beeps.system.ParameterListFile;
import org.hyperdata.beeps.util.Tone;

public class DecodeHandler extends AbstractHandler {

	String configFilename = "data/config.xml";
	DefaultDecoder decoder = new DefaultDecoder("Decoder");

	public DecodeHandler() {
		ParameterListFile plf = new ParameterListFile();
		ParameterList config = plf.load(configFilename);
		decoder.setParameters(config);
		decoder.initFromParameters();
	}

	public void handle(String target, Request baseRequest,
			HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {

		if (!request.getMethod().equalsIgnoreCase("POST"))
			return;
		if (!target.equals("/decode"))
			return;

		System.out.println("decode");
		boolean isMultipart = ServletFileUpload.isMultipartContent(request);
		// Create a factory for disk-based file items
		DiskFileItemFactory factory = new DiskFileItemFactory();

		int maxMemorySize = 100 * 1000000; // 100MB
		String fileDir = System.getProperty("java.io.tmpdir");
		int maxRequestSize = 10 * 1000000; // 10MB

		// Set factory constraints
		factory.setSizeThreshold(maxMemorySize);
		factory.setRepository(new File(fileDir));

		// Create a new file upload handler
		ServletFileUpload upload = new ServletFileUpload(factory);

		// Set overall request size constraint
		upload.setSizeMax(maxRequestSize);

		// Parse the request
		List items = null;
		try {
			items = upload.parseRequest(request);
		} catch (FileUploadException exception) {
			exception.printStackTrace();
		}

		Iterator iter = items.iterator();
		FileItem item = (FileItem) iter.next();

		// random filename
		String filenameBase = Long.toHexString(Double.doubleToLongBits(Math
				.random()));
		String mp3Filename = "www/audio/" + filenameBase + ".mp3";
		String wavFilename = "www/audio/" + filenameBase + ".wav";

		File uploadedFile = new File(mp3Filename);
		try {
			System.out.println("writing to file");
			item.write(uploadedFile);

		} catch (Exception exception) {
			// TODO Auto-generated catch block
			exception.printStackTrace();
		}

		// convert mp3 to wav
		MP3 converter = new MP3();
		converter.setWavFilename(wavFilename);
		converter.setMp3Filename(mp3Filename);
		Tone tone = converter.fromMp3();

		// decode
		long startTime = System.currentTimeMillis();
		String output = decoder.decode(tone);
		long decodeTime = System.currentTimeMillis() - startTime;

		response.setContentType("text/html;charset=utf-8");
		response.setStatus(HttpServletResponse.SC_OK);
		baseRequest.setHandled(true);

		response.getWriter().println("<h1>" + output + "</h1>");
		response.getWriter().println("<p><a href=\"/\">Back</a></p>");
	}
}