package com.ruoyi.justForTest;

import java.io.File;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.io.IOException;
import java.util.List;
import java.util.Arrays;

public class DeleteOldFolders {

    public static void main(String[] args) {
        // 设置要检查的两个目录路径
        List<String> directoryPaths = Arrays.asList(
                "/Users/eyue/Desktop/demoProjects/TestDelete/pathxhx",  // 请替换为第一个实际路径
                "/Users/eyue/Desktop/demoProjects/TestDelete/pathfbs"  // 请替换为第二个实际路径
        );

        // 获取当前日期
        LocalDate currentDate = LocalDate.now();
        // 计算一个月前的日期
        LocalDate oneMonthAgo = currentDate.minusMonths(1);

        System.out.println("当前日期: " + currentDate);
        System.out.println("一个月前: " + oneMonthAgo);
        System.out.println("将删除日期早于 " + oneMonthAgo.format(DateTimeFormatter.ofPattern("yyyyMMdd")) + " 的文件夹");

        // 创建日期格式化器
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd");

        // 记录成功和失败的统计信息
        int totalFoldersProcessed = 0;
        int successfullyDeleted = 0;
        int failedToDelete = 0;

        // 处理每个目录
        for (String directoryPath : directoryPaths) {
            System.out.println("\n处理目录: " + directoryPath);

            // 获取目录下的所有文件和文件夹
            File directory = new File(directoryPath);

            // 检查目录是否存在
            if (!directory.exists() || !directory.isDirectory()) {
                System.err.println("错误: 目录不存在或不是一个有效的目录: " + directoryPath);
                continue;
            }

            File[] files = directory.listFiles();

            if (files != null) {
                for (File file : files) {
                    if (file.isDirectory()) {
                        String folderName = file.getName();

                        // 检查文件夹名是否为日期格式
                        try {
                            // 尝试将文件夹名解析为日期
                            LocalDate folderDate = LocalDate.parse(folderName, formatter);

                            // 比较文件夹日期和一个月前的日期
                            if (folderDate.isBefore(oneMonthAgo)) {
                                totalFoldersProcessed++;
                                System.out.println("删除文件夹: " + file.getAbsolutePath());

                                // 删除文件夹及其内容
                                try {
                                    deleteFolderWithErrorHandling(file);
//                                    System.out.println("（删除代码已注释，仅模拟删除）");
                                    successfullyDeleted++;
                                } catch (Exception e) {
                                    failedToDelete++;
                                    System.err.println("删除文件夹 " + file.getAbsolutePath() + " 时出错: " + e.getMessage());
                                    System.err.println("跳过此文件夹，继续处理其他文件夹");
                                }
                            }
                        } catch (DateTimeParseException e) {
                            // 不是日期格式的文件夹名，跳过
                            System.out.println("跳过非日期格式文件夹: " + folderName);
                        }
                    }
                }
            } else {
                System.err.println("无法列出目录内容: " + directoryPath);
            }
        }

        // 打印统计信息
        System.out.println("\n执行统计:");
        System.out.println("总共需要处理的文件夹数: " + totalFoldersProcessed);
        System.out.println("成功删除的文件夹数: " + successfullyDeleted);
        System.out.println("删除失败的文件夹数: " + failedToDelete);
        System.out.println("\n操作完成");
    }

    /**
     * 递归删除文件夹及其内容，遇到错误时会抛出异常
     */
    private static void deleteFolderWithErrorHandling(File folder) throws IOException {
        if (!folder.exists()) {
            return;
        }

        // 获取文件夹内的所有内容
        File[] files = folder.listFiles();

        if (files != null) {
            for (File file : files) {
                try {
                    if (file.isDirectory()) {
                        // 递归删除子文件夹
                        deleteFolderWithErrorHandling(file);
                    } else {
                        // 删除文件
                        if (!file.delete()) {
                            System.err.println("警告: 无法删除文件: " + file.getAbsolutePath() + "，将继续尝试删除其他文件");
                        }
                    }
                } catch (Exception e) {
                    // 记录错误但继续处理其他文件
                    System.err.println("删除 " + file.getAbsolutePath() + " 时出错: " + e.getMessage() + "，将继续尝试删除其他文件");
                }
            }
        }

        // 尝试删除空文件夹
        try {
            if (!folder.delete()) {
                throw new IOException("无法删除文件夹: " + folder.getAbsolutePath());
            }
        } catch (Exception e) {
            throw new IOException("删除文件夹 " + folder.getAbsolutePath() + " 时出错: " + e.getMessage(), e);
        }
    }
}
