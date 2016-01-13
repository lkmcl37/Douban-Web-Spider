package config;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;

public class Dir implements Comparable<Dir>{
	@XmlAttribute
	public String name;
	@XmlAttribute
	public int maxPage = -1;
	@XmlElement
	public String url;
	@XmlElement
	public Next next;
	@XmlElement
	public String xpath;
	@XmlElement
	public String xpath2;
	@Override
	public int compareTo(Dir o) {
		// TODO Auto-generated method stub
		return url.compareTo(o.url);
	}
}
