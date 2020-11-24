/*************************************************************************************
 * Copyright (C) 2014-2020 GENERAL BYTES s.r.o. All rights reserved.
 *
 * This software may be distributed and modified under the terms of the GNU
 * General Public License version 2 (GPL2) as published by the Free Software
 * Foundation and appearing in the file GPL2.TXT included in the packaging of
 * this file. Please note that GPL2 Section 2[b] requires that all works based
 * on this software must also be made publicly available under the terms of
 * the GPL2 ("Copyleft").
 *
 * Contact information
 * -------------------
 *
 * GENERAL BYTES s.r.o.
 * Web      :  http://www.generalbytes.com
 *
 ************************************************************************************/
package com.generalbytes.batm.server.extensions;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

public interface ITransactionDetails {

    //Types of transactions
    int TYPE_BUY_CRYPTO = 0;
    int TYPE_SELL_CRYPTO = 1;
    int TYPE_WITHDRAW_CASH = 2;
    int TYPE_CASHBACK = 3;

    //Buy states
    int STATUS_BUY_IN_PROGRESS = 0;
    int STATUS_BUY_COMPLETED = 1;
    int STATUS_BUY_ERROR = 2;


    //Sell states
    int STATUS_SELL_PAYMENT_REQUESTED = 0;
    int STATUS_SELL_PAYMENT_ARRIVING = 1;
    int STATUS_SELL_ERROR = 2;
    int STATUS_SELL_PAYMENT_ARRIVED  = 3;


    //Withdraw states
    int STATUS_WITHDRAW_IN_PROGRESS = 0;
    int STATUS_WITHDRAW_COMPLETED = 1;
    int STATUS_WITHDRAW_ERROR = 2;

    //Cashback states
    int STATUS_CASHBACK_COMPLETED = 0;
    int STATUS_CASHBACK_ERROR = 1;

    //error codes
    int BUY_ERROR_NO_ERROR = 0;
    int BUY_ERROR_INVALID_PARAMETERS = 1;
    int BUY_ERROR_INVALID_CURRENCY = 2;
    int BUY_ERROR_INVALID_BALANCE = 3;
    int BUY_ERROR_INVALID_UNKNOWN_ERROR = 4;
    int BUY_ERROR_PROBLEM_SENDING_FROM_HOT_WALLET = 5;
    int BUY_ERROR_PROBLEM_GETTING_BALANCE_FROM_HOT_WALLET = 6;
    int BUY_ERROR_PROBLEM_GETTING_BALANCE_FROM_EXCHANGE = 7;
    int BUY_ERROR_EXCHANGE_WITHDRAWAL   = 8;
    int BUY_ERROR_EXCHANGE_PURCHASE     = 9;
    int BUY_ERROR_UNKNOWN_EXCHANGE_STRATEGY = 10;
    int BUY_ERROR_CONFIGURATION_PROBLEM = 11;
    int BUY_ERROR_FINGERPRINT_UNKNOWN = 12;
    int BUY_ERROR_FEE_GREATER_THAN_AMOUNT = 13;
    int BUY_ERROR_PUBLIC_ID_UNKNOWN = 19;
    int BUY_ERROR_NOT_APPROVED = 20;

    int SELL_ERROR_NO_ERROR = 0;
    int SELL_ERROR_INVALID_PARAMETERS = 1;
    int SELL_ERROR_INVALID_CURRENCY = 2;
    int SELL_ERROR_INVALID_BALANCE = 3;
    int SELL_ERROR_INVALID_UNKNOWN_ERROR = 4;
    int SELL_ERROR_CONFIGURATION_PROBLEM = 11;
    int SELL_ERROR_FINGERPRINT_UNKNOWN = 12;
    int SELL_ERROR_GETTING_DEPOSIT_ADDRESS = 13;
    int SELL_ERROR_PAYMENT_WAIT_TIMED_OUT = 14;
    int SELL_ERROR_NOT_ENOUGH_COINS_ON_EXCHANGE = 15;
    int SELL_ERROR_EXCHANGE_SELL = 16;
    int SELL_ERROR_PAYMENT_INVALID = 17;
    int SELL_ERROR_DISABLED_SELL = 20;
    int SELL_ERROR_NOT_APPROVED = 21;
    int SELL_ERROR_WITHDRAWAL_PROBLEM = 22;
    int SELL_ERROR_WITHDRAWAL_NOT_ALLOWED = 23;

