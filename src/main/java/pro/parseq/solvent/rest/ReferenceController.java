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
package pro.parseq.solvent.rest;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Resource;
import org.springframework.hateoas.Resources;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import pro.parseq.solvent.datasources.MasterDataSource;
import pro.parseq.solvent.entities.Contig;
import pro.parseq.solvent.entities.ReferenceGenome;
import pro.parseq.solvent.utils.HateoasUtils;

@RestController
@RequestMapping("/references")
public class ReferenceController {

	@Autowired
	private MasterDataSource masterDataSource;

	@GetMapping
	public Resources<Resource<ReferenceGenome>> getReferenceGenomes() {
		return HateoasUtils.referenceGenomeResources(masterDataSource.getReferenceService().getReferenceGenomes());
	}

	@RequestMapping("/{referenceGenome:.+}")
	public Resources<Contig> getReferenceGenome(
			@PathVariable ReferenceGenome referenceGenome) {

		return HateoasUtils.contigResources(referenceGenome,
				masterDataSource.getReferenceService().getContigs(referenceGenome.getId()));
	}
}
