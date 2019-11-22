/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.simplevat.rest.invoicecontroller;

import com.simplevat.bank.model.DeleteModel;
import com.simplevat.helper.InvoiceModelHelper;
import com.simplevat.entity.Company;
import com.simplevat.entity.Configuration;
import com.simplevat.entity.invoice.Invoice;
import com.simplevat.service.CompanyService;
import com.simplevat.service.ConfigurationService;
import com.simplevat.service.ContactService;
import com.simplevat.service.CountryService;
import com.simplevat.service.CurrencyService;
import com.simplevat.service.DiscountTypeService;
import com.simplevat.service.ProductService;
import com.simplevat.service.ProjectService;
import com.simplevat.service.TitleService;
import com.simplevat.service.UserServiceNew;
import com.simplevat.service.VatCategoryService;
import com.simplevat.service.invoice.InvoiceService;
import com.simplevat.constant.ConfigurationConstants;
import com.simplevat.constant.InvoicePaymentModeConstant;
import com.simplevat.constant.InvoicePurchaseStatusConstant;
import com.simplevat.contact.model.InvoiceRestModel;
import com.simplevat.service.invoice.InvoiceLineItemService;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author Sonu
 */
@RestController
@RequestMapping("/rest/invoice")
public class InvoiceController implements Serializable {

    private static final long serialVersionUID = 6299117288316809011L;
    private final static Logger LOGGER = LoggerFactory.getLogger(InvoiceController.class);
    @Autowired
    private ContactService contactService;

    @Autowired
    private CountryService countryService;

    @Autowired
    private ConfigurationService configurationService;

    @Autowired
    private CurrencyService currencyService;

    @Autowired
    private CompanyService companyService;

    @Autowired
    private InvoiceService invoiceService;

    @Autowired
    private DiscountTypeService discountTypeService;

    @Autowired
    private UserServiceNew userServiceNew;

    @Autowired
    private ProjectService projectService;

    @Autowired
    private VatCategoryService vatCategoryService;

    @Autowired
    private ProductService productService;

    @Autowired
    private TitleService titleService;

    @Autowired
    private InvoiceLineItemService invoiceLineItemService;
    
    private InvoiceModelHelper invoiceModelHelper = new InvoiceModelHelper();

    @GetMapping("/invoicelist")
    public ResponseEntity populateInvoiceList() {
        List<Invoice> invoices = invoiceService.getInvoiceList();

        List<InvoiceRestModel> invoiceModels = new ArrayList<>();
        if (invoices != null) {
            for (Invoice invoice : invoices) {
                InvoiceRestModel invoiceModel = invoiceModelHelper.getInvoiceModel(invoice);
                invoiceModels.add(invoiceModel);
            }
        }

        return new ResponseEntity(invoiceModels, HttpStatus.OK);
    }

    @GetMapping("/paidinvoicelist")
    public ResponseEntity populatePaidInvoiceList() {
        List<Invoice> invoices = invoiceService.getInvoiceList();

        List<InvoiceRestModel> invoiceModels = new ArrayList<>();
        if (invoices != null) {
            for (Invoice invoice : invoices) {
                InvoiceRestModel invoiceModel = invoiceModelHelper.getInvoiceModel(invoice);
                if (invoiceModel.getStatus() == InvoicePurchaseStatusConstant.PAID) {
                    invoiceModels.add(invoiceModel);
                }
            }
        }

        return new ResponseEntity(invoiceModels, HttpStatus.OK);
    }

    @GetMapping("/unpaidinvoicelist")
    public ResponseEntity populateUnPaidInvoiceList() {
        List<Invoice> invoices = invoiceService.getInvoiceList();

        List<InvoiceRestModel> invoiceModels = new ArrayList<>();
        if (invoices != null) {
            for (Invoice invoice : invoices) {
                InvoiceRestModel invoiceModel = invoiceModelHelper.getInvoiceModel(invoice);
                if (invoiceModel.getStatus() == InvoicePurchaseStatusConstant.UNPAID) {
                    invoiceModels.add(invoiceModel);
                }
            }
        }

        return new ResponseEntity(invoiceModels, HttpStatus.OK);
    }

    @GetMapping("/partialpaidinvoicelist")
    public ResponseEntity populatePartialPaidInvoiceList() {
        List<Invoice> invoices = invoiceService.getInvoiceList();

        List<InvoiceRestModel> invoiceModels = new ArrayList<>();
        if (invoices != null) {
            for (Invoice invoice : invoices) {
                InvoiceRestModel invoiceModel = invoiceModelHelper.getInvoiceModel(invoice);
                if (invoiceModel.getStatus() == InvoicePurchaseStatusConstant.PARTIALPAID) {
                    invoiceModels.add(invoiceModel);
                }
            }
        }

        return new ResponseEntity(invoiceModels, HttpStatus.OK);
    }

