package com.sesasis.donusum.yok.core.utils;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;


@Data
public class
TreeNode {
	private Long id;

	private String key;

	private String label;

	private String mineType;

	private Long parentId;

	private String hashCode;

	private Long sira;

	private List<TreeNode> children = new ArrayList<>();

	private  Object data;

	public void setFileSystemNode(TreeNode treeNode){
		     String skey=null;
		     if(treeNode.getParentId()==null){
				skey=treeNode.getId().toString()+'-'+treeNode.getId().toString();
			 }else{
				skey=treeNode.getId().toString()+'-'+treeNode.getParentId().toString();
			 }
 		    this.setLabel(treeNode.getLabel());
			this.setSira(treeNode.getSira());
			this.setParentId(treeNode.getParentId());
			this.setId(treeNode.getId());
			this.setKey(skey);

	}

}


