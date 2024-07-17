package kr.co.strato.oss.service;

import kr.co.strato.oss.dto.OssTypeDto;
import kr.co.strato.oss.repository.OssTypeRepository;
import kr.co.strato.tumblebug.dto.TumblebugDto;
import kr.co.strato.jenkins.model.JenkinsCredential;
import kr.co.strato.jenkins.service.JenkinsService;
import kr.co.strato.oss.dto.OssDto;
import kr.co.strato.oss.repository.OssRepository;
import kr.co.strato.tumblebug.service.TumblebugService;
import kr.co.strato.util.AES256Util;
import kr.co.strato.util.Base64Util;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.stream.Collectors;


@Log4j2
@Service
public class OssServiceImpl implements OssService {

	@Autowired
	private OssRepository ossRepository;

	@Autowired
	private OssTypeRepository ossTypeRepository;

	@Autowired
	private JenkinsService jenkinsService;

	@Autowired
	private TumblebugService tumblebugService;

	/**
	 * OSS 목록 조회
	 * @return List<OssDto> ossDtoList
	 */
	@Override
	public List<OssDto> getAllOssList() {
		List<OssDto> ossList = ossRepository.findAll()
				.stream()
				.map(OssDto::from)
				.collect(Collectors.toList());

		if ( !CollectionUtils.isEmpty(ossList) ) {
			ossList = ossList.stream()
					.map(ossDto -> OssDto.withModifiedEncriptPassword(ossDto, encryptBase64String(ossDto.getOssPassword())))
					.collect(Collectors.toList());
		}

		return ossList;
	}

	/**
	 * OSS 목록 조회
	 * @param ossName
	 * @return List<OssDto> ossDtoList
	 */
	@Override
	public List<OssDto> getOssList(String ossName) {
		List<OssDto> ossList = ossRepository.findByOssName(ossName).stream()
				.map(OssDto::from)
				.collect(Collectors.toList());

		if ( !CollectionUtils.isEmpty(ossList) ) {
			ossList = ossList.stream()
					.map(ossDto -> OssDto.withModifiedEncriptPassword(ossDto, encryptBase64String(ossDto.getOssPassword())))
					.collect(Collectors.toList());
		}

		return ossList;
	}

	/**
	 * OSS 등록
	 * @param ossDto
	 * @return
	 */
	@Override
	public Long registOss(OssDto ossDto) {
		OssTypeDto ossTypeDto = OssTypeDto.from(ossTypeRepository.findByOssTypeIdx(ossDto.getOssTypeIdx()));
		ossDto = ossDto.withModifiedEncriptPassword(ossDto, encryptAesString(ossDto.getOssPassword()));
		OssDto.from(ossRepository.save(OssDto.toEntity(ossDto, ossTypeDto)));
		return ossDto.getOssIdx();
	}

	/**
	 * OSS 수정
	 * @param ossDto
	 * @return
	 */
	@Override
	public Long updateOss(OssDto ossDto) {
		OssTypeDto ossTypeDto = OssTypeDto.from(ossTypeRepository.findByOssTypeIdx(ossDto.getOssTypeIdx()));

		ossDto = ossDto.withModifiedEncriptPassword(ossDto, encryptAesString(ossDto.getOssPassword()));
		managedJenkinsCredential(ossDto, "update");
		ossRepository.save(OssDto.toEntity(ossDto, ossTypeDto));
		return ossDto.getOssIdx();
	}

	/**
	 * OSS 삭제
	 * @param ossIdx
	 */
	@Transactional
	@Override
	public void deleteOss(Long ossIdx) {
		OssDto deleteOss = OssDto.from(ossRepository.findByOssIdx(ossIdx));
		managedJenkinsCredential(deleteOss, "delete");
		ossRepository.deleteById(ossIdx);
	}

