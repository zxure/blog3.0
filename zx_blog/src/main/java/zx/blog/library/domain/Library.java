package zx.blog.library.domain;

public class Library {
	private int libraryId;					//id
	private String title;			//标题
	private String itemName;		//栏目名称
	private String fileName; 		//文件名
	private String mappingShortUrl; //映射路径名称   -> 返回  fileNmae.后缀名称  给前端
	private int uploadTime;	  		//上传时间
	public int getLibraryId() {
		return libraryId;
	}
	public void setLibraryId(int libraryId) {
		this.libraryId = libraryId;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getItemName() {
		return itemName;
	}
	public void setItemName(String itemName) {
		this.itemName = itemName;
	}
	public String getFileName() {
		return fileName;
	}
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	public String getMappingShortUrl() {
		return mappingShortUrl;
	}
	public void setMappingShortUrl(String mappingShortUrl) {
		this.mappingShortUrl = mappingShortUrl;
	}
	public int getUploadTime() {
		return uploadTime;
	}
	public void setUploadTime(int uploadTime) {
		this.uploadTime = uploadTime;
	}
}
