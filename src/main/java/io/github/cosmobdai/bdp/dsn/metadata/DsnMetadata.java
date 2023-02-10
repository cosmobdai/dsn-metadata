package io.github.cosmobdai.bdp.dsn.metadata;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.cosmosource.bdai.bdp.base.response.BaseApiResponse;
import io.github.cosmobdai.bdp.dsn.metadata.model.FileExportResponse;
import io.github.cosmobdai.bdp.dsn.metadata.model.MetadataExport;
import io.github.cosmobdai.bdp.dsn.metadata.model.MetadataExportVo;
import io.github.cosmobdai.bdp.dsn.metadata.model.MetadataImport;
import io.github.cosmobdai.bdp.dsn.metadata.utils.RestTemplateUtil;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.*;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public class DsnMetadata {
    private String dsnUrl;
    private static final String HTTP = "http://";
    private static final String URL_TITLE = "/bdp/dsn/metadata/management/v1";
    private static final String IMPORT = "/import";
    private static final String AUDIT = "/audit";
    private static final String LIST = "/list";
    private static final String EXPORT = "/export";
    private static final String DELETE = "/delete";
    private static final String QUESTION_MARK = "?";
    private static final String SQL = "sql";
    private static final String ID = "id";
    private static final String SHIFT_13 = "=";

    public DsnMetadata(String dsnUrl){
        this.dsnUrl = dsnUrl;
    }
    public BaseApiResponse importFile(MetadataImport metadataImport) throws IOException {
        String querySqlUrl = HTTP + dsnUrl + URL_TITLE + IMPORT;
        MultipartFile multipartFile = metadataImport.getFile();
        ByteArrayResource fileAsResource = null;
        if(metadataImport.getFile() != null) {
            fileAsResource = new ByteArrayResource(multipartFile.getBytes()) {
                @Override
                public String getFilename() {
                    return multipartFile.getOriginalFilename();
                }

                @Override
                public long contentLength() {
                    return multipartFile.getSize();
                }
            };
        }
        MultiValueMap<String,Object> params = new LinkedMultiValueMap<>();
        params.add("path",metadataImport.getPath());
        params.add("file",fileAsResource);
        params.add("fileByte",metadataImport.getFileByte());
        params.add("filePath",metadataImport.getFilePath());
        params.add("local",metadataImport.getLocal());

        JSONObject result = RestTemplateUtil.exchange(querySqlUrl, HttpMethod.POST,params);
        BaseApiResponse queryResult = JSON.toJavaObject(result,BaseApiResponse.class);
        return queryResult;
    }
    public BaseApiResponse listAudit(){
        String querySqlUrl = HTTP + dsnUrl + URL_TITLE + AUDIT;
        JSONObject result = RestTemplateUtil.exchange(querySqlUrl, HttpMethod.POST);
        BaseApiResponse queryResult = JSON.toJavaObject(result,BaseApiResponse.class);
        return queryResult;
    }
    public BaseApiResponse listMetadata(){
        String listMetadataUrl = HTTP + dsnUrl + URL_TITLE + LIST;
        JSONObject result = RestTemplateUtil.exchange(listMetadataUrl, HttpMethod.POST);
        BaseApiResponse queryResult = JSON.toJavaObject(result,BaseApiResponse.class);
        return queryResult;
    }

    public FileExportResponse exportFile(MetadataExport metadataExport){
        String exportFileUrl = HTTP + dsnUrl + URL_TITLE + EXPORT;
        JSONObject reqBody = new JSONObject();
        reqBody.put("id",metadataExport.getId());
        reqBody.put("filePath",metadataExport.getFilePath());
        reqBody.put("local",metadataExport.getLocal());
        //JSONObject result = RestTemplateUtil.exchange(exportFileUrl, HttpMethod.POST,reqBody);
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<BaseApiResponse> fileExportResponse = restTemplate.postForEntity(exportFileUrl, reqBody,BaseApiResponse.class);
        JSONObject jsonObj = JSON.parseObject(JSON.toJSONString(fileExportResponse.getBody().getData()));
        MetadataExportVo metadataExportVo = new MetadataExportVo();
        metadataExportVo.setFileByte(jsonObj.getJSONObject("file").getBytes("body"));
        FileExportResponse fileExportResponse1 = new FileExportResponse(fileExportResponse.getBody().getCode(),fileExportResponse.getBody().getMsg(),metadataExportVo);

        return fileExportResponse1;
    }

    public BaseApiResponse deleteFile(Integer id){
        String deleteFileUrl = HTTP + dsnUrl + URL_TITLE + DELETE + QUESTION_MARK + ID + SHIFT_13 + id;
        JSONObject result = RestTemplateUtil.exchange(deleteFileUrl, HttpMethod.POST);
        BaseApiResponse queryResult = JSON.toJavaObject(result,BaseApiResponse.class);
        return queryResult;
    }
}