	/**
	 * OSS 연결 확인
	 * @param ossDto
	 * TODO : 추후 OSS 추가
	 */
	@Transactional
	@Override
	public Boolean checkConnection(OssDto ossDto) {
		OssTypeDto osstypeDto = OssTypeDto.from(ossTypeRepository.findByOssTypeIdx(ossDto.getOssTypeIdx()));

		if(!osstypeDto.getOssTypeName().isEmpty()) {
			switch(osstypeDto.getOssTypeName()) {
				case "JENKINS" :
					if (StringUtils.isBlank(ossDto.getOssUrl()) ||
							StringUtils.isBlank(ossDto.getOssUsername()) ||
							StringUtils.isBlank(ossDto.getOssPassword()) ) {
						log.error("접속정보 누락");
						return false;
					}
					// Front에서 Base64Encoding한 데이터를 복호화하여 AES256 암호화 함.
					ossDto = ossDto.withModifiedEncriptPassword(ossDto, encryptAesString(ossDto.getOssPassword()));
					return jenkinsService.isJenkinsConnect(ossDto);

				case "TUMBLEBUG" :
					if (StringUtils.isBlank(ossDto.getOssUrl()) ||
							StringUtils.isBlank(ossDto.getOssUsername()) ||
							StringUtils.isBlank(ossDto.getOssPassword()) ) {
						log.error("접속정보 누락");
						return false;
					}

					try {
						// Front에서 Base64Encoding한 데이터를 복호화하여 AES256 암호화 함.
						ossDto = ossDto.withModifiedEncriptPassword(ossDto, encryptAesString(ossDto.getOssPassword()));
						List<TumblebugDto> list = tumblebugService.getNamespaceList(ossDto);

						if(list.size() >= 0) return true;
						else return false;
					} catch (Exception e) {
						log.error("API CALL Fail");
					}
//			case "GITLAB" :
//				if (StringUtils.isBlank(ossDto.getOssUrl()) ||
//						StringUtils.isBlank(ossDto.getOssUsername()) ||
//						StringUtils.isBlank(ossDto.getOssPassword()) ) {
//					log.error("접속정보 누락");
//					return false;
//				}
//
//				// Front에서 Base64Encoding한 데이터를 복호화하여 AES256 암호화 함.
//				ossDto.withModifiedEncriptPassword(ossDto, encryptAesString(ossDto.getOssPassword()));
//				return gitlabService.isConnectByPw(ossDto);
//
//			case "NEXUS" :
//				if (StringUtils.isBlank(ossDto.getOssUrl()) ||
//						StringUtils.isBlank(ossDto.getOssUsername()) ) {
//					log.error("접속정보 누락");
//					return false;
//				}
//
//				// Front에서 Base64Encoding한 데이터를 복호화하여 AES256 암호화 함.
//				ossDto.withModifiedEncriptPassword(ossDto, encryptAesString(ossDto.getOssPassword()));
//				return nexusService.checkNexusConnection(ossDto);

				default:
					log.debug("[checkConnection] oss code >>> {}", osstypeDto.getOssTypeName());
					log.error("Code is not registered] ossTypeName >>> {}", osstypeDto.getOssTypeName());
					return false;
			}
		}
		else {
			log.debug("[checkConnection] oss code >>> {}", osstypeDto.getOssTypeName());
			log.error("[OssTypeName is Null] ossTypeIdx >>> {}", ossDto.getOssTypeIdx());
			return false;
		}
	}

	/**
	 * OSS 정보 상세 조회
	 * @param ossIdx
	 * @return
	 */
	public OssDto detailOss(Long ossIdx) {
		return OssDto.from(ossRepository.findByOssIdx(ossIdx));
	}

//	/**
//	 * OSS 정보 상세 조회
//	 * @param ossCd
//	 * @return
//	 */
//	public OssDto getOssByOssCd(String ossCd) {
//		return OssDto.from(ossRepository.findByOssType_OssTypeName(ossCd));
//	}

	/**
	 * OSS 정보 중복 체크(ossName, ossUrl, ossUsername)
	 * @param ossDto
	 * 중복: true / 아니면 false
	 * @return
	 */
	public Boolean isOssInfoDuplicated(OssDto ossDto) {
		return ossRepository.existsByOssNameAndOssUrlAndOssUsername(
				ossDto.getOssName(),
				ossDto.getOssUrl(),
				ossDto.getOssUsername());
	}

	/**
	 * 젠킨스 credential 수정 또는 삭제
	 * @param managedOss
	 * @param managedType
	 * TODO : 고도화 (같은 oss 여러개 입력받기)
	 */
	private void managedJenkinsCredential(OssDto managedOss, String managedType) {
		OssTypeDto ossTypeDto = OssTypeDto.from(ossTypeRepository.findByOssTypeIdx(managedOss.getOssTypeIdx()));

		if ( !StringUtils.equals("JENKINS", ossTypeDto.getOssTypeName()) ) {
			OssDto jenkins = OssDto.from(ossRepository.findByOssType_OssTypeName("JENKINS"));

			if ( StringUtils.equals("update", managedType) ) {
				jenkinsService.updateCredential(jenkins, managedOss, JenkinsCredential.getCredentialTypeByOss(ossTypeDto.getOssTypeName()));
			}
			else {
				jenkinsService.deleteCredential(jenkins, managedOss, JenkinsCredential.getCredentialTypeByOss(ossTypeDto.getOssTypeName()));
			}
		}
	}

	/**
	 * 패스워드 암호화 (Front로 Base64Encoding한 데이터를 보냄.)
	 * @param str
	 * @return
	 */
	public String encryptBase64String(String str) {
		if ( StringUtils.isNotBlank(str) ) {
			return Base64Util.base64Encoding(AES256Util.decrypt(str));
		}
		else {
			return null;
		}
	}

	/**
	 * 패스워드 암호화 (Front에서 Base64Encoding한 데이터를 복호화하여 AES256 암호화 함.)
	 * @param str
	 * @return
	 */
	public String encryptAesString(String str) {
		if ( StringUtils.isNotBlank(str) ) {
			return AES256Util.encrypt(Base64Util.base64Decoding(str));
		}
		else {
			return null;
		}
	}
	/**
	 * 패스워드/토큰 암호화 (Front로 Base64Encoding한 데이터를 보내.)
	 * @param str
	 * @return
	 */
	public String encodingBase64String(String str) {
		if ( StringUtils.isNotBlank(str) ) {
			return Base64Util.base64Encoding(str);
		}
		else {
			return null;
		}
	}

	/**
	 * 패스워드 복호화
	 * @param encryptedStr
	 * @return
	 */
	public String decryptAesString(String encryptedStr) {
		if (StringUtils.isNotBlank(encryptedStr)) {
			// AES256으로 암호화된 문자열을 복호화
			String decrypted = AES256Util.decrypt(encryptedStr);
			// 복호화된 문자열을 Base64로 인코딩
			return decrypted;
		} else {
			return null;
		}
	}
}