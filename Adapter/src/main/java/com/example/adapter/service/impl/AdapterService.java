//package com.example.adapter.service.impl;
//
//import com.example.adapter.scheduler.AdapterScheduler;
//import org.springframework.scheduling.annotation.EnableScheduling;
//import org.springframework.stereotype.Service;
//
//@Service
//public class AdapterService {
//
//
////    public AdapterService(AdapterScheduler adapterScheduler) {
////
////        adapterScheduler.execute();
////
////    }
//
//    //    private static final String FAILED_FOLDER = "D:\\springboot-rabbitMQ demo\\FileParsing\\Adapter\\src\\main\\resources\\Failed";
////    private static final String PROCESSING_FOLDER = "D:\\springboot-rabbitMQ demo\\FileParsing\\Adapter\\src\\main\\resources\\Processing";
////
////
////
////    @SneakyThrows
////    public String loadFileData() {
//////        String filePath = folder.getFile().getAbsolutePath() + "/" + fileName;
////        Resource classPathResource = new ClassPathResource("Transactions\\trx-sample.txt");
////        String filePath = classPathResource.getFilename();
////
////        try (BufferedReader br = new BufferedReader(new FileReader(classPathResource.getFile()))) {
////            System.out.println("Loading File Data");
////            StringBuilder data = new StringBuilder();
////            String line;
////            while ((line = br.readLine()) != null) {
////                data.append(line).append("\n");
////            }
////            return data.toString();
////        }
////    }
////    @SneakyThrows
////    public int getNumberOfTransactions(String data) {
////        System.out.println(data);
////
////        String[] lines = data.split("\n");
////
////        // Assume the line "FH0004" is located at index 0
////        String fhLine = lines[0];
////
////        // Extract the transaction count from the FH line
////        int transactionCount = Integer.parseInt(fhLine.substring(2));
//////        System.out.println("Getting Number Of Transactions");
////        return transactionCount;
////    }
////    @SneakyThrows
////    public List<String> separateTransactions(String data) {
////        String[] lines = data.split("\n");
////        System.out.println("Seperating transactions");
////        // Exclude the first line as it contains the FH line
////        List<String> transactions = new ArrayList<>();
////        for (int i = 1; i < lines.length; i++) {
////            transactions.add(lines[i]);
////        }
////
////        return transactions;
////    }
////    @SneakyThrows
////    public boolean validateTransactionCount(String data) {
////        int expectedTransactionCount = getNumberOfTransactions(data);
////        List<String> transactions = separateTransactions(data);
////        System.out.println("Validating Transaction Count");
////        // Validate the transaction count
////        return expectedTransactionCount == transactions.size();
////    }
////    @SneakyThrows
////    public boolean validateTransactionLength(String data) {
////        List<String> transactions = separateTransactions(data);
////
////        boolean isValid = true;
////
////        for (String transaction : transactions) {
////            if (transaction.length() != 84) {
////                isValid = false;
////                System.out.println(transaction.length());
////                saveFailedTransaction(transaction);
////            }
////        }
////
////        return isValid;
////    }
////    @SneakyThrows
////    private void saveFailedTransaction(String transaction) {
////        File failedFolder = new File(FAILED_FOLDER);
////        if (!failedFolder.exists()) {
////            failedFolder.mkdirs();
////        }
////
////        String fileName = "failed_transaction_" + UUID.randomUUID().toString() + ".txt";
////        String filePath = FAILED_FOLDER + "\\" + fileName;
////
////        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
////
////            writer.append(transaction);
//////            System.out.println("Saving File Transaction");
////
////        }
////    }
////    @SneakyThrows
////    public void processFile(String data) {
////        boolean isTransactionCountValid = validateTransactionCount(data);
////        boolean isTransactionLengthValid = validateTransactionLength(data);
////
////        if (isTransactionCountValid && isTransactionLengthValid) {
//////            moveFileToProcessingFolder();
////        } else {
////            System.out.println("Validation failed. Check failed transactions in the failed folder.");
////        }
////    }
//
//
//
//
//
//}
