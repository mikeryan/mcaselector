package net.querz.mcaselector.tiles.overlay;

import net.querz.mcaselector.io.mca.ChunkData;

import java.util.Arrays;

public abstract class OverlayParser implements Cloneable {

	private final OverlayType type;
	private boolean active;
	private Integer min;
	private Integer max;
	private String rawMin;
	private String rawMax;
	protected String[] multiValues = null;

	public OverlayParser(OverlayType type) {
		this.type = type;
	}

	public OverlayType getType() {
		return type;
	}

	public void setActive(boolean active) {
		this.active = active;
	}

	public boolean isActive() {
		return active;
	}

	public Integer min() {
		return min;
	}

	public Integer max() {
		return max;
	}

	public String minString() {
		return min == null ? "" : min + "";
	}

	public String maxString() {
		return max == null ? "" : max + "";
	}

	public boolean isValid() {
		return min != null && max != null && min < max;
	}

	public String getRawMin() {
		return rawMin;
	}

	public String getRawMax() {
		return rawMax;
	}

	public void setRawMin(String rawMin) {
		this.rawMin = rawMin;
	}

	public void setRawMax(String rawMax) {
		this.rawMax = rawMax;
	}

	protected boolean setMin(Integer min) {
		this.min = min;
		return isValid();
	}

	protected boolean setMax(Integer max) {
		this.max = max;
		return isValid();
	}

	public abstract int parseValue(ChunkData chunkData);

	public abstract String name();

	public abstract boolean setMin(String raw);

	public abstract boolean setMax(String raw);

	// can be overwritten to set additional data points for a single overlay
	public boolean setMultiValues(String raw) {
		return true;
	}

	// can be overwritten to supply additional data points for a single overlay
	public String[] multiValues() {
		return multiValues;
	}

	@Override
	public String toString() {
		return type + "{min=" + minString() + ", max=" + maxString() + ", active=" + active + ", valid=" + isValid() + "}";
	}

	@Override
	public boolean equals(Object other) {
		if (!(other instanceof OverlayParser)) {
			return false;
		}
		OverlayParser o = (OverlayParser) other;
				// type is equal
		return type == o.type
				// min are both null or equal
				&& (min == null && o.min == null || min != null && o.min != null && min.intValue() == o.min.intValue())
				// max are both null or equal
				&& (max == null && o.max == null || max != null && o.max != null && max.intValue() == o.max.intValue()
				// active is equal
				&& active == o.active
				// their multiValues are equal
				&& Arrays.equals(multiValues(), o.multiValues()));
	}

	@Override
	public OverlayParser clone() {
		OverlayParser clone = type.instance();
		clone.min = min;
		clone.max = max;
		clone.multiValues = multiValues == null ? null : Arrays.copyOf(multiValues, multiValues.length);
		clone.active = active;
		clone.rawMin = rawMin;
		clone.rawMax = rawMax;
		return clone;
	}
}
