package com.han.jun.utils;

import java.io.UnsupportedEncodingException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESedeKeySpec;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import org.apache.commons.codec.binary.Base64;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

/* �븘�닔 �씪�씠釉뚮윭由�
 *  spring-security-crypto 5.3.10
 *  commons-codec 1.15
 */

@Component
public class Encryption implements PasswordEncoder{
	// Spring Security媛� 湲곕낯�쟻�쓣 �젣怨듯븯�뒗 �븫�샇�솕 湲곕쾿 : SHA 湲곕컲
	private PasswordEncoder passwordEncoder;

	public Encryption() {
		this.passwordEncoder = new BCryptPasswordEncoder();
	}

	public Encryption(PasswordEncoder passwordEncoder) {
		this.passwordEncoder = passwordEncoder;
	}

	/* Spring Security媛� �젣怨듯븯�뒗 SHA湲곕컲�쓽 �븫�샇�솕 �샇異� 硫붿꽌�뱶
	 * originPassword : Encryption�씠 �쟻�슜�릺吏� �븡�� �븫�샇
	 */
	@Override
	public String encode(CharSequence originPassword) {
		return passwordEncoder.encode(originPassword);
	}

	@Override
	public boolean matches(CharSequence originPassword, String encodedPassword) {
		return passwordEncoder.matches(originPassword, encodedPassword);
	}
	
	/**
	 * DES Encryption
	 * 
	 * @param data
	 *            鍮꾨��궎 �븫�샇�솕瑜� �씗留앺븯�뒗 臾몄옄�뿴
	 *  @param hint
	 *            �븫�샇�솕�뿉 �궗�슜�릺�뼱吏��뒗 keyValue臾몄옄�뿴�쓣 留뚮뱶�뒗�뜲 �궗�슜�븷 �궗�슜�옄 吏��젙 臾몄옄
	 * @return String �븫�샇�솕�맂 DATA
	 * @exception Exception
	 */
	public String TripleDesEncoding(String data, String hint) throws Exception {
		if (data == null || data.length() == 0) {	return "";}

		String instance = (key(hint).length() == 24) ? "DESede/ECB/PKCS5Padding"
				: "DES/ECB/PKCS5Padding";
		javax.crypto.Cipher cipher = javax.crypto.Cipher.getInstance(instance);
		cipher.init(javax.crypto.Cipher.ENCRYPT_MODE, getKey(key(hint)));
		String amalgam = data;

		byte[] inputBytes1 = amalgam.getBytes("UTF8");
		byte[] outputBytes1 = cipher.doFinal(inputBytes1);

		Base64.encodeBase64(outputBytes1);

		String encryptionData = new String(Base64.encodeBase64(outputBytes1));
		return encryptionData;
	}

	/**
	 * DES Decryption
	 * 
	 * @param encryptionData
	 *            鍮꾨��궎 蹂듯샇�솕瑜� �씗留앺븯�뒗 臾몄옄�뿴
	 * @param hint
	 *    		   蹂듯샇�솕�뿉 �궗�슜�릺�뼱吏��뒗 keyValue臾몄옄�뿴�쓣 留뚮뱶�뒗�뜲 �궗�슜�븷 �궗�슜�옄 吏��젙 臾몄옄 
	 * @return String 蹂듯샇�솕�맂 DATA
	 * @exception Exception
	 */
	public String TripleDesDecoding(String encryptionData, String hint) throws Exception {
		if (encryptionData == null || encryptionData.length() == 0)
			return "";

		String instance = (key(hint).length() == 24) ? "DESede/ECB/PKCS5Padding"
				: "DES/ECB/PKCS5Padding";
		javax.crypto.Cipher cipher = javax.crypto.Cipher.getInstance(instance);
		cipher.init(javax.crypto.Cipher.DECRYPT_MODE, getKey(key(hint)));

		byte[] inputBytes1 = Base64.decodeBase64(encryptionData);
		byte[] outputBytes2 = cipher.doFinal(inputBytes1);

		String decryptionData = new String(outputBytes2, "UTF8");
		return decryptionData;
	}

