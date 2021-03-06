package com.bestlinwei.account.service.impl;

import java.util.List;

import com.bestlinwei.account.mapper.AccountMapper;
import com.bestlinwei.account.service.IAccountService;
import com.bestlinwei.common.bean.account.Account;
import com.bestlinwei.common.bean.account.AccountCriteria;
import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class AccountServiceImpl implements IAccountService {
	
	private Logger logger = LoggerFactory.getLogger(AccountServiceImpl.class);

	@Autowired
	AccountMapper accountMapper;

	@Override
	public Account login(String phone, String password) {
		AccountCriteria criteria = new AccountCriteria();
		criteria.createCriteria().andPhoneEqualTo(phone).andPasswordEqualTo(password);
		List<Account> list = accountMapper.selectByExample(criteria);
		logger.info("{}登陆成功!",phone);
		return CollectionUtils.isNotEmpty(list)?list.get(0):new Account();
	}

	@Override
	public boolean signup(String phone, String password) {
		Account account = new Account();
		account.setPhone(phone);
		account.setPassword(password);
		AccountCriteria example = new AccountCriteria();
		example.createCriteria().andPhoneEqualTo(phone);
		List<Account> list = accountMapper.selectByExample(example);
		if (CollectionUtils.isNotEmpty(list)) {
			logger.warn("{}-用户已存在，请选择其它用户名!",phone);
			return false;
		}
		int result = accountMapper.insertSelective(account);
		logger.info("{}注册成功！",phone);
		return result > 0 ? true : false;
	}

}
