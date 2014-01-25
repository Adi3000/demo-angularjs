package net.whichones.common.sheet.data;

import java.io.Serializable;
import java.util.List;

import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;

public class Header implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 8425030960313781983L;
	private static String JSON_NAME = "name";
	private static String JSON_TYPE = "type";
	private static String JSON_VALUE = "value";
	private String name;
	private String type;
	private Boolean value;
	private Integer total; 
	public Header(){
		super();
	};
	Header(JSONObject headerJson) throws JSONException{
		this.name = headerJson.getString(JSON_NAME);
		this.type = headerJson.getString(JSON_TYPE);
		this.value = headerJson.optBoolean(JSON_VALUE);
	}
	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}
	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}
	/**
	 * @return the type
	 */
	public String getType() {
		return type;
	}
	/**
	 * @param type the type to set
	 */
	public void setType(String type) {
		this.type = type;
	}
	
	/**
	 * @return the value
	 */
	public Boolean getValue() {
		return value;
	}
	/**
	 * @param value the value to set
	 */
	public void setValue(Boolean value) {
		this.value = value;
	}
	/**
	 * @return the total
	 */
	public Integer getTotal() {
		return total;
	}
	/**
	 * @param total the total to set
	 */
	public void setTotal(Integer total) {
		this.total = total;
	}
	static JSONArray toJSONArray(List<Header> headers) throws JSONException{
		JSONArray headersJson = new JSONArray();
		JSONObject jsonObject = null;
		for(Header header : headers){
			jsonObject = new JSONObject();
			jsonObject.put(JSON_NAME, header.getName());
			jsonObject.put(JSON_TYPE, header.getType());
			jsonObject.putOpt(JSON_VALUE, header.getValue());
			headersJson.put(jsonObject);
		}
		return headersJson;
	}
	
}
