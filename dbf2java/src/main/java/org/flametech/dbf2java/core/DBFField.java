package org.flametech.dbf2java.core;


public class DBFField {
	
	private String name;
	private DBFFieldTypeEnum type;
	private int length;	
	private int numberOfDecimalPlaces;
	private int offset;
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public DBFFieldTypeEnum getType() {
		return type;
	}
	public void setType(DBFFieldTypeEnum type) {
		this.type = type;
	}
	public int getLength() {
		return length;
	}
	public void setLength(int length) {
		this.length = length;
	}
	public int getNumberOfDecimalPlaces() {
		return numberOfDecimalPlaces;
	}
	public void setNumberOfDecimalPlaces(int numberOfDecimalPlaces) {
		this.numberOfDecimalPlaces = numberOfDecimalPlaces;
	}
	public int getOffset() {
		return offset;
	}
	public void setOffset(int offset) {
		this.offset = offset;
	}
	@Override
	public String toString() {
		return "DbfField [\n  name=" + name + ", \n  type=" + type
				+ ", \n  length=" + length + ", \n  numberOfDecimalPlaces="
				+ numberOfDecimalPlaces + ", \n  offset=" + offset + "\n]";
	}	
	
	public String getStringRepresentation() {
		StringBuilder sb = new StringBuilder();
		sb.append(name).append(",");
		sb.append(type.getType()).append(",");
		sb.append(length).append(",");
		sb.append(numberOfDecimalPlaces);
		return sb.toString();
	}
	
	public static DBFField fromStringRepresentation(String s) {
		String[] a = s.split(",");
		
		DBFField f = new DBFField();
		f.setName(a[0]);
		f.setType(DBFFieldTypeEnum.fromChar(a[1].charAt(0)));
		f.setLength(Integer.parseInt(a[2]));
		f.setNumberOfDecimalPlaces(Integer.parseInt(a[3]));
		return f;
	}	
}
