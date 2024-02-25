/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.uniquedevelober;

/**
 *
 * @author mohamed
 */
public class UserFile {
     String fileType;
     String filename;
     String uploadDate;

    public UserFile(String fileType, String filename, String uploadDate) {
        this.fileType = fileType;
        this.filename = filename;
        this.uploadDate = uploadDate;
    }

    public String getFileType() {
        return fileType;
    }

    public String getFilename() {
        return filename;
    }

    public String getUploadDate() {
        return uploadDate;
    } 
}
