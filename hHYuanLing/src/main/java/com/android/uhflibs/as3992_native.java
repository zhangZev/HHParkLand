package com.android.uhflibs;

import java.util.ArrayList;
import android.util.Log;

public class as3992_native {

	private int fd;
	private byte[] buf;// = new byte[0];
	private byte[] lock = new byte[0];
	private boolean read_ok = false;
	private static final String TAG = "as3992_native";

	public int OpenComPort(String device) {
		fd = open_serial(device);
		Log.i(TAG, "file is " + fd);
		if (fd < 0) {
			Log.e(TAG, "native open returns null");
			return -1;
		}
		return 0;
	}

	public void CloseComPort() {
		close_serial(fd);
	}

	private int btoi(byte a) {
		return (a < 0 ? a + 256 : a);
	}

	private byte[] wrap(byte[] in) {
		byte[] res = new byte[in.length + 5];
		res[0] = 0x2;
		res[1] = (byte) ((in.length + 2) >> 8);
		res[2] = (byte) (in.length + 2);
		System.arraycopy(in, 0, res, 3, in.length);
		res[3 + in.length] = 0x3;
		byte cc = (byte) 0xff;
		for (int i = 0; i < in.length; i++) {
			cc += in[i];
		}
		cc += 3;
		res[4 + in.length] = cc;
		return res;
	}

	public void read_thread() {
		byte[] head = try_read(fd, 5, 10);
		if (head == null) {
			return;
		}
//		for(byte i : head)
//		{
//			Log.i("as3992_debug", "head value is " + i);
//		}
		if(head[0] != 2)
		{
//			Log.e("as3992_thread", "wrong frame head");
			drop_data(fd);
			return;
		}
		int lens = btoi(head[1]) * 256 + btoi(head[2]);
		if((lens - 2) != head[4])
		{
			Log.e("as3992_thread", "frame head is " + lens + " and data head is " + head[4] + " not equal");
			drop_data(fd);
			return;
		}
		if(head[4] < 3)
		{
			Log.e("as3992_thread", "wrong data length is " + head[4] + "!!!!!!!!!!!!!!!!!!!!!!");
			drop_data(fd);
			return;
		}
		switch (head[3]) {
		case 0x40: // for iso6b
		case 0x48: // for iso6b
		case 0x50: // for iso6b
		case 0x11:
		case 0x19:
		case 0x1b:
		case 0x1d:
		case 0x32:
		case 0x34:
		case 0x36:
		case 0x38:
		case 0x3c:
		case 0x3e:
		case 0x42:
		case 0x44:
		case 0x56:
		case 0x58:
		case 0x5a:
			Log.d("as3992", "read thread get in sync");
			byte[] tp = try_read(fd, head[4], 10);
			if (tp == null) {
				drop_data(fd);
				Log.e("as3992", "read valid data failed");
				return;
			}
			if(tp[tp.length - 2] != 0x3)
			{
				drop_data(fd);
				Log.e("as3992", "etx value is not 3");
				return;
			}
			byte lrc = (byte)(0xff + head[3] + head[4]);  
			for(int sl = 0; sl < tp.length - 1; sl++)
			{
				lrc += tp[sl];
			}
			if(lrc != tp[tp.length - 1])
			{
				drop_data(fd);
				Log.e("as3992", "lrc checksum error");
				return;
			}
//			for(byte i : tp)
//			{
//				Log.i("as3992_debug", "tp value is " + i);
//			}
			synchronized (lock) {
				buf = new byte[tp.length - 2];
				System.arraycopy(tp, 0, buf, 0, buf.length);
				Log.d("as3992", "read valid data ok, ins is " + head[3]);
				read_ok = true;
				lock.notify();
				break;
			}
		default:
			Log.e("as3992", "get error data");
			// buf.notify();
			break;
		}
		Log.d("as3992", "leave success");
		return;
	}

