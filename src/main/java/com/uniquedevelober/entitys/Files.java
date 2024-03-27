/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.uniquedevelober.entitys;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 *
 * @author mahjouba
 */
@Entity
@Table(catalog = "hellbase2", schema = "")
@NamedQueries({
    @NamedQuery(name = "Files.findAll", query = "SELECT f FROM Files f"),
    @NamedQuery(name = "Files.findByFileId", query = "SELECT f FROM Files f WHERE f.fileId = :fileId"),
    @NamedQuery(name = "Files.findByFileName", query = "SELECT f FROM Files f WHERE f.fileName = :fileName"),
    @NamedQuery(name = "Files.findByUploadData", query = "SELECT f FROM Files f WHERE f.uploadData = :uploadData")})
public class Files implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "file_id", nullable = false)
    private Integer fileId;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 255)
    @Column(name = "file_name", nullable = false, length = 255)
    private String fileName;
    @Basic(optional = false)
    @NotNull
    @Lob
    @Column(name = "file_content", nullable = false)
    private byte[] fileContent;
    @Basic(optional = false)
    @NotNull
    @Column(name = "upload_data", nullable = false)
    @Temporal(TemporalType.DATE)
    private Date uploadData;
    @JoinColumn(name = "owner_user_id", referencedColumnName = "userid")
    @ManyToOne
    private Users ownerUserId;

    public Files() {
    }

    public Files(Integer fileId) {
        this.fileId = fileId;
    }

    public Files(Integer fileId, String fileName, byte[] fileContent, Date uploadData) {
        this.fileId = fileId;
        this.fileName = fileName;
        this.fileContent = fileContent;
        this.uploadData = uploadData;
    }

    public Integer getFileId() {
        return fileId;
    }

    public void setFileId(Integer fileId) {
        this.fileId = fileId;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public byte[] getFileContent() {
        return fileContent;
    }

    public void setFileContent(byte[] fileContent) {
        this.fileContent = fileContent;
    }

    public Date getUploadData() {
        return uploadData;
    }

    public void setUploadData(Date uploadData) {
        this.uploadData = uploadData;
    }

    public Users getOwnerUserId() {
        return ownerUserId;
    }

    public void setOwnerUserId(Users ownerUserId) {
        this.ownerUserId = ownerUserId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (fileId != null ? fileId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Files)) {
            return false;
        }
        Files other = (Files) object;
        if ((this.fileId == null && other.fileId != null) || (this.fileId != null && !this.fileId.equals(other.fileId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.uniquedevelober.Files[ fileId=" + fileId + " ]";
    }
    
}
