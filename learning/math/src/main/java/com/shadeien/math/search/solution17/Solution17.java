package com.shadeien.math.search.solution17;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class Solution17 {
    public static void main(String[] args) {
        char[] pre = {'A','B','D','G','H','C','E','I','F'};
        char[] mid = {'G','D','H','B','A','I','E','C','F'};
        char[] end = {'G','H','D','B','I','E','F','C','A'};
        char[] level = {'A','B','C','D','E','F','G','H','I'};


        Node node = buildTreeByPreMid(pre, 0, pre.length - 1, mid, 0, mid.length - 1);
//        Node node = buildTreeByMidEnd(mid, 0, mid.length - 1, end, 0, end.length - 1);
//        Node node = buildTreeByMidLevel(mid, level, 0, mid.length-1);
        log.info("{}", readPre(node));
        log.info("{}", readMid(node));
        log.info("{}", readEnd(node));
    }

    /**
     * 前序遍历
     */
    public static String readPre(Node node) {
        StringBuilder result = new StringBuilder();
        result.append(node.getValue()); //前序遍历
        if (node.getLeft() != null) {
            result.append(readPre(node.getLeft()));
        }
        if (node.getRight() != null) {
            result.append(readPre(node.getRight()));
        }
        return result.toString();
    }

    /**
     * 中序遍历
     */
    public static String readMid(Node node) {
        StringBuilder result = new StringBuilder();
        if (node.getLeft() != null) {
            result.append(readMid(node.getLeft()));
        }
        result.append(node.getValue()); //中序遍历
        if (node.getRight() != null) {
            result.append(readMid(node.getRight()));
        }
        return result.toString();
    }

    /**
     * 后序遍历
     */
    public static String readEnd(Node node) {
        StringBuilder result = new StringBuilder();
        if (node.getLeft() != null) {
            result.append(readEnd(node.getLeft()));
        }
        if (node.getRight() != null) {
            result.append(readEnd(node.getRight()));
        }
        result.append(node.getValue()); //后序遍历
        return result.toString();
    }

    /**
     * 根据层序遍历和中序遍历得到结果
     * @return
     */
    private static Node buildTreeByMidLevel(char[] mid, char[] level, int midBegin, int midEnd) {
        Node root = new Node();
        root.setValue(level[0]);

        int midLoc = -1;
        for (int i = midBegin; i <= midEnd; i++) {
            if (mid[i] == level[0]) {
                midLoc = i;
                break;
            }
        }

        if (level.length > 1) {
            if (isLeft(mid, level[0], level[1])) {
                Node left = buildTreeByMidLevel(mid, getLevelStr(mid, midBegin, midLoc - 1, level), midBegin, midLoc - 1);
                root.left = left;
                if (level.length >= 3 && !isLeft(mid, level[0], level[2])) {
                    Node right = buildTreeByMidLevel(mid, getLevelStr(mid, midLoc + 1, midEnd, level), midLoc + 1, midEnd);
                    root.right = right;
                }
            } else {
                Node right = buildTreeByMidLevel(mid, getLevelStr(mid, midLoc + 1, midEnd, level), midLoc + 1, midEnd);
                root.right = right;
            }
        }

        return root;
    }

    /**
     * 将中序序列中midBegin与MidEnd的字符依次从level中提取出来，保持level中的字符顺序不变
     */
    private static char[] getLevelStr(char[] mid, int midBegin, int midEnd, char[] level) {
        char[] result = new char[midEnd - midBegin + 1];
        int curLoc = 0;
        for (int i = 0; i < level.length; i++) {
            if (contains(mid, level[i], midBegin, midEnd)) {
                result[curLoc++] = level[i];
            }
        }
        return result;
    }

    /**
     * 如果str字符串的begin和end位置之间（包括begin和end）含有字符target,则返回true。
     */
    private static boolean contains(char[] str, char target, int begin, int end) {
        for (int i = begin; i <= end; i++) {
            if (str[i] == target) {
                return true;
            }
        }
        return false;
    }

    private static boolean isLeft(char[] src, char target, char next) {
        boolean findC = false;
        for (char temp : src) {
            if (temp == next) {
                findC = true;
            } else if (temp == target) {
                return findC;
            }
        }

        return findC;
    }
        /**
         * 根据后序和中序遍历还原树
         */
    private static Node buildTreeByMidEnd(char[] mid, int midBegin, int midEnd, char[] end, int endBegin, int endEnd) {
        Node root = new Node();
        root.setValue(end[endEnd]);

        int midRootLoc = 0;
        for (int i = midBegin; i <= midEnd; i++) {
            if (mid[i] == root.value) {
                midRootLoc = i;
                break;
            }
        }

        //递归得到左子树
        if (endBegin + (midRootLoc - midBegin) - 1 >= endBegin && midRootLoc-1>=midBegin) {
            Node leftChild = buildTreeByMidEnd(mid, midBegin, midRootLoc-1,
                    end, endBegin, endBegin + (midRootLoc - midBegin) - 1);
            root.left = leftChild;
        }

        //递归得到右子树
        if (midRootLoc+1<= midEnd && endEnd - (midEnd - midRootLoc) <= endEnd - 1) {
            Node rightChild = buildTreeByMidEnd(mid, midRootLoc+1, midEnd,
                    end, endEnd - (midEnd - midRootLoc), endEnd - 1);
            root.right = rightChild;
        }

        return root;
    }

        /**
         * 根据前序和中序排序表获取树
         */
    private static Node buildTreeByPreMid(char[] pre, int preBegin, int preEnd, char[] mid, int midBegin, int midEnd) {
        Node root = new Node();
        root.setValue(pre[preBegin]);

        int midRootLoc = 0;
        for (int i = midBegin; i <= midEnd; i++) {
            if (mid[i] == pre[preBegin]) {
                midRootLoc = i;
                break;
            }
        }

        //递归得到左子树
        if (preBegin + (midRootLoc - midBegin) >= preBegin + 1 && (midRootLoc - 1) >= midBegin) {
            Node leftChild = buildTreeByPreMid(pre, preBegin + 1, preBegin + (midRootLoc - midBegin),
                    mid, midBegin, midRootLoc - 1);
            root.left = leftChild;
        }

        //递归得到右子树
        if (preEnd >= (preEnd - (midEnd - midRootLoc) + 1) && (midEnd >= midRootLoc + 1)) {
            Node rightChild = buildTreeByPreMid(pre, preEnd - (midEnd - midRootLoc) + 1, preEnd,
                    mid, midRootLoc + 1, midEnd);
            root.right = rightChild;
        }


        return root;
    }


        @Data
    static class Node {
        char value;
        Node left;
        Node right;
    }

}
