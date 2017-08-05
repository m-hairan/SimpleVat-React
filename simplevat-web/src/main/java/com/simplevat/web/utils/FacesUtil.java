package com.simplevat.web.utils;
import com.simplevat.entity.User;
import com.simplevat.web.bankaccount.model.BankAccountModel;
import javax.faces.context.FacesContext;
public class FacesUtil {  
    public static final String SESSION_KEY_BANK_ACCOUNT = "SESSION_SELECTED_BANK_ACCOUNT";
    public static final String REQUEST_KEY_BANK_ACCOUNT = "REQUEST_SELECTED_BANK_ACCOUNT";
    public static User getLoggedInUser(){
        return (User)FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("loggedInUser");
    }
    public static void setDataIntoSessionMap(String key, Object value){
        FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put(key,value);
    } 
    public static Object getDataFromSessionMap(String key){
       return FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get(key);
    } 
    public static BankAccountModel getSelectedBankAccount(){
        return (BankAccountModel)getDataFromSessionMap(SESSION_KEY_BANK_ACCOUNT);
    }
    public static void setSelectedBankAccount(BankAccountModel bankAccountModel){
        setDataIntoSessionMap(SESSION_KEY_BANK_ACCOUNT, bankAccountModel);
    }
    
    public static String getSelectedBankAccountFromFlash(FacesContext facesContext){
        return (String)facesContext.getExternalContext().getRequestMap().get(REQUEST_KEY_BANK_ACCOUNT);
    }
    
    public static void setSelectedBankAccountIntoFlash(FacesContext facesContext, String bankAccountId){
        facesContext.getExternalContext().getRequestMap().put(REQUEST_KEY_BANK_ACCOUNT, bankAccountId);
    }
}