    @DeleteMapping(value = "deleteinvoice")
    public ResponseEntity deleteInvoice(@RequestParam(value = "id") Integer id) {
        Invoice invoice = invoiceService.findByPK(id);
        if (invoice == null) {
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }
        invoice.setDeleteFlag(Boolean.TRUE);
        invoiceService.update(invoice, id);
        return new ResponseEntity(HttpStatus.OK);
    }

    @DeleteMapping(value = "deleteinvoices")
    public ResponseEntity deleteInvoices(@RequestBody DeleteModel ids) {
        try {
            invoiceService.deleteByIds(ids.getIds());
            return new ResponseEntity(HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @GetMapping(value = "/editinvoice")
    public ResponseEntity editInvoice(@RequestParam(value = "id") Integer id) {
        Invoice invoice = invoiceService.findByPK(id);
        InvoiceRestModel invoiceModel = invoiceModelHelper.getInvoiceModel(invoice);
        if (invoiceModel == null) {
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity(invoiceModel, HttpStatus.OK);
    }

    @PostMapping("/saveinvoice")
    public Integer save(@RequestBody InvoiceRestModel invoiceModel, @RequestParam(value = "id") Integer userId) {
        Company company = companyService.findByPK(userServiceNew.findByPK(userId).getCompany().getCompanyId());

        Invoice invoice = new Invoice();
        if (invoiceModel.getShippingContact() == null) {
            invoiceModel.setShippingContact(null);
        }
        invoice = invoiceModelHelper.getInvoiceEntity(invoiceModel,invoiceLineItemService);

        if (invoice.getInvoiceId() != null && invoice.getInvoiceId() > 0) {
            invoice.setLastUpdateBy(userId);
            Invoice prevInvoice = invoiceService.findByPK(invoice.getInvoiceId());
            BigDecimal defferenceAmount = invoice.getInvoiceAmount().subtract(prevInvoice.getInvoiceAmount());
            if (invoice.getPaymentMode() == null) {
                invoice.setDueAmount(invoice.getDueAmount().add(defferenceAmount));
                updateDueAmountAndStatus(invoice);
            } else if (invoice.getPaymentMode() == InvoicePaymentModeConstant.CASH) {
                invoice.setDueAmount(new BigDecimal(0));
                invoice.setStatus(InvoicePurchaseStatusConstant.PAID);
                invoice.setFreeze(Boolean.TRUE);
            }
            if (invoice.getInvoiceProject() != null) {
                projectService.updateProjectRevenueBudget(defferenceAmount, invoice.getInvoiceProject());
            }
            companyService.updateCompanyRevenueBudget(defferenceAmount, company);
            invoiceService.update(invoice);
            return invoice.getInvoiceId();
        } else {
            Configuration configuration = configurationService.getConfigurationByName(ConfigurationConstants.INVOICING_REFERENCE_PATTERN);
            if (configuration == null) {
                configuration = new Configuration();
                configuration.setValue("1");
                configuration.setName(ConfigurationConstants.INVOICING_REFERENCE_PATTERN);
            }
            String newInvoicePattern = invoiceModelHelper.getNextInvoiceRefNumber(configuration.getValue(), invoiceModel);
            configuration.setValue(newInvoicePattern);
            invoice.setInvoiceReferenceNumber(newInvoicePattern);
            System.out.println("newInvoicePattern===" + newInvoicePattern + "===id:==" + configuration.getId());
            if (configuration.getId() != null) {
                configurationService.update(configuration);
            } else {
                configurationService.persist(configuration);
            }
            invoice.setCreatedBy(userId);
            if (invoice.getPaymentMode() == null) {
                invoice.setDueAmount(invoice.getInvoiceAmount());
                updateDueAmountAndStatus(invoice);
            } else if (invoice.getPaymentMode() == InvoicePaymentModeConstant.CASH) {
                invoice.setDueAmount(new BigDecimal(0));
                invoice.setStatus(InvoicePurchaseStatusConstant.PAID);
                invoice.setFreeze(Boolean.TRUE);
            }
            if (invoice.getInvoiceProject() != null) {
                projectService.updateProjectRevenueBudget(invoice.getInvoiceAmount(), invoice.getInvoiceProject());
            }
            companyService.updateCompanyRevenueBudget(invoice.getInvoiceAmount(), company);
            invoiceService.persist(invoice);
            return invoice.getInvoiceId();
        }
    }

    private void updateDueAmountAndStatus(Invoice selectedInvoice) {
        if (selectedInvoice.getDueAmount().doubleValue() == selectedInvoice.getInvoiceAmount().doubleValue()) {
            selectedInvoice.setStatus(InvoicePurchaseStatusConstant.UNPAID);
        } else if (selectedInvoice.getDueAmount().doubleValue() < selectedInvoice.getInvoiceAmount().doubleValue()) {
            if (selectedInvoice.getDueAmount().doubleValue() == 0) {
                selectedInvoice.setStatus(InvoicePurchaseStatusConstant.PAID);
                selectedInvoice.setFreeze(Boolean.TRUE);
            } else {
                selectedInvoice.setStatus(InvoicePurchaseStatusConstant.PARTIALPAID);
            }
        }
    }

}
