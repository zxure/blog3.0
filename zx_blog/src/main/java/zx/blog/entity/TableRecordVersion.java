package zx.blog.entity;

public abstract class TableRecordVersion {
	private int version = 0;
	
	private boolean updateFlag = false;
	
	private boolean readWriteFlag = false;
	
	/**
	 * 对应的唯一 mapper 的名称
	 * @return
	 */
	public abstract String obtainMapperName();

	/**
	 * 获取唯一的key
	 * @return
	 */
	public abstract String obtainKey();

	public int getVersion() {
		return version;
	}

	public void setVersion(int version) {
		this.version = version;
	}

	public boolean isUpdateFlag() {
		return updateFlag;
	}

	public void setUpdateFlag(boolean updateFlag) {
		this.updateFlag = updateFlag;
	}

	public boolean isReadWriteFlag() {
		return readWriteFlag;
	}

	public void setReadWriteFlag(boolean readWriteFlag) {
		this.readWriteFlag = readWriteFlag;
	}

	@Override
	public String toString() {
		return "TableRecordVersion [version=" + version + ", updateFlag=" + updateFlag + ", readWriteFlag="
				+ readWriteFlag + "]";
	}
	
}

