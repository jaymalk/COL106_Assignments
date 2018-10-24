public class AVLTree {
    Node root;

    private class Node {
        private Position key;
        private Node left, right, parent;

        public Node(Position key, Node parent) {
            this.key = key;
            this.parent = parent;
        }

        public boolean isExternal() {
            return (left == null) && (right == null);
        }

        public boolean isInternal() {
            return (left != null) && (right != null);
        }

        public boolean hasLeft() {
            return left != null;
        }

        public Node getLeft() {
            return left;
        }

        public void setLeft(Node newLeft) {
            this.left = newLeft;
        }

        public boolean isLeftChild() {
            Node parent = getParent();
            if(parent != null)
                if(parent.hasLeft())
                    return parent.getLeft().equals(this);
            return false;
        }

        public boolean hasRight() {
            return right != null;
        }

        public Node getRight() {
            return right;
        }

        public void setRight(Node newRight) {
            this.right = newRight;
        }

        public boolean isRightChild() {
            Node parent = getParent();
            if(parent != null)
                if(parent.hasRight())
                    return parent.getRight().equals(this);
            return false;
        }

        public Node getParent() {
            return parent;
        }

        public void setParent(Node newParent) {
            this.parent = newParent;
        }

        public Position key() {
            return this.key;
        }

        protected void changeKey(Position key) {
            this.key = key;
        }

        public boolean equals(Node node) {
            return node.key().compareTo(this.key()) == 0;
        }

        @Override
        public String toString() {
            return String.format("Key: "+key());
        }
    }

    // CONSTRUCTORS
    public AVLTree() {
        root = null;
    }

    public AVLTree(Node root) {
        this.root = root;
    }

    // AVL Structural Constraint Check

    public void AVLCheck(Node checkNode) {
        if(checkNode == null)
            return;
        if(Math.abs(nodeHeight(checkNode.getLeft()) - nodeHeight(checkNode.getRight())) <= 1) { // BALANCED
            AVLCheck(checkNode.getParent());
            return;
        }
        if(nodeHeight(checkNode.getLeft()) - nodeHeight(checkNode.getRight()) > 1) {    // LEFT HEAVY
            if(nodeHeight(checkNode.getLeft().getLeft()) > nodeHeight(checkNode.getLeft().getRight())) {
                Node x = checkNode;
                rightRotate(x);
            }
            else {
                Node y = checkNode.getLeft();
                leftRotate(y);
                rightRotate(checkNode);
            }
        }
        else {                                                                          // RIGHT HEAVY
            if(nodeHeight(checkNode.getRight().getRight()) > nodeHeight(checkNode.getRight().getLeft())) {
                Node x = checkNode;
                leftRotate(x);
            }
            else {
                Node y = checkNode.getRight();
                rightRotate(y);
                leftRotate(checkNode);
            }
        }
    }

    private void leftRotate(Node parent) {
        Node rightChild = parent.getRight();
        rightChild.setParent(parent.getParent());
        if(parent.getParent()!=null) {
            if(parent.isLeftChild())
                parent.getParent().setLeft(rightChild);
            else
                parent.getParent().setRight(rightChild);
        }
        parent.setRight(rightChild.getLeft());
        if(rightChild.hasLeft())
            rightChild.getLeft().setParent(parent);
        parent.setParent(rightChild);
        rightChild.setLeft(parent);
    }

    private void rightRotate(Node parent) {
        Node leftChild = parent.getLeft();
        leftChild.setParent(parent.getParent());
        if(parent.getParent()!=null) {
            if(parent.isLeftChild())
                parent.getParent().setLeft(leftChild);
            else
                parent.getParent().setRight(leftChild);
        }
        parent.setLeft(leftChild.getRight());
        if(leftChild.hasRight())
            leftChild.getRight().setParent(parent);
        parent.setParent(leftChild);
        leftChild.setRight(parent);
    }

    // NODE RELATED OPERATIONS
    public Node getRoot() {
        return this.root;
    }

    public Position getRootKey(){
        return getRoot().key();
    }

    private int nodeHeight(Node node) {
        if(node == null)
            return -1;
        if(node.isExternal())
            return 0;
        else
            return 1+Math.max(nodeHeight(node.getLeft()), nodeHeight(node.getRight()));
    }

    public void nodeHeight(Position key) {
        if(contains(key)) {
            System.out.println(nodeHeight(getNode(key)));
        }
        else {
            System.out.println("Node with key: "+key+" doesn't exist in the tree");
        }
    }

    private int nodeDepth(Node node) {
        if(node == null)
            return -1;
        return 1+nodeDepth(node.getParent());
    }

    public void nodeDepth(Position key) {
        if(contains(key)) {
            System.out.println(nodeDepth(getNode(key)));
        }
        else {
            System.out.println("Node with key: "+key+" doesn't exist in the tree");
        }
    }

    public void addNewElement(Position key) {
        try {
            addItem(key);
        }
        catch(Exception e) {
            System.out.println("Node already exists. Repetition not allowed.");
        }
        setNewRoot();
    }

    private void addItem(Position key) {
        if(contains(key))
            throw new IllegalArgumentException("Node repetition not allowed.");
        if(isEmpty()) {
            root = new Node(key, null);
        }
        else if(getRoot().key().compareTo(key) >= 0) { // root.key() >= key
            if(getRoot().hasLeft())
                leftSubTree().addItem(key);
            else {
                getRoot().setLeft(new Node(key, getRoot()));
                AVLCheck(getRoot());
            }
        }
        else {                                          // root.key() <= key
            if(getRoot().hasRight())
                rightSubTree().addItem(key);
            else {
                getRoot().setRight(new Node(key, getRoot()));
                AVLCheck(getRoot());
            }
        }
    }

    public void deleteItem(Position key) {
        try {
            Node node = getNode(key);
            deleteItem(node);
            setNewRoot();
        }
        catch(Exception e) {
            System.out.println("No such node in the tree");
        }
    }

    private void deleteItem(Node node) {
        if(node.isExternal()) {
            if(node.getParent() != null) {
                if(node.isLeftChild())
                    node.getParent().setLeft(null);
                else
                    node.getParent().setRight(null);
                AVLCheck(node.getParent());
            }
            else
                root = null;
        }
        else if(node.isInternal()) {
            try {
                Node temp = getInorderPredecessor(node);
                Node succ = getInorderSuccessor(node);
                if(nodeDepth(succ) > nodeDepth(temp))
                    temp = succ;
                node.changeKey(temp.key());
                deleteItem(temp);
            }
            catch(Exception e) {
                System.out.println("This condition should never come.");
            }
        }
        else {
            if(node.hasLeft()) {
                node.getLeft().setParent(node.getParent());
                if(node.getParent() != null)
                    if(node.isLeftChild())
                        node.getParent().setLeft(node.getLeft());
                    else
                        node.getParent().setRight(node.getLeft());
            }
            else {
                node.getRight().setParent(node.getParent());
                if(node.getParent() != null)
                    if(node.isLeftChild())
                        node.getParent().setLeft(node.getRight());
                    else
                        node.getParent().setRight(node.getRight());
            }
            AVLCheck(node.getParent());
        }
    }

    public boolean contains(Position key) {
        if(isEmpty())
            return false;
        if(root.key().equals(key))
            return true;
        try {
            return (leftSubTree().contains(key) || rightSubTree().contains(key));
        }
        catch(Exception e) {
            return false;
        }
    }

    public Node getNode(Position key) {
        if(contains(key)) {
            if(root.key().equals(key))
                return root;
            else if(root.hasLeft())
                if(leftSubTree().contains(key))
                    return leftSubTree().getNode(key);
            return rightSubTree().getNode(key);
        }
        throw new NullPointerException("No node with key "+key+" present in the tree.");
    }

    // NODE ACCESS OPERATIONS
    public Node getInorderSuccessor(Position key) throws Exception{
        if(contains(key)) {
            Node temp = getNode(key);
            return getInorderSuccessor(temp);
        }
        throw new NullPointerException("No such node present in the tree.");
    }

    public Node getInorderSuccessor(Node givenNode) throws Exception {
        if(givenNode.hasRight()) {
            givenNode = givenNode.getRight();
            while(givenNode.hasLeft())
                givenNode = givenNode.getLeft();
            return givenNode;
        }
        else {
            Node ancestor = givenNode.getParent();
            while ((ancestor != null) && (givenNode.isRightChild())) {
                givenNode = ancestor;
                ancestor = givenNode.getParent();
            }
            if (ancestor == null)
                throw new NullPointerException("The element is the largest in the tree.");
            return ancestor;
        }
    }

    public Node getInorderPredecessor(Position key) throws Exception {
        if(contains(key)) {
            Node temp = getNode(key);
            return getInorderSuccessor(temp);
        }
        throw new NullPointerException("No such node present in the tree.");
    }

    public Node getInorderPredecessor(Node givenNode) throws Exception {
        if(givenNode.hasLeft()) {
            givenNode = givenNode.getLeft();
            while(givenNode.hasRight())
                givenNode = givenNode.getRight();
            return givenNode;
        }
        else {
            Node ancestor = givenNode.getParent();
            while ((ancestor != null) && (givenNode.isLeftChild())) {
                givenNode = ancestor;
                ancestor = givenNode.getParent();
            }
            if (ancestor == null)
                throw new NullPointerException("The element is the smallest in the tree.");
            return ancestor;
        }
    }

    public void getRange(Position key) {
        if(!contains(key)) {
            if(isEmpty()) {
                System.out.println("-iNF & Inf");
                return;
            }
            if(getRoot().key().compareTo(key) == 1) {
                if(!getRoot().hasLeft()) {
                    try {
                        System.out.print(getSuperTree().getInorderPredecessor(getRoot()).key());
                    }
                    catch(Exception e) {
                        System.out.print("-iNF");
                    }
                    System.out.println(" & " + getRoot().key());

                }
                else {
                    leftSubTree().getRange(key);
                }
            }
            if(getRoot().key().compareTo(key) == -1) {
                if(!getRoot().hasRight()) {
                    System.out.print(getRoot().key()+" & ");
                    try {
                        System.out.println(getSuperTree().getInorderSuccessor(getRoot()).key());
                    }
                    catch(Exception e) {
                        System.out.println("Inf");
                    }
                }
                else {
                    rightSubTree().getRange(key);
                }
            }
            return;
        }
        throw new IllegalArgumentException("Key already exists in the tree.");
    }

    // TREE RELATED OPERATIONS
    public boolean isEmpty() {
        return root == null;
    }

    public int totalNodes() {
        if(isEmpty())
            return 0;
        return 1+leftSubTree().totalNodes()+rightSubTree().totalNodes();
    }

    public int treeHeight() {
        return nodeHeight(root);
    }

    public void getMin() {
        if(isEmpty())
            return;
        if(!root.hasLeft())
            System.out.println(root);
        else
            leftSubTree().getMin();
    }

    public void getMax() {
        if(isEmpty())
            return;
        if(!root.hasRight())
            System.out.println(root);
        else
            rightSubTree().getMax();
    }

    public AVLTree leftSubTree() {
        return new AVLTree(getRoot().getLeft());
    }

    public AVLTree rightSubTree() {
        return new AVLTree(getRoot().getRight());
    }

    public AVLTree getSuperTree() {
        Node temp = getRoot();
        while(temp.getParent() != null)
            temp = temp.getParent();
        return new AVLTree(temp);
    }

    public boolean isSuperTree() {
        return root.getParent() == null;
    }

    public void clearTree() {
        if(isSuperTree())
            root = null;
        else
            System.out.println("Tree is a subtree. I am not allowed to clear it.");
    }

    // PRINTING THE TREE
    public void Inorder(int... param) {
        if(isEmpty())
            return;
        int space = 0;
        if(param.length == 1)
            space = param[0];
        leftSubTree().Inorder(space+2);
        for(int i=0; i<space; i++)
            System.out.print("     ");
        System.out.println("("+root.key()+")");
        rightSubTree().Inorder(space+2);
    }

    private void setNewRoot() {
        while(root.getParent()!=null)
            root = root.getParent();
    }
}
