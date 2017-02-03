package pro.parseq.ghop.utils;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.fail;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import pro.parseq.ghop.exceptions.UnknownContigException;
import pro.parseq.ghop.exceptions.UnknownReferenceGenomeException;

@RunWith(SpringRunner.class)
@SpringBootTest
public class GenomicCoordinateComparatorTest {

	private static final String TEST_REFERENCE = "TestReference";
	private static final String HUMAN_REFERENCE = "GRCh37.p13";
	private static final String UNKNOWN_REFERENCE = "TotallyUnknown";
	private static final String CHR1 = "chr1";
	private static final String CHR11 = "chr11";
	private static final String CHR2 = "chr2";
	private static final String CHRM = "chrM";
	private static final String SOMEBIGCONTIG = "somebigcontig";

	@Autowired
	private GenomicCoordinateComparator comparator;

	@Test
	public void testGenomicCoordinatesSorting() throws Exception {

		GenomicCoordinate somebigcontig_1 = new GenomicCoordinate(TEST_REFERENCE, SOMEBIGCONTIG, 1);
		GenomicCoordinate somebigcontig_3 = new GenomicCoordinate(TEST_REFERENCE, SOMEBIGCONTIG, 3);
		GenomicCoordinate somebigcontig_12 = new GenomicCoordinate(TEST_REFERENCE, SOMEBIGCONTIG, 12);
		GenomicCoordinate chr1_10 = new GenomicCoordinate(HUMAN_REFERENCE, CHR1, 10);
		GenomicCoordinate chr1_20 = new GenomicCoordinate(HUMAN_REFERENCE, CHR1, 20);
		GenomicCoordinate chr2_1 = new GenomicCoordinate(HUMAN_REFERENCE, CHR2, 1);
		GenomicCoordinate chr2_2 = new GenomicCoordinate(HUMAN_REFERENCE, CHR2, 2);
		GenomicCoordinate chr11_1 = new GenomicCoordinate(HUMAN_REFERENCE, CHR11, 1);
		GenomicCoordinate chrM_5 = new GenomicCoordinate(HUMAN_REFERENCE, CHRM, 5);
		GenomicCoordinate chrM_6 = new GenomicCoordinate(HUMAN_REFERENCE, CHRM, 6);
		GenomicCoordinate unknownReferenceCoord = new GenomicCoordinate(UNKNOWN_REFERENCE, CHR1, 0);
		GenomicCoordinate unknownContigCoord = new GenomicCoordinate(HUMAN_REFERENCE, SOMEBIGCONTIG, 0);

		List<GenomicCoordinate> coords = Arrays.asList(somebigcontig_3, chrM_6, chrM_5, chr11_1,
				somebigcontig_1, chr1_20, chr2_2, chr1_10, chrM_5,
				somebigcontig_12, chr1_10, chr2_1, somebigcontig_3);
		Collections.sort(coords, comparator);

		assertThat(coords).containsExactly(chr1_10, chr1_10, chr1_20, chr2_1,
				chr2_2, chr11_1, chrM_5, chrM_5, chrM_6, somebigcontig_1,
				somebigcontig_3, somebigcontig_3, somebigcontig_12);

		coords = Arrays.asList(unknownReferenceCoord, chr1_10, chrM_5);
		try {
			Collections.sort(coords, comparator);
			fail("Coordinate with unknown reference genome id should cause an exception!");
		} catch (UnknownReferenceGenomeException e) {}

		coords = Arrays.asList(chr1_10, chrM_5, unknownContigCoord);
		try {
			Collections.sort(coords, comparator);
			fail("Coordinate with unknown contig id should cause an exception!");
		} catch (UnknownContigException e) {}
	}
}
