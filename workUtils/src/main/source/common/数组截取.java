package main.source.common;
public class 数组截取{
 
 public static void main(String[] args){
  char[] arr1 = new char[]{'a','b','c','d','e','f','g','h','i','j','k'};
  //调用库中的copy方法
  char[] arr2 = java.util.Arrays.copyOf(arr1,5);
  printArray(arr1);
  printArray(arr2);
 }

//打印数组方法
 public static void printArray(char[] arr1){
  for(int i = 0;i < arr1.length;i++){
   System.out.print(arr1[i]+"\t");
  }
  System.out.println();
 }
}