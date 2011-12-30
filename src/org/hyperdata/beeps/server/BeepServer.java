// ========================================================================
// Copyright (c) 2006-2009 Mort Bay Consulting Pty. Ltd.
// ------------------------------------------------------------------------
// All rights reserved. This program and the accompanying materials
// are made available under the terms of the Eclipse Public License v1.0
// and Apache License v2.0 which accompanies this distribution.
// The Eclipse Public License is available at 
// http://www.eclipse.org/legal/epl-v10.html
// The Apache License v2.0 is available at
// http://www.opensource.org/licenses/apache2.0.php
// You may elect to redistribute this code under either of these licenses. 
// ========================================================================

package org.hyperdata.beeps.server;

import java.io.File;


import org.eclipse.jetty.server.Handler;
import org.eclipse.jetty.server.NCSARequestLog;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.handler.DefaultHandler;
import org.eclipse.jetty.server.handler.HandlerCollection;
import org.eclipse.jetty.server.handler.HandlerList;
import org.eclipse.jetty.server.handler.HandlerWrapper;
import org.eclipse.jetty.server.handler.RequestLogHandler;
import org.eclipse.jetty.server.handler.ResourceHandler;

/* ------------------------------------------------------------ */
/**
 * Frequently many handlers are combined together to handle different aspects of
 * a request. A handler may:
 * <ul>
 * <li>handle the request and completely generate the response
 * <li>partially handle the request, but defer response generation to another
 * handler.
 * <li>select another handler to pass the request to.
 * <li>use business logic to decide to do one of the above.
 * </ul>
 * 
 * Multiple handlers may be combined with:
 * <ul>
 * <li>{@link HandlerWrapper} which will nest one handler inside another. In
 * this example, the HelloHandler is nested inside a HandlerWrapper that sets
 * the greeting as a request attribute.
 * <li>{@link HandlerList} which will call a collection of handlers until the
 * request is marked as handled. In this example, a list is used to combine the
 * param handler (which only handles the request if there are parameters) and
 * the wrapper handler. Frequently handler lists are terminated with the
 * {@link DefaultHandler}, which will generate a suitable 404 response if the
 * request has not been handled.
 * <li>{@link HandlerCollection} which will call each handler regardless if the
 * request has been handled or not. Typically this is used to always pass a
 * request to the logging handler.
 * </ul>
 */
public class BeepServer {
	public static void main(String[] args) throws Exception {
		Server server = new Server(8080);

		ResourceHandler fileServerHandler = new ResourceHandler();
        fileServerHandler.setDirectoriesListed(true);
        fileServerHandler.setWelcomeFiles(new String[]{ "index.html" });
        fileServerHandler.setResourceBase("www");
        
	//	Handler param = new ParamHandler();
		Handler encodeHandler = new EncodeHandler();
		Handler decodeHandler = new DecodeHandler();
		Handler dft = new DefaultHandler();
		RequestLogHandler log = new RequestLogHandler();

		// configure logs
//		log.setRequestLog(new NCSARequestLog(File.createTempFile("demo", "log")
//				.getAbsolutePath()));
		
		log.setRequestLog(new NCSARequestLog("server.log"));
		

		// create the handler collections
		HandlerCollection handlers = new HandlerCollection();
		HandlerList list = new HandlerList();

		// link them all togethe
		list.setHandlers(new Handler[] { fileServerHandler, encodeHandler, decodeHandler, dft });
		handlers.setHandlers(new Handler[] { list, log });

		server.setHandler(handlers);

		server.start();
		server.join();
	}
}
