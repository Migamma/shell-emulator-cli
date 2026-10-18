package org.example.vfs;

import java.util.Collection;
import java.util.Map;
import java.util.TreeMap;

public class VfsDirectory extends VfsNode {
    private final Map<String, VfsNode> children = new TreeMap<>();

    public VfsDirectory(String name){
        super(name);
    }

    public void addChild(VfsNode child){
        children.put(child.getName(), child);
        child.setParent(this);
    }

    public VfsNode getChild(String name){
        return children.get(name);
    }

    public Collection<VfsNode> getChildren(){
        return children.values();
    }
}
