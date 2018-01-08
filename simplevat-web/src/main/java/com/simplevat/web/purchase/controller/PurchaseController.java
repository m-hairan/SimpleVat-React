package com.simplevat.web.purchase.controller;

import com.github.javaplugs.jsf.SpringScopeView;
import com.simplevat.entity.Company;
import com.simplevat.entity.Contact;
import java.io.Serializable;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

import lombok.Getter;
import lombok.Setter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import com.simplevat.entity.Currency;
import com.simplevat.entity.CurrencyConversion;
import com.simplevat.entity.Product;
import com.simplevat.entity.Purchase;
import com.simplevat.entity.User;
import com.simplevat.entity.VatCategory;
import com.simplevat.entity.bankaccount.TransactionCategory;
import com.simplevat.entity.bankaccount.TransactionType;
import com.simplevat.service.CompanyService;
import com.simplevat.service.ContactService;
import com.simplevat.service.CurrencyService;
import com.simplevat.service.ProductService;
import com.simplevat.service.ProjectService;
import com.simplevat.service.PurchaseService;
import com.simplevat.service.TransactionCategoryServiceNew;
import com.simplevat.service.UserServiceNew;
import com.simplevat.service.VatCategoryService;
import com.simplevat.service.bankaccount.TransactionTypeService;
import com.simplevat.web.common.controller.BaseController;
import com.simplevat.web.constant.ContactTypeConstant;
import com.simplevat.web.constant.InvoicePaymentModeConstant;
import com.simplevat.web.constant.InvoicePurchaseStatusConstant;
import com.simplevat.web.constant.ModuleName;
import com.simplevat.web.constant.TransactionCategoryConsatant;
import com.simplevat.web.contact.model.ContactModel;
import com.simplevat.web.purchase.model.PurchaseItemModel;
import com.simplevat.web.purchase.model.PurchaseModel;
import com.simplevat.web.utils.FacesUtil;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.model.SelectItem;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.DefaultStreamedContent;

@Controller
@SpringScopeView
public class PurchaseController extends BaseController implements Serializable {

    private static final long serialVersionUID = 5366159429842989755L;

    @Autowired
    private PurchaseService purchaseService;

    @Autowired
    private ProductService productService;

    @Autowired
    private TransactionTypeService transactionTypeService;

    @Autowired
    private TransactionCategoryServiceNew transactionCategoryService;

    @Autowired
    private CompanyService companyService;

    @Autowired
    private CurrencyService currencyService;

    @Autowired
    private ProjectService projectService;

    @Autowired
    private UserServiceNew userServiceNew;

    @Getter
    @Setter
    private PurchaseModel selectedPurchaseModel;

    @Getter
    @Setter
    private List<TransactionType> transactionTypes;

    @Getter
    @Setter
    private List<VatCategory> vatCategoryList = new ArrayList<>();

    @Getter
    private BigDecimal total;

    @Autowired
    private VatCategoryService vatCategoryService;

    @Getter
    @Setter
    List<SelectItem> vatCategorySelectItemList = new ArrayList<>();

    @Getter
    @Setter
    String fileName;

    @Getter
    private Company company;

    @Getter
    @Setter
    PurchaseControllerHelper purchaseControllerHelper;

    @Getter
    @Setter
    private ContactModel contactModel;

    @Getter
    @Setter
    private CurrencyConversion currencyConversion;

    @Autowired
    private ContactService contactService;

    public PurchaseController() {
        super(ModuleName.PURCHASE_MODULE);
    }

    @PostConstruct
    public void init() {
        company = companyService.findByPK(userServiceNew.findByPK(FacesUtil.getLoggedInUser().getUserId()).getCompany().getCompanyId());
        contactModel = new ContactModel();
        purchaseControllerHelper = new PurchaseControllerHelper();
        Object objSelectedPurchaseModel = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("selectedPurchaseModelId");
        selectedPurchaseModel = new PurchaseModel();
        if (objSelectedPurchaseModel == null) {
            selectedPurchaseModel.setPurchaseItems(new ArrayList<>());
            selectedPurchaseModel.setPurchaseDueOn(30);
            Currency defaultCurrency = company.getCompanyCountryCode().getCurrencyCode();
            if (defaultCurrency != null) {
                selectedPurchaseModel.setCurrency(defaultCurrency);
            }
        } else {
            Purchase purchase = purchaseService.findByPK(Integer.parseInt(objSelectedPurchaseModel.toString()));
            selectedPurchaseModel = purchaseControllerHelper.getPurchaseModel(purchase);

            if (selectedPurchaseModel.getReceiptAttachmentBinary() != null) {
                InputStream stream = new ByteArrayInputStream(selectedPurchaseModel.getReceiptAttachmentBinary());
                selectedPurchaseModel.setAttachmentFileContent(new DefaultStreamedContent(stream));
            }
        }
        populateVatCategory();
        calculateTotal();
    }

