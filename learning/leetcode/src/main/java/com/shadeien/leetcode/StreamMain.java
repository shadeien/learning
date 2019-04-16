package com.shadeien.leetcode;

import lombok.extern.slf4j.Slf4j;

import java.io.*;
import java.net.URI;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

@Slf4j
public class StreamMain {

    public static void main(String[] args) throws Exception {
        int target = 10;
        int[] dp = new int[target];
        Arrays.fill(dp, 1);
        for (int i = 1; i < target; i++)
            for (int j = 0; j < i; j++)
                dp[i] += dp[j];
        System.out.println(dp[target - 1]);

        System.out.println(Math.pow(2,target-1));

        String filePath = "D:\\wwwareWorkspace\\UserCenter\\usercenter-dao\\src\\main\\java\\com\\wwwarehouse\\xdw\\usercenter\\dao\\mapper\\AuUserCardAuthorityDOMapper.xml";
        File srcFile = new File(filePath);

//        InputStream inputStream = Files.newInputStream(Paths.get(URI.create(filePath)));

        zip1(srcFile);
        zip2(srcFile);
        zip3(srcFile);
        files(filePath);
    }

    public static void files(String filePath) throws IOException {
        String outFilePath = "G://3.txt";
        BufferedWriter writer = Files.newBufferedWriter(Paths.get(URI.create("file:///"+outFilePath)));
        BufferedReader reader = Files.newBufferedReader(Paths.get(URI.create("file:///G://gfwlist.txt")));
        long start = System.currentTimeMillis();
        log.info("{}", start);
        int data = 0;
        while ((data = reader.read()) != -1) {
            writer.write(data);
        }
        log.info("diff:{}", System.currentTimeMillis() - start);
        reader.close();
        writer.close();
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
        bufferedInputStream.close();
        bufferedOutputStream.close();
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
