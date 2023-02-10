import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.cosmosource.bdai.bdp.base.response.BaseApiResponse;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import io.github.cosmobdai.bdp.dsn.metadata.DsnMetadata;
import io.github.cosmobdai.bdp.dsn.metadata.model.FileExportResponse;
import io.github.cosmobdai.bdp.dsn.metadata.model.MetadataExport;
import io.github.cosmobdai.bdp.dsn.metadata.model.MetadataExportVo;
import io.github.cosmobdai.bdp.dsn.metadata.model.MetadataImport;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

import org.junit.Test;
import org.springframework.util.LinkedMultiValueMap;

public class MetadataTest {

 /*   @Test
    public void importFile() throws IOException {
        DsnMetadata dsnMetadata = new DsnMetadata("192.168.0.149:2345");
        MetadataImport metadataImport = new MetadataImport();
        metadataImport.setPath("db:/lixiao88");
        metadataImport.setLocal(false);
        metadataImport.setFilePath("/tmp/file");
        BaseApiResponse response = dsnMetadata.importFile(metadataImport);
//        System.out.println(response.getData().toString());
    }

    @Test
    public void exportFile(){
        DsnMetadata dsnMetadata = new DsnMetadata("192.168.0.149:2345");
        MetadataExport metadataExport = new MetadataExport();
        metadataExport.setId(1);
        metadataExport.setFilePath("/tmp/export1");
        metadataExport.setLocal(false);
        dsnMetadata.exportFile(metadataExport);
    }

    @Test
    public void deleteFile(){
        DsnMetadata dsnMetadata = new DsnMetadata("192.168.0.149:2345");
        dsnMetadata.deleteFile(2);
    }

    @Test
    public void exportHdfsFile(){
        DsnMetadata dsnMetadata = new DsnMetadata("192.168.0.149:2345");
        MetadataExport metadataExport = new MetadataExport();
        metadataExport.setId(3);
        FileExportResponse response = dsnMetadata.exportFile(metadataExport);
        String s = new String(response.getData().getFileByte(), StandardCharsets.UTF_8);
        System.out.println(s);
    }*/
}
