package com.ruoyi.justForTest;

import java.io.File;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.attribute.BasicFileAttributes;
import java.nio.file.attribute.FileTime;
import java.util.List;
import java.util.Arrays;

public class DeleteOldFilesAndFolders {

    public static void main(String[] args) {
        // 设置要检查的路径
        List<String> folderPaths = Arrays.asList(
                "/path/to/first/directory",  // 第一个路径 - 处理文件夹
                "/path/to/second/directory"  // 第二个路径 - 处理文件夹
        );

        List<String> filePaths = Arrays.asList(
                "/path/to/third/directory",  // 第三个路径 - 处理文件
                "/path/to/fourth/directory"  // 第四个路径 - 处理文件
        );

        // 获取当前日期和一个月前的日期
        LocalDateTime currentDateTime = LocalDateTime.now();
        LocalDateTime oneMonthAgo = currentDateTime.minusMonths(1);
        LocalDate oneMonthAgoDate = oneMonthAgo.toLocalDate();

        System.out.println("当前日期时间: " + currentDateTime);
        System.out.println("一个月前的日期时间: " + oneMonthAgo);
        System.out.println("将删除日期早于 " + oneMonthAgoDate.format(DateTimeFormatter.ofPattern("yyyyMMdd")) + " 的文件夹和文件");

        // 创建日期格式化器
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd");

        // 记录统计信息
        int totalFoldersProcessed = 0;
        int successfullyDeletedFolders = 0;
        int failedToDeleteFolders = 0;

        int totalFilesProcessed = 0;
        int successfullyDeletedFiles = 0;
        int failedToDeleteFiles = 0;

        // 第一部分：处理文件夹路径
        System.out.println("\n==================== 处理文件夹 ====================");

        for (String directoryPath : folderPaths) {
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
                            if (folderDate.isBefore(oneMonthAgoDate)) {
                                totalFoldersProcessed++;
                                System.out.println("删除文件夹: " + file.getAbsolutePath());

                                // 删除文件夹及其内容
                                try {
                                    // deleteFolderWithErrorHandling(file);
                                    System.out.println("（删除代码已注释，仅模拟删除）");
                                    successfullyDeletedFolders++;
                                } catch (Exception e) {
                                    failedToDeleteFolders++;
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

        // 第二部分：处理文件路径
        System.out.println("\n==================== 处理文件 ====================");

        for (String directoryPath : filePaths) {
            System.out.println("\n处理目录: " + directoryPath);

            // 获取目录下的所有文件
            File directory = new File(directoryPath);

            // 检查目录是否存在
            if (!directory.exists() || !directory.isDirectory()) {
                System.err.println("错误: 目录不存在或不是一个有效的目录: " + directoryPath);
                continue;
            }

            File[] files = directory.listFiles();

            if (files != null) {
                // 获取系统时区
                ZoneId systemZone = ZoneId.systemDefault();
                for (File file : files) {
                    if (file.isFile()) {  // 只处理文件，不处理子文件夹
                        try {
                            // 获取文件的创建时间
                            Path filePath = file.toPath();
                            BasicFileAttributes attrs = Files.readAttributes(filePath, BasicFileAttributes.class);
                            FileTime lastModifiedTime = attrs.lastModifiedTime();

                            // 将FileTime转换为LocalDateTime
                            LocalDateTime fileLastModifiedDateTime = LocalDateTime.ofInstant(
                                    lastModifiedTime.toInstant(), systemZone);

                            // 比较文件创建时间是否早于一个月前
                            if (fileLastModifiedDateTime.isBefore(oneMonthAgo)) {
                                totalFilesProcessed++;
                                System.out.println("删除文件: " + file.getAbsolutePath() +
                                        " (最后修改时间: " + fileLastModifiedDateTime + ")");

                                // 删除文件
                                try {
                                    // if (file.delete()) {
                                    //     successfullyDeletedFiles++;
                                    // } else {
                                    //     failedToDeleteFiles++;
                                    //     System.err.println("无法删除文件: " + file.getAbsolutePath());
                                    // }
                                    System.out.println("（删除代码已注释，仅模拟删除）");
                                    successfullyDeletedFiles++;
                                } catch (Exception e) {
                                    failedToDeleteFiles++;
                                    System.err.println("删除文件 " + file.getAbsolutePath() + " 时出错: " + e.getMessage());
                                    System.err.println("跳过此文件，继续处理其他文件");
                                }
                            }
                        } catch (IOException e) {
                            System.err.println("获取文件属性时出错: " + file.getAbsolutePath() + " - " + e.getMessage());
                        }
                    }
                }
            } else {
                System.err.println("无法列出目录内容: " + directoryPath);
            }
        }

        // 打印统计信息
        System.out.println("\n==================== 执行统计 ====================");
        System.out.println("文件夹删除统计:");
        System.out.println("总共需要处理的文件夹数: " + totalFoldersProcessed);
        System.out.println("成功删除的文件夹数: " + successfullyDeletedFolders);
        System.out.println("删除失败的文件夹数: " + failedToDeleteFolders);

        System.out.println("\n文件删除统计:");
        System.out.println("总共需要处理的文件数: " + totalFilesProcessed);
        System.out.println("成功删除的文件数: " + successfullyDeletedFiles);
        System.out.println("删除失败的文件数: " + failedToDeleteFiles);

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
