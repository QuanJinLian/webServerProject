package webServer;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import org.apache.commons.codec.binary.Base64;

import constant.Constants;

public class UploadImage {
	 public static void convertStringtoImage(String encodedImageStr, String fileName) {
		 
		  try {
		   // Base64解码图片
		   byte[] imageByteArray = Base64.decodeBase64(encodedImageStr);
		 
		   //
		   FileOutputStream imageOutFile = new FileOutputStream(Constants.UPLOAD_ADDRESS + fileName + Constants.UPLOAD_TYPE);
		   imageOutFile.write(imageByteArray);
		 
		   imageOutFile.close();
		 
		   System.out.println("Image Successfully Stored");
		  } catch (FileNotFoundException fnfe) {
		   System.out.println("Image Path not found" + fnfe);
		  } catch (IOException ioe) {
		   System.out.println("Exception while converting the Image " + ioe);
		  }
		 }
}
