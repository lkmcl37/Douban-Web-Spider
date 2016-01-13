package config;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlValue;

public class Next {
	@XmlValue
    public String value;
	@XmlAttribute
	public int start=1;
	@XmlAttribute
	public int step=1;
}
