package com.henghao.parkland.utils.nfcutils;

import java.io.IOException;
import java.nio.charset.Charset;

import android.content.Intent;
import android.net.Uri;
import android.nfc.FormatException;
import android.nfc.NdefMessage;
import android.nfc.NdefRecord;
import android.nfc.NfcAdapter;
import android.nfc.Tag;
import android.nfc.tech.Ndef;
import android.nfc.tech.NdefFormatable;
import android.os.Parcelable;

/**
 * NFC操作类
 * 〈一句话功能简述〉
 * 〈功能详细描述〉
 * @author yanqiyun
 * @version HDMNV100R001, 2016-11-16
 * @see [相关类/方法]
 * @since [产品/模块版本]
 */
public class NFCUtil {
	/**
	 * 把Uri数据写入NFC标签
	 * @param intent
	 * @param uriString
	 */
	public void writeToNfcUri(Intent intent, String uriString) {
		Tag tag = intent.getParcelableExtra(NfcAdapter.EXTRA_TAG);
		if (tag == null) {
			return;
		}
		// 创建NdefRecord对象
		NdefRecord[] records = new NdefRecord[1];
		if (uriString != null) {// 如果uriString不为空
			byte prefix = 0;
			// 从uriString前缀集合中找到匹配的前缀，并获得相应的标识代码
			for (Byte b : UriRecord.URI_PREFIX_MAP.keySet()) {
				// 将UriString前缀转换成小写
				String prefixStr = UriRecord.URI_PREFIX_MAP.get(b).toLowerCase();
				// 前缀不为空串
				if ("".equals(prefixStr)) {
					continue;
				}
				// 比较UriString前缀
				if (uriString.toLowerCase().startsWith(prefixStr)) {
					// 用字节表示的UriString前缀
					prefix = b;
					// 截取完整UriString中除了UriString前缀外的其他部分
					uriString = uriString.substring(prefixStr.length());
					break;
				}
			}
			// 为存储在标签中的UriString创建一个Byte数组
			byte[] data = new byte[1 + uriString.length()];
			// 指定第1字节为UriString前缀的标识代码
			data[0] = prefix;
			// 将剩余的部分复制到data字节数组中
			System.arraycopy(uriString.getBytes(), 0, data, 1, uriString.length());
			// 创建封装UriString的NdefRecord对象
			records[0] = new NdefRecord(NdefRecord.TNF_WELL_KNOWN, NdefRecord.RTD_URI, new byte[0], data);
		}

		// 将records传入NdefMessage中，用于向NFC标签写入数据
		NdefMessage ndefMessage = new NdefMessage(records);

		Ndef ndef = Ndef.get(tag);// ndef格式
		if (ndef != null) {// 判断是否格式化
			try {
				ndef.connect();// 连接
				if (ndef.isWritable()) {// 判断是否可写
					int size = ndefMessage.toByteArray().length;
					if (ndef.getMaxSize() > size) {// 判断写入的数据量是否大于标签的最大可写数据量
						ndef.writeNdefMessage(ndefMessage);
					}
				}
			}
			catch (IOException e) {
				e.printStackTrace();
			}
			catch (FormatException e) {
				e.printStackTrace();
			}
		}
		else {
			try {
				NdefFormatable ndefFormatable = NdefFormatable.get(tag);
				if (ndefFormatable != null) {
					ndefFormatable.connect();
					ndefFormatable.format(ndefMessage);// 格式化并写入数据
				}
			}
			catch (IOException e) {
				e.printStackTrace();
			}
			catch (FormatException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * 把文本数据text写入NFC标签
	 * @param intent
	 * @param text
	 */
	public void writeToNfcText(Intent intent, String text) {
		Tag tag = intent.getParcelableExtra(NfcAdapter.EXTRA_TAG);
		if (tag == null) {
			return;
		}
		// 创建NdefRecord对象
		NdefRecord[] records = new NdefRecord[1];
		if (text != null) {// 如果text不为空
			records[0] = new NdefRecord(NdefRecord.TNF_WELL_KNOWN, NdefRecord.RTD_TEXT, new byte[] {
				0
			}, text.getBytes(Charset.forName("utf-8")));
		}
		// 将records传入NdefMessage中，用于向NFC标签写入数据
		NdefMessage ndefMessage = new NdefMessage(records);

		Ndef ndef = Ndef.get(tag);// ndef格式
		if (ndef != null) {// 判断是否格式化
			try {
				ndef.connect();// 连接
				if (ndef.isWritable()) {// 判断是否可写
					int size = ndefMessage.toByteArray().length;
					if (ndef.getMaxSize() > size) {// 判断写入的数据量是否大于标签的最大可写数据量
						ndef.writeNdefMessage(ndefMessage);
					}
				}
			}
			catch (IOException e) {
				e.printStackTrace();
			}
			catch (FormatException e) {
				e.printStackTrace();
			}
		}
		else {
			try {
				NdefFormatable ndefFormatable = NdefFormatable.get(tag);
				if (ndefFormatable != null) {
					ndefFormatable.connect();
					ndefFormatable.format(ndefMessage);// 格式化并写入数据
				}
			}
			catch (IOException e) {
				e.printStackTrace();
			}
			catch (FormatException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * 读取NFC标签，返回Uri对象
	 * @param intent
	 * @return
	 */
	public Uri readNFCUriTag(Intent intent) {
		Uri uri = null;
		Tag tag = intent.getParcelableExtra(NfcAdapter.EXTRA_TAG);
		if (tag == null) {
			return null;
		}
		// 判断是否为ACTION_NDEF_DISCOVERED
		if (NfcAdapter.ACTION_NDEF_DISCOVERED.equals(intent.getAction())) {
			// 从标签读取数据（Parcelable对象）
			Parcelable[] rawMsgs = intent.getParcelableArrayExtra(NfcAdapter.EXTRA_NDEF_MESSAGES);

			NdefMessage msgs[] = null;
			if (rawMsgs != null) {
				msgs = new NdefMessage[rawMsgs.length];
				// 标签可能存储了多个NdefMessage对象，一般情况下只有一个NdefMessage对象
				for (int i = 0; i < rawMsgs.length; i++) {
					// 转换成NdefMessage对象
					msgs[i] = (NdefMessage) rawMsgs[i];
				}
			}

			if (msgs != null) {
				// 程序中只考虑了1个NdefRecord对象，若是通用软件应该考虑所有的NdefRecord对象
				NdefRecord record = msgs[0].getRecords()[0];
				UriRecord uriRecord = UriRecord.parse(record);
				uri = uriRecord.getUri();
			}
		}
		return uri;
	}

	/**
	 * 读取NFC
	 * 〈一句话功能简述〉
	 * 〈功能详细描述〉
	 * @param intent
	 * @return String
	 * @see [类、类#方法、类#成员]
	 * @since [产品/模块版本]
	 */
	public String readNFCTextTag(Intent intent) {
		String text = null;
		Tag tag = intent.getParcelableExtra(NfcAdapter.EXTRA_TAG);
		if (tag == null) {
			return null;
		}
		// 判断是否为ACTION_NDEF_DISCOVERED
		if (NfcAdapter.ACTION_NDEF_DISCOVERED.equals(intent.getAction())) {
			// 从标签读取数据（Parcelable对象）
			Parcelable[] rawMsgs = intent.getParcelableArrayExtra(NfcAdapter.EXTRA_NDEF_MESSAGES);

			NdefMessage msgs[] = null;
			int contentSize = 0;
			if (rawMsgs != null) {
				msgs = new NdefMessage[rawMsgs.length];
				// 标签可能存储了多个NdefMessage对象，一般情况下只有一个NdefMessage对象
				for (int i = 0; i < rawMsgs.length; i++) {
					// 转换成NdefMessage对象
					msgs[i] = (NdefMessage) rawMsgs[i];
				}
			}

			if (msgs != null) {
				// 程序中只考虑了1个NdefRecord对象，若是通用软件应该考虑所有的NdefRecord对象
				NdefRecord record = msgs[0].getRecords()[0];
				text = new String(record.getPayload(), Charset.forName("utf-8"));
			}
		}
		return text;
	}
}