    public List<User> users(final String searchQuery) throws Exception {
        return userServiceNew.executeNamedQuery("findAllUsers");
    }

    public List<TransactionType> getAllTransactionType(String str) {
        if (str == null) {
            return this.transactionTypes;
        }
        List<TransactionType> filterList = new ArrayList<>();
        transactionTypes = transactionTypeService.executeNamedQuery("findMoneyOutTransactionType");
        for (TransactionType type : transactionTypes) {
            filterList.add(type);
        }
        return filterList;
    }

    public List<TransactionCategory> completeCategory() {

        List<TransactionCategory> transactionCategoryList = transactionCategoryService.findTransactionCategoryListByParentCategory(TransactionCategoryConsatant.TRANSACTION_CATEGORY_PURCHASE);
        if (transactionCategoryList != null) {
            selectedPurchaseModel.setTransactionType(transactionCategoryList.get(0).getTransactionType());
            return transactionCategoryList;
        } else {
            return new ArrayList<>();
        }
    }

    public List<Product> products(final String searchQuery) throws Exception {
        if (productService.getProductList() != null) {
            return productService.getProductList();
        }
        return null;
    }

    public List<VatCategory> vatCategorys(final String searchQuery) throws Exception {
        if (vatCategoryService.getVatCategoryList() != null) {
            return vatCategoryService.getVatCategoryList();
        }
        return null;
    }

    public void updateVatPercentage(PurchaseItemModel purchaseItemModel) {
        if (purchaseItemModel.getProductService() != null) {
            if (purchaseItemModel.getProductService().getVatCategory() != null) {
                VatCategory vatCategory = vatCategoryService.findByPK(purchaseItemModel.getProductService().getVatCategory().getId());
                purchaseItemModel.setVatId(vatCategory);
            } else {
                purchaseItemModel.setVatId(null);
            }
        }
        updateSubTotal(purchaseItemModel);
    }

    // TODO compare companycurrency and selected Currency
    public String exchangeRate(Currency currency) {
        String exchangeRateString = "";
        currencyConversion = currencyService.getCurrencyRateFromCurrencyConversion(currency.getCurrencyCode());
        if (currencyConversion != null) {
            exchangeRateString = "1 " + currency.getCurrencyIsoCode() + " = " + new BigDecimal(BigInteger.ONE).divide(currencyConversion.getExchangeRate(), 9, RoundingMode.HALF_UP) + " " + company.getCompanyCountryCode().getCurrencyCode().getCurrencyIsoCode();
        }
        return exchangeRateString;
    }

    public BigDecimal totalAmountInHomeCurrency(Currency currency) {
        if (total != null) {
            if (currencyConversion != null) {
                return total.divide(currencyConversion.getExchangeRate(), 9, RoundingMode.HALF_UP);
            }
        }
        return new BigDecimal(0);
    }

    public void deleteLineItem(PurchaseItemModel itemModel) {
        selectedPurchaseModel.getPurchaseItems().remove(itemModel);
        if (itemModel.getSubTotal() != null) {
            total = total.subtract(itemModel.getSubTotal());
        }
    }

    private void populateVatCategory() {
        vatCategoryList = vatCategoryService.getVatCategoryList();
        if (vatCategoryList != null) {
            for (VatCategory vatCategory : vatCategoryList) {
                SelectItem item = new SelectItem(vatCategory.getVat(), vatCategory.getName());
                vatCategorySelectItemList.add(item);
            }
        }
    }

    public void addPurchaseItem() {     //---------------
        boolean validated = validatePurchaseLineItems();

        if (validated) {
            selectedPurchaseModel.addPurchaseItem(new PurchaseItemModel());
        }

    }

