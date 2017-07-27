package com.simplevat.web.enums;

public enum TransactionTypeFlag {

	CREDIT('C'),
	DEBIT('D');
	
	private final Character type;
	
	TransactionTypeFlag(Character type){
		this.type = type;	
	}

	public Character getType() {
		return type;
	}
	
}
