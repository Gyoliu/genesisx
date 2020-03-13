package com.genesis.x;

import com.alibaba.fastjson.JSON;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @Author: liuxing
 * @Date: 2019/10/9 9:18
 * @Description:
 */
public class TestSX {

    /**
     * 99乘法表
     */
    @Test
    public void test01(){
        for (int i = 1; i < 10; i++) {
            for (int j = 1; j <= i; j++) {
                System.out.print("" + i + " x " + j + " = " + (i*j) + "    ");
            }
            System.out.println();
        }
    }

    private void permute(char[] str, int low, ArrayList<String> list){
        // 递归终止条件
        if(low == str.length-1){
            String result = String.valueOf(str);
            if(!list.contains(result)){
                list.add(result);
            }
        } else {
            for(int j = low;j < str.length;j++){
                swap(low,j,str);//依次选一个数固定住
                permute(str, low+1,list);//让后面的进行全排列
                swap(low,j,str);//恢复原来的模样，回溯关键
            }
        }

    }

    public static void swap(int i,int j,char[] cha){
        char temp=cha[i];
        cha[i]=cha[j];
        cha[j]=temp;
    }

    /**
     * 找出字符串 所有的排列
     */
    @Test
    public void test02(){
        String str = "abca";
        char[] chars = str.toCharArray();
        ArrayList<String> list = new ArrayList<>();
        this.permute(chars, 0, list);
        list.forEach(x -> System.out.println(x));
    }

    /**
     * 求最大值
     */
    @Test
    public void test03(){
        int[] arr = new int[]{55,23,2,31,56,88,42};
        int maxnum = 0;
        int index = 0;
        for (int i = 0; i < arr.length; i++) {
            if(maxnum < arr[i]){
                maxnum = arr[i];
                index = i;
            }
        }
        System.out.println(maxnum);
        System.out.println(index);
    }

    /**
     * 求两个集合的并集
     */
    @Test
    public void test04(){
        int[] arr1 = new int[]{1,2,3,4,5,6};
        int[] arr2 = new int[]{6,7,8,9,10};
        for (int i = 0; i < arr1.length; i++) {
            for (int i1 = 0; i1 < arr2.length; i1++) {
                if(arr1[i] == arr2[i1]){
                    System.out.println(arr1[i]);
                }
            }
        }
    }

    public static void game(List<Integer> persons, int distance){
        int i = 0;
        while(persons.size()>1){
            //按照循环方式对表进行遍历
            //由于是环，所以对i值做如下处理
            i = (i+distance-1)%persons.size();
            //处决
            System.out.println("删除"+persons.remove(i).toString()+"，");
        }
    }

    @Test
    public void test05(){
        int[] arr1 = new int[]{1,2,3,4,5};
        List<Integer> integers = new ArrayList<>();
        Arrays.stream(arr1).forEach(x -> integers.add(x));
        int c =  1;
        game(integers, c);
    }


    /**
     * 快速排序
     */
    @Test
    public void test06(){
        int[] arr = {10,7,2,4,7,62,3,4,2,1,8,9,19};
        quickSort(arr, 0, arr.length - 1);
        System.out.println(JSON.toJSONString(arr));
    }

    public static void quickSort(int[] arr,int low,int high){
        int i,j,temp, t;
        if(low > high){
            return;
        }
        i = low;
        j = high;
        temp = arr[low];
        while(i < j){
            //先看右边，依次往左递减
            while (temp <= arr[j] && i < j) {
                j--;
            }
            //再看左边，依次往右递增
            while (temp >= arr[i] && i < j) {
                i++;
            }
            //如果满足条件则交换
            if (i<j) {
                t = arr[j];
                arr[j] = arr[i];
                arr[i] = t;
            }
        }

        //最后将基准为与i和j相等位置的数字交换
        arr[low] = arr[i];
        arr[i] = temp;
        //递归调用左半数组
        quickSort(arr, low, j-1);
        //递归调用右半数组
        quickSort(arr, j+1, high);

    }


}