package m.cmp.wfManager.oss.service;

import java.util.List;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import m.cmp.wfManager.oss.mapper.OssMapper;
import m.cmp.wfManager.oss.model.Oss;
import m.cmp.wfManager.util.AES256Util;
import m.cmp.wfManager.util.Base64Utils;



@Service
public class OssService {
	
	
	@Autowired
	private OssMapper ossMapper;
	
	/**
	 * OSS 목록 조회
	 * @param ossCd
	 * @return
	 */
	public List<Oss> getOssList(String ossCd) {
		List<Oss> ossList = ossMapper.selectOssList(ossCd);
		if ( !CollectionUtils.isEmpty(ossList) ) {
			for ( Oss oss : ossList ) {
				// 비밀번호 암호화
				oss.setOssPassword(encryptBase64String(oss.getOssPassword()));
				
				// 토큰 암호화
				oss.setOssToken(encryptBase64String(oss.getOssToken()));
			}
		}
		
		return ossList;
	}
	
	/**
	 * 패스워드/토큰 암호화 (Front로 Base64Encoding한 데이터를 보내.)
	 * @param str
	 * @return
	 */
	public String encryptBase64String(String str) {
		if ( StringUtils.isNotBlank(str) ) {
			return Base64Utils.base64Encoding(AES256Util.decrypt(str));
		}
		else {
			return null;
		}
	}
	
	/**
	 * OSS 정보 상세 조회
	 * @param ossId
	 * @return
	 */
	public Oss getOss(int ossId) {
		return ossMapper.selectOss(ossId);
	}
	
	/**
	 * OSS 정보 중복 체크(ossName, ossUrl, ossUsername)
	 * @param oss
	 * @return
	 */
	public boolean isOssInfoDuplicated(Oss oss) {
		// 중복이면 true / 아니면 false
		return ossMapper.isOssInfoDuplicated(oss);
	}
	
	/**
	 * 패스워드/토큰 암호화 (Front에서 Base64Encoding한 데이터를 복호화하여 AES256 암호화 함.)
	 * @param str
	 * @return
	 */
	public String encryptAesString(String str) {
		if ( StringUtils.isNotBlank(str) ) {
			return AES256Util.encrypt(Base64Utils.base64Decoding(str));
		}
		else {
			return null;
		}
	}

	/**
	 * OSS 등록
	 * @param oss
	 * @return
	 */
	public int createOss(Oss oss) {
		// 비밀번호 암호화
		oss.setOssPassword(encryptAesString(oss.getOssPassword()));
		
		// 토큰 암호화
		oss.setOssToken(encryptAesString(oss.getOssToken()));
        
		ossMapper.insertOss(oss);
		return oss.getOssId();
	}

	/**
	 * OSS 수정
	 * @param oss
	 * @return
	 */
	public int updateOss(Oss oss) {
		// 비밀번호 암호화
		oss.setOssPassword(encryptAesString(oss.getOssPassword()));
		
		// 토큰 암호화
		oss.setOssToken(encryptAesString(oss.getOssToken()));
		
		ossMapper.updateOss(oss);
		return oss.getOssId();
	}

	/**
	 * OSS 삭제
	 * @param ossId
	 */
	public void deleteOss(int ossId) {
		// DB에서 삭제
		ossMapper.deleteOss(ossId);
	}
	
}

	