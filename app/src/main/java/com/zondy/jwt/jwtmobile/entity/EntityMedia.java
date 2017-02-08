package com.zondy.jwt.jwtmobile.entity;

import java.io.Serializable;


public class EntityMedia implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	String filename;// 文件名 带后缀
	String type;// 文件类型（video,audio,image）
	String fileUrl;// 文件路径
	String imgUri;//文件对应的图片uri，
	String imgTypeUri;//文件类型对应的图片uri;
	public String getFilename() {
		return filename;
	}
	public void setFilename(String filename) {
		this.filename = filename;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getFileUrl() {
		return fileUrl;
	}
	public void setFileUrl(String fileUrl) {
		this.fileUrl = fileUrl;
	}
	public String getImgUri() {
		if(imgUri.startsWith("http://") || imgUri.startsWith("file://")){
			return imgUri;
		}
		return "file://"+imgUri;
	}
	public void setImgUri(String imgUri) {
		this.imgUri = imgUri;
	}
//	public String getImgTypeUri() {
//		String uri = null;
//		if("audio".equals(type)){
//			uri= Scheme.ASSETS.wrap("ic_audio.png");
//		}
//		if("video".equals(type)){
//			uri= Scheme.ASSETS.wrap("ic_video.png");
//		}
//		if("image".equals(type)){
//			uri= Scheme.ASSETS.wrap("ic_image.png");
//		}
//		return uri;
//	}
	public void setImgTypeUri(String imgTypeUri) {
		this.imgTypeUri = imgTypeUri;
	}

	
}
	