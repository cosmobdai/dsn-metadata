package io.github.cosmobdai.bdp.dsn.metadata.model;

import io.swagger.annotations.ApiModelProperty;
import org.springframework.web.multipart.MultipartFile;

public class MetadataImport {
    @ApiModelProperty(value = "�洢·��")
    private String path;

    @ApiModelProperty(value = "�ļ�")
    private MultipartFile file;

    @ApiModelProperty(value = "�������ļ�")
    private byte[] fileByte;

    @ApiModelProperty(value = "�ļ�·��")
    private String filePath;

    @ApiModelProperty(value = "�Ƿ�Ϊ���ش洢")
    private Boolean local;

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public MultipartFile getFile() {
        return file;
    }

    public void setFile(MultipartFile file) {
        this.file = file;
    }

    public byte[] getFileByte() {
        return fileByte;
    }

    public void setFileByte(byte[] fileByte) {
        this.fileByte = fileByte;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public Boolean getLocal() {
        return local;
    }

    public void setLocal(Boolean local) {
        this.local = local;
    }
}
