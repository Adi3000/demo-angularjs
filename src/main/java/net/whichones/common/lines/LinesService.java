package net.whichones.common.lines;

import net.whichones.common.lines.data.Line;

public interface LinesService {

	Line getLine(Integer id);
	Line saveLine(Line line);
	Boolean deleteLine(Integer id);
}
