package com.shope.admin;

import java.nio.file.Path;
import java.nio.file.Paths;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

//웹에 이미지가 들어나게 하는 부분
@Configuration
public class MvcConfig implements WebMvcConfigurer {
   
   @Override
   public void addResourceHandlers(ResourceHandlerRegistry registry) {
      String dirName = "user-photos";
      Path userPhotosDir = Paths.get(dirName);
      
      String userPhotosPath = userPhotosDir.toFile().getAbsolutePath();
      
      registry.addResourceHandler("/"+dirName+"/**").addResourceLocations("file:/"+userPhotosPath+"/");
      
      /*----------------------------------------------------------------------------------------*/
      
      String categoryImagesDirName = "../category-images";
      Path categoryImagesDir = Paths.get(categoryImagesDirName);
      
      String categoryImagesPath = categoryImagesDir.toFile().getAbsolutePath();

      registry.addResourceHandler("/category-images/**").addResourceLocations("file:/"+categoryImagesPath+"/");
        
   }
}