package m.cmp.wfManager.jenkins.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import m.cmp.wfManager.jenkins.model.Pipeline;


@Mapper
public interface JenkinsPipelineMapper {

	// 파이프라인 목록 조회
	public List<Pipeline> selectJenkinsPipelineList(Pipeline pipeline);
	
	// 파이프라인 상세 조회
	public Pipeline selectJenkinsPipeline(int pipelineId);
	
	// 파이프라인 명 중복 체크
	public boolean isPipelineNameDuplicated(Pipeline pipeline);
	
	// 파이프라인 등록 
	public int insertJenkinsPipeline(Pipeline pipeline);
	
	// 파이프라인 수정 
	public int updateJenkinsPipeline(Pipeline pipeline);
	
	// 파이프라인 삭제
	public int deleteJenkinsPipeline(int pipelineId);
}
