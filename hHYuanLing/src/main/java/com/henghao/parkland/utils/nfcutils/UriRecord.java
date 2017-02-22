package com.henghao.parkland.utils.nfcutils;

import java.nio.charset.Charset;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import android.net.Uri;
import android.nfc.NdefRecord;

public class UriRecord {
	// 映射Uri前缀和对应的值
	public static final Map<Byte, String> URI_PREFIX_MAP = new HashMap<Byte, String>();
	static {
		// 设置NDEF Uri规范支持的Uri前缀，在解析payload时，需要根据payload的第1个字节定位相应的uri前缀
		URI_PREFIX_MAP.put((byte) 0x00, "");
		URI_PREFIX_MAP.put((byte) 0x01, "http://www.");
		URI_PREFIX_MAP.put((byte) 0x02, "https://www.");
		URI_PREFIX_MAP.put((byte) 0x03, "http://");
		URI_PREFIX_MAP.put((byte) 0x04, "https://");
		URI_PREFIX_MAP.put((byte) 0x05, "tel:");
		URI_PREFIX_MAP.put((byte) 0x06, "mailto:");
		URI_PREFIX_MAP.put((byte) 0x07, "ftp://anonymous:anonymous@");
		URI_PREFIX_MAP.put((byte) 0x08, "ftp://ftp.");
		URI_PREFIX_MAP.put((byte) 0x09, "ftps://");
		URI_PREFIX_MAP.put((byte) 0x0A, "sftp://");
		URI_PREFIX_MAP.put((byte) 0x0B, "smb://");
		URI_PREFIX_MAP.put((byte) 0x0C, "nfs://");
		URI_PREFIX_MAP.put((byte) 0x0D, "ftp://");
		URI_PREFIX_MAP.put((byte) 0x0E, "dav://");
		URI_PREFIX_MAP.put((byte) 0x0F, "news:");
		URI_PREFIX_MAP.put((byte) 0x10, "telnet://");
		URI_PREFIX_MAP.put((byte) 0x11, "imap:");
		URI_PREFIX_MAP.put((byte) 0x12, "rtsp://");
		URI_PREFIX_MAP.put((byte) 0x13, "urn:");
		URI_PREFIX_MAP.put((byte) 0x14, "pop:");
		URI_PREFIX_MAP.put((byte) 0x15, "sip:");
		URI_PREFIX_MAP.put((byte) 0x16, "sips:");
		URI_PREFIX_MAP.put((byte) 0x17, "tftp:");
		URI_PREFIX_MAP.put((byte) 0x18, "btspp://");
		URI_PREFIX_MAP.put((byte) 0x19, "btl2cap://");
		URI_PREFIX_MAP.put((byte) 0x1A, "btgoep://");
		URI_PREFIX_MAP.put((byte) 0x1B, "tcpobex://");
		URI_PREFIX_MAP.put((byte) 0x1C, "irdaobex://");
		URI_PREFIX_MAP.put((byte) 0x1D, "file://");
		URI_PREFIX_MAP.put((byte) 0x1E, "urn:epc:id:");
		URI_PREFIX_MAP.put((byte) 0x1F, "urn:epc:tag:");
		URI_PREFIX_MAP.put((byte) 0x20, "urn:epc:pat:");
		URI_PREFIX_MAP.put((byte) 0x21, "urn:epc:raw:");
		URI_PREFIX_MAP.put((byte) 0x22, "urn:epc:");
		URI_PREFIX_MAP.put((byte) 0x23, "urn:nfc:");
	}

	private final Uri mUri;

	private UriRecord(Uri uri) {
		this.mUri = uri;
	}

	// 获取已经解析的Uri
	public Uri getUri() {
		return this.mUri;
	}

	public static UriRecord parse(NdefRecord record) {
		// 获取TNF
		short tnf = record.getTnf();
		// TNF是TNF_WELL_KNOWN，使用了前缀的Uri
		if (tnf == NdefRecord.TNF_WELL_KNOWN) {
			return parseWellKnown(record);
		}
		// TNF是TNF_ABSOLUTE_URI，即绝对Uri，不使用前缀
		else if (tnf == NdefRecord.TNF_ABSOLUTE_URI) {
			return parseAbsolute(record);
		}
		throw new IllegalArgumentException("Unknown TNF " + tnf);
	}

	/** Parse and absolute URI record */
	private static UriRecord parseAbsolute(NdefRecord record) {
		// 直接将payload转成uri
		byte[] payload = record.getPayload();
		Uri uri = Uri.parse(new String(payload, Charset.forName("UTF-8")));
		return new UriRecord(uri);
	}

	/** Parse an well known URI record */
	private static UriRecord parseWellKnown(NdefRecord record) {
		// 判断RTD是否为RTD_URI
		if (!Arrays.equals(record.getType(), NdefRecord.RTD_URI)) {
			return null;
		}
		byte[] payload = record.getPayload();
		/*
		 * payload[0] contains the URI Identifier Code, per the NFC Forum
		 * "URI Record Type Definition" section 3.2.2.
		 * payload[1]...payload[payload.length - 1] contains the rest of the
		 * URI.
		 */
		// payload[0]中包括URI标识代码，也就是URI_PREFIX_MAP中的key
		// 根据Uri标识代码获取Uri前缀
		String prefix = URI_PREFIX_MAP.get(payload[0]);
		// 获取Uri前缀占用的字节数
		byte[] prefixBytes = prefix.getBytes(Charset.forName("UTF-8"));
		// 为容纳完整的Uri创建一个byte数组
		byte[] fullUri = new byte[prefixBytes.length + payload.length - 1];
		// 将Uri前缀和其余部分组合，形成一个完整的Uri
		System.arraycopy(prefixBytes, 0, fullUri, 0, prefixBytes.length);
		System.arraycopy(payload, 1, fullUri, prefixBytes.length, payload.length - 1);
		// 根据解析出来的Uri创建Uri对象
		Uri uri = Uri.parse(new String(fullUri, Charset.forName("UTF-8")));
		// 创建UriRecord对象并返回
		return new UriRecord(uri);
	}

}
