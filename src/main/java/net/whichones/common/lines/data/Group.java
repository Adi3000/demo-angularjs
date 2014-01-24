package net.whichones.common.lines.data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.adi3000.common.database.hibernate.data.AbstractDataObject;

@Entity
@Table(name="groups")
@SequenceGenerator(name = "groups_group_id_seq", sequenceName = "groups_group_id_seq", allocationSize=1)
public class Group extends AbstractDataObject{

	/**
	 * 
	 */
	private static final long serialVersionUID = 3261867112232758544L;
	private Integer id;
	private Integer index;
	private String name;
	
	/**
	 * @return the name
	 */
	@Column(name="group_name")
	public String getName() {
		return name;
	}
	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}
	public Group() {
	}
	/**
	 * @return the id
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "groups_group_id_seq")
	@Column(name="group_id")
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
	 * @return the index
	 */
	@Column(name="group_index")
	public Integer getIndex() {
		return index;
	}
	/**
	 * @param index the index to set
	 */
	public void setIndex(Integer index) {
		this.index = index;
	}

}
