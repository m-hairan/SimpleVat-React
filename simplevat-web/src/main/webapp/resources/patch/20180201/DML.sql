ALTER TABLE CONFIGURATION CHANGE `VALUE` `VALUE` VARCHAR(1000) CHARACTER SET utf8 COLLATE utf8_unicode_ci NULL DEFAULT NULL;
INSERT INTO `CONFIGURATION` (`LAST_UPDATED_BY`, `LAST_UPDATE_DATE`, `NAME`, `VALUE`) VALUES
(1, '2018-02-01 19:49:44', 'INVOICE_MAIL_TAMPLATE_BODY', '<p>Hi {contactName},</p><p><br></p><p>Please find the attached your latest invoice of {invoiceCurrencySymbol}{dueAmount} due on {invoiceDueDate}.</p><p><br></p><p>Pay it through wired transfer to our bank account given in attached invoice.</p><p><br></p><p>OR</p><p><br></p><p>Pay here :&nbsp;</p><p><br></p><p>Many thanks,</p><p>{senderName}</p><p>For {companyName}</p>'),
(1, '2018-02-01 19:49:44', 'INVOICE_MAIL_TAMPLATE_SUBJECT', 'Invoice  {invoiceReferenceNumber}');

INSERT INTO PATCH(EXECUTION_DATE,PATCH_NO) VALUES(NOW(),'20180201');