package org.chain3j.protocol.scs.methods.response;

import java.math.BigInteger;
import org.chain3j.utils.Numeric;

public class ExchangeDetail {
    public static class DepositRecord {
        private String depositAmt;
        private String depositTime;

        public String getDepositAmtRaw() {
            return depositAmt;
        }

        public BigInteger getDepositAmt() {
            return Numeric.decodeQuantity(depositAmt);
        }

        public void setDepositAmt(String depositAmt) {
            this.depositAmt = depositAmt;
        }

        public String getDepositTime() {
            return depositTime;
        }

        public void setDepositTime(String depositTime) {
            this.depositTime = depositTime;
        }
    }

    public static class DepositingRecord {
        private String depositingAmt;
        private String depositingTime;

        public String getDepositingAmtRaw() {
            return depositingAmt;
        }

        public BigInteger getDepositingAmt() {
            return Numeric.decodeQuantity(depositingAmt);
        }

        public void setDepositingAmt(String depositingAmt) {
            this.depositingAmt = depositingAmt;
        }

        public String getDepositingTime() {
            return depositingTime;
        }

        public void setDepositTime(String depositingTime) {
            this.depositingTime = depositingTime;
        }
    }

    public static class WithdrawRecord {
        private String withdrawAmt;
        private String withdrawTime;

        public String getWithdrawAmtRaw() {
            return withdrawAmt;
        }

        public BigInteger getWithdrawAmt() {
            return Numeric.decodeQuantity(withdrawAmt);
        }

        public void setWithdrawAmt(String withdrawAmt) {
            this.withdrawAmt = withdrawAmt;
        }

        public String getWithdrawTime() {
            return withdrawTime;
        }

        public void setWithdrawTime(String withdrawTime) {
            this.withdrawTime = withdrawTime;
        }
    }

    public static class WithdrawingRecord {
        private String withdrawingAmt;
        private String withdrawingTime;

        public String getWithdrawingAmtRaw() {
            return withdrawingAmt;
        }

        public BigInteger getWithdrawingAmt() {
            return Numeric.decodeQuantity(withdrawingAmt);
        }

        public void setWithdrawingAmt(String withdrawingAmt) {
            this.withdrawingAmt = withdrawingAmt;
        }

        public String getWithdrawingTime() {
            return withdrawingTime;
        }

        public void setWithdrawTime(String withdrawingTime) {
            this.withdrawingTime = withdrawingTime;
        }
    }
}
