package com.ondam.wallet.service;

import java.util.Vector;

import com.ondam.group.dao.FamilyMemberDAO;
import com.ondam.group.dto.FamilyMemberDTO;
import com.ondam.notification.dao.NotificationDAO;
import com.ondam.notification.dto.NotificationDTO;
import com.ondam.user.dto.UserDTO;
import com.ondam.wallet.dao.WalletDAO;
import com.ondam.wallet.dao.WalletTransactionDAO;
import com.ondam.wallet.dto.WalletDTO;
import com.ondam.wallet.dto.WalletTransactionDTO;

public class WalletService {

	private WalletDAO dao;
	private WalletTransactionDAO walletTransactionDao;
	private NotificationDAO notificationDao;
	private FamilyMemberDAO familyMemberDao;

	public WalletService() {
		this.dao = new WalletDAO();
	    this.walletTransactionDao = new WalletTransactionDAO();
	    this.familyMemberDao = new FamilyMemberDAO();
	    this.notificationDao = new NotificationDAO();
	}

	public Vector<WalletDTO> getWalletList() {
		return dao.getWallet();
	}

	public boolean createWallet(WalletDTO dto) {
		return dao.insertWallet(dto);
	}

	public boolean modifyWallet(WalletDTO dto, int walletNo) {
		return dao.updateWallet(dto, walletNo);
	}

	public boolean removeWallet(int walletNo) {
		return dao.deleteWallet(walletNo);
	}
	
	public WalletDTO getWalletByFamilyNo(int familyNo) {
	    return dao.getWalletByFamilyNo(familyNo);
	}
	
	public boolean updateBalance(int walletNo, int newBalance) {
	    return dao.updateBalance(walletNo, newBalance);
	}
	
	public void deductBalance(int familyNo, int amount, int orderNo, UserDTO loginUser) {
	    WalletDTO wallet = dao.getWalletByFamilyNo(familyNo);
	    if (wallet == null) return;

	    int newBalance = wallet.getBalance() - amount;

	    // 1. 잔액 차감
	    dao.updateBalance(wallet.getWalletNo(), newBalance);

	    // 2. 거래 내역 INSERT
	    WalletTransactionDTO tx = new WalletTransactionDTO();
	    tx.setWalletNo(wallet.getWalletNo());
	    tx.setUserNo(loginUser.getUserNo());
	    tx.setUserName(loginUser.getUserName());
	    tx.setTransactionType(1); // 1: 결제 사용
	    tx.setAmount(amount);
	    tx.setBalanceSnapshot(newBalance);
	    tx.setOrderNo(orderNo); // 주문번호 연결
	    tx.setTransactionDate(new java.sql.Timestamp(System.currentTimeMillis()).toString());
	    tx.setTransactionMemo("주문 결제");
	    walletTransactionDao.insertWalletTransaction(tx);

	    // 3. 그룹 전원 알림 (notificationType = 5)
	    String content = "\"" + loginUser.getUserName() + "\"님이 함께 지갑에서 " + amount + "원을 결제에 사용했어요!";
	    Vector<FamilyMemberDTO> memberList = familyMemberDao.getFamilyMembersByFamilyNo(familyNo);
	    for (FamilyMemberDTO m : memberList) {
	        NotificationDTO noti = new NotificationDTO();
	        noti.setUserNo(m.getUserNo());
	        noti.setNotificationType(5);
	        noti.setNotificationContent(content);
	        noti.setRefNo(orderNo);
	        noti.setCreatedAt(new java.sql.Timestamp(System.currentTimeMillis()).toString());
	        notificationDao.insertNotification(noti);
	    }
	}
}