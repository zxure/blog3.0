package zx.blog.entity;

import java.io.Serializable;

public abstract class TableRecordVersion implements Serializable{
	private static final long serialVersionUID = 7722409467311815297L;
	
	public static final String SEPERATOR = "_";

	private int version = 0;
	
	private boolean updateFlag = false;
	
	private boolean readWriteFlag = false;
	
	/**
	 * 获取缓存中的唯一的key
	 * @return
	 */
	public abstract String obtainCacheKey();

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

