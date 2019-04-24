package com.see.rpc.entity;

import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

public class CustomerSiteOne {
	//"自动增长ID")
	private Integer id;

	//"客户ID")
	private Integer custId;

	//"一级站点名称")
	private String name;

	//"eas的code")
	private String code;

	//"合同开始日期")
	private Date contractStartdate;

	//"合同结束日期")
	private Date contractEnddate;

	//"结算主管")
	private Integer settleUserId;

	//"一级客服主管")
	private Integer serviceUserId;

	//"客户客服专员")
	private Integer customerServiceId;

	//"客服负责人Id")
	private Integer customerPrincipalId;

	//"客服负责人姓名")
	private String customerPrincipalName;

	//"商务负责人Id")
	private Integer businessPrincipalId;

	//"商务负责人姓名")
	private String businessPrincipalName;

	//"付款方名称")
	private String payerName;

	//"客户业务联系人")
	private String customerContact;

	//"滞纳金比例")
	private Float penaltyRatio;

	//"允许直接添加收费条目")
	private Byte isAllowAddChargeItem;

	//"服务流程文档路径")
	private String docUrl;

	//"开票内容备注")
	private String receiptComment;

	//创建人id")
	private Integer createId;

	//"创建时间")
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private Date createTime;

	//"修改人员")
	private Integer updateId;

	//"修改时间")
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private Date updateTime;

	//"二级站点的数量")
	private Integer siteTwoNum;

	//"社保方案计数器")
	private Integer socialProductNum;

	//"公积金方案计数器")
	private Integer accfundProductNum;

	//"结算类型code")
	private String settleTypeCode;

	//"付款方类型code")
	private String payerTypeCode;

	//"付款方证件类型code")
	private String payerCertificateCode;

	//"付款方识别号")
	private String payerIdentifier;

	//"付款方手机号码")
	private String payerCellphoneNumber;

	//"付款方地址及电话")
	private String payerAddressPhone;

	//"付款方开户行")
	private String payerBankName;

	//"付款方账号")
	private String payerAccount;

	//"收款方ID")
	private Integer payeeId;

	//"银行账号")
	private Integer bankAccountId;

	//"收款方识别号")
	private String payeeIdentifier;

	//"收款方地址及电话")
	private String payeeAddressPhone;

	//"合同主体ID")
	private Integer contractSubjectId;

	//"客户类型 1:派遣(HRO) 2:人事代理 3:非HRBPO 4:培训咨询 5:招聘 6:EHR软件 7:商保体检福利")
	private Integer customertype;

	//"是否新上线站点")
	private Byte isNewOnLine;

	//"收款设置类型 (0:按收款周期  1：按付款周期)")
	private Integer recSettingType;

	//"收款单导出模板")
	private String recExportTempleteCode;

	//"社保增员失败自动形成补缴项目")
	private Integer isSocialAutoBj;

	//"公积金增员失败自动形成补缴项目")
	private Integer isAccfundAutoBj;

	//"允许多地区投保")
	private Integer isAllowMoreAreaTb;

	//"是否校验员工工资银行卡。用于校验员工上次和本次发放工资银行卡是否一致。0代表不对比，1代表对比")
	private Integer isComparePreviousBankInfoSalary;

	//"校验员工的银行卡号是否重复 0代表不校验，1代表校验")
	private Integer isCheckRepeatedOfEmployeeBankCard;

	//"是否允许其他银行发放: 1,允许 0 不允许")
	private Byte allowOtherBank;

	//"鏄?惁鍒犻櫎")
	private Byte isDelete;

	//"方案类型(0:供应商  1:方案)")
	private Integer recComminsureType;

	//"客户所属行业")
	private String industryType;

	//"发票收件人")
	private String recipient;

	//"发票寄送地址")
	private String recipientAddress;

	//"收件人电话")
	private String recipientPhone;

	//"事业部编码")
	private String unitCode;

	//"事业部名称")
	private String unitName;

	//"部门编码")
	private String deptCode;

	//"部门名称")
	private String deptName;

	//"服务注意事项")
	private String comment;

