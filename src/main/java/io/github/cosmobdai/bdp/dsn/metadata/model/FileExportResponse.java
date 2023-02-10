package io.github.cosmobdai.bdp.dsn.metadata.model;

import io.swagger.annotations.ApiModelProperty;

public class FileExportResponse {
    private static final int SUCCESS_CODE = 0;
    private static final String SUCCESS_MESSAGE = "成功";
    @ApiModelProperty(
            value = "响应码",
            name = "code",
            required = true,
            example = "0"
    )
    private int code;
    @ApiModelProperty(
            value = "响应消息",
            name = "msg",
            required = true,
            example = "成功"
    )
    private String msg;
    @ApiModelProperty(
            value = "响应数据",
            name = "data"
    )
    private MetadataExportVo data;

    public FileExportResponse(int code, String msg, MetadataExportVo data) {
        this.code = code;
        this.msg = msg;
        this.data = data;
    }

    public FileExportResponse(int code) {
        this.code = code;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public MetadataExportVo getData() {
        return data;
    }

    public void setData(MetadataExportVo data) {
        this.data = data;
    }
}
