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
package pro.parseq.solvent.datasources;

import org.springframework.hateoas.core.Relation;

@Relation(collectionRelation = "dataSourceTypes")
public enum DataSourceType {

	CHROMOSOME("chromosome"),
	REFERENCE("reference"),
	BASIC_BED("basic_bed"),
	VCF("vcf");
	// etc.

	private String value;

	private DataSourceType(String value) {
		this.value = value;
	}

	@Override
	public String toString() {
		return value;
	}

	public static final DataSourceType getEnum(String value) {

		for (DataSourceType enumValue: values()) {
			if (enumValue.value.toLowerCase().equals(value.toLowerCase())) {
				return enumValue;
			}
		}

		throw new IllegalArgumentException(
				String.format("Bad data source type: Unknown value [%s]", value));
	}
}
