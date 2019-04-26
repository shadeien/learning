package com.shadeien.math.solution5;

import lombok.extern.slf4j.Slf4j;

import java.util.Stack;

@Slf4j
public class Solution5 {

    public static void main(String[] args) {
        String str = "ahaha";

        Stack stack = new Stack();
        int next,mid=str.length()/2-1;
        for (int i=0; i<=mid; i++) {
            stack.push(str.charAt(i));
        }
        if(str.length()%2==0)
            next=mid+1;
        else
            next=mid+2;
        for (int i=next; i<str.length();i++) {
            if (str.charAt(i) != (char)stack.pop()) {
                break;
            }
        }
        if (stack.isEmpty()) {
            log.info("true");
        } else
            log.info("false");
    }

    public static void stack(String str) {
        int next,mid=str.length()/2-1;

        char[] stacks = new char[mid+1];
        int top = 0;
        for (int i=0; i<=mid; i++) {
            stacks[top++] = str.charAt(i);
        }
        if(str.length()%2==0)
            next=mid+1;
        else
            next=mid+2;
        for (int i=next; i<str.length();i++) {
            top--;
            if (str.charAt(i) != stacks[top]) {
                break;
            }
        }
        if (0 == top) {
            log.info("true");
        } else
            log.info("false");
    }

}
