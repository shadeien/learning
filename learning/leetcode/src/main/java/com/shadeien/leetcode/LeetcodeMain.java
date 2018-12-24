package com.shadeien.leetcode;

import lombok.extern.slf4j.Slf4j;

import java.util.*;

@Slf4j
public class LeetcodeMain {
    public static void main(String[] args) {
        int i = 0;
        int j = ++i;
        log.info("i:{}, n:{}", i, j);
        i = 0;
        j = i++;
        log.info("i:{}, n:{}", i, j);
        log.info("{}", -16>>>2);
        log.info("{}", -16>>2);
        log.info("{}", ~3);
        int n = 5;
        // 相连n的二进制值不一样，101010101, 可以进一步排除末位=0&倒数第二位=0的情况
        log.info("{}",  ((n + (n >> 1) ) & (n + (n >> 1)+1)) == 0);
        // 单词按照数字增加，第K个字母是啥
        String res = decodeAtIndex("leet2code3", 10);
        log.info("{}", res);

        int[] people = {3,2,2,1};
        // 最少需要几艘船
        log.info("{}", numRescueBoats(people, 3));

        // 找出不同的值
        log.info("{}", uncommonFromSentences("s z z z s", "s z ejt"));

        int[] i1 = {1,2};
        int[] i2 = {2,3};
        int[] i3 = {3,4};
        int[] i5 = {4,5};
        int[] i6 = {1,5};
        int[][] i4 = {i1, i2, i3, i5,i6};
        // 分成两部分，不包含不喜欢的人
        boolean g = possibleBipartition(5, i4);
        log.info("g{}", g);


        int[] i7 = {73,55,36,5,55,14,9,7,72,52};
        // 连续的在L&R之间的数组个数
        numSubarrayBoundedMax(i7, 32, 69);

        int a = numSubarrayBoundedMax(i7, 69) - numSubarrayBoundedMax(i7, 32 - 1);
        log.info("count:{}", a);

        int[] aa={1, 1};
        int[] bb={2, 2};
        // 交换糖果，a出多少，b出多少
        fairCandySwap(aa, bb);

        // A,B之间重复的最大数组
        findLength(new int[]{1,2,3,2,1}, new int[]{3,2,1,4,7});

        // 到达target的最小次数
        int num = reachNumber(2);
        log.info("reach num : {}", num);

        log.info("minMalwareSpread:{}", minMalwareSpread(new int[][]{{1,1,1,0},{1,1,0,0},{1,0,1,0},{0,0,0,1}}, new int[]{3, 2}));
    }

    static Map map = new LinkedHashMap();
    static ArrayList<int[]> arrlist = new ArrayList<>();
    public static int minMalwareSpread(int[][] graph, int[] initial) {
        for (int i = 0; i < graph.length; i++) {
            for (int j = 0; j < graph[i].length; j++) {
                if (graph[i][j] == 1) {
                    arrlist.add(new int[]{i, j});
                } else {
                    arrlist.add(new int[]{-1, -1});
                }
            }
        }
        int finalLastIndex = 0;
        int finalMax = 0;
        for (int i : initial) {
            int[] data = arrlist.get(i);
            if (data[0] == -1)
                continue;
            int lastIndex = 0;
            int max = 0;
            graph(graph, data[0], data[1]);
            Iterator it = map.keySet().iterator();
            while (it.hasNext()) {
                String key = (String) it.next();
                String[] arr = key.split("-");
                int tmp = (int) map.get(key);
                if (tmp > max) {
                    max = tmp;
                    int a = Integer.valueOf(arr[0]);
                    int b = Integer.valueOf(arr[1]);
                    lastIndex = a*graph.length+b;
                }
            }
            if (max > finalMax) {
                finalMax = max;
                finalLastIndex = lastIndex;
            }
        }

        return finalLastIndex;
    }

    public static void graph(int[][] graph, int i, int j) {
        StringBuilder str = new StringBuilder();
        str.append(i);
        str.append("-");
        str.append(j);

        String key = str.toString();
        if (map.containsKey(key)) {
            return;
        }

        if (i-1>=0 && graph[i-1][j]==1) {
            map.put(str.toString(), ((int) map.getOrDefault(key, 1)) + 1);
            graph(graph, i-1, j);
        }
        if (i+1<graph.length && graph[i+1][j]==1) {
            map.put(str.toString(), ((int) map.getOrDefault(key, 1)) + 1);
            graph(graph, i+1, j);
        }
        if (j-1>=0 && graph[i][j-1]==1) {
            map.put(str.toString(), ((int) map.getOrDefault(key, 1)) + 1);
            graph(graph, i, j-1);
        }
        if (j+1<graph[i].length && graph[i][j+1]==1) {
            map.put(str.toString(), ((int) map.getOrDefault(key, 1)) + 1);
            graph(graph, i, j+1);
        }
    }

