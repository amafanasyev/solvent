/*******************************************************************************
 *     Copyright 2016-2017 the original author or authors.
 *
 *     This file is part of CONC.
 *
 *     CONC. is free software: you can redistribute it and/or modify
 *     it under the terms of the GNU Affero General Public License as published by
 *     the Free Software Foundation, either version 3 of the License, or
 *     (at your option) any later version.
 *
 *     CONC. is distributed in the hope that it will be useful,
 *     but WITHOUT ANY WARRANTY; without even the implied warranty of
 *     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 *     GNU Affero General Public License for more details.
 *
 *     You should have received a copy of the GNU Affero General Public License
 *     along with CONC. If not, see <http://www.gnu.org/licenses/>.
 *******************************************************************************/
package pro.parseq.solvent.exceptions;

public class IllegalBedFileDataLineException extends RuntimeException {

	private static final long serialVersionUID = -9084953325445256636L;

	private final String dataLine;

	public IllegalBedFileDataLineException(String dataLine) {

		super(String.format("Malformed BED file dataline: %s", dataLine));

		this.dataLine = dataLine;
	}

	public String getDataLine() {
		return dataLine;
	}
}
