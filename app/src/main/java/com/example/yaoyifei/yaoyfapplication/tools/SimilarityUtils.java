package com.example.yaoyifei.yaoyfapplication.tools;

public class SimilarityUtils {
    public static void main(String[] args) {
        //要比较的两个字符串
        String str1 = "鞋子不能在实体店买";
        String str2 = "不能在实体店买鞋子";
        levenshtein(str1.toLowerCase(),str2.toLowerCase());
    }
    /**
     *
     * @param str1
     * @param str2
     */
    public static float levenshtein(String str1,String str2) {
        //计算两个字符串的长度。
        int len1 = str1.length();
        int len2 = str2.length();
        //建立上面说的数组，比字符长度大一个空间
        int[][] dif = new int[len1 + 1][len2 + 1];
        //赋初值，步骤B。
        for (int a = 0; a <= len1; a++) {
            dif[a][0] = a;
        }
        for (int a = 0; a <= len2; a++) {
            dif[0][a] = a;
        }
        //计算两个字符是否一样，计算左上的值
        int temp;
        for (int i = 1; i <= len1; i++) {
            for (int j = 1; j <= len2; j++) {
                if (str1.charAt(i - 1) == str2.charAt(j - 1)) {
                    temp = 0;
                } else {
                    temp = 1;
                }
                //取三个值中最小的
                dif[i][j] = min(dif[i - 1][j - 1] + temp, dif[i][j - 1] + 1,
                        dif[i - 1][j] + 1);
            }
        }
       // System.out.println("字符串\""+str1+"\"与\""+str2+"\"的比较");
        //取数组右下角的值，同样不同位置代表不同字符串的比较
        //System.out.println("差异步骤："+dif[len1][len2]);
        //计算相似度
        float similarity =1 - (float) dif[len1][len2] / Math.max(str1.length(), str2.length());
        //System.out.println("相似度："+similarity);
        return similarity;
    }

    //得到最小值
    private static int min(int... is) {
        int min = Integer.MAX_VALUE;
        for (int i : is) {
            if (min > i) {
                min = i;
            }
        }
        return min;
    }

    //BF算法，返回子串ps 在 母串ts中的位置
    public static int Index_BF(String ts,String ps) {
        char[] t = ts.toCharArray();
        char[] p = ps.toCharArray();
        int i=0;
        int j=0;
        //遍历母串
        while(i<t.length) {
            //遍历子串
            while(j<p.length) {
                //子串的字符与母串字符相同
                if(t[i]==p[j]) {
                    i++;
                    j++;
                } else {
                    //子串字符与母串字符不相同，回溯，子串从头开始，母串回到i-j-1位置
                    i=i-j+1;
                    j=0;
                }
                //母串已经遍历完，跳出循环
                if(i==t.length) {
                    break;
                }
            }
            //母串遍历完，子串还未遍历完，跳出循环，返回失败
            if(i==t.length&&j!=p.length) {
                break;
            } else{
                //子串遍历完，母串还未遍历完，查找成功，返回位置
                return i-p.length;
            }
        }
        return -1;
    }
}
