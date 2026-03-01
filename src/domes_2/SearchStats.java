package domes_2;

public class SearchStats {

	private long depthSumForSuccessfulSearch = 0L;

	private long depthSumForFailedSearch = 0L;

	private long successCount = 0L;

	private long failureCount = 0L;

	long t0, t1;

	public SearchStats() {
	}

	public void recordDepthForSuccessfulSearch(long d) {
		depthSumForSuccessfulSearch += d;
		successCount++;
	}

	public void recordDepthForFailedSearch(long d) {
		depthSumForFailedSearch += d;
		failureCount++;
	}

	public double avgDepthForSuccessfulSearch() {
		return ((double) depthSumForSuccessfulSearch) / successCount;
	}

	public double avgDepthForFailedSearch() {
		return ((double) depthSumForFailedSearch) / failureCount;
	}

	public long getFailureCount() {
		return failureCount;
	}

	public long getSuccessCount() {
		return successCount;
	}

	public void setStartTime(long t0) {
		this.t0 = t0;
	}

	public void setEndTime(long t1) {
		this.t1 = t1;
	}

	public long getTimeElapsed() {
		return t1 - t0;
	}

}
