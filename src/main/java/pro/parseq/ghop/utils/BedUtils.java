package pro.parseq.ghop.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import pro.parseq.ghop.datasources.BandBuilder;
import pro.parseq.ghop.entities.BedBand;
import pro.parseq.ghop.exceptions.IllegalBedFileDataLineException;

public final class BedUtils {

	private static final String DATA_LINE_DELIMITER = "\t";
	private static final String DATA_LINE_PATTERN = "\\w+(\\t\\d+){2}(\\t[\\S\\s][^\\n]+)*";
	private static final Pattern dataLinePattern = Pattern.compile(DATA_LINE_PATTERN);

	public static final Predicate<String> isDataLine = new Predicate<String>() {

		@Override
		public boolean test(String line) {
			return dataLinePattern.matcher(line).matches();
		}
	};

	public static final Region parseRegion(String line) {

		if (!isDataLine.test(line)) {
			throw new IllegalBedFileDataLineException(line);
		}

		String[] fields = line.split(DATA_LINE_DELIMITER);
		String chrom = fields[0];
		long chromStart = Long.parseLong(fields[1]);
		long chromEnd = Long.parseLong(fields[2]);
		List<String> opts = new ArrayList<>();
		for (int i = 3; i < fields.length; ++i) {
			opts.add(fields[i]);
		}

		return new Region(chrom, chromStart, chromEnd, opts);
	}
	
	public static List<BedBand> getBands(String referenceGenomeName, InputStream bed,
			BandBuilder<BedBand> bandBuilder) {

		try (BufferedReader reader = new BufferedReader(new InputStreamReader(bed))) {

			return reader.lines().filter(BedUtils.isDataLine)
					.map(BedUtils::parseRegion)
					.map(bandBuilder::build)
					.collect(Collectors.toList());
		} catch (IOException e) {
			throw new RuntimeException("I/O exception while BED file reading: "
					+ e.getMessage());
		}
	}

	/**
	 * Represents any valid (tab-delimited) BED file data entry
	 * 
	 * @author Alexander Afanasyev <a href="mailto:aafanasyev@parseq.pro">aafanasyev@parseq.pro</a>
	 */
	public static final class Region {

		// Mandatory BED fields
		private final String chrom;
		private final long chromStart;	// 0-based inclusive
		private final long chromEnd;	// 0-based exclusive

		// Holds all other specified optional (non-coordinate) field values
		private final List<String> opts;

		public Region(String chrom, long chromStart, long chromEnd, List<String> opts) {

			this.chrom = chrom;
			this.chromStart = chromStart;
			this.chromEnd = chromEnd;
			this.opts = opts;
		}

		public String getChrom() {
			return chrom;
		}

		public long getChromStart() {
			return chromStart;
		}

		public long getChromEnd() {
			return chromEnd;
		}

		public List<String> getOpts() {
			return opts;
		}

		@Override
		public String toString() {

			StringBuilder sb = new StringBuilder(chrom)
					.append(DATA_LINE_DELIMITER).append(chromStart)
					.append(DATA_LINE_DELIMITER).append(chromEnd);
			if (opts.size() > 0) {
				sb.append(DATA_LINE_DELIMITER).append(opts.stream()
						.collect(Collectors.joining(DATA_LINE_DELIMITER)));
			}

			return sb.toString();
		}
	}
}
