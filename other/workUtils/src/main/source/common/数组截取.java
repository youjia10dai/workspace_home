package main.source.common;
public class �����ȡ{
 
 public static void main(String[] args){
  char[] arr1 = new char[]{'a','b','c','d','e','f','g','h','i','j','k'};
  //���ÿ��е�copy����
  char[] arr2 = java.util.Arrays.copyOf(arr1,5);
  printArray(arr1);
  printArray(arr2);
 }

//��ӡ���鷽��
 public static void printArray(char[] arr1){
  for(int i = 0;i < arr1.length;i++){
   System.out.print(arr1[i]+"\t");
  }
  System.out.println();
 }
}