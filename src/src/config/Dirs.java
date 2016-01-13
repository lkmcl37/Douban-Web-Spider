package config;

import java.util.List;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;

public class Dirs {
	@XmlAttribute
	public String name;
	@XmlAttribute
	public String encode;
	@XmlAttribute
	public String listener = "listener.SpiderListenerAdaptor";
	@XmlAttribute
	public String forbidden;
	@XmlAttribute
	public boolean hasDetail = true;
	@XmlAttribute
	public int next = 1;
	@XmlAttribute(name="parser")
	public String parser;
	@XmlAttribute(name="fetcher")
	public String fetcher = "fetcher.DefaultFetcher";
	@XmlAttribute
	public String dirFetcher;
	@XmlElement(name="dir")
	public List<Dir> dirs;
	@XmlElement(name="model")
	public Model model;
}
