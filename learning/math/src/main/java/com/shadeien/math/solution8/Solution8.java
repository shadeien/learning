package com.shadeien.math.solution8;

import lombok.extern.slf4j.Slf4j;

/**
 * 炸弹人
 */
@Slf4j
public class Solution8 {

    public static void main(String[] args) {
        char[][] map = {
                {'#', '#','#','#','#','#','#','#','#','#','#','#','#'},
                {'#', 'G','G','.','G','G','G','#','G','G','G','.','#'},
                {'#', '#','#','.','#','G','#','G','#','G','#','G','#'},
                {'#', '.','.','.','.','.','.','.','#','.','.','G','#'},
                {'#', 'G','#','.','#','#','#','.','#','G','#','G','#'},
                {'#', 'G','G','.','G','G','G','.','#','.','G','G','#'},
                {'#', 'G','#','.','#','G','#','.','#','.','#','#','#'},
                {'#', '#','G','.','.','.','G','.','.','.','.','.','#'},
                {'#', 'G','#','.','#','G','#','#','#','.','#','G','#'},
                {'#', '.','.','.','G','#','G','G','G','.','G','G','#'},
                {'#', 'G','#','.','#','G','#','G','#','.','#','G','#'},
                {'#', 'G','G','.','G','G','G','#','G','.','G','G','#'},
                {'#', '#','#','#','#','#','#','#','#','#','#','#','#'}
        };
        int max = 0;
        int px=0,py=0;
        for (int i=0; i < map.length; i++) {
            for (int j=0; j< map[i].length; j++) {
                int sum = 0;
                char current = map[i][j];
                if (current == '.') {
                    int x=i,y=j;
                    while (map[x][y] != '#') {
                        if (map[x][y] == 'G') {
                            sum++;
                        }
                        x--;
                    }
                    x=i;
                    y=j;
                    while (map[x][y] != '#') {
                        if (map[x][y] == 'G') {
                            sum++;
                        }
                        x++;
                    }
                    x=i;
                    y=j;
                    while (map[x][y] != '#') {
                        if (map[x][y] == 'G') {
                            sum++;
                        }
                        y++;
                    }
                    x=i;
                    y=j;
                    while (map[x][y] != '#') {
                        if (map[x][y] == 'G') {
                            sum++;
                        }
                        y--;
                    }
                }

                if (sum > max) {
                    max = sum;
                    px = i;
                    py = j;
                }
            }
        }

        log.info("max:{}, px:{}, py{}", max, px, py);

    }

}