    int WITHDRAW_ERROR_NO_ERROR = 0;
    int WITHDRAW_ERROR_INVALID_PARAMETERS = 1;
    int WITHDRAW_ERROR_INVALID_CURRENCY = 2;
    int WITHDRAW_ERROR_INVALID_UNKNOWN_ERROR = 4;
    int WITHDRAW_ERROR_FINGERPRINT_UNKNOWN = 12;
    int WITHDRAW_ERROR_NOT_ENOUGH_CASH = 13;
    int WITHDRAW_ERROR_PHONE_NUMBER_UNKNOWN = 18;
    int WITHDRAW_ERROR_NOT_APPROVED = 19;
    int WITHDRAW_ERROR_CASH_DISPENSING_FAILED = 22;

    /**
     * Server time of the transaction
     * @return
     */
    Date getServerTime();

    /**
     * Terminal time of the transaction - calculated from the terminal location timezone (you may have terminals across multiple time zones)
     * @return
     */
    Date getTerminalTime();

    /**
     * Returns type of the transaction
     * {@value #TYPE_BUY_CRYPTO}
     * {@value #TYPE_SELL_CRYPTO}
     * {@value #TYPE_WITHDRAW_CASH}
     * @return
     */
    int getType();

    /**
     * Returns serial number of the terminal where the transaction was created
     * @return
     */
    String getTerminalSerialNumber();

    /**
     * Returns unique (in server scope) transaction id. It is generated by server.
     * @return
     */
    String getRemoteTransactionId();

    /**
     * Returns transaction id generated locally by terminal to perform request on server.
     * Don't use it if you don't have to. It is used only for time before server assignes remote transaction id to a transaction.
     * @return
     */
    String getLocalTransactionId();

    /**
     * Returns status of the transaction
     * @return
     */
    int getStatus();

    /**
     * Contains undocumented internal error code
     * 0 = No Error
     * @return
     */
    int getErrorCode();

    /**
     * Fiat amount
     * @return
     */
    BigDecimal getCashAmount();

    /**
     * Fiat currency code (USD, EUR etc)
     * @return
     */
    String getCashCurrency();

    /**
     * Amount of coins - crypto-currency
     * @return
     */
    BigDecimal getCryptoAmount();

    /**
     * Crypto currency code (BTC, ETH etc)
     * @return
     */
    String getCryptoCurrency();

    /**
     * Destination address where the coins were sent to or where the coins were supposed to be sent to.
     * @return
     */
    String getCryptoAddress();

    /**
     * Returns what was the fixed transaction fee in fiat currency used for the transaction
     * @return
     */
    BigDecimal getFixedTransactionFee();

    /**
     * Returns exchange strategy number that was used to finish transaction
     * To see list exchange strategies see crypto settings
     * @return
     */
    int getExchangeStrategyUsed();

    /**
     * Server internal identity id of person performing the transaction
     * @return
     */
    String getIdentityPublicId();

    /**
     * Returns string returned by the wallet or exchange usually it is transaction hash or exchange trade id.
     * @return
     */
    String getDetail();

    /**
     * Indicates if coins were purchased on exchange
     * @return
     */
    boolean isPurchased();

    /**
     * Indicates if coins were sold on exchange
     * @return
     */
    boolean isSold();

    /**
     * In transaction type {@value TYPE_SELL_CRYPTO} this method indicates it cash was already withdrawn
     * @return
     */
    boolean isWithdrawn();

    /**
     * In transaction type {@value TYPE_SELL_CRYPTO} this method indicates it cash is ready to be withdrawn
     * @return
     */
    boolean canBeCashedOut();

    /**
     * In {@value TYPE_WITHDRAW_CASH} type of transaction this method returns
     * remote transaction id of related {@value TYPE_SELL_CRYPTO} sell transaction.
     * @return
     */
    String getRelatedRemoteTransactionId();

    /**
     * Contains customer phonenumber that was used during transaction
     * @return
     */
    String getCellPhoneUsed();

    /**
     * Indicates that sell transaction was withdrawn with 0-confirmation.
     * @return
     */
    boolean isRisk();

    /**
     * Indicates that transaction was automatically finished by server.
     * Ie. terminal reported that 100 USD was inserted into the machine and then terminal went offline.
     * Server automatically finished transaction and sent coins to customer.
     * @return
     */
    boolean isAutoexecuted();


    /**
     * Returns discount code used when performing transaction
     * @return
     */
    String getDiscountCode();

    /**
     * Returns discount percentage of fee
     * @return
     */
    BigDecimal getFeeDiscount();

    /**
     * Amount of discount in cryptocurrency
     * @return
     */
    BigDecimal getCryptoDiscountAmount();

    /**
     * TODO: I don't know
     * @return
     */
    BigDecimal getDiscountQuotient();

    /**
     * @return rate source price
     */
    BigDecimal getRateSourcePrice();

    /**
     * @return expected profit in %
     */
    BigDecimal getExpectedProfit();

    /**
     * @return transaction note
     */
    String getNote();

    List<IBanknoteCounts> getBanknotes();
}
