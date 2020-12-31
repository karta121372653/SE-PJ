package user;

import java.io.IOException;

public class administrator extends user {
	final boolean powerOfAccount;
	private String account;
	
	administrator(){
		powerOfAccount = true;
		account = printUserInf()[0];
	}
	
	public String addUser(String userName, String presetAccount, int idenity) throws IOException {
		
		if (!powerOfAccount) {
			return "沒有權限";
		}
		String word = super.addUser(presetAccount, "123456", userName, idenity);
		return word;
	}
	
	@Override
	public String delUser(String delAccount) throws IOException {
		
		if (!powerOfAccount) {
			return "沒有權限";
		}
		if (delAccount == this.account) {
			return "不能刪除自己";
		}
		else {
			String word = super.delUser(delAccount);
			return word;
		}
	}
	
	@Override
	public String changeUser(String userName, String newAccount, String newPassword) throws IOException {
		if (!powerOfAccount) {
			return "沒有權限";
		}
		String word = super.changeUser(userName, newAccount, newPassword);
		return word;
	}
	
}
