package com.eassignment.web.util;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

import org.springframework.core.io.ClassPathResource;

public class ImageUtil {

	public static byte[] getDefaultImage() {
		File file;
		byte[] defaultAvatarImage = null;
		try {
			file = new ClassPathResource("static/assets/images/avatar-big.png").getFile();
			defaultAvatarImage = Files.readAllBytes(file.toPath());
		} catch (IOException e) {
			e.printStackTrace();
		}
		return defaultAvatarImage;
	}

}
