package com.shope.admin;

import java.io.IOException;
import java.util.Base64;

import org.springframework.core.convert.converter.Converter;
import org.springframework.web.multipart.MultipartFile;

public class MultipartFileToBase64Converter implements Converter<MultipartFile, String> {

    @Override
    public String convert(MultipartFile source) {
        try {
            return Base64.getEncoder().encodeToString(source.getBytes());
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}