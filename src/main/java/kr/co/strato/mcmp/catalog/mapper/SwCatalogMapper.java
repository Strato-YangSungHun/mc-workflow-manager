package kr.co.strato.mcmp.catalog.mapper;

import kr.co.strato.mcmp.catalog.model.SwCatalog;
import kr.co.strato.mcmp.catalog.model.SwCatalogDetail;
import kr.co.strato.mcmp.jenkins.pipeline.model.Pipeline;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface SwCatalogMapper {

    public Integer selectLastInsertId();

    public List<SwCatalogDetail> selectSwCatalogList(String title);

    public SwCatalogDetail selectSwCatalogDetail(Integer scIdx);

    public boolean insertSwCatalog(SwCatalogDetail swCatalogDetail);

    public boolean deleteSwCatalog(Integer scIdx);

    public boolean updateSwCatalog(SwCatalogDetail swCatalogDetail);


    public List<SwCatalog> selectRelationSwCatalogList(Integer scIdx);

    public List<Pipeline> selectRelationWorkflowList(Integer scIdx);


    public boolean insertSwCatalogRelationCatalog(Integer scIdx, Integer refIdx);

    public boolean deleteSwCatalogRelationCatalog(Integer scIdx, Integer refIdx);

    public boolean insertSwCatalogRelationWorkflow(Integer scIdx, Integer refIdx);

    public boolean deleteSwCatalogRelationWorkflow(Integer scIdx, Integer refIdx);


}
