/*
 * Copyright (c) 2018  �Ͼ���h�Ƽ����޹�˾. All Rights Reserved.
 */ 
package main.source.common.random;

import java.security.SecureRandom;
import java.util.Arrays;

/** 
 * @description �˽�SecureRandom����
 * @author ������
 * @date 2018-08-29 
 */
public class SecureRandomDemo {

    public static void main(String[] args) throws Exception {
        //ͨ��new����ʼ����Ĭ����˵��ʹ��NativePRNG�㷨���������
        SecureRandom generater = new SecureRandom(); 
        System.out.println(generater.nextInt(37));//���[0~37)������
        /*
         * ����ͨ��getInstance����ʼ������:
         * ������Ҫ���εķ���,���㷨������,����������㷨���׳��쳣;
         * ������Ҫ����,������������,�ڶ�������������ָ���㷨�������
         */
        SecureRandom secureRandom3 = SecureRandom.getInstance("SHA1PRNG");
        SecureRandom secureRandom2 = SecureRandom.getInstance("SHA1PRNG", "SUN");
        
        /*
         * ��������  nextBytes(byte[] bytes)
         * Typical callers of SecureRandom invoke the following methods to retrieve random bytes: 
         * ���Ի�ȡ�����һ��byte����,ע�����ﲻ�Ƿ���,���������void��������,��ֱ������ı���test
         */
        SecureRandom random = new SecureRandom();
        byte[] test = new byte[20];
        random.nextBytes(test);
        System.out.println(Arrays.toString(test));
        
        /*
         * generateSeed(int numBytes)
         * ͨ��,Ҳ����ʹ��generateSeed����,����ȡһ�������byte����,
         * ��������е���ͨ�������������������������������
         */
        byte seed[] = random.generateSeed(20);//�������һ�ֶ��巽��
        System.out.println(Arrays.toString(seed));
    }
}
/*
 * 1.����֪��Random����ʵ�ֵ�����㷨��α���,Ҳ�����й�������.�ڽ������ʱ,����㷨����Դ���ֳ�Ϊ������(seed),
 * ���������Ļ����Ͻ���һ���ı任,�Ӷ�������Ҫ���������.
 * ��ͬ��������Random����,��ͬ�������ɵ������������ȫ��ͬ��.Ҳ����˵,������������ͬ��Random����,���ɵ����������ȫ��ͬ.
 * ��������ҪƵ�����������,���߰�ȫҪ��ϸߵ�ʱ��,��Ҫʹ��Random,��Ϊ�����ɵ�ֵ��ʵ�ǿ���Ԥ���.
 * 
 * 2.SecureRandom���ṩ���ܵ�ǿ����������� (RNG)
 * ��Ȼ,�������ʵ�ֶ���α����������� (PRNG) ��ʽ,����ζ�����ǽ�ʹ��ȷ�����㷨����ʵ�ʵ������������α�������
 * Ҳ������ʵ�ֿ�������ʵ�ʵ������
 * ������һЩʵ������ܽ��ʹ���������
 * 
 * 3.SecureRandom��Random����,Ҳ���������һ��,�����������Ҳһ��:��Ϊ����ȷ��,������㷨Ҳȷ��,��������ȷ���ġ�
 * ֻ��˵,SecureRandom���ռ���һЩ����¼�,���������,���̵���ȵ�,SecureRandom ʹ����Щ����¼���Ϊ���ӡ�
 * ����ζ��,�����ǲ���Ԥ���,������RandomĬ��ʹ��ϵͳ��ǰʱ��ĺ�������Ϊ����,�й��ɿ�Ѱ��
 * 
 * 3������ ��������seed��ȡ˼·
 * 
 * 4.������ǿ�ȵ������,��������Ҫ������:���Ӻ��㷨.��Ȼ�㷨�ǿ����кܶ��,
 *   �������ѡ�������Ƿǳ��ؼ�������.
 *   ��Random,����������System.currentTimeMillis(),����������������ǿ�Ԥ���.
 *   ��Ԥ����ʲôΣ����,���Կ���������:
 *   ������ϵͳʱ���Ԥ���ƽ�java�������
 *   ��������������:һ�������˿���Ϸ�Ľ�ѵ��
 *   ��ô��εõ�һ���������������?������һ��˼·:
 *   �ռ�������ĸ�����Ϣ,���������ʱ��,CPUʱ��,�ڴ�ʹ��״̬,Ӳ�̿��пռ�,IO��ʱ,
 *   ��������,�߳���������Ϣ,���õ�һ���������������
 *   �����Ļ�,�������������ƽ�Ŀ���,ʵ���ϻ���û�б��ƽ�Ŀ���.����ʵ��,
 *   ���ڵĸ�ǿ�ȵ��������������������ʵ�ֵ�
 */