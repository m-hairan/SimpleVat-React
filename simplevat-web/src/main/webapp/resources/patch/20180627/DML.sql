ALTER TABLE `expense` CHANGE `RECEIPT_NUMBER` `RECEIPT_NUMBER` VARCHAR(55) CHARACTER SET utf8 COLLATE utf8_unicode_ci NULL DEFAULT NULL;
INSERT INTO PATCH(EXECUTION_DATE,PATCH_NO) VALUES(NOW(),'20180627');