	/**
	 * AES Encryption
	 * 
	 * @param encryptionData
	 *            鍮꾨��궎 蹂듯샇�솕瑜� �씗留앺븯�뒗 臾몄옄�뿴
	 * @param hint
	 *    		   蹂듯샇�솕�뿉 �궗�슜�릺�뼱吏��뒗 keyValue臾몄옄�뿴�쓣 留뚮뱶�뒗�뜲 �궗�슜�븷 �궗�슜�옄 吏��젙 臾몄옄 
	 * @return String 蹂듯샇�솕�맂 DATA
	 * @exception Exception
	 */
	// AES256 �븫�샇�솕
	public String aesEncode(String str, String hint) throws java.io.UnsupportedEncodingException, 
	NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException, 
	InvalidAlgorithmParameterException, IllegalBlockSizeException, 
	BadPaddingException {

		String keyValue = key(hint);
		HashMap<String, Object> mapAES = AES256Key(keyValue);

		Cipher c = Cipher.getInstance("AES/CBC/PKCS5Padding");
		c.init(Cipher.ENCRYPT_MODE, (Key)mapAES.get("key"), new IvParameterSpec(mapAES.get("iv").toString().getBytes()));

		byte[] encrypted = c.doFinal(str.getBytes("UTF-8"));
		String enStr = new String(Base64.encodeBase64(encrypted));

		return enStr;
	}

	//AES256 蹂듯샇�솕
	public String aesDecode(String encryptionData, String hint) throws java.io.UnsupportedEncodingException,
	NoSuchAlgorithmException,
	NoSuchPaddingException, 
	InvalidKeyException, 
	InvalidAlgorithmParameterException,
	IllegalBlockSizeException, 
	BadPaddingException {
		String keyValue = key(hint);
		HashMap<String, Object> mapAES = AES256Key(keyValue);

		Cipher c = Cipher.getInstance("AES/CBC/PKCS5Padding");
		c.init(Cipher.DECRYPT_MODE, (Key)mapAES.get("key"), new IvParameterSpec(mapAES.get("iv").toString().getBytes("UTF-8")));

		byte[] byteStr = Base64.decodeBase64(encryptionData.getBytes());

		return new String(c.doFinal(byteStr),"UTF-8");
	}

	// Triple DES Encoding and Decoding + AES Encoding and Decoding
	// key媛� 由ы꽩
	private String key(String hint) {  // iciaorkr
		// 24�옄由�(24諛붿씠�듃)留� key 媛믪쑝濡� �엯�젰 媛��뒫
		char[] compareValue = ("k1cj4w@3ib9!lhv#sd7$x0a%qtm^rg2&y6?epu5zn8fo").toCharArray();
		char[] addRootKey = ("KoreaHoonZzangVictoryWin").toCharArray();
		
		String keyValue = "";

		// �뙆�씪誘명꽣濡� �쟾�떖 諛쏆� rootKey瑜� char[]�뿉 ���옣
		char[] keyValueArray = new char[24];
		char[] charHint = hint.toCharArray();
		for(int index = 0; index < charHint.length; index++) {
			keyValueArray[index] = charHint[index]; 
		}
		
		// rootKey�쓽 媛믪씠 24bytes媛� �븞�맆 寃쎌슦 紐⑥옄�� bytes留뚰겮 �엫�쓽�쓽 媛믪쓣 ���엯
		for(int i=0; i < 24 - hint.length(); i++){
			keyValueArray[hint.length()+i] = addRootKey[i]; 
		}
		
		// keyValueArray�뿉 ���옣�맂 媛믪쓣 keyValue�쓽 媛믨낵 鍮꾧탳�븯�뿬 �씪移섑븯�뒗 index媛믪쓣 idx�뿉 ���옣
		for(int i=0; i<keyValueArray.length; i++){
			for(int j=0; j<compareValue.length; j++){ 
				if (keyValueArray[i] == compareValue[j]){
					int index = j % 24;
					keyValue += addRootKey[index];
					break;
				} 
			}
		}
		return keyValue;
	}

	/** Tripple DES
	 * 吏��젙�맂 鍮꾨��궎瑜� 媛�吏�怨� �삤�뒗 硫붿꽌�뱶 (TripleDES) require Key Size : 24 bytes
	 * �엫�쓽�쓽 key媛믪쑝濡� �븫�샇�솕�� 蹂듯샇�솕 媛��뒫 
	 */
	private Key getKey(String keyValue) throws Exception {
		DESedeKeySpec desKeySpec = new DESedeKeySpec(keyValue.getBytes());
		SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DESede");
		Key key = keyFactory.generateSecret(desKeySpec);
		return key;
	}

	// AES 256 
	private HashMap<String, Object> AES256Key(String keyValue) throws UnsupportedEncodingException {
		String iv;
		Key key;
		HashMap<String, Object> mapAES = new HashMap<String, Object>();

		iv = keyValue.substring(0, 16);
		mapAES.put("iv", iv);

		byte[] keyBytes = new byte[16];
		byte[] b = keyValue.getBytes("UTF-8");
		int len = b.length;
		if (len > keyBytes.length) {
			len = keyBytes.length;
		}
		System.arraycopy(b, 0, keyBytes, 0, len);
		SecretKeySpec keySpec = new SecretKeySpec(keyBytes, "AES");

		key = keySpec;
		mapAES.put("key", key);
		return mapAES;
	}

}
