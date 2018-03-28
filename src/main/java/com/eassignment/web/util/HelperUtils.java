package com.eassignment.web.util;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;

public class HelperUtils {

	
	public static Direction toggleSortDirection(Direction currentDirection,Boolean changeDirection, Boolean ispagination){
		if(currentDirection!=null){
			
			return changeDirection?currentDirection.equals(Direction.ASC)? Direction.DESC:Direction.ASC:ispagination?currentDirection:Sort.Direction.DESC;
			
		}else {
			return Sort.Direction.DESC;
		}
		
	}
	
	public static PageRequest createPageRequest(Model model, Integer page,
			String sortString, String oldSortString, Direction oldDirection, int intialPage, int intialPageSize, String defaultSort ) {
		int evalPage = (page == null || page < 1) ? intialPage : page - 1;
		String futureSortString = !StringUtils.isEmpty(sortString)  ? sortString:!StringUtils.isEmpty(oldSortString)?oldSortString:defaultSort;
		
		Boolean changeDirection = !StringUtils.isEmpty(sortString)&& !StringUtils.isEmpty(oldSortString) && oldSortString.equals(futureSortString);
		Boolean isPagination =StringUtils.isEmpty(sortString)&& !StringUtils.isEmpty(oldSortString);
		
		Direction futureDirection = HelperUtils.toggleSortDirection(oldDirection,changeDirection, isPagination);		
		Sort sort = new Sort(futureDirection,futureSortString);			
		model.addAttribute("oldDirection", futureDirection);
		model.addAttribute("oldSortString", futureSortString);
		PageRequest pageRequest = new PageRequest(evalPage, intialPageSize,sort);
		return pageRequest;
	}

}