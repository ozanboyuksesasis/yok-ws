package com.sesasis.donusum.yok.core.utils;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class TreeUtils {


    public  static List<TreeNode> Nodes(TreeNode node, List<TreeNode> rootList){
        List<TreeNode> treeNodeList = new ArrayList<>();
        List<TreeNode> nodeList=new ArrayList<>();
        if(node!=null){
            nodeList=rootList.stream().filter(x->x.getParentId()!=null ).filter(x->x.getParentId().equals(node.getId())).collect(Collectors.toList());
        }
        if(node==null){
            nodeList=rootList.stream().filter(x->x.getParentId()==null).collect(Collectors.toList());
        }

        for (TreeNode nodeTree : nodeList) {

            TreeNode treeNode = new TreeNode();
            treeNode.setFileSystemNode(nodeTree);
            treeNodeList.add(treeNode);
            List<TreeNode> parentKlasorList=Nodes(nodeTree,rootList);
            treeNode.setChildren(parentKlasorList);
        }

        return treeNodeList;
    }

}