    static ArrayList<Integer>[] graph;
    static Map<Integer, Integer> color;
    public static boolean possibleBipartition(int N, int[][] dislikes) {
        graph = new ArrayList[N+1];
        for (int i = 1; i <= N; ++i)
            graph[i] = new ArrayList();

        // 构造可以相连的树状结构
        for (int[] edge: dislikes) {
            graph[edge[0]].add(edge[1]);
            graph[edge[1]].add(edge[0]);
        }

        color = new HashMap();
        for (int node = 1; node <= N; ++node)
            if (!color.containsKey(node) && !dfs(node, 0))
                return false;
        return true;

    }

    public static boolean dfs(int node, int c) {
        if (color.containsKey(node))
            return color.get(node) == c;
        color.put(node, c);

        for (int nei: graph[node])
            if (!dfs(nei, c ^ 1))
                return false;
        return true;
    }


    public static int numRescueBoats(int[] people, int limit) {
        Arrays.sort(people);
        int i = 0, j = people.length - 1;
        int ans = 0;

        while (i <= j) {
            ans++;
            if (people[i] + people[j] <= limit)
                i++;
            j--;
        }

        return ans;
    }

    public static String decodeAtIndex(String S, int K) {
        long size = 0;
        int N = S.length();

        // Find size = length of decoded string
        for (int i = 0; i < N; ++i) {
            char c = S.charAt(i);
            if (Character.isDigit(c))
                size *= c - '0';
            else
                size++;
        }

        for (int i = N-1; i >= 0; --i) {
            char c = S.charAt(i);
            K %= size;
            if (K == 0 && Character.isLetter(c))
                return Character.toString(c);

            if (Character.isDigit(c))
                size /= c - '0';
            else
                size--;
        }

        return null;
    }

    public static String[] uncommonFromSentences(String A, String B) {
        Map<String, Integer> count = new HashMap();
        for (String word: A.split(" "))
            count.put(word, count.getOrDefault(word, 0) + 1);
        for (String word: B.split(" "))
            count.put(word, count.getOrDefault(word, 0) + 1);

        List<String> ans = new LinkedList();
        for (String word: count.keySet())
            if (count.get(word) == 1)
                ans.add(word);

        return ans.toArray(new String[ans.size()]);
    }

    public static int[] fairCandySwap(int[] A, int[] B) {
        int totalA = 0;
        int totalB = 0;
        for (int tmp : A) {
            totalA += tmp;
        }
        for (int tmp : B) {
            totalB += tmp;
        }
        int avg = (totalB - totalA) / 2;
        Set<Integer> setB = new HashSet();
        for (int x: B) setB.add(x);

        for (int x: A)
            if (setB.contains(x + avg))
                return new int[]{x, x + avg};
        return null;
    }

    //[73,55,36,5,55,14,9,7,72,52]
    public static int numSubarrayBoundedMax(int[] A, int L, int R) {
        int tempMax ;
        int count = 0 ;
        for(int i=0;i<A.length;i++){
            tempMax = -1 ;
            for(int j=i;j<A.length;j++){
                if(A[j]>tempMax){
                    tempMax = A[j] ;
                }
                if(L<=tempMax&&tempMax<=R){
                    log.info("{}-{}", A[i], A[j]);
                    count++ ;
                }else{
                    if(tempMax>R)break ;
                }
            }
        }

        return count;
    }

    public static int numSubarrayBoundedMax(int[] A, int Max) {
        int res = 0;
        int numSubarry = 0;
        for (int num : A) {
            if (num <= Max) {
                numSubarry++;
                log.info("num:{}， numSubarry:{}", num, numSubarry);
                res += numSubarry;
            } else {
                numSubarry = 0;
            }
        }
        return res;
    }

    public static int findLength(int[] A, int[] B) {
        if(A.length<B.length){
            return findLength(B, A);
        }
        int max=0;
        int[] samecount=new int[A.length];
        //初始化第一行
        for(int i=0;i<A.length;i++){
            samecount[i]=0;
            if(B[0]==A[i]){
                samecount[i]=1;
            }
        }

        for(int i=1;i<B.length;i++){
            int[] tempsamcount=new int[A.length];
            for(int j=0;j<A.length;j++){
                if(A[j]==B[i]){
                    if(j==0){
                        tempsamcount[j]=1;
                    }
                    else{
                        //状态转移
                        tempsamcount[j]=samecount[j-1]+1;
                    }
                    //全局比较
                    max=max>tempsamcount[j]?max:tempsamcount[j];
                }
            }
            //下一次迭代
            samecount=tempsamcount;
        }
        return max;
    }

    public static int reachNumber(int target) {
        target = Math.abs(target);
        int i=0,j=1;
        while(true) {
            i+=j;
            if (i==target || (i>target && (i-target)%2==0)) {
                break;
            }
            j += 1;
        }
        return j;
    }
}
