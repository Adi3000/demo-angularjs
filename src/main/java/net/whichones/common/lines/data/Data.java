package net.whichones.common.lines.data;

import java.io.Serializable;
import java.util.List;

import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;

public class Data implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 3191158060214209558L;
	private static String JSON_VALUE = "value";
	private static String JSON_NEW_VALUE = "newValue";
	private Object value;
	private Boolean newValue;
	
	public Data(){
		super();
	}

	Data (JSONObject dataJson) throws JSONException{
		this.value = dataJson.get(JSON_VALUE);
		this.newValue = dataJson.optBoolean(JSON_NEW_VALUE);
	}
	/**
	 * @return the value
	 */
	public Object getValue() {
		return value;
	}
	/**
	 * @param value the value to set
	 */
	public void setValue(Object value) {
		this.value = value;
	}
	/**
	 * @return the newValue
	 */
	public Boolean getNewValue() {
		return newValue;
	}
	/**
	 * @param newValue the newValue to set
	 */
	public void setNewValue(Boolean newValue) {
		this.newValue = newValue;
	}
	static JSONArray toJSONArray(List<Data> data) throws JSONException{
		JSONArray dataJson = new JSONArray();
		JSONObject jsonObject = null;
		for(Data datum : data){
			jsonObject = new JSONObject();
			jsonObject.put(JSON_VALUE, datum.getValue());
			jsonObject.putOpt(JSON_NEW_VALUE, datum.getNewValue());
			dataJson.put(jsonObject);
		}
		return dataJson;
	}
}
