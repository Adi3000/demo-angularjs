package net.whichones.common.sheet;

import net.whichones.common.lines.data.Line;
import net.whichones.common.sheet.data.Sheet;

public interface SheetsService {
	/**
	 * Create a new sheet and init its token id
	 * @param sheet
	 * @return sheet with token id
	 */
	public Sheet createSheet(Sheet sheet);
	/**
	 * Create a new sheet and init its token id
	 * @param sheet
	 * @return sheet with token id
	 */
	public Sheet getSheetByToken(String token);
	/**
	 * Create a new sheet and init its token id
	 * @param sheet
	 * @return sheet with token id
	 */
	public Line addLine(Line line, Integer sheetId);
	
}
