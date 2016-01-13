package config;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlValue;

public class Field {
	@XmlAttribute
	public String name;
	@XmlAttribute
	public boolean html;
	@XmlValue
	public String value;
}