	public ArrayList<byte[]> search_card6B() {
		byte[] num = new byte[1];
		num[0] = 0;
		ArrayList<byte[]> tags = new ArrayList<byte[]>();
		do {
			byte[] tmp = search_card6B_helper(num);
			if (tmp == null) {
				return null;
			} else {
				tags.add(tmp);
			}
		} while (num[0] > 1);
		return tags;
	}

	private int[] tj = new int[6];

	public byte[] search_card6B_helper(byte[] num) {
		if ((num == null) || (num.length == 0)) {
			return null;
		}
		byte[] cmd = new byte[13];
		for (int i = 0; i < cmd.length; i++) {
			cmd[i] = 0;
		}
		cmd[0] = 0x3f;
		cmd[1] = (byte) cmd.length;
		if (num[0] == 0) {
			Log.w(TAG, "********start cmd**************");
			cmd[2] = 1;
		} else {
			Log.w(TAG, "*******next cmd*********");
			cmd[2] = 2;
		}
		int reval = try_write(fd, wrap(cmd));
		if (reval < 0) {
			return null;
		}
		synchronized (lock) {
			try {
				lock.wait(200);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			if (read_ok && (buf[0] > 0) && (buf.length >= 14)) {
				read_ok = false;
				num[0] = buf[0];
				Log.i(TAG, "this is " + buf[0] + " cards");
				if (buf[2] != 8) {
					Log.w(TAG, "something error");
					return null;
				}
				byte[] uid = new byte[8];
				System.arraycopy(buf, 3, uid, 0, uid.length);
				Log.i(TAG, String.format("%02X%02X%02X%02X%02X%02X%02X%02X",
						uid[0], uid[1], uid[2], uid[3], uid[4], uid[5], uid[6],
						uid[7]));
				int freq = btoi(buf[11]) + (btoi(buf[12]) << 8)
						+ (btoi(buf[13]) << 16);
				Log.i(TAG, "freq is " + freq);
				if ((freq >= 920625) && (freq <= 924375)) {
					int ids = (freq - 920625) / 750;
					tj[ids]++;
					for (int nn = 0; nn < tj.length; nn++) {
						if (nn == ids) {
							Log.w("as3992_native", "freq "
									+ (920625 + nn * 750) + " times is "
									+ tj[nn]);
						} else {
							Log.i("as3992_native", "freq "
									+ (920625 + nn * 750) + " times is "
									+ tj[nn]);
						}
					}
				}
				return uid;
			} else if (read_ok) {
				Log.e(TAG, "search 6B failed, read is " + read_ok
						+ " num of tags is " + buf[0] + " buf length is "
						+ buf.length);
				read_ok = false;
				return null;
			} else {
				Log.e(TAG, "nothing readed, time out");
				return null;
			}
		}

	}

	public byte[] read_tag6B(byte[] uid, int addr, int count) {
		if ((uid == null) || (uid.length != 8)) {
			return null;
		}
		byte[] cmd = new byte[12];
		cmd[0] = 0x49;
		cmd[1] = (byte) cmd.length;
		System.arraycopy(uid, 0, cmd, 2, uid.length);
		cmd[10] = (byte) addr;
		cmd[11] = (byte) count;
		int reval = try_write(fd, wrap(cmd));
		if (reval < 0) {
			return null;
		}
		synchronized (lock) {
			try {
				lock.wait(200);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			if ((read_ok) && (buf[0] == 0) && (buf[1] > 0)) {
				read_ok = false;
				byte[] cnt = new byte[buf[1]];
				Log.i(TAG, "cnt length is " + buf[1]);
				System.arraycopy(buf, 2, cnt, 0, cnt.length);
				for (byte s : cnt) {
					Log.i(TAG, String.format("%x ", s));
				}
				return cnt;
			} else if (read_ok) {
				Log.e(TAG, "read 6b failed buf[0] is " + buf[0]);
				read_ok = false;
				return null;
			} else {
				Log.e(TAG, "nothing readed, time out");
				return null;
			}
		}
	}

	public int write_tag6B(byte[] uid, int addr, byte[] cnt) {
		if ((uid == null) || (uid.length != 8) || (addr < 8)
				|| (cnt.length > 243)) {
			Log.e(TAG, "write 6B param error");
			return -1;
		}
		byte[] cmd = new byte[12 + cnt.length];
		cmd[0] = 0x47;
		cmd[1] = (byte) cmd.length;
		System.arraycopy(uid, 0, cmd, 2, uid.length);
		cmd[10] = (byte) addr;
		cmd[11] = (byte) cnt.length;
		System.arraycopy(cnt, 0, cmd, 12, cnt.length);
		int reval = try_write(fd, wrap(cmd));
		if (reval < 0) {
			return -1;
		}
		synchronized (lock) {
			try {
				lock.wait(200);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			if ((read_ok) && (buf[0] == 0)) {
				read_ok = false;
				Log.i(TAG, "write success");
				return 0;
			} else if (read_ok) {
				Log.e(TAG, "write failed, res is " + buf[0]);
				read_ok = false;
				return -1;
			} else {
				Log.e(TAG, "nothing readed, time out");
				return -1;
			}
		}
	}

	public int setlock(byte type, byte area, byte[] passwd) {
		byte[] cmd = new byte[8];
		cmd[0] = 0x3B;
		cmd[1] = (byte) cmd.length;
		cmd[2] = type;
		cmd[3] = area;
		System.arraycopy(passwd, 0, cmd, 4, 4);
		int reval = try_write(fd, wrap(cmd));
		if (reval < 0) {
			return -1;
		}
		synchronized (lock) {
			try {
				lock.wait(100);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			if (read_ok && (buf.length >= 1) && buf[0] == 0) {
				read_ok = false;
				return 0;
			} else {
				if (buf.length >= 1) {
					Log.e("as3992", "lock card failed " + read_ok + " error "
							+ buf[0]);
				} else {
					Log.e("as3992", "lock card failed " + read_ok);
				}
				read_ok = false;
				return -1;
			}
		}
	}

	public int setkill(byte[] passwd, byte recom) {
		byte[] cmd = new byte[7];
		cmd[0] = 0x3d;
		cmd[1] = (byte) cmd.length;
		cmd[6] = recom;
		System.arraycopy(passwd, 0, cmd, 2, 4);
		int reval = try_write(fd, wrap(cmd));
		if (reval < 0) {
			return -1;
		}
		synchronized (lock) {
			try {
				lock.wait(100);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			if (read_ok/* && buf[0] == 0 */) {
				read_ok = false;
				return 0;
			} else {
				Log.e("as3992", "kill card failed");
				return -1;
			}
		}
	}

	public class Tag_Data {
		Tag_Data(byte[] n_pc, byte[] n_epc) {
			pc = n_pc;
			epc = n_epc;
		}

		public byte[] pc;
		public byte[] epc;
	}

	public ArrayList<Tag_Data> search_card() {
		ArrayList<Tag_Data> cx = new ArrayList<Tag_Data>();
		byte[] cmd = new byte[3];
		cmd[0] = 0x31;
		cmd[1] = 3;
		cmd[2] = 1;
		int card_num = 0;
		int reval = try_write(fd, wrap(cmd));
		if (reval < 0) {
			return null;
		}
		synchronized (lock) {
			do {
				try {
					Log.d("as3992", "search begin to wait");
					lock.wait(100);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				if (read_ok && buf.length >= 2 && (buf[1] > 2) && (buf.length >= buf[1] + 2)) {
					Log.d("as3992", "search card read ok");
					read_ok = false;
					if (card_num == 0) {
						if (buf[0] == 0) {
							Log.e("as3992", "no card found");
							return null;
						}
						card_num = buf[0];
						Log.d("as3992", "get at last one card");
					}
					int epc_length = buf[1] - 2;
					byte[] pc = new byte[2];
					byte[] epc = new byte[epc_length];
					System.arraycopy(buf, 2, pc, 0, 2);
					System.arraycopy(buf, 4, epc, 0, epc_length);
					cx.add(new Tag_Data(pc, epc));
					card_num--;
				} else {
					read_ok = false;
					return null;
				}
			} while (card_num > 0);
		}
		return cx;
	}

	public class Tag_Data_Rssi {
		Tag_Data_Rssi(Tag_Data n_tdata, int n_freq, int n_ch_i, int n_ch_q) {
			tdata = n_tdata;
			freq = n_freq;
			ch_i = n_ch_i;
			ch_q = n_ch_q;
		}

		public Tag_Data tdata;
		public int freq;
		public int ch_i;
		public int ch_q;
	}

	public ArrayList<Tag_Data_Rssi> search_card_rssi() {
		ArrayList<Tag_Data_Rssi> cx = new ArrayList<Tag_Data_Rssi>();
		byte[] cmd = new byte[3];
		cmd[0] = 0x43;
		cmd[1] = 3;
		cmd[2] = 1;
		int card_num = 0;
		int reval = try_write(fd, wrap(cmd));
		if (reval < 0) {
			return null;
		}
		synchronized (lock) {
			do {
				try {
					Log.d("as3992", "search begin to wait");
					lock.wait(100);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
//				if (read_ok && (buf[5] > 2) && (buf.length >= buf[5] + 6)) {	//8?
				if (read_ok && buf.length >= 6 && (buf[5] > 2) && (buf.length >= buf[5] + 6)) {	//8?
					Log.d("as3992", "search card read ok");
					read_ok = false;
					if (card_num == 0) {
						if (buf[0] == 0) {
							Log.e("as3992", "no card found");
							return null;
						}
						card_num = buf[0];
						Log.d("as3992", "get at last one card");
					}
					int epc_length = buf[5] - 2;
					byte[] pc = new byte[2];
					byte[] epc = new byte[epc_length];
					System.arraycopy(buf, 6, pc, 0, 2);
					System.arraycopy(buf, 8, epc, 0, epc_length);
					int ch_i = ((buf[1] >> 4) & 0xf);
					int ch_q = (buf[1] & 0xf);
					int freq = btoi(buf[2]) + (btoi(buf[3]) << 8)
							+ (btoi(buf[4]) << 16);
					cx.add(new Tag_Data_Rssi(new Tag_Data(pc, epc), freq, ch_i,
							ch_q));
					card_num--;
				} else {
					read_ok = false;
					return null;
				}
			} while (card_num > 0);
		}
		return cx;
	}

	public int select_card(byte[] epc) {
		byte[] cmd = new byte[3 + epc.length];
		cmd[0] = 0x33;
		cmd[1] = (byte) (cmd.length);
		cmd[2] = (byte) epc.length;
		System.arraycopy(epc, 0, cmd, 3, epc.length);
		int reval = try_write(fd, wrap(cmd));
		if (reval < 0) {
			return -1;
		}
		synchronized (lock) {
			try {
				lock.wait(100);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			if (read_ok && (buf.length >= 1) && buf[0] == 0) {
				read_ok = false;
				return 0;
			} else {
				if (buf.length >= 1) {
					Log.e("as3992", "selsect card " + read_ok + " error "
							+ buf[0]);
				} else {
					Log.e("as3992", "selsect card " + read_ok);
				}
				read_ok = false;
				return -1;
			}
		}
	}

	public byte[] read_area(int area, int addr, int count) {
		byte[] cmd = new byte[5];
		cmd[0] = 0x37;
		cmd[1] = 5;
		cmd[2] = (byte) area;
		cmd[3] = (byte) addr;
		cmd[4] = (byte) count;
		int reval = try_write(fd, wrap(cmd));
		if (reval < 0) {
			return null;
		}
		synchronized (lock) {
			try {
				lock.wait(100);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			if (read_ok && (buf[1] > 0)
					&& (buf.length > 1 ? (count == 0 ? true : buf[1] == count)
							: false) && (count == 0 ? true : buf[0] == 0)) {
				read_ok = false;
				// byte[] res = new byte[buf.length - 2];
				Log.i("as3992_native", "readed " + buf[1]);
				if ((count == 0) && (buf[1] == 0)) {
					Log.e("as3992_native", "try read more faild");
					return null;
				}
				if (buf.length < (2 + buf[1] * 2)) {
					Log.e("as3992_native", "read_area don't get enough data");
					return null;
				}
				byte[] res = new byte[buf[1] * 2];
				System.arraycopy(buf, 2, res, 0, res.length);
				return res;
			} else {
				if (buf.length >= 2) {
					Log.e("as3992", "read area failed " + read_ok + " error "
							+ buf[0] + " readed number " + buf[1]);
				} else {
					Log.e("as3992", "read area failed " + read_ok + " error");
				}
				read_ok = false;
				return null;
			}
		}
	}

//	public int write_area(int area, int addr, int count, byte[] passwd,
//			byte[] content) {
//		if (count * 2 != content.length) {
//			return -1;
//		}
//		byte[] cmd = new byte[9 + content.length];
//		cmd[0] = 0x35;
//		cmd[1] = (byte) cmd.length;
//		cmd[2] = (byte) area;
//		cmd[3] = (byte) addr;
//		System.arraycopy(passwd, 0, cmd, 4, passwd.length);
//		cmd[8] = (byte) count;
//		System.arraycopy(content, 0, cmd, 9, content.length);
//		for (int s = 0; s < cmd.length; s++) {
//			Log.d("as3992", "cmd " + s + ": " + cmd[s]);
//		}
//		int reval = try_write(fd, wrap(cmd));
//		if (reval < 0) {
//			return -1;
//		}
//		synchronized (lock) {
//			try {
//				lock.wait(100);
//			} catch (InterruptedException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
//			// if(read_ok /*&& buf[0] == 0 */&& buf[1] == count)
//			if (read_ok && (buf.length >= 2) && buf[0] == 0) {
//				read_ok = false;
//				Log.i("as3992_native", "writed " + buf[1] + " words");
//				return buf[1];
//			} else {
//				if (buf.length >= 2) {
//					Log.e("as3992_native", "wrtie area failed " + read_ok
//							+ " error " + buf[0] + " write ok : write number "
//							+ buf[1]);
//				} else {
//					Log.e("as3992_native", "wrtie area failed " + read_ok);
//				}
//				read_ok = false;
//				return -1;
//			}
//		}
//	}

	public int write_area(int area, int addr, int count, byte[] passwd,
			byte[] content) {
		if (count * 2 != content.length) {
			return -1;
		}
//		byte[] cmd = new byte[9 + content.length];
		byte[] cmd = new byte[11];
		cmd[0] = 0x35;
		cmd[1] = (byte) cmd.length;
		cmd[2] = (byte) area;
//		cmd[3] = (byte) addr;
		System.arraycopy(passwd, 0, cmd, 4, passwd.length);
		cmd[8] = 1;
		int rts = 0;
		for(int i = 0; i < count; )
		{
			cmd[3] = (byte) (addr + i);
			System.arraycopy(content, i * 2, cmd, 9, 2);
//				Log.d("as3992", "cmd " + s + ": " + cmd[s]);
			int reval = try_write(fd, wrap(cmd));
			if (reval < 0) {
				return -1;
			}
			synchronized (lock) {
				try {
					lock.wait(100);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				// if(read_ok /*&& buf[0] == 0 */&& buf[1] == count)
				if (read_ok && (buf.length >= 2) && buf[0] == 0) {
					read_ok = false;
					Log.i("as3992_native", "writed " + buf[1] + " words");
					i++;
					rts = 0;
				} else {
					if (buf.length >= 2) {
						Log.e("as3992_native", "wrtie area failed " + read_ok
								+ " error " + buf[0] + " write ok : write number "
								+ buf[1]);
					} else {
						Log.e("as3992_native", "wrtie area failed " + read_ok);
					}
					read_ok = false;
					rts++;
					if(rts == 10)
					{
						Log.e("as3992_debug", "only write " + i + " words");
						return i;
					}
				}
			}
		}
		return count;
	}

	public int set_freq(int start, int stop, int increment, int rssi, int id) {
		if (increment <= 0) {
			Log.e("as3993", "inc value can't be 0");
			return -1;
		}
		byte mode = 8;
		byte[] cmd = new byte[8];
		cmd[0] = 0x41;
		cmd[1] = (byte) cmd.length;
		cmd[6] = (byte) rssi;
		cmd[7] = (byte) id;
		for (int freq = start; freq <= stop; freq += increment) {
			cmd[2] = mode;
			cmd[3] = (byte) freq;
			cmd[4] = (byte) (freq >> 8);
			cmd[5] = (byte) (freq >> 16);
			int reval = try_write(fd, wrap(cmd));
			if (reval < 0) {
				Log.e("as3992", "write set freq cmd failed in " + freq);
				return -1;
			}
			synchronized (lock) {
				try {
					lock.wait(100);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				if (read_ok && (buf.length >= 1) && buf[0] != 0) {
					Log.d("as3992", "set freq " + freq + " ok");
					read_ok = false;
				} else {
					Log.e("as3992", "set freq failed or wait too long time "
							+ freq);
					if (buf != null && (buf.length >= 1) && (buf[0] == 0)) {
						Log.e("as3992", "freq list is full");
						return -2;
					}
					read_ok = false;
					return -1;
				}
			}
			mode = 4;
		}
		return 0;
	}

	public int set_alloc_param(int listentime, int allocationtime, int idletime) {
		byte[] cmd = new byte[9];
		cmd[0] = 0x41;
		cmd[1] = (byte) cmd.length;
		cmd[2] = 0x10;
		cmd[3] = (byte) listentime;
		cmd[4] = (byte) (listentime >> 8);
		cmd[5] = (byte) allocationtime;
		cmd[6] = (byte) (allocationtime >> 8);
		cmd[7] = (byte) idletime;
		cmd[8] = (byte) (idletime >> 8);
		int reval = try_write(fd, wrap(cmd));
		if (reval < 0) {
			return -1;
		}
		synchronized (lock) {
			try {
				lock.wait(100);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			if (read_ok) {
				read_ok = false;
				return 0;
			} else {
				return -1;
			}
		}
	}

	public class Freq_Msg {
		Freq_Msg(int n_start, int n_stop, int n_inc, int n_rssi, int n_id,
				int n_listentime, int n_allocationtime, int n_idletime) {
			start = n_start;
			stop = n_stop;
			inc = n_inc;
			rssi = n_rssi;
			id = n_id;
			listentime = n_listentime;
			allocationtime = n_allocationtime;
			idletime = n_idletime;
		}

		public int start;
		public int stop;
		public int inc;
		public int rssi;
		public int id;
		public int listentime;
		public int allocationtime;
		public int idletime;
	}

	public Freq_Msg get_freq() {
		byte[] cmd = { 0x41, 0x3, 0x11 };
		int reval = try_write(fd, wrap(cmd));
		if (reval < 0) {
			Log.e("as3992", "write get freq cmd failed");
			return null;
		}
		synchronized (lock) {
			try {
				lock.wait(100);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			if (read_ok && (buf.length >= 18)) {
				Log.d("as3992", "get freq ok");
				Log.i("as3392_native", "number of freq " + buf[15]);
				int start = btoi(buf[9]) + (btoi(buf[10]) << 8)
						+ (btoi(buf[11]) << 16);
				int stop = btoi(buf[12]) + (btoi(buf[13]) << 8)
						+ (btoi(buf[14]) << 16);
				int inc;
				if (btoi(buf[15]) == 1) {
					inc = 0;
				} else {
					inc = (stop - start) / (btoi(buf[15]) - 1);
				}
				int rssi = buf[16];
				int id = buf[2];
				int listentime = btoi(buf[3]) + (btoi(buf[4]) << 8);
				int allocationtime = btoi(buf[5]) + (btoi(buf[6]) << 8);
				int idletime = btoi(buf[7]) + (btoi(buf[8]) << 8);
				Log.i("as3992_natvie", "start " + start + " stop " + stop
						+ " inc " + inc + " rssi " + rssi + " listen "
						+ listentime + " alloc " + allocationtime + " idle "
						+ idletime);
				read_ok = false;
				return new Freq_Msg(start, stop, inc, rssi, id, listentime,
						allocationtime, idletime);
			} else {
				Log.e("as3992", "get freq failed " + read_ok + " buflength "
						+ buf.length);
				read_ok = false;
				return null;
			}
		}
	}

	public int set_gen2(int which, byte data) {
		Log.v("as3992_temp", "" + data);
		if (which > 5 || which < 0) {
			return -1;
		}
		byte[] cmd = new byte[14];
		for (int s = 0; s < cmd.length; s++) {
			cmd[s] = 0;
		}
		cmd[0] = 0x59;
		cmd[1] = (byte) cmd.length;
		cmd[2 + which * 2] = 1;
		cmd[2 + which * 2 + 1] = data;
		int reval = try_write(fd, wrap(cmd));
		if (reval < 0) {
			Log.e("as3992", "write set sens cmd failed");
			return -1;
		}
		synchronized (lock) {
			try {
				lock.wait(100);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			if (read_ok) {
				Log.d("as3992", "set sens ok");
				read_ok = false;
				return 0;
			} else {
				Log.e("as3992", "set sens failed");
				return -1;
			}
		}
	}

	public short get_gen2(int which) {
		if (which > 5 || which < 0) {
			return 1000;
		}
		byte[] cmd = new byte[14];
		for (int s = 0; s < cmd.length; s++) {
			cmd[s] = 0;
		}
		cmd[0] = 0x59;
		cmd[1] = (byte) cmd.length;
		int reval = try_write(fd, wrap(cmd));
		if (reval < 0) {
			Log.e("as3992", "write get sens failed");
			return -1;
		}
		synchronized (lock) {
			try {
				lock.wait(100);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			if (read_ok && buf.length >= 12) {
				Log.d("as3992", "get sens ok");
				read_ok = false;
				Log.v("as3992_temp", "" + buf[which * 2 + 1]);
				return buf[which * 2 + 1];
			} else {
				Log.e("as3992", "get sens failed " + read_ok + " buf length "
						+ buf.length);
				read_ok = false;
				return 1000;
			}
		}
	}

	public byte[] get_reg(int reg) {
		byte[] cmd = { 0x1c, 0x3, (byte) reg };
		int reval = try_write(fd, wrap(cmd));
		if (reval < 0) {
			return null;
		}
		synchronized (lock) {
			try {
				lock.wait(100);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			if (read_ok) {
				read_ok = false;
				byte[] res = buf;
				return res;
			} else {
				return null;
			}
		}
	}

	public int set_reg(int reg, byte[] content) {
		if (content.length > 3) {
			return -1;
		}
		byte[] cmd = new byte[3 + content.length];
		cmd[0] = 0x1a;
		cmd[1] = (byte) cmd.length;
		cmd[2] = (byte) reg;
		System.arraycopy(content, 0, cmd, 3, content.length);
		int reval = try_write(fd, wrap(cmd));
		if (reval < 0) {
			return -1;
		}
		synchronized (lock) {
			try {
				lock.wait(100);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			if (read_ok) {
				read_ok = false;
				return 0;
			} else {
				return -1;
			}
		}
	}

	public int set_antenna_power(boolean power) {
		byte[] cmd = new byte[3];
		cmd[0] = 0x18;
		cmd[1] = (byte) cmd.length;
		cmd[2] = (byte) (power ? 0xff : 0);
		int reval = try_write(fd, wrap(cmd));
		if (reval < 0) {
			return -1;
		}
		synchronized (lock) {
			try {
				lock.wait(100);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			if (read_ok) {
				read_ok = false;
				return 0;
			} else {
				return -1;
			}
		}
	}

	public byte[] get_version(int which) {
		byte[] cmd = new byte[3];
		cmd[0] = 0x10;
		cmd[1] = (byte) cmd.length;
		cmd[2] = (byte) which;
		int reval = try_write(fd, wrap(cmd));
		if (reval < 0) {
			Log.e(TAG, "write get_ver cmd failed");
			return null;
		}
		synchronized (lock) {
			try {
				lock.wait(100);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			if (read_ok) {
				read_ok = false;
				byte[] res = buf;
				Log.i(TAG, "get vaild ver data");
				return res;
			} else {
				Log.e(TAG, "time out or no valid ver data");
				return null;
			}
		}
	}

	public byte[] get_all_reg() {
		byte[] cmd = { 0x57, 2 };
		int reval = try_write(fd, wrap(cmd));
		if (reval < 0) {
			return null;
		}
		synchronized (lock) {
			try {
				lock.wait(100);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			if (read_ok && (buf.length >= 41)) {
				read_ok = false;
				byte[] res = buf;
				return res;
			} else {
				Log.e("as3992", "get all reg " + read_ok + " buf length is "
						+ buf.length);
				read_ok = false;
				return null;
			}
		}
	}

	public int[] get_freq_rssi_level(int freq) {
		byte[] cmd = new byte[6];
		cmd[0] = 0x41;
		cmd[1] = (byte) cmd.length;
		cmd[2] = 1;
		cmd[3] = (byte) freq;
		cmd[4] = (byte) (freq >> 8);
		cmd[5] = (byte) (freq >> 16);
		int reval = try_write(fd, wrap(cmd));
		if (reval < 0) {
			return null;
		}
		synchronized (lock) {
			try {
				lock.wait(100);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			if (read_ok && (buf.length >= 3)) {
				read_ok = false;
				int[] res = new int[3];
				res[0] = buf[0];
				res[1] = buf[1];
				res[2] = buf[2];
				return res;
			} else {
				Log.e("as3992", "get freq rssi " + read_ok + " buf length is "
						+ buf.length);
				read_ok = false;
				return null;
			}
		}
	}

	public int[] get_freq_reflect_level(int freq) {
		byte[] cmd = new byte[6];
		cmd[0] = 0x41;
		cmd[1] = (byte) cmd.length;
		cmd[2] = 2;
		cmd[3] = (byte) freq;
		cmd[4] = (byte) (freq >> 8);
		cmd[5] = (byte) (freq >> 16);
		int reval = try_write(fd, wrap(cmd));
		if (reval < 0) {
			return null;
		}
		synchronized (lock) {
			try {
				lock.wait(100);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			if (read_ok && (buf.length >= 2)) {
				read_ok = false;
				int[] res = new int[2];
				res[0] = buf[0];
				res[1] = buf[1];
				return res;
			} else {
				Log.e("as3992", "get freq rssi " + read_ok + " buf length is "
						+ buf.length);
				read_ok = false;
				return null;
			}
		}
	}

	private native int open_serial(String port);

	private native void close_serial(int fd);

	private native int try_write(int fd, byte[] buf);

	private native byte[] try_read(int fd, int count, int delay);

	private native void drop_data(int fd);

	static {
		System.loadLibrary("package");
		System.loadLibrary("uhfrfid");
	}
}