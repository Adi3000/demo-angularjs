package net.whichones.common.sheet.dao;

import net.whichones.common.sheet.data.Sheet;

import com.adi3000.common.database.hibernate.session.DAO;

public interface SheetDao extends DAO<Sheet> {
	
	public boolean isTokenUsed(String token);
	public Sheet getSheetByToken(String token);
}