    private boolean validatePurchaseLineItems() { //---------------
        boolean validated = true;
        for (PurchaseItemModel lastItem : selectedPurchaseModel.getPurchaseItems()) {
            StringBuilder validationMessage=new StringBuilder("Please Enter ");
            if (lastItem.getUnitPrice() == null) {
                validationMessage.append("Unit Price ");
                validated = false;
            }
            if(validated && lastItem.getUnitPrice().compareTo(BigDecimal.ZERO) <= 0){
                validationMessage=new StringBuilder("Unit price should be greater than 0 ");
                validated = false;
            }
            if(lastItem.getQuatity() < 1 ){
                if(!validated){
                    validationMessage.append("and ");
                }
                validationMessage.append("Quantity should be greater than 0 ");
                validated = false;
            }
            if(!validated){
                validationMessage.append("in Purchase items ");
                FacesMessage message = new FacesMessage(validationMessage.toString());
                message.setSeverity(FacesMessage.SEVERITY_ERROR);
                FacesContext.getCurrentInstance().addMessage("validationId", message);
                validated = false;
            }
        }
        return validated;
    }

    private boolean validateAtLeastOneItem() {
        if (selectedPurchaseModel.getPurchaseItems().size() < 1) {
            FacesMessage message = new FacesMessage("Please add atleast one item to create purchase.");
            message.setSeverity(FacesMessage.SEVERITY_ERROR);
            FacesContext.getCurrentInstance().addMessage("validationId", message);
            return false;
        }
        return true;
    }

    public String deletePurchase() {
        Purchase purchase = purchaseControllerHelper.getPurchase(selectedPurchaseModel);
        purchase.setDeleteFlag(true);
        purchaseService.update(purchase);
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Purchase deleted successfully"));
        return "purchase-list.xhtml?faces-redirect=true";
    }

    public void updateCurrency() {
        setDefaultCurrency();
        if (selectedPurchaseModel.getPurchaseContact() != null) {
            final Contact contact = contactService
                    .getContact(selectedPurchaseModel.getPurchaseContact().getContactId());
            selectedPurchaseModel.setCurrency(contact.getCurrency());
        }
    }

    private void setDefaultCurrency() {
        Currency defaultCurrency = company.getCompanyCountryCode().getCurrencyCode();
        if (defaultCurrency != null) {
            selectedPurchaseModel.setCurrency(defaultCurrency);
        }
    }

    public List<Contact> contacts(final String searchQuery) {
        return contactService.getContacts(searchQuery, ContactTypeConstant.VENDOR);
    }

    public void initCreateContact() {
        contactModel = new ContactModel();
    }

    public void createContact() {
        Currency defaultCurrency = currencyService.getDefaultCurrency();
        final Contact contact = new Contact();
        contact.setBillingEmail(contactModel.getEmail());
        contact.setDeleteFlag(Boolean.FALSE);
        contact.setEmail(contactModel.getEmail());
        contact.setFirstName(contactModel.getFirstName());
        contact.setLastName(contactModel.getLastName());
        contact.setOrganization(contactModel.getOrganization());
        contact.setCreatedBy(FacesUtil.getLoggedInUser().getUserId());
        contact.setCurrency(defaultCurrency);
        contact.setContactType(ContactTypeConstant.VENDOR);
        if (defaultCurrency != null) {
            contactModel.setCurrency(defaultCurrency);
        }
        if (contact.getContactId() != null) {
            contactService.update(contact);
        } else {
            contactService.persist(contact);
        }
        selectedPurchaseModel.setPurchaseContact(contact);

    }

    public String savePurchase() {
        if (!validatePurchaseLineItems() || !validateAtLeastOneItem()) {
            return "";
        }
        save();
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Purchase saved successfully"));
        return "purchase-list?faces-redirect=true";
    }

    public String saveAndContinuePurchase() {
        if (!validatePurchaseLineItems() || !validateAtLeastOneItem()) {
            return "";
        }
        save();
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Purchase saved successfully"));
        return "purchase?faces-redirect=true";
    }

