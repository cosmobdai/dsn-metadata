package io.github.cosmobdai.bdp.dsn.metadata.model;

import io.swagger.annotations.ApiModelProperty;

public class MetadataExport {
    @ApiModelProperty(value = "id")
    private Integer id;

    @ApiModelProperty(value = "文件路径")
    private String filePath;

    @ApiModelProperty(value = "是否为本地存储")
    private Boolean local;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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
