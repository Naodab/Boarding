package model.dto;

import java.time.LocalDate;

public class InvoiceAdminResponse {
    private int invoiceId;
    private int studentId;
    private String studentName;
    private String studentClass;
    private LocalDate paymentDate;
    private long money;
    private String status;
    private long returnMoney;
    private long mainCosts;
    private long subCosts;

    public InvoiceAdminResponse(int invoiceId, int studentId,
        LocalDate paymentDate, long money) {
        this.invoiceId = invoiceId;
        this.studentId = studentId;
        this.paymentDate = paymentDate;
        this.money = money;
    }

    public int getInvoiceId() {
        return invoiceId;
    }

    public void setInvoiceId(int invoiceId) {
        this.invoiceId = invoiceId;
    }

    public int getStudentId() {
        return studentId;
    }

    public void setStudentId(int studentId) {
        this.studentId = studentId;
    }

    public String getStudentName() {
        return studentName;
    }

    public void setStudentName(String studentName) {
        this.studentName = studentName;
    }

    public String getStudentClass() {
        return studentClass;
    }

    public void setStudentClass(String studentClass) {
        this.studentClass = studentClass;
    }

    public LocalDate getPaymentDate() {
        return paymentDate;
    }

    public void setPaymentDate(LocalDate paymentDate) {
        this.paymentDate = paymentDate;
    }

    public long getMoney() {
        return money;
    }

    public void setMoney(long money) {
        this.money = money;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public long getReturnMoney() {
        return returnMoney;
    }

    public void setReturnMoney(long returnMoney) {
        this.returnMoney = returnMoney;
    }

    public long getMainCosts() {
        return mainCosts;
    }

    public long getSubCosts() {
        return subCosts;
    }

    public void setSubCosts(long subCosts) {
        this.subCosts = subCosts;
    }

    public void setMainCosts(long mainCosts) {
        this.mainCosts = mainCosts;
    }
}
