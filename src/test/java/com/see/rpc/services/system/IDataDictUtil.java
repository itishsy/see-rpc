package com.see.rpc.services.system;

import com.see.rpc.entity.DataDict;

import java.util.List;

public interface IDataDictUtil {

	/**
	 * 根据数据字典类型代码获得数据字典列表
	 * @param dataType
	 * 数据字典类型代码
	 * @return
	 */
    List<DataDict> getDictlistByTypeCode(String dataType);

	/**
	 * 在数据字典列表中 取名称
	 * @param dictlist
	 * @param dictCode
	 * 数据字典类型代码
	 * @return
	 */
	String getDictNameByCode(List<DataDict> dictlist, String dictCode);


	/**
	 * 根据数据字典类型代码 取名称
	 * @param dataType
	 * 数据字典类型代码
	 * @return
	 */
	String getDictNameByTypeAndCode(String dataType, String dictCode);
	/**
	 * 根据数据字典类型代码及逗号分开的code获取逗号分开名称 
	 * @param dataType
	 * @param dictCodes
	 * @return
	 */
    String getDictNamesByTypeAndCodes(String dataType, String dictCodes);

	/**
	 * 根据省份ID获取所有地区
	 * @return
	 */
    List<DataDict> getCountysByCityId(Integer cityId);

	/**
	 * 获取个性化信息
	 * @return
	 */
    List<DataDict> getPersonalDetail();

}



