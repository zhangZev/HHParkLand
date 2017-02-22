package com.henghao.parkland.utils;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class DeviceControl {
	private final BufferedWriter CtrlFile;

	public DeviceControl(String path) throws IOException {
		File DeviceName = new File(path);
		this.CtrlFile = new BufferedWriter(new FileWriter(DeviceName, false));	// open file
	}

	public void PowerOnDevice() throws IOException		// poweron as3992 device
	{
		this.CtrlFile.write("-wdout94 1");
		this.CtrlFile.flush();
	}

	public void PowerOffDevice() throws IOException	// poweroff as3992 device
	{
		this.CtrlFile.write("-wdout94 0");
		this.CtrlFile.flush();
	}

	public void DeviceClose() throws IOException		// close file
	{
		this.CtrlFile.close();
	}
}
