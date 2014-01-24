package net.whichones.common.lines.dao;

import org.springframework.stereotype.Repository;

import net.whichones.common.lines.data.Section;

import com.adi3000.common.database.hibernate.session.AbstractDAO;

@Repository
public class SectionDaoImpl extends AbstractDAO<Section> implements SectionDao{

	/**
	 * 
	 */
	private static final long serialVersionUID = -2059626999315067728L;

	public SectionDaoImpl() {
		super(Section.class);
		// TODO Auto-generated constructor stub
	}

}
