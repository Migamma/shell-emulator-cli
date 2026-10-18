package org.example.vfs;

public class VirtualFileSystem {
    private final VfsDirectory root;
    private VfsDirectory current;

    public VirtualFileSystem(VfsDirectory root){
        this.root = root;
        this.current = root;
    }

    public VfsDirectory getCurrent() {
        return current;
    }

    public VfsDirectory getRoot() {
        return root;
    }

    public void setCurrent(VfsDirectory current) {
        this.current = current;
    }

    public VfsNode resolve(String path){
        VfsNode node;
        if (path.startsWith("/")){
            node = root;
        } else{
            node = current;
        }
        for (String part : path.split("/")){
            if (part.isEmpty() || part.equals(".")){
                continue;
            }
            if (part.equals("..")){
                if (node.getParent() != null){
                    node = node.getParent();
                }
                continue;
            }
            if (!(node instanceof VfsDirectory)){
                return null;
            }
            node = ((VfsDirectory) node).getChild(part);
            if (node == null){
                return null;
            }
        }
        return node;
    }

    public String getPath(VfsNode node){
        if (node == root){
            return "/";
        }
        StringBuilder path = new StringBuilder();
        while (node.getParent() != null){
            path.insert(0, "/" + node.getName());
            node = node.getParent();
        }
        return path.toString();
    }
}

