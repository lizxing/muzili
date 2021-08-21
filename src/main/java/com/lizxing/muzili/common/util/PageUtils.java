package com.lizxing.muzili.common.util;

import com.baomidou.mybatisplus.core.metadata.IPage;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * 分页工具类
 * @author lizxing
 */
public class PageUtils {

	@Data
	private static class PageData {
		/**
		 * 总记录数
		 */
		private int totalCount;
		/**
		 * 每页记录数
		 */
		private int pageSize;
		/**
		 * 总页数
		 */
		private int totalPage;
		/**
		 * 当前页数
		 */
		private int currPage;
		/**
		 * 列表数据
		 */
		private List<?> list;
	}

	/**
	 * 数据处理
	 */
	public static PageData getData(IPage<?> page) {
		PageData pageData = new PageData();
		pageData.setList(page.getRecords());
		pageData.setTotalCount((int) page.getTotal());
		pageData.setPageSize((int)page.getSize());
		pageData.setCurrPage((int)page.getCurrent());
		pageData.setTotalPage((int)page.getPages());
		return pageData;
	}

}
