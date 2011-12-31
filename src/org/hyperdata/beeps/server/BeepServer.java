package org.hyperdata.beeps.server;

import java.io.File;

import org.eclipse.jetty.server.Connector;
import org.eclipse.jetty.server.Handler;
import org.eclipse.jetty.server.NCSARequestLog;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.handler.DefaultHandler;
import org.eclipse.jetty.server.handler.HandlerCollection;
import org.eclipse.jetty.server.handler.HandlerList;
import org.eclipse.jetty.server.handler.HandlerWrapper;
import org.eclipse.jetty.server.handler.RequestLogHandler;
import org.eclipse.jetty.server.handler.ResourceHandler;

public class BeepServer {
	
	public static void main(String[] args) throws Exception {

		String host = "localhost";
		int port = 8080;

		if (args.length > 0) {
			host = args[0];
			port = Integer.parseInt(args[1]);
		}
		Server server = new Server(port);

		ResourceHandler fileServerHandler = new ResourceHandler();
		fileServerHandler.setDirectoriesListed(true);
		fileServerHandler.setWelcomeFiles(new String[] { "index.html" });
		fileServerHandler.setResourceBase("www");

		// Handler param = new ParamHandler();
		Handler encodeHandler = new EncodeHandler();
		Handler decodeHandler = new DecodeHandler();
		Handler dft = new DefaultHandler();
		RequestLogHandler log = new RequestLogHandler();

		// configure logs
		// log.setRequestLog(new NCSARequestLog(File.createTempFile("demo",
		// "log")
		// .getAbsolutePath()));

		log.setRequestLog(new NCSARequestLog("server.log"));

		// create the handler collections
		HandlerCollection handlers = new HandlerCollection();
		HandlerList list = new HandlerList();

		// link them all togethe
		list.setHandlers(new Handler[] { fileServerHandler, encodeHandler,
				decodeHandler, dft });
		handlers.setHandlers(new Handler[] { list, log });

		server.setHandler(handlers);

		Connector[] connectors = server.getConnectors();
		for (int i = 0; i < connectors.length; i++) {
			System.out.println("Host set to : "+host);
			connectors[i].setHost(host);
		}

		server.start();
		server.join();
	}
}
