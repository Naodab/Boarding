package model.dto;

import java.time.LocalDate;

public class BoardingFeeAdminResponse {
    private int boardingFeeId;
    private LocalDate startDate;
    private LocalDate endDate;
    private long mainCosts;
    private long subCosts;
    private int numberDays;
    private long payedMoneys;
    private long payedStudents;
    private long nonPrintedInvoices;
    private long totalMoney;

    public BoardingFeeAdminResponse(int boardingFeeId, LocalDate startDate,
        LocalDate endDate, long mainCosts, long subCosts, int numberDays,
        long payedMoneys, long payedStudents, long nonPrintedInvoices,
                                    long totalMoney) {
        this.boardingFeeId = boardingFeeId;
        this.startDate = startDate;
        this.endDate = endDate;
        this.mainCosts = mainCosts;
        this.subCosts = subCosts;
        this.numberDays = numberDays;
        this.payedMoneys = payedMoneys;
        this.payedStudents = payedStudents;
        this.nonPrintedInvoices = nonPrintedInvoices;
        this.totalMoney = totalMoney;
    }

    public int getBoardingFeeId() {
        return boardingFeeId;
    }

    public void setBoardingFeeId(int boardingFeeId) {
        this.boardingFeeId = boardingFeeId;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    public long getMainCosts() {
        return mainCosts;
    }

    public void setMainCosts(long mainCosts) {
        this.mainCosts = mainCosts;
    }

    public long getSubCosts() {
        return subCosts;
    }

    public void setSubCosts(long subCosts) {
        this.subCosts = subCosts;
    }

    public int getNumberDays() {
        return numberDays;
    }

    public void setNumberDays(int numberDays) {
        this.numberDays = numberDays;
    }

    public long getPayedMoneys() {
        return payedMoneys;
    }

    public void setPayedMoneys(long payedMoneys) {
        this.payedMoneys = payedMoneys;
    }

    public long getPayedStudents() {
        return payedStudents;
    }

    public void setPayedStudents(long payedStudents) {
        this.payedStudents = payedStudents;
    }

    public long getNonPrintedInvoices() {
        return nonPrintedInvoices;
    }

    public void setNonPrintedInvoices(long nonPrintedInvoices) {
        this.nonPrintedInvoices = nonPrintedInvoices;
    }

    public long getTotalMoney() {
        return totalMoney;
    }

    public void setTotalMoney(long totalMoney) {
        this.totalMoney = totalMoney;
    }
}
