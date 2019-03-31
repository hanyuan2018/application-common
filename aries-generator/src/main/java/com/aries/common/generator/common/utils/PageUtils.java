package com.aries.common.generator.common.utils;

import java.io.Serializable;
import java.util.List;

/**
 * 分页工具类
 * 
 * @author hanp
 *
 */
public class PageUtils implements Serializable{

	private static final long serialVersionUID = 1L;
	// 数据总条数
	private int totalCount = 0;
	// 每页条数
	private int pageSize = 10;
	// 总页数
	private int totalPage = 1;
	// 上一页
	private int prePage = 1;
	// 当前页
	private int currPage = 1;
	// 下一页
	private int nextPage = 1;
	// 列表数据
	private List<?> result;
	
	public PageUtils(int totalCount, int pageSize, int currPage, List<?> result) {
		
		int totalPage = getTotalPage(totalCount, pageSize);
		
		this.totalCount = totalCount;
		this.pageSize = pageSize;
		this.totalPage = totalPage;
		this.prePage = getPrePage(currPage);
		this.currPage = currPage;
		this.nextPage = getNextPage(totalPage, currPage);
		this.result = result;
	}
	
	/** setters and getters  -- start  **/
	public int getTotalCount() {
		return totalCount;
	}

	public void setTotalCount(int totalCount) {
		this.totalCount = totalCount;
	}

	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	public int getTotalPage() {
		return totalPage;
	}

	public void setTotalPage(int totalPage) {
		this.totalPage = totalPage;
	}

	public int getPrePage() {
		return prePage;
	}

	public void setPrePage(int prePage) {
		this.prePage = prePage;
	}

	public int getCurrPage() {
		return currPage;
	}

	public void setCurrPage(int currPage) {
		this.currPage = currPage;
	}

	public int getNextPage() {
		return nextPage;
	}

	public void setNextPage(int nextPage) {
		this.nextPage = nextPage;
	}

	public List<?> getResult() {
		return result;
	}

	public void setResult(List<?> result) {
		this.result = result;
	}
	/** setters and getters  -- end  **/
	
	//获取总页数
	public int getTotalPage(int totalCount, int pageSize) {
		// 向上取整，获取总页数
		return (int) Math.ceil((double)totalCount/pageSize);
	}
	//获取上一页数
	public int getPrePage(int currPage) {
		return currPage == 1 || currPage == 0 ? 1 : currPage - 1 ;
	}
	//获取下一页数
	public int getNextPage(int totalPage, int currPage) {
		return currPage == totalPage && totalPage != 0 ? currPage : currPage + 1 ;
	}
}
