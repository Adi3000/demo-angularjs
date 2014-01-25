package net.whichones.common.sheet;

import java.util.HashMap;
import java.util.Map;

import javax.inject.Inject;

import net.whichones.common.lines.LinesService;
import net.whichones.common.lines.dao.GroupDao;
import net.whichones.common.lines.dao.SectionDao;
import net.whichones.common.lines.data.Group;
import net.whichones.common.lines.data.Line;
import net.whichones.common.lines.data.Section;
import net.whichones.common.sheet.dao.SheetDao;
import net.whichones.common.sheet.data.Sheet;

import org.springframework.stereotype.Service;

import com.adi3000.common.database.hibernate.DatabaseOperation;
import com.adi3000.common.database.spring.TransactionalReadOnly;
import com.adi3000.common.database.spring.TransactionalUpdate;
import com.adi3000.common.util.security.Security;

@Service
public class SheetsServiceImpl implements SheetsService{

	private static final int GENERATE_TOKEN_MAX_TRY = 15;
	private SheetDao sheetDao;
	private LinesService lineService;
	private SectionDao sectionDao;
	private GroupDao groupDao;
	/**
	 * @param sectionDao the sectionDao to set
	 */
	@Inject
	public void setSectionDao(SectionDao sectionDao) {
		this.sectionDao = sectionDao;
	}
	/**
	 * @param groupDao the groupDao to set
	 */
	@Inject
	public void setGroupDao(GroupDao groupDao) {
		this.groupDao = groupDao;
	}
	/**
	 * @param lineService the lineService to set
	 */
	@Inject
	public void setLineService(LinesService lineService) {
		this.lineService = lineService;
	}
	/**
	 * @param sheetDao the sheetDao to set
	 */
	@Inject
	public void setSheetDao(SheetDao sheetDao) {
		this.sheetDao = sheetDao;
	}
	@Override
	@TransactionalUpdate
	public Line addLine(Line line, Integer sheetId) {
		lineService.saveLine(line);
		return line;
	}
	@TransactionalUpdate
	public Sheet createSheet(Sheet sheet){
		String token = null;
		boolean tokenChoosen = false;
		int i = 0;
		do{
			token = Security.generateTokenID().substring(0, 10);
			tokenChoosen = !sheetDao.isTokenUsed(token);
			i++;
		}while(!tokenChoosen && i < GENERATE_TOKEN_MAX_TRY);
		if(tokenChoosen){
			sheet.setToken(token);
			sheet.setId(null);
			if(sheet.getLines() != null){
				Map<Integer, Section> sectionMapCache = new HashMap<>();
				Map<Integer, Group> groupMapCache = new HashMap<>();
				Section section = null;
				Group group = null;
				for(Line line : sheet.getLines()){
					line.setId(null);
					line.setDatabaseOperation(DatabaseOperation.INSERT);
					line.setSheet(sheet);
					group = line.getGroup();
					if(group != null){
						if(!groupMapCache.containsKey(group.getIndex())){
							group.setId(null);
							group.setDatabaseOperation(DatabaseOperation.INSERT);
							groupMapCache.put(group.getIndex(), group);
						}else{
							group = groupMapCache.get(group.getIndex());
						}
						line.setGroup(group);
					}
					section = line.getSection();
					if(section != null){
						if(!sectionMapCache.containsKey(section.getIndex())){
							section.setId(null);
							section.setDatabaseOperation(DatabaseOperation.INSERT);
							sectionMapCache.put(section.getIndex(), section);
						}else{
							section = sectionMapCache.get(section.getIndex());
						}
						line.setSection(section);
					}
				}
				for(Section s : sectionMapCache.values()){
					sectionDao.modify(s);
				}
				for(Group g : groupMapCache.values()){
					groupDao.modify(g);
				}
			}
			sheet.setDatabaseOperation(DatabaseOperation.INSERT);
			return sheetDao.modifyDataObject(sheet);
		}else{
			return null;
		}
	}
	@TransactionalReadOnly
	public Sheet getSheetByToken(String token){
		return sheetDao.getSheetByToken(token);
	}
	


}
