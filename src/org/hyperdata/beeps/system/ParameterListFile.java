/**
 * 
 */
package org.hyperdata.beeps.system;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Iterator;
import java.util.Map.Entry;
import java.util.Properties;
import java.util.Set;

import org.hyperdata.beeps.GoCodec;
import org.hyperdata.beeps.parameters.SimpleParameter;

/**
 * example:
 * 
 * <entry key="Decoder.LP_FIR2.npoints^^datatype">java.lang.Integer</entry>
 * <entry key="Decoder.LP_FIR2.npoints">374</entry>
 * 
 * @author danny
 * 
 */
public class ParameterListFile {

	private Properties properties = new Properties();
	private ParameterList parameters;

	public void save(ParameterList parameters, String filename) {
		this.parameters = parameters;
		for (int i = 0; i < parameters.size(); i++) {
			String name = parameters.get(i).getName();
			Object value = parameters.get(i).getValue();
			String datatype = value.getClass().toString().split(" ")[1]; // class
																			// java.lang.Integer
																			// ->
																			// java.lang.Integer
			properties.setProperty(name, value.toString());
			properties.setProperty(name + "^^datatype", datatype);
		}
		OutputStream out;
		try {
			out = new FileOutputStream(filename);
			properties.storeToXML(out, "testing");
			out.close();
		} catch (Exception exception) {
			exception.printStackTrace();
		}

	}

	public ParameterList load(String filename) {
		properties.clear();
		parameters = new DefaultParameterList("From File");
		InputStream in;
		try {
			in = new FileInputStream(filename);
			properties.loadFromXML(in);
			in.close();
		} catch (Exception exception) {
			exception.printStackTrace();
		}
		Iterator<String> keys = properties.stringPropertyNames().iterator();
		while (keys.hasNext()) {
			String key = keys.next();
			// String valueString = properties.getProperty(key);
			if (key.endsWith("^^datatype")) {
				String datatype = properties.getProperty(key);
				String name = key.substring(0,
						key.length() - "^^datatype".length());
				String value = properties.getProperty(name);
				parameters.add(makeParameter(name, value, datatype));
			}
		}
		return parameters;
	}

	public String toString() {
		return parameters.toString();
	}

	/**
	 * @param name
	 * @param value
	 * @param datatype
	 * @return
	 */
	private Parameter makeParameter(String name, String value, String datatype) {

		Object valueObject = null;
		if ("java.lang.String".equals(datatype)) {
			valueObject = value;
		}
		if ("java.lang.Boolean".equals(datatype)) {
			valueObject = Boolean.parseBoolean(value);
		}
		if ("java.lang.Integer".equals(datatype)) {
			valueObject = Integer.parseInt(value);
		}
		if ("java.lang.Float".equals(datatype)) {
			valueObject = Float.parseFloat(value);
		}
		if ("java.lang.Double".equals(datatype)) {
			valueObject = Double.parseDouble(value);
		}
		return new SimpleParameter(name, valueObject);
	}

	public static void main(String[] args) {
		GoCodec codec = new GoCodec();
		codec.init();
		ParameterListFile file = new ParameterListFile();
		String filename = "./data/props.xml";
		file.save(codec.getParameters(), filename);
		ParameterListFile infile = new ParameterListFile();
		ParameterList parameters = infile.load(filename);
		System.out.println(parameters);
	}
}
