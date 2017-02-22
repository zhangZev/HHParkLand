/*
 * 文件名：TreeEntity.java
 * 版权：Copyright 2009-2010 companyName MediaNet. Co. Ltd. All Rights Reserved.
 * 描述：
 * 修改人：
 * 修改时间：
 * 跟踪单号：
 * 修改单号：
 * 修改内容：
 */
package com.henghao.parkland.model.entity;

import com.henghao.parkland.model.IdEntity;

/**
 * 〈一句话功能简述〉
 * 〈功能详细描述〉
 * @author zhangxianwen
 * @version HDMNV100R001, 2016-11-18
 * @see [相关类/方法]
 * @since [产品/模块版本]
 */
public class TreeEntity extends IdEntity {

	private String treeName;

	private String nfcId;

	private boolean isCheck;

	/**
	 * @return the greenMianji
	 */
	public String getGreenMianji() {
		return this.greenMianji;
	}

	/**
	 * @param greenMianji the greenMianji to set
	 */
	public void setGreenMianji(String greenMianji) {
		this.greenMianji = greenMianji;
	}

	private String greenMianji;

	/**
	 * @return the nfcId
	 */
	public String getNfcId() {
		return this.nfcId;
	}

	/**
	 * @return the isCheck
	 */
	public boolean isCheck() {
		return this.isCheck;
	}

	/**
	 * @param isCheck the isCheck to set
	 */
	public void setCheck(boolean isCheck) {
		this.isCheck = isCheck;
	}

	/**
	 * @param nfcId the nfcId to set
	 */
	public void setNfcId(String nfcId) {
		this.nfcId = nfcId;
	}

	/**
	 * 未 检
	 */
	private int treenot;

	private int allTree;

	/**
	 * nfcNo
	 */
	private String nfcNo;

	private double lat;

	private double lng;

	/**
	 * 介绍
	 */
	private String treeIntroduce;

	private String treeNianLing;

	private String treeZhiJing;

	private String treeLocation;

	private String treeChandi;

	private String treeHeight;
	private String treeJK;

	/**
	 * 属于
	 */
	private String treeAlong;
	private String suoshu;
	private String fuzeren;
	private String miaomu;

	/**
	 * @return the suoshu
	 */
	public String getSuoshu() {
		return this.suoshu;
	}

	/**
	 * @param suoshu the suoshu to set
	 */
	public void setSuoshu(String suoshu) {
		this.suoshu = suoshu;
	}

	/**
	 * @return the fuzeren
	 */
	public String getFuzeren() {
		return this.fuzeren;
	}

	/**
	 * @param fuzeren the fuzeren to set
	 */
	public void setFuzeren(String fuzeren) {
		this.fuzeren = fuzeren;
	}

	/**
	 * @return the miaomu
	 */
	public String getMiaomu() {
		return this.miaomu;
	}

	/**
	 * @param miaomu the miaomu to set
	 */
	public void setMiaomu(String miaomu) {
		this.miaomu = miaomu;
	}

	/**
	 * 分布区域
	 */
	private String treeAddress;

	private int imageResouse;

	/**
	 * @return the treeName
	 */
	public String getTreeName() {
		return this.treeName;
	}

	/**
	 * @param treeName the treeName to set
	 */
	public void setTreeName(String treeName) {
		this.treeName = treeName;
	}

	/**
	 * @return the treenot
	 */
	public int getTreenot() {
		return this.treenot;
	}

	/**
	 * @param treenot the treenot to set
	 */
	public void setTreenot(int treenot) {
		this.treenot = treenot;
	}

	/**
	 * @return the allTree
	 */
	public int getAllTree() {
		return this.allTree;
	}

	/**
	 * @param allTree the allTree to set
	 */
	public void setAllTree(int allTree) {
		this.allTree = allTree;
	}

	/**
	 * @return the treeIntroduce
	 */
	public String getTreeIntroduce() {
		return this.treeIntroduce;
	}

	/**
	 * @param treeIntroduce the treeIntroduce to set
	 */
	public void setTreeIntroduce(String treeIntroduce) {
		this.treeIntroduce = treeIntroduce;
	}

	/**
	 * @return the treeAlong
	 */
	public String getTreeAlong() {
		return this.treeAlong;
	}

	/**
	 * @param treeAlong the treeAlong to set
	 */
	public void setTreeAlong(String treeAlong) {
		this.treeAlong = treeAlong;
	}

	/**
	 * @return the treeAddress
	 */
	public String getTreeAddress() {
		return this.treeAddress;
	}

	/**
	 * @param treeAddress the treeAddress to set
	 */
	public void setTreeAddress(String treeAddress) {
		this.treeAddress = treeAddress;
	}

	/**
	 * @return the imageResouse
	 */
	public int getImageResouse() {
		return this.imageResouse;
	}

	/**
	 * @param imageResouse the imageResouse to set
	 */
	public void setImageResouse(int imageResouse) {
		this.imageResouse = imageResouse;
	}

	/**
	 * @return the nfcNo
	 */
	public String getNfcNo() {
		return this.nfcNo;
	}

	/**
	 * @param nfcNo the nfcNo to set
	 */
	public void setNfcNo(String nfcNo) {
		this.nfcNo = nfcNo;
	}

	/**
	 * @return the lat
	 */
	public double getLat() {
		return this.lat;
	}

	/**
	 * @param lat the lat to set
	 */
	public void setLat(double lat) {
		this.lat = lat;
	}

	/**
	 * @return the lng
	 */
	public double getLng() {
		return this.lng;
	}

	/**
	 * @param lng the lng to set
	 */
	public void setLng(double lng) {
		this.lng = lng;
	}

	/**
	 * @return the treeNianLing
	 */
	public String getTreeNianLing() {
		return this.treeNianLing;
	}

	/**
	 * @param treeNianLing the treeNianLing to set
	 */
	public void setTreeNianLing(String treeNianLing) {
		this.treeNianLing = treeNianLing;
	}

	/**
	 * @return the treeZhiJing
	 */
	public String getTreeZhiJing() {
		return this.treeZhiJing;
	}

	/**
	 * @param treeZhiJing the treeZhiJing to set
	 */
	public void setTreeZhiJing(String treeZhiJing) {
		this.treeZhiJing = treeZhiJing;
	}

	/**
	 * @return the treeLocation
	 */
	public String getTreeLocation() {
		return this.treeLocation;
	}

	/**
	 * @param treeLocation the treeLocation to set
	 */
	public void setTreeLocation(String treeLocation) {
		this.treeLocation = treeLocation;
	}

	/**
	 * @return the treeChandi
	 */
	public String getTreeChandi() {
		return this.treeChandi;
	}

	/**
	 * @param treeChandi the treeChandi to set
	 */
	public void setTreeChandi(String treeChandi) {
		this.treeChandi = treeChandi;
	}

	/**
	 * @return the treeHeight
	 */
	public String getTreeHeight() {
		return this.treeHeight;
	}

	/**
	 * @param treeHeight the treeHeight to set
	 */
	public void setTreeHeight(String treeHeight) {
		this.treeHeight = treeHeight;
	}

	/**
	 * @return the treeJK
	 */
	public String getTreeJK() {
		return this.treeJK;
	}

	/**
	 * @param treeJK the treeJK to set
	 */
	public void setTreeJK(String treeJK) {
		this.treeJK = treeJK;
	}

}
