package vn.onepay.card.dao;

import java.util.Date;
import java.util.List;
import vn.onepay.account.model.Account;
import vn.onepay.card.model.CardCdr;

public abstract interface CardCdrDAO
{
  public static final String BEAN_NAME = "cardCdrDAO";

  public abstract List<String> findAllCardTypes();

  public abstract List<String> findAllCardStatus();

  public abstract List<CardCdr> findCardCdr(Account paramAccount, List<String> paramList1, String paramString1, String paramString2, String paramString3, List<Integer> paramList, List<String> paramList2, String paramString4, List<String> paramList3, List<String> paramList4, Date paramDate1, Date paramDate2, int paramInt1, int paramInt2);

  public abstract Double[] countAndAmountCdr(Account paramAccount, List<String> paramList1, String paramString1, String paramString2, String paramString3, List<Integer> paramList, List<String> paramList2, String paramString4, List<String> paramList3, List<String> paramList4, Date paramDate1, Date paramDate2);
  
  public void save(List<CardCdr> cardCdrs);
}