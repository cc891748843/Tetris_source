package config;

import org.dom4j.Element;

public class DataConfig {
	
	private final int maxRom;
	
	private final DataInterfaceConfig dataA;
	private final DataInterfaceConfig dataB;
	
	public DataConfig(Element data) {
		this.maxRom = Integer.parseInt(data.attributeValue("maxRom"));
		dataA = new DataInterfaceConfig(data.element("dataA"));
		dataB = new DataInterfaceConfig(data.element("dataB"));
	}

	public DataInterfaceConfig getDataA() {
		return dataA;
	}

	public DataInterfaceConfig getDataB() {
		return dataB;
	}

	public int getMaxRow() {
		return maxRom;
	}
	
	
}
