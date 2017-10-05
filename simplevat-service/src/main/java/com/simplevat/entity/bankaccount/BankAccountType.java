package com.simplevat.entity.bankaccount;
import java.io.Serializable;
import javax.persistence.*;
import lombok.Data;

/**
 * Created by Uday on 9/28/2017.
 */
@NamedQueries({
    @NamedQuery(name = "allBankAccountType",
            query = "SELECT v FROM BankAccountType v order by v.defaultFlag DESC, v.orderSequence ASC ")
})
@Entity
@Table(name = "BANK_ACCOUNT_TYPE")
@Data
public class BankAccountType implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Basic
    @Column(name = "name")
    private String name;
    @Column(name = "DEFAULT_FLAG")
    private Character defaultFlag;
    @Column(name = "ORDER_SEQUENCE")
    private Integer orderSequence;
    @Version
    @Column(name = "version_number")
    private Integer versionNumber;
    @Basic
    @Column(name = "delete_flag", columnDefinition = "TINYINT")
    private Boolean deleteFlag = Boolean.FALSE;

}
