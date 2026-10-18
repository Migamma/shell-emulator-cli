package org.example.vfs;

public abstract class VfsNode {
    private final String name;
    private VfsDirectory parent;

    public VfsNode(String name){
        this.name = name;
    }

    public String getName(){
        return name;
    }

    public VfsDirectory getParent(){
        return parent;
    }

    void setParent(VfsDirectory parent){
        this.parent = parent;
    }
}
