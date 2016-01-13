package config;

import java.util.List;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name="spiderConfig")
public class SpiderConfig {
	@XmlAttribute
	public String name;
	
	@XmlAttribute(name="listener")
	public String listener = "listener.SpiderListenerAdaptor";
	@XmlElement(name="dirs")
	public List<Dirs> dirs;
}
