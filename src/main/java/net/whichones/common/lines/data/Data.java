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
	private Object value;
	
	public Data(){
		
	}
	Data (JSONObject dataJson) throws JSONException{
		this.value = dataJson.get(JSON_VALUE);
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
	static JSONArray toJSONArray(List<Data> data) throws JSONException{
		JSONArray dataJson = new JSONArray();
		JSONObject jsonObject = new JSONObject();
		for(Data datum : data){
			jsonObject.append(JSON_VALUE, datum.getValue());
			dataJson.put(jsonObject);
		}
		return dataJson;
	}
}
