package com.xgw.slideshow;

import java.io.Serializable;

public class ImageText  implements Serializable{
    /**
	 * 
	 */
	private static final long serialVersionUID = 3633542672188240300L;
	public static final String key = "image_text";
	public int id = -1;
    public int imageId;
    public String imagePath;
    public String imageText;
}
