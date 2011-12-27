/**
 * 
 */
package org.hyperdata.beeps.parameters;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author danny
 *
 */
public class TimeStampParameter extends SimpleParameter implements Parameter {
	
	public static SimpleDateFormat isoDate = new SimpleDateFormat(
			"yyyy-MM-dd'T'HH:mm:ssz");
	
	public TimeStampParameter(){
		super();
		setName("Date");
		setValue(isoDate.format(new Date()));
	}

}
