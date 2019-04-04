package com.shadeien.leetcode;

import lombok.extern.slf4j.Slf4j;

import java.io.*;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

@Slf4j
public class StreamMain {

    public static void main(String[] args) throws Exception {
        String filePath = "D:\\wwwareWorkspace\\UserCenter\\usercenter-dao\\src\\main\\java\\com\\wwwarehouse\\xdw\\usercenter\\dao\\mapper\\AuUserCardAuthorityDOMapper.xml";
        File srcFile = new File(filePath);

        zip1(srcFile);
        zip2(srcFile);
        zip3(srcFile);
    }

    public static void zip1(File srcFile) throws Exception {
        String outFilePath = "G:\\1.zip";
        FileOutputStream outFile = new FileOutputStream(outFilePath);

        ZipOutputStream zipOutputStream = new ZipOutputStream(outFile);
        FileInputStream in = new FileInputStream(srcFile);
        ZipEntry zipEntry = new ZipEntry("out");
        zipOutputStream.putNextEntry(zipEntry);
        BufferedInputStream bufferedInputStream = new BufferedInputStream(in);
        BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(zipOutputStream);
        long start = System.currentTimeMillis();
        log.info("{}", start);
        int len = 0;
        while ((len = bufferedInputStream.read()) != -1) {
            bufferedOutputStream.write(len);
        }
        log.info("diff:{}", System.currentTimeMillis() - start);
        in.close();
        bufferedInputStream.close();
        bufferedOutputStream.close();
        zipOutputStream.close();
    }

    public static void zip3(File srcFile) throws Exception {
        String outFilePath = "G:\\3.zip";
        FileOutputStream outFile = new FileOutputStream(outFilePath);

        ZipOutputStream zipOutputStream = new ZipOutputStream(outFile);
        FileInputStream in = new FileInputStream(srcFile);
        ZipEntry zipEntry = new ZipEntry("out");
        zipOutputStream.putNextEntry(zipEntry);
        BufferedInputStream bufferedInputStream = new BufferedInputStream(in);
        long start = System.currentTimeMillis();
        log.info("{}", start);
        int len = 0;
        while ((len = bufferedInputStream.read()) != -1) {
            zipOutputStream.write(len);
        }
        log.info("diff:{}", System.currentTimeMillis() - start);
        in.close();
        bufferedInputStream.close();
        zipOutputStream.close();
    }

    public static void zip2(File srcFile) throws Exception {
        String outFilePath = "G:\\2.zip";
        FileOutputStream outFile = new FileOutputStream(outFilePath);

        ZipOutputStream zipOutputStream = new ZipOutputStream(outFile);
        FileInputStream in = new FileInputStream(srcFile);
        ZipEntry zipEntry = new ZipEntry("out");
        zipOutputStream.putNextEntry(zipEntry);
        long start = System.currentTimeMillis();
        log.info("{}", start);
        int len = 0;
        while ((len = in.read()) != -1) {
            zipOutputStream.write(len);
        }
        log.info("diff:{}", System.currentTimeMillis() - start);
        in.close();
        zipOutputStream.close();
    }
}
