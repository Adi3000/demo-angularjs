package net.whichones.common.lines;

import javax.inject.Inject;

import net.whichones.common.lines.dao.LineDao;
import net.whichones.common.lines.data.Line;
import net.whichones.common.sheet.data.Sheet;

import org.springframework.stereotype.Service;

import com.adi3000.common.database.hibernate.DatabaseOperation;
import com.adi3000.common.database.spring.TransactionalReadOnly;
import com.adi3000.common.database.spring.TransactionalUpdate;

@Service
public class LinesServiceImpl implements LinesService {

	private LineDao lineDao;
	/**
	 * @param lineDao the lineDao to set
	 */
	@Inject
	public void setLineDao(LineDao lineDao) {
		this.lineDao = lineDao;
	}

	public LinesServiceImpl() {
	}

	@Override
	@TransactionalReadOnly
	public Line getLine(Integer id) {
		return lineDao.get(id);
	}

	@Override
	@TransactionalUpdate
	public Line saveLine(Line line) {
		if(line.getSheet() == null && line.getSheetId().getId() != null){
			line.setSheet(line.getSheetId());
		}
		line.setDatabaseOperation(DatabaseOperation.INSERT_OR_UPDATE);
		lineDao.modify(line);
		return line;
	}

	@Override
	@TransactionalUpdate
	public Boolean deleteLine(Integer id) {
		
		Line line = lineDao.get(id);
		line.getSheet().getLines().remove(line);
		lineDao.deleteDataObject(line);
		return Boolean.TRUE;
	}

}
