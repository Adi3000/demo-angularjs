package net.whichones.common.sheet.data;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.adi3000.common.database.hibernate.data.AbstractDataObject;

@Entity
@Table(name="users")
@SequenceGenerator(name = "users_user_id_seq", sequenceName = "users_user_id_seq", allocationSize=1)
public class User extends AbstractDataObject implements com.adi3000.common.util.security.User{

	/**
	 * 
	 */
	private static final long serialVersionUID = 8525488623929494945L;
	private Integer id;
	private String login;
	private String mail;
	private String token;
	private String password;
	private Timestamp lastDateLogin;
	private String lastIpLogin;
	private Integer loginState;
	private String salt;
	/**
	 * @return the id
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "users_user_id_seq")
	@Column(name="user_id")
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
	 * @return the login
	 */
	@Column(name="user_login")
	public String getLogin() {
		return login;
	}
	/**
	 * @param login the login to set
	 */
	public void setLogin(String login) {
		this.login = login;
	}
	/**
	 * @return the mail
	 */
	@Column(name="user_mail")
	public String getMail() {
		return mail;
	}
	/**
	 * @param mail the mail to set
	 */
	public void setMail(String mail) {
		this.mail = mail;
	}
	/**
	 * @return the token
	 */
	@Column(name="user_token")
	public String getToken() {
		return token;
	}
	/**
	 * @param token the token to set
	 */
	public void setToken(String token) {
		this.token = token;
	}
	/**
	 * @return the password
	 */
	@Column(name="user_password")
	public String getPassword() {
		return password;
	}
	/**
	 * @param password the password to set
	 */
	public void setPassword(String password) {
		this.password = password;
	}
	@Override
	@Column(name="user_last_date_login")
	public Timestamp getLastDateLogin() {
		return lastDateLogin;
	}
	@Override
	@Column(name="user_last_ip_login")
	public String getLastIpLogin() {
		return lastIpLogin;
	}
	@Override
	@Column(name="user_login_state")
	public Integer getLoginState() {
		return loginState;
	}
	@Override
	@Column(name="user_salt")
	public String getSalt() {
		return salt;
	}
	@Override
	public void setSalt(String salt) {
		this.salt = salt;
	}
	/**
	 * @param lastDateLogin the lastDateLogin to set
	 */
	public void setLastDateLogin(Timestamp lastDateLogin) {
		this.lastDateLogin = lastDateLogin;
	}
	/**
	 * @param lastIpLogin the lastIpLogin to set
	 */
	public void setLastIpLogin(String lastIpLogin) {
		this.lastIpLogin = lastIpLogin;
	}
	/**
	 * @param loginState the loginState to set
	 */
	public void setLoginState(Integer loginState) {
		this.loginState = loginState;
	}


}