    private void save() {
        User loggedInUser = FacesUtil.getLoggedInUser();
        selectedPurchaseModel.setPurchaseDueDate(getDueDate(selectedPurchaseModel));
        selectedPurchaseModel.setPurchaseAmount(total);
        Purchase purchase = purchaseControllerHelper.getPurchase(selectedPurchaseModel);
        purchase.setDeleteFlag(false);
        purchase.setCreatedBy(loggedInUser.getUserId());
        if (purchase.getPurchaseId() == null || purchase.getPurchaseId() == 0) {
            if (purchase.getPaymentMode() == null) {
                purchase.setPurchaseDueAmount(purchase.getPurchaseAmount());
                updateDueAmountAndStatus(purchase);
            } else if (purchase.getPaymentMode() == InvoicePaymentModeConstant.CASH) {
                purchase.setPurchaseDueAmount(new BigDecimal(0));
                purchase.setStatus(InvoicePurchaseStatusConstant.PAID);
            }
            if (purchase.getProject() != null) {
                projectService.updateProjectExpenseBudget(purchase.getPurchaseAmount(), purchase.getProject());
            }
            companyService.updateCompanyExpenseBudget(purchase.getPurchaseAmount(), company);
            purchaseService.persist(purchase);
        } else {
            purchase.setLastUpdateDate(LocalDateTime.now());
            purchase.setLastUpdateBy(loggedInUser.getUserId());
            Purchase prevPurchase = purchaseService.findByPK(purchase.getPurchaseId());
            BigDecimal defferenceAmount = purchase.getPurchaseAmount().subtract(prevPurchase.getPurchaseAmount());
            if (purchase.getPaymentMode() == null) {
                purchase.setPurchaseDueAmount(purchase.getPurchaseDueAmount().add(defferenceAmount));
                updateDueAmountAndStatus(purchase);
            } else if (purchase.getPaymentMode() == InvoicePaymentModeConstant.CASH) {
                purchase.setPurchaseDueAmount(new BigDecimal(0));
                purchase.setStatus(InvoicePurchaseStatusConstant.PAID);
            }
            if (purchase.getProject() != null) {
                projectService.updateProjectExpenseBudget(defferenceAmount, purchase.getProject());
            }
            companyService.updateCompanyExpenseBudget(defferenceAmount, company);
            purchaseService.update(purchase, purchase.getPurchaseId());
        }
    }

    private void updateDueAmountAndStatus(Purchase purchase) {
        if (purchase.getPurchaseDueAmount().doubleValue() == purchase.getPurchaseAmount().doubleValue()) {
            purchase.setStatus(InvoicePurchaseStatusConstant.UNPAID);
        } else if (purchase.getPurchaseDueAmount().doubleValue() < purchase.getPurchaseAmount().doubleValue()) {
            if (purchase.getPurchaseDueAmount().doubleValue() == 0) {
                purchase.setStatus(InvoicePurchaseStatusConstant.PAID);
            } else {
                purchase.setStatus(InvoicePurchaseStatusConstant.PARTIALPAID);
            }
        }
    }

    public void fileUploadListener(FileUploadEvent e) {
        fileName = e.getFile().getFileName();
        selectedPurchaseModel.setReceiptAttachmentBinary(e.getFile().getContents());
        if (selectedPurchaseModel.getReceiptAttachmentBinary() != null) {
            InputStream stream = new ByteArrayInputStream(selectedPurchaseModel.getReceiptAttachmentBinary());
            selectedPurchaseModel.setAttachmentFileContent(new DefaultStreamedContent(stream));
        }

    }

    public void updateSubTotal(final PurchaseItemModel itemModel) {
        BigDecimal vatPer = new BigDecimal(BigInteger.ZERO);
        final int quantity = itemModel.getQuatity();
        final BigDecimal unitPrice = itemModel.getUnitPrice();
        if (itemModel.getVatId() != null) {
            vatPer = itemModel.getVatId().getVat();
        }
        if (null != unitPrice) {
            final BigDecimal amountWithoutTax = unitPrice.multiply(new BigDecimal(quantity));
            itemModel.setSubTotal(amountWithoutTax);
            if (vatPer != null && vatPer.compareTo(BigDecimal.ZERO) >= 1) {
                final BigDecimal amountWithTax = amountWithoutTax
                        .add(amountWithoutTax.multiply(vatPer).multiply(new BigDecimal(0.01)));
                itemModel.setSubTotal(amountWithTax);
            }
        }
        calculateTotal();
    }

    private void calculateTotal() {
        total = new BigDecimal(0);
        List<PurchaseItemModel> purchaseItemModels = selectedPurchaseModel.getPurchaseItems();
        if (purchaseItemModels != null) {
            for (PurchaseItemModel itemModel : purchaseItemModels) {
                if (itemModel.getSubTotal() != null) {
                    total = total.add(itemModel.getSubTotal());
                }
            }
        }
    }

    private Date getDueDate(PurchaseModel purchaseModel) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(purchaseModel.getPurchaseDate());
        calendar.add(Calendar.DAY_OF_YEAR, purchaseModel.getPurchaseDueOn());
        return calendar.getTime();
    }

}
