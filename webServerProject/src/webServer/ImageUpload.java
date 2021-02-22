package webServer;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServlet;

import org.apache.commons.codec.binary.Base64;

import com.sun.media.sound.ModelAbstractChannelMixer;

import constant.Constants;
import controls.Controller;
import dto.Image;
import prepare.Component;
import prepare.DataBinding;

@Component("/webServer/imageUpload.do")
public class ImageUpload implements Controller,DataBinding  {
	String savePath = "upload";  //여기 바꿔주면 업로드 경로가 바뀜
	ServletContext context ;



	@Override
	public Object[] getDataBinders() {

		return new Object[] {
				"imageInfo", Image.class,
		};
	}
// result  1000 = "이미지 업로드 성공"
		//	1001 = "이미지 없음 imgEncodeStr null값"
		//	1002 = "이미지 업로드 경로를 못찾습니다 (서버의 주소 문제)"
		//	1003 = "IOException 났음 에러는 서버 콘솔에서 봐"
	@Override
	public String execute(Map<String, Object> model) throws Exception {
		Image image = (Image)model.get("imageInfo");
		context = (ServletContext)model.get("context");
		String imgEncodedStr = image.getImage();
		String fileName = image.getFilename();
//		System.out.println(context.toString());
//		System.out.println("imgEncodedStr: "+ imgEncodedStr);
//		System.out.println("Filename: "+ fileName);
		int result = 0;
		if(imgEncodedStr != null) {
			result = convertStringtoImage(imgEncodedStr, fileName);
		}else {
			result = 1001;
		}
		

		model.put("result", result);
		return "/Jsp/imageUploadResult.jsp";
	}

	 public int convertStringtoImage(String encodedImageStr, String fileName) {
		 int result = 0;
		  try {
		   // Base64解码图片
		   byte[] imageByteArray = Base64.decodeBase64(encodedImageStr);
		   
			String uploadFilePath = context.getRealPath(savePath);
		   //
//		   FileOutputStream imageOutFile = new FileOutputStream(Constants.UPLOAD_ADDRESS + fileName + Constants.UPLOAD_TYPE);
//			System.out.println(uploadFilePath);
		    FileOutputStream imageOutFile = new FileOutputStream(uploadFilePath+"/" + fileName + Constants.UPLOAD_TYPE);
		   imageOutFile.write(imageByteArray);
		 
		   imageOutFile.close();
		   System.out.println("Image Successfully Stored");
		   result = 1000;
		   return result;
		  } catch (FileNotFoundException fnfe) {
			   System.out.println("Image Path not found" + fnfe);
			   result = 1001;
			   return result;
		  } catch (IOException ioe) {
			   System.out.println("Exception while converting the Image " + ioe);
			   result = 1002;
			   return result;
		  }
		 }
}
