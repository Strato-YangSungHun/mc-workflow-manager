package kr.co.strato.oss.service;

import kr.co.strato.oss.dto.OssDto;
import kr.co.strato.oss.dto.OssTypeDto;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public interface OssTypeService {
    List<OssTypeDto> getAllOssTypeList();
    Long registOssType(OssTypeDto ossTypeDto);
    Long updateOssType(OssTypeDto ossTypeDto);
    @Transactional
    void deleteOssType(Long ossIdx);
    OssTypeDto detailOssType(Long ossIdx);
}