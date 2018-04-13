SELECT ContractID, hong1.customer.FIRSTNAME, hong1.customer.LASTNAME, hong1.model.name FROM
	(SELECT DISTINCT ContID AS ContractID FROM hong1.cont_part WHERE STATUS=1),
	hong1.customer, hong1.model, hong1.contract 
	WHERE hong1.contract.contid=ContractID AND hong1.contract.mid=hong1.model.mid
		AND hong1.contract.cusid=hong1.customer.cusid;