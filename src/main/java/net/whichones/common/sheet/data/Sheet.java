package net.whichones.common.sheet.data;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

import net.whichones.common.lines.data.Line;

import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONException;
import org.hibernate.annotations.NaturalId;
import org.hibernate.annotations.Type;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.adi3000.common.database.hibernate.data.AbstractDataObject;

@Entity
@Table(name="sheets")
@SequenceGenerator(name = "sheets_sheet_id_seq", sequenceName = "sheets_sheet_id_seq", allocationSize=1)
@XmlRootElement
public class Sheet extends AbstractDataObject{
	private static final Logger log = LoggerFactory.getLogger(Sheet.class);
	/**
	 * 
	 */
	private static final long serialVersionUID = 8808349910534017872L;
	private Integer id;
	private String title;
	private String description;
	private List<Header> headers;
	private String token;
	private String password;
	private User user;
	private Set<Line> lines;
	private Boolean newSheet;
	/**
	 * @return the id
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sheets_sheet_id_seq")
	@Column(name="sheet_id")
	public Integer getId() {
		return id;
	}
	/**
	 * @param id the id to set
	 */
	public void setId(Integer id) {
		this.id = id;
	}
	/**
	 * @return the title
	 */
	@Column(name="sheet_title")
	public String getTitle() {
		return title;
	}
	/**
	 * @param title the title to set
	 */
	public void setTitle(String title) {
		this.title = title;
	}
	/**
	 * @return the description
	 */
	@Column(name="sheet_description")
	public String getDescription() {
		return description;
	}
	/**
	 * @param description the description to set
	 */
	public void setDescription(String description) {
		this.description = description;
	}
	/**
	 * @return the token
	 */
	@Column(name="sheet_token")
	public String getToken() {
		return token;
	}
	/**
	 * @param token the token to set
	 */
	@NaturalId
	public void setToken(String token) {
		this.token = token;
	}
	/**
	 * @return the password
	 */
	@Column(name="sheet_password")
	public String getPassword() {
		return password;
	}
	/**
	 * @param password the password to set
	 */
	public void setPassword(String password) {
		this.password = password;
	}
	/**
	 * @return the user
	 */
	@JoinColumn(name="user_id")
	public User getUser() {
		return user;
	}
	/**
	 * @param user the user to set
	 */
	public void setUser(User user) {
		this.user = user;
	}
	/**
	 * @return the headers
	 */
	@Column(name="sheet_headers")
	@Type(type="com.adi3000.common.database.hibernate.usertype.JSONArrayUserType")
	@XmlTransient
	public JSONArray getHeadersJSON() {
		try {
			return Header.toJSONArray(headers);
		} catch (JSONException e) {
			log.error("Can't parse JSon for database",e);
			return null;
		}
	}
	/**
	 * @param headers the headers to set
	 */
	public void setHeadersJSON(JSONArray headersJson) {
		if(headersJson == null){
			this.headers = null;
		}else{		
			try {
				this.headers = new ArrayList<>(headersJson.length());
				for(int i = 0; i < headersJson.length(); i++){
					this.headers.add(new Header( headersJson.getJSONObject(i)));
				}
			} catch (JSONException e) {
				log.error("Can't parse JSon from database",e);
			}
		}
			
	}
	/**
	 * @return the lines
	 */
	@OneToMany
	@JoinColumn(name="sheet_id")
	public Set<Line> getLines() {
		return lines;
	}
	/**
	 * @param lines the lines to set
	 */
	public void setLines(Set<Line> lines) {
		this.lines = lines;
	}
	/**
	 * @return the newSheet
	 */
	@Transient
	public Boolean getNewSheet() {
		return newSheet;
	}
	/**
	 * @param newSheet the newSheet to set
	 */
	public void setNewSheet(Boolean newSheet) {
		this.newSheet = newSheet;
	}
	/**
	 * @return the headers
	 */
	@Transient
	public List<Header> getHeaders() {
		return headers;
	}
	/**
	 * @param headers the headers to set
	 */
	public void setHeaders(List<Header> headers) {
		this.headers = headers;
	}

}
