package io.github.cosmobdai.bdp.dsn.metadata.model;

import io.swagger.annotations.ApiModelProperty;
import org.springframework.http.ResponseEntity;

public class MetadataExportVo {
        @ApiModelProperty(value = "类型")
        private String type;

        @ApiModelProperty(value = "文件")
        private ResponseEntity<byte[]> file;

        @ApiModelProperty(value = "二进制文件")
        private byte[] fileByte;

        public String getType() {
                return type;
        }

        public void setType(String type) {
                this.type = type;
        }

        public ResponseEntity<byte[]> getFile() {
                return file;
        }

        public void setFile(ResponseEntity<byte[]> file) {
                this.file = file;
        }

        public byte[] getFileByte() {
                return fileByte;
        }

        public void setFileByte(byte[] fileByte) {
                this.fileByte = fileByte;
        }
}
