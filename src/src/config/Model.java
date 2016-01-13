package config;

import java.util.List;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;

public class Model {
	@XmlAttribute
	public boolean isArray = false;
	@XmlAttribute
	public String table;
	@XmlElement(name="field")
	public List<Field> fields;
}
