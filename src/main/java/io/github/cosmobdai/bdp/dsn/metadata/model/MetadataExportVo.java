package io.github.cosmobdai.bdp.dsn.metadata.model;

import io.swagger.annotations.ApiModelProperty;
import org.springframework.http.ResponseEntity;

public class MetadataExportVo {
        @ApiModelProperty(value = "����")
        private String type;

        @ApiModelProperty(value = "�ļ�")
        private ResponseEntity<byte[]> file;

        @ApiModelProperty(value = "�������ļ�")
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
