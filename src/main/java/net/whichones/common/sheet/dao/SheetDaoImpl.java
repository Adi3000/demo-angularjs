package net.whichones.common.sheet.dao;

import net.whichones.common.sheet.data.Sheet;

import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import com.adi3000.common.database.hibernate.session.AbstractDAO;

@Repository
public class SheetDaoImpl extends AbstractDAO<Sheet> implements SheetDao {
	public SheetDaoImpl() {
		super(Sheet.class);
		// TODO Auto-generated constructor stub
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = 2393887690506053788L;

	@Override
	public boolean isTokenUsed(String token){
		Integer totalResult = 
				((Number)createCriteria(Sheet.class)
						.add(Restrictions.eq("token", token))
						.setProjection(Projections.rowCount())
						.uniqueResult()).intValue();
		return totalResult != null && totalResult > 0;
	}

	public Sheet getSheetByToken(String token){
		return (Sheet) getSession()
				.byNaturalId(Sheet.class)
				.using("token", token)
				.load();
	}
}