	//"工资发放地")
	private String salayIssuedAddr;

	//"工资发放银行")
	private String salayIssuedBank;

	//"个税报税地")
	private String taxDeclareAddr;

	public Integer getIsCheckRepeatedOfEmployeeBankCard() {
		return isCheckRepeatedOfEmployeeBankCard;
	}

	public void setIsCheckRepeatedOfEmployeeBankCard(Integer isCheckRepeatedOfEmployeeBankCard) {
		this.isCheckRepeatedOfEmployeeBankCard = isCheckRepeatedOfEmployeeBankCard;
	}

	public CustomerSiteOne(Integer id) {
		this.id = id;
	}

	public CustomerSiteOne() {
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getCustId() {
		return custId;
	}

	public void setCustId(Integer custId) {
		this.custId = custId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name == null ? null : name.trim();
	}

	public Date getContractStartdate() {
		return contractStartdate;
	}

	public void setContractStartdate(Date contractStartdate) {
		this.contractStartdate = contractStartdate;
	}

	public Date getContractEnddate() {
		return contractEnddate;
	}

	public void setContractEnddate(Date contractEnddate) {
		this.contractEnddate = contractEnddate;
	}

	public Integer getSettleUserId() {
		return settleUserId;
	}

	public void setSettleUserId(Integer settleUserId) {
		this.settleUserId = settleUserId;
	}

	public Integer getServiceUserId() {
		return serviceUserId;
	}

	public void setServiceUserId(Integer serviceUserId) {
		this.serviceUserId = serviceUserId;
	}

	public Integer getCustomerServiceId() {
		return customerServiceId;
	}

	public void setCustomerServiceId(Integer customerServiceId) {
		this.customerServiceId = customerServiceId;
	}

	public Integer getCustomerPrincipalId() {
		return customerPrincipalId;
	}

	public void setCustomerPrincipalId(Integer customerPrincipalId) {
		this.customerPrincipalId = customerPrincipalId;
	}

	public String getCustomerPrincipalName() {
		return customerPrincipalName;
	}

	public void setCustomerPrincipalName(String customerPrincipalName) {
		this.customerPrincipalName = customerPrincipalName == null ? null : customerPrincipalName.trim();
	}

	public Integer getBusinessPrincipalId() {
		return businessPrincipalId;
	}

	public void setBusinessPrincipalId(Integer businessPrincipalId) {
		this.businessPrincipalId = businessPrincipalId;
	}

	public String getBusinessPrincipalName() {
		return businessPrincipalName;
	}

	public void setBusinessPrincipalName(String businessPrincipalName) {
		this.businessPrincipalName = businessPrincipalName == null ? null : businessPrincipalName.trim();
	}

	public String getPayerName() {
		return payerName;
	}

	public void setPayerName(String payerName) {
		this.payerName = payerName == null ? null : payerName.trim();
	}

	public String getCustomerContact() {
		return customerContact;
	}

	public void setCustomerContact(String customerContact) {
		this.customerContact = customerContact == null ? null : customerContact.trim();
	}

	public Float getPenaltyRatio() {
		return penaltyRatio;
	}

	public void setPenaltyRatio(Float penaltyRatio) {
		this.penaltyRatio = penaltyRatio;
	}

	public Byte getIsAllowAddChargeItem() {
		return isAllowAddChargeItem;
	}

	public void setIsAllowAddChargeItem(Byte isAllowAddChargeItem) {
		this.isAllowAddChargeItem = isAllowAddChargeItem;
	}

	public String getDocUrl() {
		return docUrl;
	}

	public void setDocUrl(String docUrl) {
		this.docUrl = docUrl == null ? null : docUrl.trim();
	}

	public String getReceiptComment() {
		return receiptComment;
	}

	public void setReceiptComment(String receiptComment) {
		this.receiptComment = receiptComment == null ? null : receiptComment.trim();
	}

	public Integer getCreateId() {
		return createId;
	}

	public void setCreateId(Integer createId) {
		this.createId = createId;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Integer getUpdateId() {
		return updateId;
	}

	public void setUpdateId(Integer updateId) {
		this.updateId = updateId;
	}

	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	public Integer getSiteTwoNum() {
		return siteTwoNum;
	}

	public void setSiteTwoNum(Integer siteTwoNum) {
		this.siteTwoNum = siteTwoNum;
	}

	public Integer getSocialProductNum() {
		return socialProductNum;
	}

	public void setSocialProductNum(Integer socialProductNum) {
		this.socialProductNum = socialProductNum;
	}

	public Integer getAccfundProductNum() {
		return accfundProductNum;
	}

	public void setAccfundProductNum(Integer accfundProductNum) {
		this.accfundProductNum = accfundProductNum;
	}

	public String getSettleTypeCode() {
		return settleTypeCode;
	}

	public void setSettleTypeCode(String settleTypeCode) {
		this.settleTypeCode = settleTypeCode == null ? null : settleTypeCode.trim();
	}

	public String getPayerTypeCode() {
		return payerTypeCode;
	}

	public void setPayerTypeCode(String payerTypeCode) {
		this.payerTypeCode = payerTypeCode == null ? null : payerTypeCode.trim();
	}

	public String getPayerCertificateCode() {
		return payerCertificateCode;
	}

	public void setPayerCertificateCode(String payerCertificateCode) {
		this.payerCertificateCode = payerCertificateCode == null ? null : payerCertificateCode.trim();
	}

	public String getPayerIdentifier() {
		return payerIdentifier;
	}

	public void setPayerIdentifier(String payerIdentifier) {
		this.payerIdentifier = payerIdentifier == null ? null : payerIdentifier.trim();
	}

	public String getPayerCellphoneNumber() {
		return payerCellphoneNumber;
	}

	public void setPayerCellphoneNumber(String payerCellphoneNumber) {
		this.payerCellphoneNumber = payerCellphoneNumber == null ? null : payerCellphoneNumber.trim();
	}

	public String getPayerAddressPhone() {
		return payerAddressPhone;
	}

	public void setPayerAddressPhone(String payerAddressPhone) {
		this.payerAddressPhone = payerAddressPhone == null ? null : payerAddressPhone.trim();
	}

	public String getPayerBankName() {
		return payerBankName;
	}

	public void setPayerBankName(String payerBankName) {
		this.payerBankName = payerBankName == null ? null : payerBankName.trim();
	}

	public String getPayerAccount() {
		return payerAccount;
	}

	public void setPayerAccount(String payerAccount) {
		this.payerAccount = payerAccount == null ? null : payerAccount.trim();
	}

	public Integer getPayeeId() {
		return payeeId;
	}

	public void setPayeeId(Integer payeeId) {
		this.payeeId = payeeId;
	}

	public Integer getBankAccountId() {
		return bankAccountId;
	}

	public void setBankAccountId(Integer bankAccountId) {
		this.bankAccountId = bankAccountId;
	}

	public String getPayeeIdentifier() {
		return payeeIdentifier;
	}

	public void setPayeeIdentifier(String payeeIdentifier) {
		this.payeeIdentifier = payeeIdentifier == null ? null : payeeIdentifier.trim();
	}

	public String getPayeeAddressPhone() {
		return payeeAddressPhone;
	}

	public void setPayeeAddressPhone(String payeeAddressPhone) {
		this.payeeAddressPhone = payeeAddressPhone == null ? null : payeeAddressPhone.trim();
	}

	public Integer getContractSubjectId() {
		return contractSubjectId;
	}

	public void setContractSubjectId(Integer contractSubjectId) {
		this.contractSubjectId = contractSubjectId;
	}

	public Integer getCustomertype() {
		return customertype;
	}

	public void setCustomertype(Integer customertype) {
		this.customertype = customertype;
	}

	public Byte getIsNewOnLine() {
		return isNewOnLine;
	}

	public void setIsNewOnLine(Byte isNewOnLine) {
		this.isNewOnLine = isNewOnLine;
	}

	public Integer getRecSettingType() {
		return recSettingType;
	}

	public void setRecSettingType(Integer recSettingType) {
		this.recSettingType = recSettingType;
	}

	public String getRecExportTempleteCode() {
		return recExportTempleteCode;
	}

	public void setRecExportTempleteCode(String recExportTempleteCode) {
		this.recExportTempleteCode = recExportTempleteCode == null ? null : recExportTempleteCode.trim();
	}

	public Integer getIsSocialAutoBj() {
		return isSocialAutoBj;
	}

	public void setIsSocialAutoBj(Integer isSocialAutoBj) {
		this.isSocialAutoBj = isSocialAutoBj;
	}

	public Integer getIsAccfundAutoBj() {
		return isAccfundAutoBj;
	}

	public void setIsAccfundAutoBj(Integer isAccfundAutoBj) {
		this.isAccfundAutoBj = isAccfundAutoBj;
	}

	public Integer getIsAllowMoreAreaTb() {
		return isAllowMoreAreaTb;
	}

	public void setIsAllowMoreAreaTb(Integer isAllowMoreAreaTb) {
		this.isAllowMoreAreaTb = isAllowMoreAreaTb;
	}

	public Integer getIsComparePreviousBankInfoSalary() {
		return isComparePreviousBankInfoSalary;
	}

	public void setIsComparePreviousBankInfoSalary(Integer isComparePreviousBankInfoSalary) {
		this.isComparePreviousBankInfoSalary = isComparePreviousBankInfoSalary;
	}

	public Byte getAllowOtherBank() {
		return allowOtherBank;
	}

	public void setAllowOtherBank(Byte allowOtherBank) {
		this.allowOtherBank = allowOtherBank;
	}

	public Byte getIsDelete() {
		return isDelete;
	}

	public void setIsDelete(Byte isDelete) {
		this.isDelete = isDelete;
	}

	public Integer getRecComminsureType() {
		return recComminsureType;
	}

	public void setRecComminsureType(Integer recComminsureType) {
		this.recComminsureType = recComminsureType;
	}

	public String getIndustryType() {
		return industryType;
	}

	public void setIndustryType(String industryType) {
		this.industryType = industryType == null ? null : industryType.trim();
	}

	public String getRecipient() {
		return recipient;
	}

	public void setRecipient(String recipient) {
		this.recipient = recipient == null ? null : recipient.trim();
	}

	public String getRecipientAddress() {
		return recipientAddress;
	}

	public void setRecipientAddress(String recipientAddress) {
		this.recipientAddress = recipientAddress == null ? null : recipientAddress.trim();
	}

	public String getRecipientPhone() {
		return recipientPhone;
	}

	public void setRecipientPhone(String recipientPhone) {
		this.recipientPhone = recipientPhone == null ? null : recipientPhone.trim();
	}

	public String getUnitCode() {
		return unitCode;
	}

	public void setUnitCode(String unitCode) {
		this.unitCode = unitCode == null ? null : unitCode.trim();
	}

	public String getUnitName() {
		return unitName;
	}

	public void setUnitName(String unitName) {
		this.unitName = unitName == null ? null : unitName.trim();
	}

	public String getDeptCode() {
		return deptCode;
	}

	public void setDeptCode(String deptCode) {
		this.deptCode = deptCode == null ? null : deptCode.trim();
	}

	public String getDeptName() {
		return deptName;
	}

	public void setDeptName(String deptName) {
		this.deptName = deptName == null ? null : deptName.trim();
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment == null ? null : comment.trim();
	}

	public String getSalayIssuedAddr() {
		return salayIssuedAddr;
	}

	public void setSalayIssuedAddr(String salayIssuedAddr) {
		this.salayIssuedAddr = salayIssuedAddr == null ? null : salayIssuedAddr.trim();
	}

	public String getSalayIssuedBank() {
		return salayIssuedBank;
	}


	public void setSalayIssuedBank(String salayIssuedBank) {
		this.salayIssuedBank = salayIssuedBank == null ? null : salayIssuedBank.trim();
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getTaxDeclareAddr() {
		return taxDeclareAddr;
	}

	public void setTaxDeclareAddr(String taxDeclareAddr) {
		this.taxDeclareAddr = taxDeclareAddr == null ? null : taxDeclareAddr.trim();
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append(getClass().getSimpleName());
		sb.append(" [");
		sb.append("Hash = ").append(hashCode());
		sb.append(", id=").append(id);
		sb.append(", custId=").append(custId);
		sb.append(", name=").append(name);
		sb.append(", contractStartdate=").append(contractStartdate);
		sb.append(", contractEnddate=").append(contractEnddate);
		sb.append(", settleUserId=").append(settleUserId);
		sb.append(", serviceUserId=").append(serviceUserId);
		sb.append(", customerServiceId=").append(customerServiceId);
		sb.append(", customerPrincipalId=").append(customerPrincipalId);
		sb.append(", customerPrincipalName=").append(customerPrincipalName);
		sb.append(", businessPrincipalId=").append(businessPrincipalId);
		sb.append(", businessPrincipalName=").append(businessPrincipalName);
		sb.append(", payerName=").append(payerName);
		sb.append(", customerContact=").append(customerContact);
		sb.append(", penaltyRatio=").append(penaltyRatio);
		sb.append(", isAllowAddChargeItem=").append(isAllowAddChargeItem);
		sb.append(", docUrl=").append(docUrl);
		sb.append(", receiptComment=").append(receiptComment);
		sb.append(", createdUser=").append(createId);
		sb.append(", createdTime=").append(createTime);
		sb.append(", modifyUser=").append(updateId);
		sb.append(", modifyTime=").append(updateTime);
		sb.append(", siteTwoNum=").append(siteTwoNum);
		sb.append(", socialProductNum=").append(socialProductNum);
		sb.append(", accfundProductNum=").append(accfundProductNum);
		sb.append(", settleTypeCode=").append(settleTypeCode);
		sb.append(", payerTypeCode=").append(payerTypeCode);
		sb.append(", payerCertificateCode=").append(payerCertificateCode);
		sb.append(", payerIdentifier=").append(payerIdentifier);
		sb.append(", payerCellphoneNumber=").append(payerCellphoneNumber);
		sb.append(", payerAddressPhone=").append(payerAddressPhone);
		sb.append(", payerBankName=").append(payerBankName);
		sb.append(", payerAccount=").append(payerAccount);
		sb.append(", payeeId=").append(payeeId);
		sb.append(", bankAccountId=").append(bankAccountId);
		sb.append(", payeeIdentifier=").append(payeeIdentifier);
		sb.append(", payeeAddressPhone=").append(payeeAddressPhone);
		sb.append(", contractSubjectId=").append(contractSubjectId);
		sb.append(", customertype=").append(customertype);
		sb.append(", isNewOnLine=").append(isNewOnLine);
		sb.append(", recSettingType=").append(recSettingType);
		sb.append(", recExportTempleteCode=").append(recExportTempleteCode);
		sb.append(", isSocialAutoBj=").append(isSocialAutoBj);
		sb.append(", isAccfundAutoBj=").append(isAccfundAutoBj);
		sb.append(", isAllowMoreAreaTb=").append(isAllowMoreAreaTb);
		sb.append(", isComparePreviousBankInfoSalary=").append(isComparePreviousBankInfoSalary);
		sb.append(", allowOtherBank=").append(allowOtherBank);
		sb.append(", isDelete=").append(isDelete);
		sb.append(", recComminsureType=").append(recComminsureType);
		sb.append(", industryType=").append(industryType);
		sb.append(", recipient=").append(recipient);
		sb.append(", recipientAddress=").append(recipientAddress);
		sb.append(", recipientPhone=").append(recipientPhone);
		sb.append(", unitCode=").append(unitCode);
		sb.append(", unitName=").append(unitName);
		sb.append(", deptCode=").append(deptCode);
		sb.append(", deptName=").append(deptName);
		sb.append(", comment=").append(comment);
		sb.append(", salayIssuedAddr=").append(salayIssuedAddr);
		sb.append(", salayIssuedBank=").append(salayIssuedBank);
		sb.append(", taxDeclareAddr=").append(taxDeclareAddr);
		sb.append("]");
		return sb.toString();
	}
}