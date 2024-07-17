package kr.co.strato.oss.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import kr.co.strato.api.response.ResponseCode;
import kr.co.strato.api.response.ResponseWrapper;
import kr.co.strato.oss.dto.OssDto;
import kr.co.strato.oss.service.OssService;
import kr.co.strato.oss.service.OssServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@Tag(name = "oss", description = "oss 설정")
@RequestMapping("/oss")
@RestController
public class OssController {

    @Autowired
    private OssService ossService;

    @Operation(summary = "OSS 목록 조회", description = "oss 모든 목록조회" )
    @GetMapping("/list")
    public ResponseWrapper<List<OssDto>> getOssList() {
        return new ResponseWrapper<>(ossService.getAllOssList());
    }

    @Operation(summary = "OSS 정보 중복 체크(oss명, url, username)", description = "true : 중복 / false : 중복 아님")
    @GetMapping("/duplicate")
    public ResponseWrapper<Boolean> isOssInfoDuplicated(@RequestParam String ossName, @RequestParam String ossUrl, @RequestParam String ossUsername) {
        if ( StringUtils.isBlank(ossName) ) {
            return new ResponseWrapper<>(ResponseCode.BAD_REQUEST, "ossName");
        }
        else if ( StringUtils.isBlank(ossUrl) ) {
            return new ResponseWrapper<>(ResponseCode.BAD_REQUEST, "ossUrl");
        }
        else if ( StringUtils.isBlank(ossUsername) ) {
            return new ResponseWrapper<>(ResponseCode.BAD_REQUEST, "ossUsername");
        }
        OssDto ossDto = OssDto.setOssAttributesDuplicate(ossName, ossUrl, ossUsername);
        return new ResponseWrapper<>(ossService.isOssInfoDuplicated(ossDto));
    }

    @Operation(summary = "등록", description = "oss 등록")
    @PostMapping("/regist")
    public ResponseWrapper<Long> createOss(@RequestBody OssDto ossDto) {
        return new ResponseWrapper<>(ossService.createOss(ossDto));
    }

    @Operation(summary = "수정", description = "oss 수정")
    @PutMapping("/modify/{ossIdx}")
    public ResponseWrapper<Long> updateOss(@PathVariable Long ossIdx, @RequestBody OssDto ossDto) {
        if ( ossIdx == 0 || ossDto.getOssIdx() == 0 ) {
            return new ResponseWrapper<>(ossService.updateOss(ossDto));
        }
        return new ResponseWrapper<>(null);
    }

    @Operation(summary = "삭제", description = "oss 삭제")
    @DeleteMapping("/delete/{ossId}")
    public ResponseWrapper<Void> deleteOss(@PathVariable Long ossId) {
        ossService.deleteOss(ossId);
        return new ResponseWrapper<>();
    }

    @Operation(summary = "연결확인", description = "oss 연결 확인")
    @PostMapping("/connection/check")
    public ResponseWrapper<Boolean> checkConnection(@RequestBody OssDto ossDto) {
        return new ResponseWrapper<>(ossService.checkConnection(ossDto));
    }
}