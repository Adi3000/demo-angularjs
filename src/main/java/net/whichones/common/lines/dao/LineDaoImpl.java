package net.whichones.common.lines.dao;

import org.springframework.stereotype.Repository;

import net.whichones.common.lines.data.Line;

import com.adi3000.common.database.hibernate.session.AbstractDAO;

@Repository
public class LineDaoImpl extends AbstractDAO<Line> implements LineDao{

	/**
	 * 
	 */
	private static final long serialVersionUID = 5764860459245034742L;

	public LineDaoImpl() {
		super(Line.class);
		// TODO Auto-generated constructor stub
	}

}
